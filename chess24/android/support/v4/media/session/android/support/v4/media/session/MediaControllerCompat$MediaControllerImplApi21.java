/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.os.ResultReceiver
 *  android.util.Log
 *  android.view.KeyEvent
 */
package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.annotation.RequiresApi;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaControllerCompatApi21;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.ParcelableVolumeInfo;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.KeyEvent;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiresApi(value=21)
static class MediaControllerCompat.MediaControllerImplApi21
implements MediaControllerCompat.MediaControllerImpl {
    private HashMap<MediaControllerCompat.Callback, ExtraCallback> mCallbackMap = new HashMap();
    protected final Object mControllerObj;
    private IMediaSession mExtraBinder;
    private final List<MediaControllerCompat.Callback> mPendingCallbacks = new ArrayList<MediaControllerCompat.Callback>();

    public MediaControllerCompat.MediaControllerImplApi21(Context context, MediaSessionCompat.Token token) throws RemoteException {
        this.mControllerObj = MediaControllerCompatApi21.fromToken(context, token.getToken());
        if (this.mControllerObj == null) {
            throw new RemoteException();
        }
        this.mExtraBinder = token.getExtraBinder();
        if (this.mExtraBinder == null) {
            this.requestExtraBinder();
        }
    }

    public MediaControllerCompat.MediaControllerImplApi21(Context context, MediaSessionCompat mediaSessionCompat) {
        this.mControllerObj = MediaControllerCompatApi21.fromToken(context, mediaSessionCompat.getSessionToken().getToken());
        this.mExtraBinder = mediaSessionCompat.getSessionToken().getExtraBinder();
        if (this.mExtraBinder == null) {
            this.requestExtraBinder();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void processPendingCallbacks() {
        if (this.mExtraBinder == null) {
            return;
        }
        List<MediaControllerCompat.Callback> list = this.mPendingCallbacks;
        synchronized (list) {
            for (MediaControllerCompat.Callback callback : this.mPendingCallbacks) {
                ExtraCallback extraCallback = new ExtraCallback(callback);
                this.mCallbackMap.put(callback, extraCallback);
                callback.mHasExtraCallback = true;
                try {
                    this.mExtraBinder.registerCallbackListener(extraCallback);
                }
                catch (RemoteException remoteException) {
                    Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in registerCallback.", (Throwable)remoteException);
                    break;
                }
                callback.onSessionReady();
            }
            this.mPendingCallbacks.clear();
            return;
        }
    }

    private void requestExtraBinder() {
        this.sendCommand(MediaControllerCompat.COMMAND_GET_EXTRA_BINDER, null, new ExtraBinderRequestResultReceiver(this, new Handler()));
    }

    @Override
    public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        if ((this.getFlags() & 4L) == 0L) {
            throw new UnsupportedOperationException("This session doesn't support queue management operations");
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(MediaControllerCompat.COMMAND_ARGUMENT_MEDIA_DESCRIPTION, (Parcelable)mediaDescriptionCompat);
        this.sendCommand(MediaControllerCompat.COMMAND_ADD_QUEUE_ITEM, bundle, null);
    }

    @Override
    public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat, int n) {
        if ((this.getFlags() & 4L) == 0L) {
            throw new UnsupportedOperationException("This session doesn't support queue management operations");
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(MediaControllerCompat.COMMAND_ARGUMENT_MEDIA_DESCRIPTION, (Parcelable)mediaDescriptionCompat);
        bundle.putInt(MediaControllerCompat.COMMAND_ARGUMENT_INDEX, n);
        this.sendCommand(MediaControllerCompat.COMMAND_ADD_QUEUE_ITEM_AT, bundle, null);
    }

    @Override
    public void adjustVolume(int n, int n2) {
        MediaControllerCompatApi21.adjustVolume(this.mControllerObj, n, n2);
    }

    @Override
    public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
        return MediaControllerCompatApi21.dispatchMediaButtonEvent(this.mControllerObj, keyEvent);
    }

    @Override
    public Bundle getExtras() {
        return MediaControllerCompatApi21.getExtras(this.mControllerObj);
    }

    @Override
    public long getFlags() {
        return MediaControllerCompatApi21.getFlags(this.mControllerObj);
    }

    @Override
    public Object getMediaController() {
        return this.mControllerObj;
    }

    @Override
    public MediaMetadataCompat getMetadata() {
        Object object = MediaControllerCompatApi21.getMetadata(this.mControllerObj);
        if (object != null) {
            return MediaMetadataCompat.fromMediaMetadata(object);
        }
        return null;
    }

    @Override
    public String getPackageName() {
        return MediaControllerCompatApi21.getPackageName(this.mControllerObj);
    }

    @Override
    public MediaControllerCompat.PlaybackInfo getPlaybackInfo() {
        Object object = MediaControllerCompatApi21.getPlaybackInfo(this.mControllerObj);
        if (object != null) {
            return new MediaControllerCompat.PlaybackInfo(MediaControllerCompatApi21.PlaybackInfo.getPlaybackType(object), MediaControllerCompatApi21.PlaybackInfo.getLegacyAudioStream(object), MediaControllerCompatApi21.PlaybackInfo.getVolumeControl(object), MediaControllerCompatApi21.PlaybackInfo.getMaxVolume(object), MediaControllerCompatApi21.PlaybackInfo.getCurrentVolume(object));
        }
        return null;
    }

    @Override
    public PlaybackStateCompat getPlaybackState() {
        Object object;
        if (this.mExtraBinder != null) {
            try {
                PlaybackStateCompat playbackStateCompat = this.mExtraBinder.getPlaybackState();
                return playbackStateCompat;
            }
            catch (RemoteException remoteException) {
                Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in getPlaybackState.", (Throwable)remoteException);
            }
        }
        if ((object = MediaControllerCompatApi21.getPlaybackState(this.mControllerObj)) != null) {
            return PlaybackStateCompat.fromPlaybackState(object);
        }
        return null;
    }

    @Override
    public List<MediaSessionCompat.QueueItem> getQueue() {
        List<Object> list = MediaControllerCompatApi21.getQueue(this.mControllerObj);
        if (list != null) {
            return MediaSessionCompat.QueueItem.fromQueueItemList(list);
        }
        return null;
    }

    @Override
    public CharSequence getQueueTitle() {
        return MediaControllerCompatApi21.getQueueTitle(this.mControllerObj);
    }

    @Override
    public int getRatingType() {
        if (Build.VERSION.SDK_INT < 22 && this.mExtraBinder != null) {
            try {
                int n = this.mExtraBinder.getRatingType();
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in getRatingType.", (Throwable)remoteException);
            }
        }
        return MediaControllerCompatApi21.getRatingType(this.mControllerObj);
    }

    @Override
    public int getRepeatMode() {
        if (this.mExtraBinder != null) {
            try {
                int n = this.mExtraBinder.getRepeatMode();
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in getRepeatMode.", (Throwable)remoteException);
            }
        }
        return -1;
    }

    @Override
    public PendingIntent getSessionActivity() {
        return MediaControllerCompatApi21.getSessionActivity(this.mControllerObj);
    }

    @Override
    public int getShuffleMode() {
        if (this.mExtraBinder != null) {
            try {
                int n = this.mExtraBinder.getShuffleMode();
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in getShuffleMode.", (Throwable)remoteException);
            }
        }
        return -1;
    }

    @Override
    public MediaControllerCompat.TransportControls getTransportControls() {
        Object object = MediaControllerCompatApi21.getTransportControls(this.mControllerObj);
        if (object != null) {
            return new MediaControllerCompat.TransportControlsApi21(object);
        }
        return null;
    }

    @Override
    public boolean isCaptioningEnabled() {
        if (this.mExtraBinder != null) {
            try {
                boolean bl = this.mExtraBinder.isCaptioningEnabled();
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in isCaptioningEnabled.", (Throwable)remoteException);
            }
        }
        return false;
    }

    @Override
    public boolean isSessionReady() {
        if (this.mExtraBinder != null) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void registerCallback(MediaControllerCompat.Callback callback, Handler object) {
        MediaControllerCompatApi21.registerCallback(this.mControllerObj, callback.mCallbackObj, object);
        if (this.mExtraBinder == null) {
            object = this.mPendingCallbacks;
            synchronized (object) {
                callback.mHasExtraCallback = false;
                this.mPendingCallbacks.add(callback);
                return;
            }
        }
        object = new ExtraCallback(callback);
        this.mCallbackMap.put(callback, (ExtraCallback)object);
        callback.mHasExtraCallback = true;
        try {
            this.mExtraBinder.registerCallbackListener((IMediaControllerCallback)object);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in registerCallback.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        if ((this.getFlags() & 4L) == 0L) {
            throw new UnsupportedOperationException("This session doesn't support queue management operations");
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(MediaControllerCompat.COMMAND_ARGUMENT_MEDIA_DESCRIPTION, (Parcelable)mediaDescriptionCompat);
        this.sendCommand(MediaControllerCompat.COMMAND_REMOVE_QUEUE_ITEM, bundle, null);
    }

    @Override
    public void sendCommand(String string, Bundle bundle, ResultReceiver resultReceiver) {
        MediaControllerCompatApi21.sendCommand(this.mControllerObj, string, bundle, resultReceiver);
    }

    @Override
    public void setVolumeTo(int n, int n2) {
        MediaControllerCompatApi21.setVolumeTo(this.mControllerObj, n, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void unregisterCallback(MediaControllerCompat.Callback callback) {
        MediaControllerCompatApi21.unregisterCallback(this.mControllerObj, callback.mCallbackObj);
        if (this.mExtraBinder == null) {
            List<MediaControllerCompat.Callback> list = this.mPendingCallbacks;
            synchronized (list) {
                this.mPendingCallbacks.remove(callback);
                return;
            }
        }
        try {
            ExtraCallback extraCallback = this.mCallbackMap.remove(callback);
            if (extraCallback == null) return;
            callback.mHasExtraCallback = false;
            this.mExtraBinder.unregisterCallbackListener(extraCallback);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in unregisterCallback.", (Throwable)remoteException);
            return;
        }
    }

    private static class ExtraBinderRequestResultReceiver
    extends ResultReceiver {
        private WeakReference<MediaControllerCompat.MediaControllerImplApi21> mMediaControllerImpl;

        public ExtraBinderRequestResultReceiver(MediaControllerCompat.MediaControllerImplApi21 mediaControllerImplApi21, Handler handler) {
            super(handler);
            this.mMediaControllerImpl = new WeakReference<MediaControllerCompat.MediaControllerImplApi21>(mediaControllerImplApi21);
        }

        protected void onReceiveResult(int n, Bundle bundle) {
            MediaControllerCompat.MediaControllerImplApi21 mediaControllerImplApi21 = (MediaControllerCompat.MediaControllerImplApi21)this.mMediaControllerImpl.get();
            if (mediaControllerImplApi21 != null) {
                if (bundle == null) {
                    return;
                }
                mediaControllerImplApi21.mExtraBinder = IMediaSession.Stub.asInterface(BundleCompat.getBinder(bundle, "android.support.v4.media.session.EXTRA_BINDER"));
                mediaControllerImplApi21.processPendingCallbacks();
                return;
            }
        }
    }

    private static class ExtraCallback
    extends MediaControllerCompat.Callback.StubCompat {
        ExtraCallback(MediaControllerCompat.Callback callback) {
            super(callback);
        }

        @Override
        public void onExtrasChanged(Bundle bundle) throws RemoteException {
            throw new AssertionError();
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException {
            throw new AssertionError();
        }

        @Override
        public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) throws RemoteException {
            throw new AssertionError();
        }

        @Override
        public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
            throw new AssertionError();
        }

        @Override
        public void onSessionDestroyed() throws RemoteException {
            throw new AssertionError();
        }

        @Override
        public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException {
            throw new AssertionError();
        }
    }

}
