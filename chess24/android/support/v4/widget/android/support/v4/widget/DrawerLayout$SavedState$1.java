/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 */
package android.support.v4.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.widget.DrawerLayout;

static final class DrawerLayout.SavedState
implements Parcelable.ClassLoaderCreator<DrawerLayout.SavedState> {
    DrawerLayout.SavedState() {
    }

    public DrawerLayout.SavedState createFromParcel(Parcel parcel) {
        return new DrawerLayout.SavedState(parcel, null);
    }

    public DrawerLayout.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return new DrawerLayout.SavedState(parcel, classLoader);
    }

    public DrawerLayout.SavedState[] newArray(int n) {
        return new DrawerLayout.SavedState[n];
    }
}
