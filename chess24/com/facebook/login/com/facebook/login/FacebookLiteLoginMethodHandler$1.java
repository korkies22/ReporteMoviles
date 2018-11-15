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

static final class FacebookLiteLoginMethodHandler
implements Parcelable.Creator {
    FacebookLiteLoginMethodHandler() {
    }

    public com.facebook.login.FacebookLiteLoginMethodHandler createFromParcel(Parcel parcel) {
        return new com.facebook.login.FacebookLiteLoginMethodHandler(parcel);
    }

    public com.facebook.login.FacebookLiteLoginMethodHandler[] newArray(int n) {
        return new com.facebook.login.FacebookLiteLoginMethodHandler[n];
    }
}
