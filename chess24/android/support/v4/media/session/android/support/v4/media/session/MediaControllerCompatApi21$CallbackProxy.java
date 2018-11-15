/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.MediaMetadata
 *  android.media.session.MediaController
 *  android.media.session.MediaController$Callback
 *  android.media.session.MediaController$PlaybackInfo
 *  android.media.session.MediaSession
 *  android.media.session.MediaSession$QueueItem
 *  android.media.session.PlaybackState
 *  android.os.Bundle
 */
package android.support.v4.media.session;

import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.support.v4.media.session.MediaControllerCompatApi21;
import java.util.List;

static class MediaControllerCompatApi21.CallbackProxy<T extends MediaControllerCompatApi21.Callback>
extends MediaController.Callback {
    protected final T mCallback;

    public MediaControllerCompatApi21.CallbackProxy(T t) {
        this.mCallback = t;
    }

    public void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
        this.mCallback.onAudioInfoChanged(playbackInfo.getPlaybackType(), MediaControllerCompatApi21.PlaybackInfo.getLegacyAudioStream((Object)playbackInfo), playbackInfo.getVolumeControl(), playbackInfo.getMaxVolume(), playbackInfo.getCurrentVolume());
    }

    public void onExtrasChanged(Bundle bundle) {
        this.mCallback.onExtrasChanged(bundle);
    }

    public void onMetadataChanged(MediaMetadata mediaMetadata) {
        this.mCallback.onMetadataChanged((Object)mediaMetadata);
    }

    public void onPlaybackStateChanged(PlaybackState playbackState) {
        this.mCallback.onPlaybackStateChanged((Object)playbackState);
    }

    public void onQueueChanged(List<MediaSession.QueueItem> list) {
        this.mCallback.onQueueChanged(list);
    }

    public void onQueueTitleChanged(CharSequence charSequence) {
        this.mCallback.onQueueTitleChanged(charSequence);
    }

    public void onSessionDestroyed() {
        this.mCallback.onSessionDestroyed();
    }

    public void onSessionEvent(String string, Bundle bundle) {
        this.mCallback.onSessionEvent(string, bundle);
    }
}
