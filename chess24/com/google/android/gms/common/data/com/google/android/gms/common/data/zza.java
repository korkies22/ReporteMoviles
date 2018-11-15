/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zza
implements Parcelable.Creator<BitmapTeleporter> {
    static void zza(BitmapTeleporter bitmapTeleporter, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, bitmapTeleporter.mVersionCode);
        zzc.zza(parcel, 2, (Parcelable)bitmapTeleporter.zzSn, n, false);
        zzc.zzc(parcel, 3, bitmapTeleporter.zzanR);
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaJ(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzcz(n);
    }

    public BitmapTeleporter zzaJ(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        int n2 = 0;
        StringBuilder stringBuilder = null;
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
                    stringBuilder = (ParcelFileDescriptor)zzb.zza(parcel, n4, ParcelFileDescriptor.CREATOR);
                    continue block5;
                }
                case 1: 
            }
            n2 = zzb.zzg(parcel, n4);
        }
        if (parcel.dataPosition() != n) {
            stringBuilder = new StringBuilder(37);
            stringBuilder.append("Overread allowed size end=");
            stringBuilder.append(n);
            throw new zzb.zza(stringBuilder.toString(), parcel);
        }
        return new BitmapTeleporter(n2, (ParcelFileDescriptor)stringBuilder, n3);
    }

    public BitmapTeleporter[] zzcz(int n) {
        return new BitmapTeleporter[n];
    }
}
