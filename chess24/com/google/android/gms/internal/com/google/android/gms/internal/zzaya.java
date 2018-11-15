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
import com.google.android.gms.common.internal.zzad;
import com.google.android.gms.internal.zzaxz;

public class zzaya
implements Parcelable.Creator<zzaxz> {
    static void zza(zzaxz zzaxz2, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zzaxz2.mVersionCode);
        zzc.zza(parcel, 2, zzaxz2.zzOo(), n, false);
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zziR(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzmM(n);
    }

    public zzaxz zziR(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        int n2 = 0;
        Object object = null;
        block4 : while (parcel.dataPosition() < n) {
            int n3 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n3)) {
                default: {
                    zzb.zzb(parcel, n3);
                    continue block4;
                }
                case 2: {
                    object = zzb.zza(parcel, n3, zzad.CREATOR);
                    continue block4;
                }
                case 1: 
            }
            n2 = zzb.zzg(parcel, n3);
        }
        if (parcel.dataPosition() != n) {
            object = new StringBuilder(37);
            object.append("Overread allowed size end=");
            object.append(n);
            throw new zzb.zza(object.toString(), parcel);
        }
        return new zzaxz(n2, (zzad)object);
    }

    public zzaxz[] zzmM(int n) {
        return new zzaxz[n];
    }
}
