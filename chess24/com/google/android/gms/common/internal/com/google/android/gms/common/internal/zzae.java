/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.common.internal.zzad;

public class zzae
implements Parcelable.Creator<zzad> {
    static void zza(zzad zzad2, Parcel parcel, int n) {
        int n2 = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, zzad2.mVersionCode);
        zzc.zza(parcel, 2, (Parcelable)zzad2.getAccount(), n, false);
        zzc.zzc(parcel, 3, zzad2.getSessionId());
        zzc.zza(parcel, 4, zzad2.zzxy(), n, false);
        zzc.zzJ(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaP(parcel);
    }

    public /* synthetic */ Object[] newArray(int n) {
        return this.zzcS(n);
    }

    public zzad zzaP(Parcel parcel) {
        int n = zzb.zzaU(parcel);
        Account account = null;
        int n2 = 0;
        Object object = null;
        int n3 = 0;
        block6 : while (parcel.dataPosition() < n) {
            int n4 = zzb.zzaT(parcel);
            switch (zzb.zzcW(n4)) {
                default: {
                    zzb.zzb(parcel, n4);
                    continue block6;
                }
                case 4: {
                    object = zzb.zza(parcel, n4, GoogleSignInAccount.CREATOR);
                    continue block6;
                }
                case 3: {
                    n3 = zzb.zzg(parcel, n4);
                    continue block6;
                }
                case 2: {
                    account = (Account)zzb.zza(parcel, n4, Account.CREATOR);
                    continue block6;
                }
                case 1: 
            }
            n2 = zzb.zzg(parcel, n4);
        }
        if (parcel.dataPosition() != n) {
            object = new StringBuilder(37);
            object.append("Overread allowed size end=");
            object.append(n);
            throw new zzb.zza(object.toString(), parcel);
        }
        return new zzad(n2, account, n3, (GoogleSignInAccount)object);
    }

    public zzad[] zzcS(int n) {
        return new zzad[n];
    }
}
