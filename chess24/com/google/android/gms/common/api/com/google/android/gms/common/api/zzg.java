/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzg
implements Parcelable.Creator<Scope> {
    static void zza(Scope scope, Parcel parcel, int n) {
        n = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, scope.mVersionCode);
        zzc.zza(parcel, 2, scope.zzuS(), false);
        zzc.zzJ(parcel, n);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaH(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzcs(n);
    }

    public Scope zzaH(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        int n2 = 0;
        CharSequence charSequence = null;
        block4 : while (parcel.dataPosition() < n) {
            int n3 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n3)) {
                default: {
                    zzb.zzb(parcel, n3);
                    continue block4;
                }
                case 2: {
                    charSequence = zzb.zzq(parcel, n3);
                    continue block4;
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
        return new Scope(n2, (String)charSequence);
    }

    public Scope[] zzcs(int n) {
        return new Scope[n];
    }
}
