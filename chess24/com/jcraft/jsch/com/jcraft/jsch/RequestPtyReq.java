/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.Request;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.Util;

class RequestPtyReq
extends Request {
    private int tcol = 80;
    private byte[] terminal_mode = Util.empty;
    private int thp = 480;
    private int trow = 24;
    private String ttype = "vt100";
    private int twp = 640;

    RequestPtyReq() {
    }

    @Override
    public void request(Session object, Channel channel) throws Exception {
        super.request((Session)object, channel);
        object = new Buffer();
        Packet packet = new Packet((Buffer)object);
        packet.reset();
        object.putByte((byte)98);
        object.putInt(channel.getRecipient());
        object.putString(Util.str2byte("pty-req"));
        object.putByte((byte)(this.waitForReply() ? 1 : 0));
        object.putString(Util.str2byte(this.ttype));
        object.putInt(this.tcol);
        object.putInt(this.trow);
        object.putInt(this.twp);
        object.putInt(this.thp);
        object.putString(this.terminal_mode);
        this.write(packet);
    }

    void setCode(String string) {
    }

    void setTSize(int n, int n2, int n3, int n4) {
        this.tcol = n;
        this.trow = n2;
        this.twp = n3;
        this.thp = n4;
    }

    void setTType(String string) {
        this.ttype = string;
    }

    void setTerminalMode(byte[] arrby) {
        this.terminal_mode = arrby;
    }
}
