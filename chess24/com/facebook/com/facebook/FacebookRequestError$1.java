/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.facebook;

import android.os.Parcel;
import android.os.Parcelable;

static final class FacebookRequestError
implements Parcelable.Creator<com.facebook.FacebookRequestError> {
    FacebookRequestError() {
    }

    public com.facebook.FacebookRequestError createFromParcel(Parcel parcel) {
        return new com.facebook.FacebookRequestError(parcel, null);
    }

    public com.facebook.FacebookRequestError[] newArray(int n) {
        return new com.facebook.FacebookRequestError[n];
    }
}
