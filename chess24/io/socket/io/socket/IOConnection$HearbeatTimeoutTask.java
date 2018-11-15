/*
 * Decompiled with CFR 0_134.
 */
package io.socket;

import io.socket.IOConnection;
import io.socket.SocketIOException;
import java.util.TimerTask;

private class IOConnection.HearbeatTimeoutTask
extends TimerTask {
    private IOConnection.HearbeatTimeoutTask() {
    }

    @Override
    public void run() {
        IOConnection.this.error(new SocketIOException("Timeout Error. No heartbeat from server within life time of the socket. closing.", IOConnection.this.lastException));
    }
}
