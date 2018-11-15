/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import com.facebook.share.model.ShareVideo;

public static final class ShareVideo.Builder
extends ShareMedia.Builder<ShareVideo, ShareVideo.Builder> {
    private Uri localUrl;

    static /* synthetic */ Uri access$000(ShareVideo.Builder builder) {
        return builder.localUrl;
    }

    @Override
    public ShareVideo build() {
        return new ShareVideo(this, null);
    }

    @Override
    ShareVideo.Builder readFrom(Parcel parcel) {
        return this.readFrom((ShareVideo)parcel.readParcelable(ShareVideo.class.getClassLoader()));
    }

    @Override
    public ShareVideo.Builder readFrom(ShareVideo shareVideo) {
        if (shareVideo == null) {
            return this;
        }
        return ((ShareVideo.Builder)super.readFrom(shareVideo)).setLocalUrl(shareVideo.getLocalUrl());
    }

    public ShareVideo.Builder setLocalUrl(@Nullable Uri uri) {
        this.localUrl = uri;
        return this;
    }
}
