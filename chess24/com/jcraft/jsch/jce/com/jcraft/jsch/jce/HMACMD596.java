/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jce;

import com.jcraft.jsch.jce.HMACMD5;

public class HMACMD596
extends HMACMD5 {
    private final byte[] _buf16 = new byte[16];

    public HMACMD596() {
        this.name = "hmac-md5-96";
    }

    @Override
    public void doFinal(byte[] arrby, int n) {
        super.doFinal(this._buf16, 0);
        System.arraycopy(this._buf16, 0, arrby, n, 12);
    }

    @Override
    public int getBlockSize() {
        return 12;
    }
}
