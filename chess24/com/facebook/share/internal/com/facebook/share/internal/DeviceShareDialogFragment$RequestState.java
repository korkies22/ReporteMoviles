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

private static class DeviceShareDialogFragment.RequestState
implements Parcelable {
    public static final Parcelable.Creator<DeviceShareDialogFragment.RequestState> CREATOR = new Parcelable.Creator<DeviceShareDialogFragment.RequestState>(){

        public DeviceShareDialogFragment.RequestState createFromParcel(Parcel parcel) {
            return new DeviceShareDialogFragment.RequestState(parcel);
        }

        public DeviceShareDialogFragment.RequestState[] newArray(int n) {
            return new DeviceShareDialogFragment.RequestState[n];
        }
    };
    private long expiresIn;
    private String userCode;

    DeviceShareDialogFragment.RequestState() {
    }

    protected DeviceShareDialogFragment.RequestState(Parcel parcel) {
        this.userCode = parcel.readString();
        this.expiresIn = parcel.readLong();
    }

    public int describeContents() {
        return 0;
    }

    public long getExpiresIn() {
        return this.expiresIn;
    }

    public String getUserCode() {
        return this.userCode;
    }

    public void setExpiresIn(long l) {
        this.expiresIn = l;
    }

    public void setUserCode(String string) {
        this.userCode = string;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.userCode);
        parcel.writeLong(this.expiresIn);
    }

}
