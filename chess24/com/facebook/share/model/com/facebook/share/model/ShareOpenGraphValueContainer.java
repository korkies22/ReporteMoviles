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
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public abstract class ShareOpenGraphValueContainer<P extends ShareOpenGraphValueContainer, E extends Builder>
implements ShareModel {
    private final Bundle bundle;

    ShareOpenGraphValueContainer(Parcel parcel) {
        this.bundle = parcel.readBundle(Builder.class.getClassLoader());
    }

    protected ShareOpenGraphValueContainer(Builder<P, E> builder) {
        this.bundle = (Bundle)builder.bundle.clone();
    }

    public int describeContents() {
        return 0;
    }

    @Nullable
    public Object get(String string) {
        return this.bundle.get(string);
    }

    public boolean getBoolean(String string, boolean bl) {
        return this.bundle.getBoolean(string, bl);
    }

    @Nullable
    public boolean[] getBooleanArray(String string) {
        return this.bundle.getBooleanArray(string);
    }

    public Bundle getBundle() {
        return (Bundle)this.bundle.clone();
    }

    public double getDouble(String string, double d) {
        return this.bundle.getDouble(string, d);
    }

    @Nullable
    public double[] getDoubleArray(String string) {
        return this.bundle.getDoubleArray(string);
    }

    public int getInt(String string, int n) {
        return this.bundle.getInt(string, n);
    }

    @Nullable
    public int[] getIntArray(String string) {
        return this.bundle.getIntArray(string);
    }

    public long getLong(String string, long l) {
        return this.bundle.getLong(string, l);
    }

    @Nullable
    public long[] getLongArray(String string) {
        return this.bundle.getLongArray(string);
    }

    public ShareOpenGraphObject getObject(String object) {
        if ((object = this.bundle.get((String)object)) instanceof ShareOpenGraphObject) {
            return (ShareOpenGraphObject)object;
        }
        return null;
    }

    @Nullable
    public ArrayList<ShareOpenGraphObject> getObjectArrayList(String object) {
        Object object2 = this.bundle.getParcelableArrayList((String)object);
        if (object2 == null) {
            return null;
        }
        object = new ArrayList();
        object2 = object2.iterator();
        while (object2.hasNext()) {
            Parcelable parcelable = (Parcelable)object2.next();
            if (!(parcelable instanceof ShareOpenGraphObject)) continue;
            object.add((ShareOpenGraphObject)parcelable);
        }
        return object;
    }

    @Nullable
    public SharePhoto getPhoto(String string) {
        if ((string = this.bundle.getParcelable(string)) instanceof SharePhoto) {
            return (SharePhoto)((Object)string);
        }
        return null;
    }

    @Nullable
    public ArrayList<SharePhoto> getPhotoArrayList(String object) {
        Object object2 = this.bundle.getParcelableArrayList((String)object);
        if (object2 == null) {
            return null;
        }
        object = new ArrayList();
        object2 = object2.iterator();
        while (object2.hasNext()) {
            Parcelable parcelable = (Parcelable)object2.next();
            if (!(parcelable instanceof SharePhoto)) continue;
            object.add((SharePhoto)parcelable);
        }
        return object;
    }

    @Nullable
    public String getString(String string) {
        return this.bundle.getString(string);
    }

    @Nullable
    public ArrayList<String> getStringArrayList(String string) {
        return this.bundle.getStringArrayList(string);
    }

    public Set<String> keySet() {
        return this.bundle.keySet();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBundle(this.bundle);
    }

    public static abstract class Builder<P extends ShareOpenGraphValueContainer, E extends Builder>
    implements ShareModelBuilder<P, E> {
        private Bundle bundle = new Bundle();

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

}
