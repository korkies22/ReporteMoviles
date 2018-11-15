/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.facebook.share.internal;

import android.os.Parcel;
import android.os.Parcelable;

static final class LikeContent
implements Parcelable.Creator<com.facebook.share.internal.LikeContent> {
    LikeContent() {
    }

    public com.facebook.share.internal.LikeContent createFromParcel(Parcel parcel) {
        return new com.facebook.share.internal.LikeContent(parcel);
    }

    public com.facebook.share.internal.LikeContent[] newArray(int n) {
        return new com.facebook.share.internal.LikeContent[n];
    }
}
