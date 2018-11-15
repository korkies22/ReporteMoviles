/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.v4.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.widget.NestedScrollView;

static final class NestedScrollView.SavedState
implements Parcelable.Creator<NestedScrollView.SavedState> {
    NestedScrollView.SavedState() {
    }

    public NestedScrollView.SavedState createFromParcel(Parcel parcel) {
        return new NestedScrollView.SavedState(parcel);
    }

    public NestedScrollView.SavedState[] newArray(int n) {
        return new NestedScrollView.SavedState[n];
    }
}
