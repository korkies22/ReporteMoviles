/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zzb
implements Parcelable.Creator<WebImage> {
    static void zza(WebImage webImage, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, webImage.mVersionCode);
        zzc.zza(parcel, 2, (Parcelable)webImage.getUrl(), n, false);
        zzc.zzc(parcel, 3, webImage.getWidth());
        zzc.zzc(parcel, 4, webImage.getHeight());
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaL(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzcJ(n);
    }

    public WebImage zzaL(Parcel parcel) {
        int n;
        int n2 = com.google.android.gms.common.internal.safeparcel.zzb.zzaU(parcel);
        int n3 = 0;
        StringBuilder stringBuilder = null;
        int n4 = n = 0;
        block6 : while (parcel.dataPosition() < n2) {
            int n5 = com.google.android.gms.common.internal.safeparcel.zzb.zzaT(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zzb.zzcW(n5)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, n5);
                    continue block6;
                }
                case 4: {
                    n4 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, n5);
                    continue block6;
                }
                case 3: {
                    n = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, n5);
                    continue block6;
                }
                case 2: {
                    stringBuilder = (Uri)com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, n5, Uri.CREATOR);
                    continue block6;
                }
                case 1: 
            }
            n3 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, n5);
        }
        if (parcel.dataPosition() != n2) {
            stringBuilder = new StringBuilder(37);
            stringBuilder.append("Overread allowed size end=");
            stringBuilder.append(n2);
            throw new zzb.zza(stringBuilder.toString(), parcel);
        }
        return new WebImage(n3, (Uri)stringBuilder, n, n4);
    }

    public WebImage[] zzcJ(int n) {
        return new WebImage[n];
    }
}
