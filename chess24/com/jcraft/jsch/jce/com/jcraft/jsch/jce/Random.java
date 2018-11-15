/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jce;

import java.security.SecureRandom;

public class Random
implements com.jcraft.jsch.Random {
    private SecureRandom random = new SecureRandom();
    private byte[] tmp = new byte[16];

    @Override
    public void fill(byte[] arrby, int n, int n2) {
        if (n2 > this.tmp.length) {
            this.tmp = new byte[n2];
        }
        this.random.nextBytes(this.tmp);
        System.arraycopy(this.tmp, 0, arrby, n, n2);
    }
}
