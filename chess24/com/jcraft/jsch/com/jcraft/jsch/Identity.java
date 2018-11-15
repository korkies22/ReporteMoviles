/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.JSchException;

public interface Identity {
    public void clear();

    public boolean decrypt();

    public String getAlgName();

    public String getName();

    public byte[] getPublicKeyBlob();

    public byte[] getSignature(byte[] var1);

    public boolean isEncrypted();

    public boolean setPassphrase(byte[] var1) throws JSchException;
}
