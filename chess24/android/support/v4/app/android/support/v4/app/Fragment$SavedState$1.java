/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

static final class Fragment.SavedState
implements Parcelable.Creator<Fragment.SavedState> {
    Fragment.SavedState() {
    }

    public Fragment.SavedState createFromParcel(Parcel parcel) {
        return new Fragment.SavedState(parcel, null);
    }

    public Fragment.SavedState[] newArray(int n) {
        return new Fragment.SavedState[n];
    }
}
