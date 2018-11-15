/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.IInterface
 *  android.os.RemoteCallbackList
 *  android.os.RemoteException
 *  android.view.KeyEvent
 */
package android.support.v4.media.session;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.ParcelableVolumeInfo;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.KeyEvent;
import java.util.List;

class MediaSessionCompat.MediaSessionImplApi21.ExtraSession
extends IMediaSession.Stub {
    MediaSessionCompat.MediaSessionImplApi21.ExtraSession() {
    }

    @Override
    public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        throw new AssertionError();
    }

    @Override
    public void addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, int n) {
        throw new AssertionError();
    }

    @Override
    public void adjustVolume(int n, int n2, String string) {
        throw new AssertionError();
    }

    @Override
    public void fastForward() throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public Bundle getExtras() {
        throw new AssertionError();
    }

    @Override
    public long getFlags() {
        throw new AssertionError();
    }

    @Override
    public PendingIntent getLaunchPendingIntent() {
        throw new AssertionError();
    }

    @Override
    public MediaMetadataCompat getMetadata() {
        throw new AssertionError();
    }

    @Override
    public String getPackageName() {
        throw new AssertionError();
    }

    @Override
    public PlaybackStateCompat getPlaybackState() {
        return MediaSessionCompat.getStateWithUpdatedPosition(MediaSessionImplApi21.this.mPlaybackState, MediaSessionImplApi21.this.mMetadata);
    }

    @Override
    public List<MediaSessionCompat.QueueItem> getQueue() {
        return null;
    }

    @Override
    public CharSequence getQueueTitle() {
        throw new AssertionError();
    }

    @Override
    public int getRatingType() {
        return MediaSessionImplApi21.this.mRatingType;
    }

    @Override
    public int getRepeatMode() {
        return MediaSessionImplApi21.this.mRepeatMode;
    }

    @Override
    public int getShuffleMode() {
        return MediaSessionImplApi21.this.mShuffleMode;
    }

    @Override
    public String getTag() {
        throw new AssertionError();
    }

    @Override
    public ParcelableVolumeInfo getVolumeAttributes() {
        throw new AssertionError();
    }

    @Override
    public boolean isCaptioningEnabled() {
        return MediaSessionImplApi21.this.mCaptioningEnabled;
    }

    @Override
    public boolean isShuffleModeEnabledRemoved() {
        return false;
    }

    @Override
    public boolean isTransportControlEnabled() {
        throw new AssertionError();
    }

    @Override
    public void next() throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void pause() throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void play() throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void playFromMediaId(String string, Bundle bundle) throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void playFromSearch(String string, Bundle bundle) throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void playFromUri(Uri uri, Bundle bundle) throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void prepare() throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void prepareFromMediaId(String string, Bundle bundle) throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void prepareFromSearch(String string, Bundle bundle) throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void prepareFromUri(Uri uri, Bundle bundle) throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void previous() throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void rate(RatingCompat ratingCompat) throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void rateWithExtras(RatingCompat ratingCompat, Bundle bundle) throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
        if (!MediaSessionImplApi21.this.mDestroyed) {
            MediaSessionImplApi21.this.mExtraControllerCallbacks.register((IInterface)iMediaControllerCallback);
        }
    }

    @Override
    public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        throw new AssertionError();
    }

    @Override
    public void removeQueueItemAt(int n) {
        throw new AssertionError();
    }

    @Override
    public void rewind() throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void seekTo(long l) throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void sendCommand(String string, Bundle bundle, MediaSessionCompat.ResultReceiverWrapper resultReceiverWrapper) {
        throw new AssertionError();
    }

    @Override
    public void sendCustomAction(String string, Bundle bundle) throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public boolean sendMediaButton(KeyEvent keyEvent) {
        throw new AssertionError();
    }

    @Override
    public void setCaptioningEnabled(boolean bl) throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void setRepeatMode(int n) throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void setShuffleMode(int n) throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void setShuffleModeEnabledRemoved(boolean bl) throws RemoteException {
    }

    @Override
    public void setVolumeTo(int n, int n2, String string) {
        throw new AssertionError();
    }

    @Override
    public void skipToQueueItem(long l) {
        throw new AssertionError();
    }

    @Override
    public void stop() throws RemoteException {
        throw new AssertionError();
    }

    @Override
    public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
        MediaSessionImplApi21.this.mExtraControllerCallbacks.unregister((IInterface)iMediaControllerCallback);
    }
}
