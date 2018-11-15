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
import com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public static class ShareMessengerOpenGraphMusicTemplateContent.Builder
extends ShareContent.Builder<ShareMessengerOpenGraphMusicTemplateContent, ShareMessengerOpenGraphMusicTemplateContent.Builder> {
    private ShareMessengerActionButton button;
    private Uri url;

    static /* synthetic */ Uri access$000(ShareMessengerOpenGraphMusicTemplateContent.Builder builder) {
        return builder.url;
    }

    static /* synthetic */ ShareMessengerActionButton access$100(ShareMessengerOpenGraphMusicTemplateContent.Builder builder) {
        return builder.button;
    }

    @Override
    public ShareMessengerOpenGraphMusicTemplateContent build() {
        return new ShareMessengerOpenGraphMusicTemplateContent(this, null);
    }

    @Override
    public ShareMessengerOpenGraphMusicTemplateContent.Builder readFrom(ShareMessengerOpenGraphMusicTemplateContent shareMessengerOpenGraphMusicTemplateContent) {
        if (shareMessengerOpenGraphMusicTemplateContent == null) {
            return this;
        }
        return ((ShareMessengerOpenGraphMusicTemplateContent.Builder)super.readFrom(shareMessengerOpenGraphMusicTemplateContent)).setUrl(shareMessengerOpenGraphMusicTemplateContent.getUrl()).setButton(shareMessengerOpenGraphMusicTemplateContent.getButton());
    }

    public ShareMessengerOpenGraphMusicTemplateContent.Builder setButton(ShareMessengerActionButton shareMessengerActionButton) {
        this.button = shareMessengerActionButton;
        return this;
    }

    public ShareMessengerOpenGraphMusicTemplateContent.Builder setUrl(Uri uri) {
        this.url = uri;
        return this;
    }
}
