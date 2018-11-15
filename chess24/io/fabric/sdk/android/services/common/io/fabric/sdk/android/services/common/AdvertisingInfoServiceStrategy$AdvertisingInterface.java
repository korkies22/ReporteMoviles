/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package io.fabric.sdk.android.services.common;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.AdvertisingInfoServiceStrategy;

private static final class AdvertisingInfoServiceStrategy.AdvertisingInterface
implements IInterface {
    public static final String ADVERTISING_ID_SERVICE_INTERFACE_TOKEN = "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService";
    private static final int AD_TRANSACTION_CODE_ID = 1;
    private static final int AD_TRANSACTION_CODE_LIMIT_AD_TRACKING = 2;
    private static final int FLAGS_NONE = 0;
    private final IBinder binder;

    public AdvertisingInfoServiceStrategy.AdvertisingInterface(IBinder iBinder) {
        this.binder = iBinder;
    }

    public IBinder asBinder() {
        return this.binder;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public String getId() throws RemoteException {
        String string;
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(ADVERTISING_ID_SERVICE_INTERFACE_TOKEN);
            this.binder.transact(1, parcel, parcel2, 0);
            parcel2.readException();
            string = parcel2.readString();
        }
        catch (Throwable throwable) {}
        parcel2.recycle();
        parcel.recycle();
        return string;
        parcel2.recycle();
        parcel.recycle();
        throw throwable;
        catch (Exception exception) {}
        {
            Fabric.getLogger().d("Fabric", "Could not get parcel from Google Play Service to capture AdvertisingId");
        }
        parcel2.recycle();
        parcel.recycle();
        return null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean isLimitAdTrackingEnabled() throws RemoteException {
        boolean bl;
        Parcel parcel;
        Parcel parcel2;
        block5 : {
            parcel = Parcel.obtain();
            parcel2 = Parcel.obtain();
            bl = false;
            try {
                parcel.writeInterfaceToken(ADVERTISING_ID_SERVICE_INTERFACE_TOKEN);
                parcel.writeInt(1);
                this.binder.transact(2, parcel, parcel2, 0);
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                break block5;
            }
            catch (Throwable throwable) {}
            parcel2.recycle();
            parcel.recycle();
            throw throwable;
            catch (Exception exception) {}
            Fabric.getLogger().d("Fabric", "Could not get parcel from Google Play Service to capture Advertising limitAdTracking");
        }
        parcel2.recycle();
        parcel.recycle();
        return bl;
    }
}
