/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.facebook.share.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class SharePhoto
extends ShareMedia {
    public static final Parcelable.Creator<SharePhoto> CREATOR = new Parcelable.Creator<SharePhoto>(){

        public SharePhoto createFromParcel(Parcel parcel) {
            return new SharePhoto(parcel);
        }

        public SharePhoto[] newArray(int n) {
            return new SharePhoto[n];
        }
    };
    private final Bitmap bitmap;
    private final String caption;
    private final Uri imageUrl;
    private final boolean userGenerated;

    SharePhoto(Parcel parcel) {
        super(parcel);
        this.bitmap = (Bitmap)parcel.readParcelable(Bitmap.class.getClassLoader());
        this.imageUrl = (Uri)parcel.readParcelable(Uri.class.getClassLoader());
        boolean bl = parcel.readByte() != 0;
        this.userGenerated = bl;
        this.caption = parcel.readString();
    }

    private SharePhoto(Builder builder) {
        super(builder);
        this.bitmap = builder.bitmap;
        this.imageUrl = builder.imageUrl;
        this.userGenerated = builder.userGenerated;
        this.caption = builder.caption;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Nullable
    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public String getCaption() {
        return this.caption;
    }

    @Nullable
    public Uri getImageUrl() {
        return this.imageUrl;
    }

    @Override
    public ShareMedia.Type getMediaType() {
        return ShareMedia.Type.PHOTO;
    }

    public boolean getUserGenerated() {
        return this.userGenerated;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeParcelable((Parcelable)this.bitmap, 0);
        parcel.writeParcelable((Parcelable)this.imageUrl, 0);
        parcel.writeByte((byte)(this.userGenerated ? 1 : 0));
        parcel.writeString(this.caption);
    }

    public static final class Builder
    extends ShareMedia.Builder<SharePhoto, Builder> {
        private Bitmap bitmap;
        private String caption;
        private Uri imageUrl;
        private boolean userGenerated;

        static List<SharePhoto> readPhotoListFrom(Parcel object) {
            Object object2 = Builder.readListFrom((Parcel)object);
            object = new ArrayList();
            object2 = object2.iterator();
            while (object2.hasNext()) {
                ShareMedia shareMedia = (ShareMedia)object2.next();
                if (!(shareMedia instanceof SharePhoto)) continue;
                object.add((SharePhoto)shareMedia);
            }
            return object;
        }

        static void writePhotoListTo(Parcel parcel, int n, List<SharePhoto> list) {
            Parcelable[] arrparcelable = new ShareMedia[list.size()];
            for (int i = 0; i < list.size(); ++i) {
                arrparcelable[i] = list.get(i);
            }
            parcel.writeParcelableArray(arrparcelable, n);
        }

        @Override
        public SharePhoto build() {
            return new SharePhoto(this);
        }

        Bitmap getBitmap() {
            return this.bitmap;
        }

        Uri getImageUrl() {
            return this.imageUrl;
        }

        @Override
        Builder readFrom(Parcel parcel) {
            return this.readFrom((SharePhoto)parcel.readParcelable(SharePhoto.class.getClassLoader()));
        }

        @Override
        public Builder readFrom(SharePhoto sharePhoto) {
            if (sharePhoto == null) {
                return this;
            }
            return ((Builder)super.readFrom(sharePhoto)).setBitmap(sharePhoto.getBitmap()).setImageUrl(sharePhoto.getImageUrl()).setUserGenerated(sharePhoto.getUserGenerated()).setCaption(sharePhoto.getCaption());
        }

        public Builder setBitmap(@Nullable Bitmap bitmap) {
            this.bitmap = bitmap;
            return this;
        }

        public Builder setCaption(@Nullable String string) {
            this.caption = string;
            return this;
        }

        public Builder setImageUrl(@Nullable Uri uri) {
            this.imageUrl = uri;
            return this;
        }

        public Builder setUserGenerated(boolean bl) {
            this.userGenerated = bl;
            return this;
        }
    }

}
