/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.KeyPair;
import com.jcraft.jsch.KeyPairGenRSA;
import com.jcraft.jsch.Signature;
import com.jcraft.jsch.SignatureRSA;
import com.jcraft.jsch.Util;
import java.math.BigInteger;

public class KeyPairRSA
extends KeyPair {
    private static final byte[] begin = Util.str2byte("-----BEGIN RSA PRIVATE KEY-----");
    private static final byte[] end = Util.str2byte("-----END RSA PRIVATE KEY-----");
    private static final byte[] sshrsa = Util.str2byte("ssh-rsa");
    private byte[] c_array;
    private byte[] ep_array;
    private byte[] eq_array;
    private int key_size = 1024;
    private byte[] n_array;
    private byte[] p_array;
    private byte[] prv_array;
    private byte[] pub_array;
    private byte[] q_array;

    public KeyPairRSA(JSch jSch) {
        this(jSch, null, null, null);
    }

    public KeyPairRSA(JSch jSch, byte[] arrby, byte[] arrby2, byte[] arrby3) {
        super(jSch);
        this.n_array = arrby;
        this.pub_array = arrby2;
        this.prv_array = arrby3;
        if (arrby != null) {
            this.key_size = new BigInteger(arrby).bitLength();
        }
    }

    static KeyPair fromSSHAgent(JSch object, Buffer arrby) throws JSchException {
        arrby = arrby.getBytes(8, "invalid key format");
        object = new KeyPairRSA((JSch)object, arrby[1], arrby[2], arrby[3]);
        object.c_array = arrby[4];
        object.p_array = arrby[5];
        object.q_array = arrby[6];
        object.publicKeyComment = new String(arrby[7]);
        object.vendor = 0;
        return object;
    }

    private byte[] getCArray() {
        if (this.c_array == null) {
            this.c_array = new BigInteger(this.q_array).modInverse(new BigInteger(this.p_array)).toByteArray();
        }
        return this.c_array;
    }

    private byte[] getEPArray() {
        if (this.ep_array == null) {
            this.ep_array = new BigInteger(this.prv_array).mod(new BigInteger(this.p_array).subtract(BigInteger.ONE)).toByteArray();
        }
        return this.ep_array;
    }

    private byte[] getEQArray() {
        if (this.eq_array == null) {
            this.eq_array = new BigInteger(this.prv_array).mod(new BigInteger(this.q_array).subtract(BigInteger.ONE)).toByteArray();
        }
        return this.eq_array;
    }

    @Override
    public void dispose() {
        super.dispose();
        Util.bzero(this.prv_array);
    }

    @Override
    public byte[] forSSHAgent() throws JSchException {
        if (this.isEncrypted()) {
            throw new JSchException("key is encrypted.");
        }
        Buffer buffer = new Buffer();
        buffer.putString(sshrsa);
        buffer.putString(this.n_array);
        buffer.putString(this.pub_array);
        buffer.putString(this.prv_array);
        buffer.putString(this.getCArray());
        buffer.putString(this.p_array);
        buffer.putString(this.q_array);
        buffer.putString(Util.str2byte(this.publicKeyComment));
        byte[] arrby = new byte[buffer.getLength()];
        buffer.getByte(arrby, 0, arrby.length);
        return arrby;
    }

    @Override
    void generate(int n) throws JSchException {
        this.key_size = n;
        try {
            Object object = this.jsch;
            object = (KeyPairGenRSA)Class.forName(JSch.getConfig("keypairgen.rsa")).newInstance();
            object.init(n);
            this.pub_array = object.getE();
            this.prv_array = object.getD();
            this.n_array = object.getN();
            this.p_array = object.getP();
            this.q_array = object.getQ();
            this.ep_array = object.getEP();
            this.eq_array = object.getEQ();
            this.c_array = object.getC();
            return;
        }
        catch (Exception exception) {
            if (exception instanceof Throwable) {
                throw new JSchException(exception.toString(), exception);
            }
            throw new JSchException(exception.toString());
        }
    }

    @Override
    byte[] getBegin() {
        return begin;
    }

    @Override
    byte[] getEnd() {
        return end;
    }

    @Override
    public int getKeySize() {
        return this.key_size;
    }

    @Override
    public int getKeyType() {
        return 2;
    }

    @Override
    byte[] getKeyTypeName() {
        return sshrsa;
    }

    @Override
    byte[] getPrivateKey() {
        int n = this.countLength(1) + 1 + 1 + 1 + this.countLength(this.n_array.length) + this.n_array.length + 1 + this.countLength(this.pub_array.length) + this.pub_array.length + 1 + this.countLength(this.prv_array.length) + this.prv_array.length + 1 + this.countLength(this.p_array.length) + this.p_array.length + 1 + this.countLength(this.q_array.length) + this.q_array.length + 1 + this.countLength(this.ep_array.length) + this.ep_array.length + 1 + this.countLength(this.eq_array.length) + this.eq_array.length + 1 + this.countLength(this.c_array.length) + this.c_array.length;
        byte[] arrby = new byte[this.countLength(n) + 1 + n];
        this.writeINTEGER(arrby, this.writeINTEGER(arrby, this.writeINTEGER(arrby, this.writeINTEGER(arrby, this.writeINTEGER(arrby, this.writeINTEGER(arrby, this.writeINTEGER(arrby, this.writeINTEGER(arrby, this.writeINTEGER(arrby, this.writeSEQUENCE(arrby, 0, n), new byte[1]), this.n_array), this.pub_array), this.prv_array), this.p_array), this.q_array), this.ep_array), this.eq_array), this.c_array);
        return arrby;
    }

    @Override
    public byte[] getPublicKeyBlob() {
        byte[] arrby = super.getPublicKeyBlob();
        if (arrby != null) {
            return arrby;
        }
        if (this.pub_array == null) {
            return null;
        }
        return Buffer.fromBytes((byte[][])new byte[][]{KeyPairRSA.sshrsa, this.pub_array, this.n_array}).buffer;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public byte[] getSignature(byte[] arrby) {
        try {
            Object object = this.jsch;
            object = (SignatureRSA)Class.forName(JSch.getConfig("signature.rsa")).newInstance();
            object.init();
            object.setPrvKey(this.prv_array, this.n_array);
            object.update(arrby);
            arrby = object.sign();
            return Buffer.fromBytes((byte[][])new byte[][]{KeyPairRSA.sshrsa, arrby}).buffer;
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Signature getVerifier() {
        try {
            Object object = this.jsch;
            object = (SignatureRSA)Class.forName(JSch.getConfig("signature.rsa")).newInstance();
            object.init();
            if (this.pub_array == null && this.n_array == null && this.getPublicKeyBlob() != null) {
                Buffer buffer = new Buffer(this.getPublicKeyBlob());
                buffer.getString();
                this.pub_array = buffer.getString();
                this.n_array = buffer.getString();
            }
            object.setPubKey(this.pub_array, this.n_array);
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    boolean parse(byte[] var1_1) {
        block52 : {
            block53 : {
                block50 : {
                    block51 : {
                        block48 : {
                            block49 : {
                                block46 : {
                                    block47 : {
                                        block44 : {
                                            block45 : {
                                                block42 : {
                                                    block43 : {
                                                        block40 : {
                                                            block41 : {
                                                                block39 : {
                                                                    block38 : {
                                                                        if (this.vendor != 2) break block38;
                                                                        var7_4 = new Buffer((byte[])var1_1);
                                                                        var7_4.skip(((Object)var1_1).length);
                                                                        var1_1 = var7_4.getBytes(4, "");
                                                                        this.prv_array = var1_1[0];
                                                                        this.p_array = var1_1[1];
                                                                        this.q_array = var1_1[2];
                                                                        this.c_array = var1_1[3];
                                                                        this.getEPArray();
                                                                        this.getEQArray();
                                                                        return true;
                                                                    }
                                                                    if (this.vendor != 1) break block39;
                                                                    if (var1_1[0] == 48) return false;
                                                                    var1_1 = new Buffer((byte[])var1_1);
                                                                    this.pub_array = var1_1.getMPIntBits();
                                                                    this.prv_array = var1_1.getMPIntBits();
                                                                    this.n_array = var1_1.getMPIntBits();
                                                                    var1_1.getMPIntBits();
                                                                    this.p_array = var1_1.getMPIntBits();
                                                                    this.q_array = var1_1.getMPIntBits();
                                                                    if (this.n_array != null) {
                                                                        this.key_size = new BigInteger(this.n_array).bitLength();
                                                                    }
                                                                    this.getEPArray();
                                                                    this.getEQArray();
                                                                    this.getCArray();
                                                                    return true;
lbl33: // 3 sources:
                                                                    this.n_array = new byte[var3_6];
                                                                    System.arraycopy(var1_1, var4_7 /* !! */ , this.n_array, 0, var3_6);
                                                                    var2_5 = var4_7 /* !! */  + var3_6 + 1;
                                                                    var3_6 = var2_5 + 1;
                                                                    var5_8 /* !! */  = var1_1[var2_5] & 255;
                                                                    if ((var5_8 /* !! */  & 128) == 0) break block40;
                                                                    break block41;
lbl41: // 3 sources:
                                                                    this.pub_array = new byte[var5_8 /* !! */ ];
                                                                    System.arraycopy(var1_1, var6_9, this.pub_array, 0, var5_8 /* !! */ );
                                                                    var2_5 = var6_9 + var5_8 /* !! */  + 1;
                                                                    var3_6 = var2_5 + 1;
                                                                    var5_8 /* !! */  = var1_1[var2_5] & 255;
                                                                    if ((var5_8 /* !! */  & 128) == 0) break block42;
                                                                    break block43;
lbl49: // 3 sources:
                                                                    this.prv_array = new byte[var5_8 /* !! */ ];
                                                                    System.arraycopy(var1_1, var6_9, this.prv_array, 0, var5_8 /* !! */ );
                                                                    var2_5 = var6_9 + var5_8 /* !! */  + 1;
                                                                    var3_6 = var2_5 + 1;
                                                                    var5_8 /* !! */  = var1_1[var2_5] & 255;
                                                                    if ((var5_8 /* !! */  & 128) == 0) break block44;
                                                                    break block45;
lbl57: // 3 sources:
                                                                    this.p_array = new byte[var5_8 /* !! */ ];
                                                                    System.arraycopy(var1_1, var6_9, this.p_array, 0, var5_8 /* !! */ );
                                                                    var2_5 = var6_9 + var5_8 /* !! */  + 1;
                                                                    var3_6 = var2_5 + 1;
                                                                    var5_8 /* !! */  = var1_1[var2_5] & 255;
                                                                    if ((var5_8 /* !! */  & 128) == 0) break block46;
                                                                    break block47;
lbl65: // 3 sources:
                                                                    this.q_array = new byte[var5_8 /* !! */ ];
                                                                    System.arraycopy(var1_1, var6_9, this.q_array, 0, var5_8 /* !! */ );
                                                                    var2_5 = var6_9 + var5_8 /* !! */  + 1;
                                                                    var3_6 = var2_5 + 1;
                                                                    var5_8 /* !! */  = var1_1[var2_5] & 255;
                                                                    if ((var5_8 /* !! */  & 128) == 0) break block48;
                                                                    break block49;
lbl73: // 3 sources:
                                                                    this.ep_array = new byte[var5_8 /* !! */ ];
                                                                    System.arraycopy(var1_1, var6_9, this.ep_array, 0, var5_8 /* !! */ );
                                                                    var2_5 = var6_9 + var5_8 /* !! */  + 1;
                                                                    var3_6 = var2_5 + 1;
                                                                    var5_8 /* !! */  = var1_1[var2_5] & 255;
                                                                    if ((var5_8 /* !! */  & 128) == 0) break block50;
                                                                    break block51;
lbl81: // 3 sources:
                                                                    this.eq_array = new byte[var5_8 /* !! */ ];
                                                                    System.arraycopy(var1_1, var6_9, this.eq_array, 0, var5_8 /* !! */ );
                                                                    var2_5 = var6_9 + var5_8 /* !! */  + 1;
                                                                    var3_6 = var2_5 + 1;
                                                                    var5_8 /* !! */  = var1_1[var2_5] & 255;
                                                                    if ((var5_8 /* !! */  & 128) == 0) break block52;
                                                                    break block53;
lbl88: // 2 sources:
                                                                    do {
                                                                        try {
                                                                            this.c_array = new byte[var5_8 /* !! */ ];
                                                                            System.arraycopy(var1_1, var6_9, this.c_array, 0, var5_8 /* !! */ );
                                                                            if (this.n_array == null) return true;
                                                                            this.key_size = new BigInteger(this.n_array).bitLength();
                                                                            return true;
                                                                        }
                                                                        catch (Exception var1_2) {
                                                                            return false;
                                                                        }
                                                                        break;
                                                                    } while (true);
                                                                    {
                                                                        catch (JSchException var1_3) {
                                                                            return false;
                                                                        }
                                                                    }
                                                                }
                                                                var2_5 = var1_1[1] & 255;
                                                                if ((var2_5 & 128) != 0) {
                                                                    var3_6 = var2_5 & 127;
                                                                    var2_5 = 2;
                                                                    do {
                                                                        var4_7 /* !! */  = var2_5;
                                                                        if (var3_6 > 0) {
                                                                            var4_7 /* !! */  = (int)var1_1[var2_5];
                                                                            ++var2_5;
                                                                            --var3_6;
                                                                            continue;
                                                                        }
                                                                        break;
                                                                    } while (true);
                                                                } else {
                                                                    var4_7 /* !! */  = 2;
                                                                }
                                                                if (var1_1[var4_7 /* !! */ ] != 2) {
                                                                    return false;
                                                                }
                                                                var3_6 = var4_7 /* !! */  + 1;
                                                                var2_5 = var3_6 + 1;
                                                                var5_8 /* !! */  = var1_1[var3_6] & 255;
                                                                var4_7 /* !! */  = var2_5;
                                                                var3_6 = var5_8 /* !! */ ;
                                                                if ((var5_8 /* !! */  & 128) != 0) {
                                                                    var4_7 /* !! */  = var5_8 /* !! */  & 127;
                                                                    var3_6 = 0;
                                                                    while (var4_7 /* !! */  > 0) {
                                                                        var5_8 /* !! */  = (int)var1_1[var2_5];
                                                                        --var4_7 /* !! */ ;
                                                                        var3_6 = (var5_8 /* !! */  & 255) + (var3_6 << 8);
                                                                        ++var2_5;
                                                                    }
                                                                    var4_7 /* !! */  = var2_5;
                                                                }
                                                                var3_6 = var4_7 /* !! */  + var3_6 + 1;
                                                                var2_5 = var3_6 + 1;
                                                                var3_6 = var5_8 /* !! */  = var1_1[var3_6] & 255;
                                                                var4_7 /* !! */  = var2_5;
                                                                if ((var5_8 /* !! */  & 128) == 0) ** GOTO lbl33
                                                                var3_6 = 0;
                                                                for (var4_7 /* !! */  = var5_8 /* !! */  & 127; var4_7 /* !! */  > 0; --var4_7 /* !! */ ) {
                                                                    var5_8 /* !! */  = (int)var1_1[var2_5];
                                                                    ++var2_5;
                                                                    var3_6 = (var3_6 << 8) + (var5_8 /* !! */  & 255);
                                                                }
                                                                var4_7 /* !! */  = var2_5;
                                                                ** GOTO lbl33
                                                            }
                                                            var4_7 /* !! */  = var5_8 /* !! */  & 127;
                                                            var2_5 = 0;
                                                            do {
                                                                var5_8 /* !! */  = var2_5;
                                                                var6_9 = var3_6;
                                                                if (var4_7 /* !! */  <= 0) ** GOTO lbl41
                                                                var2_5 = (var2_5 << 8) + (var1_1[var3_6] & 255);
                                                                ++var3_6;
                                                                --var4_7 /* !! */ ;
                                                            } while (true);
                                                        }
                                                        var6_9 = var3_6;
                                                        ** GOTO lbl41
                                                    }
                                                    var4_7 /* !! */  = var5_8 /* !! */  & 127;
                                                    var2_5 = 0;
                                                    do {
                                                        var5_8 /* !! */  = var2_5;
                                                        var6_9 = var3_6;
                                                        if (var4_7 /* !! */  <= 0) ** GOTO lbl49
                                                        var2_5 = (var2_5 << 8) + (var1_1[var3_6] & 255);
                                                        ++var3_6;
                                                        --var4_7 /* !! */ ;
                                                    } while (true);
                                                }
                                                var6_9 = var3_6;
                                                ** GOTO lbl49
                                            }
                                            var4_7 /* !! */  = var5_8 /* !! */  & 127;
                                            var2_5 = 0;
                                            do {
                                                var5_8 /* !! */  = var2_5;
                                                var6_9 = var3_6;
                                                if (var4_7 /* !! */  <= 0) ** GOTO lbl57
                                                var2_5 = (var2_5 << 8) + (var1_1[var3_6] & 255);
                                                ++var3_6;
                                                --var4_7 /* !! */ ;
                                            } while (true);
                                        }
                                        var6_9 = var3_6;
                                        ** GOTO lbl57
                                    }
                                    var4_7 /* !! */  = var5_8 /* !! */  & 127;
                                    var2_5 = 0;
                                    do {
                                        var5_8 /* !! */  = var2_5;
                                        var6_9 = var3_6;
                                        if (var4_7 /* !! */  <= 0) ** GOTO lbl65
                                        var2_5 = (var2_5 << 8) + (var1_1[var3_6] & 255);
                                        ++var3_6;
                                        --var4_7 /* !! */ ;
                                    } while (true);
                                }
                                var6_9 = var3_6;
                                ** GOTO lbl65
                            }
                            var4_7 /* !! */  = var5_8 /* !! */  & 127;
                            var2_5 = 0;
                            do {
                                var5_8 /* !! */  = var2_5;
                                var6_9 = var3_6;
                                if (var4_7 /* !! */  <= 0) ** GOTO lbl73
                                var2_5 = (var2_5 << 8) + (var1_1[var3_6] & 255);
                                ++var3_6;
                                --var4_7 /* !! */ ;
                            } while (true);
                        }
                        var6_9 = var3_6;
                        ** GOTO lbl73
                    }
                    var4_7 /* !! */  = var5_8 /* !! */  & 127;
                    var2_5 = 0;
                    do {
                        var5_8 /* !! */  = var2_5;
                        var6_9 = var3_6;
                        if (var4_7 /* !! */  <= 0) ** GOTO lbl81
                        var2_5 = (var2_5 << 8) + (var1_1[var3_6] & 255);
                        ++var3_6;
                        --var4_7 /* !! */ ;
                    } while (true);
                }
                var6_9 = var3_6;
                ** GOTO lbl81
            }
            var4_7 /* !! */  = var5_8 /* !! */  & 127;
            var2_5 = 0;
            do {
                var5_8 /* !! */  = var2_5;
                var6_9 = var3_6;
                if (var4_7 /* !! */  <= 0) ** GOTO lbl88
                var2_5 = (var2_5 << 8) + (var1_1[var3_6] & 255);
                ++var3_6;
                --var4_7 /* !! */ ;
            } while (true);
        }
        var6_9 = var3_6;
        ** while (true)
    }
}
