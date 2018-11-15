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
 *  android.text.TextUtils
 */
package android.support.v4.media.session;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.ParcelableVolumeInfo;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public static abstract class IMediaControllerCallback.Stub
extends Binder
implements IMediaControllerCallback {
    private static final String DESCRIPTOR = "android.support.v4.media.session.IMediaControllerCallback";
    static final int TRANSACTION_onCaptioningEnabledChanged = 11;
    static final int TRANSACTION_onEvent = 1;
    static final int TRANSACTION_onExtrasChanged = 7;
    static final int TRANSACTION_onMetadataChanged = 4;
    static final int TRANSACTION_onPlaybackStateChanged = 3;
    static final int TRANSACTION_onQueueChanged = 5;
    static final int TRANSACTION_onQueueTitleChanged = 6;
    static final int TRANSACTION_onRepeatModeChanged = 9;
    static final int TRANSACTION_onSessionDestroyed = 2;
    static final int TRANSACTION_onSessionReady = 13;
    static final int TRANSACTION_onShuffleModeChanged = 12;
    static final int TRANSACTION_onShuffleModeChangedRemoved = 10;
    static final int TRANSACTION_onVolumeInfoChanged = 8;

    public IMediaControllerCallback.Stub() {
        this.attachInterface((IInterface)this, DESCRIPTOR);
    }

    public static IMediaControllerCallback asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
        if (iInterface != null && iInterface instanceof IMediaControllerCallback) {
            return (IMediaControllerCallback)iInterface;
        }
        return new Proxy(iBinder);
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
        if (n != 1598968902) {
            boolean bl = false;
            boolean bl2 = false;
            Object var9_7 = null;
            Object var10_8 = null;
            Object var11_9 = null;
            Object var12_10 = null;
            Object var7_11 = null;
            String string = null;
            switch (n) {
                default: {
                    return super.onTransact(n, parcel, object, n2);
                }
                case 13: {
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onSessionReady();
                    return true;
                }
                case 12: {
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onShuffleModeChanged(parcel.readInt());
                    return true;
                }
                case 11: {
                    parcel.enforceInterface(DESCRIPTOR);
                    if (parcel.readInt() != 0) {
                        bl2 = true;
                    }
                    this.onCaptioningEnabledChanged(bl2);
                    return true;
                }
                case 10: {
                    parcel.enforceInterface(DESCRIPTOR);
                    bl2 = bl;
                    if (parcel.readInt() != 0) {
                        bl2 = true;
                    }
                    this.onShuffleModeChangedRemoved(bl2);
                    return true;
                }
                case 9: {
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onRepeatModeChanged(parcel.readInt());
                    return true;
                }
                case 8: {
                    parcel.enforceInterface(DESCRIPTOR);
                    object = string;
                    if (parcel.readInt() != 0) {
                        object = (ParcelableVolumeInfo)ParcelableVolumeInfo.CREATOR.createFromParcel(parcel);
                    }
                    this.onVolumeInfoChanged((ParcelableVolumeInfo)object);
                    return true;
                }
                case 7: {
                    parcel.enforceInterface(DESCRIPTOR);
                    object = var9_7;
                    if (parcel.readInt() != 0) {
                        object = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.onExtrasChanged((Bundle)object);
                    return true;
                }
                case 6: {
                    parcel.enforceInterface(DESCRIPTOR);
                    object = var10_8;
                    if (parcel.readInt() != 0) {
                        object = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
                    }
                    this.onQueueTitleChanged((CharSequence)object);
                    return true;
                }
                case 5: {
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onQueueChanged((List)parcel.createTypedArrayList(MediaSessionCompat.QueueItem.CREATOR));
                    return true;
                }
                case 4: {
                    parcel.enforceInterface(DESCRIPTOR);
                    object = var11_9;
                    if (parcel.readInt() != 0) {
                        object = (MediaMetadataCompat)MediaMetadataCompat.CREATOR.createFromParcel(parcel);
                    }
                    this.onMetadataChanged((MediaMetadataCompat)object);
                    return true;
                }
                case 3: {
                    parcel.enforceInterface(DESCRIPTOR);
                    object = var12_10;
                    if (parcel.readInt() != 0) {
                        object = (PlaybackStateCompat)PlaybackStateCompat.CREATOR.createFromParcel(parcel);
                    }
                    this.onPlaybackStateChanged((PlaybackStateCompat)object);
                    return true;
                }
                case 2: {
                    parcel.enforceInterface(DESCRIPTOR);
                    this.onSessionDestroyed();
                    return true;
                }
                case 1: 
            }
            parcel.enforceInterface(DESCRIPTOR);
            string = parcel.readString();
            object = var7_11;
            if (parcel.readInt() != 0) {
                object = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
            }
            this.onEvent(string, (Bundle)object);
            return true;
        }
        object.writeString(DESCRIPTOR);
        return true;
    }

    private static class Proxy
    implements IMediaControllerCallback {
        private IBinder mRemote;

        Proxy(IBinder iBinder) {
            this.mRemote = iBinder;
        }

        public IBinder asBinder() {
            return this.mRemote;
        }

        public String getInterfaceDescriptor() {
            return IMediaControllerCallback.Stub.DESCRIPTOR;
        }

        @Override
        public void onCaptioningEnabledChanged(boolean bl) throws RemoteException {
            RuntimeException runtimeException;
            super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
            throw runtimeException;
        }

        @Override
        public void onEvent(String string, Bundle bundle) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IMediaControllerCallback.Stub.DESCRIPTOR);
                parcel.writeString(string);
                if (bundle != null) {
                    parcel.writeInt(1);
                    bundle.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.mRemote.transact(1, parcel, null, 1);
                return;
            }
            finally {
                parcel.recycle();
            }
        }

        @Override
        public void onExtrasChanged(Bundle bundle) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IMediaControllerCallback.Stub.DESCRIPTOR);
                if (bundle != null) {
                    parcel.writeInt(1);
                    bundle.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.mRemote.transact(7, parcel, null, 1);
                return;
            }
            finally {
                parcel.recycle();
            }
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IMediaControllerCallback.Stub.DESCRIPTOR);
                if (mediaMetadataCompat != null) {
                    parcel.writeInt(1);
                    mediaMetadataCompat.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.mRemote.transact(4, parcel, null, 1);
                return;
            }
            finally {
                parcel.recycle();
            }
        }

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IMediaControllerCallback.Stub.DESCRIPTOR);
                if (playbackStateCompat != null) {
                    parcel.writeInt(1);
                    playbackStateCompat.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.mRemote.transact(3, parcel, null, 1);
                return;
            }
            finally {
                parcel.recycle();
            }
        }

        @Override
        public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IMediaControllerCallback.Stub.DESCRIPTOR);
                parcel.writeTypedList(list);
                this.mRemote.transact(5, parcel, null, 1);
                return;
            }
            finally {
                parcel.recycle();
            }
        }

        @Override
        public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IMediaControllerCallback.Stub.DESCRIPTOR);
                if (charSequence != null) {
                    parcel.writeInt(1);
                    TextUtils.writeToParcel((CharSequence)charSequence, (Parcel)parcel, (int)0);
                } else {
                    parcel.writeInt(0);
                }
                this.mRemote.transact(6, parcel, null, 1);
                return;
            }
            finally {
                parcel.recycle();
            }
        }

        @Override
        public void onRepeatModeChanged(int n) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IMediaControllerCallback.Stub.DESCRIPTOR);
                parcel.writeInt(n);
                this.mRemote.transact(9, parcel, null, 1);
                return;
            }
            finally {
                parcel.recycle();
            }
        }

        @Override
        public void onSessionDestroyed() throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IMediaControllerCallback.Stub.DESCRIPTOR);
                this.mRemote.transact(2, parcel, null, 1);
                return;
            }
            finally {
                parcel.recycle();
            }
        }

        @Override
        public void onSessionReady() throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IMediaControllerCallback.Stub.DESCRIPTOR);
                this.mRemote.transact(13, parcel, null, 1);
                return;
            }
            finally {
                parcel.recycle();
            }
        }

        @Override
        public void onShuffleModeChanged(int n) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IMediaControllerCallback.Stub.DESCRIPTOR);
                parcel.writeInt(n);
                this.mRemote.transact(12, parcel, null, 1);
                return;
            }
            finally {
                parcel.recycle();
            }
        }

        @Override
        public void onShuffleModeChangedRemoved(boolean bl) throws RemoteException {
            RuntimeException runtimeException;
            super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
            throw runtimeException;
        }

        @Override
        public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException {
            Parcel parcel = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IMediaControllerCallback.Stub.DESCRIPTOR);
                if (parcelableVolumeInfo != null) {
                    parcel.writeInt(1);
                    parcelableVolumeInfo.writeToParcel(parcel, 0);
                } else {
                    parcel.writeInt(0);
                }
                this.mRemote.transact(8, parcel, null, 1);
                return;
            }
            finally {
                parcel.recycle();
            }
        }
    }

}
