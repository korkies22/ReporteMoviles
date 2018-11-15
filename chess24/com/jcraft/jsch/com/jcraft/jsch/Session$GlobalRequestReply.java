/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Session;

private class Session.GlobalRequestReply {
    private int port = 0;
    private int reply = -1;
    private Thread thread = null;

    private Session.GlobalRequestReply() {
    }

    int getPort() {
        return this.port;
    }

    int getReply() {
        return this.reply;
    }

    Thread getThread() {
        return this.thread;
    }

    void setPort(int n) {
        this.port = n;
    }

    void setReply(int n) {
        this.reply = n;
    }

    void setThread(Thread thread) {
        this.thread = thread;
        this.reply = -1;
    }
}
