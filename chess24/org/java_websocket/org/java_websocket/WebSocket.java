/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import org.java_websocket.drafts.Draft;
import org.java_websocket.framing.Framedata;

public interface WebSocket {
    public static final int DEFAULT_PORT = 80;
    public static final int DEFAULT_WSS_PORT = 443;

    public void close();

    public void close(int var1);

    public void close(int var1, String var2);

    public void closeConnection(int var1, String var2);

    public Draft getDraft();

    public InetSocketAddress getLocalSocketAddress();

    public READYSTATE getReadyState();

    public InetSocketAddress getRemoteSocketAddress();

    public String getResourceDescriptor();

    public boolean hasBufferedData();

    public boolean isClosed();

    public boolean isClosing();

    public boolean isConnecting();

    public boolean isFlushAndClose();

    public boolean isOpen();

    public void send(String var1) throws NotYetConnectedException;

    public void send(ByteBuffer var1) throws IllegalArgumentException, NotYetConnectedException;

    public void send(byte[] var1) throws IllegalArgumentException, NotYetConnectedException;

    public void sendFragmentedFrame(Framedata.Opcode var1, ByteBuffer var2, boolean var3);

    public void sendFrame(Framedata var1);

    public static enum READYSTATE {
        NOT_YET_CONNECTED,
        CONNECTING,
        OPEN,
        CLOSING,
        CLOSED;
        

        private READYSTATE() {
        }
    }

    public static enum Role {
        CLIENT,
        SERVER;
        

        private Role() {
        }
    }

}
