/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.facebook.login.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.LoginAuthorizationType;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.R;
import com.facebook.login.widget.LoginButton;
import java.util.Collection;
import java.util.List;

protected class LoginButton.LoginClickListener
implements View.OnClickListener {
    protected LoginButton.LoginClickListener() {
    }

    protected LoginManager getLoginManager() {
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.setDefaultAudience(LoginButton.this.getDefaultAudience());
        loginManager.setLoginBehavior(LoginButton.this.getLoginBehavior());
        return loginManager;
    }

    public void onClick(View object) {
        LoginButton.this.callExternalOnClickListener(object);
        object = AccessToken.getCurrentAccessToken();
        if (object != null) {
            this.performLogout(LoginButton.this.getContext());
        } else {
            this.performLogin();
        }
        AppEventsLogger appEventsLogger = AppEventsLogger.newLogger(LoginButton.this.getContext());
        Bundle bundle = new Bundle();
        int n = object != null ? 0 : 1;
        bundle.putInt("logging_in", n);
        appEventsLogger.logSdkEvent(LoginButton.this.loginLogoutEventName, null, bundle);
    }

    protected void performLogin() {
        LoginManager loginManager = this.getLoginManager();
        if (LoginAuthorizationType.PUBLISH.equals((Object)LoginButton.this.properties.authorizationType)) {
            if (LoginButton.this.getFragment() != null) {
                loginManager.logInWithPublishPermissions(LoginButton.this.getFragment(), (Collection<String>)LoginButton.this.properties.permissions);
                return;
            }
            if (LoginButton.this.getNativeFragment() != null) {
                loginManager.logInWithPublishPermissions(LoginButton.this.getNativeFragment(), (Collection<String>)LoginButton.this.properties.permissions);
                return;
            }
            loginManager.logInWithPublishPermissions(LoginButton.this.getActivity(), (Collection<String>)LoginButton.this.properties.permissions);
            return;
        }
        if (LoginButton.this.getFragment() != null) {
            loginManager.logInWithReadPermissions(LoginButton.this.getFragment(), (Collection<String>)LoginButton.this.properties.permissions);
            return;
        }
        if (LoginButton.this.getNativeFragment() != null) {
            loginManager.logInWithReadPermissions(LoginButton.this.getNativeFragment(), (Collection<String>)LoginButton.this.properties.permissions);
            return;
        }
        loginManager.logInWithReadPermissions(LoginButton.this.getActivity(), (Collection<String>)LoginButton.this.properties.permissions);
    }

    protected void performLogout(Context context) {
        final LoginManager loginManager = this.getLoginManager();
        if (LoginButton.this.confirmLogout) {
            String string2 = LoginButton.this.getResources().getString(R.string.com_facebook_loginview_log_out_action);
            String string3 = LoginButton.this.getResources().getString(R.string.com_facebook_loginview_cancel_action);
            Object object = Profile.getCurrentProfile();
            object = object != null && object.getName() != null ? String.format(LoginButton.this.getResources().getString(R.string.com_facebook_loginview_logged_in_as), object.getName()) : LoginButton.this.getResources().getString(R.string.com_facebook_loginview_logged_in_using_facebook);
            context = new AlertDialog.Builder(context);
            context.setMessage((CharSequence)object).setCancelable(true).setPositiveButton((CharSequence)string2, new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    loginManager.logOut();
                }
            }).setNegativeButton((CharSequence)string3, null);
            context.create().show();
            return;
        }
        loginManager.logOut();
    }

}
