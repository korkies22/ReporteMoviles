/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ActivityNotFoundException
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcel
 */
package com.facebook.login;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.login.LoginClient;
import com.facebook.login.LoginMethodHandler;
import java.util.Collection;
import java.util.Set;

abstract class NativeAppLoginMethodHandler
extends LoginMethodHandler {
    NativeAppLoginMethodHandler(Parcel parcel) {
        super(parcel);
    }

    NativeAppLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    private String getError(Bundle bundle) {
        String string;
        String string2 = string = bundle.getString("error");
        if (string == null) {
            string2 = bundle.getString("error_type");
        }
        return string2;
    }

    private String getErrorMessage(Bundle bundle) {
        String string;
        String string2 = string = bundle.getString("error_message");
        if (string == null) {
            string2 = bundle.getString("error_description");
        }
        return string2;
    }

    private LoginClient.Result handleResultCancel(LoginClient.Request request, Intent object) {
        Bundle bundle = object.getExtras();
        String string = this.getError(bundle);
        object = bundle.get("error_code") != null ? bundle.get("error_code").toString() : null;
        if ("CONNECTION_FAILURE".equals(object)) {
            return LoginClient.Result.createErrorResult(request, string, this.getErrorMessage(bundle), (String)object);
        }
        return LoginClient.Result.createCancelResult(request, string);
    }

    private LoginClient.Result handleResultOk(LoginClient.Request request, Intent object) {
        Bundle bundle = object.getExtras();
        String string = this.getError(bundle);
        object = bundle.get("error_code") != null ? bundle.get("error_code").toString() : null;
        String string2 = this.getErrorMessage(bundle);
        String string3 = bundle.getString("e2e");
        if (!Utility.isNullOrEmpty(string3)) {
            this.logWebLoginCompleted(string3);
        }
        if (string == null && object == null && string2 == null) {
            try {
                object = LoginClient.Result.createTokenResult(request, NativeAppLoginMethodHandler.createAccessTokenFromWebBundle(request.getPermissions(), bundle, AccessTokenSource.FACEBOOK_APPLICATION_WEB, request.getApplicationId()));
                return object;
            }
            catch (FacebookException facebookException) {
                return LoginClient.Result.createErrorResult(request, null, facebookException.getMessage());
            }
        }
        if (ServerProtocol.errorsProxyAuthDisabled.contains(string)) {
            return null;
        }
        if (ServerProtocol.errorsUserCanceled.contains(string)) {
            return LoginClient.Result.createCancelResult(request, null);
        }
        return LoginClient.Result.createErrorResult(request, string, string2, (String)object);
    }

    @Override
    boolean onActivityResult(int n, int n2, Intent object) {
        LoginClient.Request request = this.loginClient.getPendingRequest();
        object = object == null ? LoginClient.Result.createCancelResult(request, "Operation canceled") : (n2 == 0 ? this.handleResultCancel(request, (Intent)object) : (n2 != -1 ? LoginClient.Result.createErrorResult(request, "Unexpected resultCode from authorization.", null) : this.handleResultOk(request, (Intent)object)));
        if (object != null) {
            this.loginClient.completeAndValidate((LoginClient.Result)object);
        } else {
            this.loginClient.tryNextHandler();
        }
        return true;
    }

    @Override
    abstract boolean tryAuthorize(LoginClient.Request var1);

    protected boolean tryIntent(Intent intent, int n) {
        if (intent == null) {
            return false;
        }
        try {
            this.loginClient.getFragment().startActivityForResult(intent, n);
            return true;
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            return false;
        }
    }
}
