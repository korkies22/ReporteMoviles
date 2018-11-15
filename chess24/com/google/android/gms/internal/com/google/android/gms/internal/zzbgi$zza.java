/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaj;
import com.google.android.gms.internal.zzbgi;
import java.util.Collections;
import java.util.Map;

public static class zzbgi.zza {
    private final zzaj.zza zzbFS;
    private final Map<String, zzaj.zza> zzbKA;

    private zzbgi.zza(Map<String, zzaj.zza> map, zzaj.zza zza2) {
        this.zzbKA = map;
        this.zzbFS = zza2;
    }

    public static zzbgi.zzb zzRU() {
        return new zzbgi.zzb(null);
    }

    public String toString() {
        String string = String.valueOf(this.zzRt());
        String string2 = String.valueOf(this.zzbFS);
        StringBuilder stringBuilder = new StringBuilder(32 + String.valueOf(string).length() + String.valueOf(string2).length());
        stringBuilder.append("Properties: ");
        stringBuilder.append(string);
        stringBuilder.append(" pushAfterEvaluate: ");
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    public zzaj.zza zzPM() {
        return this.zzbFS;
    }

    public Map<String, zzaj.zza> zzRt() {
        return Collections.unmodifiableMap(this.zzbKA);
    }

    public void zza(String string, zzaj.zza zza2) {
        this.zzbKA.put(string, zza2);
    }
}
