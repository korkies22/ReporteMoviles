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
import com.google.android.gms.common.internal.zza;
import com.google.android.gms.common.internal.zzk;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zzc;
import java.util.Collection;

public class zzj
extends com.google.android.gms.common.internal.safeparcel.zza {
    public static final Parcelable.Creator<zzj> CREATOR = new zzk();
    final int version;
    final int zzaEm;
    int zzaEn;
    String zzaEo;
    IBinder zzaEp;
    Scope[] zzaEq;
    Bundle zzaEr;
    Account zzaEs;
    long zzaEt;

    public zzj(int n) {
        this.version = 3;
        this.zzaEn = zzc.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        this.zzaEm = n;
    }

    zzj(int n, int n2, int n3, String string, IBinder iBinder, Scope[] arrscope, Bundle bundle, Account account, long l) {
        this.version = n;
        this.zzaEm = n2;
        this.zzaEn = n3;
        this.zzaEo = "com.google.android.gms".equals(string) ? "com.google.android.gms" : string;
        if (n < 2) {
            this.zzaEs = this.zzbq(iBinder);
        } else {
            this.zzaEp = iBinder;
            this.zzaEs = account;
        }
        this.zzaEq = arrscope;
        this.zzaEr = bundle;
        this.zzaEt = l;
    }

    private Account zzbq(IBinder iBinder) {
        if (iBinder != null) {
            return zza.zza(zzr.zza.zzbr(iBinder));
        }
        return null;
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzk.zza(this, parcel, n);
    }

    public zzj zzb(zzr zzr2) {
        if (zzr2 != null) {
            this.zzaEp = zzr2.asBinder();
        }
        return this;
    }

    public zzj zzdq(String string) {
        this.zzaEo = string;
        return this;
    }

    public zzj zze(Account account) {
        this.zzaEs = account;
        return this;
    }

    public zzj zzf(Collection<Scope> collection) {
        this.zzaEq = collection.toArray(new Scope[collection.size()]);
        return this;
    }

    public zzj zzp(Bundle bundle) {
        this.zzaEr = bundle;
        return this;
    }
}
