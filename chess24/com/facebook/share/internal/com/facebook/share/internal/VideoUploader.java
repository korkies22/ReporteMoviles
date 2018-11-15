/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.ParcelFileDescriptor
 *  android.os.ParcelFileDescriptor$AutoCloseInputStream
 *  android.text.TextUtils
 *  android.util.Log
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.internal.WorkQueue;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoUploader {
    private static final String ERROR_BAD_SERVER_RESPONSE = "Unexpected error in server response";
    private static final String ERROR_UPLOAD = "Video upload failed";
    private static final int MAX_RETRIES_PER_PHASE = 2;
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_END_OFFSET = "end_offset";
    private static final String PARAM_FILE_SIZE = "file_size";
    private static final String PARAM_REF = "ref";
    private static final String PARAM_SESSION_ID = "upload_session_id";
    private static final String PARAM_START_OFFSET = "start_offset";
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_UPLOAD_PHASE = "upload_phase";
    private static final String PARAM_VALUE_UPLOAD_FINISH_PHASE = "finish";
    private static final String PARAM_VALUE_UPLOAD_START_PHASE = "start";
    private static final String PARAM_VALUE_UPLOAD_TRANSFER_PHASE = "transfer";
    private static final String PARAM_VIDEO_FILE_CHUNK = "video_file_chunk";
    private static final String PARAM_VIDEO_ID = "video_id";
    private static final int RETRY_DELAY_BACK_OFF_FACTOR = 3;
    private static final int RETRY_DELAY_UNIT_MS = 5000;
    private static final String TAG = "VideoUploader";
    private static final int UPLOAD_QUEUE_MAX_CONCURRENT = 8;
    private static AccessTokenTracker accessTokenTracker;
    private static Handler handler;
    private static boolean initialized;
    private static Set<UploadContext> pendingUploads;
    private static WorkQueue uploadQueue;

    static {
        uploadQueue = new WorkQueue(8);
        pendingUploads = new HashSet<UploadContext>();
    }

    private static void cancelAllRequests() {
        synchronized (VideoUploader.class) {
            Iterator<UploadContext> iterator = pendingUploads.iterator();
            while (iterator.hasNext()) {
                iterator.next().isCanceled = true;
            }
            return;
        }
    }

    private static void enqueueRequest(UploadContext uploadContext, Runnable runnable) {
        synchronized (VideoUploader.class) {
            uploadContext.workItem = uploadQueue.addActiveWorkItem(runnable);
            return;
        }
    }

    private static void enqueueUploadChunk(UploadContext uploadContext, String string, String string2, int n) {
        VideoUploader.enqueueRequest(uploadContext, new TransferChunkWorkItem(uploadContext, string, string2, n));
    }

    private static void enqueueUploadFinish(UploadContext uploadContext, int n) {
        VideoUploader.enqueueRequest(uploadContext, new FinishUploadWorkItem(uploadContext, n));
    }

    private static void enqueueUploadStart(UploadContext uploadContext, int n) {
        VideoUploader.enqueueRequest(uploadContext, new StartUploadWorkItem(uploadContext, n));
    }

    private static byte[] getChunk(UploadContext uploadContext, String object, String string) throws IOException {
        int n;
        if (!Utility.areObjectsEqual(object, uploadContext.chunkStart)) {
            VideoUploader.logError(null, "Error reading video chunk. Expected chunk '%s'. Requested chunk '%s'.", uploadContext.chunkStart, object);
            return null;
        }
        long l = Long.parseLong((String)object);
        int n2 = (int)(Long.parseLong(string) - l);
        object = new ByteArrayOutputStream();
        byte[] arrby = new byte[Math.min(8192, n2)];
        while ((n = uploadContext.videoStream.read(arrby)) != -1) {
            object.write(arrby, 0, n);
            int n3 = n2 - n;
            if (n3 == 0) break;
            n2 = n3;
            if (n3 >= 0) continue;
            VideoUploader.logError(null, "Error reading video chunk. Expected buffer length - '%d'. Actual - '%d'.", n3 + n, n);
            return null;
        }
        uploadContext.chunkStart = string;
        return object.toByteArray();
    }

    private static Handler getHandler() {
        synchronized (VideoUploader.class) {
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            Handler handler = VideoUploader.handler;
            return handler;
        }
    }

    private static void issueResponse(UploadContext uploadContext, FacebookException facebookException, String string) {
        VideoUploader.removePendingUpload(uploadContext);
        Utility.closeQuietly(uploadContext.videoStream);
        if (uploadContext.callback != null) {
            if (facebookException != null) {
                ShareInternalUtility.invokeOnErrorCallback(uploadContext.callback, facebookException);
                return;
            }
            if (uploadContext.isCanceled) {
                ShareInternalUtility.invokeOnCancelCallback(uploadContext.callback);
                return;
            }
            ShareInternalUtility.invokeOnSuccessCallback(uploadContext.callback, string);
        }
    }

    private static /* varargs */ void logError(Exception exception, String string, Object ... arrobject) {
        Log.e((String)TAG, (String)String.format(Locale.ROOT, string, arrobject), (Throwable)exception);
    }

    private static void registerAccessTokenTracker() {
        accessTokenTracker = new AccessTokenTracker(){

            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                if (accessToken == null) {
                    return;
                }
                if (accessToken2 == null || !Utility.areObjectsEqual(accessToken2.getUserId(), accessToken.getUserId())) {
                    VideoUploader.cancelAllRequests();
                }
            }
        };
    }

    private static void removePendingUpload(UploadContext uploadContext) {
        synchronized (VideoUploader.class) {
            pendingUploads.remove(uploadContext);
            return;
        }
    }

    public static void uploadAsync(ShareVideoContent shareVideoContent, FacebookCallback<Sharer.Result> facebookCallback) throws FileNotFoundException {
        synchronized (VideoUploader.class) {
            VideoUploader.uploadAsync(shareVideoContent, "me", facebookCallback);
            return;
        }
    }

    public static void uploadAsync(ShareVideoContent object, String string, FacebookCallback<Sharer.Result> facebookCallback) throws FileNotFoundException {
        synchronized (VideoUploader.class) {
            if (!initialized) {
                VideoUploader.registerAccessTokenTracker();
                initialized = true;
            }
            Validate.notNull(object, "videoContent");
            Validate.notNull(string, "graphNode");
            ShareVideo shareVideo = object.getVideo();
            Validate.notNull(shareVideo, "videoContent.video");
            Validate.notNull((Object)shareVideo.getLocalUrl(), "videoContent.video.localUrl");
            object = new UploadContext((ShareVideoContent)object, string, facebookCallback);
            ((UploadContext)object).initialize();
            pendingUploads.add((UploadContext)object);
            VideoUploader.enqueueUploadStart((UploadContext)object, 0);
            return;
        }
    }

    private static class FinishUploadWorkItem
    extends UploadWorkItemBase {
        static final Set<Integer> transientErrorCodes = new HashSet<Integer>(){
            {
                this.add(1363011);
            }
        };

        public FinishUploadWorkItem(UploadContext uploadContext, int n) {
            super(uploadContext, n);
        }

        @Override
        protected void enqueueRetry(int n) {
            VideoUploader.enqueueUploadFinish(this.uploadContext, n);
        }

        @Override
        public Bundle getParameters() {
            Bundle bundle = new Bundle();
            if (this.uploadContext.params != null) {
                bundle.putAll(this.uploadContext.params);
            }
            bundle.putString(VideoUploader.PARAM_UPLOAD_PHASE, VideoUploader.PARAM_VALUE_UPLOAD_FINISH_PHASE);
            bundle.putString(VideoUploader.PARAM_SESSION_ID, this.uploadContext.sessionId);
            Utility.putNonEmptyString(bundle, VideoUploader.PARAM_TITLE, this.uploadContext.title);
            Utility.putNonEmptyString(bundle, VideoUploader.PARAM_DESCRIPTION, this.uploadContext.description);
            Utility.putNonEmptyString(bundle, VideoUploader.PARAM_REF, this.uploadContext.ref);
            return bundle;
        }

        @Override
        protected Set<Integer> getTransientErrorCodes() {
            return transientErrorCodes;
        }

        @Override
        protected void handleError(FacebookException facebookException) {
            VideoUploader.logError(facebookException, "Video '%s' failed to finish uploading", new Object[]{this.uploadContext.videoId});
            this.endUploadWithFailure(facebookException);
        }

        @Override
        protected void handleSuccess(JSONObject jSONObject) throws JSONException {
            if (jSONObject.getBoolean("success")) {
                this.issueResponseOnMainThread(null, this.uploadContext.videoId);
                return;
            }
            this.handleError(new FacebookException(VideoUploader.ERROR_BAD_SERVER_RESPONSE));
        }

    }

    private static class StartUploadWorkItem
    extends UploadWorkItemBase {
        static final Set<Integer> transientErrorCodes = new HashSet<Integer>(){
            {
                this.add(6000);
            }
        };

        public StartUploadWorkItem(UploadContext uploadContext, int n) {
            super(uploadContext, n);
        }

        @Override
        protected void enqueueRetry(int n) {
            VideoUploader.enqueueUploadStart(this.uploadContext, n);
        }

        @Override
        public Bundle getParameters() {
            Bundle bundle = new Bundle();
            bundle.putString(VideoUploader.PARAM_UPLOAD_PHASE, VideoUploader.PARAM_VALUE_UPLOAD_START_PHASE);
            bundle.putLong(VideoUploader.PARAM_FILE_SIZE, this.uploadContext.videoSize);
            return bundle;
        }

        @Override
        protected Set<Integer> getTransientErrorCodes() {
            return transientErrorCodes;
        }

        @Override
        protected void handleError(FacebookException facebookException) {
            VideoUploader.logError(facebookException, "Error starting video upload", new Object[0]);
            this.endUploadWithFailure(facebookException);
        }

        @Override
        protected void handleSuccess(JSONObject object) throws JSONException {
            this.uploadContext.sessionId = object.getString(VideoUploader.PARAM_SESSION_ID);
            this.uploadContext.videoId = object.getString(VideoUploader.PARAM_VIDEO_ID);
            String string = object.getString(VideoUploader.PARAM_START_OFFSET);
            object = object.getString(VideoUploader.PARAM_END_OFFSET);
            VideoUploader.enqueueUploadChunk(this.uploadContext, string, (String)object, 0);
        }

    }

    private static class TransferChunkWorkItem
    extends UploadWorkItemBase {
        static final Set<Integer> transientErrorCodes = new HashSet<Integer>(){
            {
                this.add(1363019);
                this.add(1363021);
                this.add(1363030);
                this.add(1363033);
                this.add(1363041);
            }
        };
        private String chunkEnd;
        private String chunkStart;

        public TransferChunkWorkItem(UploadContext uploadContext, String string, String string2, int n) {
            super(uploadContext, n);
            this.chunkStart = string;
            this.chunkEnd = string2;
        }

        @Override
        protected void enqueueRetry(int n) {
            VideoUploader.enqueueUploadChunk(this.uploadContext, this.chunkStart, this.chunkEnd, n);
        }

        @Override
        public Bundle getParameters() throws IOException {
            Bundle bundle = new Bundle();
            bundle.putString(VideoUploader.PARAM_UPLOAD_PHASE, VideoUploader.PARAM_VALUE_UPLOAD_TRANSFER_PHASE);
            bundle.putString(VideoUploader.PARAM_SESSION_ID, this.uploadContext.sessionId);
            bundle.putString(VideoUploader.PARAM_START_OFFSET, this.chunkStart);
            byte[] arrby = VideoUploader.getChunk(this.uploadContext, this.chunkStart, this.chunkEnd);
            if (arrby != null) {
                bundle.putByteArray(VideoUploader.PARAM_VIDEO_FILE_CHUNK, arrby);
                return bundle;
            }
            throw new FacebookException("Error reading video");
        }

        @Override
        protected Set<Integer> getTransientErrorCodes() {
            return transientErrorCodes;
        }

        @Override
        protected void handleError(FacebookException facebookException) {
            VideoUploader.logError(facebookException, "Error uploading video '%s'", new Object[]{this.uploadContext.videoId});
            this.endUploadWithFailure(facebookException);
        }

        @Override
        protected void handleSuccess(JSONObject object) throws JSONException {
            String string = object.getString(VideoUploader.PARAM_START_OFFSET);
            if (Utility.areObjectsEqual(string, object = object.getString(VideoUploader.PARAM_END_OFFSET))) {
                VideoUploader.enqueueUploadFinish(this.uploadContext, 0);
                return;
            }
            VideoUploader.enqueueUploadChunk(this.uploadContext, string, (String)object, 0);
        }

    }

    private static class UploadContext {
        public final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        public final FacebookCallback<Sharer.Result> callback;
        public String chunkStart = "0";
        public final String description;
        public final String graphNode;
        public boolean isCanceled;
        public Bundle params;
        public final String ref;
        public String sessionId;
        public final String title;
        public String videoId;
        public long videoSize;
        public InputStream videoStream;
        public final Uri videoUri;
        public WorkQueue.WorkItem workItem;

        private UploadContext(ShareVideoContent shareVideoContent, String string, FacebookCallback<Sharer.Result> facebookCallback) {
            this.videoUri = shareVideoContent.getVideo().getLocalUrl();
            this.title = shareVideoContent.getContentTitle();
            this.description = shareVideoContent.getContentDescription();
            this.ref = shareVideoContent.getRef();
            this.graphNode = string;
            this.callback = facebookCallback;
            this.params = shareVideoContent.getVideo().getParameters();
            if (!Utility.isNullOrEmpty(shareVideoContent.getPeopleIds())) {
                this.params.putString("tags", TextUtils.join((CharSequence)", ", shareVideoContent.getPeopleIds()));
            }
            if (!Utility.isNullOrEmpty(shareVideoContent.getPlaceId())) {
                this.params.putString("place", shareVideoContent.getPlaceId());
            }
            if (!Utility.isNullOrEmpty(shareVideoContent.getRef())) {
                this.params.putString(VideoUploader.PARAM_REF, shareVideoContent.getRef());
            }
        }

        private void initialize() throws FileNotFoundException {
            try {
                if (Utility.isFileUri(this.videoUri)) {
                    ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open((File)new File(this.videoUri.getPath()), (int)268435456);
                    this.videoSize = parcelFileDescriptor.getStatSize();
                    this.videoStream = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
                    return;
                }
                if (Utility.isContentUri(this.videoUri)) {
                    this.videoSize = Utility.getContentSize(this.videoUri);
                    this.videoStream = FacebookSdk.getApplicationContext().getContentResolver().openInputStream(this.videoUri);
                    return;
                }
                throw new FacebookException("Uri must be a content:// or file:// uri");
            }
            catch (FileNotFoundException fileNotFoundException) {
                Utility.closeQuietly(this.videoStream);
                throw fileNotFoundException;
            }
        }
    }

    private static abstract class UploadWorkItemBase
    implements Runnable {
        protected int completedRetries;
        protected UploadContext uploadContext;

        protected UploadWorkItemBase(UploadContext uploadContext, int n) {
            this.uploadContext = uploadContext;
            this.completedRetries = n;
        }

        private boolean attemptRetry(int n) {
            if (this.completedRetries < 2 && this.getTransientErrorCodes().contains(n)) {
                n = (int)Math.pow(3.0, this.completedRetries);
                VideoUploader.getHandler().postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        UploadWorkItemBase.this.enqueueRetry(UploadWorkItemBase.this.completedRetries + 1);
                    }
                }, (long)(5000 * n));
                return true;
            }
            return false;
        }

        protected void endUploadWithFailure(FacebookException facebookException) {
            this.issueResponseOnMainThread(facebookException, null);
        }

        protected abstract void enqueueRetry(int var1);

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        protected void executeGraphRequestSynchronously(Bundle object) {
            object = new GraphRequest(this.uploadContext.accessToken, String.format(Locale.ROOT, "%s/videos", this.uploadContext.graphNode), (Bundle)object, HttpMethod.POST, null).executeAndWait();
            if (object != null) {
                FacebookRequestError facebookRequestError = object.getError();
                JSONObject jSONObject = object.getJSONObject();
                if (facebookRequestError != null) {
                    if (this.attemptRetry(facebookRequestError.getSubErrorCode())) return;
                    this.handleError(new FacebookGraphResponseException((GraphResponse)object, "Video upload failed"));
                    return;
                }
                if (jSONObject != null) {
                    try {
                        this.handleSuccess(jSONObject);
                        return;
                    }
                    catch (JSONException jSONException) {
                        this.endUploadWithFailure(new FacebookException("Unexpected error in server response", (Throwable)jSONException));
                        return;
                    }
                }
                this.handleError(new FacebookException("Unexpected error in server response"));
                return;
            }
            this.handleError(new FacebookException("Unexpected error in server response"));
        }

        protected abstract Bundle getParameters() throws Exception;

        protected abstract Set<Integer> getTransientErrorCodes();

        protected abstract void handleError(FacebookException var1);

        protected abstract void handleSuccess(JSONObject var1) throws JSONException;

        protected void issueResponseOnMainThread(final FacebookException facebookException, final String string) {
            VideoUploader.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    VideoUploader.issueResponse(UploadWorkItemBase.this.uploadContext, facebookException, string);
                }
            });
        }

        @Override
        public void run() {
            if (!this.uploadContext.isCanceled) {
                try {
                    this.executeGraphRequestSynchronously(this.getParameters());
                    return;
                }
                catch (Exception exception) {
                    this.endUploadWithFailure(new FacebookException("Video upload failed", exception));
                    return;
                }
                catch (FacebookException facebookException) {
                    this.endUploadWithFailure(facebookException);
                    return;
                }
            }
            this.endUploadWithFailure(null);
        }

    }

}
