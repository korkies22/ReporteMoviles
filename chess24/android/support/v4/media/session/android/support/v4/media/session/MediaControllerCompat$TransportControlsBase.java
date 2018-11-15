/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.RemoteException
 *  android.util.Log
 */
package android.support.v4.media.session;

import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

static class MediaControllerCompat.TransportControlsBase
extends MediaControllerCompat.TransportControls {
    private IMediaSession mBinder;

    public MediaControllerCompat.TransportControlsBase(IMediaSession iMediaSession) {
        this.mBinder = iMediaSession;
    }

    @Override
    public void fastForward() {
        try {
            this.mBinder.fastForward();
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in fastForward.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void pause() {
        try {
            this.mBinder.pause();
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in pause.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void play() {
        try {
            this.mBinder.play();
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in play.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void playFromMediaId(String string, Bundle bundle) {
        try {
            this.mBinder.playFromMediaId(string, bundle);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in playFromMediaId.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void playFromSearch(String string, Bundle bundle) {
        try {
            this.mBinder.playFromSearch(string, bundle);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in playFromSearch.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void playFromUri(Uri uri, Bundle bundle) {
        try {
            this.mBinder.playFromUri(uri, bundle);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in playFromUri.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void prepare() {
        try {
            this.mBinder.prepare();
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in prepare.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void prepareFromMediaId(String string, Bundle bundle) {
        try {
            this.mBinder.prepareFromMediaId(string, bundle);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in prepareFromMediaId.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void prepareFromSearch(String string, Bundle bundle) {
        try {
            this.mBinder.prepareFromSearch(string, bundle);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in prepareFromSearch.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void prepareFromUri(Uri uri, Bundle bundle) {
        try {
            this.mBinder.prepareFromUri(uri, bundle);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in prepareFromUri.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void rewind() {
        try {
            this.mBinder.rewind();
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in rewind.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void seekTo(long l) {
        try {
            this.mBinder.seekTo(l);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in seekTo.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void sendCustomAction(PlaybackStateCompat.CustomAction customAction, Bundle bundle) {
        this.sendCustomAction(customAction.getAction(), bundle);
    }

    @Override
    public void sendCustomAction(String string, Bundle bundle) {
        MediaControllerCompat.validateCustomAction(string, bundle);
        try {
            this.mBinder.sendCustomAction(string, bundle);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in sendCustomAction.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void setCaptioningEnabled(boolean bl) {
        try {
            this.mBinder.setCaptioningEnabled(bl);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in setCaptioningEnabled.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void setRating(RatingCompat ratingCompat) {
        try {
            this.mBinder.rate(ratingCompat);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in setRating.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void setRating(RatingCompat ratingCompat, Bundle bundle) {
        try {
            this.mBinder.rateWithExtras(ratingCompat, bundle);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in setRating.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void setRepeatMode(int n) {
        try {
            this.mBinder.setRepeatMode(n);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in setRepeatMode.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void setShuffleMode(int n) {
        try {
            this.mBinder.setShuffleMode(n);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in setShuffleMode.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void skipToNext() {
        try {
            this.mBinder.next();
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in skipToNext.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void skipToPrevious() {
        try {
            this.mBinder.previous();
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in skipToPrevious.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void skipToQueueItem(long l) {
        try {
            this.mBinder.skipToQueueItem(l);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in skipToQueueItem.", (Throwable)remoteException);
            return;
        }
    }

    @Override
    public void stop() {
        try {
            this.mBinder.stop();
            return;
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaControllerCompat.TAG, (String)"Dead object in stop.", (Throwable)remoteException);
            return;
        }
    }
}
