/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.Cipher;
import com.jcraft.jsch.HASH;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.KeyPairDSA;
import com.jcraft.jsch.KeyPairECDSA;
import com.jcraft.jsch.KeyPairPKCS8;
import com.jcraft.jsch.KeyPairRSA;
import com.jcraft.jsch.Random;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.Signature;
import com.jcraft.jsch.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Vector;

public abstract class KeyPair {
    public static final int DSA = 1;
    public static final int ECDSA = 3;
    public static final int ERROR = 0;
    public static final int RSA = 2;
    public static final int UNKNOWN = 4;
    static final int VENDOR_FSECURE = 1;
    static final int VENDOR_OPENSSH = 0;
    static final int VENDOR_PKCS8 = 3;
    static final int VENDOR_PUTTY = 2;
    private static final byte[] cr = Util.str2byte("\n");
    static byte[][] header = new byte[][]{Util.str2byte("Proc-Type: 4,ENCRYPTED"), Util.str2byte("DEK-Info: DES-EDE3-CBC,")};
    private static final String[] header1;
    private static final String[] header2;
    private static final String[] header3;
    private static byte[] space;
    private Cipher cipher;
    protected byte[] data = null;
    protected boolean encrypted = false;
    private HASH hash;
    private byte[] iv = null;
    JSch jsch = null;
    private byte[] passphrase;
    protected String publicKeyComment = "no comment";
    private byte[] publickeyblob = null;
    private Random random;
    int vendor = 0;

    static {
        space = Util.str2byte(" ");
        header1 = new String[]{"PuTTY-User-Key-File-2: ", "Encryption: ", "Comment: ", "Public-Lines: "};
        header2 = new String[]{"Private-Lines: "};
        header3 = new String[]{"Private-MAC: "};
    }

    public KeyPair(JSch jSch) {
        this.jsch = jSch;
    }

    private static byte a2b(byte by) {
        if (48 <= by && by <= 57) {
            return (byte)(by - 48);
        }
        return (byte)(by - 97 + 10);
    }

    private static byte b2a(byte by) {
        if (by >= 0 && by <= 9) {
            return (byte)(by + 48);
        }
        return (byte)(by - 10 + 65);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private byte[] decrypt(byte[] arrby, byte[] arrby2, byte[] arrby3) {
        try {
            arrby2 = this.genKey(arrby2, arrby3);
            this.cipher.init(1, arrby2, arrby3);
            Util.bzero(arrby2);
            arrby2 = new byte[arrby.length];
            this.cipher.update(arrby, 0, arrby.length, arrby2, 0);
            return arrby2;
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
    private byte[] encrypt(byte[] arrby, byte[][] arrby2, byte[] arrby3) {
        if (arrby3 == null) {
            return arrby;
        }
        if (this.cipher == null) {
            this.cipher = this.genCipher();
        }
        byte[] arrby4 = new byte[this.cipher.getIVSize()];
        arrby2[0] = arrby4;
        if (this.random == null) {
            this.random = this.genRandom();
        }
        this.random.fill(arrby4, 0, arrby4.length);
        arrby2 = this.genKey(arrby3, arrby4);
        int n = this.cipher.getIVSize();
        arrby3 = new byte[(arrby.length / n + 1) * n];
        System.arraycopy(arrby, 0, arrby3, 0, arrby.length);
        int n2 = n - arrby.length % n;
        for (n = arrby3.length - 1; arrby3.length - n2 <= n; --n) {
            arrby3[n] = (byte)n2;
        }
        try {
            this.cipher.init(0, (byte[])arrby2, arrby4);
            this.cipher.update(arrby3, 0, arrby3.length, arrby3, 0);
        }
        catch (Exception exception) {}
        Util.bzero((byte[])arrby2);
        return arrby3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Cipher genCipher() {
        try {
            JSch jSch = this.jsch;
            this.cipher = (Cipher)Class.forName(JSch.getConfig("3des-cbc")).newInstance();
            do {
                return this.cipher;
                break;
            } while (true);
        }
        catch (Exception exception) {
            return this.cipher;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private HASH genHash() {
        try {
            JSch jSch = this.jsch;
            this.hash = (HASH)Class.forName(JSch.getConfig("md5")).newInstance();
            this.hash.init();
            do {
                return this.hash;
                break;
            } while (true);
        }
        catch (Exception exception) {
            return this.hash;
        }
    }

    public static KeyPair genKeyPair(JSch jSch, int n) throws JSchException {
        return KeyPair.genKeyPair(jSch, n, 1024);
    }

    public static KeyPair genKeyPair(JSch object, int n, int n2) throws JSchException {
        object = n == 1 ? new KeyPairDSA((JSch)object) : (n == 2 ? new KeyPairRSA((JSch)object) : (n == 3 ? new KeyPairECDSA((JSch)object) : null));
        if (object != null) {
            object.generate(n2);
        }
        return object;
    }

    private Random genRandom() {
        if (this.random == null) {
            try {
                JSch jSch = this.jsch;
                this.random = (Random)Class.forName(JSch.getConfig("random")).newInstance();
            }
            catch (Exception exception) {
                PrintStream printStream = System.err;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("connect: random ");
                stringBuilder.append(exception);
                printStream.println(stringBuilder.toString());
            }
        }
        return this.random;
    }

    public static KeyPair load(JSch jSch, String string) throws JSchException {
        CharSequence charSequence = new StringBuilder();
        charSequence.append(string);
        charSequence.append(".pub");
        String string2 = charSequence.toString();
        charSequence = string2;
        if (!new File(string2).exists()) {
            charSequence = null;
        }
        return KeyPair.load(jSch, string, (String)charSequence);
    }

    public static KeyPair load(JSch object, String object2, String string) throws JSchException {
        byte[] arrby;
        block9 : {
            try {
                arrby = Util.fromFile((String)object2);
                if (string == null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append((String)object2);
                    stringBuilder.append(".pub");
                    object2 = stringBuilder.toString();
                    break block9;
                }
                object2 = string;
            }
            catch (IOException iOException) {
                throw new JSchException(iOException.toString(), iOException);
            }
        }
        try {
            object2 = Util.fromFile((String)object2);
        }
        catch (IOException iOException) {
            if (string != null) {
                throw new JSchException(iOException.toString(), iOException);
            }
            object2 = null;
        }
        try {
            object = KeyPair.load((JSch)object, arrby, object2);
            return object;
        }
        finally {
            Util.bzero(arrby);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public static KeyPair load(JSch var0, byte[] var1_2, byte[] var2_3) throws JSchException {
        block129 : {
            block128 : {
                block127 : {
                    block155 : {
                        block154 : {
                            block125 : {
                                block152 : {
                                    block150 : {
                                        block122 : {
                                            block161 : {
                                                block160 : {
                                                    block148 : {
                                                        block146 : {
                                                            block147 : {
                                                                block121 : {
                                                                    block120 : {
                                                                        block140 : {
                                                                            block142 : {
                                                                                block158 : {
                                                                                    block118 : {
                                                                                        block159 : {
                                                                                            block141 : {
                                                                                                block116 : {
                                                                                                    block117 : {
                                                                                                        block157 : {
                                                                                                            block139 : {
                                                                                                                block156 : {
                                                                                                                    block138 : {
                                                                                                                        block137 : {
                                                                                                                            block134 : {
                                                                                                                                block115 : {
                                                                                                                                    block133 : {
                                                                                                                                        block130 : {
                                                                                                                                            block144 : {
                                                                                                                                                block151 : {
                                                                                                                                                    block124 : {
                                                                                                                                                        block153 : {
                                                                                                                                                            block126 : {
                                                                                                                                                                block145 : {
                                                                                                                                                                    block149 : {
                                                                                                                                                                        block119 : {
                                                                                                                                                                            block143 : {
                                                                                                                                                                                block131 : {
                                                                                                                                                                                    block136 : {
                                                                                                                                                                                        block135 : {
                                                                                                                                                                                            block132 : {
                                                                                                                                                                                                var15_7 = new byte[8];
                                                                                                                                                                                                var17_12 = "";
                                                                                                                                                                                                if (var2_3 == null && var1_2 != null && var1_2.length > 11 && var1_2[0] == 0 && var1_2[1] == 0 && var1_2[2] == 0 && (var1_2[3] == 7 || var1_2[3] == 19)) {
                                                                                                                                                                                                    var2_3 = new Buffer(var1_2);
                                                                                                                                                                                                    var2_3.skip(var1_2.length);
                                                                                                                                                                                                    var13_22 = new String(var2_3.getString());
                                                                                                                                                                                                    var2_3.rewind();
                                                                                                                                                                                                    if (var13_22.equals("ssh-rsa")) {
                                                                                                                                                                                                        return KeyPairRSA.fromSSHAgent((JSch)var0, (Buffer)var2_3);
                                                                                                                                                                                                    }
                                                                                                                                                                                                    if (var13_22.equals("ssh-dss")) {
                                                                                                                                                                                                        return KeyPairDSA.fromSSHAgent((JSch)var0, (Buffer)var2_3);
                                                                                                                                                                                                    }
                                                                                                                                                                                                    if (!(var13_22.equals("ecdsa-sha2-nistp256") || var13_22.equals("ecdsa-sha2-nistp384") || var13_22.equals("ecdsa-sha2-nistp512"))) {
                                                                                                                                                                                                        var0 = new StringBuilder();
                                                                                                                                                                                                        var0.append("privatekey: invalid key ");
                                                                                                                                                                                                        var0.append(new String(var1_2, 4, 7));
                                                                                                                                                                                                        throw new JSchException(var0.toString());
                                                                                                                                                                                                    }
                                                                                                                                                                                                    return KeyPairECDSA.fromSSHAgent((JSch)var0, (Buffer)var2_3);
                                                                                                                                                                                                }
                                                                                                                                                                                                if (var1_2 == null) ** GOTO lbl23
                                                                                                                                                                                                var13_23 = KeyPair.loadPPK((JSch)var0, var1_2);
                                                                                                                                                                                                if (var13_23 != null) {
                                                                                                                                                                                                    return var13_23;
                                                                                                                                                                                                }
lbl23: // 3 sources:
                                                                                                                                                                                                var7_24 = var1_2 != null ? var1_2.length : 0;
lbl24: // 2 sources:
                                                                                                                                                                                                var4_26 = var5_27;
                                                                                                                                                                                                if (var5_27 >= var7_24) break block131;
                                                                                                                                                                                                if (var1_2[var5_27] != 66 || (var4_26 = var5_27 + 3) >= var7_24 || var1_2[var5_27 + 1] != 69 || var1_2[var5_27 + 2] != 71 || var1_2[var4_26] != 73) break block132;
                                                                                                                                                                                                var4_26 = (var5_27 += 6) + 2;
                                                                                                                                                                                                if (var4_26 >= var7_24) {
                                                                                                                                                                                                    var0 = new StringBuilder();
                                                                                                                                                                                                    var0.append("invalid privatekey: ");
                                                                                                                                                                                                    var0.append(var1_2);
                                                                                                                                                                                                    throw new JSchException(var0.toString());
                                                                                                                                                                                                    do {
                                                                                                                                                                                                        var0 = new StringBuilder();
                                                                                                                                                                                                        var0.append("invalid privatekey: ");
                                                                                                                                                                                                        var0.append(var1_2);
                                                                                                                                                                                                        throw new JSchException(var0.toString());
                                                                                                                                                                                                        break;
                                                                                                                                                                                                    } while (true);
                                                                                                                                                                                                }
                                                                                                                                                                                                break block133;
                                                                                                                                                                                            }
                                                                                                                                                                                            if (var1_2[var5_27] == 65 && (var4_26 = var5_27 + 7) < var7_24 && var1_2[var5_27 + 1] == 69 && var1_2[var5_27 + 2] == 83 && var1_2[var5_27 + 3] == 45 && var1_2[var5_27 + 4] == 50 && var1_2[var5_27 + 5] == 53 && var1_2[var5_27 + 6] == 54 && var1_2[var4_26] == 45) {
                                                                                                                                                                                                var4_26 = var5_27 + 8;
                                                                                                                                                                                                if (!Session.checkCipher(JSch.getConfig("aes256-cbc"))) {
                                                                                                                                                                                                    var0 = new StringBuilder();
                                                                                                                                                                                                    var0.append("privatekey: aes256-cbc is not available ");
                                                                                                                                                                                                    var0.append(var1_2);
                                                                                                                                                                                                    throw new JSchException(var0.toString());
                                                                                                                                                                                                }
                                                                                                                                                                                                var13_23 = (Cipher)Class.forName(JSch.getConfig("aes256-cbc")).newInstance();
                                                                                                                                                                                                var14_32 = new byte[var13_23.getIVSize()];
                                                                                                                                                                                            }
                                                                                                                                                                                            if (var1_2[var5_27] == 65 && (var4_26 = var5_27 + 7) < var7_24 && var1_2[var5_27 + 1] == 69 && var1_2[var5_27 + 2] == 83 && var1_2[var5_27 + 3] == 45 && var1_2[var5_27 + 4] == 49 && var1_2[var5_27 + 5] == 57 && var1_2[var5_27 + 6] == 50 && var1_2[var4_26] == 45) {
                                                                                                                                                                                                var4_26 = var5_27 + 8;
                                                                                                                                                                                                if (!Session.checkCipher(JSch.getConfig("aes192-cbc"))) {
                                                                                                                                                                                                    var0 = new StringBuilder();
                                                                                                                                                                                                    var0.append("privatekey: aes192-cbc is not available ");
                                                                                                                                                                                                    var0.append(var1_2);
                                                                                                                                                                                                    throw new JSchException(var0.toString());
                                                                                                                                                                                                }
                                                                                                                                                                                                var13_23 = (Cipher)Class.forName(JSch.getConfig("aes192-cbc")).newInstance();
                                                                                                                                                                                                var14_32 = new byte[var13_23.getIVSize()];
                                                                                                                                                                                            }
                                                                                                                                                                                            if (var1_2[var5_27] != 65 || (var4_26 = var5_27 + 7) >= var7_24 || var1_2[var5_27 + 1] != 69 || var1_2[var5_27 + 2] != 83 || var1_2[var5_27 + 3] != 45 || var1_2[var5_27 + 4] != 49 || var1_2[var5_27 + 5] != 50 || var1_2[var5_27 + 6] != 56 || var1_2[var4_26] != 45) break block134;
                                                                                                                                                                                            var4_26 = var5_27 + 8;
                                                                                                                                                                                            if (Session.checkCipher(JSch.getConfig("aes128-cbc"))) {
                                                                                                                                                                                                var13_23 = (Cipher)Class.forName(JSch.getConfig("aes128-cbc")).newInstance();
                                                                                                                                                                                                var14_32 = new byte[var13_23.getIVSize()];
                                                                                                                                                                                                break block115;
                                                                                                                                                                                            }
                                                                                                                                                                                            var0 = new StringBuilder();
                                                                                                                                                                                            var0.append("privatekey: aes128-cbc is not available ");
                                                                                                                                                                                            var0.append(var1_2);
                                                                                                                                                                                            throw new JSchException(var0.toString());
lbl74: // 2 sources:
                                                                                                                                                                                            var13_23 = var15_8;
                                                                                                                                                                                            var4_26 = var5_27;
                                                                                                                                                                                            var14_32 = var16_33;
                                                                                                                                                                                            if (var8_29 >= ((void)var15_8).length) break block116;
                                                                                                                                                                                            var4_26 = var5_27 + 1;
                                                                                                                                                                                            var9_30 = KeyPair.a2b(var1_2[var5_27]);
                                                                                                                                                                                            var5_27 = var4_26 + 1;
                                                                                                                                                                                            var15_8[var8_29] = (byte)((var9_30 << 4 & 240) + (KeyPair.a2b(var1_2[var4_26]) & 15));
                                                                                                                                                                                            ++var8_29;
                                                                                                                                                                                            ** GOTO lbl74
lbl86: // 1 sources:
                                                                                                                                                                                            if (var1_2[var5_27] != 13) break block135;
                                                                                                                                                                                            var4_26 = var5_27 + 1;
                                                                                                                                                                                            ** try [egrp 7[TRYBLOCK] [13 : 1033->1040)] { 
lbl89: // 1 sources:
                                                                                                                                                                                            if (var4_26 >= var1_2.length || var1_2[var4_26] != 10) break block135;
                                                                                                                                                                                            var13_23 = var15_8;
                                                                                                                                                                                            var14_32 = var16_33;
                                                                                                                                                                                            break block116;
                                                                                                                                                                                        }
                                                                                                                                                                                        if (var1_2[var5_27] != 10) break block117;
                                                                                                                                                                                        var4_26 = var5_27 + 1;
                                                                                                                                                                                        if (var4_26 >= var1_2.length) break block117;
                                                                                                                                                                                        if (var1_2[var4_26] != 10) break block136;
                                                                                                                                                                                        var4_26 = var5_27 + 2;
                                                                                                                                                                                        break block131;
                                                                                                                                                                                    }
                                                                                                                                                                                    if (var1_2[var4_26] != 13) break block137;
                                                                                                                                                                                    var8_29 = var5_27 + 2;
                                                                                                                                                                                    ** try [egrp 9[TRYBLOCK] [15 : 1115->1122)] { 
lbl105: // 1 sources:
                                                                                                                                                                                    if (var8_29 >= var1_2.length || var1_2[var8_29] != 10) break block137;
                                                                                                                                                                                    var4_26 = var5_27 + 3;
                                                                                                                                                                                    break block131;
lbl108: // 2 sources:
                                                                                                                                                                                    ** try [egrp 10[TRYBLOCK] [16 : 1140->1147)] { 
lbl109: // 1 sources:
                                                                                                                                                                                    if (var8_29 < var1_2.length && var1_2[var8_29] != 10) break block138;
                                                                                                                                                                                    break block139;
                                                                                                                                                                                }
lbl112: // 2 sources:
                                                                                                                                                                                if (var1_2 == null) break block140;
                                                                                                                                                                                if (var3_25 != 0) break block141;
                                                                                                                                                                                var0 = new StringBuilder();
                                                                                                                                                                                var0.append("invalid privatekey: ");
                                                                                                                                                                                var0.append(var1_2);
                                                                                                                                                                                throw new JSchException(var0.toString());
lbl119: // 1 sources:
                                                                                                                                                                                do {
                                                                                                                                                                                    var14_32 = new byte[var5_27];
                                                                                                                                                                                    System.arraycopy(var1_2, var4_26, var14_32, 0, ((byte[])var14_32).length);
                                                                                                                                                                                    var4_26 = ((byte[])var14_32).length;
                                                                                                                                                                                    var5_27 = 0;
                                                                                                                                                                                    break;
                                                                                                                                                                                } while (true);
lbl125: // 2 sources:
                                                                                                                                                                                var8_29 = var5_27 + 1;
                                                                                                                                                                                System.arraycopy(var14_32, var8_29, var14_32, var5_27 - var7_24, var4_26 - var8_29);
                                                                                                                                                                                var8_29 = var4_26;
                                                                                                                                                                                if (var7_24 == 0) break block118;
                                                                                                                                                                                var8_29 = var4_26 - 1;
                                                                                                                                                                                break block118;
lbl132: // 2 sources:
                                                                                                                                                                                if ((var4_26 = var5_27 - 0) <= 0) break block142;
                                                                                                                                                                                var13_23 = Util.fromBase64((byte[])var14_32, 0, var4_26);
lbl135: // 2 sources:
                                                                                                                                                                                Util.bzero((byte[])var14_32);
                                                                                                                                                                                break block143;
lbl137: // 1 sources:
                                                                                                                                                                                do {
                                                                                                                                                                                    var0 = new StringBuilder();
                                                                                                                                                                                    var0.append("invalid privatekey: ");
                                                                                                                                                                                    var0.append(var1_2);
                                                                                                                                                                                    throw new JSchException(var0.toString());
                                                                                                                                                                                    break;
                                                                                                                                                                                } while (true);
                                                                                                                                                                            }
lbl143: // 2 sources:
                                                                                                                                                                            var18_36 = var13_23;
                                                                                                                                                                            var12_45 = var11_31;
                                                                                                                                                                            if (var13_23 == null) break block119;
                                                                                                                                                                            var18_37 = var13_23;
                                                                                                                                                                            var12_45 = var11_31;
                                                                                                                                                                            if (((KeyPair)var13_23).length <= 4) break block119;
                                                                                                                                                                            var18_38 = var13_23;
                                                                                                                                                                            var12_45 = var11_31;
                                                                                                                                                                            if (var13_23[0] == 63) {
                                                                                                                                                                                var18_39 = var13_23;
                                                                                                                                                                                var12_45 = var11_31;
                                                                                                                                                                                if (var13_23[1] == 111) {
                                                                                                                                                                                    var18_40 = var13_23;
                                                                                                                                                                                    var12_45 = var11_31;
                                                                                                                                                                                    if (var13_23[2] == -7) {
                                                                                                                                                                                        var18_41 = var13_23;
                                                                                                                                                                                        var12_45 = var11_31;
                                                                                                                                                                                        if (var13_23[3] == -21) {
                                                                                                                                                                                            var14_32 = new Buffer((byte[])var13_23);
                                                                                                                                                                                            var14_32.getInt();
                                                                                                                                                                                            var14_32.getInt();
                                                                                                                                                                                            var14_32.getString();
                                                                                                                                                                                            var19_46 = Util.byte2str(var14_32.getString());
                                                                                                                                                                                            if (var19_46.equals("3des-cbc")) {
                                                                                                                                                                                                var14_32.getInt();
                                                                                                                                                                                                var14_32.getByte(new byte[((Object)var13_23).length - var14_32.getOffSet()]);
                                                                                                                                                                                                var0 = new StringBuilder();
                                                                                                                                                                                                var0.append("unknown privatekey format: ");
                                                                                                                                                                                                var0.append(var1_2);
                                                                                                                                                                                                throw new JSchException(var0.toString());
                                                                                                                                                                                            }
                                                                                                                                                                                            var18_42 = var13_23;
                                                                                                                                                                                            var12_45 = var11_31;
                                                                                                                                                                                            if (!var19_46.equals("none")) ** GOTO lbl189
                                                                                                                                                                                            var14_32.getInt();
                                                                                                                                                                                            var14_32.getInt();
                                                                                                                                                                                            var18_43 = new byte[((Object)var13_23).length - var14_32.getOffSet()];
                                                                                                                                                                                            var14_32.getByte(var18_43);
                                                                                                                                                                                            var12_45 = false;
lbl183: // 1 sources:
                                                                                                                                                                                            do {
                                                                                                                                                                                                if (var0_1 instanceof JSchException) {
                                                                                                                                                                                                    throw (JSchException)var0_1;
                                                                                                                                                                                                }
                                                                                                                                                                                                if (var0_1 instanceof Throwable) {
                                                                                                                                                                                                    throw new JSchException(var0_1.toString(), var0_1);
                                                                                                                                                                                                }
                                                                                                                                                                                                throw new JSchException(var0_1.toString());
                                                                                                                                                                                                break;
                                                                                                                                                                                            } while (true);
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                }
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                        if (var2_3 == null) break block144;
                                                                                                                                                                        var14_32 = var17_12;
                                                                                                                                                                        var9_30 = ((Object)var2_3).length;
                                                                                                                                                                        var14_32 = var17_12;
                                                                                                                                                                        if (((Object)var2_3).length <= 4 || var2_3[0] != 45 || var2_3[1] != 45 || var2_3[2] != 45 || var2_3[3] != 45) break block145;
                                                                                                                                                                        var4_26 = 0;
                                                                                                                                                                        do {
                                                                                                                                                                            ++var4_26;
                                                                                                                                                                            var14_32 = var17_12;
                                                                                                                                                                            ** try [egrp 16[TRYBLOCK] [28 : 1708->1715)] { 
lbl201: // 1 sources:
                                                                                                                                                                        } while (((Object)var2_3).length > var4_26 && var2_3[var4_26] != 10);
                                                                                                                                                                        var14_32 = var17_12;
                                                                                                                                                                        if (((Object)var2_3).length > var4_26) break block120;
                                                                                                                                                                        var7_24 = 0;
                                                                                                                                                                        var13_23 = var17_12;
                                                                                                                                                                        break block121;
lbl208: // 2 sources:
                                                                                                                                                                        var14_32 = var13_23;
                                                                                                                                                                        if (var8_29 >= ((Object)var2_3).length || (var10_47 = var2_3[var8_29]) == 10) break block146;
                                                                                                                                                                        if (var2_3[var8_29] != 58) break block147;
                                                                                                                                                                        var8_29 = 1;
                                                                                                                                                                        break block148;
lbl213: // 2 sources:
                                                                                                                                                                        var5_27 = var3_25;
                                                                                                                                                                        var14_32 = var13_23;
                                                                                                                                                                        if (((Object)var2_3).length > var4_26) break block122;
                                                                                                                                                                        var7_24 = 0;
                                                                                                                                                                        break block122;
lbl219: // 3 sources:
                                                                                                                                                                        if (var7_24 == 0 || var8_29 >= var9_30) break block149;
                                                                                                                                                                        if (var2_3[var8_29] != 10) break block150;
                                                                                                                                                                        var5_27 = var3_25;
                                                                                                                                                                        var14_32 = var13_23;
                                                                                                                                                                        System.arraycopy(var2_3, var8_29 + 1, var2_3, var8_29, var9_30 - var8_29 - 1);
                                                                                                                                                                        --var9_30;
                                                                                                                                                                        ** GOTO lbl219
                                                                                                                                                                    }
lbl228: // 2 sources:
                                                                                                                                                                    var5_27 = var3_25;
                                                                                                                                                                    var14_32 = var13_23;
                                                                                                                                                                    if (var7_24 != 0) {
                                                                                                                                                                        block123 : {
                                                                                                                                                                            var5_27 = var3_25;
                                                                                                                                                                            var14_32 = var13_23;
                                                                                                                                                                            var17_13 = Util.fromBase64((byte[])var2_3, var4_26, var8_29 - var4_26);
                                                                                                                                                                            if (var1_2 == null) break block123;
                                                                                                                                                                            var4_26 = var3_25;
                                                                                                                                                                            var2_3 = var17_13;
                                                                                                                                                                            var14_32 = var13_23;
                                                                                                                                                                            if (var3_25 != 4) break block124;
                                                                                                                                                                        }
                                                                                                                                                                        if (var17_13[8] == 100) {
                                                                                                                                                                            var4_26 = 1;
                                                                                                                                                                            var2_3 = var17_13;
                                                                                                                                                                            var14_32 = var13_23;
                                                                                                                                                                        } else {
                                                                                                                                                                            var4_26 = var3_25;
                                                                                                                                                                            var2_3 = var17_13;
                                                                                                                                                                            var14_32 = var13_23;
                                                                                                                                                                            if (var17_13[8] == 114) {
                                                                                                                                                                                var4_26 = 2;
                                                                                                                                                                                var2_3 = var17_13;
                                                                                                                                                                                var14_32 = var13_23;
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                        {
                                                                                                                                                                            break block124;
                                                                                                                                                                        }
                                                                                                                                                                    }
lbl254: // 4 sources:
                                                                                                                                                                    var2_3 = null;
                                                                                                                                                                    var4_26 = var5_27;
                                                                                                                                                                    break block124;
                                                                                                                                                                }
                                                                                                                                                                var19_46 = "";
                                                                                                                                                                if (var2_3[0] != 115 || var2_3[1] != 115 || var2_3[2] != 104 || var2_3[3] != 45) break block151;
                                                                                                                                                                var4_26 = var3_25;
                                                                                                                                                                if (var1_2 != null) break block125;
                                                                                                                                                                var5_27 = var3_25;
                                                                                                                                                                var14_32 = var19_46;
                                                                                                                                                                var4_26 = var3_25;
                                                                                                                                                                if (((Object)var2_3).length <= 7) break block125;
                                                                                                                                                                if (var2_3[4] != 100) break block152;
                                                                                                                                                                var4_26 = 1;
                                                                                                                                                                break block125;
lbl270: // 1 sources:
                                                                                                                                                                var5_27 = var4_26;
                                                                                                                                                                var14_32 = var19_46;
                                                                                                                                                                var13_23 = Util.fromBase64((byte[])var2_3, var7_24, var3_25 - var7_24);
                                                                                                                                                                var7_24 = var3_25;
                                                                                                                                                                break block126;
lbl276: // 1 sources:
                                                                                                                                                                var13_23 = null;
                                                                                                                                                            }
                                                                                                                                                            var8_29 = var7_24 + 1;
                                                                                                                                                            var3_25 = var4_26;
                                                                                                                                                            var14_32 = var13_23;
                                                                                                                                                            var17_14 = var19_46;
                                                                                                                                                            if (var7_24 >= var9_30) break block153;
                                                                                                                                                            var3_25 = var8_29;
lbl284: // 2 sources:
                                                                                                                                                            if (var3_25 < var9_30 && var2_3[var3_25] != 10) break block154;
                                                                                                                                                            break block155;
lbl286: // 3 sources:
                                                                                                                                                            var3_25 = var4_26;
                                                                                                                                                            var14_32 = var13_23;
                                                                                                                                                            var17_15 = var19_46;
                                                                                                                                                            if (var8_29 < var5_27) {
                                                                                                                                                                var3_25 = var4_26;
                                                                                                                                                                var14_32 = var13_23;
                                                                                                                                                                var17_16 = new String((byte[])var2_3, var8_29, var5_27 - var8_29);
                                                                                                                                                                var14_32 = var13_23;
                                                                                                                                                                var3_25 = var4_26;
                                                                                                                                                            }
                                                                                                                                                        }
lbl297: // 4 sources:
                                                                                                                                                        var2_3 = var14_32;
                                                                                                                                                        var14_32 = var17_17;
                                                                                                                                                        var4_26 = var3_25;
                                                                                                                                                    }
                                                                                                                                                    var13_23 = var14_32;
                                                                                                                                                    var3_25 = var4_26;
                                                                                                                                                    var14_32 = var2_3;
                                                                                                                                                    break block130;
                                                                                                                                                }
                                                                                                                                                if (var2_3[0] != 101 || var2_3[1] != 99 || var2_3[2] != 100 || var2_3[3] != 115) break block144;
                                                                                                                                                var4_26 = var3_25;
                                                                                                                                                if (var1_2 != null) break block127;
                                                                                                                                                var5_27 = var3_25;
                                                                                                                                                var14_32 = var19_46;
                                                                                                                                                var4_26 = var3_25;
                                                                                                                                                if (((Object)var2_3).length <= 7) break block127;
                                                                                                                                                var4_26 = 3;
                                                                                                                                                break block127;
lbl316: // 1 sources:
                                                                                                                                                var5_27 = var4_26;
                                                                                                                                                var14_32 = var19_46;
                                                                                                                                                var13_23 = Util.fromBase64((byte[])var2_3, var7_24, var3_25 - var7_24);
                                                                                                                                                var7_24 = var3_25;
                                                                                                                                                ** GOTO lbl323
lbl321: // 1 sources:
                                                                                                                                                do {
                                                                                                                                                    var13_23 = null;
lbl323: // 2 sources:
                                                                                                                                                    var8_29 = var7_24 + 1;
                                                                                                                                                    var3_25 = var4_26;
                                                                                                                                                    var14_32 = var13_23;
                                                                                                                                                    var17_18 = var19_46;
                                                                                                                                                    if (var7_24 >= var9_30) ** GOTO lbl297
                                                                                                                                                    var3_25 = var8_29;
lbl329: // 2 sources:
                                                                                                                                                    if (var3_25 < var9_30 && var2_3[var3_25] != 10) break block128;
                                                                                                                                                    break block129;
                                                                                                                                                    break;
                                                                                                                                                } while (true);
lbl331: // 3 sources:
                                                                                                                                                var3_25 = var4_26;
                                                                                                                                                var14_32 = var13_23;
                                                                                                                                                var17_19 = var19_46;
                                                                                                                                                if (var8_29 >= var5_27) ** GOTO lbl297
                                                                                                                                                var3_25 = var4_26;
                                                                                                                                                var14_32 = var13_23;
                                                                                                                                                try {
                                                                                                                                                    var2_3 = new String((byte[])var2_3, var8_29, var5_27 - var8_29);
                                                                                                                                                    var14_32 = var13_23;
                                                                                                                                                    var3_25 = var4_26;
                                                                                                                                                    var13_23 = var2_3;
                                                                                                                                                    break block130;
                                                                                                                                                }
                                                                                                                                                catch (Exception var2_6) {
                                                                                                                                                    var17_21 = var19_46;
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                            var13_23 = "";
                                                                                                                                            var14_32 = null;
                                                                                                                                            break block130;
lbl347: // 1 sources:
                                                                                                                                            do {
                                                                                                                                                var5_27 = var3_25;
                                                                                                                                                ** GOTO lbl254
                                                                                                                                                break;
                                                                                                                                            } while (true);
                                                                                                                                        }
                                                                                                                                        var2_3 = null;
                                                                                                                                        if (var3_25 == 1) {
                                                                                                                                            var2_3 = new KeyPairDSA((JSch)var0);
                                                                                                                                        } else if (var3_25 == 2) {
                                                                                                                                            var2_3 = new KeyPairRSA((JSch)var0);
                                                                                                                                        } else if (var3_25 == 3) {
                                                                                                                                            var2_3 = new KeyPairECDSA((JSch)var0);
                                                                                                                                        } else if (var6_28 == 3) {
                                                                                                                                            var2_3 = new KeyPairPKCS8((JSch)var0);
                                                                                                                                        }
                                                                                                                                        if (var2_3 != null) {
                                                                                                                                            var2_3.encrypted = var12_45;
                                                                                                                                            var2_3.publickeyblob = var14_32;
                                                                                                                                            var2_3.vendor = var6_28;
                                                                                                                                            var2_3.publicKeyComment = var13_23;
                                                                                                                                            var2_3.cipher = var16_33;
                                                                                                                                            if (var12_45) {
                                                                                                                                                var2_3.encrypted = true;
                                                                                                                                                var2_3.iv = var15_8;
                                                                                                                                                var2_3.data = var18_44;
                                                                                                                                                return var2_3;
                                                                                                                                            }
                                                                                                                                            if (var2_3.parse((byte[])var18_44)) {
                                                                                                                                                var2_3.encrypted = false;
                                                                                                                                                return var2_3;
                                                                                                                                            }
                                                                                                                                            var0 = new StringBuilder();
                                                                                                                                            var0.append("invalid privatekey: ");
                                                                                                                                            var0.append(var1_2);
                                                                                                                                            throw new JSchException(var0.toString());
                                                                                                                                        }
                                                                                                                                        return var2_3;
lbl382: // 3 sources:
                                                                                                                                        catch (Exception var2_4) {
                                                                                                                                            ** continue;
                                                                                                                                        }
                                                                                                                                        catch (Exception var2_5) {}
                                                                                                                                        ** GOTO lbl254
                                                                                                                                        ** GOTO lbl297
lbl389: // 15 sources:
                                                                                                                                        catch (Exception var0_1) {
                                                                                                                                            ** continue;
                                                                                                                                        }
                                                                                                                                        for (var5_27 = 0; var5_27 < var7_24 && (var1_2[var5_27] != 45 || (var3_25 = var5_27 + 4) >= var7_24 || var1_2[var5_27 + 1] != 45 || var1_2[var5_27 + 2] != 45 || var1_2[var5_27 + 3] != 45 || var1_2[var3_25] != 45); ++var5_27) {
                                                                                                                                        }
                                                                                                                                        var16_34 = null;
                                                                                                                                        var6_28 = var3_25 = 0;
                                                                                                                                        var11_31 = true;
                                                                                                                                        ** GOTO lbl24
                                                                                                                                    }
                                                                                                                                    if (var1_2[var5_27] == 68 && var1_2[var5_27 + 1] == 83 && var1_2[var4_26] == 65) {
                                                                                                                                        var3_25 = 1;
                                                                                                                                        var4_26 = var6_28;
                                                                                                                                    } else if (var1_2[var5_27] == 82 && var1_2[var5_27 + 1] == 83 && var1_2[var4_26] == 65) {
                                                                                                                                        var3_25 = 2;
                                                                                                                                        var4_26 = var6_28;
                                                                                                                                    } else if (var1_2[var5_27] == 69 && var1_2[var5_27 + 1] == 67) {
                                                                                                                                        var3_25 = 3;
                                                                                                                                        var4_26 = var6_28;
                                                                                                                                    } else if (var1_2[var5_27] == 83 && var1_2[var5_27 + 1] == 83 && var1_2[var4_26] == 72) {
                                                                                                                                        var3_25 = 4;
                                                                                                                                        var4_26 = 1;
                                                                                                                                    } else {
                                                                                                                                        var6_28 = var5_27 + 6;
                                                                                                                                        if (var6_28 < var7_24 && var1_2[var5_27] == 80 && var1_2[var5_27 + 1] == 82 && var1_2[var4_26] == 73 && var1_2[var3_25 = var5_27 + 3] == 86 && var1_2[var5_27 + 4] == 65 && var1_2[var5_27 + 5] == 84 && var1_2[var6_28] == 69) {
                                                                                                                                            var5_27 = var3_25;
                                                                                                                                            var3_25 = 4;
                                                                                                                                            var4_26 = 3;
                                                                                                                                            var11_31 = false;
                                                                                                                                        } else {
                                                                                                                                            if ((var8_29 = var5_27 + 8) >= var7_24 || var1_2[var5_27] != 69 || var1_2[var5_27 + 1] != 78 || var1_2[var4_26] != 67 || var1_2[var5_27 + 3] != 82 || var1_2[var5_27 + 4] != 89 || var1_2[var3_25 = var5_27 + 5] != 80 || var1_2[var6_28] != 84 || var1_2[var5_27 + 7] != 69 || var1_2[var8_29] != 68) ** continue;
                                                                                                                                            var5_27 = var3_25;
                                                                                                                                            var3_25 = 4;
                                                                                                                                            var4_26 = 3;
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                    var13_23 = var15_8;
                                                                                                                                    var6_28 = var4_26;
                                                                                                                                    var4_26 = var5_27 += 3;
                                                                                                                                    var14_32 = var16_33;
                                                                                                                                    break block116;
                                                                                                                                }
                                                                                                                                var15_9 = var13_23;
                                                                                                                                var13_23 = var14_32;
                                                                                                                                var14_32 = var15_9;
                                                                                                                                break block116;
                                                                                                                            }
                                                                                                                            if (var1_2[var5_27] != 67 || (var4_26 = var5_27 + 3) >= var7_24 || var1_2[var5_27 + 1] != 66 || var1_2[var5_27 + 2] != 67 || var1_2[var4_26] != 44) ** GOTO lbl86
                                                                                                                            var5_27 += 4;
                                                                                                                            var8_29 = 0;
                                                                                                                            ** GOTO lbl74
                                                                                                                        }
                                                                                                                        var8_29 = var4_26;
                                                                                                                        ** GOTO lbl108
                                                                                                                    }
                                                                                                                    if (var1_2[var8_29] != 58) break block156;
                                                                                                                    var8_29 = 1;
                                                                                                                    break block157;
                                                                                                                }
                                                                                                                ++var8_29;
                                                                                                                ** GOTO lbl108
                                                                                                            }
                                                                                                            var8_29 = 0;
                                                                                                        }
                                                                                                        if (var8_29 != 0) break block117;
                                                                                                        if (var6_28 != 3) {
                                                                                                            var11_31 = false;
                                                                                                        }
                                                                                                        ** GOTO lbl112
                                                                                                    }
                                                                                                    var4_26 = var5_27 + 1;
                                                                                                    var14_32 = var16_33;
                                                                                                    var13_23 = var15_8;
                                                                                                }
                                                                                                var15_11 = var13_23;
                                                                                                var5_27 = var4_26;
                                                                                                var16_35 /* !! */  = var14_32;
                                                                                                ** GOTO lbl24
                                                                                            }
                                                                                            for (var5_27 = var4_26; var5_27 < var7_24 && var1_2[var5_27] != 45; ++var5_27) {
                                                                                            }
                                                                                            ** while (var7_24 - var5_27 != 0 && (var5_27 -= var4_26) != 0)
lbl470: // 1 sources:
                                                                                            ** while (true)
lbl471: // 3 sources:
                                                                                            if (var5_27 >= var4_26) ** GOTO lbl132
                                                                                            if (var14_32[var5_27] != 10) break block158;
                                                                                            if (var14_32[var5_27 - 1] != 13) break block159;
                                                                                            var7_24 = 1;
                                                                                            ** GOTO lbl125
                                                                                        }
                                                                                        var7_24 = 0;
                                                                                        ** GOTO lbl125
                                                                                    }
                                                                                    var4_26 = var8_29 - 1;
                                                                                    ** GOTO lbl471
                                                                                }
                                                                                if (var14_32[var5_27] == 45) ** GOTO lbl132
                                                                                ++var5_27;
                                                                                ** GOTO lbl471
                                                                            }
                                                                            var13_23 = null;
                                                                            ** GOTO lbl135
                                                                        }
                                                                        var13_23 = null;
                                                                        ** GOTO lbl143
                                                                    }
                                                                    var7_24 = 1;
                                                                    var13_23 = var17_12;
                                                                }
lbl496: // 2 sources:
                                                                if (var7_24 == 0) ** GOTO lbl213
                                                                if (var2_3[var4_26] != 10) break block160;
                                                                var8_29 = var5_27 = var4_26 + 1;
                                                                ** GOTO lbl208
                                                            }
                                                            ++var8_29;
                                                            ** GOTO lbl208
                                                        }
                                                        var8_29 = 0;
                                                    }
                                                    var14_32 = var13_23;
                                                    if (var8_29 != 0) break block161;
                                                    var4_26 = var5_27;
                                                    ** GOTO lbl213
                                                }
                                                var14_32 = var13_23;
                                            }
                                            ++var4_26;
                                            var13_23 = var14_32;
                                            ** GOTO lbl496
                                        }
                                        var8_29 = var4_26;
                                        ** GOTO lbl219
                                    }
                                    if (var2_3[var8_29] == 45) ** GOTO lbl228
                                    ++var8_29;
                                    ** GOTO lbl219
                                }
                                var4_26 = var3_25;
                                if (var2_3[4] == 114) {
                                    var4_26 = 2;
                                }
                            }
                            for (var3_25 = 0; var3_25 < var9_30 && var2_3[var3_25] != 32; ++var3_25) {
                            }
                            var7_24 = var3_25 + 1;
                            if (var7_24 >= var9_30) ** GOTO lbl276
                            var3_25 = var7_24;
                            do {
                                if (var3_25 >= var9_30 || var2_3[var3_25] == 32) ** GOTO lbl270
                                ++var3_25;
                            } while (true);
                        }
                        ++var3_25;
                        ** GOTO lbl284
                    }
                    var5_27 = var3_25;
                    if (var3_25 <= 0) ** GOTO lbl286
                    var5_27 = var3_25;
                    if (var2_3[var3_25 - 1] != 13) ** GOTO lbl286
                    var5_27 = var3_25 - 1;
                    ** GOTO lbl286
                }
                for (var3_25 = 0; var3_25 < var9_30 && var2_3[var3_25] != 32; ++var3_25) {
                }
                ** while ((var7_24 = var3_25 + 1) >= var9_30)
lbl551: // 1 sources:
                var3_25 = var7_24;
                do {
                    if (var3_25 >= var9_30 || var2_3[var3_25] == 32) ** GOTO lbl316
                    ++var3_25;
                } while (true);
            }
            ++var3_25;
            ** GOTO lbl329
        }
        var5_27 = var3_25;
        if (var3_25 <= 0) ** GOTO lbl331
        var5_27 = var3_25;
        if (var2_3[var3_25 - 1] != 13) ** GOTO lbl331
        var5_27 = var3_25 - 1;
        ** GOTO lbl331
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static KeyPair loadPPK(JSch object, byte[] object2) throws JSchException {
        byte[] arrby = new byte[]((byte[])object2);
        object2 = new Hashtable();
        while (KeyPair.parseHeader((Buffer)arrby, (Hashtable)object2)) {
        }
        Object object3 = (String)object2.get("PuTTY-User-Key-File-2");
        if (object3 == null) {
            return null;
        }
        byte[] arrby2 = KeyPair.parseLines((Buffer)arrby, Integer.parseInt((String)object2.get("Public-Lines")));
        while (KeyPair.parseHeader((Buffer)arrby, (Hashtable)object2)) {
        }
        byte[] arrby3 = KeyPair.parseLines((Buffer)arrby, Integer.parseInt((String)object2.get("Private-Lines")));
        while (KeyPair.parseHeader((Buffer)arrby, (Hashtable)object2)) {
        }
        arrby = Util.fromBase64(arrby3, 0, arrby3.length);
        arrby2 = Util.fromBase64(arrby2, 0, arrby2.length);
        if (object3.equals("ssh-rsa")) {
            object3 = new Buffer(arrby2);
            object3.skip(arrby2.length);
            object3.getByte(new byte[object3.getInt()]);
            arrby2 = new byte[object3.getInt()];
            object3.getByte(arrby2);
            arrby3 = new byte[object3.getInt()];
            object3.getByte(arrby3);
            object = new KeyPairRSA((JSch)object, arrby3, arrby2, null);
        } else {
            if (!object3.equals("ssh-dss")) return null;
            object3 = new Buffer(arrby2);
            object3.skip(arrby2.length);
            object3.getByte(new byte[object3.getInt()]);
            arrby2 = new byte[object3.getInt()];
            object3.getByte(arrby2);
            arrby3 = new byte[object3.getInt()];
            object3.getByte(arrby3);
            byte[] arrby4 = new byte[object3.getInt()];
            object3.getByte(arrby4);
            byte[] arrby5 = new byte[object3.getInt()];
            object3.getByte(arrby5);
            object = new KeyPairDSA((JSch)object, arrby2, arrby3, arrby4, arrby5, null);
        }
        if (object == null) {
            return null;
        }
        object.encrypted = object2.get("Encryption").equals("none") ^ true;
        object.vendor = 2;
        object.publicKeyComment = (String)object2.get("Comment");
        if (!object.encrypted) {
            object.data = arrby;
            object.parse(arrby);
            return object;
        }
        if (!Session.checkCipher(JSch.getConfig("aes256-cbc"))) throw new JSchException("The cipher 'aes256-cbc' is required, but it is not available.");
        try {
            object.cipher = (Cipher)Class.forName(JSch.getConfig("aes256-cbc")).newInstance();
            object.iv = new byte[object.cipher.getIVSize()];
            object.data = arrby;
            return object;
        }
        catch (Exception exception) {
            throw new JSchException("The cipher 'aes256-cbc' is required, but it is not available.");
        }
    }

    private static boolean parseHeader(Buffer buffer, Hashtable hashtable) {
        int n;
        int n2;
        byte[] arrby;
        String string;
        int n3;
        String string2;
        String string3;
        block12 : {
            arrby = buffer.buffer;
            n2 = n3 = buffer.index;
            do {
                string3 = null;
                if (n2 >= arrby.length || arrby[n2] == 13) break;
                if (arrby[n2] == 58) {
                    string = new String(arrby, n3, n2 - n3);
                    n3 = ++n2;
                    if (n2 < arrby.length) {
                        n3 = n2;
                        if (arrby[n2] == 32) {
                            n3 = n2 + 1;
                        }
                    }
                    break block12;
                }
                ++n2;
            } while (true);
            string = null;
        }
        boolean bl = false;
        if (string == null) {
            return false;
        }
        n2 = n3;
        do {
            n = n3;
            string2 = string3;
            if (n2 >= arrby.length) break;
            if (arrby[n2] == 13) {
                string2 = new String(arrby, n3, n2 - n3);
                n3 = ++n2;
                if (n2 < arrby.length) {
                    n3 = n2;
                    if (arrby[n2] == 10) {
                        n3 = n2 + 1;
                    }
                }
                n = n3;
                break;
            }
            ++n2;
        } while (true);
        if (string2 != null) {
            hashtable.put(string, string2);
            buffer.index = n;
        }
        boolean bl2 = bl;
        if (string != null) {
            bl2 = bl;
            if (string2 != null) {
                bl2 = true;
            }
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static byte[] parseLines(Buffer buffer, int n) {
        byte[] arrby = buffer.buffer;
        int n2 = buffer.index;
        byte[] arrby2 = null;
        int n3 = n;
        n = n2;
        do {
            byte[] arrby3;
            block10 : {
                if (n3 > 0) {
                    n2 = n;
                } else {
                    if (arrby2 != null) {
                        buffer.index = n;
                    }
                    return arrby2;
                }
                while (arrby.length > n2) {
                    int n4 = n2 + 1;
                    if (arrby[n2] == 13) {
                        if (arrby2 == null) {
                            n2 = n4 - n - 1;
                            arrby3 = new byte[n2];
                            System.arraycopy(arrby, n, arrby3, 0, n2);
                        } else {
                            byte[] arrby4 = new byte[arrby2.length + n4 - n - 1];
                            System.arraycopy(arrby2, 0, arrby4, 0, arrby2.length);
                            System.arraycopy(arrby, n, arrby4, arrby2.length, n4 - n - 1);
                            n = 0;
                            do {
                                arrby3 = arrby4;
                                if (n >= arrby2.length) break;
                                arrby2[n] = 0;
                                ++n;
                            } while (true);
                        }
                        n = n4;
                        break block10;
                    }
                    n2 = n4;
                }
                n = n2;
                arrby3 = arrby2;
            }
            n2 = n;
            if (arrby[n] == 10) {
                n2 = n + 1;
            }
            n = n2;
            --n3;
            arrby2 = arrby3;
        } while (true);
    }

    void copy(KeyPair keyPair) {
        this.publickeyblob = keyPair.publickeyblob;
        this.vendor = keyPair.vendor;
        this.publicKeyComment = keyPair.publicKeyComment;
        this.cipher = keyPair.cipher;
    }

    int countLength(int n) {
        int n2 = 1;
        int n3 = n;
        if (n <= 127) {
            return 1;
        }
        while (n3 > 0) {
            n3 >>>= 8;
            ++n2;
        }
        return n2;
    }

    public boolean decrypt(String string) {
        if (string != null && string.length() != 0) {
            return this.decrypt(Util.str2byte(string));
        }
        return this.encrypted ^ true;
    }

    public boolean decrypt(byte[] arrby) {
        if (!this.encrypted) {
            return true;
        }
        if (arrby == null) {
            return this.encrypted ^ true;
        }
        byte[] arrby2 = new byte[arrby.length];
        System.arraycopy(arrby, 0, arrby2, 0, arrby2.length);
        arrby = this.decrypt(this.data, arrby2, this.iv);
        Util.bzero(arrby2);
        if (this.parse(arrby)) {
            this.encrypted = false;
        }
        return this.encrypted ^ true;
    }

    public void dispose() {
        Util.bzero(this.passphrase);
    }

    public void finalize() {
        this.dispose();
    }

    public abstract byte[] forSSHAgent() throws JSchException;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    byte[] genKey(byte[] arrby, byte[] object32) {
        block22 : {
            block23 : {
                // MONITORENTER : this
                if (this.cipher == null) {
                    this.cipher = this.genCipher();
                }
                if (this.hash == null) {
                    this.hash = this.genHash();
                }
                arrby4 = new byte[this.cipher.getBlockSize()];
                n2 = this.hash.getBlockSize();
                n3 = arrby4.length / n2;
                n = arrby4.length % n2 == 0 ? 0 : n2;
                arrby3 = new byte[n3 * n2 + n];
                arrby2 = null;
                try {
                    if (this.vendor == 0) {
                        arrby2 = null;
                        n = 0;
                    } else {
                        if (this.vendor == 1) {
                            n = 0;
                            object2 /* !! */  = arrby2;
                            do {
                                if (n + n2 > arrby3.length) {
                                    System.arraycopy(arrby3, 0, arrby4, 0, arrby4.length);
                                    return arrby4;
                                }
                                if (object2 /* !! */  != null) {
                                    this.hash.update(object2 /* !! */ , 0, object2 /* !! */ .length);
                                }
                                this.hash.update(arrby /* !! */ , 0, arrby /* !! */ .length);
                                object2 /* !! */  = this.hash.digest();
                                System.arraycopy(object2 /* !! */ , 0, arrby3, n, object2 /* !! */ .length);
                                n += object2 /* !! */ .length;
                            } while (true);
                        }
                        object2 /* !! */  = arrby4;
                        if (this.vendor != 2) break block22;
                        object2 /* !! */  = this.jsch;
                        arrby2 = (byte[])Class.forName(JSch.getConfig("sha-1")).newInstance();
                        arrby3 = new byte[4];
                        object2 /* !! */  = new byte[40];
                        break block23;
                    }
                    while (n + n2 <= arrby3.length) {
                        if (arrby2 != null) {
                            this.hash.update(arrby2, 0, arrby2.length);
                        }
                        this.hash.update(arrby /* !! */ , 0, arrby /* !! */ .length);
                        arrby2 = this.hash;
                        n4 = object2 /* !! */ .length;
                        n3 = 8;
                        if (n4 <= 8) {
                            n3 = object2 /* !! */ .length;
                        }
                        arrby2.update(object2 /* !! */ , 0, n3);
                        arrby2 = this.hash.digest();
                        System.arraycopy(arrby2, 0, arrby3, n, arrby2.length);
                        n += arrby2.length;
                    }
                    System.arraycopy(arrby3, 0, arrby4, 0, arrby4.length);
                    return arrby4;
                }
                catch (Exception object32) {
                    arrby /* !! */  = arrby4;
                    ** GOTO lbl70
                }
            }
            for (n = 0; n < 2; ++n) {
                arrby2.init();
                arrby3[3] = (byte)n;
                try {
                    arrby2.update(arrby3, 0, arrby3.length);
                    arrby2.update(arrby /* !! */ , 0, arrby /* !! */ .length);
                    System.arraycopy(arrby2.digest(), 0, object2 /* !! */ , n * 20, 20);
                    continue;
                }
                catch (Exception exception) {
                    arrby /* !! */  = object2 /* !! */ ;
                    object2 /* !! */  = exception;
                }
lbl70: // 2 sources:
                System.err.println(object2 /* !! */ );
                object2 /* !! */  = arrby /* !! */ ;
                return object2 /* !! */ ;
            }
        }
        // MONITOREXIT : this
        return object2 /* !! */ ;
    }

    abstract void generate(int var1) throws JSchException;

    abstract byte[] getBegin();

    abstract byte[] getEnd();

    public String getFingerPrint() {
        byte[] arrby;
        if (this.hash == null) {
            this.hash = this.genHash();
        }
        if ((arrby = this.getPublicKeyBlob()) == null) {
            return null;
        }
        return Util.getFingerPrint(this.hash, arrby);
    }

    abstract int getKeySize();

    public abstract int getKeyType();

    abstract byte[] getKeyTypeName();

    abstract byte[] getPrivateKey();

    public byte[] getPublicKeyBlob() {
        return this.publickeyblob;
    }

    public String getPublicKeyComment() {
        return this.publicKeyComment;
    }

    public abstract byte[] getSignature(byte[] var1);

    public abstract Signature getVerifier();

    public boolean isEncrypted() {
        return this.encrypted;
    }

    abstract boolean parse(byte[] var1);

    public void setPassphrase(String string) {
        if (string != null && string.length() != 0) {
            this.setPassphrase(Util.str2byte(string));
            return;
        }
        this.setPassphrase((byte[])null);
    }

    public void setPassphrase(byte[] arrby) {
        byte[] arrby2 = arrby;
        if (arrby != null) {
            arrby2 = arrby;
            if (arrby.length == 0) {
                arrby2 = null;
            }
        }
        this.passphrase = arrby2;
    }

    public void setPublicKeyComment(String string) {
        this.publicKeyComment = string;
    }

    int writeDATA(byte[] arrby, byte by, int n, byte[] arrby2) {
        arrby[n] = by;
        n = this.writeLength(arrby, n + 1, arrby2.length);
        System.arraycopy(arrby2, 0, arrby, n, arrby2.length);
        return n + arrby2.length;
    }

    int writeINTEGER(byte[] arrby, int n, byte[] arrby2) {
        arrby[n] = 2;
        n = this.writeLength(arrby, n + 1, arrby2.length);
        System.arraycopy(arrby2, 0, arrby, n, arrby2.length);
        return n + arrby2.length;
    }

    int writeLength(byte[] arrby, int n, int n2) {
        int n3 = this.countLength(n2) - 1;
        if (n3 == 0) {
            arrby[n] = (byte)n2;
            return n + 1;
        }
        int n4 = n + 1;
        arrby[n] = (byte)(128 | n3);
        for (n = n3; n > 0; --n) {
            arrby[n4 + n - 1] = (byte)(n2 & 255);
            n2 >>>= 8;
        }
        return n4 + n3;
    }

    int writeOCTETSTRING(byte[] arrby, int n, byte[] arrby2) {
        arrby[n] = 4;
        n = this.writeLength(arrby, n + 1, arrby2.length);
        System.arraycopy(arrby2, 0, arrby, n, arrby2.length);
        return n + arrby2.length;
    }

    public void writePrivateKey(OutputStream outputStream) {
        this.writePrivateKey(outputStream, null);
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void writePrivateKey(OutputStream var1_1, byte[] var2_3) {
        block12 : {
            var5_4 = var2_3;
            if (var2_3 == null) {
                var5_4 = this.passphrase;
            }
            if ((var2_3 = this.encrypt(var6_5 = this.getPrivateKey(), var7_6 = new byte[1][], var5_4)) != var6_5) {
                Util.bzero(var6_5);
            }
            var4_7 = 0;
            var6_5 = var7_6[0];
            var2_3 = Util.toBase64(var2_3, 0, var2_3.length);
            var1_1.write(this.getBegin());
            var1_1.write(KeyPair.cr);
            var3_8 = var4_7;
            if (var5_4 == null) break block12;
            var1_1.write(KeyPair.header[0]);
            var1_1.write(KeyPair.cr);
            var1_1.write(KeyPair.header[1]);
            var3_8 = 0;
            do {
                if (var3_8 >= var6_5.length) break;
                var1_1.write(KeyPair.b2a((byte)(var6_5[var3_8] >>> 4 & 15)));
                var1_1.write(KeyPair.b2a((byte)(var6_5[var3_8] & 15)));
                ++var3_8;
            } while (true);
            var1_1.write(KeyPair.cr);
            var1_1.write(KeyPair.cr);
            var3_8 = var4_7;
        }
        do {
            if (var3_8 >= var2_3.length) ** GOTO lbl44
            var4_7 = var3_8 + 64;
            if (var4_7 >= var2_3.length) break;
            var1_1.write(var2_3, var3_8, 64);
            var1_1.write(KeyPair.cr);
            var3_8 = var4_7;
            continue;
            break;
        } while (true);
        try {
            var1_1.write(var2_3, var3_8, var2_3.length - var3_8);
            var1_1.write(KeyPair.cr);
lbl44: // 2 sources:
            var1_1.write(this.getEnd());
            var1_1.write(KeyPair.cr);
            return;
        }
        catch (Exception var1_2) {
            return;
        }
    }

    public void writePrivateKey(String string) throws FileNotFoundException, IOException {
        this.writePrivateKey(string, null);
    }

    public void writePrivateKey(String object, byte[] arrby) throws FileNotFoundException, IOException {
        object = new FileOutputStream((String)object);
        this.writePrivateKey((OutputStream)object, arrby);
        object.close();
    }

    public void writePublicKey(OutputStream outputStream, String string) {
        byte[] arrby = this.getPublicKeyBlob();
        arrby = Util.toBase64(arrby, 0, arrby.length);
        try {
            outputStream.write(this.getKeyTypeName());
            outputStream.write(space);
            outputStream.write(arrby, 0, arrby.length);
            outputStream.write(space);
            outputStream.write(Util.str2byte(string));
            outputStream.write(cr);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public void writePublicKey(String object, String string) throws FileNotFoundException, IOException {
        object = new FileOutputStream((String)object);
        this.writePublicKey((OutputStream)object, string);
        object.close();
    }

    public void writeSECSHPublicKey(OutputStream outputStream, String string) {
        int n;
        int n2;
        byte[] arrby = this.getPublicKeyBlob();
        arrby = Util.toBase64(arrby, 0, arrby.length);
        try {
            outputStream.write(Util.str2byte("---- BEGIN SSH2 PUBLIC KEY ----"));
            outputStream.write(cr);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Comment: \"");
            stringBuilder.append(string);
            stringBuilder.append("\"");
            outputStream.write(Util.str2byte(stringBuilder.toString()));
            outputStream.write(cr);
            for (n2 = 0; n2 < arrby.length; n2 += n) {
                n = 70;
            }
        }
        catch (Exception exception) {
            return;
        }
        {
            if (arrby.length - n2 < 70) {
                n = arrby.length - n2;
            }
            outputStream.write(arrby, n2, n);
            outputStream.write(cr);
            continue;
        }
        outputStream.write(Util.str2byte("---- END SSH2 PUBLIC KEY ----"));
        outputStream.write(cr);
    }

    public void writeSECSHPublicKey(String object, String string) throws FileNotFoundException, IOException {
        object = new FileOutputStream((String)object);
        this.writeSECSHPublicKey((OutputStream)object, string);
        object.close();
    }

    int writeSEQUENCE(byte[] arrby, int n, int n2) {
        arrby[n] = 48;
        return this.writeLength(arrby, n + 1, n2);
    }

    class ASN1 {
        byte[] buf;
        int length;
        int start;

        ASN1(byte[] arrby) throws ASN1Exception {
            this(arrby, 0, arrby.length);
        }

        ASN1(byte[] arrby, int n, int n2) throws ASN1Exception {
            this.buf = arrby;
            this.start = n;
            this.length = n2;
            if (n + n2 > arrby.length) {
                throw new ASN1Exception();
            }
        }

        private int getLength(int[] arrn) {
            int n;
            int n2 = arrn[0];
            byte[] arrby = this.buf;
            int n3 = n2 + 1;
            int n4 = n = arrby[n2] & 255;
            n2 = n3;
            if ((n & 128) != 0) {
                n4 = n & 127;
                n2 = 0;
                while (n4 > 0) {
                    n2 = (this.buf[n3] & 255) + (n2 << 8);
                    --n4;
                    ++n3;
                }
                n4 = n2;
                n2 = n3;
            }
            arrn[0] = n2;
            return n4;
        }

        byte[] getContent() {
            int[] arrn = new int[]{this.start + 1};
            int n = this.getLength(arrn);
            int n2 = arrn[0];
            arrn = new byte[n];
            System.arraycopy(this.buf, n2, arrn, 0, arrn.length);
            return arrn;
        }

        ASN1[] getContents() throws ASN1Exception {
            int n = this.buf[this.start];
            Object[] arrobject = new int[1];
            int n2 = this.start;
            int n3 = 0;
            arrobject[0] = n2 + 1;
            n2 = this.getLength((int[])arrobject);
            if (n == 5) {
                return new ASN1[0];
            }
            n = arrobject[0];
            Vector<ASN1> vector = new Vector<ASN1>();
            while (n2 > 0) {
                arrobject[0] = ++n;
                int n4 = this.getLength((int[])arrobject);
                int n5 = arrobject[0];
                int n6 = n5 - n;
                vector.addElement(new ASN1(this.buf, n - 1, n6 + 1 + n4));
                n = n5 + n4;
                n2 = n2 - 1 - n6 - n4;
            }
            arrobject = new ASN1[vector.size()];
            for (n2 = n3; n2 < vector.size(); ++n2) {
                arrobject[n2] = (int)((ASN1)vector.elementAt(n2));
            }
            return arrobject;
        }

        int getType() {
            return this.buf[this.start] & 255;
        }

        boolean isINTEGER() {
            if (this.getType() == 2) {
                return true;
            }
            return false;
        }

        boolean isOBJECT() {
            if (this.getType() == 6) {
                return true;
            }
            return false;
        }

        boolean isOCTETSTRING() {
            if (this.getType() == 4) {
                return true;
            }
            return false;
        }

        boolean isSEQUENCE() {
            if (this.getType() == 48) {
                return true;
            }
            return false;
        }
    }

    class ASN1Exception
    extends Exception {
        ASN1Exception() {
        }
    }

}
