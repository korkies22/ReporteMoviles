/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Cipher;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.KeyPair;
import com.jcraft.jsch.KeyPairDSA;
import com.jcraft.jsch.KeyPairRSA;
import com.jcraft.jsch.PBKDF;
import com.jcraft.jsch.Signature;
import com.jcraft.jsch.Util;
import java.math.BigInteger;
import java.util.Vector;

public class KeyPairPKCS8
extends KeyPair {
    private static final byte[] aes128cbc;
    private static final byte[] aes192cbc;
    private static final byte[] aes256cbc;
    private static final byte[] begin;
    private static final byte[] dsaEncryption;
    private static final byte[] end;
    private static final byte[] pbeWithMD5AndDESCBC;
    private static final byte[] pbes2;
    private static final byte[] pbkdf2;
    private static final byte[] rsaEncryption;
    private KeyPair kpair = null;

    static {
        rsaEncryption = new byte[]{42, -122, 72, -122, -9, 13, 1, 1, 1};
        dsaEncryption = new byte[]{42, -122, 72, -50, 56, 4, 1};
        pbes2 = new byte[]{42, -122, 72, -122, -9, 13, 1, 5, 13};
        pbkdf2 = new byte[]{42, -122, 72, -122, -9, 13, 1, 5, 12};
        aes128cbc = new byte[]{96, -122, 72, 1, 101, 3, 4, 1, 2};
        aes192cbc = new byte[]{96, -122, 72, 1, 101, 3, 4, 1, 22};
        aes256cbc = new byte[]{96, -122, 72, 1, 101, 3, 4, 1, 42};
        pbeWithMD5AndDESCBC = new byte[]{42, -122, 72, -122, -9, 13, 1, 5, 3};
        begin = Util.str2byte("-----BEGIN DSA PRIVATE KEY-----");
        end = Util.str2byte("-----END DSA PRIVATE KEY-----");
    }

    public KeyPairPKCS8(JSch jSch) {
        super(jSch);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public boolean decrypt(byte[] arrby) {
        if (!this.isEncrypted()) {
            return true;
        }
        if (arrby == null) {
            return this.isEncrypted() ^ true;
        }
        object = new KeyPair.ASN1(this, this.data).getContents();
        arrby2 = object[1].getContent();
        object2 = object[0].getContents();
        object = object2[0].getContent();
        object2 = object2[1];
        if (!Util.array_equals((byte[])object, KeyPairPKCS8.pbes2)) ** GOTO lbl37
        object = object2.getContents();
        object2 = object[0];
        object = object[1];
        try {
            object2 = object2.getContents();
            object2[0].getContent();
            object2 = object2[1].getContents();
            arrby3 = object2[0].getContent();
            n = Integer.parseInt(new BigInteger(object2[1].getContent()).toString());
            object = object.getContents();
            object2 = object[0].getContent();
            object = object[1].getContent();
            object2 = this.getCipher((byte[])object2);
            if (object2 != null) ** break block11
            return false;
        }
lbl29: // 7 sources:
        catch (KeyPair.ASN1Exception | Exception exception) {
            return false;
        }
        {
            
            jSch = this.jsch;
            arrby = ((PBKDF)Class.forName(JSch.getConfig("pbkdf")).newInstance()).getKey(arrby, arrby3, n, object2.getBlockSize());
            ** GOTO lbl41
            {
                catch (Exception exception) {}
lbl37: // 1 sources:
                bl = Util.array_equals((byte[])object, KeyPairPKCS8.pbeWithMD5AndDESCBC);
                if (bl == false) return false;
                return false;
                return false;
lbl41: // 1 sources:
                if (arrby == null) {
                    return false;
                }
                ** try [egrp 5[TRYBLOCK] [9 : 242->302)] { 
lbl44: // 1 sources:
                object2.init(1, arrby, (byte[])object);
                Util.bzero(arrby);
                arrby = new byte[arrby2.length];
                object2.update(arrby2, 0, arrby2.length, arrby, 0);
                if (this.parse(arrby) == false) return false;
                this.encrypted = false;
                return true;
            }
        }
    }

    @Override
    public byte[] forSSHAgent() throws JSchException {
        return this.kpair.forSSHAgent();
    }

    @Override
    void generate(int n) throws JSchException {
    }

    @Override
    byte[] getBegin() {
        return begin;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    Cipher getCipher(byte[] object) {
        Object object2;
        try {
            object2 = Util.array_equals((byte[])object, aes128cbc) ? "aes128-cbc" : (Util.array_equals((byte[])object, aes192cbc) ? "aes192-cbc" : (Util.array_equals((byte[])object, aes256cbc) ? "aes256-cbc" : null));
        }
        catch (Exception exception) {}
        try {
            JSch jSch = this.jsch;
            return (Cipher)Class.forName(JSch.getConfig((String)object2)).newInstance();
        }
        catch (Exception exception) {}
        object2 = null;
        if (!JSch.getLogger().isEnabled(4)) return null;
        if (object2 == null) {
            object2 = new StringBuilder();
            object2.append("unknown oid: ");
            object2.append(Util.toHex((byte[])object));
            object = object2.toString();
        } else {
            object = new StringBuilder();
            object.append("function ");
            object.append((String)object2);
            object.append(" is not supported");
            object = object.toString();
        }
        object2 = JSch.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PKCS8: ");
        stringBuilder.append((String)object);
        object2.log(4, stringBuilder.toString());
        return null;
    }

    @Override
    byte[] getEnd() {
        return end;
    }

    @Override
    public int getKeySize() {
        return this.kpair.getKeySize();
    }

    @Override
    public int getKeyType() {
        return this.kpair.getKeyType();
    }

    @Override
    byte[] getKeyTypeName() {
        return this.kpair.getKeyTypeName();
    }

    @Override
    byte[] getPrivateKey() {
        return null;
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
    public Signature getVerifier() {
        return this.kpair.getVerifier();
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    boolean parse(byte[] var1_1) {
        block13 : {
            block12 : {
                var3_4 = false;
                var4_5 = new Vector<byte[]>();
                var1_1 = new KeyPair.ASN1(this, (byte[])var1_1).getContents();
                var5_6 = var1_1[1];
                var1_1 = var1_1[2];
                var6_12 = var5_6.getContents();
                var5_7 = var6_12[0].getContent();
                var6_12 = var6_12[1].getContents();
                if (var6_12.length <= 0) break block12;
                var2_13 = 0;
                do {
                    if (var2_13 >= var6_12.length) break;
                    var4_5.addElement(var6_12[var2_13].getContent());
                    ++var2_13;
                } while (true);
            }
            var1_1 = var1_1.getContent();
            if (Util.array_equals(var5_7, KeyPairPKCS8.rsaEncryption)) {
                var4_5 = new KeyPairRSA(this.jsch);
                var4_5.copy(this);
                if (var4_5.parse((byte[])var1_1)) {
                    this.kpair = var4_5;
                }
                break block13;
            }
            if (!Util.array_equals(var5_7, KeyPairPKCS8.dsaEncryption)) break block13;
            var1_1 = new KeyPair.ASN1(this, (byte[])var1_1);
            if (var4_5.size() != 0) ** GOTO lbl44
            var5_8 = var1_1.getContents();
            var1_1 = var5_8[1].getContent();
            var5_9 = var5_8[0].getContents();
            var2_13 = 0;
            do {
                if (var2_13 >= var5_9.length) break;
                var4_5.addElement(var5_9[var2_13].getContent());
                ++var2_13;
            } while (true);
            try {
                block14 : {
                    var4_5.addElement(var1_1);
                    break block14;
lbl44: // 1 sources:
                    var4_5.addElement(var1_1.getContent());
                }
                var1_1 = (byte[])var4_5.elementAt(0);
                var5_11 = (byte[])var4_5.elementAt(1);
                var6_12 = (byte[])var4_5.elementAt(2);
                var4_5 = (byte[])var4_5.elementAt(3);
                var7_14 = new BigInteger((byte[])var6_12).modPow(new BigInteger((byte[])var4_5), new BigInteger((byte[])var1_1)).toByteArray();
                var1_1 = new KeyPairDSA(this.jsch, (byte[])var1_1, var5_11, (byte[])var6_12, var7_14, (byte[])var4_5).getPrivateKey();
                var4_5 = new KeyPairDSA(this.jsch);
                var4_5.copy(this);
                if (!var4_5.parse((byte[])var1_1)) break block13;
                this.kpair = var4_5;
            }
            catch (KeyPair.ASN1Exception var1_2) {
                return false;
            }
            catch (Exception var1_3) {
                return false;
            }
        }
        if (this.kpair == null) return var3_4;
        return true;
    }
}
