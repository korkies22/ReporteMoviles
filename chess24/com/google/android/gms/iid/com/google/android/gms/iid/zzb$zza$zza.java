/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Message
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.iid;

import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.iid.zzb;

private static class zzb.zza.zza
implements zzb {
    private IBinder zzrp;

    zzb.zza.zza(IBinder iBinder) {
        this.zzrp = iBinder;
    }

    public IBinder asBinder() {
        return this.zzrp;
    }

    @Override
    public void send(Message message) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.google.android.gms.iid.IMessengerCompat");
            if (message != null) {
                parcel.writeInt(1);
                message.writeToParcel(parcel, 0);
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
