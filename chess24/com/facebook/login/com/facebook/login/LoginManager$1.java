/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.facebook.login;

import android.content.Intent;
import com.facebook.FacebookCallback;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginResult;

class LoginManager
implements CallbackManagerImpl.Callback {
    final /* synthetic */ FacebookCallback val$callback;

    LoginManager(FacebookCallback facebookCallback) {
        this.val$callback = facebookCallback;
    }

    @Override
    public boolean onActivityResult(int n, Intent intent) {
        return LoginManager.this.onActivityResult(n, intent, this.val$callback);
    }
}
