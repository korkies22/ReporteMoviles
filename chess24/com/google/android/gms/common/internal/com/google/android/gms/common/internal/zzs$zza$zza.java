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
import com.google.android.gms.common.internal.zzs;

private static class zzs.zza.zza
implements zzs {
    private IBinder zzrp;

    zzs.zza.zza(IBinder iBinder) {
        this.zzrp = iBinder;
    }

    public IBinder asBinder() {
        return this.zzrp;
    }

    @Override
    public void cancel() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.google.android.gms.common.internal.ICancelToken");
            this.zzrp.transact(2, parcel, null, 1);
            return;
        }
        finally {
            parcel.recycle();
        }
    }
}
