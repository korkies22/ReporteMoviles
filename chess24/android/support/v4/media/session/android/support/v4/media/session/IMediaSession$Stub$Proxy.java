/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 *  android.text.TextUtils
 *  android.view.KeyEvent
 */
package android.support.v4.media.session;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.ParcelableVolumeInfo;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.List;

private static class IMediaSession.Stub.Proxy
implements IMediaSession {
    private IBinder mRemote;

    IMediaSession.Stub.Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override
    public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            if (mediaDescriptionCompat != null) {
                parcel.writeInt(1);
                mediaDescriptionCompat.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(41, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, int n) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            if (mediaDescriptionCompat != null) {
                parcel.writeInt(1);
                mediaDescriptionCompat.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeInt(n);
            this.mRemote.transact(42, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void adjustVolume(int n, int n2, String string) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            parcel.writeInt(n);
            parcel.writeInt(n2);
            parcel.writeString(string);
            this.mRemote.transact(11, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    public IBinder asBinder() {
        return this.mRemote;
    }

    @Override
    public void fastForward() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(22, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public Bundle getExtras() throws RemoteException {
        Bundle bundle;
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(31, parcel, parcel2, 0);
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
    public long getFlags() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(9, parcel, parcel2, 0);
            parcel2.readException();
            long l = parcel2.readLong();
            return l;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    public String getInterfaceDescriptor() {
        return IMediaSession.Stub.DESCRIPTOR;
    }

    @Override
    public PendingIntent getLaunchPendingIntent() throws RemoteException {
        PendingIntent pendingIntent;
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(8, parcel, parcel2, 0);
            parcel2.readException();
            pendingIntent = parcel2.readInt() != 0 ? (PendingIntent)PendingIntent.CREATOR.createFromParcel(parcel2) : null;
        }
        catch (Throwable throwable) {
            parcel2.recycle();
            parcel.recycle();
            throw throwable;
        }
        parcel2.recycle();
        parcel.recycle();
        return pendingIntent;
    }

    @Override
    public MediaMetadataCompat getMetadata() throws RemoteException {
        MediaMetadataCompat mediaMetadataCompat;
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(27, parcel, parcel2, 0);
            parcel2.readException();
            mediaMetadataCompat = parcel2.readInt() != 0 ? (MediaMetadataCompat)MediaMetadataCompat.CREATOR.createFromParcel(parcel2) : null;
        }
        catch (Throwable throwable) {
            parcel2.recycle();
            parcel.recycle();
            throw throwable;
        }
        parcel2.recycle();
        parcel.recycle();
        return mediaMetadataCompat;
    }

    @Override
    public String getPackageName() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(6, parcel, parcel2, 0);
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
    public PlaybackStateCompat getPlaybackState() throws RemoteException {
        PlaybackStateCompat playbackStateCompat;
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(28, parcel, parcel2, 0);
            parcel2.readException();
            playbackStateCompat = parcel2.readInt() != 0 ? (PlaybackStateCompat)PlaybackStateCompat.CREATOR.createFromParcel(parcel2) : null;
        }
        catch (Throwable throwable) {
            parcel2.recycle();
            parcel.recycle();
            throw throwable;
        }
        parcel2.recycle();
        parcel.recycle();
        return playbackStateCompat;
    }

    @Override
    public List<MediaSessionCompat.QueueItem> getQueue() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(29, parcel, parcel2, 0);
            parcel2.readException();
            ArrayList arrayList = parcel2.createTypedArrayList(MediaSessionCompat.QueueItem.CREATOR);
            return arrayList;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public CharSequence getQueueTitle() throws RemoteException {
        CharSequence charSequence;
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(30, parcel, parcel2, 0);
            parcel2.readException();
            charSequence = parcel2.readInt() != 0 ? (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel2) : null;
        }
        catch (Throwable throwable) {
            parcel2.recycle();
            parcel.recycle();
            throw throwable;
        }
        parcel2.recycle();
        parcel.recycle();
        return charSequence;
    }

    @Override
    public int getRatingType() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(32, parcel, parcel2, 0);
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
    public int getRepeatMode() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(37, parcel, parcel2, 0);
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
    public int getShuffleMode() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(47, parcel, parcel2, 0);
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
    public String getTag() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(7, parcel, parcel2, 0);
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
    public ParcelableVolumeInfo getVolumeAttributes() throws RemoteException {
        ParcelableVolumeInfo parcelableVolumeInfo;
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(10, parcel, parcel2, 0);
            parcel2.readException();
            parcelableVolumeInfo = parcel2.readInt() != 0 ? (ParcelableVolumeInfo)ParcelableVolumeInfo.CREATOR.createFromParcel(parcel2) : null;
        }
        catch (Throwable throwable) {
            parcel2.recycle();
            parcel.recycle();
            throw throwable;
        }
        parcel2.recycle();
        parcel.recycle();
        return parcelableVolumeInfo;
    }

    @Override
    public boolean isCaptioningEnabled() throws RemoteException {
        boolean bl;
        Parcel parcel;
        Parcel parcel2;
        block3 : {
            IBinder iBinder;
            parcel = Parcel.obtain();
            parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
                iBinder = this.mRemote;
                bl = false;
            }
            catch (Throwable throwable) {
                parcel2.recycle();
                parcel.recycle();
                throw throwable;
            }
            iBinder.transact(45, parcel, parcel2, 0);
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
    public boolean isShuffleModeEnabledRemoved() throws RemoteException {
        boolean bl;
        Parcel parcel;
        Parcel parcel2;
        block3 : {
            IBinder iBinder;
            parcel = Parcel.obtain();
            parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
                iBinder = this.mRemote;
                bl = false;
            }
            catch (Throwable throwable) {
                parcel2.recycle();
                parcel.recycle();
                throw throwable;
            }
            iBinder.transact(38, parcel, parcel2, 0);
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
    public boolean isTransportControlEnabled() throws RemoteException {
        boolean bl;
        Parcel parcel;
        Parcel parcel2;
        block3 : {
            IBinder iBinder;
            parcel = Parcel.obtain();
            parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
                iBinder = this.mRemote;
                bl = false;
            }
            catch (Throwable throwable) {
                parcel2.recycle();
                parcel.recycle();
                throw throwable;
            }
            iBinder.transact(5, parcel, parcel2, 0);
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
    public void next() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(20, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void pause() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(18, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void play() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(13, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void playFromMediaId(String string, Bundle bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            parcel.writeString(string);
            if (bundle != null) {
                parcel.writeInt(1);
                bundle.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(14, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void playFromSearch(String string, Bundle bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            parcel.writeString(string);
            if (bundle != null) {
                parcel.writeInt(1);
                bundle.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(15, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void playFromUri(Uri uri, Bundle bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            if (uri != null) {
                parcel.writeInt(1);
                uri.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            if (bundle != null) {
                parcel.writeInt(1);
                bundle.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(16, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void prepare() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(33, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void prepareFromMediaId(String string, Bundle bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            parcel.writeString(string);
            if (bundle != null) {
                parcel.writeInt(1);
                bundle.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(34, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void prepareFromSearch(String string, Bundle bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            parcel.writeString(string);
            if (bundle != null) {
                parcel.writeInt(1);
                bundle.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(35, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void prepareFromUri(Uri uri, Bundle bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            if (uri != null) {
                parcel.writeInt(1);
                uri.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            if (bundle != null) {
                parcel.writeInt(1);
                bundle.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(36, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void previous() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(21, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void rate(RatingCompat ratingCompat) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            if (ratingCompat != null) {
                parcel.writeInt(1);
                ratingCompat.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(25, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void rateWithExtras(RatingCompat ratingCompat, Bundle bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            if (ratingCompat != null) {
                parcel.writeInt(1);
                ratingCompat.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            if (bundle != null) {
                parcel.writeInt(1);
                bundle.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(51, parcel, parcel2, 0);
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
    public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            iMediaControllerCallback = iMediaControllerCallback != null ? iMediaControllerCallback.asBinder() : null;
            parcel.writeStrongBinder((IBinder)iMediaControllerCallback);
            this.mRemote.transact(3, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            if (mediaDescriptionCompat != null) {
                parcel.writeInt(1);
                mediaDescriptionCompat.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(43, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void removeQueueItemAt(int n) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            parcel.writeInt(n);
            this.mRemote.transact(44, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void rewind() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(23, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void seekTo(long l) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            parcel.writeLong(l);
            this.mRemote.transact(24, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void sendCommand(String string, Bundle bundle, MediaSessionCompat.ResultReceiverWrapper resultReceiverWrapper) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            parcel.writeString(string);
            if (bundle != null) {
                parcel.writeInt(1);
                bundle.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            if (resultReceiverWrapper != null) {
                parcel.writeInt(1);
                resultReceiverWrapper.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(1, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void sendCustomAction(String string, Bundle bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            parcel.writeString(string);
            if (bundle != null) {
                parcel.writeInt(1);
                bundle.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(26, parcel, parcel2, 0);
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
    public boolean sendMediaButton(KeyEvent keyEvent) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            boolean bl = true;
            if (keyEvent != null) {
                parcel.writeInt(1);
                keyEvent.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            this.mRemote.transact(2, parcel, parcel2, 0);
            parcel2.readException();
            int n = parcel2.readInt();
            if (n != 0) {
                return bl;
            } else {
                bl = false;
            }
            return bl;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void setCaptioningEnabled(boolean bl) throws RemoteException {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    @Override
    public void setRepeatMode(int n) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            parcel.writeInt(n);
            this.mRemote.transact(39, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void setShuffleMode(int n) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            parcel.writeInt(n);
            this.mRemote.transact(48, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void setShuffleModeEnabledRemoved(boolean bl) throws RemoteException {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    @Override
    public void setVolumeTo(int n, int n2, String string) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            parcel.writeInt(n);
            parcel.writeInt(n2);
            parcel.writeString(string);
            this.mRemote.transact(12, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void skipToQueueItem(long l) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            parcel.writeLong(l);
            this.mRemote.transact(17, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }

    @Override
    public void stop() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            this.mRemote.transact(19, parcel, parcel2, 0);
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
    public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken(IMediaSession.Stub.DESCRIPTOR);
            iMediaControllerCallback = iMediaControllerCallback != null ? iMediaControllerCallback.asBinder() : null;
            parcel.writeStrongBinder((IBinder)iMediaControllerCallback);
            this.mRemote.transact(4, parcel, parcel2, 0);
            parcel2.readException();
            return;
        }
        finally {
            parcel2.recycle();
            parcel.recycle();
        }
    }
}
