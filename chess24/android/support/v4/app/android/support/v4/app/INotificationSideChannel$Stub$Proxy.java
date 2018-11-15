/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package android.support.v4.app;

import android.app.Notification;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.app.INotificationSideChannel;

private static class INotificationSideChannel.Stub.Proxy
implements INotificationSideChannel {
    private IBinder mRemote;

    INotificationSideChannel.Stub.Proxy(IBinder iBinder) {
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
