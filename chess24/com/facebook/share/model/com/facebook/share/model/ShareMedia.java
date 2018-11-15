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
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import java.util.ArrayList;
import java.util.List;

public abstract class ShareMedia
implements ShareModel {
    private final Bundle params;

    ShareMedia(Parcel parcel) {
        this.params = parcel.readBundle();
    }

    protected ShareMedia(Builder builder) {
        this.params = new Bundle(builder.params);
    }

    public int describeContents() {
        return 0;
    }

    public abstract Type getMediaType();

    @Deprecated
    public Bundle getParameters() {
        return new Bundle(this.params);
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBundle(this.params);
    }

    public static abstract class Builder<M extends ShareMedia, B extends Builder>
    implements ShareModelBuilder<M, B> {
        private Bundle params = new Bundle();

        static List<ShareMedia> readListFrom(Parcel arrparcelable) {
            arrparcelable = arrparcelable.readParcelableArray(ShareMedia.class.getClassLoader());
            ArrayList<ShareMedia> arrayList = new ArrayList<ShareMedia>(arrparcelable.length);
            int n = arrparcelable.length;
            for (int i = 0; i < n; ++i) {
                arrayList.add((ShareMedia)arrparcelable[i]);
            }
            return arrayList;
        }

        static void writeListTo(Parcel parcel, int n, List<ShareMedia> list) {
            parcel.writeParcelableArray((Parcelable[])((ShareMedia[])list.toArray()), n);
        }

        @Override
        public B readFrom(M m) {
            if (m == null) {
                return (B)this;
            }
            return this.setParameters(m.getParameters());
        }

        @Deprecated
        public B setParameter(String string, String string2) {
            this.params.putString(string, string2);
            return (B)this;
        }

        @Deprecated
        public B setParameters(Bundle bundle) {
            this.params.putAll(bundle);
            return (B)this;
        }
    }

    public static enum Type {
        PHOTO,
        VIDEO;
        

        private Type() {
        }
    }

}
