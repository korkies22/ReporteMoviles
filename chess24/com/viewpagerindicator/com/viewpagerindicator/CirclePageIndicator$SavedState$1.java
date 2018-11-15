/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.viewpagerindicator;

import android.os.Parcel;
import android.os.Parcelable;
import com.viewpagerindicator.CirclePageIndicator;

static final class CirclePageIndicator.SavedState
implements Parcelable.Creator<CirclePageIndicator.SavedState> {
    CirclePageIndicator.SavedState() {
    }

    public CirclePageIndicator.SavedState createFromParcel(Parcel parcel) {
        return new CirclePageIndicator.SavedState(parcel, null);
    }

    public CirclePageIndicator.SavedState[] newArray(int n) {
        return new CirclePageIndicator.SavedState[n];
    }
}
