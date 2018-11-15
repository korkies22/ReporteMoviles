/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.CancellationToken;
import bolts.Continuation;
import bolts.TaskCompletionSource;
import java.util.concurrent.CancellationException;

static final class Task
implements Runnable {
    final /* synthetic */ Continuation val$continuation;
    final /* synthetic */ CancellationToken val$ct;
    final /* synthetic */ bolts.Task val$task;
    final /* synthetic */ TaskCompletionSource val$tcs;

    Task(CancellationToken cancellationToken, TaskCompletionSource taskCompletionSource, Continuation continuation, bolts.Task task) {
        this.val$ct = cancellationToken;
        this.val$tcs = taskCompletionSource;
        this.val$continuation = continuation;
        this.val$task = task;
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
            Object TContinuationResult = this.val$continuation.then(this.val$task);
            this.val$tcs.setResult(TContinuationResult);
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
