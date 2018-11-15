/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.login;

import com.facebook.login.LoginClient;

class LoginFragment
implements LoginClient.OnCompletedListener {
    LoginFragment() {
    }

    @Override
    public void onCompleted(LoginClient.Result result) {
        LoginFragment.this.onLoginClientCompleted(result);
    }
}
