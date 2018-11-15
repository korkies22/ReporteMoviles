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

static final class PlaybackStateCompat
implements Parcelable.Creator<android.support.v4.media.session.PlaybackStateCompat> {
    PlaybackStateCompat() {
    }

    public android.support.v4.media.session.PlaybackStateCompat createFromParcel(Parcel parcel) {
        return new android.support.v4.media.session.PlaybackStateCompat(parcel);
    }

    public android.support.v4.media.session.PlaybackStateCompat[] newArray(int n) {
        return new android.support.v4.media.session.PlaybackStateCompat[n];
    }
}
