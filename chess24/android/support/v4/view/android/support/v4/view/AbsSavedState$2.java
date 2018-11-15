/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 */
package android.support.v4.view;

import android.os.Parcel;
import android.os.Parcelable;

static final class AbsSavedState
implements Parcelable.ClassLoaderCreator<android.support.v4.view.AbsSavedState> {
    AbsSavedState() {
    }

    public android.support.v4.view.AbsSavedState createFromParcel(Parcel parcel) {
        return this.createFromParcel(parcel, null);
    }

    public android.support.v4.view.AbsSavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        if (parcel.readParcelable(classLoader) != null) {
            throw new IllegalStateException("superState must be null");
        }
        return android.support.v4.view.AbsSavedState.EMPTY_STATE;
    }

    public android.support.v4.view.AbsSavedState[] newArray(int n) {
        return new android.support.v4.view.AbsSavedState[n];
    }
}
