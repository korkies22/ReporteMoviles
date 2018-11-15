/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jce;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.KeySpec;

public class SignatureDSA
implements com.jcraft.jsch.SignatureDSA {
    KeyFactory keyFactory;
    Signature signature;

    @Override
    public void init() throws Exception {
        this.signature = Signature.getInstance("SHA1withDSA");
        this.keyFactory = KeyFactory.getInstance("DSA");
    }

    @Override
    public void setPrvKey(byte[] object, byte[] arrby, byte[] arrby2, byte[] arrby3) throws Exception {
        object = new DSAPrivateKeySpec(new BigInteger((byte[])object), new BigInteger(arrby), new BigInteger(arrby2), new BigInteger(arrby3));
        object = this.keyFactory.generatePrivate((KeySpec)object);
        this.signature.initSign((PrivateKey)object);
    }

    @Override
    public void setPubKey(byte[] object, byte[] arrby, byte[] arrby2, byte[] arrby3) throws Exception {
        object = new DSAPublicKeySpec(new BigInteger((byte[])object), new BigInteger(arrby), new BigInteger(arrby2), new BigInteger(arrby3));
        object = this.keyFactory.generatePublic((KeySpec)object);
        this.signature.initVerify((PublicKey)object);
    }

    @Override
    public byte[] sign() throws Exception {
        byte[] arrby = this.signature.sign();
        int n = arrby[3] & 255;
        byte[] arrby2 = new byte[n];
        int n2 = 1;
        int n3 = 20;
        System.arraycopy(arrby, 4, arrby2, 0, arrby2.length);
        n = 4 + n + 1;
        byte[] arrby3 = new byte[arrby[n] & 255];
        System.arraycopy(arrby, n + 1, arrby3, 0, arrby3.length);
        arrby = new byte[40];
        n = arrby2.length > 20 ? 1 : 0;
        int n4 = arrby2.length > 20 ? 0 : 20 - arrby2.length;
        int n5 = arrby2.length > 20 ? 20 : arrby2.length;
        System.arraycopy(arrby2, n, arrby, n4, n5);
        n = arrby3.length > 20 ? n2 : 0;
        n4 = arrby3.length > 20 ? 20 : 40 - arrby3.length;
        n5 = arrby3.length > 20 ? n3 : arrby3.length;
        System.arraycopy(arrby3, n, arrby, n4, n5);
        return arrby;
    }

    @Override
    public void update(byte[] arrby) throws Exception {
        this.signature.update(arrby);
    }

    @Override
    public boolean verify(byte[] arrby) throws Exception {
        int n;
        int n2;
        byte[] arrby2 = arrby;
        if (arrby[0] == 0) {
            arrby2 = arrby;
            if (arrby[1] == 0) {
                arrby2 = arrby;
                if (arrby[2] == 0) {
                    n2 = (arrby[0] << 24 & -16777216 | arrby[1] << 16 & 16711680 | arrby[2] << 8 & 65280 | arrby[3] & 255) + 4;
                    int n3 = n2 + 1;
                    n2 = arrby[n2];
                    n = n3 + 1;
                    byte by = arrby[n3];
                    n3 = n + 1;
                    n2 = n2 << 24 & -16777216 | by << 16 & 16711680 | arrby[n] << 8 & 65280 | arrby[n3] & 255;
                    arrby2 = new byte[n2];
                    System.arraycopy(arrby, n3 + 1, arrby2, 0, n2);
                }
            }
        }
        n2 = (arrby2[0] & 128) != 0 ? 1 : 0;
        n = (arrby2[20] & 128) != 0 ? 1 : 0;
        arrby = new byte[arrby2.length + 6 + n2 + n];
        arrby[0] = 48;
        arrby[1] = 44;
        arrby[1] = (byte)(arrby[1] + n2);
        arrby[1] = (byte)(arrby[1] + n);
        arrby[2] = 2;
        arrby[3] = 20;
        arrby[3] = (byte)(arrby[3] + n2);
        System.arraycopy(arrby2, 0, arrby, n2 + 4, 20);
        arrby[4 + arrby[3]] = 2;
        arrby[5 + arrby[3]] = 20;
        n2 = 5 + arrby[3];
        arrby[n2] = (byte)(arrby[n2] + n);
        System.arraycopy(arrby2, 20, arrby, 6 + arrby[3] + n, 20);
        return this.signature.verify(arrby);
    }
}
