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
import com.viewpagerindicator.LinePageIndicator;

static class LinePageIndicator.SavedState
extends View.BaseSavedState {
    public static final Parcelable.Creator<LinePageIndicator.SavedState> CREATOR = new Parcelable.Creator<LinePageIndicator.SavedState>(){

        public LinePageIndicator.SavedState createFromParcel(Parcel parcel) {
            return new LinePageIndicator.SavedState(parcel);
        }

        public LinePageIndicator.SavedState[] newArray(int n) {
            return new LinePageIndicator.SavedState[n];
        }
    };
    int currentPage;

    private LinePageIndicator.SavedState(Parcel parcel) {
        super(parcel);
        this.currentPage = parcel.readInt();
    }

    public LinePageIndicator.SavedState(Parcelable parcelable) {
        super(parcelable);
    }

    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeInt(this.currentPage);
    }

}
