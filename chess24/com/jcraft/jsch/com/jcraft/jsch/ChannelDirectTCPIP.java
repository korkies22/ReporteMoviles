/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.IO;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.Util;
import java.io.InputStream;
import java.io.OutputStream;

public class ChannelDirectTCPIP
extends Channel {
    private static final int LOCAL_MAXIMUM_PACKET_SIZE = 16384;
    private static final int LOCAL_WINDOW_SIZE_MAX = 131072;
    private static final byte[] _type = Util.str2byte("direct-tcpip");
    String host;
    String originator_IP_address = "127.0.0.1";
    int originator_port = 0;
    int port;

    ChannelDirectTCPIP() {
        this.type = _type;
        this.setLocalWindowSizeMax(131072);
        this.setLocalWindowSize(131072);
        this.setLocalPacketSize(16384);
    }

    @Override
    public void connect(int n) throws JSchException {
        this.connectTimeout = n;
        try {
            Session session = this.getSession();
            if (!session.isConnected()) {
                throw new JSchException("session is down");
            }
            if (this.io.in != null) {
                Thread thread = this.thread = new Thread(this);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("DirectTCPIP thread ");
                stringBuilder.append(session.getHost());
                thread.setName(stringBuilder.toString());
                if (session.daemon_thread) {
                    this.thread.setDaemon(session.daemon_thread);
                }
                this.thread.start();
                return;
            }
            this.sendChannelOpen();
            return;
        }
        catch (Exception exception) {
            this.io.close();
            this.io = null;
            Channel.del(this);
            if (exception instanceof JSchException) {
                throw (JSchException)exception;
            }
            return;
        }
    }

    @Override
    protected Packet genChannelOpenPacket() {
        Buffer buffer = new Buffer(50 + this.host.length() + this.originator_IP_address.length() + 128);
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte)90);
        buffer.putString(this.type);
        buffer.putInt(this.id);
        buffer.putInt(this.lwsize);
        buffer.putInt(this.lmpsize);
        buffer.putString(Util.str2byte(this.host));
        buffer.putInt(this.port);
        buffer.putString(Util.str2byte(this.originator_IP_address));
        buffer.putInt(this.originator_port);
        return packet;
    }

    @Override
    void init() {
        this.io = new IO();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        Buffer buffer;
        Session session;
        Packet packet;
        block9 : {
            try {
                this.sendChannelOpen();
                buffer = new Buffer(this.rmpsize);
                packet = new Packet(buffer);
                session = this.getSession();
                break block9;
            }
            catch (Exception exception) {}
            if (!this.connected) {
                this.connected = true;
            }
            this.disconnect();
            return;
        }
        while (this.isConnected() && this.thread != null && this.io != null && this.io.in != null) {
            int n = this.io.in.read(buffer.buffer, 14, buffer.buffer.length - 14 - 128);
            if (n <= 0) {
                this.eof();
                break;
            }
            packet.reset();
            buffer.putByte((byte)94);
            buffer.putInt(this.recipient);
            buffer.putInt(n);
            buffer.skip(n);
            synchronized (this) {
                if (this.close) {
                    break;
                }
                session.write(packet, this, n);
            }
        }
        this.eof();
        this.disconnect();
    }

    public void setHost(String string) {
        this.host = string;
    }

    @Override
    public void setInputStream(InputStream inputStream) {
        this.io.setInputStream(inputStream);
    }

    public void setOrgIPAddress(String string) {
        this.originator_IP_address = string;
    }

    public void setOrgPort(int n) {
        this.originator_port = n;
    }

    @Override
    public void setOutputStream(OutputStream outputStream) {
        this.io.setOutputStream(outputStream);
    }

    public void setPort(int n) {
        this.port = n;
    }
}
