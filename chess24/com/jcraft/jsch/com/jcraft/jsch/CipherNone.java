/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Cipher;

public class CipherNone
implements Cipher {
    private static final int bsize = 16;
    private static final int ivsize = 8;

    @Override
    public int getBlockSize() {
        return 16;
    }

    @Override
    public int getIVSize() {
        return 8;
    }

    @Override
    public void init(int n, byte[] arrby, byte[] arrby2) throws Exception {
    }

    @Override
    public boolean isCBC() {
        return false;
    }

    @Override
    public void update(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws Exception {
    }
}
