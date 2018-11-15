/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 */
package android.support.v4.media.session;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaControllerCompatApi21;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.ParcelableVolumeInfo;
import android.support.v4.media.session.PlaybackStateCompat;
import java.lang.ref.WeakReference;
import java.util.List;

public static abstract class MediaControllerCompat.Callback
implements IBinder.DeathRecipient {
    private final Object mCallbackObj;
    MessageHandler mHandler;
    boolean mHasExtraCallback;

    public MediaControllerCompat.Callback() {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mCallbackObj = MediaControllerCompatApi21.createCallback(new StubApi21(this));
            return;
        }
        this.mCallbackObj = new StubCompat(this);
    }

    static /* synthetic */ Object access$000(MediaControllerCompat.Callback callback) {
        return callback.mCallbackObj;
    }

    public void binderDied() {
        this.onSessionDestroyed();
    }

    public void onAudioInfoChanged(MediaControllerCompat.PlaybackInfo playbackInfo) {
    }

    public void onCaptioningEnabledChanged(boolean bl) {
    }

    public void onExtrasChanged(Bundle bundle) {
    }

    public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
    }

    public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
    }

    public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) {
    }

    public void onQueueTitleChanged(CharSequence charSequence) {
    }

    public void onRepeatModeChanged(int n) {
    }

    public void onSessionDestroyed() {
    }

    public void onSessionEvent(String string, Bundle bundle) {
    }

    public void onSessionReady() {
    }

    public void onShuffleModeChanged(int n) {
    }

    void postToHandler(int n, Object object, Bundle bundle) {
        if (this.mHandler != null) {
            object = this.mHandler.obtainMessage(n, object);
            object.setData(bundle);
            object.sendToTarget();
        }
    }

    void setHandler(Handler handler) {
        if (handler == null) {
            if (this.mHandler != null) {
                this.mHandler.mRegistered = false;
                this.mHandler.removeCallbacksAndMessages(null);
                this.mHandler = null;
                return;
            }
        } else {
            this.mHandler = new MessageHandler(handler.getLooper());
            this.mHandler.mRegistered = true;
        }
    }

    private class MessageHandler
    extends Handler {
        private static final int MSG_DESTROYED = 8;
        private static final int MSG_EVENT = 1;
        private static final int MSG_SESSION_READY = 13;
        private static final int MSG_UPDATE_CAPTIONING_ENABLED = 11;
        private static final int MSG_UPDATE_EXTRAS = 7;
        private static final int MSG_UPDATE_METADATA = 3;
        private static final int MSG_UPDATE_PLAYBACK_STATE = 2;
        private static final int MSG_UPDATE_QUEUE = 5;
        private static final int MSG_UPDATE_QUEUE_TITLE = 6;
        private static final int MSG_UPDATE_REPEAT_MODE = 9;
        private static final int MSG_UPDATE_SHUFFLE_MODE = 12;
        private static final int MSG_UPDATE_VOLUME = 4;
        boolean mRegistered;

        MessageHandler(Looper looper) {
            super(looper);
            this.mRegistered = false;
        }

        public void handleMessage(Message message) {
            if (!this.mRegistered) {
                return;
            }
            switch (message.what) {
                default: {
                    return;
                }
                case 13: {
                    Callback.this.onSessionReady();
                    return;
                }
                case 12: {
                    Callback.this.onShuffleModeChanged((Integer)message.obj);
                    return;
                }
                case 11: {
                    Callback.this.onCaptioningEnabledChanged((Boolean)message.obj);
                    return;
                }
                case 9: {
                    Callback.this.onRepeatModeChanged((Integer)message.obj);
                    return;
                }
                case 8: {
                    Callback.this.onSessionDestroyed();
                    return;
                }
                case 7: {
                    Callback.this.onExtrasChanged((Bundle)message.obj);
                    return;
                }
                case 6: {
                    Callback.this.onQueueTitleChanged((CharSequence)message.obj);
                    return;
                }
                case 5: {
                    Callback.this.onQueueChanged((List)message.obj);
                    return;
                }
                case 4: {
                    Callback.this.onAudioInfoChanged((MediaControllerCompat.PlaybackInfo)message.obj);
                    return;
                }
                case 3: {
                    Callback.this.onMetadataChanged((MediaMetadataCompat)message.obj);
                    return;
                }
                case 2: {
                    Callback.this.onPlaybackStateChanged((PlaybackStateCompat)message.obj);
                    return;
                }
                case 1: 
            }
            Callback.this.onSessionEvent((String)message.obj, message.getData());
        }
    }

    private static class StubApi21
    implements MediaControllerCompatApi21.Callback {
        private final WeakReference<MediaControllerCompat.Callback> mCallback;

        StubApi21(MediaControllerCompat.Callback callback) {
            this.mCallback = new WeakReference<MediaControllerCompat.Callback>(callback);
        }

        @Override
        public void onAudioInfoChanged(int n, int n2, int n3, int n4, int n5) {
            MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
            if (callback != null) {
                callback.onAudioInfoChanged(new MediaControllerCompat.PlaybackInfo(n, n2, n3, n4, n5));
            }
        }

        @Override
        public void onExtrasChanged(Bundle bundle) {
            MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
            if (callback != null) {
                callback.onExtrasChanged(bundle);
            }
        }

        @Override
        public void onMetadataChanged(Object object) {
            MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
            if (callback != null) {
                callback.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata(object));
            }
        }

        @Override
        public void onPlaybackStateChanged(Object object) {
            MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
            if (callback != null) {
                if (callback.mHasExtraCallback) {
                    return;
                }
                callback.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState(object));
            }
        }

        @Override
        public void onQueueChanged(List<?> list) {
            MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
            if (callback != null) {
                callback.onQueueChanged(MediaSessionCompat.QueueItem.fromQueueItemList(list));
            }
        }

        @Override
        public void onQueueTitleChanged(CharSequence charSequence) {
            MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
            if (callback != null) {
                callback.onQueueTitleChanged(charSequence);
            }
        }

        @Override
        public void onSessionDestroyed() {
            MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
            if (callback != null) {
                callback.onSessionDestroyed();
            }
        }

        @Override
        public void onSessionEvent(String string, Bundle bundle) {
            MediaControllerCompat.Callback callback = (MediaControllerCompat.Callback)this.mCallback.get();
            if (callback != null) {
                if (callback.mHasExtraCallback && Build.VERSION.SDK_INT < 23) {
                    return;
                }
                callback.onSessionEvent(string, bundle);
            }
        }
    }

    private static class StubCompat
    extends IMediaControllerCallback.Stub {
        private final WeakReference<MediaControllerCompat.Callback> mCallback;

        StubCompat(MediaControllerCompat.Callback callback) {
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

}
