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
import android.support.v4.media.session.PlaybackStateCompat;

static final class PlaybackStateCompat.CustomAction
implements Parcelable.Creator<PlaybackStateCompat.CustomAction> {
    PlaybackStateCompat.CustomAction() {
    }

    public PlaybackStateCompat.CustomAction createFromParcel(Parcel parcel) {
        return new PlaybackStateCompat.CustomAction(parcel);
    }

    public PlaybackStateCompat.CustomAction[] newArray(int n) {
        return new PlaybackStateCompat.CustomAction[n];
    }
}
