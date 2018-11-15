/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.client;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;

private class WebSocketClient.WebsocketWriteThread
implements Runnable {
    private WebSocketClient.WebsocketWriteThread() {
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        Thread.currentThread().setName("WebsocketWriteThread");
        try {
            while (!Thread.interrupted()) {
                ByteBuffer byteBuffer = WebSocketClient.access$100((WebSocketClient)WebSocketClient.this).outQueue.take();
                WebSocketClient.this.ostream.write(byteBuffer.array(), 0, byteBuffer.limit());
                WebSocketClient.this.ostream.flush();
            }
            return;
        }
        catch (IOException iOException) {}
        WebSocketClient.this.engine.eot();
        return;
        catch (InterruptedException interruptedException) {
            return;
        }
    }
}
