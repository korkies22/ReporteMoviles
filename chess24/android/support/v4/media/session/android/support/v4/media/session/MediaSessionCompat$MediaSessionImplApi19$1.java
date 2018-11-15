/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.Rating
 *  android.media.RemoteControlClient
 *  android.media.RemoteControlClient$OnMetadataUpdateListener
 */
package android.support.v4.media.session;

import android.media.Rating;
import android.media.RemoteControlClient;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.MediaSessionCompat;

class MediaSessionCompat.MediaSessionImplApi19
implements RemoteControlClient.OnMetadataUpdateListener {
    MediaSessionCompat.MediaSessionImplApi19() {
    }

    public void onMetadataUpdate(int n, Object object) {
        if (n == 268435457 && object instanceof Rating) {
            MediaSessionImplApi19.this.postToHandler(19, RatingCompat.fromRating(object));
        }
    }
}
