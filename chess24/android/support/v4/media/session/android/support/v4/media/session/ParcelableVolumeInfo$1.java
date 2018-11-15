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

static final class ParcelableVolumeInfo
implements Parcelable.Creator<android.support.v4.media.session.ParcelableVolumeInfo> {
    ParcelableVolumeInfo() {
    }

    public android.support.v4.media.session.ParcelableVolumeInfo createFromParcel(Parcel parcel) {
        return new android.support.v4.media.session.ParcelableVolumeInfo(parcel);
    }

    public android.support.v4.media.session.ParcelableVolumeInfo[] newArray(int n) {
        return new android.support.v4.media.session.ParcelableVolumeInfo[n];
    }
}
