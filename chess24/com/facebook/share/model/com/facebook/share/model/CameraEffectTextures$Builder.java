/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package com.facebook.share.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.internal.Utility;
import com.facebook.share.model.CameraEffectTextures;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public static class CameraEffectTextures.Builder
implements ShareModelBuilder<CameraEffectTextures, CameraEffectTextures.Builder> {
    private Bundle textures = new Bundle();

    static /* synthetic */ Bundle access$000(CameraEffectTextures.Builder builder) {
        return builder.textures;
    }

    private CameraEffectTextures.Builder putParcelableTexture(String string, Parcelable parcelable) {
        if (!Utility.isNullOrEmpty(string) && parcelable != null) {
            this.textures.putParcelable(string, parcelable);
        }
        return this;
    }

    @Override
    public CameraEffectTextures build() {
        return new CameraEffectTextures(this, null);
    }

    public CameraEffectTextures.Builder putTexture(String string, Bitmap bitmap) {
        return this.putParcelableTexture(string, (Parcelable)bitmap);
    }

    public CameraEffectTextures.Builder putTexture(String string, Uri uri) {
        return this.putParcelableTexture(string, (Parcelable)uri);
    }

    @Override
    public CameraEffectTextures.Builder readFrom(Parcel parcel) {
        return this.readFrom((CameraEffectTextures)parcel.readParcelable(CameraEffectTextures.class.getClassLoader()));
    }

    @Override
    public CameraEffectTextures.Builder readFrom(CameraEffectTextures cameraEffectTextures) {
        if (cameraEffectTextures != null) {
            this.textures.putAll(cameraEffectTextures.textures);
        }
        return this;
    }
}
