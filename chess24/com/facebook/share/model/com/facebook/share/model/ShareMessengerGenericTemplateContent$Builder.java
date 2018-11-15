/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.model;

import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareMessengerGenericTemplateContent;
import com.facebook.share.model.ShareMessengerGenericTemplateElement;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public static class ShareMessengerGenericTemplateContent.Builder
extends ShareContent.Builder<ShareMessengerGenericTemplateContent, ShareMessengerGenericTemplateContent.Builder> {
    private ShareMessengerGenericTemplateElement genericTemplateElement;
    private ShareMessengerGenericTemplateContent.ImageAspectRatio imageAspectRatio;
    private boolean isSharable;

    static /* synthetic */ boolean access$000(ShareMessengerGenericTemplateContent.Builder builder) {
        return builder.isSharable;
    }

    static /* synthetic */ ShareMessengerGenericTemplateContent.ImageAspectRatio access$100(ShareMessengerGenericTemplateContent.Builder builder) {
        return builder.imageAspectRatio;
    }

    static /* synthetic */ ShareMessengerGenericTemplateElement access$200(ShareMessengerGenericTemplateContent.Builder builder) {
        return builder.genericTemplateElement;
    }

    @Override
    public ShareMessengerGenericTemplateContent build() {
        return new ShareMessengerGenericTemplateContent(this);
    }

    @Override
    public ShareMessengerGenericTemplateContent.Builder readFrom(ShareMessengerGenericTemplateContent shareMessengerGenericTemplateContent) {
        if (shareMessengerGenericTemplateContent == null) {
            return this;
        }
        return ((ShareMessengerGenericTemplateContent.Builder)super.readFrom(shareMessengerGenericTemplateContent)).setIsSharable(shareMessengerGenericTemplateContent.getIsSharable()).setImageAspectRatio(shareMessengerGenericTemplateContent.getImageAspectRatio()).setGenericTemplateElement(shareMessengerGenericTemplateContent.getGenericTemplateElement());
    }

    public ShareMessengerGenericTemplateContent.Builder setGenericTemplateElement(ShareMessengerGenericTemplateElement shareMessengerGenericTemplateElement) {
        this.genericTemplateElement = shareMessengerGenericTemplateElement;
        return this;
    }

    public ShareMessengerGenericTemplateContent.Builder setImageAspectRatio(ShareMessengerGenericTemplateContent.ImageAspectRatio imageAspectRatio) {
        this.imageAspectRatio = imageAspectRatio;
        return this;
    }

    public ShareMessengerGenericTemplateContent.Builder setIsSharable(boolean bl) {
        this.isSharable = bl;
        return this;
    }
}
