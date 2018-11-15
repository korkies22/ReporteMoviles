/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package com.facebook.login.widget;

import android.app.Activity;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;

class LoginButton
implements Runnable {
    final /* synthetic */ String val$appId;

    LoginButton(String string) {
        this.val$appId = string;
    }

    @Override
    public void run() {
        final FetchedAppSettings fetchedAppSettings = FetchedAppSettingsManager.queryAppSettings(this.val$appId, false);
        LoginButton.this.getActivity().runOnUiThread(new Runnable(){

            @Override
            public void run() {
                LoginButton.this.showToolTipPerSettings(fetchedAppSettings);
            }
        });
    }

}
