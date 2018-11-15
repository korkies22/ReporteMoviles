/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

public interface KeyPairGenRSA {
    public byte[] getC();

    public byte[] getD();

    public byte[] getE();

    public byte[] getEP();

    public byte[] getEQ();

    public byte[] getN();

    public byte[] getP();

    public byte[] getQ();

    public void init(int var1) throws Exception;
}
