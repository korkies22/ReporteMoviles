/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzcu;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

class zzcu
implements zzcu.zzb {
    zzcu(com.google.android.gms.tagmanager.zzcu zzcu2) {
    }

    @Override
    public ScheduledExecutorService zzPG() {
        return Executors.newSingleThreadScheduledExecutor();
    }
}
