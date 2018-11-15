/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

public interface HASH {
    public byte[] digest() throws Exception;

    public int getBlockSize();

    public void init() throws Exception;

    public void update(byte[] var1, int var2, int var3) throws Exception;
}
