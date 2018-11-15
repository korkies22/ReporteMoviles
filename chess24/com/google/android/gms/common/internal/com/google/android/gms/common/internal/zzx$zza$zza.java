/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzaf;
import com.google.android.gms.common.internal.zzx;

private static class zzx.zza.zza
implements zzx {
    private IBinder zzrp;

    zzx.zza.zza(IBinder iBinder) {
        this.zzrp = iBinder;
    }

    public IBinder asBinder() {
        return this.zzrp;
    }

    @Override
    public void zza(zzaf zzaf2) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.google.android.gms.common.internal.IResolveAccountCallbacks");
            if (zzaf2 != null) {
                parcel.writeInt(1);
                zzaf2.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.zzrp.transact(2, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }
}
