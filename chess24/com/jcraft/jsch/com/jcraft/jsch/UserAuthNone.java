/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserAuth;
import com.jcraft.jsch.UserInfo;
import com.jcraft.jsch.Util;

class UserAuthNone
extends UserAuth {
    private static final int SSH_MSG_SERVICE_ACCEPT = 6;
    private String methods = null;

    UserAuthNone() {
    }

    String getMethods() {
        return this.methods;
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
        super.start((Session)arrby);
        this.packet.reset();
        this.buf.putByte((byte)5);
        this.buf.putString(Util.str2byte("ssh-userauth"));
        arrby.write(this.packet);
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_SERVICE_REQUEST sent");
        }
        this.buf = arrby.read(this.buf);
        int n = this.buf.getCommand() == 6 ? 1 : 0;
        if (JSch.getLogger().isEnabled(1)) {
            JSch.getLogger().log(1, "SSH_MSG_SERVICE_ACCEPT received");
        }
        if (n == 0) {
            return false;
        }
        Object object = Util.str2byte(this.username);
        this.packet.reset();
        this.buf.putByte((byte)50);
        this.buf.putString((byte[])object);
        this.buf.putString(Util.str2byte("ssh-connection"));
        this.buf.putString(Util.str2byte("none"));
        arrby.write(this.packet);
        do {
            this.buf = arrby.read(this.buf);
            n = this.buf.getCommand() & 255;
            if (n == 52) {
                return true;
            }
            if (n == 53) {
                this.buf.getInt();
                this.buf.getByte();
                this.buf.getByte();
                object = this.buf.getString();
                this.buf.getString();
                object = Util.byte2str(object);
                if (this.userinfo == null) continue;
                this.userinfo.showMessage((String)object);
            }
            if (n == 51) {
                this.buf.getInt();
                this.buf.getByte();
                this.buf.getByte();
                arrby = this.buf.getString();
                this.buf.getByte();
                this.methods = Util.byte2str((byte[])arrby);
                return false;
            }
            arrby = new StringBuilder();
            arrby.append("USERAUTH fail (");
            arrby.append(n);
            arrby.append(")");
            throw new JSchException(arrby.toString());
            catch (RuntimeException runtimeException) {
            }
        } while (true);
    }
}
