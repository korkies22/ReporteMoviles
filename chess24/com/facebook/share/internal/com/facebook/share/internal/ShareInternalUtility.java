/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.util.Pair
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Pair;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.NativeAppCallAttachmentStore;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.share.Sharer;
import com.facebook.share.internal.OpenGraphJSONUtility;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.model.CameraEffectTextures;
import com.facebook.share.model.ShareCameraEffectContent;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.LikeView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ShareInternalUtility {
    public static final String MY_PHOTOS = "me/photos";
    private static final String MY_STAGING_RESOURCES = "me/staging_resources";
    private static final String STAGING_PARAM = "file";

    private static AppCall getAppCallFromActivityResult(int n, int n2, Intent object) {
        if ((object = NativeProtocol.getCallIdFromIntent(object)) == null) {
            return null;
        }
        return AppCall.finishPendingCall((UUID)object, n);
    }

    private static NativeAppCallAttachmentStore.Attachment getAttachment(UUID uUID, Uri uri, Bitmap bitmap) {
        if (bitmap != null) {
            return NativeAppCallAttachmentStore.createAttachment(uUID, bitmap);
        }
        if (uri != null) {
            return NativeAppCallAttachmentStore.createAttachment(uUID, uri);
        }
        return null;
    }

    private static NativeAppCallAttachmentStore.Attachment getAttachment(UUID uUID, ShareMedia shareMedia) {
        boolean bl = shareMedia instanceof SharePhoto;
        Bitmap bitmap = null;
        if (bl) {
            shareMedia = (SharePhoto)shareMedia;
            bitmap = shareMedia.getBitmap();
            shareMedia = shareMedia.getImageUrl();
        } else {
            shareMedia = shareMedia instanceof ShareVideo ? ((ShareVideo)shareMedia).getLocalUrl() : null;
        }
        return ShareInternalUtility.getAttachment(uUID, (Uri)shareMedia, bitmap);
    }

    public static Pair<String, String> getFieldNameAndNamespaceFromFullName(String string) {
        int n;
        String string2;
        int n2;
        int n3 = string.indexOf(58);
        if (n3 != -1 && (n = string.length()) > (n2 = n3 + 1)) {
            string2 = string.substring(0, n3);
            String string3 = string.substring(n2);
            string = string2;
            string2 = string3;
        } else {
            Object var5_6 = null;
            string2 = string;
            string = var5_6;
        }
        return new Pair((Object)string, (Object)string2);
    }

    public static List<Bundle> getMediaInfos(ShareMediaContent object, UUID object2) {
        List<ShareMedia> list;
        if (object != null && (list = object.getMedia()) != null) {
            object = new ArrayList();
            object2 = Utility.map(list, new Utility.Mapper<ShareMedia, Bundle>((UUID)object2, (List)object){
                final /* synthetic */ UUID val$appCallId;
                final /* synthetic */ List val$attachments;
                {
                    this.val$appCallId = uUID;
                    this.val$attachments = list;
                }

                @Override
                public Bundle apply(ShareMedia shareMedia) {
                    NativeAppCallAttachmentStore.Attachment attachment = ShareInternalUtility.getAttachment(this.val$appCallId, shareMedia);
                    this.val$attachments.add(attachment);
                    Bundle bundle = new Bundle();
                    bundle.putString("type", shareMedia.getMediaType().name());
                    bundle.putString("uri", attachment.getAttachmentUrl());
                    return bundle;
                }
            });
            NativeAppCallAttachmentStore.addAttachments((Collection<NativeAppCallAttachmentStore.Attachment>)object);
            return object2;
        }
        return null;
    }

    @Nullable
    public static LikeView.ObjectType getMostSpecificObjectType(LikeView.ObjectType objectType, LikeView.ObjectType objectType2) {
        if (objectType == objectType2) {
            return objectType;
        }
        if (objectType == LikeView.ObjectType.UNKNOWN) {
            return objectType2;
        }
        if (objectType2 == LikeView.ObjectType.UNKNOWN) {
            return objectType;
        }
        return null;
    }

    public static String getNativeDialogCompletionGesture(Bundle bundle) {
        if (bundle.containsKey("completionGesture")) {
            return bundle.getString("completionGesture");
        }
        return bundle.getString("com.facebook.platform.extra.COMPLETION_GESTURE");
    }

    public static List<String> getPhotoUrls(SharePhotoContent list, UUID object) {
        if (list != null && (list = list.getPhotos()) != null) {
            list = Utility.map(list, new Utility.Mapper<SharePhoto, NativeAppCallAttachmentStore.Attachment>((UUID)object){
                final /* synthetic */ UUID val$appCallId;
                {
                    this.val$appCallId = uUID;
                }

                @Override
                public NativeAppCallAttachmentStore.Attachment apply(SharePhoto sharePhoto) {
                    return ShareInternalUtility.getAttachment(this.val$appCallId, sharePhoto);
                }
            });
            object = Utility.map(list, new Utility.Mapper<NativeAppCallAttachmentStore.Attachment, String>(){

                @Override
                public String apply(NativeAppCallAttachmentStore.Attachment attachment) {
                    return attachment.getAttachmentUrl();
                }
            });
            NativeAppCallAttachmentStore.addAttachments(list);
            return object;
        }
        return null;
    }

    public static String getShareDialogPostId(Bundle bundle) {
        if (bundle.containsKey("postId")) {
            return bundle.getString("postId");
        }
        if (bundle.containsKey("com.facebook.platform.extra.POST_ID")) {
            return bundle.getString("com.facebook.platform.extra.POST_ID");
        }
        return bundle.getString("post_id");
    }

    public static ResultProcessor getShareResultProcessor(final FacebookCallback<Sharer.Result> facebookCallback) {
        return new ResultProcessor(facebookCallback){

            @Override
            public void onCancel(AppCall appCall) {
                ShareInternalUtility.invokeOnCancelCallback(facebookCallback);
            }

            @Override
            public void onError(AppCall appCall, FacebookException facebookException) {
                ShareInternalUtility.invokeOnErrorCallback((FacebookCallback<Sharer.Result>)facebookCallback, facebookException);
            }

            @Override
            public void onSuccess(AppCall object, Bundle bundle) {
                if (bundle != null) {
                    object = ShareInternalUtility.getNativeDialogCompletionGesture(bundle);
                    if (object != null && !"post".equalsIgnoreCase((String)object)) {
                        if ("cancel".equalsIgnoreCase((String)object)) {
                            ShareInternalUtility.invokeOnCancelCallback(facebookCallback);
                            return;
                        }
                        ShareInternalUtility.invokeOnErrorCallback((FacebookCallback<Sharer.Result>)facebookCallback, new FacebookException("UnknownError"));
                        return;
                    }
                    object = ShareInternalUtility.getShareDialogPostId(bundle);
                    ShareInternalUtility.invokeOnSuccessCallback(facebookCallback, (String)object);
                }
            }
        };
    }

    public static Bundle getTextureUrlBundle(ShareCameraEffectContent shareModel, UUID uUID) {
        if (shareModel != null && (shareModel = shareModel.getTextures()) != null) {
            Bundle bundle = new Bundle();
            ArrayList<NativeAppCallAttachmentStore.Attachment> arrayList = new ArrayList<NativeAppCallAttachmentStore.Attachment>();
            for (String string : shareModel.keySet()) {
                NativeAppCallAttachmentStore.Attachment attachment = ShareInternalUtility.getAttachment(uUID, shareModel.getTextureUri(string), shareModel.getTextureBitmap(string));
                arrayList.add(attachment);
                bundle.putString(string, attachment.getAttachmentUrl());
            }
            NativeAppCallAttachmentStore.addAttachments(arrayList);
            return bundle;
        }
        return null;
    }

    public static String getVideoUrl(ShareVideoContent object, UUID serializable) {
        if (object != null && object.getVideo() != null) {
            object = NativeAppCallAttachmentStore.createAttachment((UUID)serializable, object.getVideo().getLocalUrl());
            serializable = new ArrayList(1);
            serializable.add(object);
            NativeAppCallAttachmentStore.addAttachments((Collection<NativeAppCallAttachmentStore.Attachment>)((Object)serializable));
            return object.getAttachmentUrl();
        }
        return null;
    }

    public static boolean handleActivityResult(int n, int n2, Intent intent, ResultProcessor resultProcessor) {
        AppCall appCall = ShareInternalUtility.getAppCallFromActivityResult(n, n2, intent);
        if (appCall == null) {
            return false;
        }
        NativeAppCallAttachmentStore.cleanupAttachmentsForCall(appCall.getCallId());
        if (resultProcessor == null) {
            return true;
        }
        FacebookException facebookException = NativeProtocol.getExceptionFromErrorData(NativeProtocol.getErrorDataFromResultIntent(intent));
        if (facebookException != null) {
            if (facebookException instanceof FacebookOperationCanceledException) {
                resultProcessor.onCancel(appCall);
                return true;
            }
            resultProcessor.onError(appCall, facebookException);
            return true;
        }
        resultProcessor.onSuccess(appCall, NativeProtocol.getSuccessResultsFromIntent(intent));
        return true;
    }

    public static void invokeCallbackWithError(FacebookCallback<Sharer.Result> facebookCallback, String string) {
        ShareInternalUtility.invokeOnErrorCallback(facebookCallback, string);
    }

    public static void invokeCallbackWithException(FacebookCallback<Sharer.Result> facebookCallback, Exception exception) {
        if (exception instanceof FacebookException) {
            ShareInternalUtility.invokeOnErrorCallback(facebookCallback, (FacebookException)exception);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error preparing share content: ");
        stringBuilder.append(exception.getLocalizedMessage());
        ShareInternalUtility.invokeCallbackWithError(facebookCallback, stringBuilder.toString());
    }

    public static void invokeCallbackWithResults(FacebookCallback<Sharer.Result> facebookCallback, String object, GraphResponse graphResponse) {
        Object object2 = graphResponse.getError();
        if (object2 != null) {
            object = object2 = object2.getErrorMessage();
            if (Utility.isNullOrEmpty((String)object2)) {
                object = "Unexpected error sharing.";
            }
            ShareInternalUtility.invokeOnErrorCallback(facebookCallback, graphResponse, (String)object);
            return;
        }
        ShareInternalUtility.invokeOnSuccessCallback(facebookCallback, (String)object);
    }

    static void invokeOnCancelCallback(FacebookCallback<Sharer.Result> facebookCallback) {
        ShareInternalUtility.logShareResult("cancelled", null);
        if (facebookCallback != null) {
            facebookCallback.onCancel();
        }
    }

    static void invokeOnErrorCallback(FacebookCallback<Sharer.Result> facebookCallback, FacebookException facebookException) {
        ShareInternalUtility.logShareResult("error", facebookException.getMessage());
        if (facebookCallback != null) {
            facebookCallback.onError(facebookException);
        }
    }

    static void invokeOnErrorCallback(FacebookCallback<Sharer.Result> facebookCallback, GraphResponse graphResponse, String string) {
        ShareInternalUtility.logShareResult("error", string);
        if (facebookCallback != null) {
            facebookCallback.onError(new FacebookGraphResponseException(graphResponse, string));
        }
    }

    static void invokeOnErrorCallback(FacebookCallback<Sharer.Result> facebookCallback, String string) {
        ShareInternalUtility.logShareResult("error", string);
        if (facebookCallback != null) {
            facebookCallback.onError(new FacebookException(string));
        }
    }

    static void invokeOnSuccessCallback(FacebookCallback<Sharer.Result> facebookCallback, String string) {
        ShareInternalUtility.logShareResult("succeeded", null);
        if (facebookCallback != null) {
            facebookCallback.onSuccess(new Sharer.Result(string));
        }
    }

    private static void logShareResult(String string, String string2) {
        AppEventsLogger appEventsLogger = AppEventsLogger.newLogger(FacebookSdk.getApplicationContext());
        Bundle bundle = new Bundle();
        bundle.putString("fb_share_dialog_outcome", string);
        if (string2 != null) {
            bundle.putString("error_message", string2);
        }
        appEventsLogger.logSdkEvent("fb_share_dialog_result", null, bundle);
    }

    public static GraphRequest newUploadStagingResourceWithImageRequest(AccessToken accessToken, Bitmap bitmap, GraphRequest.Callback callback) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(STAGING_PARAM, (Parcelable)bitmap);
        return new GraphRequest(accessToken, MY_STAGING_RESOURCES, bundle, HttpMethod.POST, callback);
    }

    public static GraphRequest newUploadStagingResourceWithImageRequest(AccessToken accessToken, Uri object, GraphRequest.Callback callback) throws FileNotFoundException {
        if (Utility.isFileUri(object)) {
            return ShareInternalUtility.newUploadStagingResourceWithImageRequest(accessToken, new File(object.getPath()), callback);
        }
        if (!Utility.isContentUri(object)) {
            throw new FacebookException("The image Uri must be either a file:// or content:// Uri");
        }
        object = new GraphRequest.ParcelableResourceWithMimeType<Uri>((Uri)object, "image/png");
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(STAGING_PARAM, (Parcelable)object);
        return new GraphRequest(accessToken, MY_STAGING_RESOURCES, bundle, HttpMethod.POST, callback);
    }

    public static GraphRequest newUploadStagingResourceWithImageRequest(AccessToken accessToken, File object, GraphRequest.Callback callback) throws FileNotFoundException {
        object = new GraphRequest.ParcelableResourceWithMimeType<ParcelFileDescriptor>(ParcelFileDescriptor.open((File)object, (int)268435456), "image/png");
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(STAGING_PARAM, (Parcelable)object);
        return new GraphRequest(accessToken, MY_STAGING_RESOURCES, bundle, HttpMethod.POST, callback);
    }

    public static void registerSharerCallback(final int n, CallbackManager callbackManager, final FacebookCallback<Sharer.Result> facebookCallback) {
        if (!(callbackManager instanceof CallbackManagerImpl)) {
            throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
        }
        ((CallbackManagerImpl)callbackManager).registerCallback(n, new CallbackManagerImpl.Callback(){

            @Override
            public boolean onActivityResult(int n2, Intent intent) {
                return ShareInternalUtility.handleActivityResult(n, n2, intent, ShareInternalUtility.getShareResultProcessor(facebookCallback));
            }
        });
    }

    public static void registerStaticShareCallback(final int n) {
        CallbackManagerImpl.registerStaticCallback(n, new CallbackManagerImpl.Callback(){

            @Override
            public boolean onActivityResult(int n2, Intent intent) {
                return ShareInternalUtility.handleActivityResult(n, n2, intent, ShareInternalUtility.getShareResultProcessor(null));
            }
        });
    }

    public static JSONArray removeNamespacesFromOGJsonArray(JSONArray jSONArray, boolean bl) throws JSONException {
        JSONArray jSONArray2 = new JSONArray();
        for (int i = 0; i < jSONArray.length(); ++i) {
            Object object;
            Object object2 = jSONArray.get(i);
            if (object2 instanceof JSONArray) {
                object = ShareInternalUtility.removeNamespacesFromOGJsonArray((JSONArray)object2, bl);
            } else {
                object = object2;
                if (object2 instanceof JSONObject) {
                    object = ShareInternalUtility.removeNamespacesFromOGJsonObject((JSONObject)object2, bl);
                }
            }
            jSONArray2.put(object);
        }
        return jSONArray2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static JSONObject removeNamespacesFromOGJsonObject(JSONObject jSONObject, boolean bl) {
        int n;
        JSONArray jSONArray;
        JSONObject jSONObject2;
        JSONObject jSONObject3;
        if (jSONObject == null) {
            return null;
        }
        try {
            jSONObject3 = new JSONObject();
            jSONObject2 = new JSONObject();
            jSONArray = jSONObject.names();
            n = 0;
        }
        catch (JSONException jSONException) {
            throw new FacebookException("Failed to create json object from share content");
        }
        do {
            Object object;
            if (n >= jSONArray.length()) {
                if (jSONObject2.length() <= 0) return jSONObject3;
                jSONObject3.put("data", (Object)jSONObject2);
                return jSONObject3;
            }
            String string = jSONArray.getString(n);
            Object object2 = jSONObject.get(string);
            if (object2 instanceof JSONObject) {
                object = ShareInternalUtility.removeNamespacesFromOGJsonObject((JSONObject)object2, true);
            } else {
                object = object2;
                if (object2 instanceof JSONArray) {
                    object = ShareInternalUtility.removeNamespacesFromOGJsonArray((JSONArray)object2, true);
                }
            }
            Pair<String, String> pair = ShareInternalUtility.getFieldNameAndNamespaceFromFullName(string);
            object2 = (String)pair.first;
            String string2 = (String)pair.second;
            if (bl) {
                if (object2 != null && object2.equals("fbsdk")) {
                    jSONObject3.put(string, object);
                } else if (object2 != null && !object2.equals("og")) {
                    jSONObject2.put(string2, object);
                } else {
                    jSONObject3.put(string2, object);
                }
            } else if (object2 != null && object2.equals("fb")) {
                jSONObject3.put(string, object);
            } else {
                jSONObject3.put(string2, object);
            }
            ++n;
        } while (true);
    }

    public static JSONObject toJSONObjectForCall(UUID set, ShareOpenGraphContent object) throws JSONException {
        ShareOpenGraphAction shareOpenGraphAction = object.getAction();
        ArrayList<NativeAppCallAttachmentStore.Attachment> arrayList = new ArrayList<NativeAppCallAttachmentStore.Attachment>();
        shareOpenGraphAction = OpenGraphJSONUtility.toJSONObject(shareOpenGraphAction, new OpenGraphJSONUtility.PhotoJSONProcessor((UUID)((Object)set), arrayList){
            final /* synthetic */ ArrayList val$attachments;
            final /* synthetic */ UUID val$callId;
            {
                this.val$callId = uUID;
                this.val$attachments = arrayList;
            }

            @Override
            public JSONObject toJSONObject(SharePhoto sharePhoto) {
                NativeAppCallAttachmentStore.Attachment attachment = ShareInternalUtility.getAttachment(this.val$callId, sharePhoto);
                if (attachment == null) {
                    return null;
                }
                this.val$attachments.add(attachment);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("url", (Object)attachment.getAttachmentUrl());
                    if (sharePhoto.getUserGenerated()) {
                        jSONObject.put("user_generated", true);
                    }
                    return jSONObject;
                }
                catch (JSONException jSONException) {
                    throw new FacebookException("Unable to attach images", (Throwable)jSONException);
                }
            }
        });
        NativeAppCallAttachmentStore.addAttachments(arrayList);
        if (object.getPlaceId() != null && Utility.isNullOrEmpty(shareOpenGraphAction.optString("place"))) {
            shareOpenGraphAction.put("place", (Object)object.getPlaceId());
        }
        if (object.getPeopleIds() != null) {
            set = shareOpenGraphAction.optJSONArray("tags");
            set = set == null ? new HashSet<String>() : Utility.jsonArrayToSet((JSONArray)set);
            object = object.getPeopleIds().iterator();
            while (object.hasNext()) {
                set.add((String)object.next());
            }
            shareOpenGraphAction.put("tags", (Object)new JSONArray(set));
        }
        return shareOpenGraphAction;
    }

    public static JSONObject toJSONObjectForWeb(ShareOpenGraphContent shareOpenGraphContent) throws JSONException {
        return OpenGraphJSONUtility.toJSONObject(shareOpenGraphContent.getAction(), new OpenGraphJSONUtility.PhotoJSONProcessor(){

            @Override
            public JSONObject toJSONObject(SharePhoto sharePhoto) {
                if (!Utility.isWebUri((Uri)(sharePhoto = sharePhoto.getImageUrl()))) {
                    throw new FacebookException("Only web images may be used in OG objects shared via the web dialog");
                }
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("url", (Object)sharePhoto.toString());
                    return jSONObject;
                }
                catch (JSONException jSONException) {
                    throw new FacebookException("Unable to attach images", (Throwable)jSONException);
                }
            }
        });
    }

}
