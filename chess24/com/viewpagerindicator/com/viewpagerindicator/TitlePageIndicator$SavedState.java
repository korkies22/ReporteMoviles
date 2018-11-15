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
import com.viewpagerindicator.TitlePageIndicator;

static class TitlePageIndicator.SavedState
extends View.BaseSavedState {
    public static final Parcelable.Creator<TitlePageIndicator.SavedState> CREATOR = new Parcelable.Creator<TitlePageIndicator.SavedState>(){

        public TitlePageIndicator.SavedState createFromParcel(Parcel parcel) {
            return new TitlePageIndicator.SavedState(parcel);
        }

        public TitlePageIndicator.SavedState[] newArray(int n) {
            return new TitlePageIndicator.SavedState[n];
        }
    };
    int currentPage;

    private TitlePageIndicator.SavedState(Parcel parcel) {
        super(parcel);
        this.currentPage = parcel.readInt();
    }

    public TitlePageIndicator.SavedState(Parcelable parcelable) {
        super(parcelable);
    }

    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeInt(this.currentPage);
    }

}
