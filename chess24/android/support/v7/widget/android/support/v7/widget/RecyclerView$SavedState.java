/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 */
package android.support.v7.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.v4.view.AbsSavedState;
import android.support.v7.widget.RecyclerView;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public static class RecyclerView.SavedState
extends AbsSavedState {
    public static final Parcelable.Creator<RecyclerView.SavedState> CREATOR = new Parcelable.ClassLoaderCreator<RecyclerView.SavedState>(){

        public RecyclerView.SavedState createFromParcel(Parcel parcel) {
            return new RecyclerView.SavedState(parcel, null);
        }

        public RecyclerView.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new RecyclerView.SavedState(parcel, classLoader);
        }

        public RecyclerView.SavedState[] newArray(int n) {
            return new RecyclerView.SavedState[n];
        }
    };
    Parcelable mLayoutState;

    RecyclerView.SavedState(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
        if (classLoader == null) {
            classLoader = RecyclerView.LayoutManager.class.getClassLoader();
        }
        this.mLayoutState = parcel.readParcelable(classLoader);
    }

    RecyclerView.SavedState(Parcelable parcelable) {
        super(parcelable);
    }

    void copyFrom(RecyclerView.SavedState savedState) {
        this.mLayoutState = savedState.mLayoutState;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeParcelable(this.mLayoutState, 0);
    }

}
