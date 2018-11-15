/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.BadParcelableException
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcelable
 *  android.os.ResultReceiver
 *  android.util.Log
 */
package android.support.v4.media.session;

import android.content.Intent;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.annotation.RequiresApi;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.MediaSessionCompatApi21;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.List;

@RequiresApi(value=21)
private class MediaSessionCompat.Callback.StubApi21
implements MediaSessionCompatApi21.Callback {
    MediaSessionCompat.Callback.StubApi21() {
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
