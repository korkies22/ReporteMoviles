/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaam;
import com.google.android.gms.internal.zzaan;
import java.util.concurrent.locks.Lock;

static abstract class zzaan.zza {
    private final zzaam zzaAL;

    protected zzaan.zza(zzaam zzaam2) {
        this.zzaAL = zzaam2;
    }

    public final void zzc(zzaan zzaan2) {
        block4 : {
            zzaan2.zzazn.lock();
            zzaam zzaam2 = zzaan2.zzaAH;
            zzaam zzaam3 = this.zzaAL;
            if (zzaam2 == zzaam3) break block4;
            zzaan2.zzazn.unlock();
            return;
        }
        try {
            this.zzvA();
            return;
        }
        finally {
            zzaan2.zzazn.unlock();
        }
    }

    protected abstract void zzvA();
}
