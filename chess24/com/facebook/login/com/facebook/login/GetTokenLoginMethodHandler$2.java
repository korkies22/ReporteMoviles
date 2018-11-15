/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.login;

import android.os.Bundle;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.login.LoginClient;
import org.json.JSONException;
import org.json.JSONObject;

class GetTokenLoginMethodHandler
implements Utility.GraphMeRequestWithCacheCallback {
    final /* synthetic */ LoginClient.Request val$request;
    final /* synthetic */ Bundle val$result;

    GetTokenLoginMethodHandler(Bundle bundle, LoginClient.Request request) {
        this.val$result = bundle;
        this.val$request = request;
    }

    @Override
    public void onFailure(FacebookException facebookException) {
        GetTokenLoginMethodHandler.this.loginClient.complete(LoginClient.Result.createErrorResult(GetTokenLoginMethodHandler.this.loginClient.getPendingRequest(), "Caught exception", facebookException.getMessage()));
    }

    @Override
    public void onSuccess(JSONObject object) {
        try {
            object = object.getString("id");
            this.val$result.putString("com.facebook.platform.extra.USER_ID", (String)object);
            GetTokenLoginMethodHandler.this.onComplete(this.val$request, this.val$result);
            return;
        }
        catch (JSONException jSONException) {
            GetTokenLoginMethodHandler.this.loginClient.complete(LoginClient.Result.createErrorResult(GetTokenLoginMethodHandler.this.loginClient.getPendingRequest(), "Caught exception", jSONException.getMessage()));
            return;
        }
    }
}
