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

static final class KatanaProxyLoginMethodHandler
implements Parcelable.Creator {
    KatanaProxyLoginMethodHandler() {
    }

    public com.facebook.login.KatanaProxyLoginMethodHandler createFromParcel(Parcel parcel) {
        return new com.facebook.login.KatanaProxyLoginMethodHandler(parcel);
    }

    public com.facebook.login.KatanaProxyLoginMethodHandler[] newArray(int n) {
        return new com.facebook.login.KatanaProxyLoginMethodHandler[n];
    }
}
