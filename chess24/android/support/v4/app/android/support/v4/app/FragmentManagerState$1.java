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

static final class FragmentManagerState
implements Parcelable.Creator<android.support.v4.app.FragmentManagerState> {
    FragmentManagerState() {
    }

    public android.support.v4.app.FragmentManagerState createFromParcel(Parcel parcel) {
        return new android.support.v4.app.FragmentManagerState(parcel);
    }

    public android.support.v4.app.FragmentManagerState[] newArray(int n) {
        return new android.support.v4.app.FragmentManagerState[n];
    }
}
