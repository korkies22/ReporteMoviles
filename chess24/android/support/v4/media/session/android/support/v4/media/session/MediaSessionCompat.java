/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.media.AudioManager
 *  android.media.MediaMetadataEditor
 *  android.media.Rating
 *  android.media.RemoteControlClient
 *  android.media.RemoteControlClient$MetadataEditor
 *  android.media.RemoteControlClient$OnMetadataUpdateListener
 *  android.media.RemoteControlClient$OnPlaybackPositionUpdateListener
 *  android.net.Uri
 *  android.os.BadParcelableException
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteCallbackList
 *  android.os.RemoteException
 *  android.os.ResultReceiver
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.KeyEvent
 *  android.view.ViewConfiguration
 */
package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaMetadataEditor;
import android.media.Rating;
import android.media.RemoteControlClient;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompatApi21;
import android.support.v4.media.session.MediaSessionCompatApi22;
import android.support.v4.media.session.MediaSessionCompatApi23;
import android.support.v4.media.session.MediaSessionCompatApi24;
import android.support.v4.media.session.ParcelableVolumeInfo;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MediaSessionCompat {
    static final String ACTION_ARGUMENT_CAPTIONING_ENABLED = "android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED";
    static final String ACTION_ARGUMENT_EXTRAS = "android.support.v4.media.session.action.ARGUMENT_EXTRAS";
    static final String ACTION_ARGUMENT_MEDIA_ID = "android.support.v4.media.session.action.ARGUMENT_MEDIA_ID";
    static final String ACTION_ARGUMENT_QUERY = "android.support.v4.media.session.action.ARGUMENT_QUERY";
    static final String ACTION_ARGUMENT_RATING = "android.support.v4.media.session.action.ARGUMENT_RATING";
    static final String ACTION_ARGUMENT_REPEAT_MODE = "android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE";
    static final String ACTION_ARGUMENT_SHUFFLE_MODE = "android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE";
    static final String ACTION_ARGUMENT_URI = "android.support.v4.media.session.action.ARGUMENT_URI";
    public static final String ACTION_FLAG_AS_INAPPROPRIATE = "android.support.v4.media.session.action.FLAG_AS_INAPPROPRIATE";
    public static final String ACTION_FOLLOW = "android.support.v4.media.session.action.FOLLOW";
    static final String ACTION_PLAY_FROM_URI = "android.support.v4.media.session.action.PLAY_FROM_URI";
    static final String ACTION_PREPARE = "android.support.v4.media.session.action.PREPARE";
    static final String ACTION_PREPARE_FROM_MEDIA_ID = "android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID";
    static final String ACTION_PREPARE_FROM_SEARCH = "android.support.v4.media.session.action.PREPARE_FROM_SEARCH";
    static final String ACTION_PREPARE_FROM_URI = "android.support.v4.media.session.action.PREPARE_FROM_URI";
    static final String ACTION_SET_CAPTIONING_ENABLED = "android.support.v4.media.session.action.SET_CAPTIONING_ENABLED";
    static final String ACTION_SET_RATING = "android.support.v4.media.session.action.SET_RATING";
    static final String ACTION_SET_REPEAT_MODE = "android.support.v4.media.session.action.SET_REPEAT_MODE";
    static final String ACTION_SET_SHUFFLE_MODE = "android.support.v4.media.session.action.SET_SHUFFLE_MODE";
    public static final String ACTION_SKIP_AD = "android.support.v4.media.session.action.SKIP_AD";
    public static final String ACTION_UNFOLLOW = "android.support.v4.media.session.action.UNFOLLOW";
    public static final String ARGUMENT_MEDIA_ATTRIBUTE = "android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE";
    public static final String ARGUMENT_MEDIA_ATTRIBUTE_VALUE = "android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE_VALUE";
    static final String EXTRA_BINDER = "android.support.v4.media.session.EXTRA_BINDER";
    public static final int FLAG_HANDLES_MEDIA_BUTTONS = 1;
    public static final int FLAG_HANDLES_QUEUE_COMMANDS = 4;
    public static final int FLAG_HANDLES_TRANSPORT_CONTROLS = 2;
    private static final int MAX_BITMAP_SIZE_IN_DP = 320;
    public static final int MEDIA_ATTRIBUTE_ALBUM = 1;
    public static final int MEDIA_ATTRIBUTE_ARTIST = 0;
    public static final int MEDIA_ATTRIBUTE_PLAYLIST = 2;
    static final String TAG = "MediaSessionCompat";
    static int sMaxBitmapSize;
    private final ArrayList<OnActiveChangeListener> mActiveListeners = new ArrayList();
    private final MediaControllerCompat mController;
    private final MediaSessionImpl mImpl;

    private MediaSessionCompat(Context context, MediaSessionImpl mediaSessionImpl) {
        this.mImpl = mediaSessionImpl;
        if (Build.VERSION.SDK_INT >= 21 && !MediaSessionCompatApi21.hasCallback(mediaSessionImpl.getMediaSession())) {
            this.setCallback(new Callback(){});
        }
        this.mController = new MediaControllerCompat(context, this);
    }

    public MediaSessionCompat(Context context, String string) {
        this(context, string, null, null);
    }

    public MediaSessionCompat(Context context, String string, ComponentName componentName, PendingIntent pendingIntent) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        if (TextUtils.isEmpty((CharSequence)string)) {
            throw new IllegalArgumentException("tag must not be null or empty");
        }
        ComponentName componentName2 = componentName;
        if (componentName == null) {
            componentName2 = componentName = MediaButtonReceiver.getMediaButtonReceiverComponent(context);
            if (componentName == null) {
                Log.w((String)TAG, (String)"Couldn't find a unique registered media button receiver in the given context.");
                componentName2 = componentName;
            }
        }
        componentName = pendingIntent;
        if (componentName2 != null) {
            componentName = pendingIntent;
            if (pendingIntent == null) {
                componentName = new Intent("android.intent.action.MEDIA_BUTTON");
                componentName.setComponent(componentName2);
                componentName = PendingIntent.getBroadcast((Context)context, (int)0, (Intent)componentName, (int)0);
            }
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaSessionImplApi21(context, string);
            this.setCallback(new Callback(){});
            this.mImpl.setMediaButtonReceiver((PendingIntent)componentName);
        } else {
            this.mImpl = Build.VERSION.SDK_INT >= 19 ? new MediaSessionImplApi19(context, string, componentName2, (PendingIntent)componentName) : (Build.VERSION.SDK_INT >= 18 ? new MediaSessionImplApi18(context, string, componentName2, (PendingIntent)componentName) : new MediaSessionImplBase(context, string, componentName2, (PendingIntent)componentName));
        }
        this.mController = new MediaControllerCompat(context, this);
        if (sMaxBitmapSize == 0) {
            sMaxBitmapSize = (int)TypedValue.applyDimension((int)1, (float)320.0f, (DisplayMetrics)context.getResources().getDisplayMetrics());
        }
    }

    public static MediaSessionCompat fromMediaSession(Context context, Object object) {
        if (context != null && object != null && Build.VERSION.SDK_INT >= 21) {
            return new MediaSessionCompat(context, new MediaSessionImplApi21(object));
        }
        return null;
    }

    private static PlaybackStateCompat getStateWithUpdatedPosition(PlaybackStateCompat playbackStateCompat, MediaMetadataCompat mediaMetadataCompat) {
        if (playbackStateCompat != null) {
            long l = playbackStateCompat.getPosition();
            long l2 = -1L;
            if (l == -1L) {
                return playbackStateCompat;
            }
            if ((playbackStateCompat.getState() == 3 || playbackStateCompat.getState() == 4 || playbackStateCompat.getState() == 5) && (l = playbackStateCompat.getLastPositionUpdateTime()) > 0L) {
                long l3 = SystemClock.elapsedRealtime();
                long l4 = (long)(playbackStateCompat.getPlaybackSpeed() * (float)(l3 - l)) + playbackStateCompat.getPosition();
                l = l2;
                if (mediaMetadataCompat != null) {
                    l = l2;
                    if (mediaMetadataCompat.containsKey("android.media.metadata.DURATION")) {
                        l = mediaMetadataCompat.getLong("android.media.metadata.DURATION");
                    }
                }
                if (l < 0L || l4 <= l) {
                    l = l4 < 0L ? 0L : l4;
                }
                return new PlaybackStateCompat.Builder(playbackStateCompat).setState(playbackStateCompat.getState(), l, playbackStateCompat.getPlaybackSpeed(), l3).build();
            }
            return playbackStateCompat;
        }
        return playbackStateCompat;
    }

    public void addOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        if (onActiveChangeListener == null) {
            throw new IllegalArgumentException("Listener may not be null");
        }
        this.mActiveListeners.add(onActiveChangeListener);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public String getCallingPackage() {
        return this.mImpl.getCallingPackage();
    }

    public MediaControllerCompat getController() {
        return this.mController;
    }

    public Object getMediaSession() {
        return this.mImpl.getMediaSession();
    }

    public Object getRemoteControlClient() {
        return this.mImpl.getRemoteControlClient();
    }

    public Token getSessionToken() {
        return this.mImpl.getSessionToken();
    }

    public boolean isActive() {
        return this.mImpl.isActive();
    }

    public void release() {
        this.mImpl.release();
    }

    public void removeOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        if (onActiveChangeListener == null) {
            throw new IllegalArgumentException("Listener may not be null");
        }
        this.mActiveListeners.remove(onActiveChangeListener);
    }

    public void sendSessionEvent(String string, Bundle bundle) {
        if (TextUtils.isEmpty((CharSequence)string)) {
            throw new IllegalArgumentException("event cannot be null or empty");
        }
        this.mImpl.sendSessionEvent(string, bundle);
    }

    public void setActive(boolean bl) {
        this.mImpl.setActive(bl);
        Iterator<OnActiveChangeListener> iterator = this.mActiveListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onActiveChanged();
        }
    }

    public void setCallback(Callback callback) {
        this.setCallback(callback, null);
    }

    public void setCallback(Callback callback, Handler handler) {
        MediaSessionImpl mediaSessionImpl = this.mImpl;
        if (handler == null) {
            handler = new Handler();
        }
        mediaSessionImpl.setCallback(callback, handler);
    }

    public void setCaptioningEnabled(boolean bl) {
        this.mImpl.setCaptioningEnabled(bl);
    }

    public void setExtras(Bundle bundle) {
        this.mImpl.setExtras(bundle);
    }

    public void setFlags(int n) {
        this.mImpl.setFlags(n);
    }

    public void setMediaButtonReceiver(PendingIntent pendingIntent) {
        this.mImpl.setMediaButtonReceiver(pendingIntent);
    }

    public void setMetadata(MediaMetadataCompat mediaMetadataCompat) {
        this.mImpl.setMetadata(mediaMetadataCompat);
    }

    public void setPlaybackState(PlaybackStateCompat playbackStateCompat) {
        this.mImpl.setPlaybackState(playbackStateCompat);
    }

    public void setPlaybackToLocal(int n) {
        this.mImpl.setPlaybackToLocal(n);
    }

    public void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
        if (volumeProviderCompat == null) {
            throw new IllegalArgumentException("volumeProvider may not be null!");
        }
        this.mImpl.setPlaybackToRemote(volumeProviderCompat);
    }

    public void setQueue(List<QueueItem> list) {
        this.mImpl.setQueue(list);
    }

    public void setQueueTitle(CharSequence charSequence) {
        this.mImpl.setQueueTitle(charSequence);
    }

    public void setRatingType(int n) {
        this.mImpl.setRatingType(n);
    }

    public void setRepeatMode(int n) {
        this.mImpl.setRepeatMode(n);
    }

    public void setSessionActivity(PendingIntent pendingIntent) {
        this.mImpl.setSessionActivity(pendingIntent);
    }

    public void setShuffleMode(int n) {
        this.mImpl.setShuffleMode(n);
    }

    public static abstract class Callback {
        private Callback$CallbackHandler mCallbackHandler = null;
        final Object mCallbackObj;
        private boolean mMediaPlayPauseKeyPending;
        private WeakReference<MediaSessionImpl> mSessionImpl;

        public Callback() {
            if (Build.VERSION.SDK_INT >= 24) {
                this.mCallbackObj = MediaSessionCompatApi24.createCallback(new Callback$StubApi24());
                return;
            }
            if (Build.VERSION.SDK_INT >= 23) {
                this.mCallbackObj = MediaSessionCompatApi23.createCallback(new Callback$StubApi23());
                return;
            }
            if (Build.VERSION.SDK_INT >= 21) {
                this.mCallbackObj = MediaSessionCompatApi21.createCallback(new Callback$StubApi21());
                return;
            }
            this.mCallbackObj = null;
        }

        private void handleMediaPlayPauseKeySingleTapIfPending() {
            if (!this.mMediaPlayPauseKeyPending) {
                return;
            }
            boolean bl = false;
            this.mMediaPlayPauseKeyPending = false;
            this.mCallbackHandler.removeMessages(1);
            Object object = (MediaSessionImpl)this.mSessionImpl.get();
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

        private void setSessionImpl(MediaSessionImpl mediaSessionImpl, Handler handler) {
            this.mSessionImpl = new WeakReference<MediaSessionImpl>(mediaSessionImpl);
            if (this.mCallbackHandler != null) {
                this.mCallbackHandler.removeCallbacksAndMessages(null);
            }
            this.mCallbackHandler = new Callback$CallbackHandler(handler.getLooper());
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
            MediaSessionImpl mediaSessionImpl = (MediaSessionImpl)this.mSessionImpl.get();
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
    }

    private class Callback$CallbackHandler
    extends Handler {
        private static final int MSG_MEDIA_PLAY_PAUSE_KEY_DOUBLE_TAP_TIMEOUT = 1;

        Callback$CallbackHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            if (message.what == 1) {
                Callback.this.handleMediaPlayPauseKeySingleTapIfPending();
            }
        }
    }

    @RequiresApi(value=21)
    private class Callback$StubApi21
    implements MediaSessionCompatApi21.Callback {
        Callback$StubApi21() {
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
                    object = (MediaSessionImplApi21)Callback.this.mSessionImpl.get();
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
                object2 = (MediaSessionImplApi21)Callback.this.mSessionImpl.get();
                if (object2 == null) return;
                if (((MediaSessionImplApi21)object2).mQueue == null) return;
                int n = bundle.getInt("android.support.v4.media.session.command.ARGUMENT_INDEX", -1);
                object = var7_6;
                if (n >= 0) {
                    object = var7_6;
                    if (n < ((MediaSessionImplApi21)object2).mQueue.size()) {
                        object = (QueueItem)((MediaSessionImplApi21)object2).mQueue.get(n);
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
    private class Callback$StubApi23
    extends Callback$StubApi21
    implements MediaSessionCompatApi23.Callback {
        Callback$StubApi23() {
        }

        @Override
        public void onPlayFromUri(Uri uri, Bundle bundle) {
            Callback.this.onPlayFromUri(uri, bundle);
        }
    }

    @RequiresApi(value=24)
    private class Callback$StubApi24
    extends Callback$StubApi23
    implements MediaSessionCompatApi24.Callback {
        Callback$StubApi24() {
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

    static interface MediaSessionImpl {
        public String getCallingPackage();

        public Object getMediaSession();

        public PlaybackStateCompat getPlaybackState();

        public Object getRemoteControlClient();

        public Token getSessionToken();

        public boolean isActive();

        public void release();

        public void sendSessionEvent(String var1, Bundle var2);

        public void setActive(boolean var1);

        public void setCallback(Callback var1, Handler var2);

        public void setCaptioningEnabled(boolean var1);

        public void setExtras(Bundle var1);

        public void setFlags(int var1);

        public void setMediaButtonReceiver(PendingIntent var1);

        public void setMetadata(MediaMetadataCompat var1);

        public void setPlaybackState(PlaybackStateCompat var1);

        public void setPlaybackToLocal(int var1);

        public void setPlaybackToRemote(VolumeProviderCompat var1);

        public void setQueue(List<QueueItem> var1);

        public void setQueueTitle(CharSequence var1);

        public void setRatingType(int var1);

        public void setRepeatMode(int var1);

        public void setSessionActivity(PendingIntent var1);

        public void setShuffleMode(int var1);
    }

    @RequiresApi(value=18)
    static class MediaSessionImplApi18
    extends MediaSessionImplBase {
        private static boolean sIsMbrPendingIntentSupported = true;

        MediaSessionImplApi18(Context context, String string, ComponentName componentName, PendingIntent pendingIntent) {
            super(context, string, componentName, pendingIntent);
        }

        @Override
        int getRccTransportControlFlagsFromActions(long l) {
            int n;
            int n2 = n = super.getRccTransportControlFlagsFromActions(l);
            if ((l & 256L) != 0L) {
                n2 = n | 256;
            }
            return n2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        void registerMediaButtonEventReceiver(PendingIntent pendingIntent, ComponentName componentName) {
            block4 : {
                if (sIsMbrPendingIntentSupported) {
                    try {
                        this.mAudioManager.registerMediaButtonEventReceiver(pendingIntent);
                        break block4;
                    }
                    catch (NullPointerException nullPointerException) {}
                    Log.w((String)MediaSessionCompat.TAG, (String)"Unable to register media button event receiver with PendingIntent, falling back to ComponentName.");
                    sIsMbrPendingIntentSupported = false;
                }
            }
            if (!sIsMbrPendingIntentSupported) {
                super.registerMediaButtonEventReceiver(pendingIntent, componentName);
            }
        }

        @Override
        public void setCallback(Callback object, Handler handler) {
            super.setCallback((Callback)object, handler);
            if (object == null) {
                this.mRcc.setPlaybackPositionUpdateListener(null);
                return;
            }
            object = new RemoteControlClient.OnPlaybackPositionUpdateListener(){

                public void onPlaybackPositionUpdate(long l) {
                    MediaSessionImplApi18.this.postToHandler(18, l);
                }
            };
            this.mRcc.setPlaybackPositionUpdateListener((RemoteControlClient.OnPlaybackPositionUpdateListener)object);
        }

        @Override
        void setRccState(PlaybackStateCompat playbackStateCompat) {
            long l = playbackStateCompat.getPosition();
            float f = playbackStateCompat.getPlaybackSpeed();
            long l2 = playbackStateCompat.getLastPositionUpdateTime();
            long l3 = SystemClock.elapsedRealtime();
            long l4 = l;
            if (playbackStateCompat.getState() == 3) {
                long l5 = 0L;
                l4 = l;
                if (l > 0L) {
                    l4 = l5;
                    if (l2 > 0L) {
                        l4 = l5 = l3 - l2;
                        if (f > 0.0f) {
                            l4 = l5;
                            if (f != 1.0f) {
                                l4 = (long)((float)l5 * f);
                            }
                        }
                    }
                    l4 = l + l4;
                }
            }
            this.mRcc.setPlaybackState(this.getRccStateFromState(playbackStateCompat.getState()), l4, f);
        }

        @Override
        void unregisterMediaButtonEventReceiver(PendingIntent pendingIntent, ComponentName componentName) {
            if (sIsMbrPendingIntentSupported) {
                this.mAudioManager.unregisterMediaButtonEventReceiver(pendingIntent);
                return;
            }
            super.unregisterMediaButtonEventReceiver(pendingIntent, componentName);
        }

    }

    @RequiresApi(value=19)
    static class MediaSessionImplApi19
    extends MediaSessionImplApi18 {
        MediaSessionImplApi19(Context context, String string, ComponentName componentName, PendingIntent pendingIntent) {
            super(context, string, componentName, pendingIntent);
        }

        @Override
        RemoteControlClient.MetadataEditor buildRccMetadata(Bundle bundle) {
            RemoteControlClient.MetadataEditor metadataEditor = super.buildRccMetadata(bundle);
            long l = this.mState == null ? 0L : this.mState.getActions();
            if ((l & 128L) != 0L) {
                metadataEditor.addEditableKey(268435457);
            }
            if (bundle == null) {
                return metadataEditor;
            }
            if (bundle.containsKey("android.media.metadata.YEAR")) {
                metadataEditor.putLong(8, bundle.getLong("android.media.metadata.YEAR"));
            }
            if (bundle.containsKey("android.media.metadata.RATING")) {
                metadataEditor.putObject(101, (Object)bundle.getParcelable("android.media.metadata.RATING"));
            }
            if (bundle.containsKey("android.media.metadata.USER_RATING")) {
                metadataEditor.putObject(268435457, (Object)bundle.getParcelable("android.media.metadata.USER_RATING"));
            }
            return metadataEditor;
        }

        @Override
        int getRccTransportControlFlagsFromActions(long l) {
            int n;
            int n2 = n = super.getRccTransportControlFlagsFromActions(l);
            if ((l & 128L) != 0L) {
                n2 = n | 512;
            }
            return n2;
        }

        @Override
        public void setCallback(Callback object, Handler handler) {
            super.setCallback((Callback)object, handler);
            if (object == null) {
                this.mRcc.setMetadataUpdateListener(null);
                return;
            }
            object = new RemoteControlClient.OnMetadataUpdateListener(){

                public void onMetadataUpdate(int n, Object object) {
                    if (n == 268435457 && object instanceof Rating) {
                        MediaSessionImplApi19.this.postToHandler(19, RatingCompat.fromRating(object));
                    }
                }
            };
            this.mRcc.setMetadataUpdateListener((RemoteControlClient.OnMetadataUpdateListener)object);
        }

    }

    @RequiresApi(value=21)
    static class MediaSessionImplApi21
    implements MediaSessionImpl {
        boolean mCaptioningEnabled;
        private boolean mDestroyed = false;
        private final RemoteCallbackList<IMediaControllerCallback> mExtraControllerCallbacks = new RemoteCallbackList();
        private MediaMetadataCompat mMetadata;
        private PlaybackStateCompat mPlaybackState;
        private List<QueueItem> mQueue;
        int mRatingType;
        int mRepeatMode;
        private final Object mSessionObj;
        int mShuffleMode;
        private final Token mToken;

        public MediaSessionImplApi21(Context context, String string) {
            this.mSessionObj = MediaSessionCompatApi21.createSession(context, string);
            this.mToken = new Token((Object)MediaSessionCompatApi21.getSessionToken(this.mSessionObj), new MediaSessionImplApi21$ExtraSession());
        }

        public MediaSessionImplApi21(Object object) {
            this.mSessionObj = MediaSessionCompatApi21.verifySession(object);
            this.mToken = new Token((Object)MediaSessionCompatApi21.getSessionToken(this.mSessionObj), new MediaSessionImplApi21$ExtraSession());
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
        public Token getSessionToken() {
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
        public void setCallback(Callback callback, Handler handler) {
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
        public void setQueue(List<QueueItem> list) {
            this.mQueue = list;
            if (list != null) {
                ArrayList<Object> arrayList = new ArrayList<Object>();
                Iterator<QueueItem> iterator = list.iterator();
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
    }

    class MediaSessionImplApi21$ExtraSession
    extends IMediaSession.Stub {
        MediaSessionImplApi21$ExtraSession() {
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
        public List<QueueItem> getQueue() {
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
        public void sendCommand(String string, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) {
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

    static class MediaSessionImplBase
    implements MediaSessionImpl {
        static final int RCC_PLAYSTATE_NONE = 0;
        final AudioManager mAudioManager;
        volatile Callback mCallback;
        boolean mCaptioningEnabled;
        private final Context mContext;
        final RemoteCallbackList<IMediaControllerCallback> mControllerCallbacks = new RemoteCallbackList();
        boolean mDestroyed = false;
        Bundle mExtras;
        int mFlags;
        private MediaSessionImplBase$MessageHandler mHandler;
        boolean mIsActive = false;
        private boolean mIsMbrRegistered = false;
        private boolean mIsRccRegistered = false;
        int mLocalStream;
        final Object mLock = new Object();
        private final ComponentName mMediaButtonReceiverComponentName;
        private final PendingIntent mMediaButtonReceiverIntent;
        MediaMetadataCompat mMetadata;
        final String mPackageName;
        List<QueueItem> mQueue;
        CharSequence mQueueTitle;
        int mRatingType;
        final RemoteControlClient mRcc;
        int mRepeatMode;
        PendingIntent mSessionActivity;
        int mShuffleMode;
        PlaybackStateCompat mState;
        private final MediaSessionImplBase$MediaSessionStub mStub;
        final String mTag;
        private final Token mToken;
        private VolumeProviderCompat.Callback mVolumeCallback = new VolumeProviderCompat.Callback(){

            @Override
            public void onVolumeChanged(VolumeProviderCompat object) {
                if (MediaSessionImplBase.this.mVolumeProvider != object) {
                    return;
                }
                object = new ParcelableVolumeInfo(MediaSessionImplBase.this.mVolumeType, MediaSessionImplBase.this.mLocalStream, object.getVolumeControl(), object.getMaxVolume(), object.getCurrentVolume());
                MediaSessionImplBase.this.sendVolumeInfoChanged((ParcelableVolumeInfo)object);
            }
        };
        VolumeProviderCompat mVolumeProvider;
        int mVolumeType;

        public MediaSessionImplBase(Context context, String string, ComponentName componentName, PendingIntent pendingIntent) {
            if (componentName == null) {
                throw new IllegalArgumentException("MediaButtonReceiver component may not be null.");
            }
            this.mContext = context;
            this.mPackageName = context.getPackageName();
            this.mAudioManager = (AudioManager)context.getSystemService("audio");
            this.mTag = string;
            this.mMediaButtonReceiverComponentName = componentName;
            this.mMediaButtonReceiverIntent = pendingIntent;
            this.mStub = new MediaSessionImplBase$MediaSessionStub();
            this.mToken = new Token(this.mStub);
            this.mRatingType = 0;
            this.mVolumeType = 1;
            this.mLocalStream = 3;
            this.mRcc = new RemoteControlClient(pendingIntent);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendCaptioningEnabled(boolean bl) {
            int n = this.mControllerCallbacks.beginBroadcast() - 1;
            do {
                if (n < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
                try {
                    iMediaControllerCallback.onCaptioningEnabledChanged(bl);
                }
                catch (RemoteException remoteException) {}
                --n;
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendEvent(String string, Bundle bundle) {
            int n = this.mControllerCallbacks.beginBroadcast() - 1;
            do {
                if (n < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
                try {
                    iMediaControllerCallback.onEvent(string, bundle);
                }
                catch (RemoteException remoteException) {}
                --n;
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendExtras(Bundle bundle) {
            int n = this.mControllerCallbacks.beginBroadcast() - 1;
            do {
                if (n < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
                try {
                    iMediaControllerCallback.onExtrasChanged(bundle);
                }
                catch (RemoteException remoteException) {}
                --n;
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendMetadata(MediaMetadataCompat mediaMetadataCompat) {
            int n = this.mControllerCallbacks.beginBroadcast() - 1;
            do {
                if (n < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
                try {
                    iMediaControllerCallback.onMetadataChanged(mediaMetadataCompat);
                }
                catch (RemoteException remoteException) {}
                --n;
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendQueue(List<QueueItem> list) {
            int n = this.mControllerCallbacks.beginBroadcast() - 1;
            do {
                if (n < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
                try {
                    iMediaControllerCallback.onQueueChanged(list);
                }
                catch (RemoteException remoteException) {}
                --n;
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendQueueTitle(CharSequence charSequence) {
            int n = this.mControllerCallbacks.beginBroadcast() - 1;
            do {
                if (n < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
                try {
                    iMediaControllerCallback.onQueueTitleChanged(charSequence);
                }
                catch (RemoteException remoteException) {}
                --n;
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendRepeatMode(int n) {
            int n2 = this.mControllerCallbacks.beginBroadcast() - 1;
            do {
                if (n2 < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n2);
                try {
                    iMediaControllerCallback.onRepeatModeChanged(n);
                }
                catch (RemoteException remoteException) {}
                --n2;
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendSessionDestroyed() {
            int n = this.mControllerCallbacks.beginBroadcast() - 1;
            do {
                if (n < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    this.mControllerCallbacks.kill();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
                try {
                    iMediaControllerCallback.onSessionDestroyed();
                }
                catch (RemoteException remoteException) {}
                --n;
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendShuffleMode(int n) {
            int n2 = this.mControllerCallbacks.beginBroadcast() - 1;
            do {
                if (n2 < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n2);
                try {
                    iMediaControllerCallback.onShuffleModeChanged(n);
                }
                catch (RemoteException remoteException) {}
                --n2;
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendState(PlaybackStateCompat playbackStateCompat) {
            int n = this.mControllerCallbacks.beginBroadcast() - 1;
            do {
                if (n < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
                try {
                    iMediaControllerCallback.onPlaybackStateChanged(playbackStateCompat);
                }
                catch (RemoteException remoteException) {}
                --n;
            } while (true);
        }

        void adjustVolume(int n, int n2) {
            if (this.mVolumeType == 2) {
                if (this.mVolumeProvider != null) {
                    this.mVolumeProvider.onAdjustVolume(n);
                    return;
                }
            } else {
                this.mAudioManager.adjustStreamVolume(this.mLocalStream, n, n2);
            }
        }

        RemoteControlClient.MetadataEditor buildRccMetadata(Bundle bundle) {
            RemoteControlClient.MetadataEditor metadataEditor = this.mRcc.editMetadata(true);
            if (bundle == null) {
                return metadataEditor;
            }
            if (bundle.containsKey("android.media.metadata.ART")) {
                Bitmap bitmap;
                Bitmap bitmap2 = bitmap = (Bitmap)bundle.getParcelable("android.media.metadata.ART");
                if (bitmap != null) {
                    bitmap2 = bitmap.copy(bitmap.getConfig(), false);
                }
                metadataEditor.putBitmap(100, bitmap2);
            } else if (bundle.containsKey("android.media.metadata.ALBUM_ART")) {
                Bitmap bitmap;
                Bitmap bitmap3 = bitmap = (Bitmap)bundle.getParcelable("android.media.metadata.ALBUM_ART");
                if (bitmap != null) {
                    bitmap3 = bitmap.copy(bitmap.getConfig(), false);
                }
                metadataEditor.putBitmap(100, bitmap3);
            }
            if (bundle.containsKey("android.media.metadata.ALBUM")) {
                metadataEditor.putString(1, bundle.getString("android.media.metadata.ALBUM"));
            }
            if (bundle.containsKey("android.media.metadata.ALBUM_ARTIST")) {
                metadataEditor.putString(13, bundle.getString("android.media.metadata.ALBUM_ARTIST"));
            }
            if (bundle.containsKey("android.media.metadata.ARTIST")) {
                metadataEditor.putString(2, bundle.getString("android.media.metadata.ARTIST"));
            }
            if (bundle.containsKey("android.media.metadata.AUTHOR")) {
                metadataEditor.putString(3, bundle.getString("android.media.metadata.AUTHOR"));
            }
            if (bundle.containsKey("android.media.metadata.COMPILATION")) {
                metadataEditor.putString(15, bundle.getString("android.media.metadata.COMPILATION"));
            }
            if (bundle.containsKey("android.media.metadata.COMPOSER")) {
                metadataEditor.putString(4, bundle.getString("android.media.metadata.COMPOSER"));
            }
            if (bundle.containsKey("android.media.metadata.DATE")) {
                metadataEditor.putString(5, bundle.getString("android.media.metadata.DATE"));
            }
            if (bundle.containsKey("android.media.metadata.DISC_NUMBER")) {
                metadataEditor.putLong(14, bundle.getLong("android.media.metadata.DISC_NUMBER"));
            }
            if (bundle.containsKey("android.media.metadata.DURATION")) {
                metadataEditor.putLong(9, bundle.getLong("android.media.metadata.DURATION"));
            }
            if (bundle.containsKey("android.media.metadata.GENRE")) {
                metadataEditor.putString(6, bundle.getString("android.media.metadata.GENRE"));
            }
            if (bundle.containsKey("android.media.metadata.TITLE")) {
                metadataEditor.putString(7, bundle.getString("android.media.metadata.TITLE"));
            }
            if (bundle.containsKey("android.media.metadata.TRACK_NUMBER")) {
                metadataEditor.putLong(0, bundle.getLong("android.media.metadata.TRACK_NUMBER"));
            }
            if (bundle.containsKey("android.media.metadata.WRITER")) {
                metadataEditor.putString(11, bundle.getString("android.media.metadata.WRITER"));
            }
            return metadataEditor;
        }

        @Override
        public String getCallingPackage() {
            return null;
        }

        @Override
        public Object getMediaSession() {
            return null;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public PlaybackStateCompat getPlaybackState() {
            Object object = this.mLock;
            synchronized (object) {
                return this.mState;
            }
        }

        int getRccStateFromState(int n) {
            switch (n) {
                default: {
                    return -1;
                }
                case 10: 
                case 11: {
                    return 6;
                }
                case 9: {
                    return 7;
                }
                case 7: {
                    return 9;
                }
                case 6: 
                case 8: {
                    return 8;
                }
                case 5: {
                    return 5;
                }
                case 4: {
                    return 4;
                }
                case 3: {
                    return 3;
                }
                case 2: {
                    return 2;
                }
                case 1: {
                    return 1;
                }
                case 0: 
            }
            return 0;
        }

        int getRccTransportControlFlagsFromActions(long l) {
            int n = (l & 1L) != 0L ? 32 : 0;
            int n2 = n;
            if ((l & 2L) != 0L) {
                n2 = n | 16;
            }
            n = n2;
            if ((l & 4L) != 0L) {
                n = n2 | 4;
            }
            n2 = n;
            if ((l & 8L) != 0L) {
                n2 = n | 2;
            }
            n = n2;
            if ((l & 16L) != 0L) {
                n = n2 | 1;
            }
            n2 = n;
            if ((l & 32L) != 0L) {
                n2 = n | 128;
            }
            n = n2;
            if ((l & 64L) != 0L) {
                n = n2 | 64;
            }
            n2 = n;
            if ((l & 512L) != 0L) {
                n2 = n | 8;
            }
            return n2;
        }

        @Override
        public Object getRemoteControlClient() {
            return null;
        }

        @Override
        public Token getSessionToken() {
            return this.mToken;
        }

        @Override
        public boolean isActive() {
            return this.mIsActive;
        }

        void postToHandler(int n) {
            this.postToHandler(n, null);
        }

        void postToHandler(int n, int n2) {
            this.postToHandler(n, null, n2);
        }

        void postToHandler(int n, Object object) {
            this.postToHandler(n, object, null);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void postToHandler(int n, Object object, int n2) {
            Object object2 = this.mLock;
            synchronized (object2) {
                if (this.mHandler != null) {
                    this.mHandler.post(n, object, n2);
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void postToHandler(int n, Object object, Bundle bundle) {
            Object object2 = this.mLock;
            synchronized (object2) {
                if (this.mHandler != null) {
                    this.mHandler.post(n, object, bundle);
                }
                return;
            }
        }

        void registerMediaButtonEventReceiver(PendingIntent pendingIntent, ComponentName componentName) {
            this.mAudioManager.registerMediaButtonEventReceiver(componentName);
        }

        @Override
        public void release() {
            this.mIsActive = false;
            this.mDestroyed = true;
            this.update();
            this.sendSessionDestroyed();
        }

        @Override
        public void sendSessionEvent(String string, Bundle bundle) {
            this.sendEvent(string, bundle);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void sendVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) {
            int n = this.mControllerCallbacks.beginBroadcast() - 1;
            do {
                if (n < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n);
                try {
                    iMediaControllerCallback.onVolumeInfoChanged(parcelableVolumeInfo);
                }
                catch (RemoteException remoteException) {}
                --n;
            } while (true);
        }

        @Override
        public void setActive(boolean bl) {
            if (bl == this.mIsActive) {
                return;
            }
            this.mIsActive = bl;
            if (this.update()) {
                this.setMetadata(this.mMetadata);
                this.setPlaybackState(this.mState);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setCallback(Callback callback, Handler object) {
            this.mCallback = callback;
            if (callback == null) {
                return;
            }
            callback = object;
            if (object == null) {
                callback = new Handler();
            }
            object = this.mLock;
            synchronized (object) {
                if (this.mHandler != null) {
                    this.mHandler.removeCallbacksAndMessages(null);
                }
                this.mHandler = new MediaSessionImplBase$MessageHandler(callback.getLooper());
                this.mCallback.setSessionImpl(this, (Handler)callback);
                return;
            }
        }

        @Override
        public void setCaptioningEnabled(boolean bl) {
            if (this.mCaptioningEnabled != bl) {
                this.mCaptioningEnabled = bl;
                this.sendCaptioningEnabled(bl);
            }
        }

        @Override
        public void setExtras(Bundle bundle) {
            this.mExtras = bundle;
            this.sendExtras(bundle);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setFlags(int n) {
            Object object = this.mLock;
            synchronized (object) {
                this.mFlags = n;
            }
            this.update();
        }

        @Override
        public void setMediaButtonReceiver(PendingIntent pendingIntent) {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setMetadata(MediaMetadataCompat object) {
            MediaMetadataCompat mediaMetadataCompat = object;
            if (object != null) {
                mediaMetadataCompat = new MediaMetadataCompat.Builder((MediaMetadataCompat)object, MediaSessionCompat.sMaxBitmapSize).build();
            }
            object = this.mLock;
            synchronized (object) {
                this.mMetadata = mediaMetadataCompat;
            }
            this.sendMetadata(mediaMetadataCompat);
            if (!this.mIsActive) {
                return;
            }
            object = mediaMetadataCompat == null ? null : mediaMetadataCompat.getBundle();
            this.buildRccMetadata((Bundle)object).apply();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setPlaybackState(PlaybackStateCompat playbackStateCompat) {
            Object object = this.mLock;
            synchronized (object) {
                this.mState = playbackStateCompat;
            }
            this.sendState(playbackStateCompat);
            if (!this.mIsActive) {
                return;
            }
            if (playbackStateCompat == null) {
                this.mRcc.setPlaybackState(0);
                this.mRcc.setTransportControlFlags(0);
                return;
            }
            this.setRccState(playbackStateCompat);
            this.mRcc.setTransportControlFlags(this.getRccTransportControlFlagsFromActions(playbackStateCompat.getActions()));
        }

        @Override
        public void setPlaybackToLocal(int n) {
            if (this.mVolumeProvider != null) {
                this.mVolumeProvider.setCallback(null);
            }
            this.mVolumeType = 1;
            this.sendVolumeInfoChanged(new ParcelableVolumeInfo(this.mVolumeType, this.mLocalStream, 2, this.mAudioManager.getStreamMaxVolume(this.mLocalStream), this.mAudioManager.getStreamVolume(this.mLocalStream)));
        }

        @Override
        public void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
            if (volumeProviderCompat == null) {
                throw new IllegalArgumentException("volumeProvider may not be null");
            }
            if (this.mVolumeProvider != null) {
                this.mVolumeProvider.setCallback(null);
            }
            this.mVolumeType = 2;
            this.mVolumeProvider = volumeProviderCompat;
            this.sendVolumeInfoChanged(new ParcelableVolumeInfo(this.mVolumeType, this.mLocalStream, this.mVolumeProvider.getVolumeControl(), this.mVolumeProvider.getMaxVolume(), this.mVolumeProvider.getCurrentVolume()));
            volumeProviderCompat.setCallback(this.mVolumeCallback);
        }

        @Override
        public void setQueue(List<QueueItem> list) {
            this.mQueue = list;
            this.sendQueue(list);
        }

        @Override
        public void setQueueTitle(CharSequence charSequence) {
            this.mQueueTitle = charSequence;
            this.sendQueueTitle(charSequence);
        }

        @Override
        public void setRatingType(int n) {
            this.mRatingType = n;
        }

        void setRccState(PlaybackStateCompat playbackStateCompat) {
            this.mRcc.setPlaybackState(this.getRccStateFromState(playbackStateCompat.getState()));
        }

        @Override
        public void setRepeatMode(int n) {
            if (this.mRepeatMode != n) {
                this.mRepeatMode = n;
                this.sendRepeatMode(n);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setSessionActivity(PendingIntent pendingIntent) {
            Object object = this.mLock;
            synchronized (object) {
                this.mSessionActivity = pendingIntent;
                return;
            }
        }

        @Override
        public void setShuffleMode(int n) {
            if (this.mShuffleMode != n) {
                this.mShuffleMode = n;
                this.sendShuffleMode(n);
            }
        }

        void setVolumeTo(int n, int n2) {
            if (this.mVolumeType == 2) {
                if (this.mVolumeProvider != null) {
                    this.mVolumeProvider.onSetVolumeTo(n);
                    return;
                }
            } else {
                this.mAudioManager.setStreamVolume(this.mLocalStream, n, n2);
            }
        }

        void unregisterMediaButtonEventReceiver(PendingIntent pendingIntent, ComponentName componentName) {
            this.mAudioManager.unregisterMediaButtonEventReceiver(componentName);
        }

        boolean update() {
            if (this.mIsActive) {
                if (!this.mIsMbrRegistered && (this.mFlags & 1) != 0) {
                    this.registerMediaButtonEventReceiver(this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
                    this.mIsMbrRegistered = true;
                } else if (this.mIsMbrRegistered && (this.mFlags & 1) == 0) {
                    this.unregisterMediaButtonEventReceiver(this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
                    this.mIsMbrRegistered = false;
                }
                if (!this.mIsRccRegistered && (this.mFlags & 2) != 0) {
                    this.mAudioManager.registerRemoteControlClient(this.mRcc);
                    this.mIsRccRegistered = true;
                    return true;
                }
                if (this.mIsRccRegistered && (this.mFlags & 2) == 0) {
                    this.mRcc.setPlaybackState(0);
                    this.mAudioManager.unregisterRemoteControlClient(this.mRcc);
                    this.mIsRccRegistered = false;
                }
            } else {
                if (this.mIsMbrRegistered) {
                    this.unregisterMediaButtonEventReceiver(this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
                    this.mIsMbrRegistered = false;
                }
                if (this.mIsRccRegistered) {
                    this.mRcc.setPlaybackState(0);
                    this.mAudioManager.unregisterRemoteControlClient(this.mRcc);
                    this.mIsRccRegistered = false;
                }
            }
            return false;
        }

    }

    private static final class MediaSessionImplBase$Command {
        public final String command;
        public final Bundle extras;
        public final ResultReceiver stub;

        public MediaSessionImplBase$Command(String string, Bundle bundle, ResultReceiver resultReceiver) {
            this.command = string;
            this.extras = bundle;
            this.stub = resultReceiver;
        }
    }

    class MediaSessionImplBase$MediaSessionStub
    extends IMediaSession.Stub {
        MediaSessionImplBase$MediaSessionStub() {
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
        public List<QueueItem> getQueue() {
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
                    n2 = volumeProviderCompat.getMaxVolume();
                    n = volumeProviderCompat.getCurrentVolume();
                } else {
                    n2 = MediaSessionImplBase.this.mAudioManager.getStreamMaxVolume(n4);
                    n = MediaSessionImplBase.this.mAudioManager.getStreamVolume(n4);
                }
                return new ParcelableVolumeInfo(n3, n4, n5, n2, n);
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
        public void sendCommand(String string, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) {
            MediaSessionImplBase.this.postToHandler(1, new MediaSessionImplBase$Command(string, bundle, resultReceiverWrapper.mResultReceiver));
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

    class MediaSessionImplBase$MessageHandler
    extends Handler {
        private static final int KEYCODE_MEDIA_PAUSE = 127;
        private static final int KEYCODE_MEDIA_PLAY = 126;
        private static final int MSG_ADD_QUEUE_ITEM = 25;
        private static final int MSG_ADD_QUEUE_ITEM_AT = 26;
        private static final int MSG_ADJUST_VOLUME = 2;
        private static final int MSG_COMMAND = 1;
        private static final int MSG_CUSTOM_ACTION = 20;
        private static final int MSG_FAST_FORWARD = 16;
        private static final int MSG_MEDIA_BUTTON = 21;
        private static final int MSG_NEXT = 14;
        private static final int MSG_PAUSE = 12;
        private static final int MSG_PLAY = 7;
        private static final int MSG_PLAY_MEDIA_ID = 8;
        private static final int MSG_PLAY_SEARCH = 9;
        private static final int MSG_PLAY_URI = 10;
        private static final int MSG_PREPARE = 3;
        private static final int MSG_PREPARE_MEDIA_ID = 4;
        private static final int MSG_PREPARE_SEARCH = 5;
        private static final int MSG_PREPARE_URI = 6;
        private static final int MSG_PREVIOUS = 15;
        private static final int MSG_RATE = 19;
        private static final int MSG_RATE_EXTRA = 31;
        private static final int MSG_REMOVE_QUEUE_ITEM = 27;
        private static final int MSG_REMOVE_QUEUE_ITEM_AT = 28;
        private static final int MSG_REWIND = 17;
        private static final int MSG_SEEK_TO = 18;
        private static final int MSG_SET_CAPTIONING_ENABLED = 29;
        private static final int MSG_SET_REPEAT_MODE = 23;
        private static final int MSG_SET_SHUFFLE_MODE = 30;
        private static final int MSG_SET_VOLUME = 22;
        private static final int MSG_SKIP_TO_ITEM = 11;
        private static final int MSG_STOP = 13;

        public MediaSessionImplBase$MessageHandler(Looper looper) {
            super(looper);
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        private void onMediaButtonEvent(KeyEvent keyEvent, Callback callback) {
            if (keyEvent == null) return;
            if (keyEvent.getAction() != 0) {
                return;
            }
            long l = MediaSessionImplBase.this.mState == null ? 0L : MediaSessionImplBase.this.mState.getActions();
            int n = keyEvent.getKeyCode();
            if (n != 79) {
                switch (n) {
                    default: {
                        switch (n) {
                            default: {
                                return;
                            }
                            case 127: {
                                if ((l & 2L) == 0L) return;
                                callback.onPause();
                                return;
                            }
                            case 126: 
                        }
                        if ((l & 4L) == 0L) return;
                        callback.onPlay();
                        return;
                    }
                    case 90: {
                        if ((l & 64L) == 0L) return;
                        callback.onFastForward();
                        return;
                    }
                    case 89: {
                        if ((l & 8L) == 0L) return;
                        callback.onRewind();
                        return;
                    }
                    case 88: {
                        if ((l & 16L) == 0L) return;
                        callback.onSkipToPrevious();
                        return;
                    }
                    case 87: {
                        if ((l & 32L) == 0L) return;
                        callback.onSkipToNext();
                        return;
                    }
                    case 86: {
                        if ((l & 1L) == 0L) return;
                        callback.onStop();
                        return;
                    }
                    case 85: 
                }
            }
            Log.w((String)MediaSessionCompat.TAG, (String)"KEYCODE_MEDIA_PLAY_PAUSE and KEYCODE_HEADSETHOOK are handled already");
        }

        public void handleMessage(Message object) {
            Callback callback = MediaSessionImplBase.this.mCallback;
            if (callback == null) {
                return;
            }
            switch (object.what) {
                default: {
                    return;
                }
                case 31: {
                    callback.onSetRating((RatingCompat)object.obj, object.getData());
                    return;
                }
                case 30: {
                    callback.onSetShuffleMode(object.arg1);
                    return;
                }
                case 29: {
                    callback.onSetCaptioningEnabled((Boolean)object.obj);
                    return;
                }
                case 28: {
                    if (MediaSessionImplBase.this.mQueue == null) break;
                    object = object.arg1 >= 0 && object.arg1 < MediaSessionImplBase.this.mQueue.size() ? MediaSessionImplBase.this.mQueue.get(object.arg1) : null;
                    if (object == null) break;
                    callback.onRemoveQueueItem(object.getDescription());
                    return;
                }
                case 27: {
                    callback.onRemoveQueueItem((MediaDescriptionCompat)object.obj);
                    return;
                }
                case 26: {
                    callback.onAddQueueItem((MediaDescriptionCompat)object.obj, object.arg1);
                    return;
                }
                case 25: {
                    callback.onAddQueueItem((MediaDescriptionCompat)object.obj);
                    return;
                }
                case 23: {
                    callback.onSetRepeatMode(object.arg1);
                    return;
                }
                case 22: {
                    MediaSessionImplBase.this.setVolumeTo(object.arg1, 0);
                    return;
                }
                case 21: {
                    object = (KeyEvent)object.obj;
                    Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                    intent.putExtra("android.intent.extra.KEY_EVENT", (Parcelable)object);
                    if (callback.onMediaButtonEvent(intent)) break;
                    this.onMediaButtonEvent((KeyEvent)object, callback);
                    return;
                }
                case 20: {
                    callback.onCustomAction((String)object.obj, object.getData());
                    return;
                }
                case 19: {
                    callback.onSetRating((RatingCompat)object.obj);
                    return;
                }
                case 18: {
                    callback.onSeekTo((Long)object.obj);
                    return;
                }
                case 17: {
                    callback.onRewind();
                    return;
                }
                case 16: {
                    callback.onFastForward();
                    return;
                }
                case 15: {
                    callback.onSkipToPrevious();
                    return;
                }
                case 14: {
                    callback.onSkipToNext();
                    return;
                }
                case 13: {
                    callback.onStop();
                    return;
                }
                case 12: {
                    callback.onPause();
                    return;
                }
                case 11: {
                    callback.onSkipToQueueItem((Long)object.obj);
                    return;
                }
                case 10: {
                    callback.onPlayFromUri((Uri)object.obj, object.getData());
                    return;
                }
                case 9: {
                    callback.onPlayFromSearch((String)object.obj, object.getData());
                    return;
                }
                case 8: {
                    callback.onPlayFromMediaId((String)object.obj, object.getData());
                    return;
                }
                case 7: {
                    callback.onPlay();
                    return;
                }
                case 6: {
                    callback.onPrepareFromUri((Uri)object.obj, object.getData());
                    return;
                }
                case 5: {
                    callback.onPrepareFromSearch((String)object.obj, object.getData());
                    return;
                }
                case 4: {
                    callback.onPrepareFromMediaId((String)object.obj, object.getData());
                    return;
                }
                case 3: {
                    callback.onPrepare();
                    return;
                }
                case 2: {
                    MediaSessionImplBase.this.adjustVolume(object.arg1, 0);
                    return;
                }
                case 1: {
                    object = (MediaSessionImplBase$Command)object.obj;
                    callback.onCommand(object.command, object.extras, object.stub);
                }
            }
        }

        public void post(int n) {
            this.post(n, null);
        }

        public void post(int n, Object object) {
            this.obtainMessage(n, object).sendToTarget();
        }

        public void post(int n, Object object, int n2) {
            this.obtainMessage(n, n2, 0, object).sendToTarget();
        }

        public void post(int n, Object object, Bundle bundle) {
            object = this.obtainMessage(n, object);
            object.setData(bundle);
            object.sendToTarget();
        }
    }

    public static interface OnActiveChangeListener {
        public void onActiveChanged();
    }

    public static final class QueueItem
    implements Parcelable {
        public static final Parcelable.Creator<QueueItem> CREATOR = new Parcelable.Creator<QueueItem>(){

            public QueueItem createFromParcel(Parcel parcel) {
                return new QueueItem(parcel);
            }

            public QueueItem[] newArray(int n) {
                return new QueueItem[n];
            }
        };
        public static final int UNKNOWN_ID = -1;
        private final MediaDescriptionCompat mDescription;
        private final long mId;
        private Object mItem;

        QueueItem(Parcel parcel) {
            this.mDescription = (MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
            this.mId = parcel.readLong();
        }

        public QueueItem(MediaDescriptionCompat mediaDescriptionCompat, long l) {
            this(null, mediaDescriptionCompat, l);
        }

        private QueueItem(Object object, MediaDescriptionCompat mediaDescriptionCompat, long l) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("Description cannot be null.");
            }
            if (l == -1L) {
                throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
            }
            this.mDescription = mediaDescriptionCompat;
            this.mId = l;
            this.mItem = object;
        }

        public static QueueItem fromQueueItem(Object object) {
            if (object != null && Build.VERSION.SDK_INT >= 21) {
                return new QueueItem(object, MediaDescriptionCompat.fromMediaDescription(MediaSessionCompatApi21.QueueItem.getDescription(object)), MediaSessionCompatApi21.QueueItem.getQueueId(object));
            }
            return null;
        }

        public static List<QueueItem> fromQueueItemList(List<?> object) {
            if (object != null && Build.VERSION.SDK_INT >= 21) {
                ArrayList<QueueItem> arrayList = new ArrayList<QueueItem>();
                object = object.iterator();
                while (object.hasNext()) {
                    arrayList.add(QueueItem.fromQueueItem(object.next()));
                }
                return arrayList;
            }
            return null;
        }

        public int describeContents() {
            return 0;
        }

        public MediaDescriptionCompat getDescription() {
            return this.mDescription;
        }

        public long getQueueId() {
            return this.mId;
        }

        public Object getQueueItem() {
            if (this.mItem == null && Build.VERSION.SDK_INT >= 21) {
                this.mItem = MediaSessionCompatApi21.QueueItem.createItem(this.mDescription.getMediaDescription(), this.mId);
                return this.mItem;
            }
            return this.mItem;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MediaSession.QueueItem {Description=");
            stringBuilder.append(this.mDescription);
            stringBuilder.append(", Id=");
            stringBuilder.append(this.mId);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel parcel, int n) {
            this.mDescription.writeToParcel(parcel, n);
            parcel.writeLong(this.mId);
        }

    }

    static final class ResultReceiverWrapper
    implements Parcelable {
        public static final Parcelable.Creator<ResultReceiverWrapper> CREATOR = new Parcelable.Creator<ResultReceiverWrapper>(){

            public ResultReceiverWrapper createFromParcel(Parcel parcel) {
                return new ResultReceiverWrapper(parcel);
            }

            public ResultReceiverWrapper[] newArray(int n) {
                return new ResultReceiverWrapper[n];
            }
        };
        private ResultReceiver mResultReceiver;

        ResultReceiverWrapper(Parcel parcel) {
            this.mResultReceiver = (ResultReceiver)ResultReceiver.CREATOR.createFromParcel(parcel);
        }

        public ResultReceiverWrapper(ResultReceiver resultReceiver) {
            this.mResultReceiver = resultReceiver;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n) {
            this.mResultReceiver.writeToParcel(parcel, n);
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface SessionFlags {
    }

    public static final class Token
    implements Parcelable {
        public static final Parcelable.Creator<Token> CREATOR = new Parcelable.Creator<Token>(){

            public Token createFromParcel(Parcel object) {
                object = Build.VERSION.SDK_INT >= 21 ? object.readParcelable(null) : object.readStrongBinder();
                return new Token(object);
            }

            public Token[] newArray(int n) {
                return new Token[n];
            }
        };
        private final IMediaSession mExtraBinder;
        private final Object mInner;

        Token(Object object) {
            this(object, null);
        }

        Token(Object object, IMediaSession iMediaSession) {
            this.mInner = object;
            this.mExtraBinder = iMediaSession;
        }

        public static Token fromToken(Object object) {
            return Token.fromToken(object, null);
        }

        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        public static Token fromToken(Object object, IMediaSession iMediaSession) {
            if (object != null && Build.VERSION.SDK_INT >= 21) {
                return new Token(MediaSessionCompatApi21.verifyToken(object), iMediaSession);
            }
            return null;
        }

        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof Token)) {
                return false;
            }
            object = (Token)object;
            if (this.mInner == null) {
                if (object.mInner == null) {
                    return true;
                }
                return false;
            }
            if (object.mInner == null) {
                return false;
            }
            return this.mInner.equals(object.mInner);
        }

        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        public IMediaSession getExtraBinder() {
            return this.mExtraBinder;
        }

        public Object getToken() {
            return this.mInner;
        }

        public int hashCode() {
            if (this.mInner == null) {
                return 0;
            }
            return this.mInner.hashCode();
        }

        public void writeToParcel(Parcel parcel, int n) {
            if (Build.VERSION.SDK_INT >= 21) {
                parcel.writeParcelable((Parcelable)this.mInner, n);
                return;
            }
            parcel.writeStrongBinder((IBinder)this.mInner);
        }

    }

}
