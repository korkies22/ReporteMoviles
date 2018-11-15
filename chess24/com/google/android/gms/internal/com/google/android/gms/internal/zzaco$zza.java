/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.internal.zzack;
import com.google.android.gms.internal.zzaco;
import com.google.android.gms.internal.zzacq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public static class zzaco.zza
extends zza {
    public static final Parcelable.Creator<zzaco.zza> CREATOR = new zzacq();
    final String className;
    final int versionCode;
    final ArrayList<zzaco.zzb> zzaFN;

    zzaco.zza(int n, String string, ArrayList<zzaco.zzb> arrayList) {
        this.versionCode = n;
        this.className = string;
        this.zzaFN = arrayList;
    }

    zzaco.zza(String string, Map<String, zzack.zza<?, ?>> map) {
        this.versionCode = 1;
        this.className = string;
        this.zzaFN = zzaco.zza.zzW(map);
    }

    private static ArrayList<zzaco.zzb> zzW(Map<String, zzack.zza<?, ?>> map) {
        if (map == null) {
            return null;
        }
        ArrayList<zzaco.zzb> arrayList = new ArrayList<zzaco.zzb>();
        for (String string : map.keySet()) {
            arrayList.add(new zzaco.zzb(string, map.get(string)));
        }
        return arrayList;
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzacq.zza(this, parcel, n);
    }

    HashMap<String, zzack.zza<?, ?>> zzxZ() {
        HashMap hashMap = new HashMap();
        int n = this.zzaFN.size();
        for (int i = 0; i < n; ++i) {
            zzaco.zzb zzb2 = this.zzaFN.get(i);
            hashMap.put(zzb2.zzaA, zzb2.zzaFO);
        }
        return hashMap;
    }
}
