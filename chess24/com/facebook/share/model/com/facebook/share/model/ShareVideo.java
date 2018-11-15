/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public final class ShareVideo
extends ShareMedia {
    public static final Parcelable.Creator<ShareVideo> CREATOR = new Parcelable.Creator<ShareVideo>(){

        public ShareVideo createFromParcel(Parcel parcel) {
            return new ShareVideo(parcel);
        }

        public ShareVideo[] newArray(int n) {
            return new ShareVideo[n];
        }
    };
    private final Uri localUrl;

    ShareVideo(Parcel parcel) {
        super(parcel);
        this.localUrl = (Uri)parcel.readParcelable(Uri.class.getClassLoader());
    }

    private ShareVideo(Builder builder) {
        super(builder);
        this.localUrl = builder.localUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Nullable
    public Uri getLocalUrl() {
        return this.localUrl;
    }

    @Override
    public ShareMedia.Type getMediaType() {
        return ShareMedia.Type.VIDEO;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeParcelable((Parcelable)this.localUrl, 0);
    }

    public static final class Builder
    extends ShareMedia.Builder<ShareVideo, Builder> {
        private Uri localUrl;

        @Override
        public ShareVideo build() {
            return new ShareVideo(this);
        }

        @Override
        Builder readFrom(Parcel parcel) {
            return this.readFrom((ShareVideo)parcel.readParcelable(ShareVideo.class.getClassLoader()));
        }

        @Override
        public Builder readFrom(ShareVideo shareVideo) {
            if (shareVideo == null) {
                return this;
            }
            return ((Builder)super.readFrom(shareVideo)).setLocalUrl(shareVideo.getLocalUrl());
        }

        public Builder setLocalUrl(@Nullable Uri uri) {
            this.localUrl = uri;
            return this;
        }
    }

}
