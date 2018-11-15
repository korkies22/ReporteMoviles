/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.share.internal.ShareFeedContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public static final class ShareFeedContent.Builder
extends ShareContent.Builder<ShareFeedContent, ShareFeedContent.Builder> {
    private String link;
    private String linkCaption;
    private String linkDescription;
    private String linkName;
    private String mediaSource;
    private String picture;
    private String toId;

    static /* synthetic */ String access$000(ShareFeedContent.Builder builder) {
        return builder.toId;
    }

    static /* synthetic */ String access$100(ShareFeedContent.Builder builder) {
        return builder.link;
    }

    static /* synthetic */ String access$200(ShareFeedContent.Builder builder) {
        return builder.linkName;
    }

    static /* synthetic */ String access$300(ShareFeedContent.Builder builder) {
        return builder.linkCaption;
    }

    static /* synthetic */ String access$400(ShareFeedContent.Builder builder) {
        return builder.linkDescription;
    }

    static /* synthetic */ String access$500(ShareFeedContent.Builder builder) {
        return builder.picture;
    }

    static /* synthetic */ String access$600(ShareFeedContent.Builder builder) {
        return builder.mediaSource;
    }

    @Override
    public ShareFeedContent build() {
        return new ShareFeedContent(this, null);
    }

    @Override
    public ShareFeedContent.Builder readFrom(ShareFeedContent shareFeedContent) {
        if (shareFeedContent == null) {
            return this;
        }
        return ((ShareFeedContent.Builder)super.readFrom(shareFeedContent)).setToId(shareFeedContent.getToId()).setLink(shareFeedContent.getLink()).setLinkName(shareFeedContent.getLinkName()).setLinkCaption(shareFeedContent.getLinkCaption()).setLinkDescription(shareFeedContent.getLinkDescription()).setPicture(shareFeedContent.getPicture()).setMediaSource(shareFeedContent.getMediaSource());
    }

    public ShareFeedContent.Builder setLink(String string) {
        this.link = string;
        return this;
    }

    public ShareFeedContent.Builder setLinkCaption(String string) {
        this.linkCaption = string;
        return this;
    }

    public ShareFeedContent.Builder setLinkDescription(String string) {
        this.linkDescription = string;
        return this;
    }

    public ShareFeedContent.Builder setLinkName(String string) {
        this.linkName = string;
        return this;
    }

    public ShareFeedContent.Builder setMediaSource(String string) {
        this.mediaSource = string;
        return this;
    }

    public ShareFeedContent.Builder setPicture(String string) {
        this.picture = string;
        return this;
    }

    public ShareFeedContent.Builder setToId(String string) {
        this.toId = string;
        return this;
    }
}
