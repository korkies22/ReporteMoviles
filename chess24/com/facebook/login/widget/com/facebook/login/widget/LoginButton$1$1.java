/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.login.widget;

import com.facebook.internal.FetchedAppSettings;
import com.facebook.login.widget.LoginButton;

class LoginButton
implements Runnable {
    final /* synthetic */ FetchedAppSettings val$settings;

    LoginButton(FetchedAppSettings fetchedAppSettings) {
        this.val$settings = fetchedAppSettings;
    }

    @Override
    public void run() {
        1.this.this$0.showToolTipPerSettings(this.val$settings);
    }
}
