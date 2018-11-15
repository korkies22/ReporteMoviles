/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.facebook.share.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.internal.Utility;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import java.util.Set;

public class CameraEffectTextures
implements ShareModel {
    public static final Parcelable.Creator<CameraEffectTextures> CREATOR = new Parcelable.Creator<CameraEffectTextures>(){

        public CameraEffectTextures createFromParcel(Parcel parcel) {
            return new CameraEffectTextures(parcel);
        }

        public CameraEffectTextures[] newArray(int n) {
            return new CameraEffectTextures[n];
        }
    };
    private final Bundle textures;

    CameraEffectTextures(Parcel parcel) {
        this.textures = parcel.readBundle(this.getClass().getClassLoader());
    }

    private CameraEffectTextures(Builder builder) {
        this.textures = builder.textures;
    }

    public int describeContents() {
        return 0;
    }

    @Nullable
    public Object get(String string) {
        return this.textures.get(string);
    }

    @Nullable
    public Bitmap getTextureBitmap(String object) {
        if ((object = this.textures.get((String)object)) instanceof Bitmap) {
            return (Bitmap)object;
        }
        return null;
    }

    @Nullable
    public Uri getTextureUri(String object) {
        if ((object = this.textures.get((String)object)) instanceof Uri) {
            return (Uri)object;
        }
        return null;
    }

    public Set<String> keySet() {
        return this.textures.keySet();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBundle(this.textures);
    }

    public static class Builder
    implements ShareModelBuilder<CameraEffectTextures, Builder> {
        private Bundle textures = new Bundle();

        private Builder putParcelableTexture(String string, Parcelable parcelable) {
            if (!Utility.isNullOrEmpty(string) && parcelable != null) {
                this.textures.putParcelable(string, parcelable);
            }
            return this;
        }

        @Override
        public CameraEffectTextures build() {
            return new CameraEffectTextures(this);
        }

        public Builder putTexture(String string, Bitmap bitmap) {
            return this.putParcelableTexture(string, (Parcelable)bitmap);
        }

        public Builder putTexture(String string, Uri uri) {
            return this.putParcelableTexture(string, (Parcelable)uri);
        }

        @Override
        public Builder readFrom(Parcel parcel) {
            return this.readFrom((CameraEffectTextures)parcel.readParcelable(CameraEffectTextures.class.getClassLoader()));
        }

        @Override
        public Builder readFrom(CameraEffectTextures cameraEffectTextures) {
            if (cameraEffectTextures != null) {
                this.textures.putAll(cameraEffectTextures.textures);
            }
            return this;
        }
    }

}
