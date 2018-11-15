/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.view.View
 *  android.view.View$BaseSavedState
 */
package com.viewpagerindicator;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import com.viewpagerindicator.CirclePageIndicator;

static class CirclePageIndicator.SavedState
extends View.BaseSavedState {
    public static final Parcelable.Creator<CirclePageIndicator.SavedState> CREATOR = new Parcelable.Creator<CirclePageIndicator.SavedState>(){

        public CirclePageIndicator.SavedState createFromParcel(Parcel parcel) {
            return new CirclePageIndicator.SavedState(parcel);
        }

        public CirclePageIndicator.SavedState[] newArray(int n) {
            return new CirclePageIndicator.SavedState[n];
        }
    };
    int currentPage;

    private CirclePageIndicator.SavedState(Parcel parcel) {
        super(parcel);
        this.currentPage = parcel.readInt();
    }

    public CirclePageIndicator.SavedState(Parcelable parcelable) {
        super(parcelable);
    }

    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeInt(this.currentPage);
    }

}
