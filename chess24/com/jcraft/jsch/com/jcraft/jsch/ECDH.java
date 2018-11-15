/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

public interface ECDH {
    public byte[] getQ() throws Exception;

    public byte[] getSecret(byte[] var1, byte[] var2) throws Exception;

    public void init(int var1) throws Exception;

    public boolean validate(byte[] var1, byte[] var2) throws Exception;
}
