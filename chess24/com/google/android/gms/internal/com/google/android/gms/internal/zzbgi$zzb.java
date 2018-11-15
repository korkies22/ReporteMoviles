/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaj;
import com.google.android.gms.internal.zzbgi;
import java.util.HashMap;
import java.util.Map;

public static class zzbgi.zzb {
    private zzaj.zza zzbFS;
    private final Map<String, zzaj.zza> zzbKA = new HashMap<String, zzaj.zza>();

    private zzbgi.zzb() {
    }

    public zzbgi.zza zzRV() {
        return new zzbgi.zza(this.zzbKA, this.zzbFS, null);
    }

    public zzbgi.zzb zzb(String string, zzaj.zza zza2) {
        this.zzbKA.put(string, zza2);
        return this;
    }

    public zzbgi.zzb zzo(zzaj.zza zza2) {
        this.zzbFS = zza2;
        return this;
    }
}
