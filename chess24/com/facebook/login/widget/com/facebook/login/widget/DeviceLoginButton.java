/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.util.AttributeSet
 */
package com.facebook.login.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import com.facebook.login.DefaultAudience;
import com.facebook.login.DeviceLoginManager;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

public class DeviceLoginButton
extends LoginButton {
    private Uri deviceRedirectUri;

    public DeviceLoginButton(Context context) {
        super(context);
    }

    public DeviceLoginButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DeviceLoginButton(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public Uri getDeviceRedirectUri() {
        return this.deviceRedirectUri;
    }

    @Override
    protected LoginButton.LoginClickListener getNewLoginClickListener() {
        return new DeviceLoginClickListener();
    }

    public void setDeviceRedirectUri(Uri uri) {
        this.deviceRedirectUri = uri;
    }

    private class DeviceLoginClickListener
    extends LoginButton.LoginClickListener {
        private DeviceLoginClickListener() {
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

}
