/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.v4.media;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserCompatApi23;

private class MediaBrowserCompat.ItemCallback.StubApi23
implements MediaBrowserCompatApi23.ItemCallback {
    MediaBrowserCompat.ItemCallback.StubApi23() {
    }

    @Override
    public void onError(@NonNull String string) {
        ItemCallback.this.onError(string);
    }

    @Override
    public void onItemLoaded(Parcel parcel) {
        if (parcel == null) {
            ItemCallback.this.onItemLoaded(null);
            return;
        }
        parcel.setDataPosition(0);
        MediaBrowserCompat.MediaItem mediaItem = (MediaBrowserCompat.MediaItem)MediaBrowserCompat.MediaItem.CREATOR.createFromParcel(parcel);
        parcel.recycle();
        ItemCallback.this.onItemLoaded(mediaItem);
    }
}
