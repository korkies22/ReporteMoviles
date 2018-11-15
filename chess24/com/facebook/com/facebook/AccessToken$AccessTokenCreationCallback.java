/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.AccessToken;
import com.facebook.FacebookException;

public static interface AccessToken.AccessTokenCreationCallback {
    public void onError(FacebookException var1);

    public void onSuccess(AccessToken var1);
}
