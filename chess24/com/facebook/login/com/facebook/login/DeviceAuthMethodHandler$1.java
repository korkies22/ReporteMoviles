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

static final class DeviceAuthMethodHandler
implements Parcelable.Creator {
    DeviceAuthMethodHandler() {
    }

    public com.facebook.login.DeviceAuthMethodHandler createFromParcel(Parcel parcel) {
        return new com.facebook.login.DeviceAuthMethodHandler(parcel);
    }

    public com.facebook.login.DeviceAuthMethodHandler[] newArray(int n) {
        return new com.facebook.login.DeviceAuthMethodHandler[n];
    }
}
