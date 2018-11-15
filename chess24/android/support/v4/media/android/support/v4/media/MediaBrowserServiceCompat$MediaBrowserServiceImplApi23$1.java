/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package android.support.v4.media;

import android.os.Parcel;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaBrowserServiceCompatApi21;

class MediaBrowserServiceCompat.MediaBrowserServiceImplApi23
extends MediaBrowserServiceCompat.Result<MediaBrowserCompat.MediaItem> {
    final /* synthetic */ MediaBrowserServiceCompatApi21.ResultWrapper val$resultWrapper;

    MediaBrowserServiceCompat.MediaBrowserServiceImplApi23(Object object, MediaBrowserServiceCompatApi21.ResultWrapper resultWrapper) {
        this.val$resultWrapper = resultWrapper;
        super(object);
    }

    @Override
    public void detach() {
        this.val$resultWrapper.detach();
    }

    @Override
    void onResultSent(MediaBrowserCompat.MediaItem mediaItem) {
        if (mediaItem == null) {
            this.val$resultWrapper.sendResult(null);
            return;
        }
        Parcel parcel = Parcel.obtain();
        mediaItem.writeToParcel(parcel, 0);
        this.val$resultWrapper.sendResult(parcel);
    }
}
