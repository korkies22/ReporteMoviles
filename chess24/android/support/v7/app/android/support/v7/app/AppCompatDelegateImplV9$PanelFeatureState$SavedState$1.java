/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 */
package android.support.v7.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatDelegateImplV9;

static final class AppCompatDelegateImplV9.PanelFeatureState.SavedState
implements Parcelable.ClassLoaderCreator<AppCompatDelegateImplV9.PanelFeatureState.SavedState> {
    AppCompatDelegateImplV9.PanelFeatureState.SavedState() {
    }

    public AppCompatDelegateImplV9.PanelFeatureState.SavedState createFromParcel(Parcel parcel) {
        return AppCompatDelegateImplV9.PanelFeatureState.SavedState.readFromParcel(parcel, null);
    }

    public AppCompatDelegateImplV9.PanelFeatureState.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return AppCompatDelegateImplV9.PanelFeatureState.SavedState.readFromParcel(parcel, classLoader);
    }

    public AppCompatDelegateImplV9.PanelFeatureState.SavedState[] newArray(int n) {
        return new AppCompatDelegateImplV9.PanelFeatureState.SavedState[n];
    }
}
