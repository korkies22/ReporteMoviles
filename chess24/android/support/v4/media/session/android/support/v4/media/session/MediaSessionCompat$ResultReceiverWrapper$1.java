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

static final class MediaSessionCompat.ResultReceiverWrapper
implements Parcelable.Creator<MediaSessionCompat.ResultReceiverWrapper> {
    MediaSessionCompat.ResultReceiverWrapper() {
    }

    public MediaSessionCompat.ResultReceiverWrapper createFromParcel(Parcel parcel) {
        return new MediaSessionCompat.ResultReceiverWrapper(parcel);
    }

    public MediaSessionCompat.ResultReceiverWrapper[] newArray(int n) {
        return new MediaSessionCompat.ResultReceiverWrapper[n];
    }
}
