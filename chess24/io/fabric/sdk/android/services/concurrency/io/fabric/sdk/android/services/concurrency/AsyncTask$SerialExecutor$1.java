/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

class AsyncTask.SerialExecutor
implements Runnable {
    final /* synthetic */ Runnable val$r;

    AsyncTask.SerialExecutor(Runnable runnable) {
        this.val$r = runnable;
    }

    @Override
    public void run() {
        try {
            this.val$r.run();
            return;
        }
        finally {
            SerialExecutor.this.scheduleNext();
        }
    }
}
