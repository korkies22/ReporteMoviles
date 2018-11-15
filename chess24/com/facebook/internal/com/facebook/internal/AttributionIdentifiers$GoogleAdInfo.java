/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.facebook.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.facebook.internal.AttributionIdentifiers;

private static final class AttributionIdentifiers.GoogleAdInfo
implements IInterface {
    private static final int FIRST_TRANSACTION_CODE = 1;
    private static final int SECOND_TRANSACTION_CODE = 2;
    private IBinder binder;

    AttributionIdentifiers.GoogleAdInfo(IBinder iBinder) {
        this.binder = iBinder;
    }

    public IBinder asBinder() {
        return this.binder;
    }

    public String getAdvertiserId() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
            this.binder.transact(1, parcel, parcel2, 0);
            parcel2.readException();
            String string = parcel2.readString();
            return string;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    public boolean isTrackingLimited() throws RemoteException {
        Parcel parcel;
        boolean bl;
        Parcel parcel2;
        block3 : {
            parcel = Parcel.obtain();
            parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                bl = true;
            }
            catch (Throwable throwable) {
                parcel2.recycle();
                parcel.recycle();
                throw throwable;
            }
            parcel.writeInt(1);
            this.binder.transact(2, parcel, parcel2, 0);
            parcel2.readException();
            int n = parcel2.readInt();
            if (n != 0) break block3;
            bl = false;
        }
        parcel2.recycle();
        parcel.recycle();
        return bl;
    }
}
