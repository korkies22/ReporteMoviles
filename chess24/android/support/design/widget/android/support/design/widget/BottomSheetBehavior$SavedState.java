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
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.AbsSavedState;

protected static class BottomSheetBehavior.SavedState
extends AbsSavedState {
    public static final Parcelable.Creator<BottomSheetBehavior.SavedState> CREATOR = new Parcelable.ClassLoaderCreator<BottomSheetBehavior.SavedState>(){

        public BottomSheetBehavior.SavedState createFromParcel(Parcel parcel) {
            return new BottomSheetBehavior.SavedState(parcel, null);
        }

        public BottomSheetBehavior.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new BottomSheetBehavior.SavedState(parcel, classLoader);
        }

        public BottomSheetBehavior.SavedState[] newArray(int n) {
            return new BottomSheetBehavior.SavedState[n];
        }
    };
    final int state;

    public BottomSheetBehavior.SavedState(Parcel parcel) {
        this(parcel, null);
    }

    public BottomSheetBehavior.SavedState(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
        this.state = parcel.readInt();
    }

    public BottomSheetBehavior.SavedState(Parcelable parcelable, int n) {
        super(parcelable);
        this.state = n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeInt(this.state);
    }

}
