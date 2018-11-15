/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Process
 */
package io.fabric.sdk.android.services.concurrency;

import android.os.Process;
import io.fabric.sdk.android.services.concurrency.AsyncTask;
import java.util.concurrent.atomic.AtomicBoolean;

class AsyncTask
extends AsyncTask.WorkerRunnable<Params, Result> {
    AsyncTask() {
        super(null);
    }

    @Override
    public Result call() throws Exception {
        AsyncTask.this.taskInvoked.set(true);
        Process.setThreadPriority((int)10);
        return (Result)AsyncTask.this.postResult(AsyncTask.this.doInBackground(this.params));
    }
}
