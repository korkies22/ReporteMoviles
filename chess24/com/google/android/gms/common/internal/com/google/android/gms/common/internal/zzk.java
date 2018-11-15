/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.internal.zzj;

public class zzk
implements Parcelable.Creator<zzj> {
    static void zza(zzj zzj2, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zzj2.version);
        zzc.zzc(parcel, 2, zzj2.zzaEm);
        zzc.zzc(parcel, 3, zzj2.zzaEn);
        zzc.zza(parcel, 4, zzj2.zzaEo, false);
        zzc.zza(parcel, 5, zzj2.zzaEp, false);
        zzc.zza((Parcel)parcel, (int)6, (Parcelable[])zzj2.zzaEq, (int)n, (boolean)false);
        zzc.zza(parcel, 7, zzj2.zzaEr, false);
        zzc.zza(parcel, 8, (Parcelable)zzj2.zzaEs, n, false);
        zzc.zza(parcel, 9, zzj2.zzaEt);
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaO(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzcO(n);
    }

    public zzj zzaO(Parcel parcel) {
        int n;
        int n2;
        Scope[] arrscope;
        Scope[] arrscope2;
        Object object;
        Scope[] arrscope3;
        int n3 = zzb.zzaU(parcel);
        int n4 = n2 = (n = 0);
        Scope[] arrscope4 = arrscope2 = (arrscope3 = (arrscope = (object = null)));
        long l = 0L;
        block11 : while (parcel.dataPosition() < n3) {
            int n5 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n5)) {
                default: {
                    zzb.zzb(parcel, n5);
                    continue block11;
                }
                case 9: {
                    l = zzb.zzi(parcel, n5);
                    continue block11;
                }
                case 8: {
                    arrscope4 = (Account)zzb.zza(parcel, n5, Account.CREATOR);
                    continue block11;
                }
                case 7: {
                    arrscope2 = zzb.zzs(parcel, n5);
                    continue block11;
                }
                case 6: {
                    arrscope3 = zzb.zzb(parcel, n5, Scope.CREATOR);
                    continue block11;
                }
                case 5: {
                    arrscope = zzb.zzr(parcel, n5);
                    continue block11;
                }
                case 4: {
                    object = zzb.zzq(parcel, n5);
                    continue block11;
                }
                case 3: {
                    n4 = zzb.zzg(parcel, n5);
                    continue block11;
                }
                case 2: {
                    n2 = zzb.zzg(parcel, n5);
                    continue block11;
                }
                case 1: 
            }
            n = zzb.zzg(parcel, n5);
        }
        if (parcel.dataPosition() != n3) {
            arrscope = new Scope[](37);
            arrscope.append("Overread allowed size end=");
            arrscope.append(n3);
            throw new zzb.zza(arrscope.toString(), parcel);
        }
        return new zzj(n, n2, n4, (String)object, (IBinder)arrscope, arrscope3, (Bundle)arrscope2, (Account)arrscope4, l);
    }

    public zzj[] zzcO(int n) {
        return new zzj[n];
    }
}
