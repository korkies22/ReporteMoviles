/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.Looper
 */
package com.facebook.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.ImageResponse;
import com.facebook.internal.ImageResponseCache;
import com.facebook.internal.UrlRedirectCache;
import com.facebook.internal.Utility;
import com.facebook.internal.WorkQueue;
import java.io.Closeable;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageDownloader {
    private static final int CACHE_READ_QUEUE_MAX_CONCURRENT = 2;
    private static final int DOWNLOAD_QUEUE_MAX_CONCURRENT = 8;
    private static WorkQueue cacheReadQueue;
    private static WorkQueue downloadQueue;
    private static Handler handler;
    private static final Map<RequestKey, DownloaderContext> pendingRequests;

    static {
        downloadQueue = new WorkQueue(8);
        cacheReadQueue = new WorkQueue(2);
        pendingRequests = new HashMap<RequestKey, DownloaderContext>();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean cancelRequest(ImageRequest object) {
        RequestKey requestKey = new RequestKey(object.getImageUri(), object.getCallerTag());
        object = pendingRequests;
        synchronized (object) {
            DownloaderContext downloaderContext = pendingRequests.get(requestKey);
            boolean bl = true;
            if (downloaderContext == null) return false;
            if (downloaderContext.workItem.cancel()) {
                pendingRequests.remove(requestKey);
            } else {
                downloaderContext.isCancelled = true;
            }
            return bl;
        }
    }

    public static void clearCache(Context context) {
        ImageResponseCache.clearCache(context);
        UrlRedirectCache.clearCache();
    }

    /*
     * Exception decompiling
     */
    private static void download(RequestKey var0, Context var1_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:367)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:374)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void downloadAsync(ImageRequest imageRequest) {
        if (imageRequest == null) {
            return;
        }
        RequestKey requestKey = new RequestKey(imageRequest.getImageUri(), imageRequest.getCallerTag());
        Map<RequestKey, DownloaderContext> map = pendingRequests;
        synchronized (map) {
            DownloaderContext downloaderContext = pendingRequests.get(requestKey);
            if (downloaderContext != null) {
                downloaderContext.request = imageRequest;
                downloaderContext.isCancelled = false;
                downloaderContext.workItem.moveToFront();
            } else {
                ImageDownloader.enqueueCacheRead(imageRequest, requestKey, imageRequest.isCachedRedirectAllowed());
            }
            return;
        }
    }

    private static void enqueueCacheRead(ImageRequest imageRequest, RequestKey requestKey, boolean bl) {
        ImageDownloader.enqueueRequest(imageRequest, requestKey, cacheReadQueue, new CacheReadWorkItem(imageRequest.getContext(), requestKey, bl));
    }

    private static void enqueueDownload(ImageRequest imageRequest, RequestKey requestKey) {
        ImageDownloader.enqueueRequest(imageRequest, requestKey, downloadQueue, new DownloadImageWorkItem(imageRequest.getContext(), requestKey));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void enqueueRequest(ImageRequest imageRequest, RequestKey requestKey, WorkQueue workQueue, Runnable runnable) {
        Map<RequestKey, DownloaderContext> map = pendingRequests;
        synchronized (map) {
            DownloaderContext downloaderContext = new DownloaderContext();
            downloaderContext.request = imageRequest;
            pendingRequests.put(requestKey, downloaderContext);
            downloaderContext.workItem = workQueue.addActiveWorkItem(runnable);
            return;
        }
    }

    private static Handler getHandler() {
        synchronized (ImageDownloader.class) {
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            Handler handler = ImageDownloader.handler;
            return handler;
        }
    }

    private static void issueResponse(RequestKey object, Exception exception, Bitmap bitmap, boolean bl) {
        ImageRequest.Callback callback;
        if ((object = ImageDownloader.removePendingRequest((RequestKey)object)) != null && !object.isCancelled && (callback = (object = object.request).getCallback()) != null) {
            ImageDownloader.getHandler().post(new Runnable((ImageRequest)object, exception, bl, bitmap, callback){
                final /* synthetic */ Bitmap val$bitmap;
                final /* synthetic */ ImageRequest.Callback val$callback;
                final /* synthetic */ Exception val$error;
                final /* synthetic */ boolean val$isCachedRedirect;
                final /* synthetic */ ImageRequest val$request;
                {
                    this.val$request = imageRequest;
                    this.val$error = exception;
                    this.val$isCachedRedirect = bl;
                    this.val$bitmap = bitmap;
                    this.val$callback = callback;
                }

                @Override
                public void run() {
                    ImageResponse imageResponse = new ImageResponse(this.val$request, this.val$error, this.val$isCachedRedirect, this.val$bitmap);
                    this.val$callback.onCompleted(imageResponse);
                }
            });
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void prioritizeRequest(ImageRequest object) {
        Object object2 = new RequestKey(object.getImageUri(), object.getCallerTag());
        object = pendingRequests;
        synchronized (object) {
            object2 = pendingRequests.get(object2);
            if (object2 != null) {
                object2.workItem.moveToFront();
            }
            return;
        }
    }

    private static void readFromCache(RequestKey requestKey, Context object, boolean bl) {
        Object object2;
        boolean bl2 = false;
        if (bl && (object2 = UrlRedirectCache.getRedirectedUri(requestKey.uri)) != null) {
            InputStream inputStream = ImageResponseCache.getCachedImageStream(object2, object);
            bl = bl2;
            object2 = inputStream;
            if (inputStream != null) {
                bl = true;
                object2 = inputStream;
            }
        } else {
            object2 = null;
            bl = bl2;
        }
        if (!bl) {
            object2 = ImageResponseCache.getCachedImageStream(requestKey.uri, object);
        }
        if (object2 != null) {
            object = BitmapFactory.decodeStream((InputStream)object2);
            Utility.closeQuietly((Closeable)object2);
            ImageDownloader.issueResponse(requestKey, null, (Bitmap)object, bl);
            return;
        }
        object = ImageDownloader.removePendingRequest(requestKey);
        if (object != null && !object.isCancelled) {
            ImageDownloader.enqueueDownload(object.request, requestKey);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static DownloaderContext removePendingRequest(RequestKey object) {
        Map<RequestKey, DownloaderContext> map = pendingRequests;
        synchronized (map) {
            return pendingRequests.remove(object);
        }
    }

    private static class CacheReadWorkItem
    implements Runnable {
        private boolean allowCachedRedirects;
        private Context context;
        private RequestKey key;

        CacheReadWorkItem(Context context, RequestKey requestKey, boolean bl) {
            this.context = context;
            this.key = requestKey;
            this.allowCachedRedirects = bl;
        }

        @Override
        public void run() {
            ImageDownloader.readFromCache(this.key, this.context, this.allowCachedRedirects);
        }
    }

    private static class DownloadImageWorkItem
    implements Runnable {
        private Context context;
        private RequestKey key;

        DownloadImageWorkItem(Context context, RequestKey requestKey) {
            this.context = context;
            this.key = requestKey;
        }

        @Override
        public void run() {
            ImageDownloader.download(this.key, this.context);
        }
    }

    private static class DownloaderContext {
        boolean isCancelled;
        ImageRequest request;
        WorkQueue.WorkItem workItem;

        private DownloaderContext() {
        }
    }

    private static class RequestKey {
        private static final int HASH_MULTIPLIER = 37;
        private static final int HASH_SEED = 29;
        Object tag;
        Uri uri;

        RequestKey(Uri uri, Object object) {
            this.uri = uri;
            this.tag = object;
        }

        public boolean equals(Object object) {
            boolean bl;
            boolean bl2 = bl = false;
            if (object != null) {
                bl2 = bl;
                if (object instanceof RequestKey) {
                    object = (RequestKey)object;
                    bl2 = bl;
                    if (object.uri == this.uri) {
                        bl2 = bl;
                        if (object.tag == this.tag) {
                            bl2 = true;
                        }
                    }
                }
            }
            return bl2;
        }

        public int hashCode() {
            return (1073 + this.uri.hashCode()) * 37 + this.tag.hashCode();
        }
    }

}
