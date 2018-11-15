/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;

public interface zzc
extends IInterface {
    public Bundle getArguments() throws RemoteException;

    public int getId() throws RemoteException;

    public boolean getRetainInstance() throws RemoteException;

    public String getTag() throws RemoteException;

    public int getTargetRequestCode() throws RemoteException;

    public boolean getUserVisibleHint() throws RemoteException;

    public zzd getView() throws RemoteException;

    public boolean isAdded() throws RemoteException;

    public boolean isDetached() throws RemoteException;

    public boolean isHidden() throws RemoteException;

    public boolean isInLayout() throws RemoteException;

    public boolean isRemoving() throws RemoteException;

    public boolean isResumed() throws RemoteException;

    public boolean isVisible() throws RemoteException;

    public void setHasOptionsMenu(boolean var1) throws RemoteException;

    public void setMenuVisibility(boolean var1) throws RemoteException;

    public void setRetainInstance(boolean var1) throws RemoteException;

    public void setUserVisibleHint(boolean var1) throws RemoteException;

    public void startActivity(Intent var1) throws RemoteException;

    public void startActivityForResult(Intent var1, int var2) throws RemoteException;

    public zzd zzAZ() throws RemoteException;

    public zzc zzBa() throws RemoteException;

    public zzd zzBb() throws RemoteException;

    public zzc zzBc() throws RemoteException;

    public void zzC(zzd var1) throws RemoteException;

    public void zzD(zzd var1) throws RemoteException;

    public static abstract class zza
    extends Binder
    implements zzc {
        public zza() {
            this.attachInterface((IInterface)this, "com.google.android.gms.dynamic.IFragmentWrapper");
        }

        public static zzc zzcc(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IFragmentWrapper");
            if (iInterface != null && iInterface instanceof zzc) {
                return (zzc)iInterface;
            }
            return new zza$zza(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            RuntimeException runtimeException;
            super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
            throw runtimeException;
        }
    }

    private static class zza$zza
    implements zzc {
        private IBinder zzrp;

        zza$zza(IBinder iBinder) {
            this.zzrp = iBinder;
        }

        public IBinder asBinder() {
            return this.zzrp;
        }

        @Override
        public Bundle getArguments() throws RemoteException {
            Bundle bundle;
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                this.zzrp.transact(3, parcel, parcel2, 0);
                parcel2.readException();
                bundle = parcel2.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel2) : null;
            }
            catch (Throwable throwable) {
                parcel2.recycle();
                parcel.recycle();
                throw throwable;
            }
            parcel2.recycle();
            parcel.recycle();
            return bundle;
        }

        @Override
        public int getId() throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                this.zzrp.transact(4, parcel, parcel2, 0);
                parcel2.readException();
                int n = parcel2.readInt();
                return n;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        @Override
        public boolean getRetainInstance() throws RemoteException {
            boolean bl;
            Parcel parcel;
            Parcel parcel2;
            block3 : {
                IBinder iBinder;
                parcel = Parcel.obtain();
                parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    iBinder = this.zzrp;
                    bl = false;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
                iBinder.transact(7, parcel, parcel2, 0);
                parcel2.readException();
                int n = parcel2.readInt();
                if (n == 0) break block3;
                bl = true;
            }
            parcel2.recycle();
            parcel.recycle();
            return bl;
        }

        @Override
        public String getTag() throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                this.zzrp.transact(8, parcel, parcel2, 0);
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
        public int getTargetRequestCode() throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                this.zzrp.transact(10, parcel, parcel2, 0);
                parcel2.readException();
                int n = parcel2.readInt();
                return n;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        @Override
        public boolean getUserVisibleHint() throws RemoteException {
            boolean bl;
            Parcel parcel;
            Parcel parcel2;
            block3 : {
                IBinder iBinder;
                parcel = Parcel.obtain();
                parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    iBinder = this.zzrp;
                    bl = false;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
                iBinder.transact(11, parcel, parcel2, 0);
                parcel2.readException();
                int n = parcel2.readInt();
                if (n == 0) break block3;
                bl = true;
            }
            parcel2.recycle();
            parcel.recycle();
            return bl;
        }

        @Override
        public zzd getView() throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                this.zzrp.transact(12, parcel, parcel2, 0);
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
        public boolean isAdded() throws RemoteException {
            boolean bl;
            Parcel parcel;
            Parcel parcel2;
            block3 : {
                IBinder iBinder;
                parcel = Parcel.obtain();
                parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    iBinder = this.zzrp;
                    bl = false;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
                iBinder.transact(13, parcel, parcel2, 0);
                parcel2.readException();
                int n = parcel2.readInt();
                if (n == 0) break block3;
                bl = true;
            }
            parcel2.recycle();
            parcel.recycle();
            return bl;
        }

        @Override
        public boolean isDetached() throws RemoteException {
            boolean bl;
            Parcel parcel;
            Parcel parcel2;
            block3 : {
                IBinder iBinder;
                parcel = Parcel.obtain();
                parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    iBinder = this.zzrp;
                    bl = false;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
                iBinder.transact(14, parcel, parcel2, 0);
                parcel2.readException();
                int n = parcel2.readInt();
                if (n == 0) break block3;
                bl = true;
            }
            parcel2.recycle();
            parcel.recycle();
            return bl;
        }

        @Override
        public boolean isHidden() throws RemoteException {
            boolean bl;
            Parcel parcel;
            Parcel parcel2;
            block3 : {
                IBinder iBinder;
                parcel = Parcel.obtain();
                parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    iBinder = this.zzrp;
                    bl = false;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
                iBinder.transact(15, parcel, parcel2, 0);
                parcel2.readException();
                int n = parcel2.readInt();
                if (n == 0) break block3;
                bl = true;
            }
            parcel2.recycle();
            parcel.recycle();
            return bl;
        }

        @Override
        public boolean isInLayout() throws RemoteException {
            boolean bl;
            Parcel parcel;
            Parcel parcel2;
            block3 : {
                IBinder iBinder;
                parcel = Parcel.obtain();
                parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    iBinder = this.zzrp;
                    bl = false;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
                iBinder.transact(16, parcel, parcel2, 0);
                parcel2.readException();
                int n = parcel2.readInt();
                if (n == 0) break block3;
                bl = true;
            }
            parcel2.recycle();
            parcel.recycle();
            return bl;
        }

        @Override
        public boolean isRemoving() throws RemoteException {
            boolean bl;
            Parcel parcel;
            Parcel parcel2;
            block3 : {
                IBinder iBinder;
                parcel = Parcel.obtain();
                parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    iBinder = this.zzrp;
                    bl = false;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
                iBinder.transact(17, parcel, parcel2, 0);
                parcel2.readException();
                int n = parcel2.readInt();
                if (n == 0) break block3;
                bl = true;
            }
            parcel2.recycle();
            parcel.recycle();
            return bl;
        }

        @Override
        public boolean isResumed() throws RemoteException {
            boolean bl;
            Parcel parcel;
            Parcel parcel2;
            block3 : {
                IBinder iBinder;
                parcel = Parcel.obtain();
                parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    iBinder = this.zzrp;
                    bl = false;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
                iBinder.transact(18, parcel, parcel2, 0);
                parcel2.readException();
                int n = parcel2.readInt();
                if (n == 0) break block3;
                bl = true;
            }
            parcel2.recycle();
            parcel.recycle();
            return bl;
        }

        @Override
        public boolean isVisible() throws RemoteException {
            boolean bl;
            Parcel parcel;
            Parcel parcel2;
            block3 : {
                IBinder iBinder;
                parcel = Parcel.obtain();
                parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    iBinder = this.zzrp;
                    bl = false;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
                iBinder.transact(19, parcel, parcel2, 0);
                parcel2.readException();
                int n = parcel2.readInt();
                if (n == 0) break block3;
                bl = true;
            }
            parcel2.recycle();
            parcel.recycle();
            return bl;
        }

        @Override
        public void setHasOptionsMenu(boolean bl) throws RemoteException {
            RuntimeException runtimeException;
            super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
            throw runtimeException;
        }

        @Override
        public void setMenuVisibility(boolean bl) throws RemoteException {
            RuntimeException runtimeException;
            super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
            throw runtimeException;
        }

        @Override
        public void setRetainInstance(boolean bl) throws RemoteException {
            RuntimeException runtimeException;
            super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
            throw runtimeException;
        }

        @Override
        public void setUserVisibleHint(boolean bl) throws RemoteException {
            RuntimeException runtimeException;
            super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
            throw runtimeException;
        }

        @Override
        public void startActivity(Intent intent) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                if (intent != null) {
                    parcel.writeInt(1);
                    intent.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.zzrp.transact(25, parcel, parcel2, 0);
                parcel2.readException();
                return;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        @Override
        public void startActivityForResult(Intent intent, int n) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                if (intent != null) {
                    parcel.writeInt(1);
                    intent.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                parcel.writeInt(n);
                this.zzrp.transact(26, parcel, parcel2, 0);
                parcel2.readException();
                return;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        @Override
        public zzd zzAZ() throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
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

        @Override
        public zzc zzBa() throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                this.zzrp.transact(5, parcel, parcel2, 0);
                parcel2.readException();
                zzc zzc2 = zza.zzcc(parcel2.readStrongBinder());
                return zzc2;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        @Override
        public zzd zzBb() throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                this.zzrp.transact(6, parcel, parcel2, 0);
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
        public zzc zzBc() throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                this.zzrp.transact(9, parcel, parcel2, 0);
                parcel2.readException();
                zzc zzc2 = zza.zzcc(parcel2.readStrongBinder());
                return zzc2;
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
        public void zzC(zzd zzd2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                zzd2 = zzd2 != null ? zzd2.asBinder() : null;
                parcel.writeStrongBinder((IBinder)zzd2);
                this.zzrp.transact(20, parcel, parcel2, 0);
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
        public void zzD(zzd zzd2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                zzd2 = zzd2 != null ? zzd2.asBinder() : null;
                parcel.writeStrongBinder((IBinder)zzd2);
                this.zzrp.transact(27, parcel, parcel2, 0);
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
