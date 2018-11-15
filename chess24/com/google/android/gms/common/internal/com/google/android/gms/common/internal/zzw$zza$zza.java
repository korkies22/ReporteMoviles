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
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.dynamic.zzd;

private static class zzw.zza.zza
implements zzw {
    private IBinder zzrp;

    zzw.zza.zza(IBinder iBinder) {
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
    public boolean zzd(String string, zzd zzd2) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGoogleCertificatesApi");
            parcel.writeString(string);
            string = zzd2 != null ? zzd2.asBinder() : null;
            parcel.writeStrongBinder((IBinder)string);
            string = this.zzrp;
            boolean bl = false;
            string.transact(3, parcel, parcel2, 0);
            parcel2.readException();
            int n = parcel2.readInt();
            if (n != 0) {
                bl = true;
            }
            return bl;
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
    public boolean zze(String string, zzd zzd2) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGoogleCertificatesApi");
            parcel.writeString(string);
            string = zzd2 != null ? zzd2.asBinder() : null;
            parcel.writeStrongBinder((IBinder)string);
            string = this.zzrp;
            boolean bl = false;
            string.transact(4, parcel, parcel2, 0);
            parcel2.readException();
            int n = parcel2.readInt();
            if (n != 0) {
                bl = true;
            }
            return bl;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public zzd zzxv() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGoogleCertificatesApi");
            this.zzrp.transact(1, parcel, parcel2, 0);
            parcel2.readException();
            zzd zzd2 = zzd.zza.zzcd(parcel2.readStrongBinder());
            return zzd2;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public zzd zzxw() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGoogleCertificatesApi");
            this.zzrp.transact(2, parcel, parcel2, 0);
            parcel2.readException();
            zzd zzd2 = zzd.zza.zzcd(parcel2.readStrongBinder());
            return zzd2;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }
}
