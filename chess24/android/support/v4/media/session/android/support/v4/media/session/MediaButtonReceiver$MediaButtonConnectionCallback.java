/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.BroadcastReceiver$PendingResult
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.util.Log
 *  android.view.KeyEvent
 */
package android.support.v4.media.session;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.KeyEvent;

private static class MediaButtonReceiver.MediaButtonConnectionCallback
extends MediaBrowserCompat.ConnectionCallback {
    private final Context mContext;
    private final Intent mIntent;
    private MediaBrowserCompat mMediaBrowser;
    private final BroadcastReceiver.PendingResult mPendingResult;

    MediaButtonReceiver.MediaButtonConnectionCallback(Context context, Intent intent, BroadcastReceiver.PendingResult pendingResult) {
        this.mContext = context;
        this.mIntent = intent;
        this.mPendingResult = pendingResult;
    }

    private void finish() {
        this.mMediaBrowser.disconnect();
        this.mPendingResult.finish();
    }

    @Override
    public void onConnected() {
        try {
            new MediaControllerCompat(this.mContext, this.mMediaBrowser.getSessionToken()).dispatchMediaButtonEvent((KeyEvent)this.mIntent.getParcelableExtra("android.intent.extra.KEY_EVENT"));
        }
        catch (RemoteException remoteException) {
            Log.e((String)MediaButtonReceiver.TAG, (String)"Failed to create a media controller", (Throwable)remoteException);
        }
        this.finish();
    }

    @Override
    public void onConnectionFailed() {
        this.finish();
    }

    @Override
    public void onConnectionSuspended() {
        this.finish();
    }

    void setMediaBrowser(MediaBrowserCompat mediaBrowserCompat) {
        this.mMediaBrowser = mediaBrowserCompat;
    }
}
