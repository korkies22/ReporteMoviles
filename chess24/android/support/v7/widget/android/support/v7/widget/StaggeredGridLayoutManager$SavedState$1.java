/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.v7.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.StaggeredGridLayoutManager;

static final class StaggeredGridLayoutManager.SavedState
implements Parcelable.Creator<StaggeredGridLayoutManager.SavedState> {
    StaggeredGridLayoutManager.SavedState() {
    }

    public StaggeredGridLayoutManager.SavedState createFromParcel(Parcel parcel) {
        return new StaggeredGridLayoutManager.SavedState(parcel);
    }

    public StaggeredGridLayoutManager.SavedState[] newArray(int n) {
        return new StaggeredGridLayoutManager.SavedState[n];
    }
}
