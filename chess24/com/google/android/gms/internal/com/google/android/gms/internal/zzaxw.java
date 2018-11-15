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
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.internal.zzaxx;

public class zzaxw
extends zza {
    public static final Parcelable.Creator<zzaxw> CREATOR = new zzaxx();
    final int mVersionCode;
    private final Account zzagg;
    private final String zzajk;
    private final Scope[] zzbCp;

    zzaxw(int n, Account account, Scope[] arrscope, String string) {
        this.mVersionCode = n;
        this.zzagg = account;
        this.zzbCp = arrscope;
        this.zzajk = string;
    }

    public Account getAccount() {
        return this.zzagg;
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzaxx.zza(this, parcel, n);
    }

    public Scope[] zzOm() {
        return this.zzbCp;
    }

    public String zzqN() {
        return this.zzajk;
    }
}
