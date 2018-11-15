/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

public interface DH {
    public void checkRange() throws Exception;

    public byte[] getE() throws Exception;

    public byte[] getK() throws Exception;

    public void init() throws Exception;

    public void setF(byte[] var1);

    public void setG(byte[] var1);

    public void setP(byte[] var1);
}
