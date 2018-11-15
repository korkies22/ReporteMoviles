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

static final class ShareLinkContent
implements Parcelable.Creator<com.facebook.share.model.ShareLinkContent> {
    ShareLinkContent() {
    }

    public com.facebook.share.model.ShareLinkContent createFromParcel(Parcel parcel) {
        return new com.facebook.share.model.ShareLinkContent(parcel);
    }

    public com.facebook.share.model.ShareLinkContent[] newArray(int n) {
        return new com.facebook.share.model.ShareLinkContent[n];
    }
}
