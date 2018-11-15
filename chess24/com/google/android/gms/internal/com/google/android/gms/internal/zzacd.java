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
package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzacc;

public interface zzacd
extends IInterface {
    public void zza(zzacc var1) throws RemoteException;

    public static abstract class zza
    extends Binder
    implements zzacd {
        public static zzacd zzbB(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.service.ICommonService");
            if (iInterface != null && iInterface instanceof zzacd) {
                return (zzacd)iInterface;
            }
            return new zza$zza(iBinder);
        }

        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                parcel2.writeString("com.google.android.gms.common.internal.service.ICommonService");
                return true;
            }
            parcel.enforceInterface("com.google.android.gms.common.internal.service.ICommonService");
            this.zza(zzacc.zza.zzbA(parcel.readStrongBinder()));
            return true;
        }
    }

    private static class zza$zza
    implements zzacd {
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
        public void zza(zzacc zzacc2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.common.internal.service.ICommonService");
                zzacc2 = zzacc2 != null ? zzacc2.asBinder() : null;
                parcel.writeStrongBinder((IBinder)zzacc2);
                this.zzrp.transact(1, parcel, null, 1);
                return;
            }
            finally {
                parcel.recycle();
            }
        }
    }

}
