/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.internal.zzah;

public class zzai
implements Parcelable.Creator<zzah> {
    static void zza(zzah zzah2, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zzah2.mVersionCode);
        zzc.zzc(parcel, 2, zzah2.zzxD());
        zzc.zzc(parcel, 3, zzah2.zzxE());
        zzc.zza((Parcel)parcel, (int)4, (Parcelable[])zzah2.zzxF(), (int)n, (boolean)false);
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaR(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzcU(n);
    }

    public zzah zzaR(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        int n2 = 0;
        int n3 = 0;
        Scope[] arrscope = null;
        int n4 = n3;
        block6 : while (parcel.dataPosition() < n) {
            int n5 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n5)) {
                default: {
                    zzb.zzb(parcel, n5);
                    continue block6;
                }
                case 4: {
                    arrscope = zzb.zzb(parcel, n5, Scope.CREATOR);
                    continue block6;
                }
                case 3: {
                    n3 = zzb.zzg(parcel, n5);
                    continue block6;
                }
                case 2: {
                    n4 = zzb.zzg(parcel, n5);
                    continue block6;
                }
                case 1: 
            }
            n2 = zzb.zzg(parcel, n5);
        }
        if (parcel.dataPosition() != n) {
            arrscope = new Scope[](37);
            arrscope.append("Overread allowed size end=");
            arrscope.append(n);
            throw new zzb.zza(arrscope.toString(), parcel);
        }
        return new zzah(n2, n4, n3, arrscope);
    }

    public zzah[] zzcU(int n) {
        return new zzah[n];
    }
}
