/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.login;

import android.os.Bundle;
import com.facebook.internal.PlatformServiceClient;
import com.facebook.login.LoginClient;

class GetTokenLoginMethodHandler
implements PlatformServiceClient.CompletedListener {
    final /* synthetic */ LoginClient.Request val$request;

    GetTokenLoginMethodHandler(LoginClient.Request request) {
        this.val$request = request;
    }

    @Override
    public void completed(Bundle bundle) {
        GetTokenLoginMethodHandler.this.getTokenCompleted(this.val$request, bundle);
    }
}
