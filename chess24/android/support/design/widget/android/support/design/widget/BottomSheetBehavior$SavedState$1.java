/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 */
package android.support.design.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.BottomSheetBehavior;

static final class BottomSheetBehavior.SavedState
implements Parcelable.ClassLoaderCreator<BottomSheetBehavior.SavedState> {
    BottomSheetBehavior.SavedState() {
    }

    public BottomSheetBehavior.SavedState createFromParcel(Parcel parcel) {
        return new BottomSheetBehavior.SavedState(parcel, null);
    }

    public BottomSheetBehavior.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return new BottomSheetBehavior.SavedState(parcel, classLoader);
    }

    public BottomSheetBehavior.SavedState[] newArray(int n) {
        return new BottomSheetBehavior.SavedState[n];
    }
}
