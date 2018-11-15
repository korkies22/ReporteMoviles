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

class RequestAgentForwarding
extends Request {
    RequestAgentForwarding() {
    }

    @Override
    public void request(Session session, Channel channel) throws Exception {
        super.request(session, channel);
        this.setReply(false);
        Buffer buffer = new Buffer();
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)98);
        buffer.putInt(channel.getRecipient());
        buffer.putString(Util.str2byte("auth-agent-req@openssh.com"));
        buffer.putByte((byte)(this.waitForReply() ? 1 : 0));
        this.write(packet);
        session.agent_forwarding = true;
    }
}
