/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 */
package android.support.v4.view;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewPager;

public static class ViewPager.SavedState
extends AbsSavedState {
    public static final Parcelable.Creator<ViewPager.SavedState> CREATOR = new Parcelable.ClassLoaderCreator<ViewPager.SavedState>(){

        public ViewPager.SavedState createFromParcel(Parcel parcel) {
            return new ViewPager.SavedState(parcel, null);
        }

        public ViewPager.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new ViewPager.SavedState(parcel, classLoader);
        }

        public ViewPager.SavedState[] newArray(int n) {
            return new ViewPager.SavedState[n];
        }
    };
    Parcelable adapterState;
    ClassLoader loader;
    int position;

    ViewPager.SavedState(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
        ClassLoader classLoader2 = classLoader;
        if (classLoader == null) {
            classLoader2 = this.getClass().getClassLoader();
        }
        this.position = parcel.readInt();
        this.adapterState = parcel.readParcelable(classLoader2);
        this.loader = classLoader2;
    }

    public ViewPager.SavedState(@NonNull Parcelable parcelable) {
        super(parcelable);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FragmentPager.SavedState{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" position=");
        stringBuilder.append(this.position);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeInt(this.position);
        parcel.writeParcelable(this.adapterState, n);
    }

}
