/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.FacebookException;

public class FacebookAuthorizationException
extends FacebookException {
    static final long serialVersionUID = 1L;

    public FacebookAuthorizationException() {
    }

    public FacebookAuthorizationException(String string) {
        super(string);
    }

    public FacebookAuthorizationException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public FacebookAuthorizationException(Throwable throwable) {
        super(throwable);
    }
}
