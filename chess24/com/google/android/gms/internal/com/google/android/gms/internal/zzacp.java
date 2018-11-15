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
import java.util.ArrayList;

public class zzacp
implements Parcelable.Creator<zzaco> {
    static void zza(zzaco zzaco2, Parcel parcel, int n) {
        n = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zzaco2.mVersionCode);
        zzc.zzc(parcel, 2, zzaco2.zzxX(), false);
        zzc.zza(parcel, 3, zzaco2.zzxY(), false);
        zzc.zzJ(parcel, n);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzbc(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzde(n);
    }

    public zzaco zzbc(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        ArrayList<zzaco.zza> arrayList = null;
        int n2 = 0;
        CharSequence charSequence = null;
        block5 : while (parcel.dataPosition() < n) {
            int n3 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n3)) {
                default: {
                    zzb.zzb(parcel, n3);
                    continue block5;
                }
                case 3: {
                    charSequence = zzb.zzq(parcel, n3);
                    continue block5;
                }
                case 2: {
                    arrayList = zzb.zzc(parcel, n3, zzaco.zza.CREATOR);
                    continue block5;
                }
                case 1: 
            }
            n2 = zzb.zzg(parcel, n3);
        }
        if (parcel.dataPosition() != n) {
            charSequence = new StringBuilder(37);
            charSequence.append("Overread allowed size end=");
            charSequence.append(n);
            throw new zzb.zza(((StringBuilder)charSequence).toString(), parcel);
        }
        return new zzaco(n2, arrayList, (String)charSequence);
    }

    public zzaco[] zzde(int n) {
        return new zzaco[n];
    }
}
