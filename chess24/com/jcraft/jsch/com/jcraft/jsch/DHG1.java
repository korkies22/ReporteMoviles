/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.DH;
import com.jcraft.jsch.HASH;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.KeyExchange;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.Util;
import java.io.PrintStream;

public class DHG1
extends KeyExchange {
    private static final int SSH_MSG_KEXDH_INIT = 30;
    private static final int SSH_MSG_KEXDH_REPLY = 31;
    static final byte[] g = new byte[]{2};
    static final byte[] p = new byte[]{0, -1, -1, -1, -1, -1, -1, -1, -1, -55, 15, -38, -94, 33, 104, -62, 52, -60, -58, 98, -117, -128, -36, 28, -47, 41, 2, 78, 8, -118, 103, -52, 116, 2, 11, -66, -90, 59, 19, -101, 34, 81, 74, 8, 121, -114, 52, 4, -35, -17, -107, 25, -77, -51, 58, 67, 27, 48, 43, 10, 109, -14, 95, 20, 55, 79, -31, 53, 109, 109, 81, -62, 69, -28, -123, -75, 118, 98, 94, 126, -58, -12, 76, 66, -23, -90, 55, -19, 107, 11, -1, 92, -74, -12, 6, -73, -19, -18, 56, 107, -5, 90, -119, -97, -91, -82, -97, 36, 17, 124, 75, 31, -26, 73, 40, 102, 81, -20, -26, 83, -127, -1, -1, -1, -1, -1, -1, -1, -1};
    byte[] I_C;
    byte[] I_S;
    byte[] V_C;
    byte[] V_S;
    private Buffer buf;
    DH dh;
    byte[] e;
    private Packet packet;
    private int state;

    @Override
    public int getState() {
        return this.state;
    }

    @Override
    public void init(Session session, byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4) throws Exception {
        this.session = session;
        this.V_S = arrby;
        this.V_C = arrby2;
        this.I_S = arrby3;
        this.I_C = arrby4;
        try {
            this.sha = (HASH)Class.forName(session.getConfig("sha-1")).newInstance();
            this.sha.init();
        }
        catch (Exception exception) {
            System.err.println(exception);
        }
        this.buf = new Buffer();
        this.packet = new Packet(this.buf);
        this.dh = (DH)Class.forName(session.getConfig("dh")).newInstance();
        this.dh.init();
        this.dh.setP(p);
        this.dh.setG(g);
        this.e = this.dh.getE();
        this.packet.reset();
        this.buf.putByte((byte)30);
        this.buf.putMPInt(this.e);
        session.write(this.packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_KEXDH_INIT sent");
            JSch.getLogger().log(1, "expecting SSH_MSG_KEXDH_REPLY");
        }
        this.state = 31;
    }

    @Override
    public boolean next(Buffer arrby) throws Exception {
        if (this.state != 31) {
            return false;
        }
        arrby.getInt();
        arrby.getByte();
        int n = arrby.getByte();
        if (n != 31) {
            arrby = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("type: must be 31 ");
            stringBuilder.append(n);
            arrby.println(stringBuilder.toString());
            return false;
        }
        this.K_S = arrby.getString();
        byte[] arrby2 = arrby.getMPInt();
        arrby = arrby.getString();
        this.dh.setF(arrby2);
        this.dh.checkRange();
        this.K = this.normalize(this.dh.getK());
        this.buf.reset();
        this.buf.putString(this.V_C);
        this.buf.putString(this.V_S);
        this.buf.putString(this.I_C);
        this.buf.putString(this.I_S);
        this.buf.putString(this.K_S);
        this.buf.putMPInt(this.e);
        this.buf.putMPInt(arrby2);
        this.buf.putMPInt(this.K);
        arrby2 = new byte[this.buf.getLength()];
        this.buf.getByte(arrby2);
        this.sha.update(arrby2, 0, arrby2.length);
        this.H = this.sha.digest();
        n = this.K_S[0] << 24 & -16777216 | this.K_S[1] << 16 & 16711680 | this.K_S[2] << 8 & 65280 | this.K_S[3] & 255;
        boolean bl = this.verify(Util.byte2str(this.K_S, 4, n), this.K_S, 4 + n, arrby);
        this.state = 0;
        return bl;
    }
}
