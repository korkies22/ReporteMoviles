/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.facebook.login;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import com.facebook.internal.NativeProtocol;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginClient;
import com.facebook.login.NativeAppLoginMethodHandler;
import java.util.Set;

class KatanaProxyLoginMethodHandler
extends NativeAppLoginMethodHandler {
    public static final Parcelable.Creator<KatanaProxyLoginMethodHandler> CREATOR = new Parcelable.Creator(){

        public KatanaProxyLoginMethodHandler createFromParcel(Parcel parcel) {
            return new KatanaProxyLoginMethodHandler(parcel);
        }

        public KatanaProxyLoginMethodHandler[] newArray(int n) {
            return new KatanaProxyLoginMethodHandler[n];
        }
    };

    KatanaProxyLoginMethodHandler(Parcel parcel) {
        super(parcel);
    }

    KatanaProxyLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    String getNameForLogging() {
        return "katana_proxy_auth";
    }

    @Override
    boolean tryAuthorize(LoginClient.Request request) {
        String string = LoginClient.getE2E();
        request = NativeProtocol.createProxyAuthIntent((Context)this.loginClient.getActivity(), request.getApplicationId(), request.getPermissions(), string, request.isRerequest(), request.hasPublishPermission(), request.getDefaultAudience(), this.getClientState(request.getAuthId()));
        this.addLoggingExtra("e2e", string);
        return this.tryIntent((Intent)request, LoginClient.getLoginRequestCode());
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
    }

}
