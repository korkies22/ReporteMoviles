/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Process
 */
package com.google.android.gms.internal;

import android.os.Process;

class zzacv
implements Runnable {
    private final int mPriority;
    private final Runnable zzv;

    public zzacv(Runnable runnable, int n) {
        this.zzv = runnable;
        this.mPriority = n;
    }

    @Override
    public void run() {
        Process.setThreadPriority((int)this.mPriority);
        this.zzv.run();
    }
}
