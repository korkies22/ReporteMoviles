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
package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.zzsf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface zzsu
extends IInterface {
    public String getVersion() throws RemoteException;

    public void zza(Map var1, long var2, String var4, List<zzsf> var5) throws RemoteException;

    public void zznj() throws RemoteException;

    public static abstract class zza
    extends Binder
    implements zzsu {
        public static zzsu zzam(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.analytics.internal.IAnalyticsService");
            if (iInterface != null && iInterface instanceof zzsu) {
                return (zzsu)iInterface;
            }
            return new zza$zza(iBinder);
        }

        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, object, parcel, n2);
                    }
                    case 3: {
                        object.enforceInterface("com.google.android.gms.analytics.internal.IAnalyticsService");
                        object = this.getVersion();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 2: {
                        object.enforceInterface("com.google.android.gms.analytics.internal.IAnalyticsService");
                        this.zznj();
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                object.enforceInterface("com.google.android.gms.analytics.internal.IAnalyticsService");
                this.zza((Map)object.readHashMap(this.getClass().getClassLoader()), object.readLong(), object.readString(), (List)object.createTypedArrayList(zzsf.CREATOR));
                parcel.writeNoException();
                return true;
            }
            parcel.writeString("com.google.android.gms.analytics.internal.IAnalyticsService");
            return true;
        }
    }

    private static class zza$zza
    implements zzsu {
        private IBinder zzrp;

        zza$zza(IBinder iBinder) {
            this.zzrp = iBinder;
        }

        public IBinder asBinder() {
            return this.zzrp;
        }

        @Override
        public String getVersion() throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.analytics.internal.IAnalyticsService");
                this.zzrp.transact(3, parcel, parcel2, 0);
                parcel2.readException();
                String string = parcel2.readString();
                return string;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        @Override
        public void zza(Map map, long l, String string, List<zzsf> list) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.analytics.internal.IAnalyticsService");
                parcel.writeMap(map);
                parcel.writeLong(l);
                parcel.writeString(string);
                parcel.writeTypedList(list);
                this.zzrp.transact(1, parcel, parcel2, 0);
                parcel2.readException();
                return;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        @Override
        public void zznj() throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.analytics.internal.IAnalyticsService");
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

}
