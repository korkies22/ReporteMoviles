/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package android.support.v4.app;

import android.app.Notification;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.app.INotificationSideChannel;

public static abstract class INotificationSideChannel.Stub
extends Binder
implements INotificationSideChannel {
    private static final String DESCRIPTOR = "android.support.v4.app.INotificationSideChannel";
    static final int TRANSACTION_cancel = 2;
    static final int TRANSACTION_cancelAll = 3;
    static final int TRANSACTION_notify = 1;

    public INotificationSideChannel.Stub() {
        this.attachInterface((IInterface)this, DESCRIPTOR);
    }

    public static INotificationSideChannel asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
        if (iInterface != null && iInterface instanceof INotificationSideChannel) {
            return (INotificationSideChannel)iInterface;
        }
        return new Proxy(iBinder);
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
        if (n != 1598968902) {
            switch (n) {
                default: {
                    return super.onTransact(n, object, object2, n2);
                }
                case 3: {
                    object.enforceInterface(DESCRIPTOR);
                    this.cancelAll(object.readString());
                    return true;
                }
                case 2: {
                    object.enforceInterface(DESCRIPTOR);
                    this.cancel(object.readString(), object.readInt(), object.readString());
                    return true;
                }
                case 1: 
            }
            object.enforceInterface(DESCRIPTOR);
            object2 = object.readString();
            n = object.readInt();
            String string = object.readString();
            object = object.readInt() != 0 ? (Notification)Notification.CREATOR.createFromParcel(object) : null;
            this.notify((String)object2, n, string, (Notification)object);
            return true;
        }
        object2.writeString(DESCRIPTOR);
        return true;
    }

    private static class Proxy
    implements INotificationSideChannel {
        private IBinder mRemote;

        Proxy(IBinder iBinder) {
            this.mRemote = iBinder;
        }

        public IBinder asBinder() {
            return this.mRemote;
        }

        @Override
        public void cancel(String string, int n, String string2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(INotificationSideChannel.Stub.DESCRIPTOR);
                parcel.writeString(string);
                parcel.writeInt(n);
                parcel.writeString(string2);
                this.mRemote.transact(2, parcel, null, 1);
                return;
            }
            finally {
                parcel.recycle();
            }
        }

        @Override
        public void cancelAll(String string) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(INotificationSideChannel.Stub.DESCRIPTOR);
                parcel.writeString(string);
                this.mRemote.transact(3, parcel, null, 1);
                return;
            }
            finally {
                parcel.recycle();
            }
        }

        public String getInterfaceDescriptor() {
            return INotificationSideChannel.Stub.DESCRIPTOR;
        }

        @Override
        public void notify(String string, int n, String string2, Notification notification) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(INotificationSideChannel.Stub.DESCRIPTOR);
                parcel.writeString(string);
                parcel.writeInt(n);
                parcel.writeString(string2);
                if (notification != null) {
                    parcel.writeInt(1);
                    notification.writeToParcel(parcel, 0);
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
