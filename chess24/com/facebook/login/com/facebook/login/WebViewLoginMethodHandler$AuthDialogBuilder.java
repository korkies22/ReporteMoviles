/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 */
package com.facebook.login;

import android.content.Context;
import android.os.Bundle;
import com.facebook.internal.WebDialog;
import com.facebook.login.WebViewLoginMethodHandler;

static class WebViewLoginMethodHandler.AuthDialogBuilder
extends WebDialog.Builder {
    private static final String OAUTH_DIALOG = "oauth";
    static final String REDIRECT_URI = "fbconnect://success";
    private String e2e;
    private boolean isRerequest;

    public WebViewLoginMethodHandler.AuthDialogBuilder(Context context, String string, Bundle bundle) {
        super(context, string, OAUTH_DIALOG, bundle);
    }

    @Override
    public WebDialog build() {
        Bundle bundle = this.getParameters();
        bundle.putString("redirect_uri", REDIRECT_URI);
        bundle.putString("client_id", this.getApplicationId());
        bundle.putString("e2e", this.e2e);
        bundle.putString("response_type", "token,signed_request");
        bundle.putString("return_scopes", "true");
        bundle.putString("auth_type", "rerequest");
        return WebDialog.newInstance(this.getContext(), OAUTH_DIALOG, bundle, this.getTheme(), this.getListener());
    }

    public WebViewLoginMethodHandler.AuthDialogBuilder setE2E(String string) {
        this.e2e = string;
        return this;
    }

    public WebViewLoginMethodHandler.AuthDialogBuilder setIsRerequest(boolean bl) {
        this.isRerequest = bl;
        return this;
    }
}
