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
import android.support.v4.widget.SlidingPaneLayout;

static final class SlidingPaneLayout.SavedState
implements Parcelable.ClassLoaderCreator<SlidingPaneLayout.SavedState> {
    SlidingPaneLayout.SavedState() {
    }

    public SlidingPaneLayout.SavedState createFromParcel(Parcel parcel) {
        return new SlidingPaneLayout.SavedState(parcel, null);
    }

    public SlidingPaneLayout.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return new SlidingPaneLayout.SavedState(parcel, null);
    }

    public SlidingPaneLayout.SavedState[] newArray(int n) {
        return new SlidingPaneLayout.SavedState[n];
    }
}
