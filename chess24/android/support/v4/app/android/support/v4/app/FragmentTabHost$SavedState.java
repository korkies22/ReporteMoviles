/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.view.View
 *  android.view.View$BaseSavedState
 */
package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentTabHost;
import android.view.View;

static class FragmentTabHost.SavedState
extends View.BaseSavedState {
    public static final Parcelable.Creator<FragmentTabHost.SavedState> CREATOR = new Parcelable.Creator<FragmentTabHost.SavedState>(){

        public FragmentTabHost.SavedState createFromParcel(Parcel parcel) {
            return new FragmentTabHost.SavedState(parcel);
        }

        public FragmentTabHost.SavedState[] newArray(int n) {
            return new FragmentTabHost.SavedState[n];
        }
    };
    String curTab;

    FragmentTabHost.SavedState(Parcel parcel) {
        super(parcel);
        this.curTab = parcel.readString();
    }

    FragmentTabHost.SavedState(Parcelable parcelable) {
        super(parcelable);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FragmentTabHost.SavedState{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode((Object)this)));
        stringBuilder.append(" curTab=");
        stringBuilder.append(this.curTab);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeString(this.curTab);
    }

}
