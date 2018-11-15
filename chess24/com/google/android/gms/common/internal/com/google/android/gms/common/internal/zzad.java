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
import android.support.annotation.Nullable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzae;

public class zzad
extends zza {
    public static final Parcelable.Creator<zzad> CREATOR = new zzae();
    final int mVersionCode;
    private final int zzaFf;
    private final GoogleSignInAccount zzaFg;
    private final Account zzagg;

    zzad(int n, Account account, int n2, GoogleSignInAccount googleSignInAccount) {
        this.mVersionCode = n;
        this.zzagg = account;
        this.zzaFf = n2;
        this.zzaFg = googleSignInAccount;
    }

    public zzad(Account account, int n, GoogleSignInAccount googleSignInAccount) {
        this(2, account, n, googleSignInAccount);
    }

    public Account getAccount() {
        return this.zzagg;
    }

    public int getSessionId() {
        return this.zzaFf;
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzae.zza(this, parcel, n);
    }

    @Nullable
    public GoogleSignInAccount zzxy() {
        return this.zzaFg;
    }
}
