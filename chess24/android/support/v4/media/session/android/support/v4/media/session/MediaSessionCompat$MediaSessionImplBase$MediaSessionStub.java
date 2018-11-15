/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.media.AudioManager
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.IInterface
 *  android.os.RemoteCallbackList
 *  android.os.RemoteException
 *  android.os.ResultReceiver
 *  android.view.KeyEvent
 */
package android.support.v4.media.session;

import android.app.PendingIntent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.ParcelableVolumeInfo;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.KeyEvent;
import java.util.List;

class MediaSessionCompat.MediaSessionImplBase.MediaSessionStub
extends IMediaSession.Stub {
    MediaSessionCompat.MediaSessionImplBase.MediaSessionStub() {
    }

    @Override
    public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        MediaSessionImplBase.this.postToHandler(25, mediaDescriptionCompat);
    }

    @Override
    public void addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, int n) {
        MediaSessionImplBase.this.postToHandler(26, (Object)mediaDescriptionCompat, n);
    }

    @Override
    public void adjustVolume(int n, int n2, String string) {
        MediaSessionImplBase.this.adjustVolume(n, n2);
    }

    @Override
    public void fastForward() throws RemoteException {
        MediaSessionImplBase.this.postToHandler(16);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Bundle getExtras() {
        Object object = MediaSessionImplBase.this.mLock;
        synchronized (object) {
            return MediaSessionImplBase.this.mExtras;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long getFlags() {
        Object object = MediaSessionImplBase.this.mLock;
        synchronized (object) {
            return MediaSessionImplBase.this.mFlags;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public PendingIntent getLaunchPendingIntent() {
        Object object = MediaSessionImplBase.this.mLock;
        synchronized (object) {
            return MediaSessionImplBase.this.mSessionActivity;
        }
    }

    @Override
    public MediaMetadataCompat getMetadata() {
        return MediaSessionImplBase.this.mMetadata;
    }

    @Override
    public String getPackageName() {
        return MediaSessionImplBase.this.mPackageName;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public PlaybackStateCompat getPlaybackState() {
        Object object = MediaSessionImplBase.this.mLock;
        synchronized (object) {
            PlaybackStateCompat playbackStateCompat = MediaSessionImplBase.this.mState;
            MediaMetadataCompat mediaMetadataCompat = MediaSessionImplBase.this.mMetadata;
            return MediaSessionCompat.getStateWithUpdatedPosition(playbackStateCompat, mediaMetadataCompat);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<MediaSessionCompat.QueueItem> getQueue() {
        Object object = MediaSessionImplBase.this.mLock;
        synchronized (object) {
            return MediaSessionImplBase.this.mQueue;
        }
    }

    @Override
    public CharSequence getQueueTitle() {
        return MediaSessionImplBase.this.mQueueTitle;
    }

    @Override
    public int getRatingType() {
        return MediaSessionImplBase.this.mRatingType;
    }

    @Override
    public int getRepeatMode() {
        return MediaSessionImplBase.this.mRepeatMode;
    }

    @Override
    public int getShuffleMode() {
        return MediaSessionImplBase.this.mShuffleMode;
    }

    @Override
    public String getTag() {
        return MediaSessionImplBase.this.mTag;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ParcelableVolumeInfo getVolumeAttributes() {
        Object object = MediaSessionImplBase.this.mLock;
        synchronized (object) {
            int n;
            int n2;
            int n3 = MediaSessionImplBase.this.mVolumeType;
            int n4 = MediaSessionImplBase.this.mLocalStream;
            VolumeProviderCompat volumeProviderCompat = MediaSessionImplBase.this.mVolumeProvider;
            int n5 = 2;
            if (n3 == 2) {
                n5 = volumeProviderCompat.getVolumeControl();
                n = volumeProviderCompat.getMaxVolume();
                n2 = volumeProviderCompat.getCurrentVolume();
            } else {
                n = MediaSessionImplBase.this.mAudioManager.getStreamMaxVolume(n4);
                n2 = MediaSessionImplBase.this.mAudioManager.getStreamVolume(n4);
            }
            return new ParcelableVolumeInfo(n3, n4, n5, n, n2);
        }
    }

    @Override
    public boolean isCaptioningEnabled() {
        return MediaSessionImplBase.this.mCaptioningEnabled;
    }

    @Override
    public boolean isShuffleModeEnabledRemoved() {
        return false;
    }

    @Override
    public boolean isTransportControlEnabled() {
        if ((MediaSessionImplBase.this.mFlags & 2) != 0) {
            return true;
        }
        return false;
    }

    @Override
    public void next() throws RemoteException {
        MediaSessionImplBase.this.postToHandler(14);
    }

    @Override
    public void pause() throws RemoteException {
        MediaSessionImplBase.this.postToHandler(12);
    }

    @Override
    public void play() throws RemoteException {
        MediaSessionImplBase.this.postToHandler(7);
    }

    @Override
    public void playFromMediaId(String string, Bundle bundle) throws RemoteException {
        MediaSessionImplBase.this.postToHandler(8, (Object)string, bundle);
    }

    @Override
    public void playFromSearch(String string, Bundle bundle) throws RemoteException {
        MediaSessionImplBase.this.postToHandler(9, (Object)string, bundle);
    }

    @Override
    public void playFromUri(Uri uri, Bundle bundle) throws RemoteException {
        MediaSessionImplBase.this.postToHandler(10, (Object)uri, bundle);
    }

    @Override
    public void prepare() throws RemoteException {
        MediaSessionImplBase.this.postToHandler(3);
    }

    @Override
    public void prepareFromMediaId(String string, Bundle bundle) throws RemoteException {
        MediaSessionImplBase.this.postToHandler(4, (Object)string, bundle);
    }

    @Override
    public void prepareFromSearch(String string, Bundle bundle) throws RemoteException {
        MediaSessionImplBase.this.postToHandler(5, (Object)string, bundle);
    }

    @Override
    public void prepareFromUri(Uri uri, Bundle bundle) throws RemoteException {
        MediaSessionImplBase.this.postToHandler(6, (Object)uri, bundle);
    }

    @Override
    public void previous() throws RemoteException {
        MediaSessionImplBase.this.postToHandler(15);
    }

    @Override
    public void rate(RatingCompat ratingCompat) throws RemoteException {
        MediaSessionImplBase.this.postToHandler(19, ratingCompat);
    }

    @Override
    public void rateWithExtras(RatingCompat ratingCompat, Bundle bundle) throws RemoteException {
        MediaSessionImplBase.this.postToHandler(31, (Object)ratingCompat, bundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
        if (!MediaSessionImplBase.this.mDestroyed) {
            MediaSessionImplBase.this.mControllerCallbacks.register((IInterface)iMediaControllerCallback);
            return;
        }
        try {
            iMediaControllerCallback.onSessionDestroyed();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    @Override
    public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        MediaSessionImplBase.this.postToHandler(27, mediaDescriptionCompat);
    }

    @Override
    public void removeQueueItemAt(int n) {
        MediaSessionImplBase.this.postToHandler(28, n);
    }

    @Override
    public void rewind() throws RemoteException {
        MediaSessionImplBase.this.postToHandler(17);
    }

    @Override
    public void seekTo(long l) throws RemoteException {
        MediaSessionImplBase.this.postToHandler(18, l);
    }

    @Override
    public void sendCommand(String string, Bundle bundle, MediaSessionCompat.ResultReceiverWrapper resultReceiverWrapper) {
        MediaSessionImplBase.this.postToHandler(1, new MediaSessionCompat.MediaSessionImplBase.Command(string, bundle, resultReceiverWrapper.mResultReceiver));
    }

    @Override
    public void sendCustomAction(String string, Bundle bundle) throws RemoteException {
        MediaSessionImplBase.this.postToHandler(20, (Object)string, bundle);
    }

    @Override
    public boolean sendMediaButton(KeyEvent keyEvent) {
        int n = MediaSessionImplBase.this.mFlags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        if (bl) {
            MediaSessionImplBase.this.postToHandler(21, (Object)keyEvent);
        }
        return bl;
    }

    @Override
    public void setCaptioningEnabled(boolean bl) throws RemoteException {
        MediaSessionImplBase.this.postToHandler(29, bl);
    }

    @Override
    public void setRepeatMode(int n) throws RemoteException {
        MediaSessionImplBase.this.postToHandler(23, n);
    }

    @Override
    public void setShuffleMode(int n) throws RemoteException {
        MediaSessionImplBase.this.postToHandler(30, n);
    }

    @Override
    public void setShuffleModeEnabledRemoved(boolean bl) throws RemoteException {
    }

    @Override
    public void setVolumeTo(int n, int n2, String string) {
        MediaSessionImplBase.this.setVolumeTo(n, n2);
    }

    @Override
    public void skipToQueueItem(long l) {
        MediaSessionImplBase.this.postToHandler(11, l);
    }

    @Override
    public void stop() throws RemoteException {
        MediaSessionImplBase.this.postToHandler(13);
    }

    @Override
    public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
        MediaSessionImplBase.this.mControllerCallbacks.unregister((IInterface)iMediaControllerCallback);
    }
}
