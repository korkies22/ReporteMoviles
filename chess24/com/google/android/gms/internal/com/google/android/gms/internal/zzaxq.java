/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.internal.zzaxp;

public class zzaxq
implements Parcelable.Creator<zzaxp> {
    static void zza(zzaxp zzaxp2, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zzaxp2.mVersionCode);
        zzc.zzc(parcel, 2, zzaxp2.zzOk());
        zzc.zza(parcel, 3, (Parcelable)zzaxp2.zzOl(), n, false);
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zziO(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzmI(n);
    }

    public zzaxp zziO(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        int n2 = 0;
        StringBuilder stringBuilder = null;
        int n3 = 0;
        block5 : while (parcel.dataPosition() < n) {
            int n4 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n4)) {
                default: {
                    zzb.zzb(parcel, n4);
                    continue block5;
                }
                case 3: {
                    stringBuilder = (Intent)zzb.zza(parcel, n4, Intent.CREATOR);
                    continue block5;
                }
                case 2: {
                    n3 = zzb.zzg(parcel, n4);
                    continue block5;
                }
                case 1: 
            }
            n2 = zzb.zzg(parcel, n4);
        }
        if (parcel.dataPosition() != n) {
            stringBuilder = new StringBuilder(37);
            stringBuilder.append("Overread allowed size end=");
            stringBuilder.append(n);
            throw new zzb.zza(stringBuilder.toString(), parcel);
        }
        return new zzaxp(n2, n3, (Intent)stringBuilder);
    }

    public zzaxp[] zzmI(int n) {
        return new zzaxp[n];
    }
}
