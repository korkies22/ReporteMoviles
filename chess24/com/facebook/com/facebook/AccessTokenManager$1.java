/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.AccessToken;

class AccessTokenManager
implements Runnable {
    final /* synthetic */ AccessToken.AccessTokenRefreshCallback val$callback;

    AccessTokenManager(AccessToken.AccessTokenRefreshCallback accessTokenRefreshCallback) {
        this.val$callback = accessTokenRefreshCallback;
    }

    @Override
    public void run() {
        AccessTokenManager.this.refreshCurrentAccessTokenImpl(this.val$callback);
    }
}
