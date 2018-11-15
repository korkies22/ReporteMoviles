/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.v13.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v13.app.FragmentTabHost;

static final class FragmentTabHost.SavedState
implements Parcelable.Creator<FragmentTabHost.SavedState> {
    FragmentTabHost.SavedState() {
    }

    public FragmentTabHost.SavedState createFromParcel(Parcel parcel) {
        return new FragmentTabHost.SavedState(parcel);
    }

    public FragmentTabHost.SavedState[] newArray(int n) {
        return new FragmentTabHost.SavedState[n];
    }
}
