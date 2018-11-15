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

static final class GameRequestContent
implements Parcelable.Creator<com.facebook.share.model.GameRequestContent> {
    GameRequestContent() {
    }

    public com.facebook.share.model.GameRequestContent createFromParcel(Parcel parcel) {
        return new com.facebook.share.model.GameRequestContent(parcel);
    }

    public com.facebook.share.model.GameRequestContent[] newArray(int n) {
        return new com.facebook.share.model.GameRequestContent[n];
    }
}
