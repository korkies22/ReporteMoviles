/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public class ShareHashtag
implements ShareModel {
    public static final Parcelable.Creator<ShareHashtag> CREATOR = new Parcelable.Creator<ShareHashtag>(){

        public ShareHashtag createFromParcel(Parcel parcel) {
            return new ShareHashtag(parcel);
        }

        public ShareHashtag[] newArray(int n) {
            return new ShareHashtag[n];
        }
    };
    private final String hashtag;

    ShareHashtag(Parcel parcel) {
        this.hashtag = parcel.readString();
    }

    private ShareHashtag(Builder builder) {
        this.hashtag = builder.hashtag;
    }

    public int describeContents() {
        return 0;
    }

    public String getHashtag() {
        return this.hashtag;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.hashtag);
    }

    public static class Builder
    implements ShareModelBuilder<ShareHashtag, Builder> {
        private String hashtag;

        @Override
        public ShareHashtag build() {
            return new ShareHashtag(this);
        }

        public String getHashtag() {
            return this.hashtag;
        }

        @Override
        Builder readFrom(Parcel parcel) {
            return this.readFrom((ShareHashtag)parcel.readParcelable(ShareHashtag.class.getClassLoader()));
        }

        @Override
        public Builder readFrom(ShareHashtag shareHashtag) {
            if (shareHashtag == null) {
                return this;
            }
            return this.setHashtag(shareHashtag.getHashtag());
        }

        public Builder setHashtag(String string) {
            this.hashtag = string;
            return this;
        }
    }

}
