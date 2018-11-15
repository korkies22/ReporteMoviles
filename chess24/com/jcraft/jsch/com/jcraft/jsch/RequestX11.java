/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelX11;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.Request;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.Util;

class RequestX11
extends Request {
    RequestX11() {
    }

    @Override
    public void request(Session session, Channel channel) throws Exception {
        super.request(session, channel);
        Buffer buffer = new Buffer();
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)98);
        buffer.putInt(channel.getRecipient());
        buffer.putString(Util.str2byte("x11-req"));
        buffer.putByte((byte)(this.waitForReply() ? 1 : 0));
        buffer.putByte((byte)0);
        buffer.putString(Util.str2byte("MIT-MAGIC-COOKIE-1"));
        buffer.putString(ChannelX11.getFakedCookie(session));
        buffer.putInt(0);
        this.write(packet);
        session.x11_forwarding = true;
    }

    public void setCookie(String string) {
        ChannelX11.cookie = Util.str2byte(string);
    }
}
