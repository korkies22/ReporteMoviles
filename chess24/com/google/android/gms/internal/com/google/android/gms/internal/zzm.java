/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzb;
import com.google.android.gms.internal.zzr;

public class zzm<T> {
    public final T result;
    public final zzb.zza zzae;
    public final zzr zzaf;
    public boolean zzag = false;

    private zzm(zzr zzr2) {
        this.result = null;
        this.zzae = null;
        this.zzaf = zzr2;
    }

    private zzm(T t, zzb.zza zza2) {
        this.result = t;
        this.zzae = zza2;
        this.zzaf = null;
    }

    public static <T> zzm<T> zza(T t, zzb.zza zza2) {
        return new zzm<T>(t, zza2);
    }

    public static <T> zzm<T> zzd(zzr zzr2) {
        return new zzm<T>(zzr2);
    }

    public boolean isSuccess() {
        if (this.zzaf == null) {
            return true;
        }
        return false;
    }

    public static interface zza {
        public void zze(zzr var1);
    }

    public static interface zzb<T> {
        public void zzb(T var1);
    }

}
