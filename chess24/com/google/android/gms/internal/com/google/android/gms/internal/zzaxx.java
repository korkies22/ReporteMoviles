/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.internal.zzaxw;

public class zzaxx
implements Parcelable.Creator<zzaxw> {
    static void zza(zzaxw zzaxw2, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zzaxw2.mVersionCode);
        zzc.zza(parcel, 2, (Parcelable)zzaxw2.getAccount(), n, false);
        zzc.zza((Parcel)parcel, (int)3, (Parcelable[])zzaxw2.zzOm(), (int)n, (boolean)false);
        zzc.zza(parcel, 4, zzaxw2.zzqN(), false);
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zziQ(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzmL(n);
    }

    public zzaxw zziQ(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        Account account = null;
        int n2 = 0;
        Scope[] arrscope = null;
        Object object = arrscope;
        block6 : while (parcel.dataPosition() < n) {
            int n3 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n3)) {
                default: {
                    zzb.zzb(parcel, n3);
                    continue block6;
                }
                case 4: {
                    object = zzb.zzq(parcel, n3);
                    continue block6;
                }
                case 3: {
                    arrscope = zzb.zzb(parcel, n3, Scope.CREATOR);
                    continue block6;
                }
                case 2: {
                    account = (Account)zzb.zza(parcel, n3, Account.CREATOR);
                    continue block6;
                }
                case 1: 
            }
            n2 = zzb.zzg(parcel, n3);
        }
        if (parcel.dataPosition() != n) {
            object = new Scope[](37);
            object.append("Overread allowed size end=");
            object.append(n);
            throw new zzb.zza(object.toString(), parcel);
        }
        return new zzaxw(n2, account, arrscope, (String)object);
    }

    public zzaxw[] zzmL(int n) {
        return new zzaxw[n];
    }
}
