/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.server;

import org.java_websocket.server.WebSocketServer;

class WebSocketServer.WebSocketWorker
implements Thread.UncaughtExceptionHandler {
    final /* synthetic */ WebSocketServer val$this$0;

    WebSocketServer.WebSocketWorker(WebSocketServer webSocketServer) {
        this.val$this$0 = webSocketServer;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(thread, throwable);
    }
}
