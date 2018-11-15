/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface zzr
extends IInterface {
    public Account getAccount() throws RemoteException;

    public static abstract class zza
    extends Binder
    implements zzr {
        public static zzr zzbr(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor");
            if (iInterface != null && iInterface instanceof zzr) {
                return (zzr)iInterface;
            }
            return new zza$zza(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 2) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                parcel2.writeString("com.google.android.gms.common.internal.IAccountAccessor");
                return true;
            }
            parcel.enforceInterface("com.google.android.gms.common.internal.IAccountAccessor");
            parcel = this.getAccount();
            parcel2.writeNoException();
            if (parcel != null) {
                parcel2.writeInt(1);
                parcel.writeToParcel(parcel2, 1);
                return true;
            }
            parcel2.writeInt(0);
            return true;
        }
    }

    private static class zza$zza
    implements zzr {
        private IBinder zzrp;

        zza$zza(IBinder iBinder) {
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

}
