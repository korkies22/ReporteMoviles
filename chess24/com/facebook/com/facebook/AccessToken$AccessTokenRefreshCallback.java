/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.AccessToken;
import com.facebook.FacebookException;

public static interface AccessToken.AccessTokenRefreshCallback {
    public void OnTokenRefreshFailed(FacebookException var1);

    public void OnTokenRefreshed(AccessToken var1);
}
