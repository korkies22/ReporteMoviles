/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.CancellationToken;
import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;

class Task
implements Continuation<TContinuationResult, Void> {
    Task() {
    }

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
}
