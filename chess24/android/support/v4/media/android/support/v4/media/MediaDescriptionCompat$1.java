/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.v4.media;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaDescriptionCompatApi21;

static final class MediaDescriptionCompat
implements Parcelable.Creator<android.support.v4.media.MediaDescriptionCompat> {
    MediaDescriptionCompat() {
    }

    public android.support.v4.media.MediaDescriptionCompat createFromParcel(Parcel parcel) {
        if (Build.VERSION.SDK_INT < 21) {
            return new android.support.v4.media.MediaDescriptionCompat(parcel);
        }
        return android.support.v4.media.MediaDescriptionCompat.fromMediaDescription(MediaDescriptionCompatApi21.fromParcel(parcel));
    }

    public android.support.v4.media.MediaDescriptionCompat[] newArray(int n) {
        return new android.support.v4.media.MediaDescriptionCompat[n];
    }
}
