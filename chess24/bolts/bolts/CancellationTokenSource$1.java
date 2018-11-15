/*
 * Decompiled with CFR 0_134.
 */
package bolts;

import java.util.concurrent.ScheduledFuture;

class CancellationTokenSource
implements Runnable {
    CancellationTokenSource() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        Object object = CancellationTokenSource.this.lock;
        synchronized (object) {
            CancellationTokenSource.this.scheduledCancellation = null;
        }
        CancellationTokenSource.this.cancel();
    }
}
