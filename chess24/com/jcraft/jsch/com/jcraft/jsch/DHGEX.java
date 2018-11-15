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

public class DHGEX
extends KeyExchange {
    private static final int SSH_MSG_KEX_DH_GEX_GROUP = 31;
    private static final int SSH_MSG_KEX_DH_GEX_INIT = 32;
    private static final int SSH_MSG_KEX_DH_GEX_REPLY = 33;
    private static final int SSH_MSG_KEX_DH_GEX_REQUEST = 34;
    static int min = 1024;
    static int preferred = 1024;
    byte[] I_C;
    byte[] I_S;
    byte[] V_C;
    byte[] V_S;
    private Buffer buf;
    DH dh;
    private byte[] e;
    private byte[] g;
    protected String hash = "sha-1";
    int max = 1024;
    private byte[] p;
    private Packet packet;
    private int state;

    protected int check2048(Class object, int n) throws Exception {
        object = (DH)object.newInstance();
        object.init();
        byte[] arrby = new byte[257];
        arrby[1] = -35;
        arrby[256] = 115;
        object.setP(arrby);
        object.setG(new byte[]{2});
        try {
            object.getE();
            return 2048;
        }
        catch (Exception exception) {
            return n;
        }
    }

    @Override
    public int getState() {
        return this.state;
    }

    @Override
    public void init(Session object, byte[] object2, byte[] arrby, byte[] arrby2, byte[] arrby3) throws Exception {
        int n;
        this.session = object;
        this.V_S = object2;
        this.V_C = arrby;
        this.I_S = arrby2;
        this.I_C = arrby3;
        try {
            this.sha = (HASH)Class.forName(object.getConfig(this.hash)).newInstance();
            this.sha.init();
        }
        catch (Exception exception) {
            System.err.println(exception);
        }
        this.buf = new Buffer();
        this.packet = new Packet(this.buf);
        object2 = Class.forName(object.getConfig("dh"));
        this.max = n = this.check2048((Class)object2, this.max);
        preferred = n;
        this.dh = (DH)object2.newInstance();
        this.dh.init();
        this.packet.reset();
        this.buf.putByte((byte)34);
        this.buf.putInt(min);
        this.buf.putInt(preferred);
        this.buf.putInt(this.max);
        object.write(this.packet);
        if (JSch.getLogger().isEnabled(1)) {
            object = JSch.getLogger();
            object2 = new StringBuilder();
            object2.append("SSH_MSG_KEX_DH_GEX_REQUEST(");
            object2.append(min);
            object2.append("<");
            object2.append(preferred);
            object2.append("<");
            object2.append(this.max);
            object2.append(") sent");
            object.log(1, object2.toString());
            JSch.getLogger().log(1, "expecting SSH_MSG_KEX_DH_GEX_GROUP");
        }
        this.state = 31;
    }

    @Override
    public boolean next(Buffer arrby) throws Exception {
        int n = this.state;
        if (n != 31) {
            if (n != 33) {
                return false;
            }
            arrby.getInt();
            arrby.getByte();
            n = arrby.getByte();
            if (n != 33) {
                arrby = System.err;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("type: must be SSH_MSG_KEX_DH_GEX_REPLY ");
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
            this.buf.putInt(min);
            this.buf.putInt(preferred);
            this.buf.putInt(this.max);
            this.buf.putMPInt(this.p);
            this.buf.putMPInt(this.g);
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
        arrby.getInt();
        arrby.getByte();
        n = arrby.getByte();
        if (n != 31) {
            arrby = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("type: must be SSH_MSG_KEX_DH_GEX_GROUP ");
            stringBuilder.append(n);
            arrby.println(stringBuilder.toString());
            return false;
        }
        this.p = arrby.getMPInt();
        this.g = arrby.getMPInt();
        this.dh.setP(this.p);
        this.dh.setG(this.g);
        this.e = this.dh.getE();
        this.packet.reset();
        this.buf.putByte((byte)32);
        this.buf.putMPInt(this.e);
        this.session.write(this.packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_KEX_DH_GEX_INIT sent");
            JSch.getLogger().log(1, "expecting SSH_MSG_KEX_DH_GEX_REPLY");
        }
        this.state = 33;
        return true;
    }
}
