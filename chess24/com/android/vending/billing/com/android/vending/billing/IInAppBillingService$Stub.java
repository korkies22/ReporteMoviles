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
package com.android.vending.billing;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.vending.billing.IInAppBillingService;

public static abstract class IInAppBillingService.Stub
extends Binder
implements IInAppBillingService {
    private static final String DESCRIPTOR = "com.android.vending.billing.IInAppBillingService";
    static final int TRANSACTION_consumePurchase = 5;
    static final int TRANSACTION_getBuyIntent = 3;
    static final int TRANSACTION_getPurchases = 4;
    static final int TRANSACTION_getSkuDetails = 2;
    static final int TRANSACTION_isBillingSupported = 1;

    public IInAppBillingService.Stub() {
        this.attachInterface((IInterface)this, DESCRIPTOR);
    }

    public static IInAppBillingService asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
        if (iInterface != null && iInterface instanceof IInAppBillingService) {
            return (IInAppBillingService)iInterface;
        }
        return new Proxy(iBinder);
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
        if (n != 1598968902) {
            switch (n) {
                default: {
                    return super.onTransact(n, object, parcel, n2);
                }
                case 5: {
                    object.enforceInterface(DESCRIPTOR);
                    n = this.consumePurchase(object.readInt(), object.readString(), object.readString());
                    parcel.writeNoException();
                    parcel.writeInt(n);
                    return true;
                }
                case 4: {
                    object.enforceInterface(DESCRIPTOR);
                    object = this.getPurchases(object.readInt(), object.readString(), object.readString(), object.readString());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 3: {
                    object.enforceInterface(DESCRIPTOR);
                    object = this.getBuyIntent(object.readInt(), object.readString(), object.readString(), object.readString(), object.readString());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 2: {
                    object.enforceInterface(DESCRIPTOR);
                    n = object.readInt();
                    String string = object.readString();
                    String string2 = object.readString();
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
                    object = this.getSkuDetails(n, string, string2, (Bundle)object);
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 1: 
            }
            object.enforceInterface(DESCRIPTOR);
            n = this.isBillingSupported(object.readInt(), object.readString(), object.readString());
            parcel.writeNoException();
            parcel.writeInt(n);
            return true;
        }
        parcel.writeString(DESCRIPTOR);
        return true;
    }

    private static class Proxy
    implements IInAppBillingService {
        private IBinder mRemote;

        Proxy(IBinder iBinder) {
            this.mRemote = iBinder;
        }

        public IBinder asBinder() {
            return this.mRemote;
        }

        @Override
        public int consumePurchase(int n, String string, String string2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IInAppBillingService.Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeString(string);
                parcel.writeString(string2);
                this.mRemote.transact(5, parcel, parcel2, 0);
                parcel2.readException();
                n = parcel2.readInt();
                return n;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        @Override
        public Bundle getBuyIntent(int n, String string, String string2, String string3, String string4) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IInAppBillingService.Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeString(string);
                parcel.writeString(string2);
                parcel.writeString(string3);
                parcel.writeString(string4);
                this.mRemote.transact(3, parcel, parcel2, 0);
                parcel2.readException();
                string = parcel2.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel2) : null;
            }
            catch (Throwable throwable) {
                parcel2.recycle();
                parcel.recycle();
                throw throwable;
            }
            parcel2.recycle();
            parcel.recycle();
            return string;
        }

        public String getInterfaceDescriptor() {
            return IInAppBillingService.Stub.DESCRIPTOR;
        }

        @Override
        public Bundle getPurchases(int n, String string, String string2, String string3) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IInAppBillingService.Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeString(string);
                parcel.writeString(string2);
                parcel.writeString(string3);
                this.mRemote.transact(4, parcel, parcel2, 0);
                parcel2.readException();
                string = parcel2.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel2) : null;
            }
            catch (Throwable throwable) {
                parcel2.recycle();
                parcel.recycle();
                throw throwable;
            }
            parcel2.recycle();
            parcel.recycle();
            return string;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Bundle getSkuDetails(int n, String string, String string2, Bundle bundle) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                void var4_8;
                void var3_7;
                void var2_5;
                parcel.writeInterfaceToken(IInAppBillingService.Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeString(string);
                parcel.writeString((String)var3_7);
                if (var4_8 != null) {
                    parcel.writeInt(1);
                    var4_8.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.mRemote.transact(2, parcel, parcel2, 0);
                parcel2.readException();
                if (parcel2.readInt() != 0) {
                    Bundle bundle2 = (Bundle)Bundle.CREATOR.createFromParcel(parcel2);
                    return var2_5;
                } else {
                    Object var2_4 = null;
                }
                return var2_5;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        @Override
        public int isBillingSupported(int n, String string, String string2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IInAppBillingService.Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeString(string);
                parcel.writeString(string2);
                this.mRemote.transact(1, parcel, parcel2, 0);
                parcel2.readException();
                n = parcel2.readInt();
                return n;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }
    }

}
