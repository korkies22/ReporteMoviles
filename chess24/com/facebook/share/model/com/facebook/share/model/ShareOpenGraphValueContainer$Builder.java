/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.facebook.share.model;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.ShareOpenGraphValueContainer;
import com.facebook.share.model.SharePhoto;
import java.util.ArrayList;

public static abstract class ShareOpenGraphValueContainer.Builder<P extends ShareOpenGraphValueContainer, E extends ShareOpenGraphValueContainer.Builder>
implements ShareModelBuilder<P, E> {
    private Bundle bundle = new Bundle();

    static /* synthetic */ Bundle access$000(ShareOpenGraphValueContainer.Builder builder) {
        return builder.bundle;
    }

    public E putBoolean(String string, boolean bl) {
        this.bundle.putBoolean(string, bl);
        return (E)this;
    }

    public E putBooleanArray(String string, @Nullable boolean[] arrbl) {
        this.bundle.putBooleanArray(string, arrbl);
        return (E)this;
    }

    public E putDouble(String string, double d) {
        this.bundle.putDouble(string, d);
        return (E)this;
    }

    public E putDoubleArray(String string, @Nullable double[] arrd) {
        this.bundle.putDoubleArray(string, arrd);
        return (E)this;
    }

    public E putInt(String string, int n) {
        this.bundle.putInt(string, n);
        return (E)this;
    }

    public E putIntArray(String string, @Nullable int[] arrn) {
        this.bundle.putIntArray(string, arrn);
        return (E)this;
    }

    public E putLong(String string, long l) {
        this.bundle.putLong(string, l);
        return (E)this;
    }

    public E putLongArray(String string, @Nullable long[] arrl) {
        this.bundle.putLongArray(string, arrl);
        return (E)this;
    }

    public E putObject(String string, @Nullable ShareOpenGraphObject shareOpenGraphObject) {
        this.bundle.putParcelable(string, (Parcelable)shareOpenGraphObject);
        return (E)this;
    }

    public E putObjectArrayList(String string, @Nullable ArrayList<ShareOpenGraphObject> arrayList) {
        this.bundle.putParcelableArrayList(string, arrayList);
        return (E)this;
    }

    public E putPhoto(String string, @Nullable SharePhoto sharePhoto) {
        this.bundle.putParcelable(string, (Parcelable)sharePhoto);
        return (E)this;
    }

    public E putPhotoArrayList(String string, @Nullable ArrayList<SharePhoto> arrayList) {
        this.bundle.putParcelableArrayList(string, arrayList);
        return (E)this;
    }

    public E putString(String string, @Nullable String string2) {
        this.bundle.putString(string, string2);
        return (E)this;
    }

    public E putStringArrayList(String string, @Nullable ArrayList<String> arrayList) {
        this.bundle.putStringArrayList(string, arrayList);
        return (E)this;
    }

    @Override
    public E readFrom(P p) {
        if (p != null) {
            this.bundle.putAll(p.getBundle());
        }
        return (E)this;
    }
}
