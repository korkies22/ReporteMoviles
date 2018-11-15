/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

public interface MAC {
    public void doFinal(byte[] var1, int var2);

    public int getBlockSize();

    public String getName();

    public void init(byte[] var1) throws Exception;

    public void update(int var1);

    public void update(byte[] var1, int var2, int var3);
}
