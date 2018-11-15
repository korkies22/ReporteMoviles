/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.TaskCompletionSource;

static final class Task
implements Runnable {
    final /* synthetic */ TaskCompletionSource val$tcs;

    Task(TaskCompletionSource taskCompletionSource) {
        this.val$tcs = taskCompletionSource;
    }

    @Override
    public void run() {
        this.val$tcs.trySetResult(null);
    }
}
