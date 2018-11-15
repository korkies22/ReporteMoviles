/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.AccessToken;

public interface LoginStatusCallback {
    public void onCompleted(AccessToken var1);

    public void onError(Exception var1);

    public void onFailure();
}
