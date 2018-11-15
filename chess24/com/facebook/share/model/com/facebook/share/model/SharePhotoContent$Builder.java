/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.model;

import android.support.annotation.Nullable;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public static class SharePhotoContent.Builder
extends ShareContent.Builder<SharePhotoContent, SharePhotoContent.Builder> {
    private final List<SharePhoto> photos = new ArrayList<SharePhoto>();

    static /* synthetic */ List access$000(SharePhotoContent.Builder builder) {
        return builder.photos;
    }

    public SharePhotoContent.Builder addPhoto(@Nullable SharePhoto sharePhoto) {
        if (sharePhoto != null) {
            this.photos.add(new SharePhoto.Builder().readFrom(sharePhoto).build());
        }
        return this;
    }

    public SharePhotoContent.Builder addPhotos(@Nullable List<SharePhoto> object) {
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                this.addPhoto((SharePhoto)object.next());
            }
        }
        return this;
    }

    @Override
    public SharePhotoContent build() {
        return new SharePhotoContent(this, null);
    }

    @Override
    public SharePhotoContent.Builder readFrom(SharePhotoContent sharePhotoContent) {
        if (sharePhotoContent == null) {
            return this;
        }
        return ((SharePhotoContent.Builder)super.readFrom(sharePhotoContent)).addPhotos(sharePhotoContent.getPhotos());
    }

    public SharePhotoContent.Builder setPhotos(@Nullable List<SharePhoto> list) {
        this.photos.clear();
        this.addPhotos(list);
        return this;
    }
}
