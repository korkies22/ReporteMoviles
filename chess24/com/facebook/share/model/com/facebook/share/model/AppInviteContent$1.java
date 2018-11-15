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

static final class AppInviteContent
implements Parcelable.Creator<com.facebook.share.model.AppInviteContent> {
    AppInviteContent() {
    }

    public com.facebook.share.model.AppInviteContent createFromParcel(Parcel parcel) {
        return new com.facebook.share.model.AppInviteContent(parcel);
    }

    public com.facebook.share.model.AppInviteContent[] newArray(int n) {
        return new com.facebook.share.model.AppInviteContent[n];
    }
}
