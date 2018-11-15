/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 */
package android.support.design.widget;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.AbsSavedState;

static class BottomNavigationView.SavedState
extends AbsSavedState {
    public static final Parcelable.Creator<BottomNavigationView.SavedState> CREATOR = new Parcelable.ClassLoaderCreator<BottomNavigationView.SavedState>(){

        public BottomNavigationView.SavedState createFromParcel(Parcel parcel) {
            return new BottomNavigationView.SavedState(parcel, null);
        }

        public BottomNavigationView.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new BottomNavigationView.SavedState(parcel, classLoader);
        }

        public BottomNavigationView.SavedState[] newArray(int n) {
            return new BottomNavigationView.SavedState[n];
        }
    };
    Bundle menuPresenterState;

    public BottomNavigationView.SavedState(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
        this.readFromParcel(parcel, classLoader);
    }

    public BottomNavigationView.SavedState(Parcelable parcelable) {
        super(parcelable);
    }

    private void readFromParcel(Parcel parcel, ClassLoader classLoader) {
        this.menuPresenterState = parcel.readBundle(classLoader);
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeBundle(this.menuPresenterState);
    }

}
