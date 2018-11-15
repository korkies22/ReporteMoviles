/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.ShareOpenGraphValueContainer;

public static final class ShareOpenGraphObject.Builder
extends ShareOpenGraphValueContainer.Builder<ShareOpenGraphObject, ShareOpenGraphObject.Builder> {
    public ShareOpenGraphObject.Builder() {
        this.putBoolean("fbsdk:create_object", true);
    }

    @Override
    public ShareOpenGraphObject build() {
        return new ShareOpenGraphObject(this, null);
    }

    @Override
    ShareOpenGraphObject.Builder readFrom(Parcel parcel) {
        return (ShareOpenGraphObject.Builder)this.readFrom((ShareOpenGraphObject)parcel.readParcelable(ShareOpenGraphObject.class.getClassLoader()));
    }
}
