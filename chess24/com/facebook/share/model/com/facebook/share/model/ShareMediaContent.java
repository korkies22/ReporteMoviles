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
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.ShareVideo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class ShareMediaContent
extends ShareContent<ShareMediaContent, Builder> {
    public static final Parcelable.Creator<ShareMediaContent> CREATOR = new Parcelable.Creator<ShareMediaContent>(){

        public ShareMediaContent createFromParcel(Parcel parcel) {
            return new ShareMediaContent(parcel);
        }

        public ShareMediaContent[] newArray(int n) {
            return new ShareMediaContent[n];
        }
    };
    private final List<ShareMedia> media;

    ShareMediaContent(Parcel parcel) {
        super(parcel);
        this.media = Arrays.asList((ShareMedia[])parcel.readParcelableArray(ShareMedia.class.getClassLoader()));
    }

    private ShareMediaContent(Builder builder) {
        super(builder);
        this.media = Collections.unmodifiableList(builder.media);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Nullable
    public List<ShareMedia> getMedia() {
        return this.media;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeParcelableArray((Parcelable[])((ShareMedia[])this.media.toArray()), n);
    }

    public static class Builder
    extends ShareContent.Builder<ShareMediaContent, Builder> {
        private final List<ShareMedia> media = new ArrayList<ShareMedia>();

        public Builder addMedia(@Nullable List<ShareMedia> object) {
            if (object != null) {
                object = object.iterator();
                while (object.hasNext()) {
                    this.addMedium((ShareMedia)object.next());
                }
            }
            return this;
        }

        public Builder addMedium(@Nullable ShareMedia shareMedia) {
            block2 : {
                block5 : {
                    block4 : {
                        block3 : {
                            if (shareMedia == null) break block2;
                            if (!(shareMedia instanceof SharePhoto)) break block3;
                            shareMedia = new SharePhoto.Builder().readFrom((SharePhoto)shareMedia).build();
                            break block4;
                        }
                        if (!(shareMedia instanceof ShareVideo)) break block5;
                        shareMedia = new ShareVideo.Builder().readFrom((ShareVideo)shareMedia).build();
                    }
                    this.media.add(shareMedia);
                    return this;
                }
                throw new IllegalArgumentException("medium must be either a SharePhoto or ShareVideo");
            }
            return this;
        }

        @Override
        public ShareMediaContent build() {
            return new ShareMediaContent(this);
        }

        @Override
        public Builder readFrom(ShareMediaContent shareMediaContent) {
            if (shareMediaContent == null) {
                return this;
            }
            return ((Builder)super.readFrom(shareMediaContent)).addMedia(shareMediaContent.getMedia());
        }

        public Builder setMedia(@Nullable List<ShareMedia> list) {
            this.media.clear();
            this.addMedia(list);
            return this;
        }
    }

}
