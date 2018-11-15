/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.facebook.login;

import android.view.View;
import com.facebook.login.LoginClient;

class LoginFragment
implements LoginClient.BackgroundProcessingListener {
    final /* synthetic */ View val$progressBar;

    LoginFragment(View view) {
        this.val$progressBar = view;
    }

    @Override
    public void onBackgroundProcessingStarted() {
        this.val$progressBar.setVisibility(0);
    }

    @Override
    public void onBackgroundProcessingStopped() {
        this.val$progressBar.setVisibility(8);
    }
}
