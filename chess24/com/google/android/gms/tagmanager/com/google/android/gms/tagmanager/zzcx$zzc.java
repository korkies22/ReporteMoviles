/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbgi;
import com.google.android.gms.tagmanager.zzcx;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

private static class zzcx.zzc {
    private final Set<zzbgi.zze> zzbFJ = new HashSet<zzbgi.zze>();
    private final Map<zzbgi.zze, List<zzbgi.zza>> zzbFT = new HashMap<zzbgi.zze, List<zzbgi.zza>>();
    private final Map<zzbgi.zze, List<zzbgi.zza>> zzbFU = new HashMap<zzbgi.zze, List<zzbgi.zza>>();
    private final Map<zzbgi.zze, List<String>> zzbFV = new HashMap<zzbgi.zze, List<String>>();
    private final Map<zzbgi.zze, List<String>> zzbFW = new HashMap<zzbgi.zze, List<String>>();
    private zzbgi.zza zzbFX;

    public Set<zzbgi.zze> zzPN() {
        return this.zzbFJ;
    }

    public Map<zzbgi.zze, List<zzbgi.zza>> zzPO() {
        return this.zzbFT;
    }

    public Map<zzbgi.zze, List<String>> zzPP() {
        return this.zzbFV;
    }

    public Map<zzbgi.zze, List<String>> zzPQ() {
        return this.zzbFW;
    }

    public Map<zzbgi.zze, List<zzbgi.zza>> zzPR() {
        return this.zzbFU;
    }

    public zzbgi.zza zzPS() {
        return this.zzbFX;
    }

    public void zza(zzbgi.zze zze2) {
        this.zzbFJ.add(zze2);
    }

    public void zza(zzbgi.zze zze2, zzbgi.zza zza2) {
        List<zzbgi.zza> list;
        List<zzbgi.zza> list2 = list = this.zzbFT.get(zze2);
        if (list == null) {
            list2 = new ArrayList<zzbgi.zza>();
            this.zzbFT.put(zze2, list2);
        }
        list2.add(zza2);
    }

    public void zza(zzbgi.zze zze2, String string) {
        List<String> list;
        List<String> list2 = list = this.zzbFV.get(zze2);
        if (list == null) {
            list2 = new ArrayList<String>();
            this.zzbFV.put(zze2, list2);
        }
        list2.add(string);
    }

    public void zzb(zzbgi.zza zza2) {
        this.zzbFX = zza2;
    }

    public void zzb(zzbgi.zze zze2, zzbgi.zza zza2) {
        List<zzbgi.zza> list;
        List<zzbgi.zza> list2 = list = this.zzbFU.get(zze2);
        if (list == null) {
            list2 = new ArrayList<zzbgi.zza>();
            this.zzbFU.put(zze2, list2);
        }
        list2.add(zza2);
    }

    public void zzb(zzbgi.zze zze2, String string) {
        List<String> list;
        List<String> list2 = list = this.zzbFW.get(zze2);
        if (list == null) {
            list2 = new ArrayList<String>();
            this.zzbFW.put(zze2, list2);
        }
        list2.add(string);
    }
}
