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

static final class WebViewLoginMethodHandler
implements Parcelable.Creator {
    WebViewLoginMethodHandler() {
    }

    public com.facebook.login.WebViewLoginMethodHandler createFromParcel(Parcel parcel) {
        return new com.facebook.login.WebViewLoginMethodHandler(parcel);
    }

    public com.facebook.login.WebViewLoginMethodHandler[] newArray(int n) {
        return new com.facebook.login.WebViewLoginMethodHandler[n];
    }
}
