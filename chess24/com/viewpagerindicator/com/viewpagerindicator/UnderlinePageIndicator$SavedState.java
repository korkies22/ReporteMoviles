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
import com.viewpagerindicator.UnderlinePageIndicator;

static class UnderlinePageIndicator.SavedState
extends View.BaseSavedState {
    public static final Parcelable.Creator<UnderlinePageIndicator.SavedState> CREATOR = new Parcelable.Creator<UnderlinePageIndicator.SavedState>(){

        public UnderlinePageIndicator.SavedState createFromParcel(Parcel parcel) {
            return new UnderlinePageIndicator.SavedState(parcel);
        }

        public UnderlinePageIndicator.SavedState[] newArray(int n) {
            return new UnderlinePageIndicator.SavedState[n];
        }
    };
    int currentPage;

    private UnderlinePageIndicator.SavedState(Parcel parcel) {
        super(parcel);
        this.currentPage = parcel.readInt();
    }

    public UnderlinePageIndicator.SavedState(Parcelable parcelable) {
        super(parcelable);
    }

    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeInt(this.currentPage);
    }

}
