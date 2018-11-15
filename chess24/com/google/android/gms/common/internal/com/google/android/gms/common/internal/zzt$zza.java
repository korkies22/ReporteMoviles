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
package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.dynamic.zzd;

public static abstract class zzt.zza
extends Binder
implements zzt {
    public zzt.zza() {
        this.attachInterface((IInterface)this, "com.google.android.gms.common.internal.ICertData");
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
        if (n != 1598968902) {
            switch (n) {
                default: {
                    return super.onTransact(n, object, parcel, n2);
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.common.internal.ICertData");
                    n = this.zzuC();
                    parcel.writeNoException();
                    parcel.writeInt(n);
                    return true;
                }
                case 1: 
            }
            object.enforceInterface("com.google.android.gms.common.internal.ICertData");
            object = this.zzuB();
            parcel.writeNoException();
            object = object != null ? object.asBinder() : null;
            parcel.writeStrongBinder((IBinder)object);
            return true;
        }
        parcel.writeString("com.google.android.gms.common.internal.ICertData");
        return true;
    }
}
