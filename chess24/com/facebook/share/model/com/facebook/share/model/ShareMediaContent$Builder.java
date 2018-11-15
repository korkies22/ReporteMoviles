/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.model;

import android.support.annotation.Nullable;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.ShareVideo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public static class ShareMediaContent.Builder
extends ShareContent.Builder<ShareMediaContent, ShareMediaContent.Builder> {
    private final List<ShareMedia> media = new ArrayList<ShareMedia>();

    static /* synthetic */ List access$000(ShareMediaContent.Builder builder) {
        return builder.media;
    }

    public ShareMediaContent.Builder addMedia(@Nullable List<ShareMedia> object) {
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                this.addMedium((ShareMedia)object.next());
            }
        }
        return this;
    }

    public ShareMediaContent.Builder addMedium(@Nullable ShareMedia shareMedia) {
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
        return new ShareMediaContent(this, null);
    }

    @Override
    public ShareMediaContent.Builder readFrom(ShareMediaContent shareMediaContent) {
        if (shareMediaContent == null) {
            return this;
        }
        return ((ShareMediaContent.Builder)super.readFrom(shareMediaContent)).addMedia(shareMediaContent.getMedia());
    }

    public ShareMediaContent.Builder setMedia(@Nullable List<ShareMedia> list) {
        this.media.clear();
        this.addMedia(list);
        return this;
    }
}
