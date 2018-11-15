/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.CancellationToken;
import bolts.Continuation;
import bolts.TaskCompletionSource;
import java.util.concurrent.Executor;

class Task
implements Continuation<TResult, Void> {
    final /* synthetic */ Continuation val$continuation;
    final /* synthetic */ CancellationToken val$ct;
    final /* synthetic */ Executor val$executor;
    final /* synthetic */ TaskCompletionSource val$tcs;

    Task(TaskCompletionSource taskCompletionSource, Continuation continuation, Executor executor, CancellationToken cancellationToken) {
        this.val$tcs = taskCompletionSource;
        this.val$continuation = continuation;
        this.val$executor = executor;
        this.val$ct = cancellationToken;
    }

    @Override
    public Void then(bolts.Task<TResult> task) {
        bolts.Task.completeImmediately(this.val$tcs, this.val$continuation, task, this.val$executor, this.val$ct);
        return null;
    }
}
