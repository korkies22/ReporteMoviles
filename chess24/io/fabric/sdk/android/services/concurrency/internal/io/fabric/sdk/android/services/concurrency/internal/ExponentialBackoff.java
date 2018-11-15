/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency.internal;

import io.fabric.sdk.android.services.concurrency.internal.Backoff;

public class ExponentialBackoff
implements Backoff {
    private static final int DEFAULT_POWER = 2;
    private final long baseTimeMillis;
    private final int power;

    public ExponentialBackoff(long l) {
        this(l, 2);
    }

    public ExponentialBackoff(long l, int n) {
        this.baseTimeMillis = l;
        this.power = n;
    }

    @Override
    public long getDelayMillis(int n) {
        return (long)((double)this.baseTimeMillis * Math.pow(this.power, n));
    }
}
