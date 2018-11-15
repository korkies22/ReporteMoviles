/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.NativeProtocol;

private static class NativeProtocol.FBLiteAppInfo
extends NativeProtocol.NativeAppInfo {
    static final String FACEBOOK_LITE_ACTIVITY = "com.facebook.lite.platform.LoginGDPDialogActivity";
    static final String FBLITE_PACKAGE = "com.facebook.lite";

    private NativeProtocol.FBLiteAppInfo() {
        super(null);
    }

    @Override
    protected String getLoginActivity() {
        return FACEBOOK_LITE_ACTIVITY;
    }

    @Override
    protected String getPackage() {
        return FBLITE_PACKAGE;
    }
}
