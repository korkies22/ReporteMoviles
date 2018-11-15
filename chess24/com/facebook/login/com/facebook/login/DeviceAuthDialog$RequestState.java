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
import java.util.Date;
import java.util.Locale;

private static class DeviceAuthDialog.RequestState
implements Parcelable {
    public static final Parcelable.Creator<DeviceAuthDialog.RequestState> CREATOR = new Parcelable.Creator<DeviceAuthDialog.RequestState>(){

        public DeviceAuthDialog.RequestState createFromParcel(Parcel parcel) {
            return new DeviceAuthDialog.RequestState(parcel);
        }

        public DeviceAuthDialog.RequestState[] newArray(int n) {
            return new DeviceAuthDialog.RequestState[n];
        }
    };
    private String authorizationUri;
    private long interval;
    private long lastPoll;
    private String requestCode;
    private String userCode;

    DeviceAuthDialog.RequestState() {
    }

    protected DeviceAuthDialog.RequestState(Parcel parcel) {
        this.userCode = parcel.readString();
        this.requestCode = parcel.readString();
        this.interval = parcel.readLong();
        this.lastPoll = parcel.readLong();
    }

    public int describeContents() {
        return 0;
    }

    public String getAuthorizationUri() {
        return this.authorizationUri;
    }

    public long getInterval() {
        return this.interval;
    }

    public String getRequestCode() {
        return this.requestCode;
    }

    public String getUserCode() {
        return this.userCode;
    }

    public void setInterval(long l) {
        this.interval = l;
    }

    public void setLastPoll(long l) {
        this.lastPoll = l;
    }

    public void setRequestCode(String string) {
        this.requestCode = string;
    }

    public void setUserCode(String string) {
        this.userCode = string;
        this.authorizationUri = String.format(Locale.ENGLISH, "https://facebook.com/device?user_code=%1$s&qr=1", string);
    }

    public boolean withinLastRefreshWindow() {
        long l = this.lastPoll;
        boolean bl = false;
        if (l == 0L) {
            return false;
        }
        if (new Date().getTime() - this.lastPoll - this.interval * 1000L < 0L) {
            bl = true;
        }
        return bl;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.userCode);
        parcel.writeString(this.requestCode);
        parcel.writeLong(this.interval);
        parcel.writeLong(this.lastPoll);
    }

}
