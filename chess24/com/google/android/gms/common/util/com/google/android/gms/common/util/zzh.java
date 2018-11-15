/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 */
package com.google.android.gms.common.util;

import android.os.SystemClock;
import com.google.android.gms.common.util.zze;

public class zzh
implements zze {
    private static zzh zzaGK = new zzh();

    private zzh() {
    }

    public static zze zzyv() {
        return zzaGK;
    }

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override
    public long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    @Override
    public long nanoTime() {
        return System.nanoTime();
    }
}
