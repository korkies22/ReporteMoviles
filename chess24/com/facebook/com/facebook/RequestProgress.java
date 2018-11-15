/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package com.facebook;

import android.os.Handler;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;

class RequestProgress {
    private final Handler callbackHandler;
    private long lastReportedProgress;
    private long maxProgress;
    private long progress;
    private final GraphRequest request;
    private final long threshold;

    RequestProgress(Handler handler, GraphRequest graphRequest) {
        this.request = graphRequest;
        this.callbackHandler = handler;
        this.threshold = FacebookSdk.getOnProgressThreshold();
    }

    void addProgress(long l) {
        this.progress += l;
        if (this.progress >= this.lastReportedProgress + this.threshold || this.progress >= this.maxProgress) {
            this.reportProgress();
        }
    }

    void addToMax(long l) {
        this.maxProgress += l;
    }

    long getMaxProgress() {
        return this.maxProgress;
    }

    long getProgress() {
        return this.progress;
    }

    void reportProgress() {
        if (this.progress > this.lastReportedProgress) {
            GraphRequest.Callback callback = this.request.getCallback();
            if (this.maxProgress > 0L && callback instanceof GraphRequest.OnProgressCallback) {
                long l = this.progress;
                long l2 = this.maxProgress;
                callback = (GraphRequest.OnProgressCallback)callback;
                if (this.callbackHandler == null) {
                    callback.onProgress(l, l2);
                } else {
                    this.callbackHandler.post(new Runnable((GraphRequest.OnProgressCallback)callback, l, l2){
                        final /* synthetic */ GraphRequest.OnProgressCallback val$callbackCopy;
                        final /* synthetic */ long val$currentCopy;
                        final /* synthetic */ long val$maxProgressCopy;
                        {
                            this.val$callbackCopy = onProgressCallback;
                            this.val$currentCopy = l;
                            this.val$maxProgressCopy = l2;
                        }

                        @Override
                        public void run() {
                            this.val$callbackCopy.onProgress(this.val$currentCopy, this.val$maxProgressCopy);
                        }
                    });
                }
                this.lastReportedProgress = this.progress;
            }
        }
    }

}
