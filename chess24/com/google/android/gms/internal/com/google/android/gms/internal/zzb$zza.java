/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzb;
import java.util.Collections;
import java.util.Map;

public static class zzb.zza {
    public byte[] data;
    public String zza;
    public long zzb;
    public long zzc;
    public long zzd;
    public long zze;
    public Map<String, String> zzf = Collections.emptyMap();

    public boolean zza() {
        if (this.zzd < System.currentTimeMillis()) {
            return true;
        }
        return false;
    }

    public boolean zzb() {
        if (this.zze < System.currentTimeMillis()) {
            return true;
        }
        return false;
    }
}
