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
import android.support.v4.view.AbsSavedState;
import android.support.v7.widget.SearchView;

static class SearchView.SavedState
extends AbsSavedState {
    public static final Parcelable.Creator<SearchView.SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SearchView.SavedState>(){

        public SearchView.SavedState createFromParcel(Parcel parcel) {
            return new SearchView.SavedState(parcel, null);
        }

        public SearchView.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new SearchView.SavedState(parcel, classLoader);
        }

        public SearchView.SavedState[] newArray(int n) {
            return new SearchView.SavedState[n];
        }
    };
    boolean isIconified;

    public SearchView.SavedState(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
        this.isIconified = (Boolean)parcel.readValue(null);
    }

    SearchView.SavedState(Parcelable parcelable) {
        super(parcelable);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SearchView.SavedState{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" isIconified=");
        stringBuilder.append(this.isIconified);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeValue((Object)this.isIconified);
    }

}
