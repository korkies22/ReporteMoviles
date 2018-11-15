/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

public interface Cipher {
    public static final int DECRYPT_MODE = 1;
    public static final int ENCRYPT_MODE = 0;

    public int getBlockSize();

    public int getIVSize();

    public void init(int var1, byte[] var2, byte[] var3) throws Exception;

    public boolean isCBC();

    public void update(byte[] var1, int var2, int var3, byte[] var4, int var5) throws Exception;
}
