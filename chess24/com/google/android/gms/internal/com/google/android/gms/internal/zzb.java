/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import java.util.Collections;
import java.util.Map;

public interface zzb {
    public void initialize();

    public zza zza(String var1);

    public void zza(String var1, zza var2);

    public static class zza {
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

}
