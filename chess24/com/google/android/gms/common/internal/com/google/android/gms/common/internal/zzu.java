/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface zzu
extends IInterface {
    public void zza(int var1, IBinder var2, Bundle var3) throws RemoteException;

    public void zzb(int var1, Bundle var2) throws RemoteException;

    public static abstract class zza
    extends Binder
    implements zzu {
        public zza() {
            this.attachInterface((IInterface)this, "com.google.android.gms.common.internal.IGmsCallbacks");
        }

        public static zzu zzbt(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsCallbacks");
            if (iInterface != null && iInterface instanceof zzu) {
                return (zzu)iInterface;
            }
            return new zza$zza(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1598968902) {
                IBinder iBinder = null;
                IBinder iBinder2 = null;
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    case 2: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsCallbacks");
                        n = parcel.readInt();
                        iBinder = iBinder2;
                        if (parcel.readInt() != 0) {
                            iBinder = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                        }
                        this.zzb(n, (Bundle)iBinder);
                        parcel2.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface("com.google.android.gms.common.internal.IGmsCallbacks");
                n = parcel.readInt();
                iBinder2 = parcel.readStrongBinder();
                if (parcel.readInt() != 0) {
                    iBinder = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                }
                this.zza(n, iBinder2, (Bundle)iBinder);
                parcel2.writeNoException();
                return true;
            }
            parcel2.writeString("com.google.android.gms.common.internal.IGmsCallbacks");
            return true;
        }
    }

    private static class zza$zza
    implements zzu {
        private IBinder zzrp;

        zza$zza(IBinder iBinder) {
            this.zzrp = iBinder;
        }

        public IBinder asBinder() {
            return this.zzrp;
        }

        @Override
        public void zza(int n, IBinder iBinder, Bundle bundle) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsCallbacks");
                parcel.writeInt(n);
                parcel.writeStrongBinder(iBinder);
                if (bundle != null) {
                    parcel.writeInt(1);
                    bundle.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.zzrp.transact(1, parcel, parcel2, 0);
                parcel2.readException();
                return;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        @Override
        public void zzb(int n, Bundle bundle) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsCallbacks");
                parcel.writeInt(n);
                if (bundle != null) {
                    parcel.writeInt(1);
                    bundle.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.zzrp.transact(2, parcel, parcel2, 0);
                parcel2.readException();
                return;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }
    }

}
