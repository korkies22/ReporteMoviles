/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.internal.zzbgi;
import com.google.android.gms.tagmanager.zzdm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public static class zzbgi.zzd {
    private String zzaux = "";
    private final List<zzbgi.zze> zzbKx = new ArrayList<zzbgi.zze>();
    private final Map<String, List<zzbgi.zza>> zzbKy = new HashMap<String, List<zzbgi.zza>>();
    private int zzbKz = 0;

    private zzbgi.zzd() {
    }

    public zzbgi.zzc zzRY() {
        return new zzbgi.zzc(this.zzbKx, this.zzbKy, this.zzaux, this.zzbKz, null);
    }

    public zzbgi.zzd zzb(zzbgi.zze zze2) {
        this.zzbKx.add(zze2);
        return this;
    }

    public zzbgi.zzd zzc(zzbgi.zza zza2) {
        List<zzbgi.zza> list;
        String string = zzdm.zze(zza2.zzRt().get(zzah.zzhL.toString()));
        List<zzbgi.zza> list2 = list = this.zzbKy.get(string);
        if (list == null) {
            list2 = new ArrayList<zzbgi.zza>();
            this.zzbKy.put(string, list2);
        }
        list2.add(zza2);
        return this;
    }

    public zzbgi.zzd zzik(String string) {
        this.zzaux = string;
        return this;
    }

    public zzbgi.zzd zznd(int n) {
        this.zzbKz = n;
        return this;
    }
}
