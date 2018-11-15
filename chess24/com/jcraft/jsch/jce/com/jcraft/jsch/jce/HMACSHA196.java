/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jce;

import com.jcraft.jsch.jce.HMACSHA1;

public class HMACSHA196
extends HMACSHA1 {
    private final byte[] _buf20 = new byte[20];

    public HMACSHA196() {
        this.name = "hmac-sha1-96";
    }

    @Override
    public void doFinal(byte[] arrby, int n) {
        super.doFinal(this._buf20, 0);
        System.arraycopy(this._buf20, 0, arrby, n, 12);
    }

    @Override
    public int getBlockSize() {
        return 12;
    }
}
