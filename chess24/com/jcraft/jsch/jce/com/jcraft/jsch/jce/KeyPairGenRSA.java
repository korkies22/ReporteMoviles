/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jce;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class KeyPairGenRSA
implements com.jcraft.jsch.KeyPairGenRSA {
    byte[] c;
    byte[] d;
    byte[] e;
    byte[] ep;
    byte[] eq;
    byte[] n;
    byte[] p;
    byte[] q;

    @Override
    public byte[] getC() {
        return this.c;
    }

    @Override
    public byte[] getD() {
        return this.d;
    }

    @Override
    public byte[] getE() {
        return this.e;
    }

    @Override
    public byte[] getEP() {
        return this.ep;
    }

    @Override
    public byte[] getEQ() {
        return this.eq;
    }

    @Override
    public byte[] getN() {
        return this.n;
    }

    @Override
    public byte[] getP() {
        return this.p;
    }

    @Override
    public byte[] getQ() {
        return this.q;
    }

    @Override
    public void init(int n) throws Exception {
        Object object = KeyPairGenerator.getInstance("RSA");
        object.initialize(n, new SecureRandom());
        Serializable serializable = object.generateKeyPair();
        object = serializable.getPublic();
        serializable = serializable.getPrivate();
        RSAPrivateKey rSAPrivateKey = (RSAPrivateKey)serializable;
        this.d = rSAPrivateKey.getPrivateExponent().toByteArray();
        this.e = ((RSAPublicKey)object).getPublicExponent().toByteArray();
        this.n = rSAPrivateKey.getModulus().toByteArray();
        object = (RSAPrivateCrtKey)serializable;
        this.c = object.getCrtCoefficient().toByteArray();
        this.ep = object.getPrimeExponentP().toByteArray();
        this.eq = object.getPrimeExponentQ().toByteArray();
        this.p = object.getPrimeP().toByteArray();
        this.q = object.getPrimeQ().toByteArray();
    }
}
