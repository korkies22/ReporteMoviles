/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.util.Base64
 *  android.util.Log
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.Utility;
import com.facebook.login.LoginClient;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

abstract class LoginMethodHandler
implements Parcelable {
    protected LoginClient loginClient;
    Map<String, String> methodLoggingExtras;

    LoginMethodHandler(Parcel parcel) {
        this.methodLoggingExtras = Utility.readStringMapFromParcel(parcel);
    }

    LoginMethodHandler(LoginClient loginClient) {
        this.loginClient = loginClient;
    }

    static AccessToken createAccessTokenFromNativeLogin(Bundle bundle, AccessTokenSource accessTokenSource, String string) {
        Date date = Utility.getBundleLongAsDate(bundle, "com.facebook.platform.extra.EXPIRES_SECONDS_SINCE_EPOCH", new Date(0L));
        ArrayList arrayList = bundle.getStringArrayList("com.facebook.platform.extra.PERMISSIONS");
        String string2 = bundle.getString("com.facebook.platform.extra.ACCESS_TOKEN");
        if (Utility.isNullOrEmpty(string2)) {
            return null;
        }
        return new AccessToken(string2, string, bundle.getString("com.facebook.platform.extra.USER_ID"), arrayList, null, accessTokenSource, date, new Date());
    }

    public static AccessToken createAccessTokenFromWebBundle(Collection<String> collection, Bundle bundle, AccessTokenSource accessTokenSource, String string) throws FacebookException {
        Date date = Utility.getBundleLongAsDate(bundle, "expires_in", new Date());
        String string2 = bundle.getString("access_token");
        Object object = bundle.getString("granted_scopes");
        if (!Utility.isNullOrEmpty((String)object)) {
            collection = new ArrayList<String>(Arrays.asList(object.split(",")));
        }
        object = !Utility.isNullOrEmpty((String)(object = bundle.getString("denied_scopes"))) ? new ArrayList<String>(Arrays.asList(object.split(","))) : null;
        if (Utility.isNullOrEmpty(string2)) {
            return null;
        }
        return new AccessToken(string2, string, LoginMethodHandler.getUserIDFromSignedRequest(bundle.getString("signed_request")), collection, (Collection<String>)object, accessTokenSource, date, new Date());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static String getUserIDFromSignedRequest(String object) throws FacebookException {
        if (object == null) throw new FacebookException("Authorization response does not contain the signed_request");
        if (object.isEmpty()) throw new FacebookException("Authorization response does not contain the signed_request");
        try {
            String[] arrstring = object.split("\\.");
            if (arrstring.length != 2) throw new FacebookException("Failed to retrieve user_id from signed_request");
            return new JSONObject(new String(Base64.decode((String)arrstring[1], (int)0), "UTF-8")).getString("user_id");
        }
        catch (UnsupportedEncodingException | JSONException object2) {
            throw new FacebookException("Failed to retrieve user_id from signed_request");
        }
    }

    protected void addLoggingExtra(String string, Object object) {
        if (this.methodLoggingExtras == null) {
            this.methodLoggingExtras = new HashMap<String, String>();
        }
        Map<String, String> map = this.methodLoggingExtras;
        object = object == null ? null : object.toString();
        map.put(string, (String)object);
    }

    void cancel() {
    }

    protected String getClientState(String string) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("0_auth_logger_id", (Object)string);
            jSONObject.put("3_method", (Object)this.getNameForLogging());
            this.putChallengeParam(jSONObject);
        }
        catch (JSONException jSONException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error creating client state json: ");
            stringBuilder.append(jSONException.getMessage());
            Log.w((String)"LoginMethodHandler", (String)stringBuilder.toString());
        }
        return jSONObject.toString();
    }

    abstract String getNameForLogging();

    protected void logWebLoginCompleted(String string) {
        String string2 = this.loginClient.getPendingRequest().getApplicationId();
        AppEventsLogger appEventsLogger = AppEventsLogger.newLogger((Context)this.loginClient.getActivity(), string2);
        Bundle bundle = new Bundle();
        bundle.putString("fb_web_login_e2e", string);
        bundle.putLong("fb_web_login_switchback_time", System.currentTimeMillis());
        bundle.putString("app_id", string2);
        appEventsLogger.logSdkEvent("fb_dialogs_web_login_dialog_complete", null, bundle);
    }

    boolean needsInternetPermission() {
        return false;
    }

    boolean onActivityResult(int n, int n2, Intent intent) {
        return false;
    }

    void putChallengeParam(JSONObject jSONObject) throws JSONException {
    }

    void setLoginClient(LoginClient loginClient) {
        if (this.loginClient != null) {
            throw new FacebookException("Can't set LoginClient if it is already set.");
        }
        this.loginClient = loginClient;
    }

    abstract boolean tryAuthorize(LoginClient.Request var1);

    public void writeToParcel(Parcel parcel, int n) {
        Utility.writeStringMapToParcel(parcel, this.methodLoggingExtras);
    }
}
