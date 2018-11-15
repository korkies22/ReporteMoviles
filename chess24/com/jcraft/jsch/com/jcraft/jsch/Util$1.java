/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import java.net.Socket;

static final class Util
implements Runnable {
    final /* synthetic */ String val$_host;
    final /* synthetic */ int val$_port;
    final /* synthetic */ Exception[] val$ee;
    final /* synthetic */ Socket[] val$sockp;

    Util(Socket[] arrsocket, String string, int n, Exception[] arrexception) {
        this.val$sockp = arrsocket;
        this.val$_host = string;
        this.val$_port = n;
        this.val$ee = arrexception;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        this.val$sockp[0] = null;
        try {
            this.val$sockp[0] = new Socket(this.val$_host, this.val$_port);
            return;
        }
        catch (Exception exception) {
            this.val$ee[0] = exception;
            if (this.val$sockp[0] != null && this.val$sockp[0].isConnected()) {
                try {
                    this.val$sockp[0].close();
                }
                catch (Exception exception2) {}
            }
            this.val$sockp[0] = null;
            return;
        }
    }
}
