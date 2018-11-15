/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Identity;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.KeyPair;

class IdentityFile
implements Identity {
    private String identity;
    private JSch jsch;
    private KeyPair kpair;

    private IdentityFile(JSch jSch, String string, KeyPair keyPair) throws JSchException {
        this.jsch = jSch;
        this.identity = string;
        this.kpair = keyPair;
    }

    static IdentityFile newInstance(String string, String string2, JSch jSch) throws JSchException {
        return new IdentityFile(jSch, string, KeyPair.load(jSch, string, string2));
    }

    static IdentityFile newInstance(String string, byte[] arrby, byte[] arrby2, JSch jSch) throws JSchException {
        return new IdentityFile(jSch, string, KeyPair.load(jSch, arrby, arrby2));
    }

    @Override
    public void clear() {
        this.kpair.dispose();
        this.kpair = null;
    }

    @Override
    public boolean decrypt() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public String getAlgName() {
        return new String(this.kpair.getKeyTypeName());
    }

    public KeyPair getKeyPair() {
        return this.kpair;
    }

    @Override
    public String getName() {
        return this.identity;
    }

    @Override
    public byte[] getPublicKeyBlob() {
        return this.kpair.getPublicKeyBlob();
    }

    @Override
    public byte[] getSignature(byte[] arrby) {
        return this.kpair.getSignature(arrby);
    }

    @Override
    public boolean isEncrypted() {
        return this.kpair.isEncrypted();
    }

    @Override
    public boolean setPassphrase(byte[] arrby) throws JSchException {
        return this.kpair.decrypt(arrby);
    }
}
