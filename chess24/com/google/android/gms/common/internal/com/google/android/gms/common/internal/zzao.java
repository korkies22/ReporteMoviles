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
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.internal.zzan;

public class zzao
implements Parcelable.Creator<zzan> {
    static void zza(zzan zzan2, Parcel parcel, int n) {
        n = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zzan2.mVersionCode);
        zzc.zzJ(parcel, n);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaS(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzcV(n);
    }

    public zzan zzaS(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        int n2 = 0;
        while (parcel.dataPosition() < n) {
            int n3 = zzb.zzaT(parcel);
            if (zzb.zzcW(n3) != 1) {
                zzb.zzb(parcel, n3);
                continue;
            }
            n2 = zzb.zzg(parcel, n3);
        }
        if (parcel.dataPosition() != n) {
            StringBuilder stringBuilder = new StringBuilder(37);
            stringBuilder.append("Overread allowed size end=");
            stringBuilder.append(n);
            throw new zzb.zza(stringBuilder.toString(), parcel);
        }
        return new zzan(n2);
    }

    public zzan[] zzcV(int n) {
        return new zzan[n];
    }
}
