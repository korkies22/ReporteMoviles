/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.CancellationToken;
import bolts.Capture;
import bolts.Continuation;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

class Task
implements Continuation<Void, bolts.Task<Void>> {
    final /* synthetic */ Continuation val$continuation;
    final /* synthetic */ CancellationToken val$ct;
    final /* synthetic */ Executor val$executor;
    final /* synthetic */ Callable val$predicate;
    final /* synthetic */ Capture val$predicateContinuation;

    Task(CancellationToken cancellationToken, Callable callable, Continuation continuation, Executor executor, Capture capture) {
        this.val$ct = cancellationToken;
        this.val$predicate = callable;
        this.val$continuation = continuation;
        this.val$executor = executor;
        this.val$predicateContinuation = capture;
    }

    @Override
    public bolts.Task<Void> then(bolts.Task<Void> task) throws Exception {
        if (this.val$ct != null && this.val$ct.isCancellationRequested()) {
            return bolts.Task.cancelled();
        }
        if (((Boolean)this.val$predicate.call()).booleanValue()) {
            return bolts.Task.forResult(null).onSuccessTask(this.val$continuation, this.val$executor).onSuccessTask((Continuation)this.val$predicateContinuation.get(), this.val$executor);
        }
        return bolts.Task.forResult(null);
    }
}
