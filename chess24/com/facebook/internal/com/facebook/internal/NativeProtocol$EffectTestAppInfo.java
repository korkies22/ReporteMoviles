/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.NativeProtocol;

private static class NativeProtocol.EffectTestAppInfo
extends NativeProtocol.NativeAppInfo {
    static final String EFFECT_TEST_APP_PACKAGE = "com.facebook.arstudio.player";

    private NativeProtocol.EffectTestAppInfo() {
        super(null);
    }

    @Override
    protected String getLoginActivity() {
        return null;
    }

    @Override
    protected String getPackage() {
        return EFFECT_TEST_APP_PACKAGE;
    }
}
