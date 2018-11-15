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
import com.facebook.login.DeviceAuthDialog;

static final class DeviceAuthDialog.RequestState
implements Parcelable.Creator<DeviceAuthDialog.RequestState> {
    DeviceAuthDialog.RequestState() {
    }

    public DeviceAuthDialog.RequestState createFromParcel(Parcel parcel) {
        return new DeviceAuthDialog.RequestState(parcel);
    }

    public DeviceAuthDialog.RequestState[] newArray(int n) {
        return new DeviceAuthDialog.RequestState[n];
    }
}
