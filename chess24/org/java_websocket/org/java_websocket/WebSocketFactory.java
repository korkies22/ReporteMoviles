/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket;

import java.net.Socket;
import java.util.List;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketAdapter;
import org.java_websocket.drafts.Draft;

public interface WebSocketFactory {
    public WebSocket createWebSocket(WebSocketAdapter var1, List<Draft> var2, Socket var3);

    public WebSocket createWebSocket(WebSocketAdapter var1, Draft var2, Socket var3);
}
