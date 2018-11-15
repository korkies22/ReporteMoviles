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
import android.support.v4.view.ViewPager;

static final class ViewPager.SavedState
implements Parcelable.ClassLoaderCreator<ViewPager.SavedState> {
    ViewPager.SavedState() {
    }

    public ViewPager.SavedState createFromParcel(Parcel parcel) {
        return new ViewPager.SavedState(parcel, null);
    }

    public ViewPager.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return new ViewPager.SavedState(parcel, classLoader);
    }

    public ViewPager.SavedState[] newArray(int n) {
        return new ViewPager.SavedState[n];
    }
}
