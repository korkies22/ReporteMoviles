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
import com.viewpagerindicator.LinePageIndicator;

static final class LinePageIndicator.SavedState
implements Parcelable.Creator<LinePageIndicator.SavedState> {
    LinePageIndicator.SavedState() {
    }

    public LinePageIndicator.SavedState createFromParcel(Parcel parcel) {
        return new LinePageIndicator.SavedState(parcel, null);
    }

    public LinePageIndicator.SavedState[] newArray(int n) {
        return new LinePageIndicator.SavedState[n];
    }
}
