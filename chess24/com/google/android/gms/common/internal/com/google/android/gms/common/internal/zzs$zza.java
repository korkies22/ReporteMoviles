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
package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzs;

public static abstract class zzs.zza
extends Binder
implements zzs {
    public static zzs zzbs(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ICancelToken");
        if (iInterface != null && iInterface instanceof zzs) {
            return (zzs)iInterface;
        }
        return new zza(iBinder);
    }

    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (n != 2) {
            if (n != 1598968902) {
                return super.onTransact(n, parcel, parcel2, n2);
            }
            parcel2.writeString("com.google.android.gms.common.internal.ICancelToken");
            return true;
        }
        parcel.enforceInterface("com.google.android.gms.common.internal.ICancelToken");
        this.cancel();
        return true;
    }

    private static class zza
    implements zzs {
        private IBinder zzrp;

        zza(IBinder iBinder) {
            this.zzrp = iBinder;
        }

        public IBinder asBinder() {
            return this.zzrp;
        }

        @Override
        public void cancel() throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.common.internal.ICancelToken");
                this.zzrp.transact(2, parcel, null, 1);
                return;
            }
            finally {
                parcel.recycle();
            }
        }
    }

}
