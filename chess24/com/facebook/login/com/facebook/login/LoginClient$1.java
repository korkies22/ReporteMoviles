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

static final class LoginClient
implements Parcelable.Creator {
    LoginClient() {
    }

    public com.facebook.login.LoginClient createFromParcel(Parcel parcel) {
        return new com.facebook.login.LoginClient(parcel);
    }

    public com.facebook.login.LoginClient[] newArray(int n) {
        return new com.facebook.login.LoginClient[n];
    }
}
