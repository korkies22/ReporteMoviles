/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jcraft;

import java.security.MessageDigest;

class HMAC {
    private static final int B = 64;
    private int bsize = 0;
    private byte[] k_ipad = null;
    private byte[] k_opad = null;
    private MessageDigest md = null;
    private final byte[] tmp = new byte[4];

    HMAC() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void doFinal(byte[] arrby, int n) {
        byte[] arrby2 = this.md.digest();
        this.md.update(this.k_opad, 0, 64);
        this.md.update(arrby2, 0, this.bsize);
        try {
            this.md.digest(arrby, n, this.bsize);
        }
        catch (Exception exception) {}
        this.md.update(this.k_ipad, 0, 64);
    }

    public int getBlockSize() {
        return this.bsize;
    }

    public void init(byte[] arrby) throws Exception {
        this.md.reset();
        byte[] arrby2 = arrby;
        if (arrby.length > this.bsize) {
            arrby2 = new byte[this.bsize];
            System.arraycopy(arrby, 0, arrby2, 0, this.bsize);
        }
        arrby = arrby2;
        if (arrby2.length > 64) {
            this.md.update(arrby2, 0, arrby2.length);
            arrby = this.md.digest();
        }
        this.k_ipad = new byte[64];
        System.arraycopy(arrby, 0, this.k_ipad, 0, arrby.length);
        this.k_opad = new byte[64];
        System.arraycopy(arrby, 0, this.k_opad, 0, arrby.length);
        for (int i = 0; i < 64; ++i) {
            arrby = this.k_ipad;
            arrby[i] = (byte)(arrby[i] ^ 54);
            arrby = this.k_opad;
            arrby[i] = (byte)(arrby[i] ^ 92);
        }
        this.md.update(this.k_ipad, 0, 64);
    }

    protected void setH(MessageDigest messageDigest) {
        this.md = messageDigest;
        this.bsize = messageDigest.getDigestLength();
    }

    public void update(int n) {
        this.tmp[0] = (byte)(n >>> 24);
        this.tmp[1] = (byte)(n >>> 16);
        this.tmp[2] = (byte)(n >>> 8);
        this.tmp[3] = (byte)n;
        this.update(this.tmp, 0, 4);
    }

    public void update(byte[] arrby, int n, int n2) {
        this.md.update(arrby, n, n2);
    }
}
