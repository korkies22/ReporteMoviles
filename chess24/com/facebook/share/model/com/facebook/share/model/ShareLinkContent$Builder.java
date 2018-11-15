/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.util.Log
 */
package com.facebook.share.model;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public static final class ShareLinkContent.Builder
extends ShareContent.Builder<ShareLinkContent, ShareLinkContent.Builder> {
    static final String TAG = "ShareLinkContent$Builder";
    @Deprecated
    private String contentDescription;
    @Deprecated
    private String contentTitle;
    @Deprecated
    private Uri imageUrl;
    private String quote;

    static /* synthetic */ String access$000(ShareLinkContent.Builder builder) {
        return builder.contentDescription;
    }

    static /* synthetic */ String access$100(ShareLinkContent.Builder builder) {
        return builder.contentTitle;
    }

    static /* synthetic */ Uri access$200(ShareLinkContent.Builder builder) {
        return builder.imageUrl;
    }

    static /* synthetic */ String access$300(ShareLinkContent.Builder builder) {
        return builder.quote;
    }

    @Override
    public ShareLinkContent build() {
        return new ShareLinkContent(this, null);
    }

    @Override
    public ShareLinkContent.Builder readFrom(ShareLinkContent shareLinkContent) {
        if (shareLinkContent == null) {
            return this;
        }
        return ((ShareLinkContent.Builder)super.readFrom(shareLinkContent)).setContentDescription(shareLinkContent.getContentDescription()).setImageUrl(shareLinkContent.getImageUrl()).setContentTitle(shareLinkContent.getContentTitle()).setQuote(shareLinkContent.getQuote());
    }

    @Deprecated
    public ShareLinkContent.Builder setContentDescription(@Nullable String string) {
        Log.w((String)TAG, (String)"This method does nothing. ContentDescription is deprecated in Graph API 2.9.");
        return this;
    }

    @Deprecated
    public ShareLinkContent.Builder setContentTitle(@Nullable String string) {
        Log.w((String)TAG, (String)"This method does nothing. ContentTitle is deprecated in Graph API 2.9.");
        return this;
    }

    @Deprecated
    public ShareLinkContent.Builder setImageUrl(@Nullable Uri uri) {
        Log.w((String)TAG, (String)"This method does nothing. ImageUrl is deprecated in Graph API 2.9.");
        return this;
    }

    public ShareLinkContent.Builder setQuote(@Nullable String string) {
        this.quote = string;
        return this;
    }
}
