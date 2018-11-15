/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSession;
import com.jcraft.jsch.IO;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Request;
import com.jcraft.jsch.RequestExec;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

public class ChannelExec
extends ChannelSession {
    byte[] command = new byte[0];

    public InputStream getErrStream() throws IOException {
        return this.getExtInputStream();
    }

    @Override
    void init() throws JSchException {
        this.io.setInputStream(this.getSession().in);
        this.io.setOutputStream(this.getSession().out);
    }

    public void setCommand(String string) {
        this.command = Util.str2byte(string);
    }

    public void setCommand(byte[] arrby) {
        this.command = arrby;
    }

    public void setErrStream(OutputStream outputStream) {
        this.setExtOutputStream(outputStream);
    }

    public void setErrStream(OutputStream outputStream, boolean bl) {
        this.setExtOutputStream(outputStream, bl);
    }

    @Override
    public void start() throws JSchException {
        Session session = this.getSession();
        try {
            this.sendRequests();
            ((Request)new RequestExec(this.command)).request(session, this);
            if (this.io.in != null) {
                Thread thread = this.thread = new Thread(this);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Exec thread ");
                stringBuilder.append(session.getHost());
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
                throw new JSchException("ChannelExec", exception);
            }
            throw new JSchException("ChannelExec");
        }
    }
}
