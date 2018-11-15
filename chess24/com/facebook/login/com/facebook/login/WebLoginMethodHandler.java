/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.text.TextUtils
 *  android.webkit.CookieSyncManager
 */
package com.facebook.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.FacebookServiceException;
import com.facebook.internal.Utility;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginClient;
import com.facebook.login.LoginMethodHandler;
import java.util.Locale;
import java.util.Set;

abstract class WebLoginMethodHandler
extends LoginMethodHandler {
    private static final String WEB_VIEW_AUTH_HANDLER_STORE = "com.facebook.login.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY";
    private static final String WEB_VIEW_AUTH_HANDLER_TOKEN_KEY = "TOKEN";
    private String e2e;

    WebLoginMethodHandler(Parcel parcel) {
        super(parcel);
    }

    WebLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    private static final String getRedirectUri() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fb");
        stringBuilder.append(FacebookSdk.getApplicationId());
        stringBuilder.append("://authorize");
        return stringBuilder.toString();
    }

    private String loadCookieToken() {
        return this.loginClient.getActivity().getSharedPreferences(WEB_VIEW_AUTH_HANDLER_STORE, 0).getString(WEB_VIEW_AUTH_HANDLER_TOKEN_KEY, "");
    }

    private void saveCookieToken(String string) {
        this.loginClient.getActivity().getSharedPreferences(WEB_VIEW_AUTH_HANDLER_STORE, 0).edit().putString(WEB_VIEW_AUTH_HANDLER_TOKEN_KEY, string).apply();
    }

    protected Bundle addExtraParameters(Bundle bundle, LoginClient.Request object) {
        bundle.putString("redirect_uri", WebLoginMethodHandler.getRedirectUri());
        bundle.putString("client_id", object.getApplicationId());
        object = this.loginClient;
        bundle.putString("e2e", LoginClient.getE2E());
        bundle.putString("response_type", "token,signed_request");
        bundle.putString("return_scopes", "true");
        bundle.putString("auth_type", "rerequest");
        if (this.getSSODevice() != null) {
            bundle.putString("sso", this.getSSODevice());
        }
        return bundle;
    }

    protected Bundle getParameters(LoginClient.Request object) {
        Bundle bundle = new Bundle();
        if (!Utility.isNullOrEmpty(object.getPermissions())) {
            String string = TextUtils.join((CharSequence)",", object.getPermissions());
            bundle.putString("scope", string);
            this.addLoggingExtra("scope", string);
        }
        bundle.putString("default_audience", object.getDefaultAudience().getNativeProtocolAudience());
        bundle.putString("state", this.getClientState(object.getAuthId()));
        object = AccessToken.getCurrentAccessToken();
        object = object != null ? object.getToken() : null;
        if (object != null && object.equals(this.loadCookieToken())) {
            bundle.putString("access_token", (String)object);
            this.addLoggingExtra("access_token", "1");
            return bundle;
        }
        Utility.clearFacebookCookies((Context)this.loginClient.getActivity());
        this.addLoggingExtra("access_token", "0");
        return bundle;
    }

    protected String getSSODevice() {
        return null;
    }

    abstract AccessTokenSource getTokenSource();

    protected void onComplete(LoginClient.Request object, Bundle object2, FacebookException facebookException) {
        this.e2e = null;
        if (object2 != null) {
            if (object2.containsKey("e2e")) {
                this.e2e = object2.getString("e2e");
            }
            try {
                object2 = WebLoginMethodHandler.createAccessTokenFromWebBundle(object.getPermissions(), (Bundle)object2, this.getTokenSource(), object.getApplicationId());
                object = LoginClient.Result.createTokenResult(this.loginClient.getPendingRequest(), (AccessToken)object2);
                CookieSyncManager.createInstance((Context)this.loginClient.getActivity()).sync();
                this.saveCookieToken(object2.getToken());
            }
            catch (FacebookException facebookException2) {
                object = LoginClient.Result.createErrorResult(this.loginClient.getPendingRequest(), null, facebookException2.getMessage());
            }
        } else if (facebookException instanceof FacebookOperationCanceledException) {
            object = LoginClient.Result.createCancelResult(this.loginClient.getPendingRequest(), "User canceled log in.");
        } else {
            this.e2e = null;
            object = facebookException.getMessage();
            if (facebookException instanceof FacebookServiceException) {
                object = ((FacebookServiceException)facebookException).getRequestError();
                object2 = String.format(Locale.ROOT, "%d", object.getErrorCode());
                object = object.toString();
            } else {
                object2 = null;
            }
            object = LoginClient.Result.createErrorResult(this.loginClient.getPendingRequest(), null, (String)object, (String)object2);
        }
        if (!Utility.isNullOrEmpty(this.e2e)) {
            this.logWebLoginCompleted(this.e2e);
        }
        this.loginClient.completeAndValidate((LoginClient.Result)object);
    }
}
