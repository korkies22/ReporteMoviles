/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzaf;

public interface zzx
extends IInterface {
    public void zza(zzaf var1) throws RemoteException;

    public static abstract class zza
    extends Binder
    implements zzx {
        public static zzx zzbw(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IResolveAccountCallbacks");
            if (iInterface != null && iInterface instanceof zzx) {
                return (zzx)iInterface;
            }
            return new zza$zza(iBinder);
        }

        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 2) {
                if (n != 1598968902) {
                    return super.onTransact(n, object, parcel, n2);
                }
                parcel.writeString("com.google.android.gms.common.internal.IResolveAccountCallbacks");
                return true;
            }
            object.enforceInterface("com.google.android.gms.common.internal.IResolveAccountCallbacks");
            object = object.readInt() != 0 ? (zzaf)zzaf.CREATOR.createFromParcel(object) : null;
            this.zza((zzaf)object);
            parcel.writeNoException();
            return true;
        }
    }

    private static class zza$zza
    implements zzx {
        private IBinder zzrp;

        zza$zza(IBinder iBinder) {
            this.zzrp = iBinder;
        }

        public IBinder asBinder() {
            return this.zzrp;
        }

        @Override
        public void zza(zzaf zzaf2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.common.internal.IResolveAccountCallbacks");
                if (zzaf2 != null) {
                    parcel.writeInt(1);
                    zzaf2.writeToParcel(parcel, 0);
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
