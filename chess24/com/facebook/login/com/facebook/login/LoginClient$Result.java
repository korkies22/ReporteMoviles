/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package com.facebook.login;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.facebook.AccessToken;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.login.LoginClient;
import java.util.Map;

public static class LoginClient.Result
implements Parcelable {
    public static final Parcelable.Creator<LoginClient.Result> CREATOR = new Parcelable.Creator(){

        public LoginClient.Result createFromParcel(Parcel parcel) {
            return new LoginClient.Result(parcel);
        }

        public LoginClient.Result[] newArray(int n) {
            return new LoginClient.Result[n];
        }
    };
    final Code code;
    final String errorCode;
    final String errorMessage;
    public Map<String, String> loggingExtras;
    final LoginClient.Request request;
    final AccessToken token;

    private LoginClient.Result(Parcel parcel) {
        this.code = Code.valueOf(parcel.readString());
        this.token = (AccessToken)parcel.readParcelable(AccessToken.class.getClassLoader());
        this.errorMessage = parcel.readString();
        this.errorCode = parcel.readString();
        this.request = (LoginClient.Request)parcel.readParcelable(LoginClient.Request.class.getClassLoader());
        this.loggingExtras = Utility.readStringMapFromParcel(parcel);
    }

    LoginClient.Result(LoginClient.Request request, Code code, AccessToken accessToken, String string, String string2) {
        Validate.notNull((Object)code, "code");
        this.request = request;
        this.token = accessToken;
        this.errorMessage = string;
        this.code = code;
        this.errorCode = string2;
    }

    static LoginClient.Result createCancelResult(LoginClient.Request request, String string) {
        return new LoginClient.Result(request, Code.CANCEL, null, string, null);
    }

    static LoginClient.Result createErrorResult(LoginClient.Request request, String string, String string2) {
        return LoginClient.Result.createErrorResult(request, string, string2, null);
    }

    static LoginClient.Result createErrorResult(LoginClient.Request request, String string, String string2, String string3) {
        string = TextUtils.join((CharSequence)": ", Utility.asListNoNulls(string, string2));
        return new LoginClient.Result(request, Code.ERROR, null, string, string3);
    }

    static LoginClient.Result createTokenResult(LoginClient.Request request, AccessToken accessToken) {
        return new LoginClient.Result(request, Code.SUCCESS, accessToken, null, null);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.code.name());
        parcel.writeParcelable((Parcelable)this.token, n);
        parcel.writeString(this.errorMessage);
        parcel.writeString(this.errorCode);
        parcel.writeParcelable((Parcelable)this.request, n);
        Utility.writeStringMapToParcel(parcel, this.loggingExtras);
    }

    static enum Code {
        SUCCESS("success"),
        CANCEL("cancel"),
        ERROR("error");
        
        private final String loggingValue;

        private Code(String string2) {
            this.loggingValue = string2;
        }

        String getLoggingValue() {
            return this.loggingValue;
        }
    }

}
