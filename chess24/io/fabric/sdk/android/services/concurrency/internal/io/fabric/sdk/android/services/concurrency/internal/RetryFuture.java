/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency.internal;

import io.fabric.sdk.android.services.concurrency.internal.AbstractFuture;
import io.fabric.sdk.android.services.concurrency.internal.Backoff;
import io.fabric.sdk.android.services.concurrency.internal.RetryPolicy;
import io.fabric.sdk.android.services.concurrency.internal.RetryState;
import io.fabric.sdk.android.services.concurrency.internal.RetryThreadPoolExecutor;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class RetryFuture<T>
extends AbstractFuture<T>
implements Runnable {
    private final RetryThreadPoolExecutor executor;
    RetryState retryState;
    private final AtomicReference<Thread> runner;
    private final Callable<T> task;

    RetryFuture(Callable<T> callable, RetryState retryState, RetryThreadPoolExecutor retryThreadPoolExecutor) {
        this.task = callable;
        this.retryState = retryState;
        this.executor = retryThreadPoolExecutor;
        this.runner = new AtomicReference();
    }

    private Backoff getBackoff() {
        return this.retryState.getBackoff();
    }

    private int getRetryCount() {
        return this.retryState.getRetryCount();
    }

    private RetryPolicy getRetryPolicy() {
        return this.retryState.getRetryPolicy();
    }

    @Override
    protected void interruptTask() {
        Thread thread = this.runner.getAndSet(null);
        if (thread != null) {
            thread.interrupt();
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        if (this.isDone() != false) return;
        if (!this.runner.compareAndSet(null, Thread.currentThread())) {
            return;
        }
        this.set(this.task.call());
lbl6: // 3 sources:
        do {
            this.runner.getAndSet(null);
            return;
            break;
        } while (true);
        {
            catch (Throwable var3_1) {
            }
            catch (Throwable var3_2) {}
            {
                block6 : {
                    if (!this.getRetryPolicy().shouldRetry(this.getRetryCount(), var3_2)) break block6;
                    var1_3 = this.getBackoff().getDelayMillis(this.getRetryCount());
                    this.retryState = this.retryState.nextRetryState();
                    this.executor.schedule(this, var1_3, TimeUnit.MILLISECONDS);
                    ** GOTO lbl6
                }
                this.setException(var3_2);
                ** continue;
            }
        }
        this.runner.getAndSet(null);
        throw var3_1;
    }
}
