/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IInterface
 *  android.os.RemoteCallbackList
 *  android.os.RemoteException
 *  android.view.KeyEvent
 */
package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.MediaSessionCompatApi21;
import android.support.v4.media.session.MediaSessionCompatApi22;
import android.support.v4.media.session.MediaSessionCompatApi24;
import android.support.v4.media.session.ParcelableVolumeInfo;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiresApi(value=21)
static class MediaSessionCompat.MediaSessionImplApi21
implements MediaSessionCompat.MediaSessionImpl {
    boolean mCaptioningEnabled;
    private boolean mDestroyed = false;
    private final RemoteCallbackList<IMediaControllerCallback> mExtraControllerCallbacks = new RemoteCallbackList();
    private MediaMetadataCompat mMetadata;
    private PlaybackStateCompat mPlaybackState;
    private List<MediaSessionCompat.QueueItem> mQueue;
    int mRatingType;
    int mRepeatMode;
    private final Object mSessionObj;
    int mShuffleMode;
    private final MediaSessionCompat.Token mToken;

    public MediaSessionCompat.MediaSessionImplApi21(Context context, String string) {
        this.mSessionObj = MediaSessionCompatApi21.createSession(context, string);
        this.mToken = new MediaSessionCompat.Token((Object)MediaSessionCompatApi21.getSessionToken(this.mSessionObj), new ExtraSession());
    }

    public MediaSessionCompat.MediaSessionImplApi21(Object object) {
        this.mSessionObj = MediaSessionCompatApi21.verifySession(object);
        this.mToken = new MediaSessionCompat.Token((Object)MediaSessionCompatApi21.getSessionToken(this.mSessionObj), new ExtraSession());
    }

    static /* synthetic */ List access$200(MediaSessionCompat.MediaSessionImplApi21 mediaSessionImplApi21) {
        return mediaSessionImplApi21.mQueue;
    }

    @Override
    public String getCallingPackage() {
        if (Build.VERSION.SDK_INT < 24) {
            return null;
        }
        return MediaSessionCompatApi24.getCallingPackage(this.mSessionObj);
    }

    @Override
    public Object getMediaSession() {
        return this.mSessionObj;
    }

    @Override
    public PlaybackStateCompat getPlaybackState() {
        return this.mPlaybackState;
    }

    @Override
    public Object getRemoteControlClient() {
        return null;
    }

    @Override
    public MediaSessionCompat.Token getSessionToken() {
        return this.mToken;
    }

    @Override
    public boolean isActive() {
        return MediaSessionCompatApi21.isActive(this.mSessionObj);
    }

    @Override
    public void release() {
        this.mDestroyed = true;
        MediaSessionCompatApi21.release(this.mSessionObj);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void sendSessionEvent(String string, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 23) ** GOTO lbl10
        n = this.mExtraControllerCallbacks.beginBroadcast() - 1;
        do {
            if (n >= 0) {
                iMediaControllerCallback = (IMediaControllerCallback)this.mExtraControllerCallbacks.getBroadcastItem(n);
                iMediaControllerCallback.onEvent(string, bundle);
            } else {
                this.mExtraControllerCallbacks.finishBroadcast();
lbl10: // 2 sources:
                MediaSessionCompatApi21.sendSessionEvent(this.mSessionObj, string, bundle);
                return;
                catch (RemoteException remoteException) {}
            }
            --n;
        } while (true);
    }

    @Override
    public void setActive(boolean bl) {
        MediaSessionCompatApi21.setActive(this.mSessionObj, bl);
    }

    @Override
    public void setCallback(MediaSessionCompat.Callback callback, Handler handler) {
        Object object = this.mSessionObj;
        Object object2 = callback == null ? null : callback.mCallbackObj;
        MediaSessionCompatApi21.setCallback(object, object2, handler);
        if (callback != null) {
            callback.setSessionImpl(this, handler);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void setCaptioningEnabled(boolean bl) {
        if (this.mCaptioningEnabled == bl) return;
        this.mCaptioningEnabled = bl;
        int n = this.mExtraControllerCallbacks.beginBroadcast() - 1;
        do {
            if (n < 0) {
                this.mExtraControllerCallbacks.finishBroadcast();
                return;
            }
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mExtraControllerCallbacks.getBroadcastItem(n);
            try {
                iMediaControllerCallback.onCaptioningEnabledChanged(bl);
            }
            catch (RemoteException remoteException) {}
            --n;
        } while (true);
    }

    @Override
    public void setExtras(Bundle bundle) {
        MediaSessionCompatApi21.setExtras(this.mSessionObj, bundle);
    }

    @Override
    public void setFlags(int n) {
        MediaSessionCompatApi21.setFlags(this.mSessionObj, n);
    }

    @Override
    public void setMediaButtonReceiver(PendingIntent pendingIntent) {
        MediaSessionCompatApi21.setMediaButtonReceiver(this.mSessionObj, pendingIntent);
    }

    @Override
    public void setMetadata(MediaMetadataCompat object) {
        this.mMetadata = object;
        Object object2 = this.mSessionObj;
        object = object == null ? null : object.getMediaMetadata();
        MediaSessionCompatApi21.setMetadata(object2, object);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void setPlaybackState(PlaybackStateCompat object) {
        this.mPlaybackState = object;
        int n = this.mExtraControllerCallbacks.beginBroadcast() - 1;
        do {
            Object object2;
            if (n >= 0) {
                object2 = (IMediaControllerCallback)this.mExtraControllerCallbacks.getBroadcastItem(n);
                object2.onPlaybackStateChanged((PlaybackStateCompat)object);
            } else {
                this.mExtraControllerCallbacks.finishBroadcast();
                object2 = this.mSessionObj;
                object = object == null ? null : object.getPlaybackState();
                MediaSessionCompatApi21.setPlaybackState(object2, object);
                return;
                catch (RemoteException remoteException) {}
            }
            --n;
        } while (true);
    }

    @Override
    public void setPlaybackToLocal(int n) {
        MediaSessionCompatApi21.setPlaybackToLocal(this.mSessionObj, n);
    }

    @Override
    public void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
        MediaSessionCompatApi21.setPlaybackToRemote(this.mSessionObj, volumeProviderCompat.getVolumeProvider());
    }

    @Override
    public void setQueue(List<MediaSessionCompat.QueueItem> list) {
        this.mQueue = list;
        if (list != null) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            Iterator<MediaSessionCompat.QueueItem> iterator = list.iterator();
            do {
                list = arrayList;
                if (iterator.hasNext()) {
                    arrayList.add(iterator.next().getQueueItem());
                    continue;
                }
                break;
            } while (true);
        } else {
            list = null;
        }
        MediaSessionCompatApi21.setQueue(this.mSessionObj, list);
    }

    @Override
    public void setQueueTitle(CharSequence charSequence) {
        MediaSessionCompatApi21.setQueueTitle(this.mSessionObj, charSequence);
    }

    @Override
    public void setRatingType(int n) {
        if (Build.VERSION.SDK_INT < 22) {
            this.mRatingType = n;
            return;
        }
        MediaSessionCompatApi22.setRatingType(this.mSessionObj, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void setRepeatMode(int n) {
        if (this.mRepeatMode == n) return;
        this.mRepeatMode = n;
        int n2 = this.mExtraControllerCallbacks.beginBroadcast() - 1;
        do {
            if (n2 < 0) {
                this.mExtraControllerCallbacks.finishBroadcast();
                return;
            }
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mExtraControllerCallbacks.getBroadcastItem(n2);
            try {
                iMediaControllerCallback.onRepeatModeChanged(n);
            }
            catch (RemoteException remoteException) {}
            --n2;
        } while (true);
    }

    @Override
    public void setSessionActivity(PendingIntent pendingIntent) {
        MediaSessionCompatApi21.setSessionActivity(this.mSessionObj, pendingIntent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void setShuffleMode(int n) {
        if (this.mShuffleMode == n) return;
        this.mShuffleMode = n;
        int n2 = this.mExtraControllerCallbacks.beginBroadcast() - 1;
        do {
            if (n2 < 0) {
                this.mExtraControllerCallbacks.finishBroadcast();
                return;
            }
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mExtraControllerCallbacks.getBroadcastItem(n2);
            try {
                iMediaControllerCallback.onShuffleModeChanged(n);
            }
            catch (RemoteException remoteException) {}
            --n2;
        } while (true);
    }

    class ExtraSession
    extends IMediaSession.Stub {
        ExtraSession() {
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

}
