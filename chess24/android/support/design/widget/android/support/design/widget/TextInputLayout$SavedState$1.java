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
import android.support.design.widget.TextInputLayout;

static final class TextInputLayout.SavedState
implements Parcelable.ClassLoaderCreator<TextInputLayout.SavedState> {
    TextInputLayout.SavedState() {
    }

    public TextInputLayout.SavedState createFromParcel(Parcel parcel) {
        return new TextInputLayout.SavedState(parcel, null);
    }

    public TextInputLayout.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return new TextInputLayout.SavedState(parcel, classLoader);
    }

    public TextInputLayout.SavedState[] newArray(int n) {
        return new TextInputLayout.SavedState[n];
    }
}
