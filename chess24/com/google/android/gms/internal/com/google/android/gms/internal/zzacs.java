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
import com.google.android.gms.internal.zzaco;
import com.google.android.gms.internal.zzacr;

public class zzacs
implements Parcelable.Creator<zzacr> {
    static void zza(zzacr zzacr2, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zzacr2.getVersionCode());
        zzc.zza(parcel, 2, zzacr2.zzya(), false);
        zzc.zza(parcel, 3, zzacr2.zzyb(), n, false);
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzbe(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzdg(n);
    }

    public zzacr zzbe(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        Parcel parcel2 = null;
        int n2 = 0;
        Object object = null;
        block5 : while (parcel.dataPosition() < n) {
            int n3 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n3)) {
                default: {
                    zzb.zzb(parcel, n3);
                    continue block5;
                }
                case 3: {
                    object = zzb.zza(parcel, n3, zzaco.CREATOR);
                    continue block5;
                }
                case 2: {
                    parcel2 = zzb.zzF(parcel, n3);
                    continue block5;
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
        return new zzacr(n2, parcel2, (zzaco)object);
    }

    public zzacr[] zzdg(int n) {
        return new zzacr[n];
    }
}
