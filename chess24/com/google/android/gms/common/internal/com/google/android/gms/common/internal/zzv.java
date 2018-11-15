/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzu;

public interface zzv
extends IInterface {
    public void zza(zzu var1, zzan var2) throws RemoteException;

    public void zza(zzu var1, zzj var2) throws RemoteException;

    public static abstract class zza
    extends Binder
    implements zzv {
        public static zzv zzbu(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
            if (iInterface != null && iInterface instanceof zzv) {
                return (zzv)iInterface;
            }
            return new zza$zza(iBinder);
        }

        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        switch (n) {
                            default: {
                                zzu zzu2 = null;
                                Object object = null;
                                switch (n) {
                                    default: {
                                        return super.onTransact(n, parcel, parcel2, n2);
                                    }
                                    case 47: {
                                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                        zzu2 = zzu.zza.zzbt(parcel.readStrongBinder());
                                        if (parcel.readInt() != 0) {
                                            object = (zzan)zzan.CREATOR.createFromParcel(parcel);
                                        }
                                        this.zza(zzu2, (zzan)object);
                                        parcel2.writeNoException();
                                        return true;
                                    }
                                    case 46: {
                                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                        zzu zzu3 = zzu.zza.zzbt(parcel.readStrongBinder());
                                        object = zzu2;
                                        if (parcel.readInt() != 0) {
                                            object = (zzj)zzj.CREATOR.createFromParcel(parcel);
                                        }
                                        this.zza(zzu3, (zzj)object);
                                        parcel2.writeNoException();
                                        return true;
                                    }
                                    case 45: {
                                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                        zzu.zza.zzbt(parcel.readStrongBinder());
                                        parcel.readInt();
                                        parcel.readString();
                                        parcel2.writeNoException();
                                        return true;
                                    }
                                    case 44: {
                                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                        zzu.zza.zzbt(parcel.readStrongBinder());
                                        parcel.readInt();
                                        parcel.readString();
                                        parcel2.writeNoException();
                                        return true;
                                    }
                                    case 43: {
                                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                        zzu.zza.zzbt(parcel.readStrongBinder());
                                        parcel.readInt();
                                        parcel.readString();
                                        if (parcel.readInt() != 0) {
                                            Bundle.CREATOR.createFromParcel(parcel);
                                        }
                                        parcel2.writeNoException();
                                        return true;
                                    }
                                    case 42: {
                                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                        zzu.zza.zzbt(parcel.readStrongBinder());
                                        parcel.readInt();
                                        parcel.readString();
                                        parcel2.writeNoException();
                                        return true;
                                    }
                                    case 41: {
                                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                        zzu.zza.zzbt(parcel.readStrongBinder());
                                        parcel.readInt();
                                        parcel.readString();
                                        if (parcel.readInt() != 0) {
                                            Bundle.CREATOR.createFromParcel(parcel);
                                        }
                                        parcel2.writeNoException();
                                        return true;
                                    }
                                    case 40: 
                                }
                                parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                zzu.zza.zzbt(parcel.readStrongBinder());
                                parcel.readInt();
                                parcel.readString();
                                parcel2.writeNoException();
                                return true;
                            }
                            case 38: {
                                parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                zzu.zza.zzbt(parcel.readStrongBinder());
                                parcel.readInt();
                                parcel.readString();
                                if (parcel.readInt() != 0) {
                                    Bundle.CREATOR.createFromParcel(parcel);
                                }
                                parcel2.writeNoException();
                                return true;
                            }
                            case 37: {
                                parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                zzu.zza.zzbt(parcel.readStrongBinder());
                                parcel.readInt();
                                parcel.readString();
                                if (parcel.readInt() != 0) {
                                    Bundle.CREATOR.createFromParcel(parcel);
                                }
                                parcel2.writeNoException();
                                return true;
                            }
                            case 36: {
                                parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                zzu.zza.zzbt(parcel.readStrongBinder());
                                parcel.readInt();
                                parcel.readString();
                                parcel2.writeNoException();
                                return true;
                            }
                            case 35: {
                                parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                zzu.zza.zzbt(parcel.readStrongBinder());
                                parcel.readInt();
                                parcel.readString();
                                parcel2.writeNoException();
                                return true;
                            }
                            case 34: {
                                parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                zzu.zza.zzbt(parcel.readStrongBinder());
                                parcel.readInt();
                                parcel.readString();
                                parcel.readString();
                                parcel2.writeNoException();
                                return true;
                            }
                            case 33: {
                                parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                zzu.zza.zzbt(parcel.readStrongBinder());
                                parcel.readInt();
                                parcel.readString();
                                parcel.readString();
                                parcel.readString();
                                parcel.createStringArray();
                                parcel2.writeNoException();
                                return true;
                            }
                            case 32: {
                                parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                zzu.zza.zzbt(parcel.readStrongBinder());
                                parcel.readInt();
                                parcel.readString();
                                parcel2.writeNoException();
                                return true;
                            }
                            case 31: {
                                parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                zzu.zza.zzbt(parcel.readStrongBinder());
                                parcel.readInt();
                                parcel.readString();
                                parcel2.writeNoException();
                                return true;
                            }
                            case 30: 
                        }
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        parcel.readString();
                        parcel.createStringArray();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 28: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        parcel2.writeNoException();
                        return true;
                    }
                    case 27: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 26: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        parcel2.writeNoException();
                        return true;
                    }
                    case 25: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 24: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        parcel2.writeNoException();
                        return true;
                    }
                    case 23: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 22: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        parcel2.writeNoException();
                        return true;
                    }
                    case 21: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        parcel2.writeNoException();
                        return true;
                    }
                    case 20: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        parcel.createStringArray();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 19: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        parcel.readStrongBinder();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 18: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 17: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 16: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 15: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 14: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 13: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 12: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 11: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 10: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        parcel.readString();
                        parcel.createStringArray();
                        parcel2.writeNoException();
                        return true;
                    }
                    case 9: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        parcel.readString();
                        parcel.createStringArray();
                        parcel.readString();
                        parcel.readStrongBinder();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 8: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 7: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel2.writeNoException();
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        parcel2.writeNoException();
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        zzu.zza.zzbt(parcel.readStrongBinder());
                        parcel.readInt();
                        parcel.readString();
                        if (parcel.readInt() != 0) {
                            Bundle.CREATOR.createFromParcel(parcel);
                        }
                        parcel2.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                zzu.zza.zzbt(parcel.readStrongBinder());
                parcel.readInt();
                parcel.readString();
                parcel.readString();
                parcel.createStringArray();
                parcel.readString();
                if (parcel.readInt() != 0) {
                    Bundle.CREATOR.createFromParcel(parcel);
                }
                parcel2.writeNoException();
                return true;
            }
            parcel2.writeString("com.google.android.gms.common.internal.IGmsServiceBroker");
            return true;
        }
    }

    private static class zza$zza
    implements zzv {
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
        public void zza(zzu zzu2, zzan zzan2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                zzu2 = zzu2 != null ? zzu2.asBinder() : null;
                parcel.writeStrongBinder((IBinder)zzu2);
                if (zzan2 != null) {
                    parcel.writeInt(1);
                    zzan2.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.zzrp.transact(47, parcel, parcel2, 0);
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
        public void zza(zzu zzu2, zzj zzj2) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                zzu2 = zzu2 != null ? zzu2.asBinder() : null;
                parcel.writeStrongBinder((IBinder)zzu2);
                if (zzj2 != null) {
                    parcel.writeInt(1);
                    zzj2.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.zzrp.transact(46, parcel, parcel2, 0);
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
