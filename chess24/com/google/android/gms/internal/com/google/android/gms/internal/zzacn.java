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
import com.google.android.gms.internal.zzack;
import com.google.android.gms.internal.zzacm;
import com.google.android.gms.internal.zzaco;

public class zzacn
implements Parcelable.Creator<zzaco.zzb> {
    static void zza(zzaco.zzb zzb2, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zzb2.versionCode);
        zzc.zza(parcel, 2, zzb2.zzaA, false);
        zzc.zza(parcel, 3, zzb2.zzaFO, n, false);
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzbb(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzdd(n);
    }

    public zzaco.zzb zzbb(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        String string = null;
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
                    object = (zzack.zza)zzb.zza(parcel, n3, zzack.zza.CREATOR);
                    continue block5;
                }
                case 2: {
                    string = zzb.zzq(parcel, n3);
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
        return new zzaco.zzb(n2, string, (zzack.zza<?, ?>)object);
    }

    public zzaco.zzb[] zzdd(int n) {
        return new zzaco.zzb[n];
    }
}
