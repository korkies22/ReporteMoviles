/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.facebook.share.model;

import android.net.Uri;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareMessengerActionButton;
import com.facebook.share.model.ShareMessengerMediaTemplateContent;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public static class ShareMessengerMediaTemplateContent.Builder
extends ShareContent.Builder<ShareMessengerMediaTemplateContent, ShareMessengerMediaTemplateContent.Builder> {
    private String attachmentId;
    private ShareMessengerActionButton button;
    private ShareMessengerMediaTemplateContent.MediaType mediaType;
    private Uri mediaUrl;

    static /* synthetic */ ShareMessengerMediaTemplateContent.MediaType access$000(ShareMessengerMediaTemplateContent.Builder builder) {
        return builder.mediaType;
    }

    static /* synthetic */ String access$100(ShareMessengerMediaTemplateContent.Builder builder) {
        return builder.attachmentId;
    }

    static /* synthetic */ Uri access$200(ShareMessengerMediaTemplateContent.Builder builder) {
        return builder.mediaUrl;
    }

    static /* synthetic */ ShareMessengerActionButton access$300(ShareMessengerMediaTemplateContent.Builder builder) {
        return builder.button;
    }

    @Override
    public ShareMessengerMediaTemplateContent build() {
        return new ShareMessengerMediaTemplateContent(this, null);
    }

    @Override
    public ShareMessengerMediaTemplateContent.Builder readFrom(ShareMessengerMediaTemplateContent shareMessengerMediaTemplateContent) {
        if (shareMessengerMediaTemplateContent == null) {
            return this;
        }
        return ((ShareMessengerMediaTemplateContent.Builder)super.readFrom(shareMessengerMediaTemplateContent)).setMediaType(shareMessengerMediaTemplateContent.getMediaType()).setAttachmentId(shareMessengerMediaTemplateContent.getAttachmentId()).setMediaUrl(shareMessengerMediaTemplateContent.getMediaUrl()).setButton(shareMessengerMediaTemplateContent.getButton());
    }

    public ShareMessengerMediaTemplateContent.Builder setAttachmentId(String string) {
        this.attachmentId = string;
        return this;
    }

    public ShareMessengerMediaTemplateContent.Builder setButton(ShareMessengerActionButton shareMessengerActionButton) {
        this.button = shareMessengerActionButton;
        return this;
    }

    public ShareMessengerMediaTemplateContent.Builder setMediaType(ShareMessengerMediaTemplateContent.MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public ShareMessengerMediaTemplateContent.Builder setMediaUrl(Uri uri) {
        this.mediaUrl = uri;
        return this;
    }
}
