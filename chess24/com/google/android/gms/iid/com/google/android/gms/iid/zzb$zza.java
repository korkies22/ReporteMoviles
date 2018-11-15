/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Message
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.iid;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.iid.zzb;

public static abstract class zzb.zza
extends Binder
implements zzb {
    public zzb.zza() {
        this.attachInterface((IInterface)this, "com.google.android.gms.iid.IMessengerCompat");
    }

    public static zzb zzcZ(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.iid.IMessengerCompat");
        if (iInterface != null && iInterface instanceof zzb) {
            return (zzb)iInterface;
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
            parcel.writeString("com.google.android.gms.iid.IMessengerCompat");
            return true;
        }
        object.enforceInterface("com.google.android.gms.iid.IMessengerCompat");
        object = object.readInt() != 0 ? (Message)Message.CREATOR.createFromParcel(object) : null;
        this.send((Message)object);
        return true;
    }

    private static class zza
    implements zzb {
        private IBinder zzrp;

        zza(IBinder iBinder) {
            this.zzrp = iBinder;
        }

        public IBinder asBinder() {
            return this.zzrp;
        }

        @Override
        public void send(Message message) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.iid.IMessengerCompat");
                if (message != null) {
                    parcel.writeInt(1);
                    message.writeToParcel(parcel, 0);
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
