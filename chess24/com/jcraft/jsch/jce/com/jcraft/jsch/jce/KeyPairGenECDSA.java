/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jce;

import com.jcraft.jsch.JSchException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;

public class KeyPairGenECDSA
implements com.jcraft.jsch.KeyPairGenECDSA {
    byte[] d;
    ECParameterSpec params;
    ECPrivateKey prvKey;
    ECPublicKey pubKey;
    byte[] r;
    byte[] s;

    private void bzero(byte[] arrby) {
        for (int i = 0; i < arrby.length; ++i) {
            arrby[i] = 0;
        }
    }

    private byte[] chop0(byte[] arrby) {
        if (arrby[0] == 0) {
            if ((arrby[1] & 128) == 0) {
                return arrby;
            }
            byte[] arrby2 = new byte[arrby.length - 1];
            System.arraycopy(arrby, 1, arrby2, 0, arrby2.length);
            this.bzero(arrby);
            return arrby2;
        }
        return arrby;
    }

    private byte[] insert0(byte[] arrby) {
        byte[] arrby2 = new byte[arrby.length + 1];
        System.arraycopy(arrby, 0, arrby2, 1, arrby.length);
        this.bzero(arrby);
        return arrby2;
    }

    @Override
    public byte[] getD() {
        return this.d;
    }

    ECPrivateKey getPrivateKey() {
        return this.prvKey;
    }

    ECPublicKey getPublicKey() {
        return this.pubKey;
    }

    @Override
    public byte[] getR() {
        return this.r;
    }

    @Override
    public byte[] getS() {
        return this.s;
    }

    @Override
    public void init(int n) throws Exception {
        block8 : {
            String string;
            block6 : {
                block7 : {
                    block5 : {
                        if (n != 256) break block5;
                        string = "secp256r1";
                        break block6;
                    }
                    if (n != 384) break block7;
                    string = "secp384r1";
                    break block6;
                }
                if (n != 521) break block8;
                string = "secp521r1";
            }
            for (int i = 0; i < 1000; ++i) {
                Object object = KeyPairGenerator.getInstance("EC");
                object.initialize(new ECGenParameterSpec(string));
                object = object.genKeyPair();
                this.prvKey = (ECPrivateKey)object.getPrivate();
                this.pubKey = (ECPublicKey)object.getPublic();
                this.params = this.pubKey.getParams();
                this.d = this.prvKey.getS().toByteArray();
                object = this.pubKey.getW();
                this.r = object.getAffineX().toByteArray();
                this.s = object.getAffineY().toByteArray();
                if (this.r.length == this.s.length && (n == 256 && this.r.length == 32 || n == 384 && this.r.length == 48 || n == 521 && this.r.length == 66)) break;
            }
            if (this.d.length < this.r.length) {
                this.d = this.insert0(this.d);
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unsupported key size: ");
        stringBuilder.append(n);
        throw new JSchException(stringBuilder.toString());
    }
}
