/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.facebook.login;

import android.content.Context;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginLogger;
import com.facebook.login.LoginManager;

private static class LoginManager.LoginLoggerHolder {
    private static LoginLogger logger;

    private LoginManager.LoginLoggerHolder() {
    }

    static /* synthetic */ LoginLogger access$000(Context context) {
        return LoginManager.LoginLoggerHolder.getLogger(context);
    }

    private static LoginLogger getLogger(Context object) {
        synchronized (LoginManager.LoginLoggerHolder.class) {
            if (object == null) {
                object = FacebookSdk.getApplicationContext();
            }
            if (object == null) {
                return null;
            }
            try {
                if (logger == null) {
                    logger = new LoginLogger((Context)object, FacebookSdk.getApplicationId());
                }
                object = logger;
                return object;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
            }
        }
    }
}
