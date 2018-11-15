/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.model;

import com.facebook.share.model.AppGroupCreationContent;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public static class AppGroupCreationContent.Builder
implements ShareModelBuilder<AppGroupCreationContent, AppGroupCreationContent.Builder> {
    private String description;
    private String name;
    private AppGroupCreationContent.AppGroupPrivacy privacy;

    static /* synthetic */ String access$000(AppGroupCreationContent.Builder builder) {
        return builder.name;
    }

    static /* synthetic */ String access$100(AppGroupCreationContent.Builder builder) {
        return builder.description;
    }

    static /* synthetic */ AppGroupCreationContent.AppGroupPrivacy access$200(AppGroupCreationContent.Builder builder) {
        return builder.privacy;
    }

    @Override
    public AppGroupCreationContent build() {
        return new AppGroupCreationContent(this, null);
    }

    @Override
    public AppGroupCreationContent.Builder readFrom(AppGroupCreationContent appGroupCreationContent) {
        if (appGroupCreationContent == null) {
            return this;
        }
        return this.setName(appGroupCreationContent.getName()).setDescription(appGroupCreationContent.getDescription()).setAppGroupPrivacy(appGroupCreationContent.getAppGroupPrivacy());
    }

    public AppGroupCreationContent.Builder setAppGroupPrivacy(AppGroupCreationContent.AppGroupPrivacy appGroupPrivacy) {
        this.privacy = appGroupPrivacy;
        return this;
    }

    public AppGroupCreationContent.Builder setDescription(String string) {
        this.description = string;
        return this;
    }

    public AppGroupCreationContent.Builder setName(String string) {
        this.name = string;
        return this;
    }
}
