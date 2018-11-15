/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import bolts.TaskCompletionSource;
import java.util.concurrent.ScheduledFuture;

static final class Task
implements Runnable {
    final /* synthetic */ ScheduledFuture val$scheduled;
    final /* synthetic */ TaskCompletionSource val$tcs;

    Task(ScheduledFuture scheduledFuture, TaskCompletionSource taskCompletionSource) {
        this.val$scheduled = scheduledFuture;
        this.val$tcs = taskCompletionSource;
    }

    @Override
    public void run() {
        this.val$scheduled.cancel(true);
        this.val$tcs.trySetCancelled();
    }
}
