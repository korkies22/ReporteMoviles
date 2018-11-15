/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

public interface Signature {
    public void init() throws Exception;

    public byte[] sign() throws Exception;

    public void update(byte[] var1) throws Exception;

    public boolean verify(byte[] var1) throws Exception;
}
