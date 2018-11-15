/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzad;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzaxs;
import com.google.android.gms.internal.zzaxu;
import com.google.android.gms.internal.zzaxv;
import com.google.android.gms.internal.zzaxw;
import com.google.android.gms.internal.zzaxz;

public static abstract class zzaxv.zza
extends Binder
implements zzaxv {
    public static zzaxv zzeY(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInService");
        if (iInterface != null && iInterface instanceof zzaxv) {
            return (zzaxv)iInterface;
        }
        return new zza(iBinder);
    }

    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        if (n != 1598968902) {
            boolean bl = false;
            boolean bl2 = false;
            zzr zzr2 = null;
            Object var9_8 = null;
            zzr zzr3 = null;
            zzr zzr4 = null;
            zzr zzr5 = null;
            Object object = null;
            switch (n) {
                default: {
                    switch (n) {
                        default: {
                            return super.onTransact(n, parcel, parcel2, n2);
                        }
                        case 12: {
                            parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                            if (parcel.readInt() != 0) {
                                object = (zzaxz)zzaxz.CREATOR.createFromParcel(parcel);
                            }
                            this.zza((zzaxz)object, zzaxu.zza.zzeX(parcel.readStrongBinder()));
                            parcel2.writeNoException();
                            return true;
                        }
                        case 11: {
                            parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                            this.zzb(zzaxu.zza.zzeX(parcel.readStrongBinder()));
                            parcel2.writeNoException();
                            return true;
                        }
                        case 10: {
                            parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                            object = zzr2;
                            if (parcel.readInt() != 0) {
                                object = (zzaxw)zzaxw.CREATOR.createFromParcel(parcel);
                            }
                            this.zza((zzaxw)object, zzaxu.zza.zzeX(parcel.readStrongBinder()));
                            parcel2.writeNoException();
                            return true;
                        }
                        case 9: {
                            parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                            object = zzr.zza.zzbr(parcel.readStrongBinder());
                            n = parcel.readInt();
                            if (parcel.readInt() != 0) {
                                bl2 = true;
                            }
                            this.zza((zzr)object, n, bl2);
                            parcel2.writeNoException();
                            return true;
                        }
                        case 8: {
                            parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                            n = parcel.readInt();
                            object = var9_8;
                            if (parcel.readInt() != 0) {
                                object = (Account)Account.CREATOR.createFromParcel(parcel);
                            }
                            this.zza(n, (Account)object, zzaxu.zza.zzeX(parcel.readStrongBinder()));
                            parcel2.writeNoException();
                            return true;
                        }
                        case 7: 
                    }
                    parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    this.zzmK(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 5: {
                    parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    object = zzr3;
                    if (parcel.readInt() != 0) {
                        object = (zzad)zzad.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((zzad)object, zzx.zza.zzbw(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    bl2 = bl;
                    if (parcel.readInt() != 0) {
                        bl2 = true;
                    }
                    this.zzaK(bl2);
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
                    object = zzr4;
                    if (parcel.readInt() != 0) {
                        object = (zzaxs)zzaxs.CREATOR.createFromParcel(parcel);
                    }
                    this.zza((zzaxs)object);
                    parcel2.writeNoException();
                    return true;
                }
                case 2: 
            }
            parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
            object = zzr5;
            if (parcel.readInt() != 0) {
                object = (zzd)zzd.CREATOR.createFromParcel(parcel);
            }
            this.zza((zzd)object, zzaxu.zza.zzeX(parcel.readStrongBinder()));
            parcel2.writeNoException();
            return true;
        }
        parcel2.writeString("com.google.android.gms.signin.internal.ISignInService");
        return true;
    }

    private static class zza
    implements zzaxv {
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
        public void zza(int n, Account object, zzaxu zzaxu2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                parcel.writeInt(n);
                if (object != null) {
                    parcel.writeInt(1);
                    object.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                object = zzaxu2 != null ? zzaxu2.asBinder() : null;
                parcel.writeStrongBinder((IBinder)object);
                this.zzrp.transact(8, parcel, parcel2, 0);
                parcel2.readException();
                return;
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
        public void zza(zzad zzad2, zzx zzx2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                if (zzad2 != null) {
                    parcel.writeInt(1);
                    zzad2.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                zzad2 = zzx2 != null ? zzx2.asBinder() : null;
                parcel.writeStrongBinder((IBinder)zzad2);
                this.zzrp.transact(5, parcel, parcel2, 0);
                parcel2.readException();
                return;
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
        public void zza(zzd zzd2, zzaxu zzaxu2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                if (zzd2 != null) {
                    parcel.writeInt(1);
                    zzd2.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                zzd2 = zzaxu2 != null ? zzaxu2.asBinder() : null;
                parcel.writeStrongBinder((IBinder)zzd2);
                this.zzrp.transact(2, parcel, parcel2, 0);
                parcel2.readException();
                return;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        @Override
        public void zza(zzr zzr2, int n, boolean bl) throws RemoteException {
            RuntimeException runtimeException;
            super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
            throw runtimeException;
        }

        @Override
        public void zza(zzaxs zzaxs2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                if (zzaxs2 != null) {
                    parcel.writeInt(1);
                    zzaxs2.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.zzrp.transact(3, parcel, parcel2, 0);
                parcel2.readException();
                return;
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
        public void zza(zzaxw zzaxw2, zzaxu zzaxu2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                if (zzaxw2 != null) {
                    parcel.writeInt(1);
                    zzaxw2.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                zzaxw2 = zzaxu2 != null ? zzaxu2.asBinder() : null;
                parcel.writeStrongBinder((IBinder)zzaxw2);
                this.zzrp.transact(10, parcel, parcel2, 0);
                parcel2.readException();
                return;
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
        public void zza(zzaxz zzaxz2, zzaxu zzaxu2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                if (zzaxz2 != null) {
                    parcel.writeInt(1);
                    zzaxz2.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                zzaxz2 = zzaxu2 != null ? zzaxu2.asBinder() : null;
                parcel.writeStrongBinder((IBinder)zzaxz2);
                this.zzrp.transact(12, parcel, parcel2, 0);
                parcel2.readException();
                return;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        @Override
        public void zzaK(boolean bl) throws RemoteException {
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
        public void zzb(zzaxu zzaxu2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                zzaxu2 = zzaxu2 != null ? zzaxu2.asBinder() : null;
                parcel.writeStrongBinder((IBinder)zzaxu2);
                this.zzrp.transact(11, parcel, parcel2, 0);
                parcel2.readException();
                return;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        @Override
        public void zzmK(int n) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
                parcel.writeInt(n);
                this.zzrp.transact(7, parcel, parcel2, 0);
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
