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
package android.support.customtabs;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.customtabs.ICustomTabsCallback;

public interface IPostMessageService
extends IInterface {
    public void onMessageChannelReady(ICustomTabsCallback var1, Bundle var2) throws RemoteException;

    public void onPostMessage(ICustomTabsCallback var1, String var2, Bundle var3) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IPostMessageService {
        private static final String DESCRIPTOR = "android.support.customtabs.IPostMessageService";
        static final int TRANSACTION_onMessageChannelReady = 2;
        static final int TRANSACTION_onPostMessage = 3;

        public Stub() {
            this.attachInterface((IInterface)this, DESCRIPTOR);
        }

        public static IPostMessageService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPostMessageService) {
                return (IPostMessageService)iInterface;
            }
            return new Stub$Proxy(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1598968902) {
                ICustomTabsCallback iCustomTabsCallback = null;
                ICustomTabsCallback iCustomTabsCallback2 = null;
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        iCustomTabsCallback = ICustomTabsCallback.Stub.asInterface(parcel.readStrongBinder());
                        String string = parcel.readString();
                        if (parcel.readInt() != 0) {
                            iCustomTabsCallback2 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                        }
                        this.onPostMessage(iCustomTabsCallback, string, (Bundle)iCustomTabsCallback2);
                        parcel2.writeNoException();
                        return true;
                    }
                    case 2: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                ICustomTabsCallback iCustomTabsCallback3 = ICustomTabsCallback.Stub.asInterface(parcel.readStrongBinder());
                iCustomTabsCallback2 = iCustomTabsCallback;
                if (parcel.readInt() != 0) {
                    iCustomTabsCallback2 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                }
                this.onMessageChannelReady(iCustomTabsCallback3, (Bundle)iCustomTabsCallback2);
                parcel2.writeNoException();
                return true;
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }
    }

    private static class Stub$Proxy
    implements IPostMessageService {
        private IBinder mRemote;

        Stub$Proxy(IBinder iBinder) {
            this.mRemote = iBinder;
        }

        public IBinder asBinder() {
            return this.mRemote;
        }

        public String getInterfaceDescriptor() {
            return "android.support.customtabs.IPostMessageService";
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onMessageChannelReady(ICustomTabsCallback iCustomTabsCallback, Bundle bundle) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("android.support.customtabs.IPostMessageService");
                iCustomTabsCallback = iCustomTabsCallback != null ? iCustomTabsCallback.asBinder() : null;
                parcel.writeStrongBinder((IBinder)iCustomTabsCallback);
                if (bundle != null) {
                    parcel.writeInt(1);
                    bundle.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.mRemote.transact(2, parcel, parcel2, 0);
                parcel2.readException();
                return;
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
        public void onPostMessage(ICustomTabsCallback iCustomTabsCallback, String string, Bundle bundle) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("android.support.customtabs.IPostMessageService");
                iCustomTabsCallback = iCustomTabsCallback != null ? iCustomTabsCallback.asBinder() : null;
                parcel.writeStrongBinder((IBinder)iCustomTabsCallback);
                parcel.writeString(string);
                if (bundle != null) {
                    parcel.writeInt(1);
                    bundle.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.mRemote.transact(3, parcel, parcel2, 0);
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
