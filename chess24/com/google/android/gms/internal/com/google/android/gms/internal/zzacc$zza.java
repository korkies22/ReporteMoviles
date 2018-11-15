/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzacc;

public static abstract class zzacc.zza
extends Binder
implements zzacc {
    public zzacc.zza() {
        this.attachInterface((IInterface)this, "com.google.android.gms.common.internal.service.ICommonCallbacks");
    }

    public static zzacc zzbA(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.service.ICommonCallbacks");
        if (iInterface != null && iInterface instanceof zzacc) {
            return (zzacc)iInterface;
        }
        return new zza(iBinder);
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (n != 1) {
            if (n != 1598968902) {
                return super.onTransact(n, parcel, parcel2, n2);
            }
            parcel2.writeString("com.google.android.gms.common.internal.service.ICommonCallbacks");
            return true;
        }
        parcel.enforceInterface("com.google.android.gms.common.internal.service.ICommonCallbacks");
        this.zzcX(parcel.readInt());
        parcel2.writeNoException();
        return true;
    }

    private static class zza
    implements zzacc {
        private IBinder zzrp;

        zza(IBinder iBinder) {
            this.zzrp = iBinder;
        }

        public IBinder asBinder() {
            return this.zzrp;
        }

        @Override
        public void zzcX(int n) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.common.internal.service.ICommonCallbacks");
                parcel.writeInt(n);
                this.zzrp.transact(1, parcel, parcel2, 0);
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
