/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.login;

import com.facebook.login.LoginClient;

static enum LoginClient.Result.Code {
    SUCCESS("success"),
    CANCEL("cancel"),
    ERROR("error");
    
    private final String loggingValue;

    private LoginClient.Result.Code(String string2) {
        this.loggingValue = string2;
    }

    String getLoggingValue() {
        return this.loggingValue;
    }
}
