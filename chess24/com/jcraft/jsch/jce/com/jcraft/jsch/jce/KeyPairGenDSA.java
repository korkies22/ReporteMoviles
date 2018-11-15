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
import java.security.interfaces.DSAKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;

public class KeyPairGenDSA
implements com.jcraft.jsch.KeyPairGenDSA {
    byte[] g;
    byte[] p;
    byte[] q;
    byte[] x;
    byte[] y;

    @Override
    public byte[] getG() {
        return this.g;
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
    public byte[] getX() {
        return this.x;
    }

    @Override
    public byte[] getY() {
        return this.y;
    }

    @Override
    public void init(int n) throws Exception {
        Object object = KeyPairGenerator.getInstance("DSA");
        object.initialize(n, new SecureRandom());
        Serializable serializable = object.generateKeyPair();
        object = serializable.getPublic();
        serializable = serializable.getPrivate();
        this.x = ((DSAPrivateKey)serializable).getX().toByteArray();
        this.y = ((DSAPublicKey)object).getY().toByteArray();
        object = ((DSAKey)((Object)serializable)).getParams();
        this.p = object.getP().toByteArray();
        this.q = object.getQ().toByteArray();
        this.g = object.getG().toByteArray();
    }
}
