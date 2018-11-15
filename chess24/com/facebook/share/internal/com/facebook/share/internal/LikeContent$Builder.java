/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.share.internal.LikeContent;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

@Deprecated
public static class LikeContent.Builder
implements ShareModelBuilder<LikeContent, LikeContent.Builder> {
    private String objectId;
    private String objectType;

    static /* synthetic */ String access$000(LikeContent.Builder builder) {
        return builder.objectId;
    }

    static /* synthetic */ String access$100(LikeContent.Builder builder) {
        return builder.objectType;
    }

    @Deprecated
    @Override
    public LikeContent build() {
        return new LikeContent(this, null);
    }

    @Deprecated
    @Override
    public LikeContent.Builder readFrom(LikeContent likeContent) {
        if (likeContent == null) {
            return this;
        }
        return this.setObjectId(likeContent.getObjectId()).setObjectType(likeContent.getObjectType());
    }

    @Deprecated
    public LikeContent.Builder setObjectId(String string) {
        this.objectId = string;
        return this;
    }

    @Deprecated
    public LikeContent.Builder setObjectType(String string) {
        this.objectType = string;
        return this;
    }
}
