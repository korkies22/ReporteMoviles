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
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.Validate;
import com.facebook.login.LoginManager;
import com.facebook.login.StartActivityDelegate;

private static class LoginManager.FragmentStartActivityDelegate
implements StartActivityDelegate {
    private final FragmentWrapper fragment;

    LoginManager.FragmentStartActivityDelegate(FragmentWrapper fragmentWrapper) {
        Validate.notNull(fragmentWrapper, "fragment");
        this.fragment = fragmentWrapper;
    }

    @Override
    public Activity getActivityContext() {
        return this.fragment.getActivity();
    }

    @Override
    public void startActivityForResult(Intent intent, int n) {
        this.fragment.startActivityForResult(intent, n);
    }
}
