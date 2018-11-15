/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Process
 */
package com.google.android.gms.analytics;

import android.os.Process;
import com.google.android.gms.analytics.zzh;

private static class zzh.zzc
extends Thread {
    zzh.zzc(Runnable runnable, String string) {
        super(runnable, string);
    }

    @Override
    public void run() {
        Process.setThreadPriority((int)10);
        super.run();
    }
}
