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
import android.support.annotation.RestrictTo;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.MediaSessionCompatApi21;

public static final class MediaSessionCompat.Token
implements Parcelable {
    public static final Parcelable.Creator<MediaSessionCompat.Token> CREATOR = new Parcelable.Creator<MediaSessionCompat.Token>(){

        public MediaSessionCompat.Token createFromParcel(Parcel object) {
            object = Build.VERSION.SDK_INT >= 21 ? object.readParcelable(null) : object.readStrongBinder();
            return new MediaSessionCompat.Token(object);
        }

        public MediaSessionCompat.Token[] newArray(int n) {
            return new MediaSessionCompat.Token[n];
        }
    };
    private final IMediaSession mExtraBinder;
    private final Object mInner;

    MediaSessionCompat.Token(Object object) {
        this(object, null);
    }

    MediaSessionCompat.Token(Object object, IMediaSession iMediaSession) {
        this.mInner = object;
        this.mExtraBinder = iMediaSession;
    }

    public static MediaSessionCompat.Token fromToken(Object object) {
        return MediaSessionCompat.Token.fromToken(object, null);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static MediaSessionCompat.Token fromToken(Object object, IMediaSession iMediaSession) {
        if (object != null && Build.VERSION.SDK_INT >= 21) {
            return new MediaSessionCompat.Token(MediaSessionCompatApi21.verifyToken(object), iMediaSession);
        }
        return null;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof MediaSessionCompat.Token)) {
            return false;
        }
        object = (MediaSessionCompat.Token)object;
        if (this.mInner == null) {
            if (object.mInner == null) {
                return true;
            }
            return false;
        }
        if (object.mInner == null) {
            return false;
        }
        return this.mInner.equals(object.mInner);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public IMediaSession getExtraBinder() {
        return this.mExtraBinder;
    }

    public Object getToken() {
        return this.mInner;
    }

    public int hashCode() {
        if (this.mInner == null) {
            return 0;
        }
        return this.mInner.hashCode();
    }

    public void writeToParcel(Parcel parcel, int n) {
        if (Build.VERSION.SDK_INT >= 21) {
            parcel.writeParcelable((Parcelable)this.mInner, n);
            return;
        }
        parcel.writeStrongBinder((IBinder)this.mInner);
    }

}
