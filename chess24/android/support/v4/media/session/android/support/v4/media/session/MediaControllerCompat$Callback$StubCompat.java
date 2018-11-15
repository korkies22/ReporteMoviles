/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.RemoteException
 */
package android.support.v4.media.session;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.ParcelableVolumeInfo;
import android.support.v4.media.session.PlaybackStateCompat;
import java.lang.ref.WeakReference;
import java.util.List;

private static class MediaControllerCompat.Callback.StubCompat
extends IMediaControllerCallback.Stub {
    private final WeakReference<MediaControllerCompat.Callback> mCallback;

    MediaControllerCompat.Callback.StubCompat(MediaControllerCompat.Callback callback) {
        this.mCallback = new WeakReference<MediaControllerCompat.Callback>(callback);
    }

    @Override
    public void onCaptioningEnabledChanged(boolean bl) throws RemoteException {
        MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
        if (callback != null) {
            callback.postToHandler(11, bl, null);
        }
    }

    @Override
    public void onEvent(String string, Bundle bundle) throws RemoteException {
        MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
        if (callback != null) {
            callback.postToHandler(1, string, bundle);
        }
    }

    @Override
    public void onExtrasChanged(Bundle bundle) throws RemoteException {
        MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
        if (callback != null) {
            callback.postToHandler(7, (Object)bundle, null);
        }
    }

    @Override
    public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException {
        MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
        if (callback != null) {
            callback.postToHandler(3, mediaMetadataCompat, null);
        }
    }

    @Override
    public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) throws RemoteException {
        MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
        if (callback != null) {
            callback.postToHandler(2, playbackStateCompat, null);
        }
    }

    @Override
    public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) throws RemoteException {
        MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
        if (callback != null) {
            callback.postToHandler(5, list, null);
        }
    }

    @Override
    public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
        MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
        if (callback != null) {
            callback.postToHandler(6, charSequence, null);
        }
    }

    @Override
    public void onRepeatModeChanged(int n) throws RemoteException {
        MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
        if (callback != null) {
            callback.postToHandler(9, n, null);
        }
    }

    @Override
    public void onSessionDestroyed() throws RemoteException {
        MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
        if (callback != null) {
            callback.postToHandler(8, null, null);
        }
    }

    @Override
    public void onSessionReady() throws RemoteException {
        MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
        if (callback != null) {
            callback.postToHandler(13, null, null);
        }
    }

    @Override
    public void onShuffleModeChanged(int n) throws RemoteException {
        MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
        if (callback != null) {
            callback.postToHandler(12, n, null);
        }
    }

    @Override
    public void onShuffleModeChangedRemoved(boolean bl) throws RemoteException {
    }

    @Override
    public void onVolumeInfoChanged(ParcelableVolumeInfo object) throws RemoteException {
        MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
        if (callback != null) {
            object = object != null ? new MediaControllerCompat.PlaybackInfo(object.volumeType, object.audioStream, object.controlType, object.maxVolume, object.currentVolume) : null;
            callback.postToHandler(4, object, null);
        }
    }
}
