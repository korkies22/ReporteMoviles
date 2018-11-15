/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.NativeProtocol;

private static class NativeProtocol.KatanaAppInfo
extends NativeProtocol.NativeAppInfo {
    static final String KATANA_PACKAGE = "com.facebook.katana";

    private NativeProtocol.KatanaAppInfo() {
        super(null);
    }

    @Override
    protected String getLoginActivity() {
        return NativeProtocol.FACEBOOK_PROXY_AUTH_ACTIVITY;
    }

    @Override
    protected String getPackage() {
        return KATANA_PACKAGE;
    }
}
