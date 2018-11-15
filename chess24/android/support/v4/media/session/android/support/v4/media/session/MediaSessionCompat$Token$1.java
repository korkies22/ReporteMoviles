/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.v4.media.session;

import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.session.MediaSessionCompat;

static final class MediaSessionCompat.Token
implements Parcelable.Creator<MediaSessionCompat.Token> {
    MediaSessionCompat.Token() {
    }

    public MediaSessionCompat.Token createFromParcel(Parcel object) {
        object = Build.VERSION.SDK_INT >= 21 ? object.readParcelable(null) : object.readStrongBinder();
        return new MediaSessionCompat.Token(object);
    }

    public MediaSessionCompat.Token[] newArray(int n) {
        return new MediaSessionCompat.Token[n];
    }
}
