/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.IO;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.Random;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Hashtable;

class ChannelX11
extends Channel {
    private static final int LOCAL_MAXIMUM_PACKET_SIZE = 16384;
    private static final int LOCAL_WINDOW_SIZE_MAX = 131072;
    private static final int TIMEOUT = 10000;
    static byte[] cookie;
    private static byte[] cookie_hex;
    private static Hashtable faked_cookie_hex_pool;
    private static Hashtable faked_cookie_pool;
    private static String host = "127.0.0.1";
    private static int port = 6000;
    private static byte[] table;
    private byte[] cache = new byte[0];
    private boolean init = true;
    private Socket socket = null;

    static {
        faked_cookie_pool = new Hashtable();
        faked_cookie_hex_pool = new Hashtable();
        table = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    }

    ChannelX11() {
        this.setLocalWindowSizeMax(131072);
        this.setLocalWindowSize(131072);
        this.setLocalPacketSize(16384);
        this.type = Util.str2byte("x11");
        this.connected = true;
    }

    private byte[] addCache(byte[] arrby, int n, int n2) {
        byte[] arrby2 = new byte[this.cache.length + n2];
        System.arraycopy(arrby, n, arrby2, this.cache.length, n2);
        if (this.cache.length > 0) {
            System.arraycopy(this.cache, 0, arrby2, 0, this.cache.length);
        }
        this.cache = arrby2;
        return this.cache;
    }

    private static boolean equals(byte[] arrby, byte[] arrby2) {
        if (arrby.length != arrby2.length) {
            return false;
        }
        for (int i = 0; i < arrby.length; ++i) {
            if (arrby[i] == arrby2[i]) continue;
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static byte[] getFakedCookie(Session session) {
        Hashtable hashtable = faked_cookie_hex_pool;
        synchronized (hashtable) {
            byte[] arrby = (byte[])faked_cookie_hex_pool.get(session);
            Object object = arrby;
            if (arrby == null) {
                object = Session.random;
                arrby = new byte[16];
                synchronized (object) {
                    object.fill(arrby, 0, 16);
                }
                faked_cookie_pool.put(session, arrby);
                object = new byte[32];
                for (int i = 0; i < 16; ++i) {
                    int n = 2 * i;
                    object[n] = table[arrby[i] >>> 4 & 15];
                    object[n + 1] = table[arrby[i] & 15];
                }
                faked_cookie_hex_pool.put(session, object);
            }
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void removeFakedCookie(Session session) {
        Hashtable hashtable = faked_cookie_hex_pool;
        synchronized (hashtable) {
            faked_cookie_hex_pool.remove(session);
            faked_cookie_pool.remove(session);
            return;
        }
    }

    static int revtable(byte by) {
        for (int i = 0; i < table.length; ++i) {
            if (table[i] != by) continue;
            return i;
        }
        return 0;
    }

    static void setCookie(String arrby) {
        cookie_hex = Util.str2byte((String)arrby);
        cookie = new byte[16];
        for (int i = 0; i < 16; ++i) {
            arrby = cookie;
            byte[] arrby2 = cookie_hex;
            int n = i * 2;
            arrby[i] = (byte)(ChannelX11.revtable(arrby2[n]) << 4 & 240 | ChannelX11.revtable(cookie_hex[n + 1]) & 15);
        }
    }

    static void setHost(String string) {
        host = string;
    }

    static void setPort(int n) {
        port = n;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        try {
            this.socket = Util.createSocket(ChannelX11.host, ChannelX11.port, 10000);
            this.socket.setTcpNoDelay(true);
            this.io = new IO();
            this.io.setInputStream(this.socket.getInputStream());
            this.io.setOutputStream(this.socket.getOutputStream());
            this.sendOpenConfirmation();
        }
        catch (Exception var2_2) {}
        this.thread = Thread.currentThread();
        var2_1 = new Buffer(this.rmpsize);
        var3_4 = new Packet(var2_1);
        try {}
        catch (Exception var2_3) {}
        ** GOTO lbl-1000
        this.sendOpenFailure(1);
        this.close = true;
        this.disconnect();
        return;
lbl-1000: // 2 sources:
        {
            while (this.thread != null && this.io != null && this.io.in != null) {
                var1_5 = this.io.in.read(var2_1.buffer, 14, var2_1.buffer.length - 14 - 128);
                if (var1_5 <= 0) {
                    this.eof();
                } else if (!this.close) {
                    var3_4.reset();
                    var2_1.putByte((byte)94);
                    var2_1.putInt(this.recipient);
                    var2_1.putInt(var1_5);
                    var2_1.skip(var1_5);
                    this.getSession().write(var3_4, this, var1_5);
                    continue;
                }
                break;
            }
        }
        this.disconnect();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    void write(byte[] object, int n, int n2) throws IOException {
        byte[] arrby;
        byte[] arrby2;
        if (!this.init) {
            this.io.put((byte[])object, n, n2);
            return;
        }
        try {
            arrby2 = this.getSession();
            arrby = this.addCache((byte[])object, n, n2);
        }
        catch (JSchException jSchException) {
            throw new IOException(jSchException.toString());
        }
        int n3 = arrby.length;
        if (n3 < 9) {
            return;
        }
        int n4 = (arrby[6] & 255) * 256 + (arrby[7] & 255);
        int n5 = (arrby[8] & 255) * 256 + (arrby[9] & 255);
        if ((arrby[0] & 255) == 66) {
            n2 = n4;
            n = n5;
        } else {
            n2 = n4;
            n = n5;
            if ((arrby[0] & 255) == 108) {
                n2 = n4 << 8 & 65280 | n4 >>> 8 & 255;
                n = n5 << 8 & 65280 | n5 >>> 8 & 255;
            }
        }
        n5 = - n2 & 3;
        if (n3 < 12 + n2 + n5 + n) {
            return;
        }
        byte[] arrby3 = new byte[n];
        n2 = 12 + n2 + n5;
        System.arraycopy(arrby, n2, arrby3, 0, n);
        object = faked_cookie_pool;
        // MONITORENTER : object
        arrby2 = (byte[])faked_cookie_pool.get(arrby2);
        // MONITOREXIT : object
        if (ChannelX11.equals(arrby3, arrby2)) {
            if (cookie != null) {
                System.arraycopy(cookie, 0, arrby, n2, n);
            }
        } else {
            this.thread = null;
            this.eof();
            this.io.close();
            this.disconnect();
        }
        this.init = false;
        this.io.put(arrby, 0, n3);
        this.cache = null;
    }
}
