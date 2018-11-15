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

static final class BackStackState
implements Parcelable.Creator<android.support.v4.app.BackStackState> {
    BackStackState() {
    }

    public android.support.v4.app.BackStackState createFromParcel(Parcel parcel) {
        return new android.support.v4.app.BackStackState(parcel);
    }

    public android.support.v4.app.BackStackState[] newArray(int n) {
        return new android.support.v4.app.BackStackState[n];
    }
}
