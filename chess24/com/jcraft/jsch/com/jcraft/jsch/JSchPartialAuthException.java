/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.JSchException;

class JSchPartialAuthException
extends JSchException {
    String methods;

    public JSchPartialAuthException() {
    }

    public JSchPartialAuthException(String string) {
        super(string);
        this.methods = string;
    }

    public String getMethods() {
        return this.methods;
    }
}
