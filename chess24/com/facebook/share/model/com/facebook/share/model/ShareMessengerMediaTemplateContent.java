/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareMessengerActionButton;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import java.io.Serializable;

public final class ShareMessengerMediaTemplateContent
extends ShareContent<ShareMessengerMediaTemplateContent, Builder> {
    public static final Parcelable.Creator<ShareMessengerMediaTemplateContent> CREATOR = new Parcelable.Creator<ShareMessengerMediaTemplateContent>(){

        public ShareMessengerMediaTemplateContent createFromParcel(Parcel parcel) {
            return new ShareMessengerMediaTemplateContent(parcel);
        }

        public ShareMessengerMediaTemplateContent[] newArray(int n) {
            return new ShareMessengerMediaTemplateContent[n];
        }
    };
    private final String attachmentId;
    private final ShareMessengerActionButton button;
    private final MediaType mediaType;
    private final Uri mediaUrl;

    ShareMessengerMediaTemplateContent(Parcel parcel) {
        super(parcel);
        this.mediaType = (MediaType)((Object)parcel.readSerializable());
        this.attachmentId = parcel.readString();
        this.mediaUrl = (Uri)parcel.readParcelable(Uri.class.getClassLoader());
        this.button = (ShareMessengerActionButton)parcel.readParcelable(ShareMessengerActionButton.class.getClassLoader());
    }

    private ShareMessengerMediaTemplateContent(Builder builder) {
        super(builder);
        this.mediaType = builder.mediaType;
        this.attachmentId = builder.attachmentId;
        this.mediaUrl = builder.mediaUrl;
        this.button = builder.button;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getAttachmentId() {
        return this.attachmentId;
    }

    public ShareMessengerActionButton getButton() {
        return this.button;
    }

    public MediaType getMediaType() {
        return this.mediaType;
    }

    public Uri getMediaUrl() {
        return this.mediaUrl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeSerializable((Serializable)((Object)this.mediaType));
        parcel.writeString(this.attachmentId);
        parcel.writeParcelable((Parcelable)this.mediaUrl, n);
        parcel.writeParcelable((Parcelable)this.button, n);
    }

    public static class Builder
    extends ShareContent.Builder<ShareMessengerMediaTemplateContent, Builder> {
        private String attachmentId;
        private ShareMessengerActionButton button;
        private MediaType mediaType;
        private Uri mediaUrl;

        @Override
        public ShareMessengerMediaTemplateContent build() {
            return new ShareMessengerMediaTemplateContent(this);
        }

        @Override
        public Builder readFrom(ShareMessengerMediaTemplateContent shareMessengerMediaTemplateContent) {
            if (shareMessengerMediaTemplateContent == null) {
                return this;
            }
            return ((Builder)super.readFrom(shareMessengerMediaTemplateContent)).setMediaType(shareMessengerMediaTemplateContent.getMediaType()).setAttachmentId(shareMessengerMediaTemplateContent.getAttachmentId()).setMediaUrl(shareMessengerMediaTemplateContent.getMediaUrl()).setButton(shareMessengerMediaTemplateContent.getButton());
        }

        public Builder setAttachmentId(String string) {
            this.attachmentId = string;
            return this;
        }

        public Builder setButton(ShareMessengerActionButton shareMessengerActionButton) {
            this.button = shareMessengerActionButton;
            return this;
        }

        public Builder setMediaType(MediaType mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        public Builder setMediaUrl(Uri uri) {
            this.mediaUrl = uri;
            return this;
        }
    }

    public static enum MediaType {
        IMAGE,
        VIDEO;
        

        private MediaType() {
        }
    }

}
