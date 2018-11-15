/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.HASH;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Util;
import java.io.PrintStream;

public class HostKey {
    public static final int ECDSA256 = 3;
    public static final int ECDSA384 = 4;
    public static final int ECDSA521 = 5;
    protected static final int GUESS = 0;
    public static final int SSHDSS = 1;
    public static final int SSHRSA = 2;
    static final int UNKNOWN = 6;
    private static final byte[][] names = new byte[][]{Util.str2byte("ssh-dss"), Util.str2byte("ssh-rsa"), Util.str2byte("ecdsa-sha2-nistp256"), Util.str2byte("ecdsa-sha2-nistp384"), Util.str2byte("ecdsa-sha2-nistp521")};
    protected String comment;
    protected String host;
    protected byte[] key;
    protected String marker;
    protected int type;

    public HostKey(String string, int n, byte[] arrby) throws JSchException {
        this(string, n, arrby, null);
    }

    public HostKey(String string, int n, byte[] arrby, String string2) throws JSchException {
        this("", string, n, arrby, string2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public HostKey(String string, String string2, int n, byte[] arrby, String string3) throws JSchException {
        this.marker = string;
        this.host = string2;
        if (n == 0) {
            if (arrby[8] == 100) {
                this.type = 1;
            } else if (arrby[8] == 114) {
                this.type = 2;
            } else if (arrby[8] == 97 && arrby[20] == 50) {
                this.type = 3;
            } else if (arrby[8] == 97 && arrby[20] == 51) {
                this.type = 4;
            } else {
                if (arrby[8] != 97 || arrby[20] != 53) throw new JSchException("invalid key type");
                this.type = 5;
            }
        } else {
            this.type = n;
        }
        this.key = arrby;
        this.comment = string3;
    }

    public HostKey(String string, byte[] arrby) throws JSchException {
        this(string, 0, arrby);
    }

    private boolean isIncluded(String string) {
        String string2 = this.host;
        int n = string2.length();
        int n2 = string.length();
        int n3 = 0;
        while (n3 < n) {
            int n4 = string2.indexOf(44, n3);
            if (n4 == -1) {
                if (n2 != n - n3) {
                    return false;
                }
                return string2.regionMatches(true, n3, string, 0, n2);
            }
            if (n2 == n4 - n3 && string2.regionMatches(true, n3, string, 0, n2)) {
                return true;
            }
            n3 = n4 + 1;
        }
        return false;
    }

    protected static int name2type(String string) {
        for (int i = 0; i < names.length; ++i) {
            if (!Util.byte2str(names[i]).equals(string)) continue;
            return i + 1;
        }
        return 6;
    }

    public String getComment() {
        return this.comment;
    }

    public String getFingerPrint(JSch object) {
        try {
            object = (HASH)Class.forName(JSch.getConfig("md5")).newInstance();
        }
        catch (Exception exception) {
            PrintStream printStream = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getFingerPrint: ");
            stringBuilder.append(exception);
            printStream.println(stringBuilder.toString());
            object = null;
        }
        return Util.getFingerPrint((HASH)object, this.key);
    }

    public String getHost() {
        return this.host;
    }

    public String getKey() {
        return Util.byte2str(Util.toBase64(this.key, 0, this.key.length));
    }

    public String getMarker() {
        return this.marker;
    }

    public String getType() {
        if (this.type != 1 && this.type != 2 && this.type != 3 && this.type != 4 && this.type != 5) {
            return "UNKNOWN";
        }
        return Util.byte2str(names[this.type - 1]);
    }

    boolean isMatched(String string) {
        return this.isIncluded(string);
    }
}
