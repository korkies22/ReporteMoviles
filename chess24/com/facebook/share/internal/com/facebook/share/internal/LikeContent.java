/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.facebook.share.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

@Deprecated
public class LikeContent
implements ShareModel {
    @Deprecated
    public static final Parcelable.Creator<LikeContent> CREATOR = new Parcelable.Creator<LikeContent>(){

        public LikeContent createFromParcel(Parcel parcel) {
            return new LikeContent(parcel);
        }

        public LikeContent[] newArray(int n) {
            return new LikeContent[n];
        }
    };
    private final String objectId;
    private final String objectType;

    @Deprecated
    LikeContent(Parcel parcel) {
        this.objectId = parcel.readString();
        this.objectType = parcel.readString();
    }

    private LikeContent(Builder builder) {
        this.objectId = builder.objectId;
        this.objectType = builder.objectType;
    }

    @Deprecated
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public String getObjectId() {
        return this.objectId;
    }

    @Deprecated
    public String getObjectType() {
        return this.objectType;
    }

    @Deprecated
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.objectId);
        parcel.writeString(this.objectType);
    }

    @Deprecated
    public static class Builder
    implements ShareModelBuilder<LikeContent, Builder> {
        private String objectId;
        private String objectType;

        @Deprecated
        @Override
        public LikeContent build() {
            return new LikeContent(this);
        }

        @Deprecated
        @Override
        public Builder readFrom(LikeContent likeContent) {
            if (likeContent == null) {
                return this;
            }
            return this.setObjectId(likeContent.getObjectId()).setObjectType(likeContent.getObjectType());
        }

        @Deprecated
        public Builder setObjectId(String string) {
            this.objectId = string;
            return this;
        }

        @Deprecated
        public Builder setObjectType(String string) {
            this.objectType = string;
            return this;
        }
    }

}
