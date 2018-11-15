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
package android.support.v4.os;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.os.IResultReceiver;

public static abstract class IResultReceiver.Stub
extends Binder
implements IResultReceiver {
    private static final String DESCRIPTOR = "android.support.v4.os.IResultReceiver";
    static final int TRANSACTION_send = 1;

    public IResultReceiver.Stub() {
        this.attachInterface((IInterface)this, DESCRIPTOR);
    }

    public static IResultReceiver asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
        if (iInterface != null && iInterface instanceof IResultReceiver) {
            return (IResultReceiver)iInterface;
        }
        return new Proxy(iBinder);
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
        if (n != 1) {
            if (n != 1598968902) {
                return super.onTransact(n, object, parcel, n2);
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }
        object.enforceInterface(DESCRIPTOR);
        n = object.readInt();
        object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
        this.send(n, (Bundle)object);
        return true;
    }

    private static class Proxy
    implements IResultReceiver {
        private IBinder mRemote;

        Proxy(IBinder iBinder) {
            this.mRemote = iBinder;
        }

        public IBinder asBinder() {
            return this.mRemote;
        }

        public String getInterfaceDescriptor() {
            return IResultReceiver.Stub.DESCRIPTOR;
        }

        @Override
        public void send(int n, Bundle bundle) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IResultReceiver.Stub.DESCRIPTOR);
                parcel.writeInt(n);
                if (bundle != null) {
                    parcel.writeInt(1);
                    bundle.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.mRemote.transact(1, parcel, null, 1);
                return;
            }
            finally {
                parcel.recycle();
            }
        }
    }

}
