/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.KeyPair;
import com.jcraft.jsch.KeyPairGenECDSA;
import com.jcraft.jsch.Signature;
import com.jcraft.jsch.SignatureECDSA;
import com.jcraft.jsch.Util;

public class KeyPairECDSA
extends KeyPair {
    private static final byte[] begin;
    private static final byte[] end;
    private static String[] names;
    private static byte[][] oids;
    private int key_size;
    private byte[] name = Util.str2byte(names[0]);
    private byte[] prv_array;
    private byte[] r_array;
    private byte[] s_array;

    static {
        oids = new byte[][]{{6, 8, 42, -122, 72, -50, 61, 3, 1, 7}, {6, 5, 43, -127, 4, 0, 34}, {6, 5, 43, -127, 4, 0, 35}};
        names = new String[]{"nistp256", "nistp384", "nistp521"};
        begin = Util.str2byte("-----BEGIN EC PRIVATE KEY-----");
        end = Util.str2byte("-----END EC PRIVATE KEY-----");
    }

    public KeyPairECDSA(JSch jSch) {
        this(jSch, null, null, null, null);
    }

    public KeyPairECDSA(JSch jSch, byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4) {
        super(jSch);
        int n = 256;
        this.key_size = 256;
        if (arrby != null) {
            this.name = arrby;
        }
        this.r_array = arrby2;
        this.s_array = arrby3;
        this.prv_array = arrby4;
        if (arrby4 != null) {
            if (arrby4.length >= 64) {
                n = 521;
            } else if (arrby4.length >= 48) {
                n = 384;
            }
            this.key_size = n;
        }
    }

    static byte[][] fromPoint(byte[] arrby) {
        int n = 0;
        while (arrby[n] != 4) {
            ++n;
        }
        byte[] arrby2 = new byte[(arrby.length - ++n) / 2];
        byte[] arrby3 = new byte[(arrby.length - n) / 2];
        System.arraycopy(arrby, n, arrby2, 0, arrby2.length);
        System.arraycopy(arrby, n + arrby2.length, arrby3, 0, arrby3.length);
        return new byte[][]{arrby2, arrby3};
    }

    static KeyPair fromSSHAgent(JSch object, Buffer arrby) throws JSchException {
        arrby = arrby.getBytes(5, "invalid key format");
        byte[] arrby2 = arrby[1];
        byte[][] arrby3 = KeyPairECDSA.fromPoint(arrby[2]);
        object = new KeyPairECDSA((JSch)object, arrby2, arrby3[0], arrby3[1], arrby[3]);
        object.publicKeyComment = new String(arrby[4]);
        object.vendor = 0;
        return object;
    }

    static byte[] toPoint(byte[] arrby, byte[] arrby2) {
        byte[] arrby3 = new byte[arrby.length + 1 + arrby2.length];
        arrby3[0] = 4;
        System.arraycopy(arrby, 0, arrby3, 1, arrby.length);
        System.arraycopy(arrby2, 0, arrby3, 1 + arrby.length, arrby2.length);
        return arrby3;
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
        byte[] arrby = new byte[]();
        arrby.append("ecdsa-sha2-");
        arrby.append(new String(this.name));
        buffer.putString(Util.str2byte(arrby.toString()));
        buffer.putString(this.name);
        buffer.putString(KeyPairECDSA.toPoint(this.r_array, this.s_array));
        buffer.putString(this.prv_array);
        buffer.putString(Util.str2byte(this.publicKeyComment));
        arrby = new byte[buffer.getLength()];
        buffer.getByte(arrby, 0, arrby.length);
        return arrby;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void generate(int n) throws JSchException {
        String[] arrstring;
        this.key_size = n;
        try {
            arrstring = this.jsch;
            arrstring = (KeyPairGenECDSA)Class.forName(JSch.getConfig("keypairgen.ecdsa")).newInstance();
            arrstring.init(n);
            this.prv_array = arrstring.getD();
            this.r_array = arrstring.getR();
            this.s_array = arrstring.getS();
            arrstring = names;
            n = this.prv_array.length >= 64 ? 2 : (this.prv_array.length >= 48 ? 1 : 0);
        }
        catch (Exception exception) {
            if (exception instanceof Throwable) {
                throw new JSchException(exception.toString(), exception);
            }
            throw new JSchException(exception.toString());
        }
        this.name = Util.str2byte(arrstring[n]);
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
        return 3;
    }

    @Override
    byte[] getKeyTypeName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ecdsa-sha2-");
        stringBuilder.append(new String(this.name));
        return Util.str2byte(stringBuilder.toString());
    }

    @Override
    byte[] getPrivateKey() {
        byte[] arrby = new byte[]{1};
        byte[][] arrby2 = oids;
        int n = this.r_array.length >= 64 ? 2 : (this.r_array.length >= 48 ? 1 : 0);
        arrby2 = arrby2[n];
        byte[] arrby3 = KeyPairECDSA.toPoint(this.r_array, this.s_array);
        n = (arrby3.length + 1 & 128) == 0 ? 3 : 4;
        byte[] arrby4 = new byte[arrby3.length + n];
        System.arraycopy(arrby3, 0, arrby4, n, arrby3.length);
        arrby4[0] = 3;
        if (n == 3) {
            arrby4[1] = (byte)(arrby3.length + 1);
        } else {
            arrby4[1] = -127;
            arrby4[2] = (byte)(arrby3.length + 1);
        }
        n = this.countLength(arrby.length) + 1 + arrby.length + 1 + this.countLength(this.prv_array.length) + this.prv_array.length + 1 + this.countLength(arrby2.length) + arrby2.length + 1 + this.countLength(arrby4.length) + arrby4.length;
        arrby3 = new byte[1 + this.countLength(n) + n];
        this.writeDATA(arrby3, (byte)-95, this.writeDATA(arrby3, (byte)-96, this.writeOCTETSTRING(arrby3, this.writeINTEGER(arrby3, this.writeSEQUENCE(arrby3, 0, n), arrby), this.prv_array), (byte[])arrby2), arrby4);
        return arrby3;
    }

    @Override
    public byte[] getPublicKeyBlob() {
        byte[] arrby = super.getPublicKeyBlob();
        if (arrby != null) {
            return arrby;
        }
        if (this.r_array == null) {
            return null;
        }
        arrby = new byte[3][];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ecdsa-sha2-");
        stringBuilder.append(new String(this.name));
        arrby[0] = (byte)Util.str2byte(stringBuilder.toString());
        arrby[1] = (byte)this.name;
        arrby[2] = (byte)new byte[this.r_array.length + 1 + this.s_array.length];
        arrby[2][0] = 4;
        System.arraycopy(this.r_array, 0, arrby[2], 1, this.r_array.length);
        System.arraycopy(this.s_array, 0, arrby[2], 1 + this.r_array.length, this.s_array.length);
        return Buffer.fromBytes((byte[][])arrby).buffer;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public byte[] getSignature(byte[] arrby) {
        try {
            Object object = this.jsch;
            object = (SignatureECDSA)Class.forName(JSch.getConfig("signature.ecdsa")).newInstance();
            object.init();
            object.setPrvKey(this.prv_array);
            object.update(arrby);
            arrby = object.sign();
            object = new StringBuilder();
            object.append("ecdsa-sha2-");
            object.append(new String(this.name));
            return Buffer.fromBytes((byte[][])new byte[][]{Util.str2byte((String)object.toString()), arrby}).buffer;
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
            object = (SignatureECDSA)Class.forName(JSch.getConfig("signature.ecdsa")).newInstance();
            object.init();
            if (this.r_array == null && this.s_array == null && this.getPublicKeyBlob() != null) {
                byte[][] arrby = new byte[][](this.getPublicKeyBlob());
                arrby.getString();
                arrby.getString();
                arrby = KeyPairECDSA.fromPoint(arrby.getString());
                this.r_array = arrby[0];
                this.s_array = arrby[1];
            }
            object.setPubKey(this.r_array, this.s_array);
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    boolean parse(byte[] arrby) {
        block26 : {
            int n;
            block28 : {
                block27 : {
                    int n2;
                    int n3;
                    int n4;
                    if (this.vendor == 1) {
                        return false;
                    }
                    if (this.vendor == 2) {
                        return false;
                    }
                    if (arrby[0] != 48) {
                        return false;
                    }
                    n = arrby[1] & 255;
                    if ((n & 128) != 0) {
                        n4 = n & 127;
                        n = 2;
                        do {
                            n2 = n;
                            if (n4 > 0) {
                                n2 = arrby[n];
                                ++n;
                                --n4;
                                continue;
                            }
                            break;
                        } while (true);
                    } else {
                        n2 = 2;
                    }
                    if (arrby[n2] != 2) {
                        return false;
                    }
                    n4 = n2 + 1;
                    n = n4 + 1;
                    int n5 = arrby[n4] & 255;
                    n2 = n;
                    n4 = n5;
                    if ((n5 & 128) != 0) {
                        n4 = 0;
                        for (n2 = n5 & 127; n2 > 0; --n2, ++n) {
                            n5 = arrby[n];
                            n4 = (n5 & 255) + (n4 << 8);
                        }
                        n2 = n;
                    }
                    n4 = n2 + n4 + 1;
                    n = n4 + 1;
                    n4 = n5 = arrby[n4] & 255;
                    n2 = n;
                    if ((n5 & 128) != 0) {
                        n4 = 0;
                        for (n2 = n5 & 127; n2 > 0; ++n, --n2) {
                            n5 = arrby[n];
                            n4 = (n4 << 8) + (n5 & 255);
                        }
                        n2 = n;
                    }
                    this.prv_array = new byte[n4];
                    System.arraycopy(arrby, n2, this.prv_array, 0, n4);
                    n = n2 + n4 + 1;
                    n4 = n + 1;
                    n2 = arrby[n] & 255;
                    if ((n2 & 128) != 0) {
                        n3 = n2 & 127;
                        n = 0;
                        do {
                            n2 = n;
                            n5 = n4;
                            if (n3 > 0) {
                                n = (n << 8) + (arrby[n4] & 255);
                                ++n4;
                                --n3;
                                continue;
                            }
                            break;
                        } while (true);
                    } else {
                        n5 = n4;
                    }
                    try {
                        byte[] arrby2 = new byte[n2];
                        System.arraycopy(arrby, n5, arrby2, 0, n2);
                        for (n = 0; n < oids.length; ++n) {
                            if (!Util.array_equals(oids[n], arrby2)) continue;
                            this.name = Util.str2byte(names[n]);
                            break;
                        }
                        n4 = n5 + n2 + 1;
                        n = n4 + 1;
                        n5 = arrby[n4] & 255;
                        if ((n5 & 128) != 0) {
                            n2 = n5 & 127;
                            n4 = 0;
                            do {
                                n5 = n4;
                                n3 = n;
                                if (n2 > 0) {
                                    n4 = (n4 << 8) + (arrby[n] & 255);
                                    ++n;
                                    --n2;
                                    continue;
                                }
                                break;
                            } while (true);
                        } else {
                            n3 = n;
                        }
                        arrby2 = new byte[n5];
                        System.arraycopy(arrby, n3, arrby2, 0, n5);
                        arrby = KeyPairECDSA.fromPoint(arrby2);
                        this.r_array = (byte[])arrby[0];
                        this.s_array = (byte[])arrby[1];
                        if (this.prv_array == null) break block26;
                        if (this.prv_array.length < 64) break block27;
                        n = 521;
                        break block28;
                    }
                    catch (Exception exception) {
                        return false;
                    }
                }
                n = this.prv_array.length >= 48 ? 384 : 256;
            }
            this.key_size = n;
        }
        return true;
    }
}
