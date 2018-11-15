/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaat;

private static class zzaat.zza.zza
implements zzaat {
    private IBinder zzrp;

    zzaat.zza.zza(IBinder iBinder) {
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
