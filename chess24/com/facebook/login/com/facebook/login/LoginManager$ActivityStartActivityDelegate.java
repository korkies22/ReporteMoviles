/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 */
package com.facebook.login;

import android.app.Activity;
import android.content.Intent;
import com.facebook.internal.Validate;
import com.facebook.login.LoginManager;
import com.facebook.login.StartActivityDelegate;

private static class LoginManager.ActivityStartActivityDelegate
implements StartActivityDelegate {
    private final Activity activity;

    LoginManager.ActivityStartActivityDelegate(Activity activity) {
        Validate.notNull((Object)activity, "activity");
        this.activity = activity;
    }

    @Override
    public Activity getActivityContext() {
        return this.activity;
    }

    @Override
    public void startActivityForResult(Intent intent, int n) {
        this.activity.startActivityForResult(intent, n);
    }
}
