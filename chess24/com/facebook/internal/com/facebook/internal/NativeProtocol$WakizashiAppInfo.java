/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.NativeProtocol;

private static class NativeProtocol.WakizashiAppInfo
extends NativeProtocol.NativeAppInfo {
    static final String WAKIZASHI_PACKAGE = "com.facebook.wakizashi";

    private NativeProtocol.WakizashiAppInfo() {
        super(null);
    }

    @Override
    protected String getLoginActivity() {
        return NativeProtocol.FACEBOOK_PROXY_AUTH_ACTIVITY;
    }

    @Override
    protected String getPackage() {
        return WAKIZASHI_PACKAGE;
    }
}
