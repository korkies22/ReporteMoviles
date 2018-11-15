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
import com.viewpagerindicator.UnderlinePageIndicator;

static final class UnderlinePageIndicator.SavedState
implements Parcelable.Creator<UnderlinePageIndicator.SavedState> {
    UnderlinePageIndicator.SavedState() {
    }

    public UnderlinePageIndicator.SavedState createFromParcel(Parcel parcel) {
        return new UnderlinePageIndicator.SavedState(parcel, null);
    }

    public UnderlinePageIndicator.SavedState[] newArray(int n) {
        return new UnderlinePageIndicator.SavedState[n];
    }
}
