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
import android.support.design.widget.BottomNavigationView;

static final class BottomNavigationView.SavedState
implements Parcelable.ClassLoaderCreator<BottomNavigationView.SavedState> {
    BottomNavigationView.SavedState() {
    }

    public BottomNavigationView.SavedState createFromParcel(Parcel parcel) {
        return new BottomNavigationView.SavedState(parcel, null);
    }

    public BottomNavigationView.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return new BottomNavigationView.SavedState(parcel, classLoader);
    }

    public BottomNavigationView.SavedState[] newArray(int n) {
        return new BottomNavigationView.SavedState[n];
    }
}
