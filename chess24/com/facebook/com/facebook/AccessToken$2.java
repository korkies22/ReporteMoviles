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

static final class AccessToken
implements Parcelable.Creator {
    AccessToken() {
    }

    public com.facebook.AccessToken createFromParcel(Parcel parcel) {
        return new com.facebook.AccessToken(parcel);
    }

    public com.facebook.AccessToken[] newArray(int n) {
        return new com.facebook.AccessToken[n];
    }
}
