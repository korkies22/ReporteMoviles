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
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzawb;
import com.google.android.gms.internal.zzawc;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class zzawa
extends zza
implements Comparable<zzawa> {
    public static final Parcelable.Creator<zzawa> CREATOR = new zzawb();
    final int mVersionCode;
    public final int zzbzp;
    public final zzawc[] zzbzq;
    public final String[] zzbzr;
    public final Map<String, zzawc> zzbzs;

    zzawa(int n, int n2, zzawc[] arrzzawc, String[] arrstring) {
        this.mVersionCode = n;
        this.zzbzp = n2;
        this.zzbzq = arrzzawc;
        this.zzbzs = new TreeMap<String, zzawc>();
        for (zzawc zzawc2 : arrzzawc) {
            this.zzbzs.put(zzawc2.name, zzawc2);
        }
        this.zzbzr = arrstring;
        if (this.zzbzr != null) {
            Arrays.sort(this.zzbzr);
        }
    }

    @Override
    public /* synthetic */ int compareTo(Object object) {
        return this.zza((zzawa)object);
    }

    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = bl = false;
        if (object != null) {
            bl2 = bl;
            if (object instanceof zzawa) {
                object = (zzawa)object;
                bl2 = bl;
                if (this.mVersionCode == object.mVersionCode) {
                    bl2 = bl;
                    if (this.zzbzp == object.zzbzp) {
                        bl2 = bl;
                        if (zzaa.equal(this.zzbzs, object.zzbzs)) {
                            bl2 = bl;
                            if (Arrays.equals(this.zzbzr, object.zzbzr)) {
                                bl2 = true;
                            }
                        }
                    }
                }
            }
        }
        return bl2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Configuration(");
        stringBuilder.append(this.mVersionCode);
        stringBuilder.append(", ");
        stringBuilder.append(this.zzbzp);
        stringBuilder.append(", ");
        stringBuilder.append("(");
        String[] arrstring = this.zzbzs.values().iterator();
        while (arrstring.hasNext()) {
            stringBuilder.append(arrstring.next());
            stringBuilder.append(", ");
        }
        stringBuilder.append(")");
        stringBuilder.append(", ");
        stringBuilder.append("(");
        if (this.zzbzr != null) {
            arrstring = this.zzbzr;
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                stringBuilder.append(arrstring[i]);
                stringBuilder.append(", ");
            }
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append(")");
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzawb.zza(this, parcel, n);
    }

    public int zza(zzawa zzawa2) {
        return this.zzbzp - zzawa2.zzbzp;
    }
}
