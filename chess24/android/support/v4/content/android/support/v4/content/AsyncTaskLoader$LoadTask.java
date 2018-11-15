/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.content;

import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ModernAsyncTask;
import android.support.v4.os.OperationCanceledException;
import java.util.concurrent.CountDownLatch;

final class AsyncTaskLoader.LoadTask
extends ModernAsyncTask<Void, Void, D>
implements Runnable {
    private final CountDownLatch mDone = new CountDownLatch(1);
    boolean waiting;

    AsyncTaskLoader.LoadTask() {
    }

    protected /* varargs */ D doInBackground(Void ... object) {
        try {
            object = AsyncTaskLoader.this.onLoadInBackground();
        }
        catch (OperationCanceledException operationCanceledException) {
            if (!this.isCancelled()) {
                throw operationCanceledException;
            }
            return null;
        }
        return (D)object;
    }

    @Override
    protected void onCancelled(D d) {
        try {
            AsyncTaskLoader.this.dispatchOnCancelled(this, d);
            return;
        }
        finally {
            this.mDone.countDown();
        }
    }

    @Override
    protected void onPostExecute(D d) {
        try {
            AsyncTaskLoader.this.dispatchOnLoadComplete(this, d);
            return;
        }
        finally {
            this.mDone.countDown();
        }
    }

    @Override
    public void run() {
        this.waiting = false;
        AsyncTaskLoader.this.executePendingTask();
    }

    public void waitForLoader() {
        try {
            this.mDone.await();
            return;
        }
        catch (InterruptedException interruptedException) {
            return;
        }
    }
}
