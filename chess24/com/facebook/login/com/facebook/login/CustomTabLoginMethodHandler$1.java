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

static final class CustomTabLoginMethodHandler
implements Parcelable.Creator {
    CustomTabLoginMethodHandler() {
    }

    public com.facebook.login.CustomTabLoginMethodHandler createFromParcel(Parcel parcel) {
        return new com.facebook.login.CustomTabLoginMethodHandler(parcel);
    }

    public com.facebook.login.CustomTabLoginMethodHandler[] newArray(int n) {
        return new com.facebook.login.CustomTabLoginMethodHandler[n];
    }
}
