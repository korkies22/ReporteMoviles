/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 */
package android.support.design.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.AbsSavedState;

protected static class AppBarLayout.Behavior.SavedState
extends AbsSavedState {
    public static final Parcelable.Creator<AppBarLayout.Behavior.SavedState> CREATOR = new Parcelable.ClassLoaderCreator<AppBarLayout.Behavior.SavedState>(){

        public AppBarLayout.Behavior.SavedState createFromParcel(Parcel parcel) {
            return new AppBarLayout.Behavior.SavedState(parcel, null);
        }

        public AppBarLayout.Behavior.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new AppBarLayout.Behavior.SavedState(parcel, classLoader);
        }

        public AppBarLayout.Behavior.SavedState[] newArray(int n) {
            return new AppBarLayout.Behavior.SavedState[n];
        }
    };
    boolean firstVisibleChildAtMinimumHeight;
    int firstVisibleChildIndex;
    float firstVisibleChildPercentageShown;

    public AppBarLayout.Behavior.SavedState(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
        this.firstVisibleChildIndex = parcel.readInt();
        this.firstVisibleChildPercentageShown = parcel.readFloat();
        boolean bl = parcel.readByte() != 0;
        this.firstVisibleChildAtMinimumHeight = bl;
    }

    public AppBarLayout.Behavior.SavedState(Parcelable parcelable) {
        super(parcelable);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeInt(this.firstVisibleChildIndex);
        parcel.writeFloat(this.firstVisibleChildPercentageShown);
        parcel.writeByte((byte)(this.firstVisibleChildAtMinimumHeight ? 1 : 0));
    }

}
