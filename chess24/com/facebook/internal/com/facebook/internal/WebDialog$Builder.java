/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 */
package com.facebook.internal;

import android.content.Context;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.internal.WebDialog;

public static class WebDialog.Builder {
    private AccessToken accessToken;
    private String action;
    private String applicationId;
    private Context context;
    private WebDialog.OnCompleteListener listener;
    private Bundle parameters;
    private int theme;

    public WebDialog.Builder(Context context, String string, Bundle bundle) {
        this.accessToken = AccessToken.getCurrentAccessToken();
        if (this.accessToken == null) {
            String string2 = Utility.getMetadataApplicationId(context);
            if (string2 != null) {
                this.applicationId = string2;
            } else {
                throw new FacebookException("Attempted to create a builder without a valid access token or a valid default Application ID.");
            }
        }
        this.finishInit(context, string, bundle);
    }

    public WebDialog.Builder(Context context, String string, String string2, Bundle bundle) {
        String string3 = string;
        if (string == null) {
            string3 = Utility.getMetadataApplicationId(context);
        }
        Validate.notNullOrEmpty(string3, "applicationId");
        this.applicationId = string3;
        this.finishInit(context, string2, bundle);
    }

    private void finishInit(Context context, String string, Bundle bundle) {
        this.context = context;
        this.action = string;
        if (bundle != null) {
            this.parameters = bundle;
            return;
        }
        this.parameters = new Bundle();
    }

    public WebDialog build() {
        if (this.accessToken != null) {
            this.parameters.putString("app_id", this.accessToken.getApplicationId());
            this.parameters.putString("access_token", this.accessToken.getToken());
        } else {
            this.parameters.putString("app_id", this.applicationId);
        }
        return WebDialog.newInstance(this.context, this.action, this.parameters, this.theme, this.listener);
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public Context getContext() {
        return this.context;
    }

    public WebDialog.OnCompleteListener getListener() {
        return this.listener;
    }

    public Bundle getParameters() {
        return this.parameters;
    }

    public int getTheme() {
        return this.theme;
    }

    public WebDialog.Builder setOnCompleteListener(WebDialog.OnCompleteListener onCompleteListener) {
        this.listener = onCompleteListener;
        return this;
    }

    public WebDialog.Builder setTheme(int n) {
        this.theme = n;
        return this;
    }
}
