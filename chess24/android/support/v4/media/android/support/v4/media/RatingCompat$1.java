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

static final class RatingCompat
implements Parcelable.Creator<android.support.v4.media.RatingCompat> {
    RatingCompat() {
    }

    public android.support.v4.media.RatingCompat createFromParcel(Parcel parcel) {
        return new android.support.v4.media.RatingCompat(parcel.readInt(), parcel.readFloat());
    }

    public android.support.v4.media.RatingCompat[] newArray(int n) {
        return new android.support.v4.media.RatingCompat[n];
    }
}
