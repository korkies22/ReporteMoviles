/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package android.support.customtabs;

import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.customtabs.ICustomTabsCallback;
import android.support.customtabs.ICustomTabsService;
import java.util.List;

private static class ICustomTabsService.Stub.Proxy
implements ICustomTabsService {
    private IBinder mRemote;

    ICustomTabsService.Stub.Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    public IBinder asBinder() {
        return this.mRemote;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Bundle extraCommand(String string, Bundle bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            void var2_6;
            void var1_4;
            parcel.writeInterfaceToken(ICustomTabsService.Stub.DESCRIPTOR);
            parcel.writeString(string);
            if (var2_6 != null) {
                parcel.writeInt(1);
                var2_6.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(5, parcel, parcel2, 0);
            parcel2.readException();
            if (parcel2.readInt() != 0) {
                Bundle bundle2 = (Bundle)Bundle.CREATOR.createFromParcel(parcel2);
                return var1_4;
            } else {
                Object var1_3 = null;
            }
            return var1_4;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    public String getInterfaceDescriptor() {
        return ICustomTabsService.Stub.DESCRIPTOR;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean mayLaunchUrl(ICustomTabsCallback iCustomTabsCallback, Uri uri, Bundle bundle, List<Bundle> list) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(ICustomTabsService.Stub.DESCRIPTOR);
            iCustomTabsCallback = iCustomTabsCallback != null ? iCustomTabsCallback.asBinder() : null;
            parcel.writeStrongBinder((IBinder)iCustomTabsCallback);
            boolean bl = true;
            if (uri != null) {
                parcel.writeInt(1);
                uri.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            if (bundle != null) {
                parcel.writeInt(1);
                bundle.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeTypedList(list);
            this.mRemote.transact(4, parcel, parcel2, 0);
            parcel2.readException();
            int n = parcel2.readInt();
            if (n != 0) {
                return bl;
            } else {
                bl = false;
            }
            return bl;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean newSession(ICustomTabsCallback iCustomTabsCallback) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(ICustomTabsService.Stub.DESCRIPTOR);
            iCustomTabsCallback = iCustomTabsCallback != null ? iCustomTabsCallback.asBinder() : null;
            parcel.writeStrongBinder((IBinder)iCustomTabsCallback);
            iCustomTabsCallback = this.mRemote;
            boolean bl = false;
            iCustomTabsCallback.transact(3, parcel, parcel2, 0);
            parcel2.readException();
            int n = parcel2.readInt();
            if (n != 0) {
                bl = true;
            }
            return bl;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int postMessage(ICustomTabsCallback iCustomTabsCallback, String string, Bundle bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(ICustomTabsService.Stub.DESCRIPTOR);
            iCustomTabsCallback = iCustomTabsCallback != null ? iCustomTabsCallback.asBinder() : null;
            parcel.writeStrongBinder((IBinder)iCustomTabsCallback);
            parcel.writeString(string);
            if (bundle != null) {
                parcel.writeInt(1);
                bundle.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(8, parcel, parcel2, 0);
            parcel2.readException();
            int n = parcel2.readInt();
            return n;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean requestPostMessageChannel(ICustomTabsCallback iCustomTabsCallback, Uri uri) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(ICustomTabsService.Stub.DESCRIPTOR);
            iCustomTabsCallback = iCustomTabsCallback != null ? iCustomTabsCallback.asBinder() : null;
            parcel.writeStrongBinder((IBinder)iCustomTabsCallback);
            boolean bl = true;
            if (uri != null) {
                parcel.writeInt(1);
                uri.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(7, parcel, parcel2, 0);
            parcel2.readException();
            int n = parcel2.readInt();
            if (n != 0) {
                return bl;
            } else {
                bl = false;
            }
            return bl;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean updateVisuals(ICustomTabsCallback iCustomTabsCallback, Bundle bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(ICustomTabsService.Stub.DESCRIPTOR);
            iCustomTabsCallback = iCustomTabsCallback != null ? iCustomTabsCallback.asBinder() : null;
            parcel.writeStrongBinder((IBinder)iCustomTabsCallback);
            boolean bl = true;
            if (bundle != null) {
                parcel.writeInt(1);
                bundle.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(6, parcel, parcel2, 0);
            parcel2.readException();
            int n = parcel2.readInt();
            if (n != 0) {
                return bl;
            } else {
                bl = false;
            }
            return bl;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean validateRelationship(ICustomTabsCallback iCustomTabsCallback, int n, Uri uri, Bundle bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(ICustomTabsService.Stub.DESCRIPTOR);
            iCustomTabsCallback = iCustomTabsCallback != null ? iCustomTabsCallback.asBinder() : null;
            parcel.writeStrongBinder((IBinder)iCustomTabsCallback);
            parcel.writeInt(n);
            boolean bl = true;
            if (uri != null) {
                parcel.writeInt(1);
                uri.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            if (bundle != null) {
                parcel.writeInt(1);
                bundle.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(9, parcel, parcel2, 0);
            parcel2.readException();
            n = parcel2.readInt();
            if (n != 0) {
                return bl;
            } else {
                bl = false;
            }
            return bl;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public boolean warmup(long l) throws RemoteException {
        Parcel parcel;
        boolean bl;
        Parcel parcel2;
        block3 : {
            IBinder iBinder;
            parcel2 = Parcel.obtain();
            parcel = Parcel.obtain();
            try {
                parcel2.writeInterfaceToken(ICustomTabsService.Stub.DESCRIPTOR);
                parcel2.writeLong(l);
                iBinder = this.mRemote;
                bl = false;
            }
            catch (Throwable throwable) {
                parcel.recycle();
                parcel2.recycle();
                throw throwable;
            }
            iBinder.transact(2, parcel2, parcel, 0);
            parcel.readException();
            int n = parcel.readInt();
            if (n == 0) break block3;
            bl = true;
        }
        parcel.recycle();
        parcel2.recycle();
        return bl;
    }
}
