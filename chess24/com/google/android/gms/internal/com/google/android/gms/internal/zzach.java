/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.SparseArray
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.google.android.gms.internal.zzaci;
import com.google.android.gms.internal.zzacj;
import com.google.android.gms.internal.zzack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public final class zzach
extends com.google.android.gms.common.internal.safeparcel.zza
implements zzack.zzb<String, Integer> {
    public static final Parcelable.Creator<zzach> CREATOR = new zzaci();
    final int mVersionCode;
    private final HashMap<String, Integer> zzaFv;
    private final SparseArray<String> zzaFw;
    private final ArrayList<zza> zzaFx;

    public zzach() {
        this.mVersionCode = 1;
        this.zzaFv = new HashMap();
        this.zzaFw = new SparseArray();
        this.zzaFx = null;
    }

    zzach(int n, ArrayList<zza> arrayList) {
        this.mVersionCode = n;
        this.zzaFv = new HashMap();
        this.zzaFw = new SparseArray();
        this.zzaFx = null;
        this.zzh(arrayList);
    }

    private void zzh(ArrayList<zza> object) {
        object = object.iterator();
        while (object.hasNext()) {
            zza zza2 = (zza)object.next();
            this.zzj(zza2.zzaFy, zza2.zzaFz);
        }
    }

    @Override
    public /* synthetic */ Object convertBack(Object object) {
        return this.zzd((Integer)object);
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzaci.zza(this, parcel, n);
    }

    public String zzd(Integer object) {
        String string = (String)this.zzaFw.get(object.intValue());
        object = string;
        if (string == null) {
            object = string;
            if (this.zzaFv.containsKey("gms_unknown")) {
                object = "gms_unknown";
            }
        }
        return object;
    }

    public zzach zzj(String string, int n) {
        this.zzaFv.put(string, n);
        this.zzaFw.put(n, (Object)string);
        return this;
    }

    ArrayList<zza> zzxJ() {
        ArrayList<zza> arrayList = new ArrayList<zza>();
        for (String string : this.zzaFv.keySet()) {
            arrayList.add(new zza(string, this.zzaFv.get(string)));
        }
        return arrayList;
    }

    public static final class zza
    extends com.google.android.gms.common.internal.safeparcel.zza {
        public static final Parcelable.Creator<zza> CREATOR = new zzacj();
        final int versionCode;
        final String zzaFy;
        final int zzaFz;

        zza(int n, String string, int n2) {
            this.versionCode = n;
            this.zzaFy = string;
            this.zzaFz = n2;
        }

        zza(String string, int n) {
            this.versionCode = 1;
            this.zzaFy = string;
            this.zzaFz = n;
        }

        public void writeToParcel(Parcel parcel, int n) {
            zzacj.zza(this, parcel, n);
        }
    }

}
