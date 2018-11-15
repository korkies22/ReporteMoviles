/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.server;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;
import org.java_websocket.WebSocketAdapter;
import org.java_websocket.WebSocketFactory;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.drafts.Draft;
import org.java_websocket.server.WebSocketServer;

public static interface WebSocketServer.WebSocketServerFactory
extends WebSocketFactory {
    @Override
    public WebSocketImpl createWebSocket(WebSocketAdapter var1, List<Draft> var2, Socket var3);

    @Override
    public WebSocketImpl createWebSocket(WebSocketAdapter var1, Draft var2, Socket var3);

    public ByteChannel wrapChannel(SocketChannel var1, SelectionKey var2) throws IOException;
}
