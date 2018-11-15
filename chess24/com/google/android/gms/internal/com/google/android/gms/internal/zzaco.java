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
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzack;
import com.google.android.gms.internal.zzacn;
import com.google.android.gms.internal.zzacp;
import com.google.android.gms.internal.zzacq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class zzaco
extends com.google.android.gms.common.internal.safeparcel.zza {
    public static final Parcelable.Creator<zzaco> CREATOR = new zzacp();
    final int mVersionCode;
    private final HashMap<String, Map<String, zzack.zza<?, ?>>> zzaFK;
    private final ArrayList<zza> zzaFL;
    private final String zzaFM;

    zzaco(int n, ArrayList<zza> arrayList, String string) {
        this.mVersionCode = n;
        this.zzaFL = null;
        this.zzaFK = zzaco.zzi(arrayList);
        this.zzaFM = zzac.zzw(string);
        this.zzxW();
    }

    private static HashMap<String, Map<String, zzack.zza<?, ?>>> zzi(ArrayList<zza> arrayList) {
        HashMap hashMap = new HashMap();
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            zza zza2 = arrayList.get(i);
            hashMap.put(zza2.className, zza2.zzxZ());
        }
        return hashMap;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : this.zzaFK.keySet()) {
            stringBuilder.append(string);
            stringBuilder.append(":\n");
            Map<String, zzack.zza<?, ?>> object = this.zzaFK.get(string);
            for (String string2 : object.keySet()) {
                stringBuilder.append("  ");
                stringBuilder.append(string2);
                stringBuilder.append(": ");
                stringBuilder.append(object.get(string2));
            }
        }
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzacp.zza(this, parcel, n);
    }

    public Map<String, zzack.zza<?, ?>> zzdA(String string) {
        return this.zzaFK.get(string);
    }

    public void zzxW() {
        for (String string : this.zzaFK.keySet()) {
            Map<String, zzack.zza<?, ?>> object = this.zzaFK.get(string);
            Iterator<String> iterator = object.keySet().iterator();
            while (iterator.hasNext()) {
                object.get(iterator.next()).zza(this);
            }
        }
    }

    ArrayList<zza> zzxX() {
        ArrayList<zza> arrayList = new ArrayList<zza>();
        for (String string : this.zzaFK.keySet()) {
            arrayList.add(new zza(string, this.zzaFK.get(string)));
        }
        return arrayList;
    }

    public String zzxY() {
        return this.zzaFM;
    }

    public static class zza
    extends com.google.android.gms.common.internal.safeparcel.zza {
        public static final Parcelable.Creator<zza> CREATOR = new zzacq();
        final String className;
        final int versionCode;
        final ArrayList<zzb> zzaFN;

        zza(int n, String string, ArrayList<zzb> arrayList) {
            this.versionCode = n;
            this.className = string;
            this.zzaFN = arrayList;
        }

        zza(String string, Map<String, zzack.zza<?, ?>> map) {
            this.versionCode = 1;
            this.className = string;
            this.zzaFN = zza.zzW(map);
        }

        private static ArrayList<zzb> zzW(Map<String, zzack.zza<?, ?>> map) {
            if (map == null) {
                return null;
            }
            ArrayList<zzb> arrayList = new ArrayList<zzb>();
            for (String string : map.keySet()) {
                arrayList.add(new zzb(string, map.get(string)));
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
                zzb zzb2 = this.zzaFN.get(i);
                hashMap.put(zzb2.zzaA, zzb2.zzaFO);
            }
            return hashMap;
        }
    }

    public static class zzb
    extends com.google.android.gms.common.internal.safeparcel.zza {
        public static final Parcelable.Creator<zzb> CREATOR = new zzacn();
        final int versionCode;
        final String zzaA;
        final zzack.zza<?, ?> zzaFO;

        zzb(int n, String string, zzack.zza<?, ?> zza2) {
            this.versionCode = n;
            this.zzaA = string;
            this.zzaFO = zza2;
        }

        zzb(String string, zzack.zza<?, ?> zza2) {
            this.versionCode = 1;
            this.zzaA = string;
            this.zzaFO = zza2;
        }

        public void writeToParcel(Parcel parcel, int n) {
            zzacn.zza(this, parcel, n);
        }
    }

}
