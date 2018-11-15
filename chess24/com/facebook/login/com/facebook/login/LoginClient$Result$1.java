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

static final class LoginClient.Result
implements Parcelable.Creator {
    LoginClient.Result() {
    }

    public LoginClient.Result createFromParcel(Parcel parcel) {
        return new LoginClient.Result(parcel, null);
    }

    public LoginClient.Result[] newArray(int n) {
        return new LoginClient.Result[n];
    }
}
