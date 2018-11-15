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

public static abstract class zza.zza
extends Binder
implements com.google.android.gms.dynamite.zza {
    public static com.google.android.gms.dynamite.zza zzce(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoader");
        if (iInterface != null && iInterface instanceof com.google.android.gms.dynamite.zza) {
            return (com.google.android.gms.dynamite.zza)iInterface;
        }
        return new zza(iBinder);
    }

    public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
        if (n != 1598968902) {
            switch (n) {
                default: {
                    return super.onTransact(n, object, parcel, n2);
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.dynamite.IDynamiteLoader");
                    zzd zzd2 = zzd.zza.zzcd(object.readStrongBinder());
                    String string = object.readString();
                    boolean bl = object.readInt() != 0;
                    n = this.zza(zzd2, string, bl);
                    parcel.writeNoException();
                    parcel.writeInt(n);
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.dynamite.IDynamiteLoader");
                    object = this.zza(zzd.zza.zzcd(object.readStrongBinder()), object.readString(), object.readInt());
                    parcel.writeNoException();
                    object = object != null ? object.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 1: 
            }
            object.enforceInterface("com.google.android.gms.dynamite.IDynamiteLoader");
            n = this.zzf(zzd.zza.zzcd(object.readStrongBinder()), object.readString());
            parcel.writeNoException();
            parcel.writeInt(n);
            return true;
        }
        parcel.writeString("com.google.android.gms.dynamite.IDynamiteLoader");
        return true;
    }

    private static class zza
    implements com.google.android.gms.dynamite.zza {
        private IBinder zzrp;

        zza(IBinder iBinder) {
            this.zzrp = iBinder;
        }

        public IBinder asBinder() {
            return this.zzrp;
        }

        @Override
        public int zza(zzd zzd2, String string, boolean bl) throws RemoteException {
            RuntimeException runtimeException;
            super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
            throw runtimeException;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public zzd zza(zzd zzd2, String string, int n) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.dynamite.IDynamiteLoader");
                zzd2 = zzd2 != null ? zzd2.asBinder() : null;
                parcel.writeStrongBinder((IBinder)zzd2);
                parcel.writeString(string);
                parcel.writeInt(n);
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

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int zzf(zzd zzd2, String string) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.dynamite.IDynamiteLoader");
                zzd2 = zzd2 != null ? zzd2.asBinder() : null;
                parcel.writeStrongBinder((IBinder)zzd2);
                parcel.writeString(string);
                this.zzrp.transact(1, parcel, parcel2, 0);
                parcel2.readException();
                int n = parcel2.readInt();
                return n;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }
    }

}
