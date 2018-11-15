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
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.login.DeviceAuthDialog;
import com.facebook.login.LoginClient;
import com.facebook.login.LoginMethodHandler;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;

class DeviceAuthMethodHandler
extends LoginMethodHandler {
    public static final Parcelable.Creator<DeviceAuthMethodHandler> CREATOR = new Parcelable.Creator(){

        public DeviceAuthMethodHandler createFromParcel(Parcel parcel) {
            return new DeviceAuthMethodHandler(parcel);
        }

        public DeviceAuthMethodHandler[] newArray(int n) {
            return new DeviceAuthMethodHandler[n];
        }
    };
    private static ScheduledThreadPoolExecutor backgroundExecutor;

    protected DeviceAuthMethodHandler(Parcel parcel) {
        super(parcel);
    }

    DeviceAuthMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    public static ScheduledThreadPoolExecutor getBackgroundExecutor() {
        synchronized (DeviceAuthMethodHandler.class) {
            if (backgroundExecutor == null) {
                backgroundExecutor = new ScheduledThreadPoolExecutor(1);
            }
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = backgroundExecutor;
            return scheduledThreadPoolExecutor;
        }
    }

    private void showDialog(LoginClient.Request request) {
        DeviceAuthDialog deviceAuthDialog = new DeviceAuthDialog();
        deviceAuthDialog.show(this.loginClient.getActivity().getSupportFragmentManager(), "login_with_facebook");
        deviceAuthDialog.startLogin(request);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    String getNameForLogging() {
        return "device_auth";
    }

    public void onCancel() {
        LoginClient.Result result = LoginClient.Result.createCancelResult(this.loginClient.getPendingRequest(), "User canceled log in.");
        this.loginClient.completeAndValidate(result);
    }

    public void onError(Exception object) {
        object = LoginClient.Result.createErrorResult(this.loginClient.getPendingRequest(), null, object.getMessage());
        this.loginClient.completeAndValidate((LoginClient.Result)object);
    }

    public void onSuccess(String object, String string, String string2, Collection<String> collection, Collection<String> collection2, AccessTokenSource accessTokenSource, Date date, Date date2) {
        object = new AccessToken((String)object, string, string2, collection, collection2, accessTokenSource, date, date2);
        object = LoginClient.Result.createTokenResult(this.loginClient.getPendingRequest(), (AccessToken)object);
        this.loginClient.completeAndValidate((LoginClient.Result)object);
    }

    @Override
    boolean tryAuthorize(LoginClient.Request request) {
        this.showDialog(request);
        return true;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
    }

}
