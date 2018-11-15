/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.facebook.share.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import java.util.Set;

public class CameraEffectArguments
implements ShareModel {
    public static final Parcelable.Creator<CameraEffectArguments> CREATOR = new Parcelable.Creator<CameraEffectArguments>(){

        public CameraEffectArguments createFromParcel(Parcel parcel) {
            return new CameraEffectArguments(parcel);
        }

        public CameraEffectArguments[] newArray(int n) {
            return new CameraEffectArguments[n];
        }
    };
    private final Bundle params;

    CameraEffectArguments(Parcel parcel) {
        this.params = parcel.readBundle(this.getClass().getClassLoader());
    }

    private CameraEffectArguments(Builder builder) {
        this.params = builder.params;
    }

    public int describeContents() {
        return 0;
    }

    @Nullable
    public Object get(String string) {
        return this.params.get(string);
    }

    @Nullable
    public String getString(String string) {
        return this.params.getString(string);
    }

    @Nullable
    public String[] getStringArray(String string) {
        return this.params.getStringArray(string);
    }

    public Set<String> keySet() {
        return this.params.keySet();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBundle(this.params);
    }

    public static class Builder
    implements ShareModelBuilder<CameraEffectArguments, Builder> {
        private Bundle params = new Bundle();

        @Override
        public CameraEffectArguments build() {
            return new CameraEffectArguments(this);
        }

        public Builder putArgument(String string, String string2) {
            this.params.putString(string, string2);
            return this;
        }

        public Builder putArgument(String string, String[] arrstring) {
            this.params.putStringArray(string, arrstring);
            return this;
        }

        @Override
        public Builder readFrom(Parcel parcel) {
            return this.readFrom((CameraEffectArguments)parcel.readParcelable(CameraEffectArguments.class.getClassLoader()));
        }

        @Override
        public Builder readFrom(CameraEffectArguments cameraEffectArguments) {
            if (cameraEffectArguments != null) {
                this.params.putAll(cameraEffectArguments.params);
            }
            return this;
        }
    }

}
