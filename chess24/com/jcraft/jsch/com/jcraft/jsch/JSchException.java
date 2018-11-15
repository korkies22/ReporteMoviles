/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

public class JSchException
extends Exception {
    private Throwable cause = null;

    public JSchException() {
    }

    public JSchException(String string) {
        super(string);
    }

    public JSchException(String string, Throwable throwable) {
        super(string);
        this.cause = throwable;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }
}
