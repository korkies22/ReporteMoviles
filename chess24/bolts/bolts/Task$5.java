/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.Continuation;
import bolts.TaskCompletionSource;
import java.util.concurrent.atomic.AtomicBoolean;

static final class Task
implements Continuation<TResult, Void> {
    final /* synthetic */ TaskCompletionSource val$firstCompleted;
    final /* synthetic */ AtomicBoolean val$isAnyTaskComplete;

    Task(AtomicBoolean atomicBoolean, TaskCompletionSource taskCompletionSource) {
        this.val$isAnyTaskComplete = atomicBoolean;
        this.val$firstCompleted = taskCompletionSource;
    }

    @Override
    public Void then(bolts.Task<TResult> task) {
        if (this.val$isAnyTaskComplete.compareAndSet(false, true)) {
            this.val$firstCompleted.setResult(task);
        } else {
            task.getError();
        }
        return null;
    }
}
