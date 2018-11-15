/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.facebook.login.widget;

import android.net.Uri;
import com.facebook.login.DefaultAudience;
import com.facebook.login.DeviceLoginManager;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.DeviceLoginButton;
import com.facebook.login.widget.LoginButton;

private class DeviceLoginButton.DeviceLoginClickListener
extends LoginButton.LoginClickListener {
    private DeviceLoginButton.DeviceLoginClickListener() {
        super(DeviceLoginButton.this);
    }

    @Override
    protected LoginManager getLoginManager() {
        DeviceLoginManager deviceLoginManager = DeviceLoginManager.getInstance();
        deviceLoginManager.setDefaultAudience(DeviceLoginButton.this.getDefaultAudience());
        deviceLoginManager.setLoginBehavior(LoginBehavior.DEVICE_AUTH);
        deviceLoginManager.setDeviceRedirectUri(DeviceLoginButton.this.getDeviceRedirectUri());
        return deviceLoginManager;
    }
}
