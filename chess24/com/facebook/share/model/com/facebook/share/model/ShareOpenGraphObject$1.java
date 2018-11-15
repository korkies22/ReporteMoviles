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

static final class ShareOpenGraphObject
implements Parcelable.Creator<com.facebook.share.model.ShareOpenGraphObject> {
    ShareOpenGraphObject() {
    }

    public com.facebook.share.model.ShareOpenGraphObject createFromParcel(Parcel parcel) {
        return new com.facebook.share.model.ShareOpenGraphObject(parcel);
    }

    public com.facebook.share.model.ShareOpenGraphObject[] newArray(int n) {
        return new com.facebook.share.model.ShareOpenGraphObject[n];
    }
}
