/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.internal.zzd;

public class zze
implements Parcelable.Creator<zzd> {
    static void zza(zzd zzd2, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zzd2.mVersionCode);
        zzc.zza(parcel, 2, zzd2.zzaDx, false);
        zzc.zza((Parcel)parcel, (int)3, (Parcelable[])zzd2.zzaDy, (int)n, (boolean)false);
        zzc.zza(parcel, 4, zzd2.zzaDz, false);
        zzc.zza(parcel, 5, zzd2.zzaDA, false);
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaM(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzcL(n);
    }

    public zzd zzaM(Parcel parcel) {
        Scope[] arrscope;
        Scope[] arrscope2;
        Object object;
        int n = zzb.zzaU(parcel);
        Object object2 = object = (arrscope = (arrscope2 = null));
        int n2 = 0;
        block7 : while (parcel.dataPosition() < n) {
            int n3 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n3)) {
                default: {
                    zzb.zzb(parcel, n3);
                    continue block7;
                }
                case 5: {
                    object2 = zzb.zzh(parcel, n3);
                    continue block7;
                }
                case 4: {
                    object = zzb.zzh(parcel, n3);
                    continue block7;
                }
                case 3: {
                    arrscope = zzb.zzb(parcel, n3, Scope.CREATOR);
                    continue block7;
                }
                case 2: {
                    arrscope2 = zzb.zzr(parcel, n3);
                    continue block7;
                }
                case 1: 
            }
            n2 = zzb.zzg(parcel, n3);
        }
        if (parcel.dataPosition() != n) {
            arrscope = new Scope[](37);
            arrscope.append("Overread allowed size end=");
            arrscope.append(n);
            throw new zzb.zza(arrscope.toString(), parcel);
        }
        return new zzd(n2, (IBinder)arrscope2, arrscope, (Integer)object, (Integer)object2);
    }

    public zzd[] zzcL(int n) {
        return new zzd[n];
    }
}
