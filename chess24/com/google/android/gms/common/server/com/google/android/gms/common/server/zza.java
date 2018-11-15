/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.server;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.server.FavaDiagnosticsEntity;

public class zza
implements Parcelable.Creator<FavaDiagnosticsEntity> {
    static void zza(FavaDiagnosticsEntity favaDiagnosticsEntity, Parcel parcel, int n) {
        n = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, favaDiagnosticsEntity.mVersionCode);
        zzc.zza(parcel, 2, favaDiagnosticsEntity.zzaFs, false);
        zzc.zzc(parcel, 3, favaDiagnosticsEntity.zzaFt);
        zzc.zzJ(parcel, n);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaW(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzcY(n);
    }

    public FavaDiagnosticsEntity zzaW(Parcel parcel) {
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
        return new FavaDiagnosticsEntity(n2, (String)charSequence, n3);
    }

    public FavaDiagnosticsEntity[] zzcY(int n) {
        return new FavaDiagnosticsEntity[n];
    }
}
