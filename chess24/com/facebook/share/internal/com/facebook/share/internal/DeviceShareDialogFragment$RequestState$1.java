/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.facebook.share.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.share.internal.DeviceShareDialogFragment;

static final class DeviceShareDialogFragment.RequestState
implements Parcelable.Creator<DeviceShareDialogFragment.RequestState> {
    DeviceShareDialogFragment.RequestState() {
    }

    public DeviceShareDialogFragment.RequestState createFromParcel(Parcel parcel) {
        return new DeviceShareDialogFragment.RequestState(parcel);
    }

    public DeviceShareDialogFragment.RequestState[] newArray(int n) {
        return new DeviceShareDialogFragment.RequestState[n];
    }
}
