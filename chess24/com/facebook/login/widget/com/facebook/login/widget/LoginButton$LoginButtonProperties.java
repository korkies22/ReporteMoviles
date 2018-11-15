/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.login.widget;

import com.facebook.internal.LoginAuthorizationType;
import com.facebook.internal.Utility;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.widget.LoginButton;
import java.util.Collections;
import java.util.List;

static class LoginButton.LoginButtonProperties {
    private LoginAuthorizationType authorizationType = null;
    private DefaultAudience defaultAudience = DefaultAudience.FRIENDS;
    private LoginBehavior loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK;
    private List<String> permissions = Collections.emptyList();

    LoginButton.LoginButtonProperties() {
    }

    static /* synthetic */ LoginAuthorizationType access$600(LoginButton.LoginButtonProperties loginButtonProperties) {
        return loginButtonProperties.authorizationType;
    }

    static /* synthetic */ List access$700(LoginButton.LoginButtonProperties loginButtonProperties) {
        return loginButtonProperties.permissions;
    }

    public void clearPermissions() {
        this.permissions = null;
        this.authorizationType = null;
    }

    public DefaultAudience getDefaultAudience() {
        return this.defaultAudience;
    }

    public LoginBehavior getLoginBehavior() {
        return this.loginBehavior;
    }

    List<String> getPermissions() {
        return this.permissions;
    }

    public void setDefaultAudience(DefaultAudience defaultAudience) {
        this.defaultAudience = defaultAudience;
    }

    public void setLoginBehavior(LoginBehavior loginBehavior) {
        this.loginBehavior = loginBehavior;
    }

    public void setPublishPermissions(List<String> list) {
        if (LoginAuthorizationType.READ.equals((Object)this.authorizationType)) {
            throw new UnsupportedOperationException("Cannot call setPublishPermissions after setReadPermissions has been called.");
        }
        if (Utility.isNullOrEmpty(list)) {
            throw new IllegalArgumentException("Permissions for publish actions cannot be null or empty.");
        }
        this.permissions = list;
        this.authorizationType = LoginAuthorizationType.PUBLISH;
    }

    public void setReadPermissions(List<String> list) {
        if (LoginAuthorizationType.PUBLISH.equals((Object)this.authorizationType)) {
            throw new UnsupportedOperationException("Cannot call setReadPermissions after setPublishPermissions has been called.");
        }
        this.permissions = list;
        this.authorizationType = LoginAuthorizationType.READ;
    }
}
