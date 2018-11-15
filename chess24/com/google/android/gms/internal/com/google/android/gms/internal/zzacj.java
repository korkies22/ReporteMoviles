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

public class zzacj
implements Parcelable.Creator<zzach.zza> {
    static void zza(zzach.zza zza2, Parcel parcel, int n) {
        n = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zza2.versionCode);
        zzc.zza(parcel, 2, zza2.zzaFy, false);
        zzc.zzc(parcel, 3, zza2.zzaFz);
        zzc.zzJ(parcel, n);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaZ(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzdb(n);
    }

    public zzach.zza zzaZ(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        int n2 = 0;
        CharSequence charSequence = null;
        int n3 = 0;
        block5 : while (parcel.dataPosition() < n) {
            int n4 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n4)) {
                default: {
                    zzb.zzb(parcel, n4);
                    continue block5;
                }
                case 3: {
                    n3 = zzb.zzg(parcel, n4);
                    continue block5;
                }
                case 2: {
                    charSequence = zzb.zzq(parcel, n4);
                    continue block5;
                }
                case 1: 
            }
            n2 = zzb.zzg(parcel, n4);
        }
        if (parcel.dataPosition() != n) {
            charSequence = new StringBuilder(37);
            charSequence.append("Overread allowed size end=");
            charSequence.append(n);
            throw new zzb.zza(((StringBuilder)charSequence).toString(), parcel);
        }
        return new zzach.zza(n2, (String)charSequence, n3);
    }

    public zzach.zza[] zzdb(int n) {
        return new zzach.zza[n];
    }
}
