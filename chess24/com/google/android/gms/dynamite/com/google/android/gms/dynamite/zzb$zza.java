/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.dynamite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamite.zzb;

public static abstract class zzb.zza
extends Binder
implements zzb {
    public static zzb zzcf(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2");
        if (iInterface != null && iInterface instanceof zzb) {
            return (zzb)iInterface;
        }
        return new zza(iBinder);
    }

    public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
        if (n != 1) {
            if (n != 1598968902) {
                return super.onTransact(n, object, parcel, n2);
            }
            parcel.writeString("com.google.android.gms.dynamite.IDynamiteLoaderV2");
            return true;
        }
        object.enforceInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2");
        object = this.zza(zzd.zza.zzcd(object.readStrongBinder()), object.readString(), object.createByteArray());
        parcel.writeNoException();
        object = object != null ? object.asBinder() : null;
        parcel.writeStrongBinder((IBinder)object);
        return true;
    }

    private static class zza
    implements zzb {
        private IBinder zzrp;

        zza(IBinder iBinder) {
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

}
