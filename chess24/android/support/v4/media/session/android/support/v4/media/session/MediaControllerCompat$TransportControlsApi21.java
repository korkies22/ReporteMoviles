/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package android.support.v4.media.session;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaControllerCompatApi21;
import android.support.v4.media.session.PlaybackStateCompat;

static class MediaControllerCompat.TransportControlsApi21
extends MediaControllerCompat.TransportControls {
    protected final Object mControlsObj;

    public MediaControllerCompat.TransportControlsApi21(Object object) {
        this.mControlsObj = object;
    }

    @Override
    public void fastForward() {
        MediaControllerCompatApi21.TransportControls.fastForward(this.mControlsObj);
    }

    @Override
    public void pause() {
        MediaControllerCompatApi21.TransportControls.pause(this.mControlsObj);
    }

    @Override
    public void play() {
        MediaControllerCompatApi21.TransportControls.play(this.mControlsObj);
    }

    @Override
    public void playFromMediaId(String string, Bundle bundle) {
        MediaControllerCompatApi21.TransportControls.playFromMediaId(this.mControlsObj, string, bundle);
    }

    @Override
    public void playFromSearch(String string, Bundle bundle) {
        MediaControllerCompatApi21.TransportControls.playFromSearch(this.mControlsObj, string, bundle);
    }

    @Override
    public void playFromUri(Uri uri, Bundle bundle) {
        if (uri != null && !Uri.EMPTY.equals((Object)uri)) {
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_URI", (Parcelable)uri);
            bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS", (Parcelable)bundle);
            this.sendCustomAction("android.support.v4.media.session.action.PLAY_FROM_URI", bundle2);
            return;
        }
        throw new IllegalArgumentException("You must specify a non-empty Uri for playFromUri.");
    }

    @Override
    public void prepare() {
        this.sendCustomAction("android.support.v4.media.session.action.PREPARE", null);
    }

    @Override
    public void prepareFromMediaId(String string, Bundle bundle) {
        Bundle bundle2 = new Bundle();
        bundle2.putString("android.support.v4.media.session.action.ARGUMENT_MEDIA_ID", string);
        bundle2.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle);
        this.sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID", bundle2);
    }

    @Override
    public void prepareFromSearch(String string, Bundle bundle) {
        Bundle bundle2 = new Bundle();
        bundle2.putString("android.support.v4.media.session.action.ARGUMENT_QUERY", string);
        bundle2.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle);
        this.sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_SEARCH", bundle2);
    }

    @Override
    public void prepareFromUri(Uri uri, Bundle bundle) {
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_URI", (Parcelable)uri);
        bundle2.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle);
        this.sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_URI", bundle2);
    }

    @Override
    public void rewind() {
        MediaControllerCompatApi21.TransportControls.rewind(this.mControlsObj);
    }

    @Override
    public void seekTo(long l) {
        MediaControllerCompatApi21.TransportControls.seekTo(this.mControlsObj, l);
    }

    @Override
    public void sendCustomAction(PlaybackStateCompat.CustomAction customAction, Bundle bundle) {
        MediaControllerCompat.validateCustomAction(customAction.getAction(), bundle);
        MediaControllerCompatApi21.TransportControls.sendCustomAction(this.mControlsObj, customAction.getAction(), bundle);
    }

    @Override
    public void sendCustomAction(String string, Bundle bundle) {
        MediaControllerCompat.validateCustomAction(string, bundle);
        MediaControllerCompatApi21.TransportControls.sendCustomAction(this.mControlsObj, string, bundle);
    }

    @Override
    public void setCaptioningEnabled(boolean bl) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED", bl);
        this.sendCustomAction("android.support.v4.media.session.action.SET_CAPTIONING_ENABLED", bundle);
    }

    @Override
    public void setRating(RatingCompat object) {
        Object object2 = this.mControlsObj;
        object = object != null ? object.getRating() : null;
        MediaControllerCompatApi21.TransportControls.setRating(object2, object);
    }

    @Override
    public void setRating(RatingCompat ratingCompat, Bundle bundle) {
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_RATING", (Parcelable)ratingCompat);
        bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS", (Parcelable)bundle);
        this.sendCustomAction("android.support.v4.media.session.action.SET_RATING", bundle2);
    }

    @Override
    public void setRepeatMode(int n) {
        Bundle bundle = new Bundle();
        bundle.putInt("android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE", n);
        this.sendCustomAction("android.support.v4.media.session.action.SET_REPEAT_MODE", bundle);
    }

    @Override
    public void setShuffleMode(int n) {
        Bundle bundle = new Bundle();
        bundle.putInt("android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE", n);
        this.sendCustomAction("android.support.v4.media.session.action.SET_SHUFFLE_MODE", bundle);
    }

    @Override
    public void skipToNext() {
        MediaControllerCompatApi21.TransportControls.skipToNext(this.mControlsObj);
    }

    @Override
    public void skipToPrevious() {
        MediaControllerCompatApi21.TransportControls.skipToPrevious(this.mControlsObj);
    }

    @Override
    public void skipToQueueItem(long l) {
        MediaControllerCompatApi21.TransportControls.skipToQueueItem(this.mControlsObj, l);
    }

    @Override
    public void stop() {
        MediaControllerCompatApi21.TransportControls.stop(this.mControlsObj);
    }
}
