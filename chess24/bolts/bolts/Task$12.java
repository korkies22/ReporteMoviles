/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.CancellationToken;
import bolts.Continuation;

class Task
implements Continuation<TResult, bolts.Task<TContinuationResult>> {
    final /* synthetic */ Continuation val$continuation;
    final /* synthetic */ CancellationToken val$ct;

    Task(CancellationToken cancellationToken, Continuation continuation) {
        this.val$ct = cancellationToken;
        this.val$continuation = continuation;
    }

    @Override
    public bolts.Task<TContinuationResult> then(bolts.Task<TResult> task) {
        if (this.val$ct != null && this.val$ct.isCancellationRequested()) {
            return bolts.Task.cancelled();
        }
        if (task.isFaulted()) {
            return bolts.Task.forError(task.getError());
        }
        if (task.isCancelled()) {
            return bolts.Task.cancelled();
        }
        return task.continueWith(this.val$continuation);
    }
}
