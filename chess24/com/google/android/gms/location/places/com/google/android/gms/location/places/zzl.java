/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.location.places.PlaceReport;

public class zzl
implements Parcelable.Creator<PlaceReport> {
    static void zza(PlaceReport placeReport, Parcel parcel, int n) {
        n = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, placeReport.mVersionCode);
        zzc.zza(parcel, 2, placeReport.getPlaceId(), false);
        zzc.zza(parcel, 3, placeReport.getTag(), false);
        zzc.zza(parcel, 4, placeReport.getSource(), false);
        zzc.zzJ(parcel, n);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzhb(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzkD(n);
    }

    public PlaceReport zzhb(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        String string = null;
        int n2 = 0;
        CharSequence charSequence = null;
        CharSequence charSequence2 = charSequence;
        block6 : while (parcel.dataPosition() < n) {
            int n3 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n3)) {
                default: {
                    zzb.zzb(parcel, n3);
                    continue block6;
                }
                case 4: {
                    charSequence2 = zzb.zzq(parcel, n3);
                    continue block6;
                }
                case 3: {
                    charSequence = zzb.zzq(parcel, n3);
                    continue block6;
                }
                case 2: {
                    string = zzb.zzq(parcel, n3);
                    continue block6;
                }
                case 1: 
            }
            n2 = zzb.zzg(parcel, n3);
        }
        if (parcel.dataPosition() != n) {
            charSequence2 = new StringBuilder(37);
            charSequence2.append("Overread allowed size end=");
            charSequence2.append(n);
            throw new zzb.zza(((StringBuilder)charSequence2).toString(), parcel);
        }
        return new PlaceReport(n2, string, (String)charSequence, (String)charSequence2);
    }

    public PlaceReport[] zzkD(int n) {
        return new PlaceReport[n];
    }
}
