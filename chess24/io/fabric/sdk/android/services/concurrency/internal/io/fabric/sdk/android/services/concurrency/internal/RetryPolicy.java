/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency.internal;

public interface RetryPolicy {
    public boolean shouldRetry(int var1, Throwable var2);
}
