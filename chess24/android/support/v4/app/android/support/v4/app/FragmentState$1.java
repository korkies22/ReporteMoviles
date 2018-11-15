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

static final class FragmentState
implements Parcelable.Creator<android.support.v4.app.FragmentState> {
    FragmentState() {
    }

    public android.support.v4.app.FragmentState createFromParcel(Parcel parcel) {
        return new android.support.v4.app.FragmentState(parcel);
    }

    public android.support.v4.app.FragmentState[] newArray(int n) {
        return new android.support.v4.app.FragmentState[n];
    }
}
