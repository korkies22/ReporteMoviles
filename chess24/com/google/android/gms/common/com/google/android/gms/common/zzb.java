/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzb
implements Parcelable.Creator<ConnectionResult> {
    static void zza(ConnectionResult connectionResult, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, connectionResult.mVersionCode);
        zzc.zzc(parcel, 2, connectionResult.getErrorCode());
        zzc.zza(parcel, 3, (Parcelable)connectionResult.getResolution(), n, false);
        zzc.zza(parcel, 4, connectionResult.getErrorMessage(), false);
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaG(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzcq(n);
    }

    public ConnectionResult zzaG(Parcel parcel) {
        int n = com.google.android.gms.common.internal.safeparcel.zzb.zzaU(parcel);
        int n2 = 0;
        StringBuilder stringBuilder = null;
        CharSequence charSequence = stringBuilder;
        int n3 = 0;
        block6 : while (parcel.dataPosition() < n) {
            int n4 = com.google.android.gms.common.internal.safeparcel.zzb.zzaT(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zzb.zzcW(n4)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, n4);
                    continue block6;
                }
                case 4: {
                    charSequence = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, n4);
                    continue block6;
                }
                case 3: {
                    stringBuilder = (PendingIntent)com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, n4, PendingIntent.CREATOR);
                    continue block6;
                }
                case 2: {
                    n3 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, n4);
                    continue block6;
                }
                case 1: 
            }
            n2 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, n4);
        }
        if (parcel.dataPosition() != n) {
            charSequence = new StringBuilder(37);
            charSequence.append("Overread allowed size end=");
            charSequence.append(n);
            throw new zzb.zza(((StringBuilder)charSequence).toString(), parcel);
        }
        return new ConnectionResult(n2, n3, (PendingIntent)stringBuilder, (String)charSequence);
    }

    public ConnectionResult[] zzcq(int n) {
        return new ConnectionResult[n];
    }
}
