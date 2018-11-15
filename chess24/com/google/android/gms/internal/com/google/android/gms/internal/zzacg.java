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
import com.google.android.gms.internal.zzacf;
import com.google.android.gms.internal.zzach;

public class zzacg
implements Parcelable.Creator<zzacf> {
    static void zza(zzacf zzacf2, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zzacf2.mVersionCode);
        zzc.zza(parcel, 2, zzacf2.zzxH(), n, false);
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaX(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzcZ(n);
    }

    public zzacf zzaX(Parcel parcel) {
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
                    object = zzb.zza(parcel, n3, zzach.CREATOR);
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
        return new zzacf(n2, (zzach)object);
    }

    public zzacf[] zzcZ(int n) {
        return new zzacf[n];
    }
}
