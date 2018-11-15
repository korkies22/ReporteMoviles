/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.HASH;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SignatureDSA;
import com.jcraft.jsch.SignatureECDSA;
import com.jcraft.jsch.SignatureRSA;
import com.jcraft.jsch.Util;
import java.io.PrintStream;

public abstract class KeyExchange {
    static final int PROPOSAL_COMP_ALGS_CTOS = 6;
    static final int PROPOSAL_COMP_ALGS_STOC = 7;
    static final int PROPOSAL_ENC_ALGS_CTOS = 2;
    static final int PROPOSAL_ENC_ALGS_STOC = 3;
    static final int PROPOSAL_KEX_ALGS = 0;
    static final int PROPOSAL_LANG_CTOS = 8;
    static final int PROPOSAL_LANG_STOC = 9;
    static final int PROPOSAL_MAC_ALGS_CTOS = 4;
    static final int PROPOSAL_MAC_ALGS_STOC = 5;
    static final int PROPOSAL_MAX = 10;
    static final int PROPOSAL_SERVER_HOST_KEY_ALGS = 1;
    public static final int STATE_END = 0;
    static String enc_c2s = "blowfish-cbc";
    static String enc_s2c = "blowfish-cbc";
    static String kex = "diffie-hellman-group1-sha1";
    static String lang_c2s = "";
    static String lang_s2c = "";
    static String mac_c2s = "hmac-md5";
    static String mac_s2c = "hmac-md5";
    static String server_host_key = "ssh-rsa,ssh-dss";
    protected final int DSS = 1;
    protected final int ECDSA = 2;
    protected byte[] H = null;
    protected byte[] K = null;
    protected byte[] K_S = null;
    protected final int RSA = 0;
    private String key_alg_name = "";
    protected Session session = null;
    protected HASH sha = null;
    private int type = 0;

    protected static String[] guess(byte[] object, byte[] object2) {
        Object object3;
        Object object4;
        int n;
        String[] arrstring = new String[10];
        object = new Buffer((byte[])object);
        object.setOffSet(17);
        object2 = new Buffer((byte[])object2);
        object2.setOffSet(17);
        if (JSch.getLogger().isEnabled(1)) {
            for (n = 0; n < 10; ++n) {
                object3 = JSch.getLogger();
                object4 = new byte[]();
                object4.append("kex: server: ");
                object4.append(Util.byte2str(object.getString()));
                object3.log(1, object4.toString());
            }
            for (n = 0; n < 10; ++n) {
                object3 = JSch.getLogger();
                object4 = new StringBuilder();
                object4.append("kex: client: ");
                object4.append(Util.byte2str(object2.getString()));
                object3.log(1, object4.toString());
            }
            object.setOffSet(17);
            object2.setOffSet(17);
        }
        for (int i = 0; i < 10; ++i) {
            int n2;
            object3 = object.getString();
            object4 = object2.getString();
            int n3 = n = 0;
            block3 : do {
                n2 = n;
                if (n >= ((Object)object4).length) break;
                while (n < ((Object)object4).length && object4[n] != 44) {
                    ++n;
                }
                if (n3 == n) {
                    return null;
                }
                String string = Util.byte2str((byte[])object4, n3, n - n3);
                n2 = n3 = 0;
                while (n3 < ((Object)object3).length) {
                    while (n3 < ((Object)object3).length && object3[n3] != 44) {
                        ++n3;
                    }
                    if (n2 == n3) {
                        return null;
                    }
                    if (string.equals(Util.byte2str((byte[])object3, n2, n3 - n2))) {
                        arrstring[i] = string;
                        n2 = n;
                        break block3;
                    }
                    n3 = n2 = n3 + 1;
                }
                n = n3 = n + 1;
            } while (true);
            if (n2 == 0) {
                arrstring[i] = "";
                continue;
            }
            if (arrstring[i] != null) continue;
            return null;
        }
        if (JSch.getLogger().isEnabled(1)) {
            object = JSch.getLogger();
            object2 = new StringBuilder();
            object2.append("kex: server->client ");
            object2.append(arrstring[3]);
            object2.append(" ");
            object2.append(arrstring[5]);
            object2.append(" ");
            object2.append(arrstring[7]);
            object.log(1, object2.toString());
            object = JSch.getLogger();
            object2 = new StringBuilder();
            object2.append("kex: client->server ");
            object2.append(arrstring[2]);
            object2.append(" ");
            object2.append(arrstring[4]);
            object2.append(" ");
            object2.append(arrstring[6]);
            object.log(1, object2.toString());
        }
        return arrstring;
    }

    public String getFingerPrint() {
        HASH hASH;
        try {
            hASH = (HASH)Class.forName(this.session.getConfig("md5")).newInstance();
        }
        catch (Exception exception) {
            PrintStream printStream = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getFingerPrint: ");
            stringBuilder.append(exception);
            printStream.println(stringBuilder.toString());
            hASH = null;
        }
        return Util.getFingerPrint(hASH, this.getHostKey());
    }

    byte[] getH() {
        return this.H;
    }

    HASH getHash() {
        return this.sha;
    }

    byte[] getHostKey() {
        return this.K_S;
    }

    byte[] getK() {
        return this.K;
    }

    public String getKeyAlgorithName() {
        return this.key_alg_name;
    }

    public String getKeyType() {
        if (this.type == 1) {
            return "DSA";
        }
        if (this.type == 0) {
            return "RSA";
        }
        return "ECDSA";
    }

    public abstract int getState();

    public abstract void init(Session var1, byte[] var2, byte[] var3, byte[] var4, byte[] var5) throws Exception;

    public abstract boolean next(Buffer var1) throws Exception;

    protected byte[] normalize(byte[] arrby) {
        if (arrby.length > 1 && arrby[0] == 0 && (arrby[1] & 128) == 0) {
            byte[] arrby2 = new byte[arrby.length - 1];
            System.arraycopy(arrby, 1, arrby2, 0, arrby2.length);
            return this.normalize(arrby2);
        }
        return arrby;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected boolean verify(String object, byte[] object2, int object3, byte[] arrby) throws Exception {
        byte[] arrby8;
        byte[] arrby9;
        block19 : {
            void var2_9;
            block20 : {
                boolean bl;
                if (object.equals("ssh-rsa")) {
                    boolean bl2;
                    byte[] arrby3;
                    byte[] arrby2;
                    block15 : {
                        block16 : {
                            this.type = 0;
                            this.key_alg_name = object;
                            reference n = object3 + true;
                            object3 = object2[object3];
                            Object n2 = n + true;
                            n = object2[n];
                            reference n3 = n2 + true;
                            Object by = object2[n2];
                            n2 = n3 + true;
                            object3 = n << 16 & 16711680 | object3 << 24 & -16777216 | by << 8 & 65280 | object2[n3] & 255;
                            arrby2 = new byte[object3];
                            System.arraycopy(object2, (int)n2, arrby2, 0, object3);
                            object3 = n2 + object3;
                            n3 = (reference)(object3 + 1);
                            object3 = object2[object3];
                            n = n3 + true;
                            n2 = object2[n3];
                            n3 = n + true;
                            object3 = n2 << 16 & 16711680 | object3 << 24 & -16777216 | 65280 & object2[n] << 8 | object2[n3] & 255;
                            arrby3 = new byte[object3];
                            System.arraycopy(object2, (int)(n3 + true), arrby3, 0, object3);
                            object = (SignatureRSA)Class.forName(this.session.getConfig("signature.rsa")).newInstance();
                            try {
                                object.init();
                                break block15;
                            }
                            catch (Exception exception) {
                                break block16;
                            }
                            catch (Exception exception) {
                                object = null;
                            }
                        }
                        System.err.println(object2);
                    }
                    object.setPubKey(arrby2, arrby3);
                    object.update(this.H);
                    bl = bl2 = object.verify(arrby);
                    if (!JSch.getLogger().isEnabled(1)) return bl;
                    object = JSch.getLogger();
                    object2 = new StringBuilder();
                    object2.append("ssh_rsa_verify: signature ");
                    object2.append(bl2);
                    object.log(1, object2.toString());
                    return bl2;
                }
                if (object.equals("ssh-dss")) {
                    byte[] arrby7;
                    byte[] arrby6;
                    boolean bl3;
                    byte[] arrby5;
                    byte[] arrby4;
                    block17 : {
                        block18 : {
                            this.type = 1;
                            this.key_alg_name = object;
                            reference n = object3 + true;
                            object3 = object2[object3];
                            Object object4 = n + true;
                            n = object2[n];
                            reference n4 = object4 + true;
                            Object object5 = object2[object4];
                            object4 = n4 + true;
                            object3 = n << 16 & 16711680 | object3 << 24 & -16777216 | object5 << 8 & 65280 | object2[n4] & 255;
                            arrby5 = new byte[object3];
                            System.arraycopy(object2, (int)object4, arrby5, 0, object3);
                            object3 = object4 + object3;
                            n = (reference)(object3 + 1);
                            object3 = object2[object3];
                            object4 = n + true;
                            n = object2[n];
                            n4 = object4 + true;
                            object5 = object2[object4];
                            object4 = n4 + true;
                            object3 = n << 16 & 16711680 | object3 << 24 & -16777216 | object5 << 8 & 65280 | object2[n4] & 255;
                            arrby7 = new byte[object3];
                            System.arraycopy(object2, (int)object4, arrby7, 0, object3);
                            object3 = object4 + object3;
                            n = (reference)(object3 + 1);
                            object3 = object2[object3];
                            object4 = n + true;
                            n = object2[n];
                            n4 = object4 + true;
                            object5 = object2[object4];
                            object4 = n4 + true;
                            object3 = n << 16 & 16711680 | object3 << 24 & -16777216 | object5 << 8 & 65280 | object2[n4] & 255;
                            arrby6 = new byte[object3];
                            System.arraycopy(object2, (int)object4, arrby6, 0, object3);
                            object3 = object4 + object3;
                            n4 = (reference)(object3 + 1);
                            object3 = object2[object3];
                            n = n4 + true;
                            object4 = object2[n4];
                            n4 = n + true;
                            object3 = object4 << 16 & 16711680 | -16777216 & object3 << 24 | 65280 & object2[n] << 8 | object2[n4] & 255;
                            arrby4 = new byte[object3];
                            System.arraycopy(object2, (int)(n4 + true), arrby4, 0, object3);
                            object = (SignatureDSA)Class.forName(this.session.getConfig("signature.dss")).newInstance();
                            try {
                                object.init();
                                break block17;
                            }
                            catch (Exception exception) {
                                break block18;
                            }
                            catch (Exception exception) {
                                object = null;
                            }
                        }
                        System.err.println(object2);
                    }
                    object.setPubKey(arrby4, arrby5, arrby7, arrby6);
                    object.update(this.H);
                    bl = bl3 = object.verify(arrby);
                    if (!JSch.getLogger().isEnabled(1)) return bl;
                    object = JSch.getLogger();
                    object2 = new StringBuilder();
                    object2.append("ssh_dss_verify: signature ");
                    object2.append(bl3);
                    object.log(1, object2.toString());
                    return bl3;
                }
                if (!(object.equals("ecdsa-sha2-nistp256") || object.equals("ecdsa-sha2-nistp384") || object.equals("ecdsa-sha2-nistp521"))) {
                    System.err.println("unknown alg");
                    return false;
                }
                this.type = 2;
                this.key_alg_name = object;
                Object n = object3 + true;
                object3 = object2[object3];
                Object n5 = n + true;
                n = object2[n];
                reference n6 = n5 + true;
                Object by = object2[n5];
                n5 = n6 + true;
                object3 = n << 16 & 16711680 | object3 << 24 & -16777216 | by << 8 & 65280 | object2[n6] & 255;
                System.arraycopy(object2, (int)n5, new byte[object3], 0, object3);
                object3 = n5 + object3;
                n = object3 + 1;
                object3 = object2[object3];
                n5 = n + true;
                n = object2[n];
                n6 = n5 + true;
                n5 = object2[n5];
                by = object2[n6];
                n6 = n6 + true + true;
                object3 = ((n << 16 & 16711680 | object3 << 24 & -16777216 | 65280 & n5 << 8 | by & 255) - 1) / 2;
                arrby8 = new byte[object3];
                System.arraycopy(object2, (int)n6, arrby8, 0, arrby8.length);
                arrby9 = new byte[object3];
                System.arraycopy(object2, (int)(n6 + object3), arrby9, 0, arrby9.length);
                object = (SignatureECDSA)Class.forName(this.session.getConfig("signature.ecdsa")).newInstance();
                try {
                    object.init();
                    break block19;
                }
                catch (Exception exception) {
                    break block20;
                }
                catch (Exception exception) {
                    object = null;
                }
            }
            System.err.println(var2_9);
        }
        object.setPubKey(arrby8, arrby9);
        object.update(this.H);
        return object.verify(arrby);
    }
}
