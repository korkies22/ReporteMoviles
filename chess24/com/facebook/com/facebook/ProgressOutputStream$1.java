/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.GraphRequestBatch;

class ProgressOutputStream
implements Runnable {
    final /* synthetic */ GraphRequestBatch.OnProgressCallback val$progressCallback;

    ProgressOutputStream(GraphRequestBatch.OnProgressCallback onProgressCallback) {
        this.val$progressCallback = onProgressCallback;
    }

    @Override
    public void run() {
        this.val$progressCallback.onBatchProgress(ProgressOutputStream.this.requests, ProgressOutputStream.this.batchProgress, ProgressOutputStream.this.maxProgress);
    }
}
