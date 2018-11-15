/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 */
package android.support.design.internal;

import android.os.Parcel;
import android.os.Parcelable;

static final class ParcelableSparseArray
implements Parcelable.ClassLoaderCreator<android.support.design.internal.ParcelableSparseArray> {
    ParcelableSparseArray() {
    }

    public android.support.design.internal.ParcelableSparseArray createFromParcel(Parcel parcel) {
        return new android.support.design.internal.ParcelableSparseArray(parcel, null);
    }

    public android.support.design.internal.ParcelableSparseArray createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return new android.support.design.internal.ParcelableSparseArray(parcel, classLoader);
    }

    public android.support.design.internal.ParcelableSparseArray[] newArray(int n) {
        return new android.support.design.internal.ParcelableSparseArray[n];
    }
}
