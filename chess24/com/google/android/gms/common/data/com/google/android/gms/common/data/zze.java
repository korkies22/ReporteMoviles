/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.CursorWindow
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zze
implements Parcelable.Creator<DataHolder> {
    static void zza(DataHolder dataHolder, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zza(parcel, 1, dataHolder.zzwE(), false);
        zzc.zza((Parcel)parcel, (int)2, (Parcelable[])dataHolder.zzwF(), (int)n, (boolean)false);
        zzc.zzc(parcel, 3, dataHolder.getStatusCode());
        zzc.zza(parcel, 4, dataHolder.zzwy(), false);
        zzc.zzc(parcel, 1000, dataHolder.mVersionCode);
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaK(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzcF(n);
    }

    public DataHolder zzaK(Parcel object) {
        String[] arrstring;
        int n;
        String[] arrstring2;
        int n2 = zzb.zzaU((Parcel)object);
        int n3 = n = 0;
        String[] arrstring3 = arrstring = (arrstring2 = null);
        block6 : while (object.dataPosition() < n2) {
            int n4 = zzb.zzaT((Parcel)object);
            int n5 = zzb.zzcW(n4);
            if (n5 != 1000) {
                switch (n5) {
                    default: {
                        zzb.zzb((Parcel)object, n4);
                        continue block6;
                    }
                    case 4: {
                        arrstring3 = zzb.zzs((Parcel)object, n4);
                        continue block6;
                    }
                    case 3: {
                        n3 = zzb.zzg((Parcel)object, n4);
                        continue block6;
                    }
                    case 2: {
                        arrstring = (CursorWindow[])zzb.zzb((Parcel)object, n4, CursorWindow.CREATOR);
                        continue block6;
                    }
                    case 1: 
                }
                arrstring2 = zzb.zzC((Parcel)object, n4);
                continue;
            }
            n = zzb.zzg((Parcel)object, n4);
        }
        if (object.dataPosition() != n2) {
            arrstring = new String[](37);
            arrstring.append("Overread allowed size end=");
            arrstring.append(n2);
            throw new zzb.zza(arrstring.toString(), (Parcel)object);
        }
        object = new DataHolder(n, arrstring2, (CursorWindow[])arrstring, n3, (Bundle)arrstring3);
        object.zzwD();
        return object;
    }

    public DataHolder[] zzcF(int n) {
        return new DataHolder[n];
    }
}
