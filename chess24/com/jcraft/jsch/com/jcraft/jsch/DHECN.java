/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.ECDH;
import com.jcraft.jsch.HASH;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.KeyExchange;
import com.jcraft.jsch.KeyPairECDSA;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.Util;
import java.io.PrintStream;

public abstract class DHECN
extends KeyExchange {
    private static final int SSH_MSG_KEX_ECDH_INIT = 30;
    private static final int SSH_MSG_KEX_ECDH_REPLY = 31;
    byte[] I_C;
    byte[] I_S;
    byte[] Q_C;
    byte[] V_C;
    byte[] V_S;
    private Buffer buf;
    byte[] e;
    private ECDH ecdh;
    protected int key_size;
    private Packet packet;
    protected String sha_name;
    private int state;

    @Override
    public int getState() {
        return this.state;
    }

    @Override
    public void init(Session session, byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4) throws Exception {
        block6 : {
            this.session = session;
            this.V_S = arrby;
            this.V_C = arrby2;
            this.I_S = arrby3;
            this.I_C = arrby4;
            try {
                this.sha = (HASH)Class.forName(session.getConfig(this.sha_name)).newInstance();
                this.sha.init();
            }
            catch (Exception exception) {
                System.err.println(exception);
            }
            this.buf = new Buffer();
            this.packet = new Packet(this.buf);
            this.packet.reset();
            this.buf.putByte((byte)30);
            try {
                this.ecdh = (ECDH)Class.forName(session.getConfig("ecdh-sha2-nistp")).newInstance();
                this.ecdh.init(this.key_size);
                this.Q_C = this.ecdh.getQ();
                this.buf.putString(this.Q_C);
                if (arrby != null) break block6;
                return;
            }
            catch (Exception exception) {
                if (exception instanceof Throwable) {
                    throw new JSchException(exception.toString(), exception);
                }
                throw new JSchException(exception.toString());
            }
        }
        session.write(this.packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_KEX_ECDH_INIT sent");
            JSch.getLogger().log(1, "expecting SSH_MSG_KEX_ECDH_REPLY");
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
        byte[] arrby2 = arrby.getString();
        byte[][] arrby3 = KeyPairECDSA.fromPoint(arrby2);
        if (!this.ecdh.validate(arrby3[0], arrby3[1])) {
            return false;
        }
        this.K = this.ecdh.getSecret(arrby3[0], arrby3[1]);
        this.K = this.normalize(this.K);
        arrby = arrby.getString();
        this.buf.reset();
        this.buf.putString(this.V_C);
        this.buf.putString(this.V_S);
        this.buf.putString(this.I_C);
        this.buf.putString(this.I_S);
        this.buf.putString(this.K_S);
        this.buf.putString(this.Q_C);
        this.buf.putString(arrby2);
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
