/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package android.support.v4.media;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserCompatApi21;
import android.support.v4.media.MediaDescriptionCompat;
import android.text.TextUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public static class MediaBrowserCompat.MediaItem
implements Parcelable {
    public static final Parcelable.Creator<MediaBrowserCompat.MediaItem> CREATOR = new Parcelable.Creator<MediaBrowserCompat.MediaItem>(){

        public MediaBrowserCompat.MediaItem createFromParcel(Parcel parcel) {
            return new MediaBrowserCompat.MediaItem(parcel);
        }

        public MediaBrowserCompat.MediaItem[] newArray(int n) {
            return new MediaBrowserCompat.MediaItem[n];
        }
    };
    public static final int FLAG_BROWSABLE = 1;
    public static final int FLAG_PLAYABLE = 2;
    private final MediaDescriptionCompat mDescription;
    private final int mFlags;

    MediaBrowserCompat.MediaItem(Parcel parcel) {
        this.mFlags = parcel.readInt();
        this.mDescription = (MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
    }

    public MediaBrowserCompat.MediaItem(@NonNull MediaDescriptionCompat mediaDescriptionCompat, int n) {
        if (mediaDescriptionCompat == null) {
            throw new IllegalArgumentException("description cannot be null");
        }
        if (TextUtils.isEmpty((CharSequence)mediaDescriptionCompat.getMediaId())) {
            throw new IllegalArgumentException("description must have a non-empty media id");
        }
        this.mFlags = n;
        this.mDescription = mediaDescriptionCompat;
    }

    public static MediaBrowserCompat.MediaItem fromMediaItem(Object object) {
        if (object != null && Build.VERSION.SDK_INT >= 21) {
            int n = MediaBrowserCompatApi21.MediaItem.getFlags(object);
            return new MediaBrowserCompat.MediaItem(MediaDescriptionCompat.fromMediaDescription(MediaBrowserCompatApi21.MediaItem.getDescription(object)), n);
        }
        return null;
    }

    public static List<MediaBrowserCompat.MediaItem> fromMediaItemList(List<?> object) {
        if (object != null && Build.VERSION.SDK_INT >= 21) {
            ArrayList<MediaBrowserCompat.MediaItem> arrayList = new ArrayList<MediaBrowserCompat.MediaItem>(object.size());
            object = object.iterator();
            while (object.hasNext()) {
                arrayList.add(MediaBrowserCompat.MediaItem.fromMediaItem(object.next()));
            }
            return arrayList;
        }
        return null;
    }

    public int describeContents() {
        return 0;
    }

    @NonNull
    public MediaDescriptionCompat getDescription() {
        return this.mDescription;
    }

    public int getFlags() {
        return this.mFlags;
    }

    @Nullable
    public String getMediaId() {
        return this.mDescription.getMediaId();
    }

    public boolean isBrowsable() {
        if ((this.mFlags & 1) != 0) {
            return true;
        }
        return false;
    }

    public boolean isPlayable() {
        if ((this.mFlags & 2) != 0) {
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("MediaItem{");
        stringBuilder.append("mFlags=");
        stringBuilder.append(this.mFlags);
        stringBuilder.append(", mDescription=");
        stringBuilder.append(this.mDescription);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mFlags);
        this.mDescription.writeToParcel(parcel, n);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface Flags {
    }

}
