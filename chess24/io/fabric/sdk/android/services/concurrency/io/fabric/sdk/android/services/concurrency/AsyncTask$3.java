/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package io.fabric.sdk.android.services.concurrency;

import android.util.Log;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class AsyncTask
extends FutureTask<Result> {
    AsyncTask(Callable callable) {
        super(callable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void done() {
        try {
            AsyncTask.this.postResultIfNotInvoked(this.get());
            return;
        }
        catch (ExecutionException executionException) {
            throw new RuntimeException("An error occured while executing doInBackground()", executionException.getCause());
        }
        catch (InterruptedException interruptedException) {
            Log.w((String)io.fabric.sdk.android.services.concurrency.AsyncTask.LOG_TAG, (Throwable)interruptedException);
            return;
        }
        catch (CancellationException cancellationException) {}
        AsyncTask.this.postResultIfNotInvoked(null);
    }
}
