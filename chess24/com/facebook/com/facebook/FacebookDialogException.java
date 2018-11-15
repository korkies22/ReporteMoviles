/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.FacebookException;

public class FacebookDialogException
extends FacebookException {
    static final long serialVersionUID = 1L;
    private int errorCode;
    private String failingUrl;

    public FacebookDialogException(String string, int n, String string2) {
        super(string);
        this.errorCode = n;
        this.failingUrl = string2;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getFailingUrl() {
        return this.failingUrl;
    }

    @Override
    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{FacebookDialogException: ");
        stringBuilder.append("errorCode: ");
        stringBuilder.append(this.getErrorCode());
        stringBuilder.append(", message: ");
        stringBuilder.append(this.getMessage());
        stringBuilder.append(", url: ");
        stringBuilder.append(this.getFailingUrl());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
