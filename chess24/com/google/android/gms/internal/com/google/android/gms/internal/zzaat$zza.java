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
package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaat;

public static abstract class zzaat.zza
extends Binder
implements zzaat {
    public zzaat.zza() {
        this.attachInterface((IInterface)this, "com.google.android.gms.common.api.internal.IStatusCallback");
    }

    public static zzaat zzbp(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.api.internal.IStatusCallback");
        if (iInterface != null && iInterface instanceof zzaat) {
            return (zzaat)iInterface;
        }
        return new zza(iBinder);
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
        if (n != 1) {
            if (n != 1598968902) {
                return super.onTransact(n, object, parcel, n2);
            }
            parcel.writeString("com.google.android.gms.common.api.internal.IStatusCallback");
            return true;
        }
        object.enforceInterface("com.google.android.gms.common.api.internal.IStatusCallback");
        object = object.readInt() != 0 ? (Status)Status.CREATOR.createFromParcel(object) : null;
        this.zzp((Status)object);
        return true;
    }

    private static class zza
    implements zzaat {
        private IBinder zzrp;

        zza(IBinder iBinder) {
            this.zzrp = iBinder;
        }

        public IBinder asBinder() {
            return this.zzrp;
        }

        @Override
        public void zzp(Status status) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.common.api.internal.IStatusCallback");
                if (status != null) {
                    parcel.writeInt(1);
                    status.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.zzrp.transact(1, parcel, null, 1);
                return;
            }
            finally {
                parcel.recycle();
            }
        }
    }

}
