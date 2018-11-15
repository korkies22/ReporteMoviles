/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamite.zzb;

private static class zzb.zza.zza
implements zzb {
    private IBinder zzrp;

    zzb.zza.zza(IBinder iBinder) {
        this.zzrp = iBinder;
    }

    public IBinder asBinder() {
        return this.zzrp;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public zzd zza(zzd zzd2, String string, byte[] arrby) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.google.android.gms.dynamite.IDynamiteLoaderV2");
            zzd2 = zzd2 != null ? zzd2.asBinder() : null;
            parcel.writeStrongBinder((IBinder)zzd2);
            parcel.writeString(string);
            parcel.writeByteArray(arrby);
            this.zzrp.transact(1, parcel, parcel2, 0);
            parcel2.readException();
            zzd2 = zzd.zza.zzcd(parcel2.readStrongBinder());
            return zzd2;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }
}
