/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.media.MediaDescriptionCompatApi21;
import android.support.v4.media.MediaDescriptionCompatApi23;
import android.text.TextUtils;

public final class MediaDescriptionCompat
implements Parcelable {
    public static final long BT_FOLDER_TYPE_ALBUMS = 2L;
    public static final long BT_FOLDER_TYPE_ARTISTS = 3L;
    public static final long BT_FOLDER_TYPE_GENRES = 4L;
    public static final long BT_FOLDER_TYPE_MIXED = 0L;
    public static final long BT_FOLDER_TYPE_PLAYLISTS = 5L;
    public static final long BT_FOLDER_TYPE_TITLES = 1L;
    public static final long BT_FOLDER_TYPE_YEARS = 6L;
    public static final Parcelable.Creator<MediaDescriptionCompat> CREATOR = new Parcelable.Creator<MediaDescriptionCompat>(){

        public MediaDescriptionCompat createFromParcel(Parcel parcel) {
            if (Build.VERSION.SDK_INT < 21) {
                return new MediaDescriptionCompat(parcel);
            }
            return MediaDescriptionCompat.fromMediaDescription(MediaDescriptionCompatApi21.fromParcel(parcel));
        }

        public MediaDescriptionCompat[] newArray(int n) {
            return new MediaDescriptionCompat[n];
        }
    };
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static final String DESCRIPTION_KEY_MEDIA_URI = "android.support.v4.media.description.MEDIA_URI";
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static final String DESCRIPTION_KEY_NULL_BUNDLE_FLAG = "android.support.v4.media.description.NULL_BUNDLE_FLAG";
    public static final String EXTRA_BT_FOLDER_TYPE = "android.media.extra.BT_FOLDER_TYPE";
    public static final String EXTRA_DOWNLOAD_STATUS = "android.media.extra.DOWNLOAD_STATUS";
    public static final long STATUS_DOWNLOADED = 2L;
    public static final long STATUS_DOWNLOADING = 1L;
    public static final long STATUS_NOT_DOWNLOADED = 0L;
    private final CharSequence mDescription;
    private Object mDescriptionObj;
    private final Bundle mExtras;
    private final Bitmap mIcon;
    private final Uri mIconUri;
    private final String mMediaId;
    private final Uri mMediaUri;
    private final CharSequence mSubtitle;
    private final CharSequence mTitle;

    MediaDescriptionCompat(Parcel parcel) {
        this.mMediaId = parcel.readString();
        this.mTitle = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSubtitle = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mDescription = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mIcon = (Bitmap)parcel.readParcelable(null);
        this.mIconUri = (Uri)parcel.readParcelable(null);
        this.mExtras = parcel.readBundle();
        this.mMediaUri = (Uri)parcel.readParcelable(null);
    }

    MediaDescriptionCompat(String string, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Bitmap bitmap, Uri uri, Bundle bundle, Uri uri2) {
        this.mMediaId = string;
        this.mTitle = charSequence;
        this.mSubtitle = charSequence2;
        this.mDescription = charSequence3;
        this.mIcon = bitmap;
        this.mIconUri = uri;
        this.mExtras = bundle;
        this.mMediaUri = uri2;
    }

    public static MediaDescriptionCompat fromMediaDescription(Object object) {
        block6 : {
            Object object2;
            Builder builder;
            Bundle bundle;
            block8 : {
                Bundle bundle2;
                block7 : {
                    bundle = null;
                    if (object == null || Build.VERSION.SDK_INT < 21) break block6;
                    builder = new Builder();
                    builder.setMediaId(MediaDescriptionCompatApi21.getMediaId(object));
                    builder.setTitle(MediaDescriptionCompatApi21.getTitle(object));
                    builder.setSubtitle(MediaDescriptionCompatApi21.getSubtitle(object));
                    builder.setDescription(MediaDescriptionCompatApi21.getDescription(object));
                    builder.setIconBitmap(MediaDescriptionCompatApi21.getIconBitmap(object));
                    builder.setIconUri(MediaDescriptionCompatApi21.getIconUri(object));
                    bundle2 = MediaDescriptionCompatApi21.getExtras(object);
                    object2 = bundle2 == null ? null : (Uri)bundle2.getParcelable(DESCRIPTION_KEY_MEDIA_URI);
                    if (object2 == null) break block7;
                    if (bundle2.containsKey(DESCRIPTION_KEY_NULL_BUNDLE_FLAG) && bundle2.size() == 2) break block8;
                    bundle2.remove(DESCRIPTION_KEY_MEDIA_URI);
                    bundle2.remove(DESCRIPTION_KEY_NULL_BUNDLE_FLAG);
                }
                bundle = bundle2;
            }
            builder.setExtras(bundle);
            if (object2 != null) {
                builder.setMediaUri((Uri)object2);
            } else if (Build.VERSION.SDK_INT >= 23) {
                builder.setMediaUri(MediaDescriptionCompatApi23.getMediaUri(object));
            }
            object2 = builder.build();
            object2.mDescriptionObj = object;
            return object2;
        }
        return null;
    }

    public int describeContents() {
        return 0;
    }

    @Nullable
    public CharSequence getDescription() {
        return this.mDescription;
    }

    @Nullable
    public Bundle getExtras() {
        return this.mExtras;
    }

    @Nullable
    public Bitmap getIconBitmap() {
        return this.mIcon;
    }

    @Nullable
    public Uri getIconUri() {
        return this.mIconUri;
    }

    public Object getMediaDescription() {
        if (this.mDescriptionObj == null && Build.VERSION.SDK_INT >= 21) {
            Bundle bundle;
            Object object = MediaDescriptionCompatApi21.Builder.newInstance();
            MediaDescriptionCompatApi21.Builder.setMediaId(object, this.mMediaId);
            MediaDescriptionCompatApi21.Builder.setTitle(object, this.mTitle);
            MediaDescriptionCompatApi21.Builder.setSubtitle(object, this.mSubtitle);
            MediaDescriptionCompatApi21.Builder.setDescription(object, this.mDescription);
            MediaDescriptionCompatApi21.Builder.setIconBitmap(object, this.mIcon);
            MediaDescriptionCompatApi21.Builder.setIconUri(object, this.mIconUri);
            Bundle bundle2 = bundle = this.mExtras;
            if (Build.VERSION.SDK_INT < 23) {
                bundle2 = bundle;
                if (this.mMediaUri != null) {
                    bundle2 = bundle;
                    if (bundle == null) {
                        bundle2 = new Bundle();
                        bundle2.putBoolean(DESCRIPTION_KEY_NULL_BUNDLE_FLAG, true);
                    }
                    bundle2.putParcelable(DESCRIPTION_KEY_MEDIA_URI, (Parcelable)this.mMediaUri);
                }
            }
            MediaDescriptionCompatApi21.Builder.setExtras(object, bundle2);
            if (Build.VERSION.SDK_INT >= 23) {
                MediaDescriptionCompatApi23.Builder.setMediaUri(object, this.mMediaUri);
            }
            this.mDescriptionObj = MediaDescriptionCompatApi21.Builder.build(object);
            return this.mDescriptionObj;
        }
        return this.mDescriptionObj;
    }

    @Nullable
    public String getMediaId() {
        return this.mMediaId;
    }

    @Nullable
    public Uri getMediaUri() {
        return this.mMediaUri;
    }

    @Nullable
    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    @Nullable
    public CharSequence getTitle() {
        return this.mTitle;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((Object)this.mTitle);
        stringBuilder.append(", ");
        stringBuilder.append((Object)this.mSubtitle);
        stringBuilder.append(", ");
        stringBuilder.append((Object)this.mDescription);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        if (Build.VERSION.SDK_INT < 21) {
            parcel.writeString(this.mMediaId);
            TextUtils.writeToParcel((CharSequence)this.mTitle, (Parcel)parcel, (int)n);
            TextUtils.writeToParcel((CharSequence)this.mSubtitle, (Parcel)parcel, (int)n);
            TextUtils.writeToParcel((CharSequence)this.mDescription, (Parcel)parcel, (int)n);
            parcel.writeParcelable((Parcelable)this.mIcon, n);
            parcel.writeParcelable((Parcelable)this.mIconUri, n);
            parcel.writeBundle(this.mExtras);
            parcel.writeParcelable((Parcelable)this.mMediaUri, n);
            return;
        }
        MediaDescriptionCompatApi21.writeToParcel(this.getMediaDescription(), parcel, n);
    }

    public static final class Builder {
        private CharSequence mDescription;
        private Bundle mExtras;
        private Bitmap mIcon;
        private Uri mIconUri;
        private String mMediaId;
        private Uri mMediaUri;
        private CharSequence mSubtitle;
        private CharSequence mTitle;

        public MediaDescriptionCompat build() {
            return new MediaDescriptionCompat(this.mMediaId, this.mTitle, this.mSubtitle, this.mDescription, this.mIcon, this.mIconUri, this.mExtras, this.mMediaUri);
        }

        public Builder setDescription(@Nullable CharSequence charSequence) {
            this.mDescription = charSequence;
            return this;
        }

        public Builder setExtras(@Nullable Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setIconBitmap(@Nullable Bitmap bitmap) {
            this.mIcon = bitmap;
            return this;
        }

        public Builder setIconUri(@Nullable Uri uri) {
            this.mIconUri = uri;
            return this;
        }

        public Builder setMediaId(@Nullable String string) {
            this.mMediaId = string;
            return this;
        }

        public Builder setMediaUri(@Nullable Uri uri) {
            this.mMediaUri = uri;
            return this;
        }

        public Builder setSubtitle(@Nullable CharSequence charSequence) {
            this.mSubtitle = charSequence;
            return this;
        }

        public Builder setTitle(@Nullable CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }
    }

}
