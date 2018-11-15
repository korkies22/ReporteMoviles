/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.model;

import android.support.annotation.Nullable;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;

public static final class ShareOpenGraphContent.Builder
extends ShareContent.Builder<ShareOpenGraphContent, ShareOpenGraphContent.Builder> {
    private ShareOpenGraphAction action;
    private String previewPropertyName;

    static /* synthetic */ ShareOpenGraphAction access$000(ShareOpenGraphContent.Builder builder) {
        return builder.action;
    }

    static /* synthetic */ String access$100(ShareOpenGraphContent.Builder builder) {
        return builder.previewPropertyName;
    }

    @Override
    public ShareOpenGraphContent build() {
        return new ShareOpenGraphContent(this, null);
    }

    @Override
    public ShareOpenGraphContent.Builder readFrom(ShareOpenGraphContent shareOpenGraphContent) {
        if (shareOpenGraphContent == null) {
            return this;
        }
        return ((ShareOpenGraphContent.Builder)super.readFrom(shareOpenGraphContent)).setAction(shareOpenGraphContent.getAction()).setPreviewPropertyName(shareOpenGraphContent.getPreviewPropertyName());
    }

    public ShareOpenGraphContent.Builder setAction(@Nullable ShareOpenGraphAction shareOpenGraphAction) {
        shareOpenGraphAction = shareOpenGraphAction == null ? null : new ShareOpenGraphAction.Builder().readFrom(shareOpenGraphAction).build();
        this.action = shareOpenGraphAction;
        return this;
    }

    public ShareOpenGraphContent.Builder setPreviewPropertyName(@Nullable String string) {
        this.previewPropertyName = string;
        return this;
    }
}
