/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbgi;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public static class zzbgi.zzc {
    private final String zzaux;
    private final List<zzbgi.zze> zzbKx;
    private final Map<String, List<zzbgi.zza>> zzbKy;
    private final int zzbKz;

    private zzbgi.zzc(List<zzbgi.zze> list, Map<String, List<zzbgi.zza>> map, String string, int n) {
        this.zzbKx = Collections.unmodifiableList(list);
        this.zzbKy = Collections.unmodifiableMap(map);
        this.zzaux = string;
        this.zzbKz = n;
    }

    public static zzbgi.zzd zzRW() {
        return new zzbgi.zzd(null);
    }

    public String getVersion() {
        return this.zzaux;
    }

    public String toString() {
        String string = String.valueOf(this.zzRr());
        String string2 = String.valueOf(this.zzbKy);
        StringBuilder stringBuilder = new StringBuilder(17 + String.valueOf(string).length() + String.valueOf(string2).length());
        stringBuilder.append("Rules: ");
        stringBuilder.append(string);
        stringBuilder.append("  Macros: ");
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    public Map<String, List<zzbgi.zza>> zzRX() {
        return this.zzbKy;
    }

    public List<zzbgi.zze> zzRr() {
        return this.zzbKx;
    }
}
