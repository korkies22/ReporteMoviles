/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package com.facebook.share.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.model.CameraEffectArguments;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public static class CameraEffectArguments.Builder
implements ShareModelBuilder<CameraEffectArguments, CameraEffectArguments.Builder> {
    private Bundle params = new Bundle();

    static /* synthetic */ Bundle access$000(CameraEffectArguments.Builder builder) {
        return builder.params;
    }

    @Override
    public CameraEffectArguments build() {
        return new CameraEffectArguments(this, null);
    }

    public CameraEffectArguments.Builder putArgument(String string, String string2) {
        this.params.putString(string, string2);
        return this;
    }

    public CameraEffectArguments.Builder putArgument(String string, String[] arrstring) {
        this.params.putStringArray(string, arrstring);
        return this;
    }

    @Override
    public CameraEffectArguments.Builder readFrom(Parcel parcel) {
        return this.readFrom((CameraEffectArguments)parcel.readParcelable(CameraEffectArguments.class.getClassLoader()));
    }

    @Override
    public CameraEffectArguments.Builder readFrom(CameraEffectArguments cameraEffectArguments) {
        if (cameraEffectArguments != null) {
            this.params.putAll(cameraEffectArguments.params);
        }
        return this;
    }
}
