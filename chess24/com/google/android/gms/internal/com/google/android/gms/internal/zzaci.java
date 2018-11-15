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
import com.google.android.gms.internal.zzach;
import java.io.Serializable;
import java.util.ArrayList;

public class zzaci
implements Parcelable.Creator<zzach> {
    static void zza(zzach zzach2, Parcel parcel, int n) {
        n = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zzach2.mVersionCode);
        zzc.zzc(parcel, 2, zzach2.zzxJ(), false);
        zzc.zzJ(parcel, n);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaY(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzda(n);
    }

    public zzach zzaY(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        int n2 = 0;
        Serializable serializable = null;
        block4 : while (parcel.dataPosition() < n) {
            int n3 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n3)) {
                default: {
                    zzb.zzb(parcel, n3);
                    continue block4;
                }
                case 2: {
                    serializable = zzb.zzc(parcel, n3, zzach.zza.CREATOR);
                    continue block4;
                }
                case 1: 
            }
            n2 = zzb.zzg(parcel, n3);
        }
        if (parcel.dataPosition() != n) {
            serializable = new StringBuilder(37);
            serializable.append("Overread allowed size end=");
            serializable.append(n);
            throw new zzb.zza(serializable.toString(), parcel);
        }
        return new zzach(n2, (ArrayList<zzach.zza>)serializable);
    }

    public zzach[] zzda(int n) {
        return new zzach[n];
    }
}
