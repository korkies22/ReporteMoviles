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

static final class ActionMenuPresenter.SavedState
implements Parcelable.Creator<ActionMenuPresenter.SavedState> {
    ActionMenuPresenter.SavedState() {
    }

    public ActionMenuPresenter.SavedState createFromParcel(Parcel parcel) {
        return new ActionMenuPresenter.SavedState(parcel);
    }

    public ActionMenuPresenter.SavedState[] newArray(int n) {
        return new ActionMenuPresenter.SavedState[n];
    }
}
