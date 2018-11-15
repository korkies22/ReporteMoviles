/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.RemoteException
 *  android.os.ResultReceiver
 *  android.util.Log
 *  android.view.KeyEvent
 */
package android.support.v4.media.session;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.ParcelableVolumeInfo;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.KeyEvent;
import java.util.List;

static class MediaControllerCompat.MediaControllerImplBase
implements MediaControllerCompat.MediaControllerImpl {
    private IMediaSession mBinder;
    private MediaControllerCompat.TransportControls mTransportControls;

    public MediaControllerCompat.MediaControllerImplBase(MediaSessionCompat.Token token) {
        this.mBinder = IMediaSession.Stub.asInterface((IBinder)token.getToken());
    }

    @Override
    public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        try {
            if ((this.mBinder.getFlags() & 4L) == 0L) {
                throw new UnsupportedOperationException("This session doesn't support queue management operations");
            }
            this.mBinder.addQueueItem(mediaDescriptionCompat);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in addQueueItem.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat, int n) {
        try {
            if ((this.mBinder.getFlags() & 4L) == 0L) {
                throw new UnsupportedOperationException("This session doesn't support queue management operations");
            }
            this.mBinder.addQueueItemAt(mediaDescriptionCompat, n);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in addQueueItemAt.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void adjustVolume(int n, int n2) {
        try {
            this.mBinder.adjustVolume(n, n2, null);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in adjustVolume.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
        if (keyEvent == null) {
            throw new IllegalArgumentException("event may not be null.");
        }
        try {
            this.mBinder.sendMediaButton(keyEvent);
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in dispatchMediaButtonEvent.", (Throwable)remoteException);
        }
        return false;
    }

    @Override
    public Bundle getExtras() {
        try {
            Bundle bundle = this.mBinder.getExtras();
            return bundle;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in getExtras.", (Throwable)remoteException);
            return null;
        }
    }

    @Override
    public long getFlags() {
        try {
            long l = this.mBinder.getFlags();
            return l;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in getFlags.", (Throwable)remoteException);
            return 0L;
        }
    }

    @Override
    public Object getMediaController() {
        return null;
    }

    @Override
    public MediaMetadataCompat getMetadata() {
        try {
            MediaMetadataCompat mediaMetadataCompat = this.mBinder.getMetadata();
            return mediaMetadataCompat;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in getMetadata.", (Throwable)remoteException);
            return null;
        }
    }

    @Override
    public String getPackageName() {
        try {
            String string = this.mBinder.getPackageName();
            return string;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in getPackageName.", (Throwable)remoteException);
            return null;
        }
    }

    @Override
    public MediaControllerCompat.PlaybackInfo getPlaybackInfo() {
        try {
            Object object = this.mBinder.getVolumeAttributes();
            object = new MediaControllerCompat.PlaybackInfo(object.volumeType, object.audioStream, object.controlType, object.maxVolume, object.currentVolume);
            return object;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in getPlaybackInfo.", (Throwable)remoteException);
            return null;
        }
    }

    @Override
    public PlaybackStateCompat getPlaybackState() {
        try {
            PlaybackStateCompat playbackStateCompat = this.mBinder.getPlaybackState();
            return playbackStateCompat;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in getPlaybackState.", (Throwable)remoteException);
            return null;
        }
    }

    @Override
    public List<MediaSessionCompat.QueueItem> getQueue() {
        try {
            List<MediaSessionCompat.QueueItem> list = this.mBinder.getQueue();
            return list;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in getQueue.", (Throwable)remoteException);
            return null;
        }
    }

    @Override
    public CharSequence getQueueTitle() {
        try {
            CharSequence charSequence = this.mBinder.getQueueTitle();
            return charSequence;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in getQueueTitle.", (Throwable)remoteException);
            return null;
        }
    }

    @Override
    public int getRatingType() {
        try {
            int n = this.mBinder.getRatingType();
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in getRatingType.", (Throwable)remoteException);
            return 0;
        }
    }

    @Override
    public int getRepeatMode() {
        try {
            int n = this.mBinder.getRepeatMode();
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in getRepeatMode.", (Throwable)remoteException);
            return -1;
        }
    }

    @Override
    public PendingIntent getSessionActivity() {
        try {
            PendingIntent pendingIntent = this.mBinder.getLaunchPendingIntent();
            return pendingIntent;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in getSessionActivity.", (Throwable)remoteException);
            return null;
        }
    }

    @Override
    public int getShuffleMode() {
        try {
            int n = this.mBinder.getShuffleMode();
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in getShuffleMode.", (Throwable)remoteException);
            return -1;
        }
    }

    @Override
    public MediaControllerCompat.TransportControls getTransportControls() {
        if (this.mTransportControls == null) {
            this.mTransportControls = new MediaControllerCompat.TransportControlsBase(this.mBinder);
        }
        return this.mTransportControls;
    }

    @Override
    public boolean isCaptioningEnabled() {
        try {
            boolean bl = this.mBinder.isCaptioningEnabled();
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in isCaptioningEnabled.", (Throwable)remoteException);
            return false;
        }
    }

    @Override
    public boolean isSessionReady() {
        return true;
    }

    @Override
    public void registerCallback(MediaControllerCompat.Callback callback, Handler handler) {
        if (callback == null) {
            throw new IllegalArgumentException("callback may not be null.");
        }
        try {
            this.mBinder.asBinder().linkToDeath((IBinder.DeathRecipient)callback, 0);
            this.mBinder.registerCallbackListener((IMediaControllerCallback)callback.mCallbackObj);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in registerCallback.", (Throwable)remoteException);
            callback.onSessionDestroyed();
            return;
        }
    }

    @Override
    public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        try {
            if ((this.mBinder.getFlags() & 4L) == 0L) {
                throw new UnsupportedOperationException("This session doesn't support queue management operations");
            }
            this.mBinder.removeQueueItem(mediaDescriptionCompat);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in removeQueueItem.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void sendCommand(String string, Bundle bundle, ResultReceiver resultReceiver) {
        try {
            this.mBinder.sendCommand(string, bundle, new MediaSessionCompat.ResultReceiverWrapper(resultReceiver));
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in sendCommand.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void setVolumeTo(int n, int n2) {
        try {
            this.mBinder.setVolumeTo(n, n2, null);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in setVolumeTo.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void unregisterCallback(MediaControllerCompat.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback may not be null.");
        }
        try {
            this.mBinder.unregisterCallbackListener((IMediaControllerCallback)callback.mCallbackObj);
            this.mBinder.asBinder().unlinkToDeath((IBinder.DeathRecipient)callback, 0);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in unregisterCallback.", (Throwable)remoteException);
            return;
        }
    }
}
