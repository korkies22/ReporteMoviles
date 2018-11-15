/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jcraft;

import com.jcraft.jsch.jcraft.HMACMD5;

public class HMACMD596
extends HMACMD5 {
    private static final int BSIZE = 12;
    private static final String name = "hmac-md5-96";
    private final byte[] _buf16 = new byte[16];

    @Override
    public void doFinal(byte[] arrby, int n) {
        super.doFinal(this._buf16, 0);
        System.arraycopy(this._buf16, 0, arrby, n, 12);
    }

    @Override
    public int getBlockSize() {
        return 12;
    }

    @Override
    public String getName() {
        return name;
    }
}
