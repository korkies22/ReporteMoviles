/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 */
package android.support.v4.media.session;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaControllerCompatApi21;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import java.lang.ref.WeakReference;
import java.util.List;

private static class MediaControllerCompat.Callback.StubApi21
implements MediaControllerCompatApi21.Callback {
    private final WeakReference<MediaControllerCompat.Callback> mCallback;

    MediaControllerCompat.Callback.StubApi21(MediaControllerCompat.Callback callback) {
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
