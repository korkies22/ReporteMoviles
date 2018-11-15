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
import java.util.List;

class MediaBrowserServiceCompat
extends MediaBrowserServiceCompat.Result<List<MediaBrowserCompat.MediaItem>> {
    final /* synthetic */ ResultReceiver val$receiver;

    MediaBrowserServiceCompat(Object object, ResultReceiver resultReceiver) {
        this.val$receiver = resultReceiver;
        super(object);
    }

    @Override
    void onResultSent(List<MediaBrowserCompat.MediaItem> list) {
        if ((this.getFlags() & 4) == 0 && list != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArray(android.support.v4.media.MediaBrowserServiceCompat.KEY_SEARCH_RESULTS, (Parcelable[])list.toArray(new MediaBrowserCompat.MediaItem[0]));
            this.val$receiver.send(0, bundle);
            return;
        }
        this.val$receiver.send(-1, null);
    }
}
