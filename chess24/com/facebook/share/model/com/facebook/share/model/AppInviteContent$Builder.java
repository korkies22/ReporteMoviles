/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.facebook.share.model;

import android.text.TextUtils;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

@Deprecated
public static class AppInviteContent.Builder
implements ShareModelBuilder<AppInviteContent, AppInviteContent.Builder> {
    private String applinkUrl;
    private Destination destination;
    private String previewImageUrl;
    private String promoCode;
    private String promoText;

    static /* synthetic */ String access$000(AppInviteContent.Builder builder) {
        return builder.applinkUrl;
    }

    static /* synthetic */ String access$100(AppInviteContent.Builder builder) {
        return builder.previewImageUrl;
    }

    static /* synthetic */ String access$200(AppInviteContent.Builder builder) {
        return builder.promoCode;
    }

    static /* synthetic */ String access$300(AppInviteContent.Builder builder) {
        return builder.promoText;
    }

    static /* synthetic */ Destination access$400(AppInviteContent.Builder builder) {
        return builder.destination;
    }

    private boolean isAlphanumericWithSpaces(String string) {
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (Character.isDigit(c) || Character.isLetter(c) || Character.isSpaceChar(c)) continue;
            return false;
        }
        return true;
    }

    @Deprecated
    @Override
    public AppInviteContent build() {
        return new AppInviteContent(this, null);
    }

    @Deprecated
    @Override
    public AppInviteContent.Builder readFrom(AppInviteContent appInviteContent) {
        if (appInviteContent == null) {
            return this;
        }
        return this.setApplinkUrl(appInviteContent.getApplinkUrl()).setPreviewImageUrl(appInviteContent.getPreviewImageUrl()).setPromotionDetails(appInviteContent.getPromotionText(), appInviteContent.getPromotionCode()).setDestination(appInviteContent.getDestination());
    }

    @Deprecated
    public AppInviteContent.Builder setApplinkUrl(String string) {
        this.applinkUrl = string;
        return this;
    }

    @Deprecated
    public AppInviteContent.Builder setDestination(Destination destination) {
        this.destination = destination;
        return this;
    }

    @Deprecated
    public AppInviteContent.Builder setPreviewImageUrl(String string) {
        this.previewImageUrl = string;
        return this;
    }

    @Deprecated
    public AppInviteContent.Builder setPromotionDetails(String string, String string2) {
        if (!TextUtils.isEmpty((CharSequence)string)) {
            if (string.length() > 80) {
                throw new IllegalArgumentException("Invalid promotion text, promotionText needs to be between1 and 80 characters long");
            }
            if (!this.isAlphanumericWithSpaces(string)) {
                throw new IllegalArgumentException("Invalid promotion text, promotionText can only contain alphanumericcharacters and spaces.");
            }
            if (!TextUtils.isEmpty((CharSequence)string2)) {
                if (string2.length() > 10) {
                    throw new IllegalArgumentException("Invalid promotion code, promotionCode can be between1 and 10 characters long");
                }
                if (!this.isAlphanumericWithSpaces(string2)) {
                    throw new IllegalArgumentException("Invalid promotion code, promotionCode can only contain alphanumeric characters and spaces.");
                }
            }
        } else if (!TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("promotionCode cannot be specified without a valid promotionText");
        }
        this.promoCode = string2;
        this.promoText = string;
        return this;
    }

    @Deprecated
    public static enum Destination {
        FACEBOOK("facebook"),
        MESSENGER("messenger");
        
        private final String name;

        private Destination(String string2) {
            this.name = string2;
        }

        public boolean equalsName(String string) {
            if (string == null) {
                return false;
            }
            return this.name.equals(string);
        }

        public String toString() {
            return this.name;
        }
    }

}
