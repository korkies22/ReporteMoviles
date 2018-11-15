/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.v7.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.ActionMenuPresenter;

private static class ActionMenuPresenter.SavedState
implements Parcelable {
    public static final Parcelable.Creator<ActionMenuPresenter.SavedState> CREATOR = new Parcelable.Creator<ActionMenuPresenter.SavedState>(){

        public ActionMenuPresenter.SavedState createFromParcel(Parcel parcel) {
            return new ActionMenuPresenter.SavedState(parcel);
        }

        public ActionMenuPresenter.SavedState[] newArray(int n) {
            return new ActionMenuPresenter.SavedState[n];
        }
    };
    public int openSubMenuId;

    ActionMenuPresenter.SavedState() {
    }

    ActionMenuPresenter.SavedState(Parcel parcel) {
        this.openSubMenuId = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.openSubMenuId);
    }

}
