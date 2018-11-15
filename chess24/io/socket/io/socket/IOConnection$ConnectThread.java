/*
 * Decompiled with CFR 0_134.
 */
package io.socket;

import io.socket.IOConnection;

private class IOConnection.ConnectThread
extends Thread {
    public IOConnection.ConnectThread() {
        super("ConnectThread");
    }

    @Override
    public void run() {
        if (IOConnection.this.getState() == 0) {
            IOConnection.this.handshake();
        }
        IOConnection.this.connectTransport();
    }
}
