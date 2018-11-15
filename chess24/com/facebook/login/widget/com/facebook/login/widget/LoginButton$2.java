/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.login.widget;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

class LoginButton
extends AccessTokenTracker {
    LoginButton() {
    }

    @Override
    protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
        LoginButton.this.setButtonText();
    }
}
