/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.facebook.share.model;

import android.net.Uri;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareMessengerActionButton;
import com.facebook.share.model.ShareMessengerURLActionButton;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public static final class ShareMessengerURLActionButton.Builder
extends ShareMessengerActionButton.Builder<ShareMessengerURLActionButton, ShareMessengerURLActionButton.Builder> {
    private Uri fallbackUrl;
    private boolean isMessengerExtensionURL;
    private boolean shouldHideWebviewShareButton;
    private Uri url;
    private ShareMessengerURLActionButton.WebviewHeightRatio webviewHeightRatio;

    static /* synthetic */ Uri access$000(ShareMessengerURLActionButton.Builder builder) {
        return builder.url;
    }

    static /* synthetic */ boolean access$100(ShareMessengerURLActionButton.Builder builder) {
        return builder.isMessengerExtensionURL;
    }

    static /* synthetic */ Uri access$200(ShareMessengerURLActionButton.Builder builder) {
        return builder.fallbackUrl;
    }

    static /* synthetic */ ShareMessengerURLActionButton.WebviewHeightRatio access$300(ShareMessengerURLActionButton.Builder builder) {
        return builder.webviewHeightRatio;
    }

    static /* synthetic */ boolean access$400(ShareMessengerURLActionButton.Builder builder) {
        return builder.shouldHideWebviewShareButton;
    }

    @Override
    public ShareMessengerURLActionButton build() {
        return new ShareMessengerURLActionButton(this, null);
    }

    @Override
    public ShareMessengerURLActionButton.Builder readFrom(ShareMessengerURLActionButton shareMessengerURLActionButton) {
        if (shareMessengerURLActionButton == null) {
            return this;
        }
        return this.setUrl(shareMessengerURLActionButton.getUrl()).setIsMessengerExtensionURL(shareMessengerURLActionButton.getIsMessengerExtensionURL()).setFallbackUrl(shareMessengerURLActionButton.getFallbackUrl()).setWebviewHeightRatio(shareMessengerURLActionButton.getWebviewHeightRatio()).setShouldHideWebviewShareButton(shareMessengerURLActionButton.getShouldHideWebviewShareButton());
    }

    public ShareMessengerURLActionButton.Builder setFallbackUrl(@Nullable Uri uri) {
        this.fallbackUrl = uri;
        return this;
    }

    public ShareMessengerURLActionButton.Builder setIsMessengerExtensionURL(boolean bl) {
        this.isMessengerExtensionURL = bl;
        return this;
    }

    public ShareMessengerURLActionButton.Builder setShouldHideWebviewShareButton(boolean bl) {
        this.shouldHideWebviewShareButton = bl;
        return this;
    }

    public ShareMessengerURLActionButton.Builder setUrl(@Nullable Uri uri) {
        this.url = uri;
        return this;
    }

    public ShareMessengerURLActionButton.Builder setWebviewHeightRatio(ShareMessengerURLActionButton.WebviewHeightRatio webviewHeightRatio) {
        this.webviewHeightRatio = webviewHeightRatio;
        return this;
    }
}
