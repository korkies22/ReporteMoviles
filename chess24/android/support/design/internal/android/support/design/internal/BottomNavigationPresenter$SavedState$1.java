/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.design.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.internal.BottomNavigationPresenter;

static final class BottomNavigationPresenter.SavedState
implements Parcelable.Creator<BottomNavigationPresenter.SavedState> {
    BottomNavigationPresenter.SavedState() {
    }

    public BottomNavigationPresenter.SavedState createFromParcel(Parcel parcel) {
        return new BottomNavigationPresenter.SavedState(parcel);
    }

    public BottomNavigationPresenter.SavedState[] newArray(int n) {
        return new BottomNavigationPresenter.SavedState[n];
    }
}
