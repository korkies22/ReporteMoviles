/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSession;
import com.jcraft.jsch.IO;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Request;
import com.jcraft.jsch.RequestShell;
import com.jcraft.jsch.Session;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

public class ChannelShell
extends ChannelSession {
    ChannelShell() {
        this.pty = true;
    }

    @Override
    void init() throws JSchException {
        this.io.setInputStream(this.getSession().in);
        this.io.setOutputStream(this.getSession().out);
    }

    @Override
    public void start() throws JSchException {
        Session session = this.getSession();
        try {
            this.sendRequests();
            ((Request)new RequestShell()).request(session, this);
            if (this.io.in != null) {
                Thread thread = this.thread = new Thread(this);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Shell for ");
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
                throw new JSchException("ChannelShell", exception);
            }
            throw new JSchException("ChannelShell");
        }
    }
}
