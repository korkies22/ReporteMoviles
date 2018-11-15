/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.KeyPair;
import com.jcraft.jsch.KeyPairGenDSA;
import com.jcraft.jsch.Signature;
import com.jcraft.jsch.SignatureDSA;
import com.jcraft.jsch.Util;
import java.math.BigInteger;

public class KeyPairDSA
extends KeyPair {
    private static final byte[] begin = Util.str2byte("-----BEGIN DSA PRIVATE KEY-----");
    private static final byte[] end = Util.str2byte("-----END DSA PRIVATE KEY-----");
    private static final byte[] sshdss = Util.str2byte("ssh-dss");
    private byte[] G_array;
    private byte[] P_array;
    private byte[] Q_array;
    private int key_size = 1024;
    private byte[] prv_array;
    private byte[] pub_array;

    public KeyPairDSA(JSch jSch) {
        this(jSch, null, null, null, null, null);
    }

    public KeyPairDSA(JSch jSch, byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4, byte[] arrby5) {
        super(jSch);
        this.P_array = arrby;
        this.Q_array = arrby2;
        this.G_array = arrby3;
        this.pub_array = arrby4;
        this.prv_array = arrby5;
        if (arrby != null) {
            this.key_size = new BigInteger(arrby).bitLength();
        }
    }

    static KeyPair fromSSHAgent(JSch object, Buffer arrby) throws JSchException {
        arrby = arrby.getBytes(7, "invalid key format");
        object = new KeyPairDSA((JSch)object, arrby[1], arrby[2], arrby[3], arrby[4], arrby[5]);
        object.publicKeyComment = new String(arrby[6]);
        object.vendor = 0;
        return object;
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
        buffer.putString(sshdss);
        buffer.putString(this.P_array);
        buffer.putString(this.Q_array);
        buffer.putString(this.G_array);
        buffer.putString(this.pub_array);
        buffer.putString(this.prv_array);
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
            object = (KeyPairGenDSA)Class.forName(JSch.getConfig("keypairgen.dsa")).newInstance();
            object.init(n);
            this.P_array = object.getP();
            this.Q_array = object.getQ();
            this.G_array = object.getG();
            this.pub_array = object.getY();
            this.prv_array = object.getX();
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
        return 1;
    }

    @Override
    byte[] getKeyTypeName() {
        return sshdss;
    }

    @Override
    byte[] getPrivateKey() {
        int n = this.countLength(1) + 1 + 1 + 1 + this.countLength(this.P_array.length) + this.P_array.length + 1 + this.countLength(this.Q_array.length) + this.Q_array.length + 1 + this.countLength(this.G_array.length) + this.G_array.length + 1 + this.countLength(this.pub_array.length) + this.pub_array.length + 1 + this.countLength(this.prv_array.length) + this.prv_array.length;
        byte[] arrby = new byte[this.countLength(n) + 1 + n];
        this.writeINTEGER(arrby, this.writeINTEGER(arrby, this.writeINTEGER(arrby, this.writeINTEGER(arrby, this.writeINTEGER(arrby, this.writeINTEGER(arrby, this.writeSEQUENCE(arrby, 0, n), new byte[1]), this.P_array), this.Q_array), this.G_array), this.pub_array), this.prv_array);
        return arrby;
    }

    @Override
    public byte[] getPublicKeyBlob() {
        byte[] arrby = super.getPublicKeyBlob();
        if (arrby != null) {
            return arrby;
        }
        if (this.P_array == null) {
            return null;
        }
        return Buffer.fromBytes((byte[][])new byte[][]{KeyPairDSA.sshdss, this.P_array, this.Q_array, this.G_array, this.pub_array}).buffer;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public byte[] getSignature(byte[] arrby) {
        try {
            Object object = this.jsch;
            object = (SignatureDSA)Class.forName(JSch.getConfig("signature.dss")).newInstance();
            object.init();
            object.setPrvKey(this.prv_array, this.P_array, this.Q_array, this.G_array);
            object.update(arrby);
            arrby = object.sign();
            return Buffer.fromBytes((byte[][])new byte[][]{KeyPairDSA.sshdss, arrby}).buffer;
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
            object = (SignatureDSA)Class.forName(JSch.getConfig("signature.dss")).newInstance();
            object.init();
            if (this.pub_array == null && this.P_array == null && this.getPublicKeyBlob() != null) {
                Buffer buffer = new Buffer(this.getPublicKeyBlob());
                buffer.getString();
                this.P_array = buffer.getString();
                this.Q_array = buffer.getString();
                this.G_array = buffer.getString();
                this.pub_array = buffer.getString();
            }
            object.setPubKey(this.pub_array, this.P_array, this.Q_array, this.G_array);
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    boolean parse(byte[] var1_1) {
        block37 : {
            if (this.vendor != 1) ** GOTO lbl15
            if (var1_1[0] == 48) return false;
            var1_1 = new Buffer((byte[])var1_1);
            var1_1.getInt();
            this.P_array = var1_1.getMPIntBits();
            this.G_array = var1_1.getMPIntBits();
            this.Q_array = var1_1.getMPIntBits();
            this.pub_array = var1_1.getMPIntBits();
            this.prv_array = var1_1.getMPIntBits();
            if (this.P_array == null) return true;
            this.key_size = new BigInteger(this.P_array).bitLength();
            return true;
lbl15: // 1 sources:
            if (this.vendor == 2) {
                var7_4 = new Buffer((byte[])var1_1);
                var7_4.skip(((Object)var1_1).length);
                this.prv_array = var7_4.getBytes(1, "")[0];
                return true;
            }
            if (var1_1[0] != 48) {
                return false;
            }
            break block37;
            catch (JSchException var1_3) {
                return false;
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
            var3_6 = 0;
            for (var4_7 /* !! */  = var5_8 /* !! */  & 127; var4_7 /* !! */  > 0; --var4_7 /* !! */ , ++var2_5) {
                var5_8 /* !! */  = (int)var1_1[var2_5];
                var3_6 = (var5_8 /* !! */  & 255) + (var3_6 << 8);
            }
            var4_7 /* !! */  = var2_5;
        }
        var3_6 = var4_7 /* !! */  + var3_6 + 1;
        var2_5 = var3_6 + 1;
        var3_6 = var5_8 /* !! */  = var1_1[var3_6] & 255;
        var4_7 /* !! */  = var2_5;
        if ((var5_8 /* !! */  & 128) != 0) {
            var3_6 = 0;
            for (var4_7 /* !! */  = var5_8 /* !! */  & 127; var4_7 /* !! */  > 0; ++var2_5, --var4_7 /* !! */ ) {
                var5_8 /* !! */  = (int)var1_1[var2_5];
                var3_6 = (var3_6 << 8) + (var5_8 /* !! */  & 255);
            }
            var4_7 /* !! */  = var2_5;
        }
        this.P_array = new byte[var3_6];
        System.arraycopy(var1_1, var4_7 /* !! */ , this.P_array, 0, var3_6);
        var2_5 = var4_7 /* !! */  + var3_6 + 1;
        var3_6 = var2_5 + 1;
        var5_8 /* !! */  = var1_1[var2_5] & 255;
        if ((var5_8 /* !! */  & 128) != 0) {
            var4_7 /* !! */  = var5_8 /* !! */  & 127;
            var2_5 = 0;
            do {
                var5_8 /* !! */  = var2_5;
                var6_9 = var3_6;
                if (var4_7 /* !! */  > 0) {
                    var2_5 = (var2_5 << 8) + (var1_1[var3_6] & 255);
                    ++var3_6;
                    --var4_7 /* !! */ ;
                    continue;
                }
                break;
            } while (true);
        } else {
            var6_9 = var3_6;
        }
        this.Q_array = new byte[var5_8 /* !! */ ];
        System.arraycopy(var1_1, var6_9, this.Q_array, 0, var5_8 /* !! */ );
        var2_5 = var6_9 + var5_8 /* !! */  + 1;
        var3_6 = var2_5 + 1;
        var5_8 /* !! */  = var1_1[var2_5] & 255;
        if ((var5_8 /* !! */  & 128) != 0) {
            var4_7 /* !! */  = var5_8 /* !! */  & 127;
            var2_5 = 0;
            do {
                var5_8 /* !! */  = var2_5;
                var6_9 = var3_6;
                if (var4_7 /* !! */  > 0) {
                    var2_5 = (var2_5 << 8) + (var1_1[var3_6] & 255);
                    ++var3_6;
                    --var4_7 /* !! */ ;
                    continue;
                }
                break;
            } while (true);
        } else {
            var6_9 = var3_6;
        }
        this.G_array = new byte[var5_8 /* !! */ ];
        System.arraycopy(var1_1, var6_9, this.G_array, 0, var5_8 /* !! */ );
        var2_5 = var6_9 + var5_8 /* !! */  + 1;
        var3_6 = var2_5 + 1;
        var5_8 /* !! */  = var1_1[var2_5] & 255;
        if ((var5_8 /* !! */  & 128) != 0) {
            var4_7 /* !! */  = var5_8 /* !! */  & 127;
            var2_5 = 0;
            do {
                var5_8 /* !! */  = var2_5;
                var6_9 = var3_6;
                if (var4_7 /* !! */  > 0) {
                    var2_5 = (var2_5 << 8) + (var1_1[var3_6] & 255);
                    ++var3_6;
                    --var4_7 /* !! */ ;
                    continue;
                }
                break;
            } while (true);
        } else {
            var6_9 = var3_6;
        }
        this.pub_array = new byte[var5_8 /* !! */ ];
        System.arraycopy(var1_1, var6_9, this.pub_array, 0, var5_8 /* !! */ );
        var2_5 = var6_9 + var5_8 /* !! */  + 1;
        var3_6 = var2_5 + 1;
        var5_8 /* !! */  = var1_1[var2_5] & 255;
        if ((var5_8 /* !! */  & 128) != 0) {
            var4_7 /* !! */  = var5_8 /* !! */  & 127;
            var2_5 = 0;
            do {
                var5_8 /* !! */  = var2_5;
                var6_9 = var3_6;
                if (var4_7 /* !! */  > 0) {
                    var2_5 = (var2_5 << 8) + (var1_1[var3_6] & 255);
                    ++var3_6;
                    --var4_7 /* !! */ ;
                    continue;
                }
                break;
            } while (true);
        } else {
            var6_9 = var3_6;
        }
        try {
            this.prv_array = new byte[var5_8 /* !! */ ];
            System.arraycopy(var1_1, var6_9, this.prv_array, 0, var5_8 /* !! */ );
            if (this.P_array == null) return true;
            this.key_size = new BigInteger(this.P_array).bitLength();
            return true;
        }
        catch (Exception var1_2) {
            return false;
        }
    }
}
