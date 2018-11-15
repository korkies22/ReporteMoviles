/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package android.support.v4.content;

import android.util.Log;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class ModernAsyncTask
extends FutureTask<Result> {
    ModernAsyncTask(Callable callable) {
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
            Object v = this.get();
            ModernAsyncTask.this.postResultIfNotInvoked(v);
            return;
        }
        catch (Throwable throwable) {
            throw new RuntimeException("An error occurred while executing doInBackground()", throwable);
        }
        catch (ExecutionException executionException) {
            throw new RuntimeException("An error occurred while executing doInBackground()", executionException.getCause());
        }
        catch (InterruptedException interruptedException) {
            Log.w((String)android.support.v4.content.ModernAsyncTask.LOG_TAG, (Throwable)interruptedException);
            return;
        }
        catch (CancellationException cancellationException) {}
        ModernAsyncTask.this.postResultIfNotInvoked(null);
    }
}
