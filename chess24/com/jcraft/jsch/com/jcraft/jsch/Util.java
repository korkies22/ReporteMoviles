/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.HASH;
import com.jcraft.jsch.JSchException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Vector;

class Util {
    private static final byte[] b64 = Util.str2byte("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=");
    private static String[] chars = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    static final byte[] empty = Util.str2byte("");

    Util() {
    }

    static boolean array_equals(byte[] arrby, byte[] arrby2) {
        int n = arrby.length;
        if (n != arrby2.length) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (arrby[i] == arrby2[i]) continue;
            return false;
        }
        return true;
    }

    static String byte2str(byte[] arrby) {
        return Util.byte2str(arrby, 0, arrby.length, "UTF-8");
    }

    static String byte2str(byte[] arrby, int n, int n2) {
        return Util.byte2str(arrby, n, n2, "UTF-8");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static String byte2str(byte[] arrby, int n, int n2, String string) {
        try {
            return new String(arrby, n, n2, string);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return new String(arrby, n, n2);
        }
    }

    static String byte2str(byte[] arrby, String string) {
        return Util.byte2str(arrby, 0, arrby.length, string);
    }

    static void bzero(byte[] arrby) {
        if (arrby == null) {
            return;
        }
        for (int i = 0; i < arrby.length; ++i) {
            arrby[i] = 0;
        }
    }

    static String checkTilde(String string) {
        String string2 = string;
        try {
            if (string.startsWith("~")) {
                string2 = string.replace("~", System.getProperty("user.home"));
            }
            return string2;
        }
        catch (SecurityException securityException) {
            return string;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Socket createSocket(String object, int n, int n2) throws JSchException {
        Thread thread;
        Object object2;
        Exception[] arrexception;
        block7 : {
            if (n2 == 0) {
                try {
                    return new Socket((String)object, n);
                }
                catch (Exception exception) {
                    String string = exception.toString();
                    if (!(exception instanceof Throwable)) throw new JSchException(string);
                    throw new JSchException(string, exception);
                }
            }
            object2 = new Socket[1];
            arrexception = new Exception[1];
            thread = new Thread(new Runnable((Socket[])object2, (String)object, n, arrexception){
                final /* synthetic */ String val$_host;
                final /* synthetic */ int val$_port;
                final /* synthetic */ Exception[] val$ee;
                final /* synthetic */ Socket[] val$sockp;
                {
                    this.val$sockp = arrsocket;
                    this.val$_host = string;
                    this.val$_port = n;
                    this.val$ee = arrexception;
                }

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    this.val$sockp[0] = null;
                    try {
                        this.val$sockp[0] = new Socket(this.val$_host, this.val$_port);
                        return;
                    }
                    catch (Exception exception) {
                        this.val$ee[0] = exception;
                        if (this.val$sockp[0] != null && this.val$sockp[0].isConnected()) {
                            try {
                                this.val$sockp[0].close();
                            }
                            catch (Exception exception2) {}
                        }
                        this.val$sockp[0] = null;
                        return;
                    }
                }
            });
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Opening Socket ");
            stringBuilder.append((String)object);
            thread.setName(stringBuilder.toString());
            thread.start();
            long l = n2;
            try {
                thread.join(l);
                object = "timeout: ";
                break block7;
            }
            catch (InterruptedException interruptedException) {}
            object = "";
        }
        if (object2[0] != null && object2[0].isConnected()) {
            return object2[0];
        }
        object2 = new StringBuilder();
        object2.append((String)object);
        object2.append("socket is not established");
        object = object2.toString();
        if (arrexception[0] != null) {
            object = arrexception[0].toString();
        }
        thread.interrupt();
        throw new JSchException((String)object, arrexception[0]);
    }

    static String diffString(String string, String[] arrstring) {
        String[] arrstring2 = Util.split(string, ",");
        string = null;
        block0 : for (int i = 0; i < arrstring2.length; ++i) {
            for (int j = 0; j < arrstring.length; ++j) {
                if (arrstring2[i].equals(arrstring[j])) continue block0;
            }
            if (string == null) {
                string = arrstring2[i];
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(",");
            stringBuilder.append(arrstring2[i]);
            string = stringBuilder.toString();
        }
        return string;
    }

    static byte[] fromBase64(byte[] arrby, int n, int n2) throws JSchException {
        int n3;
        byte[] arrby2 = new byte[n2];
        int n4 = n;
        int n5 = 0;
        do {
            n3 = n5;
            if (n4 >= n + n2) break;
            int n6 = Util.val(arrby[n4]);
            n3 = n4 + 1;
            arrby2[n5] = (byte)(n6 << 2 | (Util.val(arrby[n3]) & 48) >>> 4);
            n6 = n4 + 2;
            if (arrby[n6] == 61) {
                n3 = n5 + 1;
                break;
            }
            arrby2[n5 + 1] = (byte)((Util.val(arrby[n3]) & 15) << 4 | (Util.val(arrby[n6]) & 60) >>> 2);
            n3 = n4 + 3;
            if (arrby[n3] == 61) {
                n3 = n5 + 2;
                break;
            }
            arrby2[n5 + 2] = (byte)((Util.val(arrby[n6]) & 3) << 6 | Util.val(arrby[n3]) & 63);
            n5 += 3;
            n4 += 4;
            continue;
            break;
        } while (true);
        try {
            arrby = new byte[n3];
            System.arraycopy(arrby2, 0, arrby, 0, n3);
            return arrby;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new JSchException("fromBase64: invalid base64 data", arrayIndexOutOfBoundsException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static byte[] fromFile(String object) throws IOException {
        int n;
        object = Util.checkTilde((String)object);
        byte[] arrby = new byte[]((String)object);
        object = new FileInputStream((String)object);
        try {
            arrby = new byte[(int)arrby.length()];
            n = 0;
        }
        catch (Throwable throwable) {
            if (object != null) {
                object.close();
            }
            throw throwable;
        }
        do {
            int n2;
            if ((n2 = object.read(arrby, n, arrby.length - n)) <= 0) {
                object.close();
                if (object != null) {
                    object.close();
                }
                return arrby;
            }
            n += n2;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static String getFingerPrint(HASH var0, byte[] var1_2) {
        try {
            var0.init();
            var3_5 = ((void)var1_4).length;
            var2_6 = 0;
            var0.update((byte[])var1_4, 0, var3_5);
            var0_1 = var0.digest();
            var1_4 = new StringBuffer();
            ** while (var2_6 < var0_1.length)
        }
        catch (Exception var0_3) {
            return "???";
        }
lbl-1000: // 1 sources:
        {
            var3_5 = var0_1[var2_6] & 255;
            var1_4.append(Util.chars[var3_5 >>> 4 & 15]);
            var1_4.append(Util.chars[var3_5 & 15]);
            var2_6 = var3_5 = var2_6 + 1;
            if (var3_5 >= var0_1.length) continue;
            var1_4.append(":");
            var2_6 = var3_5;
            continue;
        }
lbl19: // 1 sources:
        return var1_4.toString();
    }

    private static boolean glob(byte[] arrby, int n, byte[] arrby2, int n2) {
        int n3;
        int n4;
        int n5 = arrby.length;
        if (n5 == 0) {
            return false;
        }
        int n6 = arrby2.length;
        do {
            n3 = n;
            n4 = n2;
            if (n >= n5) break;
            n3 = n;
            n4 = n2;
            if (n2 >= n6) break;
            if (arrby[n] == 92) {
                if (++n == n5) {
                    return false;
                }
                if (arrby[n] != arrby2[n2]) {
                    return false;
                }
                n += Util.skipUTF8Char(arrby[n]);
                n2 += Util.skipUTF8Char(arrby2[n2]);
                continue;
            }
            if (arrby[n] == 42) {
                while (n < n5 && arrby[n] == 42) {
                    ++n;
                }
                if (n5 == n) {
                    return true;
                }
                n4 = arrby[n];
                if (n4 == 63) {
                    while (n2 < n6) {
                        if (Util.glob(arrby, n, arrby2, n2)) {
                            return true;
                        }
                        n2 += Util.skipUTF8Char(arrby2[n2]);
                    }
                    return false;
                }
                if (n4 == 92) {
                    if (++n == n5) {
                        return false;
                    }
                    byte by = arrby[n];
                    while (n2 < n6) {
                        if (by == arrby2[n2] && Util.glob(arrby, Util.skipUTF8Char(by) + n, arrby2, Util.skipUTF8Char(arrby2[n2]) + n2)) {
                            return true;
                        }
                        n2 += Util.skipUTF8Char(arrby2[n2]);
                    }
                    return false;
                }
                for (n3 = n2; n3 < n6; n3 += Util.skipUTF8Char((byte)arrby2[n3])) {
                    if (n4 != arrby2[n3] || !Util.glob(arrby, n, arrby2, n3)) continue;
                    return true;
                }
                return false;
            }
            if (arrby[n] == 63) {
                ++n;
                n2 += Util.skipUTF8Char(arrby2[n2]);
                continue;
            }
            if (arrby[n] != arrby2[n2]) {
                return false;
            }
            n3 = n + Util.skipUTF8Char(arrby[n]);
            n4 = n2 + Util.skipUTF8Char(arrby2[n2]);
            n = n3;
            n2 = n4;
            if (n4 < n6) continue;
            if (n3 >= n5) {
                return true;
            }
            n = n3;
            n2 = n4;
            if (arrby[n3] == 42) break;
        } while (true);
        if (n3 == n5 && n4 == n6) {
            return true;
        }
        if (n4 >= n6 && arrby[n3] == 42) {
            while (n3 < n5) {
                if (arrby[n3] != 42) {
                    return false;
                }
                ++n3;
            }
            return true;
        }
        return false;
    }

    static boolean glob(byte[] arrby, byte[] arrby2) {
        return Util.glob0(arrby, 0, arrby2, 0);
    }

    private static boolean glob0(byte[] arrby, int n, byte[] arrby2, int n2) {
        if (arrby2.length > 0 && arrby2[0] == 46) {
            if (arrby.length > 0 && arrby[0] == 46) {
                if (arrby.length == 2 && arrby[1] == 42) {
                    return true;
                }
                return Util.glob(arrby, n + 1, arrby2, n2 + 1);
            }
            return false;
        }
        return Util.glob(arrby, n, arrby2, n2);
    }

    static String quote(String arrby) {
        int n;
        int n2;
        byte[] arrby2 = Util.str2byte((String)arrby);
        int n3 = 0;
        int n4 = n2 = 0;
        while (n2 < arrby2.length) {
            block10 : {
                block9 : {
                    byte by = arrby2[n2];
                    if (by == 92 || by == 63) break block9;
                    n = n4;
                    if (by != 42) break block10;
                }
                n = n4 + 1;
            }
            ++n2;
            n4 = n;
        }
        if (n4 == 0) {
            return arrby;
        }
        arrby = new byte[arrby2.length + n4];
        n2 = 0;
        for (n4 = n3; n4 < arrby2.length; ++n4) {
            byte by;
            block12 : {
                block11 : {
                    by = arrby2[n4];
                    if (by == 92 || by == 63) break block11;
                    n = n2;
                    if (by != 42) break block12;
                }
                arrby[n2] = 92;
                n = n2 + 1;
            }
            arrby[n] = by;
            n2 = n + 1;
        }
        return Util.byte2str(arrby);
    }

    private static int skipUTF8Char(byte by) {
        if ((byte)(by & 128) == 0) {
            return 1;
        }
        if ((byte)(by & 224) == -64) {
            return 2;
        }
        if ((byte)(by & 240) == -32) {
            return 3;
        }
        return 1;
    }

    static String[] split(String arrstring, String string) {
        int n;
        if (arrstring == null) {
            return null;
        }
        byte[] arrby = Util.str2byte((String)arrstring);
        Vector<String> vector = new Vector<String>();
        int n2 = 0;
        int n3 = 0;
        while ((n = arrstring.indexOf(string, n3)) >= 0) {
            vector.addElement(Util.byte2str(arrby, n3, n - n3));
            n3 = n + 1;
        }
        vector.addElement(Util.byte2str(arrby, n3, arrby.length - n3));
        arrstring = new String[vector.size()];
        for (n3 = n2; n3 < arrstring.length; ++n3) {
            arrstring[n3] = (String)vector.elementAt(n3);
        }
        return arrstring;
    }

    static byte[] str2byte(String string) {
        return Util.str2byte(string, "UTF-8");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static byte[] str2byte(String string, String arrby) {
        if (string == null) {
            return null;
        }
        try {
            return string.getBytes((String)arrby);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return string.getBytes();
        }
    }

    static byte[] toBase64(byte[] arrby, int n, int n2) {
        int n3;
        byte[] arrby2 = new byte[n2 * 2];
        int n4 = n2 / 3 * 3 + n;
        int n5 = 0;
        for (n3 = n; n3 < n4; n3 += 3) {
            byte by = arrby[n3];
            int n6 = n5 + 1;
            arrby2[n5] = b64[by >>> 2 & 63];
            by = arrby[n3];
            int n7 = n3 + 1;
            byte by2 = arrby[n7];
            n5 = n6 + 1;
            arrby2[n6] = b64[(by & 3) << 4 | by2 >>> 4 & 15];
            by = arrby[n7];
            n7 = n3 + 2;
            by2 = arrby[n7];
            n6 = n5 + 1;
            arrby2[n5] = b64[(by & 15) << 2 | by2 >>> 6 & 3];
            n5 = arrby[n7];
            arrby2[n6] = b64[n5 & 63];
            n5 = n6 + 1;
        }
        if ((n2 = n + n2 - n4) == 1) {
            n2 = arrby[n3];
            n = n5 + 1;
            arrby2[n5] = b64[n2 >>> 2 & 63];
            n5 = arrby[n3];
            n2 = n + 1;
            arrby2[n] = b64[(n5 & 3) << 4 & 63];
            n5 = n2 + 1;
            arrby2[n2] = 61;
            n = n5 + 1;
            arrby2[n5] = 61;
        } else {
            n = n5;
            if (n2 == 2) {
                n2 = arrby[n3];
                n = n5 + 1;
                arrby2[n5] = b64[n2 >>> 2 & 63];
                n5 = arrby[n3];
                n4 = arrby[++n3];
                n2 = n + 1;
                arrby2[n] = b64[(n5 & 3) << 4 | n4 >>> 4 & 15];
                n = arrby[n3];
                n5 = n2 + 1;
                arrby2[n2] = b64[(n & 15) << 2 & 63];
                n = n5 + 1;
                arrby2[n5] = 61;
            }
        }
        arrby = new byte[n];
        System.arraycopy(arrby2, 0, arrby, 0, n);
        return arrby;
    }

    static String toHex(byte[] arrby) {
        StringBuffer stringBuffer = new StringBuffer();
        int n = 0;
        while (n < arrby.length) {
            int n2;
            String string = Integer.toHexString(arrby[n] & 255);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0x");
            String string2 = string.length() == 1 ? "0" : "";
            stringBuilder.append(string2);
            stringBuilder.append(string);
            stringBuffer.append(stringBuilder.toString());
            n = n2 = n + 1;
            if (n2 >= arrby.length) continue;
            stringBuffer.append(":");
            n = n2;
        }
        return stringBuffer.toString();
    }

    static String unquote(String string) {
        byte[] arrby;
        byte[] arrby2 = Util.str2byte(string);
        if (arrby2.length == (arrby = Util.unquote(arrby2)).length) {
            return string;
        }
        return Util.byte2str(arrby);
    }

    static byte[] unquote(byte[] arrby) {
        int n = arrby.length;
        int n2 = 0;
        while (n2 < n) {
            if (arrby[n2] == 92) {
                int n3 = n2 + 1;
                if (n3 == n) break;
                System.arraycopy(arrby, n3, arrby, n2, arrby.length - n3);
                --n;
                n2 = n3;
                continue;
            }
            ++n2;
        }
        if (n == arrby.length) {
            return arrby;
        }
        byte[] arrby2 = new byte[n];
        System.arraycopy(arrby, 0, arrby2, 0, n);
        return arrby2;
    }

    private static byte val(byte by) {
        if (by == 61) {
            return 0;
        }
        for (int i = 0; i < b64.length; ++i) {
            if (by != b64[i]) continue;
            return (byte)i;
        }
        return 0;
    }

}
