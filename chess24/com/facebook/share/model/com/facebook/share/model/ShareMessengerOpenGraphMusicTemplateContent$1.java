/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;

static final class ShareMessengerOpenGraphMusicTemplateContent
implements Parcelable.Creator<com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent> {
    ShareMessengerOpenGraphMusicTemplateContent() {
    }

    public com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent createFromParcel(Parcel parcel) {
        return new com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent(parcel);
    }

    public com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent[] newArray(int n) {
        return new com.facebook.share.model.ShareMessengerOpenGraphMusicTemplateContent[n];
    }
}
