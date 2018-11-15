/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency.internal;

import io.fabric.sdk.android.services.concurrency.internal.Backoff;
import io.fabric.sdk.android.services.concurrency.internal.RetryFuture;
import io.fabric.sdk.android.services.concurrency.internal.RetryPolicy;
import io.fabric.sdk.android.services.concurrency.internal.RetryState;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class RetryThreadPoolExecutor
extends ScheduledThreadPoolExecutor {
    private final Backoff backoff;
    private final RetryPolicy retryPolicy;

    public RetryThreadPoolExecutor(int n, RetryPolicy retryPolicy, Backoff backoff) {
        this(n, Executors.defaultThreadFactory(), retryPolicy, backoff);
    }

    public RetryThreadPoolExecutor(int n, ThreadFactory threadFactory, RetryPolicy retryPolicy, Backoff backoff) {
        super(n, threadFactory);
        if (retryPolicy == null) {
            throw new NullPointerException("retry policy must not be null");
        }
        if (backoff == null) {
            throw new NullPointerException("backoff must not be null");
        }
        this.retryPolicy = retryPolicy;
        this.backoff = backoff;
    }

    private <T> Future<T> scheduleWithRetryInternal(Callable<T> object) {
        if (object == null) {
            throw new NullPointerException();
        }
        object = new RetryFuture<T>((Callable<T>)object, new RetryState(this.backoff, this.retryPolicy), this);
        this.execute((Runnable)object);
        return object;
    }

    public Backoff getBackoff() {
        return this.backoff;
    }

    public RetryPolicy getRetryPolicy() {
        return this.retryPolicy;
    }

    public Future<?> scheduleWithRetry(Runnable runnable) {
        return this.scheduleWithRetryInternal(Executors.callable(runnable));
    }

    public <T> Future<T> scheduleWithRetry(Runnable runnable, T t) {
        return this.scheduleWithRetryInternal(Executors.callable(runnable, t));
    }

    public <T> Future<T> scheduleWithRetry(Callable<T> callable) {
        return this.scheduleWithRetryInternal(callable);
    }
}
