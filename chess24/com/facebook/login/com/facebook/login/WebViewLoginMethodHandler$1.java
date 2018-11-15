/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.login;

import android.os.Bundle;
import com.facebook.FacebookException;
import com.facebook.internal.WebDialog;
import com.facebook.login.LoginClient;

class WebViewLoginMethodHandler
implements WebDialog.OnCompleteListener {
    final /* synthetic */ LoginClient.Request val$request;

    WebViewLoginMethodHandler(LoginClient.Request request) {
        this.val$request = request;
    }

    @Override
    public void onComplete(Bundle bundle, FacebookException facebookException) {
        WebViewLoginMethodHandler.this.onWebDialogComplete(this.val$request, bundle, facebookException);
    }
}
