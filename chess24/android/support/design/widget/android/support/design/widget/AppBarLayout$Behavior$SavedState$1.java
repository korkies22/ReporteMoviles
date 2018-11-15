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
import android.support.design.widget.AppBarLayout;

static final class AppBarLayout.Behavior.SavedState
implements Parcelable.ClassLoaderCreator<AppBarLayout.Behavior.SavedState> {
    AppBarLayout.Behavior.SavedState() {
    }

    public AppBarLayout.Behavior.SavedState createFromParcel(Parcel parcel) {
        return new AppBarLayout.Behavior.SavedState(parcel, null);
    }

    public AppBarLayout.Behavior.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return new AppBarLayout.Behavior.SavedState(parcel, classLoader);
    }

    public AppBarLayout.Behavior.SavedState[] newArray(int n) {
        return new AppBarLayout.Behavior.SavedState[n];
    }
}
