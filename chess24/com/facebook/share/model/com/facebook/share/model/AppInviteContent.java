/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

@Deprecated
public final class AppInviteContent
implements ShareModel {
    @Deprecated
    public static final Parcelable.Creator<AppInviteContent> CREATOR = new Parcelable.Creator<AppInviteContent>(){

        public AppInviteContent createFromParcel(Parcel parcel) {
            return new AppInviteContent(parcel);
        }

        public AppInviteContent[] newArray(int n) {
            return new AppInviteContent[n];
        }
    };
    private final String applinkUrl;
    private final Builder.Destination destination;
    private final String previewImageUrl;
    private final String promoCode;
    private final String promoText;

    @Deprecated
    AppInviteContent(Parcel object) {
        this.applinkUrl = object.readString();
        this.previewImageUrl = object.readString();
        this.promoText = object.readString();
        this.promoCode = object.readString();
        object = object.readString();
        if (object.length() > 0) {
            this.destination = Builder.Destination.valueOf((String)object);
            return;
        }
        this.destination = Builder.Destination.FACEBOOK;
    }

    private AppInviteContent(Builder builder) {
        this.applinkUrl = builder.applinkUrl;
        this.previewImageUrl = builder.previewImageUrl;
        this.promoCode = builder.promoCode;
        this.promoText = builder.promoText;
        this.destination = builder.destination;
    }

    @Deprecated
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public String getApplinkUrl() {
        return this.applinkUrl;
    }

    @Deprecated
    public Builder.Destination getDestination() {
        if (this.destination != null) {
            return this.destination;
        }
        return Builder.Destination.FACEBOOK;
    }

    @Deprecated
    public String getPreviewImageUrl() {
        return this.previewImageUrl;
    }

    @Deprecated
    public String getPromotionCode() {
        return this.promoCode;
    }

    @Deprecated
    public String getPromotionText() {
        return this.promoText;
    }

    @Deprecated
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.applinkUrl);
        parcel.writeString(this.previewImageUrl);
        parcel.writeString(this.promoText);
        parcel.writeString(this.promoCode);
        parcel.writeString(this.destination.toString());
    }

    @Deprecated
    public static class Builder
    implements ShareModelBuilder<AppInviteContent, Builder> {
        private String applinkUrl;
        private Destination destination;
        private String previewImageUrl;
        private String promoCode;
        private String promoText;

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
            return new AppInviteContent(this);
        }

        @Deprecated
        @Override
        public Builder readFrom(AppInviteContent appInviteContent) {
            if (appInviteContent == null) {
                return this;
            }
            return this.setApplinkUrl(appInviteContent.getApplinkUrl()).setPreviewImageUrl(appInviteContent.getPreviewImageUrl()).setPromotionDetails(appInviteContent.getPromotionText(), appInviteContent.getPromotionCode()).setDestination(appInviteContent.getDestination());
        }

        @Deprecated
        public Builder setApplinkUrl(String string) {
            this.applinkUrl = string;
            return this;
        }

        @Deprecated
        public Builder setDestination(Destination destination) {
            this.destination = destination;
            return this;
        }

        @Deprecated
        public Builder setPreviewImageUrl(String string) {
            this.previewImageUrl = string;
            return this;
        }

        @Deprecated
        public Builder setPromotionDetails(String string, String string2) {
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

}
