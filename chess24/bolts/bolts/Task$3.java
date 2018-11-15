/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.Continuation;

class Task
implements Continuation<TResult, bolts.Task<Void>> {
    Task() {
    }

    @Override
    public bolts.Task<Void> then(bolts.Task<TResult> task) throws Exception {
        if (task.isCancelled()) {
            return bolts.Task.cancelled();
        }
        if (task.isFaulted()) {
            return bolts.Task.forError(task.getError());
        }
        return bolts.Task.forResult(null);
    }
}
