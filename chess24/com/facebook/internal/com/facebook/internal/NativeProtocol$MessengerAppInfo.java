/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.NativeProtocol;

private static class NativeProtocol.MessengerAppInfo
extends NativeProtocol.NativeAppInfo {
    static final String MESSENGER_PACKAGE = "com.facebook.orca";

    private NativeProtocol.MessengerAppInfo() {
        super(null);
    }

    @Override
    protected String getLoginActivity() {
        return null;
    }

    @Override
    protected String getPackage() {
        return MESSENGER_PACKAGE;
    }
}
