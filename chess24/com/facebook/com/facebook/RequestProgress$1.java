/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.GraphRequest;

class RequestProgress
implements Runnable {
    final /* synthetic */ GraphRequest.OnProgressCallback val$callbackCopy;
    final /* synthetic */ long val$currentCopy;
    final /* synthetic */ long val$maxProgressCopy;

    RequestProgress(GraphRequest.OnProgressCallback onProgressCallback, long l, long l2) {
        this.val$callbackCopy = onProgressCallback;
        this.val$currentCopy = l;
        this.val$maxProgressCopy = l2;
    }

    @Override
    public void run() {
        this.val$callbackCopy.onProgress(this.val$currentCopy, this.val$maxProgressCopy);
    }
}
