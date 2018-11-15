/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.server;

import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.server.WebSocketServer;

public class WebSocketServer.WebSocketWorker
extends Thread {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private BlockingQueue<WebSocketImpl> iqueue = new LinkedBlockingQueue<WebSocketImpl>();

    public WebSocketServer.WebSocketWorker() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WebSocketWorker-");
        stringBuilder.append(this.getId());
        this.setName(stringBuilder.toString());
        this.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(WebSocketServer.this){
            final /* synthetic */ WebSocketServer val$this$0;
            {
                this.val$this$0 = webSocketServer;
            }

            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(thread, throwable);
            }
        });
    }

    public void put(WebSocketImpl webSocketImpl) throws InterruptedException {
        this.iqueue.put(webSocketImpl);
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
        Object object;
        Object object2;
        block10 : {
            object = null;
            try {
                WebSocketImpl webSocketImpl;
                do {
                    webSocketImpl = this.iqueue.take();
                    object = (ByteBuffer)webSocketImpl.inQueue.poll();
                    webSocketImpl.decode((ByteBuffer)object);
                    break;
                } while (true);
                {
                    catch (Throwable throwable) {
                        WebSocketServer.this.pushBuffer((ByteBuffer)object);
                        throw throwable;
                    }
                }
                {
                    WebSocketServer.this.pushBuffer((ByteBuffer)object);
                    object = webSocketImpl;
                    continue;
                }
                catch (RuntimeException runtimeException) {
                    object2 = webSocketImpl;
                    break block10;
                }
                catch (RuntimeException runtimeException) {
                    object2 = object;
                    object = runtimeException;
                }
            }
            catch (InterruptedException interruptedException) {
                return;
            }
        }
        WebSocketServer.this.handleFatal((WebSocket)object2, (Exception)object);
    }

}
