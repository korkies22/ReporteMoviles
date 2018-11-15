/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import io.fabric.sdk.android.services.concurrency.internal.RetryState;

class RetryManager {
    private static final long NANOSECONDS_IN_MS = 1000000L;
    long lastRetry;
    private RetryState retryState;

    public RetryManager(RetryState retryState) {
        if (retryState == null) {
            throw new NullPointerException("retryState must not be null");
        }
        this.retryState = retryState;
    }

    public boolean canRetry(long l) {
        long l2 = this.retryState.getRetryDelay();
        if (l - this.lastRetry >= 1000000L * l2) {
            return true;
        }
        return false;
    }

    public void recordRetry(long l) {
        this.lastRetry = l;
        this.retryState = this.retryState.nextRetryState();
    }

    public void reset() {
        this.lastRetry = 0L;
        this.retryState = this.retryState.initialRetryState();
    }
}
