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
import com.viewpagerindicator.TitlePageIndicator;

static final class TitlePageIndicator.SavedState
implements Parcelable.Creator<TitlePageIndicator.SavedState> {
    TitlePageIndicator.SavedState() {
    }

    public TitlePageIndicator.SavedState createFromParcel(Parcel parcel) {
        return new TitlePageIndicator.SavedState(parcel, null);
    }

    public TitlePageIndicator.SavedState[] newArray(int n) {
        return new TitlePageIndicator.SavedState[n];
    }
}
