/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency.internal;

import io.fabric.sdk.android.services.concurrency.internal.AbstractFuture;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

static final class AbstractFuture.Sync<V>
extends AbstractQueuedSynchronizer {
    static final int CANCELLED = 4;
    static final int COMPLETED = 2;
    static final int COMPLETING = 1;
    static final int INTERRUPTED = 8;
    static final int RUNNING = 0;
    private static final long serialVersionUID = 0L;
    private Throwable exception;
    private V value;

    AbstractFuture.Sync() {
    }

    private boolean complete(V v, Throwable throwable, int n) {
        boolean bl = this.compareAndSetState(0, 1);
        if (bl) {
            this.value = v;
            if ((n & 12) != 0) {
                throwable = new CancellationException("Future.cancel() was called.");
            }
            this.exception = throwable;
            this.releaseShared(n);
            return bl;
        }
        if (this.getState() == 1) {
            this.acquireShared(-1);
        }
        return bl;
    }

    private V getValue() throws CancellationException, ExecutionException {
        int n = this.getState();
        if (n != 2) {
            if (n != 4 && n != 8) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error, synchronizer in invalid state: ");
                stringBuilder.append(n);
                throw new IllegalStateException(stringBuilder.toString());
            }
            throw AbstractFuture.cancellationExceptionWithCause("Task was cancelled.", this.exception);
        }
        if (this.exception != null) {
            throw new ExecutionException(this.exception);
        }
        return this.value;
    }

    boolean cancel(boolean bl) {
        int n = bl ? 8 : 4;
        return this.complete(null, null, n);
    }

    V get() throws CancellationException, ExecutionException, InterruptedException {
        this.acquireSharedInterruptibly(-1);
        return this.getValue();
    }

    V get(long l) throws TimeoutException, CancellationException, ExecutionException, InterruptedException {
        if (!this.tryAcquireSharedNanos(-1, l)) {
            throw new TimeoutException("Timeout waiting for task.");
        }
        return this.getValue();
    }

    boolean isCancelled() {
        if ((this.getState() & 12) != 0) {
            return true;
        }
        return false;
    }

    boolean isDone() {
        if ((this.getState() & 14) != 0) {
            return true;
        }
        return false;
    }

    boolean set(V v) {
        return this.complete(v, null, 2);
    }

    boolean setException(Throwable throwable) {
        return this.complete(null, throwable, 2);
    }

    @Override
    protected int tryAcquireShared(int n) {
        if (this.isDone()) {
            return 1;
        }
        return -1;
    }

    @Override
    protected boolean tryReleaseShared(int n) {
        this.setState(n);
        return true;
    }

    boolean wasInterrupted() {
        if (this.getState() == 8) {
            return true;
        }
        return false;
    }
}
