/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package com.facebook.login.widget;

import android.content.DialogInterface;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

class LoginButton.LoginClickListener
implements DialogInterface.OnClickListener {
    final /* synthetic */ LoginManager val$loginManager;

    LoginButton.LoginClickListener(LoginManager loginManager) {
        this.val$loginManager = loginManager;
    }

    public void onClick(DialogInterface dialogInterface, int n) {
        this.val$loginManager.logOut();
    }
}
