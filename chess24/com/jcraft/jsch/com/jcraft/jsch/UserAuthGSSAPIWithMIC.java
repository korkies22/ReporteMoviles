/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.GSSContext;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.JSchPartialAuthException;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserAuth;
import com.jcraft.jsch.UserInfo;
import com.jcraft.jsch.Util;

public class UserAuthGSSAPIWithMIC
extends UserAuth {
    private static final int SSH_MSG_USERAUTH_GSSAPI_ERROR = 64;
    private static final int SSH_MSG_USERAUTH_GSSAPI_ERRTOK = 65;
    private static final int SSH_MSG_USERAUTH_GSSAPI_EXCHANGE_COMPLETE = 63;
    private static final int SSH_MSG_USERAUTH_GSSAPI_MIC = 66;
    private static final int SSH_MSG_USERAUTH_GSSAPI_RESPONSE = 60;
    private static final int SSH_MSG_USERAUTH_GSSAPI_TOKEN = 61;
    private static final String[] supported_method;
    private static final byte[][] supported_oid;

    static {
        supported_oid = new byte[][]{{6, 9, 42, -122, 72, -122, -9, 18, 1, 2, 2}};
        supported_method = new String[]{"gssapi-with-mic.krb5"};
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public boolean start(Session arrby) throws Exception {
        int n;
        Object object;
        super.start((Session)arrby);
        byte[] arrby2 = Util.str2byte(this.username);
        this.packet.reset();
        this.buf.putByte((byte)50);
        this.buf.putString(arrby2);
        this.buf.putString(Util.str2byte("ssh-connection"));
        this.buf.putString(Util.str2byte("gssapi-with-mic"));
        this.buf.putInt(supported_oid.length);
        for (n = 0; n < supported_oid.length; ++n) {
            this.buf.putString(supported_oid[n]);
        }
        arrby.write(this.packet);
        GSSContext gSSContext = null;
        do {
            this.buf = arrby.read(this.buf);
            n = this.buf.getCommand() & 255;
            if (n == 51) {
                return false;
            }
            if (n == 60) break;
            if (n != 53) return false;
            this.buf.getInt();
            this.buf.getByte();
            this.buf.getByte();
            object = this.buf.getString();
            this.buf.getString();
            object = Util.byte2str((byte[])object);
            if (this.userinfo == null) continue;
            this.userinfo.showMessage((String)object);
        } while (true);
        this.buf.getInt();
        this.buf.getByte();
        this.buf.getByte();
        byte[] arrby3 = this.buf.getString();
        n = 0;
        do {
            object = gSSContext;
            if (n >= supported_oid.length) break;
            if (Util.array_equals(arrby3, supported_oid[n])) {
                object = supported_method[n];
                break;
            }
            ++n;
        } while (true);
        if (object == null) {
            return false;
        }
        try {
            gSSContext = (GSSContext)Class.forName(arrby.getConfig((String)object)).newInstance();
        }
        catch (Exception exception) {
            return false;
        }
        try {
            gSSContext.create(this.username, arrby.host);
        }
        catch (JSchException jSchException) {
            return false;
        }
        object = new byte[]{};
        while (!gSSContext.isEstablished()) {
            object = gSSContext.init((byte[])object, 0, ((byte[])object).length);
            if (object != null) {
                this.packet.reset();
                this.buf.putByte((byte)61);
                this.buf.putString((byte[])object);
                arrby.write(this.packet);
            }
            if (gSSContext.isEstablished()) continue;
            this.buf = arrby.read(this.buf);
            int n2 = this.buf.getCommand() & 255;
            if (n2 == 64) {
                this.buf = arrby.read(this.buf);
                n = this.buf.getCommand() & 255;
            } else {
                n = n2;
                if (n2 == 65) {
                    this.buf = arrby.read(this.buf);
                    n = this.buf.getCommand() & 255;
                }
            }
            if (n == 51) {
                return false;
            }
            this.buf.getInt();
            this.buf.getByte();
            this.buf.getByte();
            object = this.buf.getString();
        }
        object = new Buffer();
        object.putString(arrby.getSessionId());
        object.putByte((byte)50);
        object.putString(arrby2);
        object.putString(Util.str2byte("ssh-connection"));
        object.putString(Util.str2byte("gssapi-with-mic"));
        object = gSSContext.getMIC(object.buffer, 0, object.getLength());
        if (object == null) {
            return false;
        }
        this.packet.reset();
        this.buf.putByte((byte)66);
        this.buf.putString((byte[])object);
        arrby.write(this.packet);
        gSSContext.dispose();
        this.buf = arrby.read(this.buf);
        n = this.buf.getCommand() & 255;
        if (n == 52) {
            return true;
        }
        if (n != 51) return false;
        this.buf.getInt();
        this.buf.getByte();
        this.buf.getByte();
        arrby = this.buf.getString();
        if (this.buf.getByte() == 0) return false;
        throw new JSchPartialAuthException(Util.byte2str(arrby));
        catch (JSchException jSchException) {
            return false;
        }
    }
}
