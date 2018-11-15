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

static final class ShareMessengerGenericTemplateElement
implements Parcelable.Creator<com.facebook.share.model.ShareMessengerGenericTemplateElement> {
    ShareMessengerGenericTemplateElement() {
    }

    public com.facebook.share.model.ShareMessengerGenericTemplateElement createFromParcel(Parcel parcel) {
        return new com.facebook.share.model.ShareMessengerGenericTemplateElement(parcel);
    }

    public com.facebook.share.model.ShareMessengerGenericTemplateElement[] newArray(int n) {
        return new com.facebook.share.model.ShareMessengerGenericTemplateElement[n];
    }
}
