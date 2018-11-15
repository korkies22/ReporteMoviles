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
import java.io.Serializable;
import java.util.ArrayList;

public class zzacq
implements Parcelable.Creator<zzaco.zza> {
    static void zza(zzaco.zza zza2, Parcel parcel, int n) {
        n = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zza2.versionCode);
        zzc.zza(parcel, 2, zza2.className, false);
        zzc.zzc(parcel, 3, zza2.zzaFN, false);
        zzc.zzJ(parcel, n);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzbd(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzdf(n);
    }

    public zzaco.zza zzbd(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        String string = null;
        int n2 = 0;
        Serializable serializable = null;
        block5 : while (parcel.dataPosition() < n) {
            int n3 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n3)) {
                default: {
                    zzb.zzb(parcel, n3);
                    continue block5;
                }
                case 3: {
                    serializable = zzb.zzc(parcel, n3, zzaco.zzb.CREATOR);
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
            serializable = new StringBuilder(37);
            serializable.append("Overread allowed size end=");
            serializable.append(n);
            throw new zzb.zza(serializable.toString(), parcel);
        }
        return new zzaco.zza(n2, string, (ArrayList<zzaco.zzb>)serializable);
    }

    public zzaco.zza[] zzdf(int n) {
        return new zzaco.zza[n];
    }
}
