/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.SparseArray
 */
package android.support.design.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.AbsSavedState;
import android.util.SparseArray;

protected static class CoordinatorLayout.SavedState
extends AbsSavedState {
    public static final Parcelable.Creator<CoordinatorLayout.SavedState> CREATOR = new Parcelable.ClassLoaderCreator<CoordinatorLayout.SavedState>(){

        public CoordinatorLayout.SavedState createFromParcel(Parcel parcel) {
            return new CoordinatorLayout.SavedState(parcel, null);
        }

        public CoordinatorLayout.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new CoordinatorLayout.SavedState(parcel, classLoader);
        }

        public CoordinatorLayout.SavedState[] newArray(int n) {
            return new CoordinatorLayout.SavedState[n];
        }
    };
    SparseArray<Parcelable> behaviorStates;

    public CoordinatorLayout.SavedState(Parcel arrparcelable, ClassLoader classLoader) {
        super((Parcel)arrparcelable, classLoader);
        int n = arrparcelable.readInt();
        int[] arrn = new int[n];
        arrparcelable.readIntArray(arrn);
        arrparcelable = arrparcelable.readParcelableArray(classLoader);
        this.behaviorStates = new SparseArray(n);
        for (int i = 0; i < n; ++i) {
            this.behaviorStates.append(arrn[i], (Object)arrparcelable[i]);
        }
    }

    public CoordinatorLayout.SavedState(Parcelable parcelable) {
        super(parcelable);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        int[] arrn = this.behaviorStates;
        int n2 = arrn != null ? this.behaviorStates.size() : 0;
        parcel.writeInt(n2);
        arrn = new int[n2];
        Parcelable[] arrparcelable = new Parcelable[n2];
        for (int i = 0; i < n2; ++i) {
            arrn[i] = this.behaviorStates.keyAt(i);
            arrparcelable[i] = (Parcelable)this.behaviorStates.valueAt(i);
        }
        parcel.writeIntArray(arrn);
        parcel.writeParcelableArray(arrparcelable, n);
    }

}
