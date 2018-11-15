/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

public class SftpException
extends Exception {
    private Throwable cause = null;
    public int id;

    public SftpException(int n, String string) {
        super(string);
        this.id = n;
    }

    public SftpException(int n, String string, Throwable throwable) {
        super(string);
        this.id = n;
        this.cause = throwable;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.id);
        stringBuilder.append(": ");
        stringBuilder.append(this.getMessage());
        return stringBuilder.toString();
    }
}
