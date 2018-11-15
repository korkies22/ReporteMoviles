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
import com.google.android.gms.common.internal.zzah;
import com.google.android.gms.common.internal.zzy;
import com.google.android.gms.dynamic.zzd;

private static class zzy.zza.zza
implements zzy {
    private IBinder zzrp;

    zzy.zza.zza(IBinder iBinder) {
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
    public zzd zza(zzd zzd2, int n, int n2) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.google.android.gms.common.internal.ISignInButtonCreator");
            zzd2 = zzd2 != null ? zzd2.asBinder() : null;
            parcel.writeStrongBinder((IBinder)zzd2);
            parcel.writeInt(n);
            parcel.writeInt(n2);
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public zzd zza(zzd zzd2, zzah zzah2) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.google.android.gms.common.internal.ISignInButtonCreator");
            zzd2 = zzd2 != null ? zzd2.asBinder() : null;
            parcel.writeStrongBinder((IBinder)zzd2);
            if (zzah2 != null) {
                parcel.writeInt(1);
                zzah2.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.zzrp.transact(2, parcel, parcel2, 0);
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
