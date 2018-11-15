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
import android.support.design.widget.NavigationView;
import android.support.v4.view.AbsSavedState;

public static class NavigationView.SavedState
extends AbsSavedState {
    public static final Parcelable.Creator<NavigationView.SavedState> CREATOR = new Parcelable.ClassLoaderCreator<NavigationView.SavedState>(){

        public NavigationView.SavedState createFromParcel(Parcel parcel) {
            return new NavigationView.SavedState(parcel, null);
        }

        public NavigationView.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new NavigationView.SavedState(parcel, classLoader);
        }

        public NavigationView.SavedState[] newArray(int n) {
            return new NavigationView.SavedState[n];
        }
    };
    public Bundle menuState;

    public NavigationView.SavedState(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
        this.menuState = parcel.readBundle(classLoader);
    }

    public NavigationView.SavedState(Parcelable parcelable) {
        super(parcelable);
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeBundle(this.menuState);
    }

}
