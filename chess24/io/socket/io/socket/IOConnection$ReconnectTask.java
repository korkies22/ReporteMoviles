/*
 * Decompiled with CFR 0_134.
 */
package io.socket;

import io.socket.IOConnection;
import java.util.TimerTask;

private class IOConnection.ReconnectTask
extends TimerTask {
    private IOConnection.ReconnectTask() {
    }

    @Override
    public void run() {
        IOConnection.this.connectTransport();
        if (!IOConnection.this.keepAliveInQueue) {
            IOConnection.this.sendPlain("2::");
            IOConnection.this.keepAliveInQueue = true;
        }
    }
}
