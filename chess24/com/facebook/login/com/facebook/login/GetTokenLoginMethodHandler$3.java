/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.facebook.login;

import android.os.Parcel;
import android.os.Parcelable;

static final class GetTokenLoginMethodHandler
implements Parcelable.Creator {
    GetTokenLoginMethodHandler() {
    }

    public com.facebook.login.GetTokenLoginMethodHandler createFromParcel(Parcel parcel) {
        return new com.facebook.login.GetTokenLoginMethodHandler(parcel);
    }

    public com.facebook.login.GetTokenLoginMethodHandler[] newArray(int n) {
        return new com.facebook.login.GetTokenLoginMethodHandler[n];
    }
}
