/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzah;
import com.google.android.gms.dynamic.zzd;

public interface zzy
extends IInterface {
    public zzd zza(zzd var1, int var2, int var3) throws RemoteException;

    public zzd zza(zzd var1, zzah var2) throws RemoteException;

    public static abstract class zza
    extends Binder
    implements zzy {
        public static zzy zzbx(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
            if (iInterface != null && iInterface instanceof zzy) {
                return (zzy)iInterface;
            }
            return new zza$zza(iBinder);
        }

        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                zzd zzd2 = null;
                zzd zzd3 = null;
                switch (n) {
                    default: {
                        return super.onTransact(n, object, parcel, n2);
                    }
                    case 2: {
                        object.enforceInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
                        zzd2 = zzd.zza.zzcd(object.readStrongBinder());
                        object = object.readInt() != 0 ? (zzah)zzah.CREATOR.createFromParcel(object) : null;
                        zzd2 = this.zza(zzd2, (zzah)object);
                        parcel.writeNoException();
                        object = zzd3;
                        if (zzd2 != null) {
                            object = zzd2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 1: 
                }
                object.enforceInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
                zzd3 = this.zza(zzd.zza.zzcd(object.readStrongBinder()), object.readInt(), object.readInt());
                parcel.writeNoException();
                object = zzd2;
                if (zzd3 != null) {
                    object = zzd3.asBinder();
                }
                parcel.writeStrongBinder((IBinder)object);
                return true;
            }
            parcel.writeString("com.google.android.gms.common.internal.ISignInButtonCreator");
            return true;
        }
    }

    private static class zza$zza
    implements zzy {
        private IBinder zzrp;

        zza$zza(IBinder iBinder) {
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

}
