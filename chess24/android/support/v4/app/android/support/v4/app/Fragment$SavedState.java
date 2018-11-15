/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.v4.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

public static class Fragment.SavedState
implements Parcelable {
    public static final Parcelable.Creator<Fragment.SavedState> CREATOR = new Parcelable.Creator<Fragment.SavedState>(){

        public Fragment.SavedState createFromParcel(Parcel parcel) {
            return new Fragment.SavedState(parcel, null);
        }

        public Fragment.SavedState[] newArray(int n) {
            return new Fragment.SavedState[n];
        }
    };
    final Bundle mState;

    Fragment.SavedState(Bundle bundle) {
        this.mState = bundle;
    }

    Fragment.SavedState(Parcel parcel, ClassLoader classLoader) {
        this.mState = parcel.readBundle();
        if (classLoader != null && this.mState != null) {
            this.mState.setClassLoader(classLoader);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBundle(this.mState);
    }

}
