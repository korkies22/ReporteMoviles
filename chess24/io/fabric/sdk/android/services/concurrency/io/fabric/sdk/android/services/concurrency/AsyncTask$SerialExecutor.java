/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import java.util.LinkedList;
import java.util.concurrent.Executor;

private static class AsyncTask.SerialExecutor
implements Executor {
    Runnable active;
    final LinkedList<Runnable> tasks = new LinkedList();

    private AsyncTask.SerialExecutor() {
    }

    @Override
    public void execute(final Runnable runnable) {
        synchronized (this) {
            this.tasks.offer(new Runnable(){

                @Override
                public void run() {
                    try {
                        runnable.run();
                        return;
                    }
                    finally {
                        SerialExecutor.this.scheduleNext();
                    }
                }
            });
            if (this.active == null) {
                this.scheduleNext();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void scheduleNext() {
        synchronized (this) {
            Runnable runnable;
            this.active = runnable = this.tasks.poll();
            if (runnable != null) {
                AsyncTask.THREAD_POOL_EXECUTOR.execute(this.active);
            }
            return;
        }
    }

}
