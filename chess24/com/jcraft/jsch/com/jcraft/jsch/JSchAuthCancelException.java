/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.JSchException;

class JSchAuthCancelException
extends JSchException {
    String method;

    JSchAuthCancelException() {
    }

    JSchAuthCancelException(String string) {
        super(string);
        this.method = string;
    }

    public String getMethod() {
        return this.method;
    }
}
