/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.facebook.share.model;

import android.os.Parcel;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public abstract class ShareMessengerActionButton
implements ShareModel {
    private final String title;

    ShareMessengerActionButton(Parcel parcel) {
        this.title = parcel.readString();
    }

    protected ShareMessengerActionButton(Builder builder) {
        this.title = builder.title;
    }

    public int describeContents() {
        return 0;
    }

    public String getTitle() {
        return this.title;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.title);
    }

    public static abstract class Builder<M extends ShareMessengerActionButton, B extends Builder>
    implements ShareModelBuilder<M, B> {
        private String title;

        @Override
        public B readFrom(M m) {
            if (m == null) {
                return (B)this;
            }
            return this.setTitle(m.getTitle());
        }

        public B setTitle(@Nullable String string) {
            this.title = string;
            return (B)this;
        }
    }

}
