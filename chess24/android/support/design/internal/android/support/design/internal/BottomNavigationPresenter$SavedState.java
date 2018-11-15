/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.design.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationPresenter;

static class BottomNavigationPresenter.SavedState
implements Parcelable {
    public static final Parcelable.Creator<BottomNavigationPresenter.SavedState> CREATOR = new Parcelable.Creator<BottomNavigationPresenter.SavedState>(){

        public BottomNavigationPresenter.SavedState createFromParcel(Parcel parcel) {
            return new BottomNavigationPresenter.SavedState(parcel);
        }

        public BottomNavigationPresenter.SavedState[] newArray(int n) {
            return new BottomNavigationPresenter.SavedState[n];
        }
    };
    int selectedItemId;

    BottomNavigationPresenter.SavedState() {
    }

    BottomNavigationPresenter.SavedState(Parcel parcel) {
        this.selectedItemId = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel parcel, int n) {
        parcel.writeInt(this.selectedItemId);
    }

}
