/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.v4.media.session;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.session.MediaSessionCompat;

static final class MediaSessionCompat.QueueItem
implements Parcelable.Creator<MediaSessionCompat.QueueItem> {
    MediaSessionCompat.QueueItem() {
    }

    public MediaSessionCompat.QueueItem createFromParcel(Parcel parcel) {
        return new MediaSessionCompat.QueueItem(parcel);
    }

    public MediaSessionCompat.QueueItem[] newArray(int n) {
        return new MediaSessionCompat.QueueItem[n];
    }
}
