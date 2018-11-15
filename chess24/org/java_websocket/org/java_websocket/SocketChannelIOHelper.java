/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.util.concurrent.BlockingQueue;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.WrappedByteChannel;
import org.java_websocket.drafts.Draft;

public class SocketChannelIOHelper {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean batch(WebSocketImpl webSocketImpl, ByteChannel byteChannel) throws IOException {
        Object object;
        block8 : {
            block7 : {
                Object object2;
                block6 : {
                    object = object2 = (ByteBuffer)webSocketImpl.outQueue.peek();
                    if (object2 != null) break block6;
                    if (!(byteChannel instanceof WrappedByteChannel)) break block7;
                    object = object2 = (WrappedByteChannel)byteChannel;
                    if (object2.isNeedWrite()) {
                        object2.writeMore();
                        object = object2;
                    }
                    break block8;
                }
                do {
                    byteChannel.write((ByteBuffer)object);
                    if (object.remaining() > 0) {
                        return false;
                    }
                    webSocketImpl.outQueue.poll();
                    object = object2 = (ByteBuffer)webSocketImpl.outQueue.peek();
                } while (object2 != null);
            }
            object = null;
        }
        if (webSocketImpl.outQueue.isEmpty() && webSocketImpl.isFlushAndClose() && webSocketImpl.getDraft().getRole() == WebSocket.Role.SERVER) {
            synchronized (webSocketImpl) {
                webSocketImpl.closeConnection();
            }
        }
        boolean bl = true;
        if (object == null) return bl;
        if (((WrappedByteChannel)byteChannel).isNeedWrite()) return false;
        return true;
    }

    public static boolean read(ByteBuffer byteBuffer, WebSocketImpl webSocketImpl, ByteChannel byteChannel) throws IOException {
        byteBuffer.clear();
        int n = byteChannel.read(byteBuffer);
        byteBuffer.flip();
        boolean bl = false;
        if (n == -1) {
            webSocketImpl.eot();
            return false;
        }
        if (n != 0) {
            bl = true;
        }
        return bl;
    }

    public static boolean readMore(ByteBuffer byteBuffer, WebSocketImpl webSocketImpl, WrappedByteChannel wrappedByteChannel) throws IOException {
        byteBuffer.clear();
        int n = wrappedByteChannel.readMore(byteBuffer);
        byteBuffer.flip();
        if (n == -1) {
            webSocketImpl.eot();
            return false;
        }
        return wrappedByteChannel.isNeedRead();
    }
}
