/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.facebook.login;

import android.content.Intent;
import com.facebook.internal.CallbackManagerImpl;

class LoginManager
implements CallbackManagerImpl.Callback {
    LoginManager() {
    }

    @Override
    public boolean onActivityResult(int n, Intent intent) {
        return LoginManager.this.onActivityResult(n, intent);
    }
}
