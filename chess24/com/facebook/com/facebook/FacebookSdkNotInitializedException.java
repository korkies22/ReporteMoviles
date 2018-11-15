/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.FacebookException;

public class FacebookSdkNotInitializedException
extends FacebookException {
    static final long serialVersionUID = 1L;

    public FacebookSdkNotInitializedException() {
    }

    public FacebookSdkNotInitializedException(String string) {
        super(string);
    }

    public FacebookSdkNotInitializedException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public FacebookSdkNotInitializedException(Throwable throwable) {
        super(throwable);
    }
}
