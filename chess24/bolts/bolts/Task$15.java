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
            bolts.Task task = (bolts.Task)this.val$continuation.then(this.val$task);
            if (task == null) {
                this.val$tcs.setResult(null);
                return;
            }
            task.continueWith(new Continuation<TContinuationResult, Void>(){

                @Override
                public Void then(bolts.Task<TContinuationResult> task) {
                    if (15.this.val$ct != null && 15.this.val$ct.isCancellationRequested()) {
                        15.this.val$tcs.setCancelled();
                        return null;
                    }
                    if (task.isCancelled()) {
                        15.this.val$tcs.setCancelled();
                        return null;
                    }
                    if (task.isFaulted()) {
                        15.this.val$tcs.setError(task.getError());
                        return null;
                    }
                    15.this.val$tcs.setResult(task.getResult());
                    return null;
                }
            });
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
