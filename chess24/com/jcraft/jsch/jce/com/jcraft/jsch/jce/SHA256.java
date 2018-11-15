/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jce;

import com.jcraft.jsch.HASH;
import java.io.PrintStream;
import java.security.MessageDigest;

public class SHA256
implements HASH {
    MessageDigest md;

    @Override
    public byte[] digest() throws Exception {
        return this.md.digest();
    }

    @Override
    public int getBlockSize() {
        return 32;
    }

    @Override
    public void init() throws Exception {
        try {
            this.md = MessageDigest.getInstance("SHA-256");
            return;
        }
        catch (Exception exception) {
            System.err.println(exception);
            return;
        }
    }

    @Override
    public void update(byte[] arrby, int n, int n2) throws Exception {
        this.md.update(arrby, n, n2);
    }
}
