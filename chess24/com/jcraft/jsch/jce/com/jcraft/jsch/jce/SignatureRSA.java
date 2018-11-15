/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jce;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class SignatureRSA
implements com.jcraft.jsch.SignatureRSA {
    KeyFactory keyFactory;
    Signature signature;

    @Override
    public void init() throws Exception {
        this.signature = Signature.getInstance("SHA1withRSA");
        this.keyFactory = KeyFactory.getInstance("RSA");
    }

    @Override
    public void setPrvKey(byte[] object, byte[] arrby) throws Exception {
        object = new RSAPrivateKeySpec(new BigInteger(arrby), new BigInteger((byte[])object));
        object = this.keyFactory.generatePrivate((KeySpec)object);
        this.signature.initSign((PrivateKey)object);
    }

    @Override
    public void setPubKey(byte[] object, byte[] arrby) throws Exception {
        object = new RSAPublicKeySpec(new BigInteger(arrby), new BigInteger((byte[])object));
        object = this.keyFactory.generatePublic((KeySpec)object);
        this.signature.initVerify((PublicKey)object);
    }

    @Override
    public byte[] sign() throws Exception {
        return this.signature.sign();
    }

    @Override
    public void update(byte[] arrby) throws Exception {
        this.signature.update(arrby);
    }

    @Override
    public boolean verify(byte[] arrby) throws Exception {
        byte[] arrby2 = arrby;
        if (arrby[0] == 0) {
            arrby2 = arrby;
            if (arrby[1] == 0) {
                arrby2 = arrby;
                if (arrby[2] == 0) {
                    int n = arrby[0];
                    n = 4 + (arrby[1] << 16 & 16711680 | n << 24 & -16777216 | arrby[2] << 8 & 65280 | arrby[3] & 255);
                    int n2 = n + 1;
                    n = arrby[n];
                    int n3 = n2 + 1;
                    byte by = arrby[n2];
                    n2 = n3 + 1;
                    n = by << 16 & 16711680 | n << 24 & -16777216 | arrby[n3] << 8 & 65280 | arrby[n2] & 255;
                    arrby2 = new byte[n];
                    System.arraycopy(arrby, n2 + 1, arrby2, 0, n);
                }
            }
        }
        return this.signature.verify(arrby2);
    }
}
