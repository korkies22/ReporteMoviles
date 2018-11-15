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
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphValueContainer;

public static final class ShareOpenGraphAction.Builder
extends ShareOpenGraphValueContainer.Builder<ShareOpenGraphAction, ShareOpenGraphAction.Builder> {
    private static final String ACTION_TYPE_KEY = "og:type";

    @Override
    public ShareOpenGraphAction build() {
        return new ShareOpenGraphAction(this, null);
    }

    @Override
    ShareOpenGraphAction.Builder readFrom(Parcel parcel) {
        return this.readFrom((ShareOpenGraphAction)parcel.readParcelable(ShareOpenGraphAction.class.getClassLoader()));
    }

    @Override
    public ShareOpenGraphAction.Builder readFrom(ShareOpenGraphAction shareOpenGraphAction) {
        if (shareOpenGraphAction == null) {
            return this;
        }
        return ((ShareOpenGraphAction.Builder)super.readFrom(shareOpenGraphAction)).setActionType(shareOpenGraphAction.getActionType());
    }

    public ShareOpenGraphAction.Builder setActionType(String string) {
        this.putString(ACTION_TYPE_KEY, string);
        return this;
    }
}
