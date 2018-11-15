/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public static class ShareHashtag.Builder
implements ShareModelBuilder<ShareHashtag, ShareHashtag.Builder> {
    private String hashtag;

    static /* synthetic */ String access$000(ShareHashtag.Builder builder) {
        return builder.hashtag;
    }

    @Override
    public ShareHashtag build() {
        return new ShareHashtag(this, null);
    }

    public String getHashtag() {
        return this.hashtag;
    }

    @Override
    ShareHashtag.Builder readFrom(Parcel parcel) {
        return this.readFrom((ShareHashtag)parcel.readParcelable(ShareHashtag.class.getClassLoader()));
    }

    @Override
    public ShareHashtag.Builder readFrom(ShareHashtag shareHashtag) {
        if (shareHashtag == null) {
            return this;
        }
        return this.setHashtag(shareHashtag.getHashtag());
    }

    public ShareHashtag.Builder setHashtag(String string) {
        this.hashtag = string;
        return this;
    }
}
