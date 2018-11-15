/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

public interface KeyPairGenDSA {
    public byte[] getG();

    public byte[] getP();

    public byte[] getQ();

    public byte[] getX();

    public byte[] getY();

    public void init(int var1) throws Exception;
}
