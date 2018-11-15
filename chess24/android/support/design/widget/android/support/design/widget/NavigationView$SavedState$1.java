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
import android.support.design.widget.NavigationView;

static final class NavigationView.SavedState
implements Parcelable.ClassLoaderCreator<NavigationView.SavedState> {
    NavigationView.SavedState() {
    }

    public NavigationView.SavedState createFromParcel(Parcel parcel) {
        return new NavigationView.SavedState(parcel, null);
    }

    public NavigationView.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return new NavigationView.SavedState(parcel, classLoader);
    }

    public NavigationView.SavedState[] newArray(int n) {
        return new NavigationView.SavedState[n];
    }
}
