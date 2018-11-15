/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.IO;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.Request;
import com.jcraft.jsch.RequestAgentForwarding;
import com.jcraft.jsch.RequestEnv;
import com.jcraft.jsch.RequestPtyReq;
import com.jcraft.jsch.RequestWindowChange;
import com.jcraft.jsch.RequestX11;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.Util;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;

class ChannelSession
extends Channel {
    private static byte[] _session = Util.str2byte("session");
    protected boolean agent_forwarding = false;
    protected Hashtable env = null;
    protected boolean pty = false;
    protected int tcol = 80;
    protected byte[] terminal_mode = null;
    protected int thp = 480;
    protected int trow = 24;
    protected String ttype = "vt100";
    protected int twp = 640;
    protected boolean xforwading = false;

    ChannelSession() {
        this.type = _session;
        this.io = new IO();
    }

    private Hashtable getEnv() {
        if (this.env == null) {
            this.env = new Hashtable();
        }
        return this.env;
    }

    private byte[] toByteArray(Object object) {
        if (object instanceof String) {
            return Util.str2byte((String)object);
        }
        return (byte[])object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        Object object;
        object = new Buffer(this.rmpsize);
        Packet packet = new Packet((Buffer)object);
        try {
            while (this.isConnected() && this.thread != null && this.io != null && this.io.in != null) {
                int n = this.io.in.read(object.buffer, 14, object.buffer.length - 14 - 128);
                if (n == 0) continue;
                if (n == -1) {
                    this.eof();
                } else if (!this.close) {
                    packet.reset();
                    object.putByte((byte)94);
                    object.putInt(this.recipient);
                    object.putInt(n);
                    object.skip(n);
                    this.getSession().write(packet, this, n);
                    continue;
                }
                break;
            }
        }
        catch (Exception exception) {}
        if ((object = this.thread) != null) {
            synchronized (object) {
                object.notifyAll();
            }
        }
        this.thread = null;
    }

    protected void sendRequests() throws Exception {
        RequestPtyReq requestPtyReq;
        Object object;
        Session session = this.getSession();
        if (this.agent_forwarding) {
            ((Request)new RequestAgentForwarding()).request(session, this);
        }
        if (this.xforwading) {
            ((Request)new RequestX11()).request(session, this);
        }
        if (this.pty) {
            object = new RequestPtyReq();
            requestPtyReq = object;
            requestPtyReq.setTType(this.ttype);
            requestPtyReq.setTSize(this.tcol, this.trow, this.twp, this.thp);
            if (this.terminal_mode != null) {
                requestPtyReq.setTerminalMode(this.terminal_mode);
            }
            object.request(session, this);
        }
        if (this.env != null) {
            object = this.env.keys();
            while (object.hasMoreElements()) {
                requestPtyReq = object.nextElement();
                Object v = this.env.get(requestPtyReq);
                RequestEnv requestEnv = new RequestEnv();
                requestEnv.setEnv(this.toByteArray(requestPtyReq), this.toByteArray(v));
                ((Request)requestEnv).request(session, this);
            }
        }
    }

    public void setAgentForwarding(boolean bl) {
        this.agent_forwarding = bl;
    }

    public void setEnv(String string, String string2) {
        this.setEnv(Util.str2byte(string), Util.str2byte(string2));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setEnv(Hashtable hashtable) {
        synchronized (this) {
            this.env = hashtable;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setEnv(byte[] arrby, byte[] arrby2) {
        synchronized (this) {
            this.getEnv().put(arrby, arrby2);
            return;
        }
    }

    public void setPty(boolean bl) {
        this.pty = bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void setPtySize(int n, int n2, int n3, int n4) {
        this.setPtyType(this.ttype, n, n2, n3, n4);
        if (!this.pty) return;
        if (!this.isConnected()) {
            return;
        }
        try {
            RequestWindowChange requestWindowChange = new RequestWindowChange();
            requestWindowChange.setSize(n, n2, n3, n4);
            requestWindowChange.request(this.getSession(), this);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public void setPtyType(String string) {
        this.setPtyType(string, 80, 24, 640, 480);
    }

    public void setPtyType(String string, int n, int n2, int n3, int n4) {
        this.ttype = string;
        this.tcol = n;
        this.trow = n2;
        this.twp = n3;
        this.thp = n4;
    }

    public void setTerminalMode(byte[] arrby) {
        this.terminal_mode = arrby;
    }

    @Override
    public void setXForwarding(boolean bl) {
        this.xforwading = bl;
    }
}
