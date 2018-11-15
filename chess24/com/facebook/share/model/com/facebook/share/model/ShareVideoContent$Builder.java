/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.model;

import android.support.annotation.Nullable;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;

public static final class ShareVideoContent.Builder
extends ShareContent.Builder<ShareVideoContent, ShareVideoContent.Builder> {
    private String contentDescription;
    private String contentTitle;
    private SharePhoto previewPhoto;
    private ShareVideo video;

    static /* synthetic */ String access$000(ShareVideoContent.Builder builder) {
        return builder.contentDescription;
    }

    static /* synthetic */ String access$100(ShareVideoContent.Builder builder) {
        return builder.contentTitle;
    }

    static /* synthetic */ SharePhoto access$200(ShareVideoContent.Builder builder) {
        return builder.previewPhoto;
    }

    static /* synthetic */ ShareVideo access$300(ShareVideoContent.Builder builder) {
        return builder.video;
    }

    @Override
    public ShareVideoContent build() {
        return new ShareVideoContent(this, null);
    }

    @Override
    public ShareVideoContent.Builder readFrom(ShareVideoContent shareVideoContent) {
        if (shareVideoContent == null) {
            return this;
        }
        return ((ShareVideoContent.Builder)super.readFrom(shareVideoContent)).setContentDescription(shareVideoContent.getContentDescription()).setContentTitle(shareVideoContent.getContentTitle()).setPreviewPhoto(shareVideoContent.getPreviewPhoto()).setVideo(shareVideoContent.getVideo());
    }

    public ShareVideoContent.Builder setContentDescription(@Nullable String string) {
        this.contentDescription = string;
        return this;
    }

    public ShareVideoContent.Builder setContentTitle(@Nullable String string) {
        this.contentTitle = string;
        return this;
    }

    public ShareVideoContent.Builder setPreviewPhoto(@Nullable SharePhoto sharePhoto) {
        sharePhoto = sharePhoto == null ? null : new SharePhoto.Builder().readFrom(sharePhoto).build();
        this.previewPhoto = sharePhoto;
        return this;
    }

    public ShareVideoContent.Builder setVideo(@Nullable ShareVideo shareVideo) {
        if (shareVideo == null) {
            return this;
        }
        this.video = new ShareVideo.Builder().readFrom(shareVideo).build();
        return this;
    }
}
