/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;

class zztd {
    private long zzKH;
    private final zze zzuI;

    public zztd(zze zze2) {
        zzac.zzw(zze2);
        this.zzuI = zze2;
    }

    public zztd(zze zze2, long l) {
        zzac.zzw(zze2);
        this.zzuI = zze2;
        this.zzKH = l;
    }

    public void clear() {
        this.zzKH = 0L;
    }

    public void start() {
        this.zzKH = this.zzuI.elapsedRealtime();
    }

    public boolean zzz(long l) {
        if (this.zzKH == 0L) {
            return true;
        }
        if (this.zzuI.elapsedRealtime() - this.zzKH > l) {
            return true;
        }
        return false;
    }
}
