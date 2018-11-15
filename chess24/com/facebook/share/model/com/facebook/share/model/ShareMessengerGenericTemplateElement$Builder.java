/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.model.ShareMessengerActionButton;
import com.facebook.share.model.ShareMessengerGenericTemplateElement;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public static class ShareMessengerGenericTemplateElement.Builder
implements ShareModelBuilder<ShareMessengerGenericTemplateElement, ShareMessengerGenericTemplateElement.Builder> {
    private ShareMessengerActionButton button;
    private ShareMessengerActionButton defaultAction;
    private Uri imageUrl;
    private String subtitle;
    private String title;

    static /* synthetic */ String access$000(ShareMessengerGenericTemplateElement.Builder builder) {
        return builder.title;
    }

    static /* synthetic */ String access$100(ShareMessengerGenericTemplateElement.Builder builder) {
        return builder.subtitle;
    }

    static /* synthetic */ Uri access$200(ShareMessengerGenericTemplateElement.Builder builder) {
        return builder.imageUrl;
    }

    static /* synthetic */ ShareMessengerActionButton access$300(ShareMessengerGenericTemplateElement.Builder builder) {
        return builder.defaultAction;
    }

    static /* synthetic */ ShareMessengerActionButton access$400(ShareMessengerGenericTemplateElement.Builder builder) {
        return builder.button;
    }

    @Override
    public ShareMessengerGenericTemplateElement build() {
        return new ShareMessengerGenericTemplateElement(this, null);
    }

    @Override
    ShareMessengerGenericTemplateElement.Builder readFrom(Parcel parcel) {
        return this.readFrom((ShareMessengerGenericTemplateElement)parcel.readParcelable(ShareMessengerGenericTemplateElement.class.getClassLoader()));
    }

    @Override
    public ShareMessengerGenericTemplateElement.Builder readFrom(ShareMessengerGenericTemplateElement shareMessengerGenericTemplateElement) {
        if (shareMessengerGenericTemplateElement == null) {
            return this;
        }
        return this.setTitle(shareMessengerGenericTemplateElement.title).setSubtitle(shareMessengerGenericTemplateElement.subtitle).setImageUrl(shareMessengerGenericTemplateElement.imageUrl).setDefaultAction(shareMessengerGenericTemplateElement.defaultAction).setButton(shareMessengerGenericTemplateElement.button);
    }

    public ShareMessengerGenericTemplateElement.Builder setButton(ShareMessengerActionButton shareMessengerActionButton) {
        this.button = shareMessengerActionButton;
        return this;
    }

    public ShareMessengerGenericTemplateElement.Builder setDefaultAction(ShareMessengerActionButton shareMessengerActionButton) {
        this.defaultAction = shareMessengerActionButton;
        return this;
    }

    public ShareMessengerGenericTemplateElement.Builder setImageUrl(Uri uri) {
        this.imageUrl = uri;
        return this;
    }

    public ShareMessengerGenericTemplateElement.Builder setSubtitle(String string) {
        this.subtitle = string;
        return this;
    }

    public ShareMessengerGenericTemplateElement.Builder setTitle(String string) {
        this.title = string;
        return this;
    }
}
