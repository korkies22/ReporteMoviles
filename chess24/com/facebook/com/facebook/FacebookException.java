/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

public class FacebookException
extends RuntimeException {
    static final long serialVersionUID = 1L;

    public FacebookException() {
    }

    public FacebookException(String string) {
        super(string);
    }

    public FacebookException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public /* varargs */ FacebookException(String string, Object ... arrobject) {
        this(String.format(string, arrobject));
    }

    public FacebookException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
