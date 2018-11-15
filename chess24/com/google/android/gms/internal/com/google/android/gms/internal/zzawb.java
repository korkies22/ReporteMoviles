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
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.internal.zzawa;
import com.google.android.gms.internal.zzawc;

public class zzawb
implements Parcelable.Creator<zzawa> {
    static void zza(zzawa zzawa2, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zzawa2.mVersionCode);
        zzc.zzc(parcel, 2, zzawa2.zzbzp);
        zzc.zza((Parcel)parcel, (int)3, (Parcelable[])zzawa2.zzbzq, (int)n, (boolean)false);
        zzc.zza(parcel, 4, zzawa2.zzbzr, false);
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzit(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzml(n);
    }

    public zzawa zzit(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        int n2 = 0;
        Object[] arrobject = null;
        String[] arrstring = arrobject;
        int n3 = 0;
        block6 : while (parcel.dataPosition() < n) {
            int n4 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n4)) {
                default: {
                    zzb.zzb(parcel, n4);
                    continue block6;
                }
                case 4: {
                    arrstring = zzb.zzC(parcel, n4);
                    continue block6;
                }
                case 3: {
                    arrobject = zzb.zzb(parcel, n4, zzawc.CREATOR);
                    continue block6;
                }
                case 2: {
                    n3 = zzb.zzg(parcel, n4);
                    continue block6;
                }
                case 1: 
            }
            n2 = zzb.zzg(parcel, n4);
        }
        if (parcel.dataPosition() != n) {
            arrstring = new String[](37);
            arrstring.append("Overread allowed size end=");
            arrstring.append(n);
            throw new zzb.zza(arrstring.toString(), parcel);
        }
        return new zzawa(n2, n3, (zzawc[])arrobject, arrstring);
    }

    public zzawa[] zzml(int n) {
        return new zzawa[n];
    }
}
