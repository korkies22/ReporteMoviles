/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzh
implements Parcelable.Creator<Status> {
    static void zza(Status status, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, status.getStatusCode());
        zzc.zza(parcel, 2, status.getStatusMessage(), false);
        zzc.zza(parcel, 3, (Parcelable)status.zzuT(), n, false);
        zzc.zzc(parcel, 1000, status.mVersionCode);
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaI(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzct(n);
    }

    public Status zzaI(Parcel parcel) {
        CharSequence charSequence;
        int n = zzb.zzaU(parcel);
        int n2 = 0;
        StringBuilder stringBuilder = charSequence = null;
        int n3 = 0;
        block5 : while (parcel.dataPosition() < n) {
            int n4 = zzb.zzaT(parcel);
            int n5 = zzb.zzcW(n4);
            if (n5 != 1000) {
                switch (n5) {
                    default: {
                        zzb.zzb(parcel, n4);
                        continue block5;
                    }
                    case 3: {
                        stringBuilder = (PendingIntent)zzb.zza(parcel, n4, PendingIntent.CREATOR);
                        continue block5;
                    }
                    case 2: {
                        charSequence = zzb.zzq(parcel, n4);
                        continue block5;
                    }
                    case 1: 
                }
                n3 = zzb.zzg(parcel, n4);
                continue;
            }
            n2 = zzb.zzg(parcel, n4);
        }
        if (parcel.dataPosition() != n) {
            stringBuilder = new StringBuilder(37);
            stringBuilder.append("Overread allowed size end=");
            stringBuilder.append(n);
            throw new zzb.zza(stringBuilder.toString(), parcel);
        }
        return new Status(n2, n3, (String)charSequence, (PendingIntent)stringBuilder);
    }

    public Status[] zzct(int n) {
        return new Status[n];
    }
}
