/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.FacebookException;

public class FacebookOperationCanceledException
extends FacebookException {
    static final long serialVersionUID = 1L;

    public FacebookOperationCanceledException() {
    }

    public FacebookOperationCanceledException(String string) {
        super(string);
    }

    public FacebookOperationCanceledException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public FacebookOperationCanceledException(Throwable throwable) {
        super(throwable);
    }
}
