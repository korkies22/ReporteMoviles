/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch.jce;

import com.jcraft.jsch.Buffer;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.KeySpec;

public class SignatureECDSA
implements com.jcraft.jsch.SignatureECDSA {
    KeyFactory keyFactory;
    Signature signature;

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

    @Override
    public void init() throws Exception {
        this.signature = Signature.getInstance("SHA256withECDSA");
        this.keyFactory = KeyFactory.getInstance("EC");
    }

    @Override
    public void setPrvKey(byte[] object) throws Exception {
        Object object2 = this.insert0((byte[])object);
        object = "secp256r1";
        if (((byte[])object2).length >= 64) {
            object = "secp521r1";
        } else if (((byte[])object2).length >= 48) {
            object = "secp384r1";
        }
        AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("EC");
        algorithmParameters.init(new ECGenParameterSpec((String)object));
        object = algorithmParameters.getParameterSpec(ECParameterSpec.class);
        object2 = new BigInteger(1, (byte[])object2);
        object = this.keyFactory.generatePrivate(new ECPrivateKeySpec((BigInteger)object2, (ECParameterSpec)object));
        this.signature.initSign((PrivateKey)object);
    }

    @Override
    public void setPubKey(byte[] object, byte[] object2) throws Exception {
        byte[] arrby = this.insert0((byte[])object);
        object2 = this.insert0((byte[])object2);
        object = "secp256r1";
        if (arrby.length >= 64) {
            object = "secp521r1";
        } else if (arrby.length >= 48) {
            object = "secp384r1";
        }
        AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("EC");
        algorithmParameters.init(new ECGenParameterSpec((String)object));
        object = algorithmParameters.getParameterSpec(ECParameterSpec.class);
        object2 = new ECPoint(new BigInteger(1, arrby), new BigInteger(1, (byte[])object2));
        object = this.keyFactory.generatePublic(new ECPublicKeySpec((ECPoint)object2, (ECParameterSpec)object));
        this.signature.initVerify((PublicKey)object);
    }

    @Override
    public byte[] sign() throws Exception {
        byte[] arrby;
        block5 : {
            int n;
            int n2;
            byte[] arrby2;
            block6 : {
                arrby = arrby2 = this.signature.sign();
                if (arrby2[0] != 48) break block5;
                n2 = arrby2[1];
                n = 3;
                if (n2 + 2 == arrby2.length) break block6;
                arrby = arrby2;
                if ((arrby2[1] & 128) == 0) break block5;
                arrby = arrby2;
                if ((arrby2[2] & 255) + 3 != arrby2.length) break block5;
            }
            n2 = n;
            if ((arrby2[1] & 128) != 0) {
                n2 = n;
                if ((arrby2[2] & 255) + 3 == arrby2.length) {
                    n2 = 4;
                }
            }
            Object object = new byte[arrby2[n2]];
            arrby = new byte[arrby2[n2 + 2 + arrby2[n2]]];
            System.arraycopy(arrby2, n2 + 1, object, 0, ((byte[])object).length);
            System.arraycopy(arrby2, n2 + 3 + arrby2[n2], arrby, 0, arrby.length);
            arrby2 = this.chop0((byte[])object);
            arrby = this.chop0(arrby);
            object = new Buffer();
            object.putMPInt(arrby2);
            object.putMPInt(arrby);
            arrby = new byte[object.getLength()];
            object.setOffSet(0);
            object.getByte(arrby);
        }
        return arrby;
    }

    @Override
    public void update(byte[] arrby) throws Exception {
        this.signature.update(arrby);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean verify(byte[] arrby) throws Exception {
        Object object;
        if (arrby[0] == 48) {
            object = arrby;
            if (arrby[1] + 2 == arrby.length) return this.signature.verify((byte[])object);
            if ((arrby[1] & 128) != 0) {
                object = arrby;
                if ((arrby[2] & 255) + 3 == arrby.length) return this.signature.verify((byte[])object);
            }
        }
        object = new Buffer(arrby);
        object.getString();
        object.getInt();
        arrby = object.getMPInt();
        byte[] arrby2 = object.getMPInt();
        object = this.insert0(arrby);
        arrby2 = this.insert0(arrby2);
        if (((Object)object).length < 64) {
            arrby = new byte[((Object)object).length + 6 + arrby2.length];
            arrby[0] = 48;
            arrby[1] = (byte)(((Object)object).length + 4 + arrby2.length);
            arrby[2] = 2;
            arrby[3] = (byte)((Object)object).length;
            System.arraycopy(object, 0, arrby, 4, ((Object)object).length);
            arrby[((Object)object).length + 4] = 2;
            arrby[((Object)object).length + 5] = (byte)arrby2.length;
            System.arraycopy(arrby2, 0, arrby, 6 + ((Object)object).length, arrby2.length);
        } else {
            arrby = new byte[((Object)object).length + 6 + arrby2.length + 1];
            arrby[0] = 48;
            arrby[1] = -127;
            arrby[2] = (byte)(((Object)object).length + 4 + arrby2.length);
            arrby[3] = 2;
            arrby[4] = (byte)((Object)object).length;
            System.arraycopy(object, 0, arrby, 5, ((Object)object).length);
            arrby[((Object)object).length + 5] = 2;
            arrby[((Object)object).length + 6] = (byte)arrby2.length;
            System.arraycopy(arrby2, 0, arrby, 7 + ((Object)object).length, arrby2.length);
        }
        object = arrby;
        return this.signature.verify((byte[])object);
    }
}
