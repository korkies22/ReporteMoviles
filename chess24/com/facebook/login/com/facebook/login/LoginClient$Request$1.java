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
import com.facebook.login.LoginClient;

static final class LoginClient.Request
implements Parcelable.Creator {
    LoginClient.Request() {
    }

    public LoginClient.Request createFromParcel(Parcel parcel) {
        return new LoginClient.Request(parcel, null);
    }

    public LoginClient.Request[] newArray(int n) {
        return new LoginClient.Request[n];
    }
}
