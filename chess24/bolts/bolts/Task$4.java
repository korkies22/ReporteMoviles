/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.CancellationToken;
import bolts.TaskCompletionSource;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;

static final class Task
implements Runnable {
    final /* synthetic */ Callable val$callable;
    final /* synthetic */ CancellationToken val$ct;
    final /* synthetic */ TaskCompletionSource val$tcs;

    Task(CancellationToken cancellationToken, TaskCompletionSource taskCompletionSource, Callable callable) {
        this.val$ct = cancellationToken;
        this.val$tcs = taskCompletionSource;
        this.val$callable = callable;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        if (this.val$ct != null && this.val$ct.isCancellationRequested()) {
            this.val$tcs.setCancelled();
            return;
        }
        try {
            this.val$tcs.setResult(this.val$callable.call());
            return;
        }
        catch (Exception exception) {
            this.val$tcs.setError(exception);
            return;
        }
        catch (CancellationException cancellationException) {}
        this.val$tcs.setCancelled();
    }
}
