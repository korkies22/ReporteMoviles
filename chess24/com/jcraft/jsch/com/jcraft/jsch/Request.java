/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.Session;

abstract class Request {
    private Channel channel = null;
    private boolean reply = false;
    private Session session = null;

    Request() {
    }

    void request(Session session, Channel channel) throws Exception {
        this.session = session;
        this.channel = channel;
        if (channel.connectTimeout > 0) {
            this.setReply(true);
        }
    }

    void setReply(boolean bl) {
        this.reply = bl;
    }

    boolean waitForReply() {
        return this.reply;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    void write(Packet packet) throws Exception {
        if (this.reply) {
            this.channel.reply = -1;
        }
        this.session.write(packet);
        if (!this.reply) return;
        long l = System.currentTimeMillis();
        long l2 = this.channel.connectTimeout;
        do {
            if (this.channel.isConnected() && this.channel.reply == -1) {
                Thread.sleep(10L);
                continue;
            }
            if (this.channel.reply != 0) return;
            throw new JSchException("failed to send channel request");
            catch (Exception exception) {}
        } while (l2 <= 0L || System.currentTimeMillis() - l <= l2);
        this.channel.reply = 0;
        throw new JSchException("channel request: timeout");
    }
}
