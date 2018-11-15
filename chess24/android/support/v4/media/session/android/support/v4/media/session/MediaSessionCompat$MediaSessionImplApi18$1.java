/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.RemoteControlClient
 *  android.media.RemoteControlClient$OnPlaybackPositionUpdateListener
 */
package android.support.v4.media.session;

import android.media.RemoteControlClient;
import android.support.v4.media.session.MediaSessionCompat;

class MediaSessionCompat.MediaSessionImplApi18
implements RemoteControlClient.OnPlaybackPositionUpdateListener {
    MediaSessionCompat.MediaSessionImplApi18() {
    }

    public void onPlaybackPositionUpdate(long l) {
        MediaSessionImplApi18.this.postToHandler(18, l);
    }
}
