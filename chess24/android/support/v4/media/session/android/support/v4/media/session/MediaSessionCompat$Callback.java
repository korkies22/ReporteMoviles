/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.BadParcelableException
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Parcelable
 *  android.os.ResultReceiver
 *  android.util.Log
 *  android.view.KeyEvent
 *  android.view.ViewConfiguration
 */
package android.support.v4.media.session;

import android.content.Intent;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.annotation.RequiresApi;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.MediaSessionCompatApi21;
import android.support.v4.media.session.MediaSessionCompatApi23;
import android.support.v4.media.session.MediaSessionCompatApi24;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import java.lang.ref.WeakReference;
import java.util.List;

public static abstract class MediaSessionCompat.Callback {
    private CallbackHandler mCallbackHandler = null;
    final Object mCallbackObj;
    private boolean mMediaPlayPauseKeyPending;
    private WeakReference<MediaSessionCompat.MediaSessionImpl> mSessionImpl;

    public MediaSessionCompat.Callback() {
        if (Build.VERSION.SDK_INT >= 24) {
            this.mCallbackObj = MediaSessionCompatApi24.createCallback(new StubApi24());
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            this.mCallbackObj = MediaSessionCompatApi23.createCallback(new StubApi23());
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mCallbackObj = MediaSessionCompatApi21.createCallback(new StubApi21());
            return;
        }
        this.mCallbackObj = null;
    }

    static /* synthetic */ void access$300(MediaSessionCompat.Callback callback, MediaSessionCompat.MediaSessionImpl mediaSessionImpl, Handler handler) {
        callback.setSessionImpl(mediaSessionImpl, handler);
    }

    private void handleMediaPlayPauseKeySingleTapIfPending() {
        if (!this.mMediaPlayPauseKeyPending) {
            return;
        }
        boolean bl = false;
        this.mMediaPlayPauseKeyPending = false;
        this.mCallbackHandler.removeMessages(1);
        Object object = (MediaSessionCompat.MediaSessionImpl)this.mSessionImpl.get();
        if (object == null) {
            return;
        }
        long l = (object = object.getPlaybackState()) == null ? 0L : object.getActions();
        boolean bl2 = object != null && object.getState() == 3;
        boolean bl3 = (l & 516L) != 0L;
        if ((l & 514L) != 0L) {
            bl = true;
        }
        if (bl2 && bl) {
            this.onPause();
            return;
        }
        if (!bl2 && bl3) {
            this.onPlay();
        }
    }

    private void setSessionImpl(MediaSessionCompat.MediaSessionImpl mediaSessionImpl, Handler handler) {
        this.mSessionImpl = new WeakReference<MediaSessionCompat.MediaSessionImpl>(mediaSessionImpl);
        if (this.mCallbackHandler != null) {
            this.mCallbackHandler.removeCallbacksAndMessages(null);
        }
        this.mCallbackHandler = new CallbackHandler(handler.getLooper());
    }

    public void onAddQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
    }

    public void onAddQueueItem(MediaDescriptionCompat mediaDescriptionCompat, int n) {
    }

    public void onCommand(String string, Bundle bundle, ResultReceiver resultReceiver) {
    }

    public void onCustomAction(String string, Bundle bundle) {
    }

    public void onFastForward() {
    }

    public boolean onMediaButtonEvent(Intent object) {
        MediaSessionCompat.MediaSessionImpl mediaSessionImpl = (MediaSessionCompat.MediaSessionImpl)this.mSessionImpl.get();
        if (mediaSessionImpl != null) {
            if (this.mCallbackHandler == null) {
                return false;
            }
            if ((object = (KeyEvent)object.getParcelableExtra("android.intent.extra.KEY_EVENT")) != null) {
                if (object.getAction() != 0) {
                    return false;
                }
                int n = object.getKeyCode();
                if (n != 79 && n != 85) {
                    this.handleMediaPlayPauseKeySingleTapIfPending();
                    return false;
                }
                if (object.getRepeatCount() > 0) {
                    this.handleMediaPlayPauseKeySingleTapIfPending();
                    return true;
                }
                if (this.mMediaPlayPauseKeyPending) {
                    this.mCallbackHandler.removeMessages(1);
                    this.mMediaPlayPauseKeyPending = false;
                    object = mediaSessionImpl.getPlaybackState();
                    long l = object == null ? 0L : object.getActions();
                    if ((l & 32L) != 0L) {
                        this.onSkipToNext();
                        return true;
                    }
                } else {
                    this.mMediaPlayPauseKeyPending = true;
                    this.mCallbackHandler.sendEmptyMessageDelayed(1, (long)ViewConfiguration.getDoubleTapTimeout());
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public void onPause() {
    }

    public void onPlay() {
    }

    public void onPlayFromMediaId(String string, Bundle bundle) {
    }

    public void onPlayFromSearch(String string, Bundle bundle) {
    }

    public void onPlayFromUri(Uri uri, Bundle bundle) {
    }

    public void onPrepare() {
    }

    public void onPrepareFromMediaId(String string, Bundle bundle) {
    }

    public void onPrepareFromSearch(String string, Bundle bundle) {
    }

    public void onPrepareFromUri(Uri uri, Bundle bundle) {
    }

    public void onRemoveQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
    }

    @Deprecated
    public void onRemoveQueueItemAt(int n) {
    }

    public void onRewind() {
    }

    public void onSeekTo(long l) {
    }

    public void onSetCaptioningEnabled(boolean bl) {
    }

    public void onSetRating(RatingCompat ratingCompat) {
    }

    public void onSetRating(RatingCompat ratingCompat, Bundle bundle) {
    }

    public void onSetRepeatMode(int n) {
    }

    public void onSetShuffleMode(int n) {
    }

    public void onSkipToNext() {
    }

    public void onSkipToPrevious() {
    }

    public void onSkipToQueueItem(long l) {
    }

    public void onStop() {
    }

    private class CallbackHandler
    extends Handler {
        private static final int MSG_MEDIA_PLAY_PAUSE_KEY_DOUBLE_TAP_TIMEOUT = 1;

        CallbackHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            if (message.what == 1) {
                Callback.this.handleMediaPlayPauseKeySingleTapIfPending();
            }
        }
    }

    @RequiresApi(value=21)
    private class StubApi21
    implements MediaSessionCompatApi21.Callback {
        StubApi21() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onCommand(String object, Bundle bundle, ResultReceiver object2) {
            try {
                boolean bl = object.equals("android.support.v4.media.session.command.GET_EXTRA_BINDER");
                Object var7_6 = null;
                Object var6_7 = null;
                if (bl) {
                    object = (MediaSessionCompat.MediaSessionImplApi21)Callback.this.mSessionImpl.get();
                    if (object == null) return;
                    bundle = new Bundle();
                    object = (object = object.getSessionToken().getExtraBinder()) == null ? var6_7 : object.asBinder();
                    BundleCompat.putBinder(bundle, MediaSessionCompat.EXTRA_BINDER, (IBinder)object);
                    object2.send(0, bundle);
                    return;
                }
                if (object.equals("android.support.v4.media.session.command.ADD_QUEUE_ITEM")) {
                    bundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
                    Callback.this.onAddQueueItem((MediaDescriptionCompat)bundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"));
                    return;
                }
                if (object.equals("android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT")) {
                    bundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
                    Callback.this.onAddQueueItem((MediaDescriptionCompat)bundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"), bundle.getInt("android.support.v4.media.session.command.ARGUMENT_INDEX"));
                    return;
                }
                if (object.equals("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM")) {
                    bundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
                    Callback.this.onRemoveQueueItem((MediaDescriptionCompat)bundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"));
                    return;
                }
                if (!object.equals("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM_AT")) {
                    Callback.this.onCommand((String)object, bundle, (ResultReceiver)object2);
                    return;
                }
                object2 = (MediaSessionCompat.MediaSessionImplApi21)Callback.this.mSessionImpl.get();
                if (object2 == null) return;
                if (((MediaSessionCompat.MediaSessionImplApi21)object2).mQueue == null) return;
                int n = bundle.getInt("android.support.v4.media.session.command.ARGUMENT_INDEX", -1);
                object = var7_6;
                if (n >= 0) {
                    object = var7_6;
                    if (n < ((MediaSessionCompat.MediaSessionImplApi21)object2).mQueue.size()) {
                        object = (MediaSessionCompat.QueueItem)((MediaSessionCompat.MediaSessionImplApi21)object2).mQueue.get(n);
                    }
                }
                if (object == null) return;
                Callback.this.onRemoveQueueItem(object.getDescription());
                return;
            }
            catch (BadParcelableException badParcelableException) {}
            Log.e((String)MediaSessionCompat.TAG, (String)"Could not unparcel the extra data.");
        }

        @Override
        public void onCustomAction(String object, Bundle bundle) {
            if (object.equals(MediaSessionCompat.ACTION_PLAY_FROM_URI)) {
                object = (Uri)bundle.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI);
                bundle = (Bundle)bundle.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS);
                Callback.this.onPlayFromUri((Uri)object, bundle);
                return;
            }
            if (object.equals(MediaSessionCompat.ACTION_PREPARE)) {
                Callback.this.onPrepare();
                return;
            }
            if (object.equals(MediaSessionCompat.ACTION_PREPARE_FROM_MEDIA_ID)) {
                object = bundle.getString(MediaSessionCompat.ACTION_ARGUMENT_MEDIA_ID);
                bundle = bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS);
                Callback.this.onPrepareFromMediaId((String)object, bundle);
                return;
            }
            if (object.equals(MediaSessionCompat.ACTION_PREPARE_FROM_SEARCH)) {
                object = bundle.getString(MediaSessionCompat.ACTION_ARGUMENT_QUERY);
                bundle = bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS);
                Callback.this.onPrepareFromSearch((String)object, bundle);
                return;
            }
            if (object.equals(MediaSessionCompat.ACTION_PREPARE_FROM_URI)) {
                object = (Uri)bundle.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI);
                bundle = bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS);
                Callback.this.onPrepareFromUri((Uri)object, bundle);
                return;
            }
            if (object.equals(MediaSessionCompat.ACTION_SET_CAPTIONING_ENABLED)) {
                boolean bl = bundle.getBoolean(MediaSessionCompat.ACTION_ARGUMENT_CAPTIONING_ENABLED);
                Callback.this.onSetCaptioningEnabled(bl);
                return;
            }
            if (object.equals(MediaSessionCompat.ACTION_SET_REPEAT_MODE)) {
                int n = bundle.getInt(MediaSessionCompat.ACTION_ARGUMENT_REPEAT_MODE);
                Callback.this.onSetRepeatMode(n);
                return;
            }
            if (object.equals(MediaSessionCompat.ACTION_SET_SHUFFLE_MODE)) {
                int n = bundle.getInt(MediaSessionCompat.ACTION_ARGUMENT_SHUFFLE_MODE);
                Callback.this.onSetShuffleMode(n);
                return;
            }
            if (object.equals(MediaSessionCompat.ACTION_SET_RATING)) {
                bundle.setClassLoader(RatingCompat.class.getClassLoader());
                object = (RatingCompat)bundle.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_RATING);
                bundle = bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS);
                Callback.this.onSetRating((RatingCompat)object, bundle);
                return;
            }
            Callback.this.onCustomAction((String)object, bundle);
        }

        @Override
        public void onFastForward() {
            Callback.this.onFastForward();
        }

        @Override
        public boolean onMediaButtonEvent(Intent intent) {
            return Callback.this.onMediaButtonEvent(intent);
        }

        @Override
        public void onPause() {
            Callback.this.onPause();
        }

        @Override
        public void onPlay() {
            Callback.this.onPlay();
        }

        @Override
        public void onPlayFromMediaId(String string, Bundle bundle) {
            Callback.this.onPlayFromMediaId(string, bundle);
        }

        @Override
        public void onPlayFromSearch(String string, Bundle bundle) {
            Callback.this.onPlayFromSearch(string, bundle);
        }

        @Override
        public void onRewind() {
            Callback.this.onRewind();
        }

        @Override
        public void onSeekTo(long l) {
            Callback.this.onSeekTo(l);
        }

        @Override
        public void onSetRating(Object object) {
            Callback.this.onSetRating(RatingCompat.fromRating(object));
        }

        @Override
        public void onSetRating(Object object, Bundle bundle) {
            Callback.this.onSetRating(RatingCompat.fromRating(object), bundle);
        }

        @Override
        public void onSkipToNext() {
            Callback.this.onSkipToNext();
        }

        @Override
        public void onSkipToPrevious() {
            Callback.this.onSkipToPrevious();
        }

        @Override
        public void onSkipToQueueItem(long l) {
            Callback.this.onSkipToQueueItem(l);
        }

        @Override
        public void onStop() {
            Callback.this.onStop();
        }
    }

    @RequiresApi(value=23)
    private class StubApi23
    extends StubApi21
    implements MediaSessionCompatApi23.Callback {
        StubApi23() {
        }

        @Override
        public void onPlayFromUri(Uri uri, Bundle bundle) {
            Callback.this.onPlayFromUri(uri, bundle);
        }
    }

    @RequiresApi(value=24)
    private class StubApi24
    extends StubApi23
    implements MediaSessionCompatApi24.Callback {
        StubApi24() {
        }

        @Override
        public void onPrepare() {
            Callback.this.onPrepare();
        }

        @Override
        public void onPrepareFromMediaId(String string, Bundle bundle) {
            Callback.this.onPrepareFromMediaId(string, bundle);
        }

        @Override
        public void onPrepareFromSearch(String string, Bundle bundle) {
            Callback.this.onPrepareFromSearch(string, bundle);
        }

        @Override
        public void onPrepareFromUri(Uri uri, Bundle bundle) {
            Callback.this.onPrepareFromUri(uri, bundle);
        }
    }

}
