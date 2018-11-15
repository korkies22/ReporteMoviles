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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaxp;
import com.google.android.gms.internal.zzaxu;
import com.google.android.gms.internal.zzayb;

public static abstract class zzaxu.zza
extends Binder
implements zzaxu {
    public zzaxu.zza() {
        this.attachInterface((IInterface)this, "com.google.android.gms.signin.internal.ISignInCallbacks");
    }

    public static zzaxu zzeX(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
        if (iInterface != null && iInterface instanceof zzaxu) {
            return (zzaxu)iInterface;
        }
        return new zza(iBinder);
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        com.google.android.gms.common.internal.safeparcel.zza zza2 = null;
        zzayb zzayb2 = null;
        zzayb zzayb3 = null;
        GoogleSignInAccount googleSignInAccount = null;
        com.google.android.gms.common.internal.safeparcel.zza zza3 = null;
        switch (n) {
            default: {
                return super.onTransact(n, parcel, parcel2, n2);
            }
            case 1598968902: {
                parcel2.writeString("com.google.android.gms.signin.internal.ISignInCallbacks");
                return true;
            }
            case 8: {
                parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
                if (parcel.readInt() != 0) {
                    zza3 = (zzayb)zzayb.CREATOR.createFromParcel(parcel);
                }
                this.zzb(zza3);
                parcel2.writeNoException();
                return true;
            }
            case 7: {
                parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
                zza3 = parcel.readInt() != 0 ? (Status)Status.CREATOR.createFromParcel(parcel) : null;
                if (parcel.readInt() != 0) {
                    zza2 = (GoogleSignInAccount)GoogleSignInAccount.CREATOR.createFromParcel(parcel);
                }
                this.zza((Status)zza3, zza2);
                parcel2.writeNoException();
                return true;
            }
            case 6: {
                parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
                zza3 = zzayb2;
                if (parcel.readInt() != 0) {
                    zza3 = (Status)Status.CREATOR.createFromParcel(parcel);
                }
                this.zzbJ((Status)zza3);
                parcel2.writeNoException();
                return true;
            }
            case 4: {
                parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
                zza3 = zzayb3;
                if (parcel.readInt() != 0) {
                    zza3 = (Status)Status.CREATOR.createFromParcel(parcel);
                }
                this.zzbI((Status)zza3);
                parcel2.writeNoException();
                return true;
            }
            case 3: 
        }
        parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
        zza3 = parcel.readInt() != 0 ? (ConnectionResult)ConnectionResult.CREATOR.createFromParcel(parcel) : null;
        zza2 = googleSignInAccount;
        if (parcel.readInt() != 0) {
            zza2 = (zzaxp)zzaxp.CREATOR.createFromParcel(parcel);
        }
        this.zza((ConnectionResult)zza3, (zzaxp)zza2);
        parcel2.writeNoException();
        return true;
    }

    private static class zza
    implements zzaxu {
        private IBinder zzrp;

        zza(IBinder iBinder) {
            this.zzrp = iBinder;
        }

        public IBinder asBinder() {
            return this.zzrp;
        }

        @Override
        public void zza(ConnectionResult connectionResult, zzaxp zzaxp2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                if (connectionResult != null) {
                    parcel.writeInt(1);
                    connectionResult.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                if (zzaxp2 != null) {
                    parcel.writeInt(1);
                    zzaxp2.writeToParcel(parcel, 0);
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

        @Override
        public void zza(Status status, GoogleSignInAccount googleSignInAccount) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                if (status != null) {
                    parcel.writeInt(1);
                    status.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                if (googleSignInAccount != null) {
                    parcel.writeInt(1);
                    googleSignInAccount.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.zzrp.transact(7, parcel, parcel2, 0);
                parcel2.readException();
                return;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        @Override
        public void zzb(zzayb zzayb2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                if (zzayb2 != null) {
                    parcel.writeInt(1);
                    zzayb2.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.zzrp.transact(8, parcel, parcel2, 0);
                parcel2.readException();
                return;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        @Override
        public void zzbI(Status status) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                if (status != null) {
                    parcel.writeInt(1);
                    status.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.zzrp.transact(4, parcel, parcel2, 0);
                parcel2.readException();
                return;
            }
            finally {
                parcel2.recycle();
                parcel.recycle();
            }
        }

        @Override
        public void zzbJ(Status status) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInCallbacks");
                if (status != null) {
                    parcel.writeInt(1);
                    status.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.zzrp.transact(6, parcel, parcel2, 0);
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
