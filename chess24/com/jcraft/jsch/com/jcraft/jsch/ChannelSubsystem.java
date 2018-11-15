/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSession;
import com.jcraft.jsch.IO;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Request;
import com.jcraft.jsch.RequestPtyReq;
import com.jcraft.jsch.RequestSubsystem;
import com.jcraft.jsch.RequestX11;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

public class ChannelSubsystem
extends ChannelSession {
    boolean pty = false;
    String subsystem = "";
    boolean want_reply = true;
    boolean xforwading = false;

    public InputStream getErrStream() throws IOException {
        return this.getExtInputStream();
    }

    @Override
    void init() throws JSchException {
        this.io.setInputStream(this.getSession().in);
        this.io.setOutputStream(this.getSession().out);
    }

    public void setErrStream(OutputStream outputStream) {
        this.setExtOutputStream(outputStream);
    }

    @Override
    public void setPty(boolean bl) {
        this.pty = bl;
    }

    public void setSubsystem(String string) {
        this.subsystem = string;
    }

    public void setWantReply(boolean bl) {
        this.want_reply = bl;
    }

    @Override
    public void setXForwarding(boolean bl) {
        this.xforwading = bl;
    }

    @Override
    public void start() throws JSchException {
        Session session = this.getSession();
        try {
            if (this.xforwading) {
                ((Request)new RequestX11()).request(session, this);
            }
            if (this.pty) {
                ((Request)new RequestPtyReq()).request(session, this);
            }
            new RequestSubsystem().request(session, this, this.subsystem, this.want_reply);
            if (this.io.in != null) {
                Thread thread = this.thread = new Thread(this);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Subsystem for ");
                stringBuilder.append(session.host);
                thread.setName(stringBuilder.toString());
                if (session.daemon_thread) {
                    this.thread.setDaemon(session.daemon_thread);
                }
                this.thread.start();
            }
            return;
        }
        catch (Exception exception) {
            if (exception instanceof JSchException) {
                throw (JSchException)exception;
            }
            if (exception instanceof Throwable) {
                throw new JSchException("ChannelSubsystem", exception);
            }
            throw new JSchException("ChannelSubsystem");
        }
    }
}
