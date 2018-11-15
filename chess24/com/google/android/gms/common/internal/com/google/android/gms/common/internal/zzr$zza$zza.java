/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzr;

private static class zzr.zza.zza
implements zzr {
    private IBinder zzrp;

    zzr.zza.zza(IBinder iBinder) {
        this.zzrp = iBinder;
    }

    public IBinder asBinder() {
        return this.zzrp;
    }

    @Override
    public Account getAccount() throws RemoteException {
        Account account;
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.google.android.gms.common.internal.IAccountAccessor");
            this.zzrp.transact(2, parcel, parcel2, 0);
            parcel2.readException();
            account = parcel2.readInt() != 0 ? (Account)Account.CREATOR.createFromParcel(parcel2) : null;
        }
        catch (Throwable throwable) {
            parcel2.recycle();
            parcel.recycle();
            throw throwable;
        }
        parcel2.recycle();
        parcel.recycle();
        return account;
    }
}
