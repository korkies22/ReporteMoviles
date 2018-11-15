/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jce;

import com.jcraft.jsch.ECDH;
import com.jcraft.jsch.jce.KeyPairGenECDSA;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.security.spec.KeySpec;
import javax.crypto.KeyAgreement;

public class ECDHN
implements ECDH {
    private static BigInteger three;
    private static BigInteger two;
    byte[] Q_array;
    private KeyAgreement myKeyAgree;
    ECPublicKey publicKey;

    static {
        two = BigInteger.ONE.add(BigInteger.ONE);
        three = two.add(BigInteger.ONE);
    }

    private void bzero(byte[] arrby) {
        for (int i = 0; i < arrby.length; ++i) {
            arrby[i] = 0;
        }
    }

    private byte[] chop0(byte[] arrby) {
        if (arrby[0] != 0) {
            return arrby;
        }
        byte[] arrby2 = new byte[arrby.length - 1];
        System.arraycopy(arrby, 1, arrby2, 0, arrby2.length);
        this.bzero(arrby);
        return arrby2;
    }

    private byte[] insert0(byte[] arrby) {
        if ((arrby[0] & 128) == 0) {
            return arrby;
        }
        byte[] arrby2 = new byte[arrby.length + 1];
        System.arraycopy(arrby, 0, arrby2, 1, arrby.length);
        this.bzero(arrby);
        return arrby2;
    }

    private byte[] toPoint(byte[] arrby, byte[] arrby2) {
        byte[] arrby3 = new byte[arrby.length + 1 + arrby2.length];
        arrby3[0] = 4;
        System.arraycopy(arrby, 0, arrby3, 1, arrby.length);
        System.arraycopy(arrby2, 0, arrby3, 1 + arrby.length, arrby2.length);
        return arrby3;
    }

    @Override
    public byte[] getQ() throws Exception {
        return this.Q_array;
    }

    @Override
    public byte[] getSecret(byte[] object, byte[] arrby) throws Exception {
        object = KeyFactory.getInstance("EC").generatePublic(new ECPublicKeySpec(new ECPoint(new BigInteger(1, (byte[])object), new BigInteger(1, arrby)), this.publicKey.getParams()));
        this.myKeyAgree.doPhase((Key)object, true);
        return this.myKeyAgree.generateSecret();
    }

    @Override
    public void init(int n) throws Exception {
        this.myKeyAgree = KeyAgreement.getInstance("ECDH");
        KeyPairGenECDSA keyPairGenECDSA = new KeyPairGenECDSA();
        keyPairGenECDSA.init(n);
        this.publicKey = keyPairGenECDSA.getPublicKey();
        this.Q_array = this.toPoint(keyPairGenECDSA.getR(), keyPairGenECDSA.getS());
        this.myKeyAgree.init(keyPairGenECDSA.getPrivateKey());
    }

    @Override
    public boolean validate(byte[] object, byte[] object2) throws Exception {
        if (new ECPoint((BigInteger)(object = new BigInteger(1, (byte[])object)), (BigInteger)(object2 = new BigInteger(1, (byte[])object2))).equals(ECPoint.POINT_INFINITY)) {
            return false;
        }
        EllipticCurve ellipticCurve = this.publicKey.getParams().getCurve();
        BigInteger bigInteger = ((ECFieldFp)ellipticCurve.getField()).getP();
        BigInteger bigInteger2 = bigInteger.subtract(BigInteger.ONE);
        if (object.compareTo(bigInteger2) <= 0) {
            if (object2.compareTo(bigInteger2) > 0) {
                return false;
            }
            object = object.multiply(ellipticCurve.getA()).add(ellipticCurve.getB()).add(object.modPow(three, bigInteger)).mod(bigInteger);
            if (!object2.modPow(two, bigInteger).equals(object)) {
                return false;
            }
            return true;
        }
        return false;
    }
}
