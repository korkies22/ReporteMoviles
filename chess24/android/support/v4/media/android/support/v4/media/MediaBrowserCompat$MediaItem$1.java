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
import android.support.v4.media.MediaBrowserCompat;

static final class MediaBrowserCompat.MediaItem
implements Parcelable.Creator<MediaBrowserCompat.MediaItem> {
    MediaBrowserCompat.MediaItem() {
    }

    public MediaBrowserCompat.MediaItem createFromParcel(Parcel parcel) {
        return new MediaBrowserCompat.MediaItem(parcel);
    }

    public MediaBrowserCompat.MediaItem[] newArray(int n) {
        return new MediaBrowserCompat.MediaItem[n];
    }
}
