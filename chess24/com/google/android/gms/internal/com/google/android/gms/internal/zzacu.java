/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzacv;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class zzacu
implements ThreadFactory {
    private final int mPriority;
    private final String zzaHh;
    private final AtomicInteger zzaHi = new AtomicInteger();
    private final ThreadFactory zzaHj = Executors.defaultThreadFactory();

    public zzacu(String string) {
        this(string, 0);
    }

    public zzacu(String string, int n) {
        this.zzaHh = zzac.zzb(string, (Object)"Name must not be null");
        this.mPriority = n;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        runnable = this.zzaHj.newThread(new zzacv(runnable, this.mPriority));
        String string = this.zzaHh;
        int n = this.zzaHi.getAndIncrement();
        StringBuilder stringBuilder = new StringBuilder(13 + String.valueOf(string).length());
        stringBuilder.append(string);
        stringBuilder.append("[");
        stringBuilder.append(n);
        stringBuilder.append("]");
        runnable.setName(stringBuilder.toString());
        return runnable;
    }
}
