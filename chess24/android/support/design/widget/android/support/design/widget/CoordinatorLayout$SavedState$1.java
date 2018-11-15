/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 */
package android.support.design.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;

static final class CoordinatorLayout.SavedState
implements Parcelable.ClassLoaderCreator<CoordinatorLayout.SavedState> {
    CoordinatorLayout.SavedState() {
    }

    public CoordinatorLayout.SavedState createFromParcel(Parcel parcel) {
        return new CoordinatorLayout.SavedState(parcel, null);
    }

    public CoordinatorLayout.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return new CoordinatorLayout.SavedState(parcel, classLoader);
    }

    public CoordinatorLayout.SavedState[] newArray(int n) {
        return new CoordinatorLayout.SavedState[n];
    }
}
