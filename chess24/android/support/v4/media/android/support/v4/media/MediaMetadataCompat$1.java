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

static final class MediaMetadataCompat
implements Parcelable.Creator<android.support.v4.media.MediaMetadataCompat> {
    MediaMetadataCompat() {
    }

    public android.support.v4.media.MediaMetadataCompat createFromParcel(Parcel parcel) {
        return new android.support.v4.media.MediaMetadataCompat(parcel);
    }

    public android.support.v4.media.MediaMetadataCompat[] newArray(int n) {
        return new android.support.v4.media.MediaMetadataCompat[n];
    }
}
