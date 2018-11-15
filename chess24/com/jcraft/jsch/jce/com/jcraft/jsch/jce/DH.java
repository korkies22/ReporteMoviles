/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jce;

import com.jcraft.jsch.JSchException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;

public class DH
implements com.jcraft.jsch.DH {
    BigInteger K;
    byte[] K_array;
    BigInteger e;
    byte[] e_array;
    BigInteger f;
    BigInteger g;
    private KeyAgreement myKeyAgree;
    private KeyPairGenerator myKpairGen;
    BigInteger p;

    private void checkRange(BigInteger bigInteger) throws Exception {
        BigInteger bigInteger2 = BigInteger.ONE;
        BigInteger bigInteger3 = this.p.subtract(bigInteger2);
        if (bigInteger2.compareTo(bigInteger) < 0 && bigInteger.compareTo(bigInteger3) < 0) {
            return;
        }
        throw new JSchException("invalid DH value");
    }

    @Override
    public void checkRange() throws Exception {
    }

    @Override
    public byte[] getE() throws Exception {
        if (this.e == null) {
            Object object = new DHParameterSpec(this.p, this.g);
            this.myKpairGen.initialize((AlgorithmParameterSpec)object);
            object = this.myKpairGen.generateKeyPair();
            this.myKeyAgree.init(object.getPrivate());
            this.e = ((DHPublicKey)object.getPublic()).getY();
            this.e_array = this.e.toByteArray();
        }
        return this.e_array;
    }

    @Override
    public byte[] getK() throws Exception {
        if (this.K == null) {
            byte[] arrby = KeyFactory.getInstance("DH").generatePublic(new DHPublicKeySpec(this.f, this.p, this.g));
            this.myKeyAgree.doPhase((Key)arrby, true);
            arrby = this.myKeyAgree.generateSecret();
            this.K = new BigInteger(1, arrby);
            this.K_array = this.K.toByteArray();
            this.K_array = arrby;
        }
        return this.K_array;
    }

    @Override
    public void init() throws Exception {
        this.myKpairGen = KeyPairGenerator.getInstance("DH");
        this.myKeyAgree = KeyAgreement.getInstance("DH");
    }

    void setF(BigInteger bigInteger) {
        this.f = bigInteger;
    }

    @Override
    public void setF(byte[] arrby) {
        this.setF(new BigInteger(1, arrby));
    }

    void setG(BigInteger bigInteger) {
        this.g = bigInteger;
    }

    @Override
    public void setG(byte[] arrby) {
        this.setG(new BigInteger(1, arrby));
    }

    void setP(BigInteger bigInteger) {
        this.p = bigInteger;
    }

    @Override
    public void setP(byte[] arrby) {
        this.setP(new BigInteger(1, arrby));
    }
}
