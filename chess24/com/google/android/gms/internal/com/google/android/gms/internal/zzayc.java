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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.internal.zzaf;
import com.google.android.gms.internal.zzayb;

public class zzayc
implements Parcelable.Creator<zzayb> {
    static void zza(zzayb zzayb2, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zzayb2.mVersionCode);
        zzc.zza(parcel, 2, zzayb2.zzxA(), n, false);
        zzc.zza(parcel, 3, zzayb2.zzOp(), n, false);
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zziS(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzmN(n);
    }

    public zzayb zziS(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        ConnectionResult connectionResult = null;
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
                    object = zzb.zza(parcel, n3, zzaf.CREATOR);
                    continue block5;
                }
                case 2: {
                    connectionResult = zzb.zza(parcel, n3, ConnectionResult.CREATOR);
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
        return new zzayb(n2, connectionResult, (zzaf)object);
    }

    public zzayb[] zzmN(int n) {
        return new zzayb[n];
    }
}
