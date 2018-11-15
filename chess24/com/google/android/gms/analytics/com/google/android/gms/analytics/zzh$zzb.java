/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.zzh;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

private static class zzh.zzb
implements ThreadFactory {
    private static final AtomicInteger zzabp = new AtomicInteger();

    private zzh.zzb() {
    }

    @Override
    public Thread newThread(Runnable runnable) {
        int n = zzabp.incrementAndGet();
        StringBuilder stringBuilder = new StringBuilder(23);
        stringBuilder.append("measurement-");
        stringBuilder.append(n);
        return new zzh.zzc(runnable, stringBuilder.toString());
    }
}
