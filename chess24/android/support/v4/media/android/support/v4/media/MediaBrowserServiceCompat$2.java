/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package android.support.v4.media;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.os.ResultReceiver;

class MediaBrowserServiceCompat
extends MediaBrowserServiceCompat.Result<MediaBrowserCompat.MediaItem> {
    final /* synthetic */ ResultReceiver val$receiver;

    MediaBrowserServiceCompat(Object object, ResultReceiver resultReceiver) {
        this.val$receiver = resultReceiver;
        super(object);
    }

    @Override
    void onResultSent(MediaBrowserCompat.MediaItem mediaItem) {
        if ((this.getFlags() & 2) != 0) {
            this.val$receiver.send(-1, null);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(android.support.v4.media.MediaBrowserServiceCompat.KEY_MEDIA_ITEM, (Parcelable)mediaItem);
        this.val$receiver.send(0, bundle);
    }
}
