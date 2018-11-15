/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketAdapter;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.WebSocketListener;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshakeBuilder;
import org.java_websocket.handshake.HandshakeImpl1Client;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;

public abstract class WebSocketClient
extends WebSocketAdapter
implements Runnable,
WebSocket {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private CountDownLatch closeLatch = new CountDownLatch(1);
    private CountDownLatch connectLatch = new CountDownLatch(1);
    private int connectTimeout = 0;
    private Draft draft;
    private WebSocketImpl engine = null;
    private Map<String, String> headers;
    private InputStream istream;
    private OutputStream ostream;
    private Proxy proxy = Proxy.NO_PROXY;
    private Socket socket = null;
    protected URI uri = null;
    private Thread writeThread;

    public WebSocketClient(URI uRI) {
        this(uRI, new Draft_17());
    }

    public WebSocketClient(URI uRI, Draft draft) {
        this(uRI, draft, null, 0);
    }

    public WebSocketClient(URI uRI, Draft draft, Map<String, String> map, int n) {
        if (uRI == null) {
            throw new IllegalArgumentException();
        }
        if (draft == null) {
            throw new IllegalArgumentException("null as draft is permitted for `WebSocketServer` only!");
        }
        this.uri = uRI;
        this.draft = draft;
        this.headers = map;
        this.connectTimeout = n;
        this.engine = new WebSocketImpl((WebSocketListener)this, draft);
    }

    private int getPort() {
        int n = this.uri.getPort();
        if (n == -1) {
            String string = this.uri.getScheme();
            if (string.equals("wss")) {
                return 443;
            }
            if (string.equals("ws")) {
                return 80;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unkonow scheme");
            stringBuilder.append(string);
            throw new RuntimeException(stringBuilder.toString());
        }
        return n;
    }

    private void sendHandshake() throws InvalidHandshakeException {
        Object object;
        String object32;
        Object object2;
        block9 : {
            block8 : {
                object2 = this.uri.getPath();
                object32 = this.uri.getQuery();
                if (object2 == null) break block8;
                object = object2;
                if (object2.length() != 0) break block9;
            }
            object = "/";
        }
        object2 = object;
        if (object32 != null) {
            object2 = new StringBuilder();
            object2.append((String)object);
            object2.append("?");
            object2.append(object32);
            object2 = object2.toString();
        }
        int n = this.getPort();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.uri.getHost());
        if (n != 80) {
            object = new StringBuilder();
            object.append(":");
            object.append(n);
            object = object.toString();
        } else {
            object = "";
        }
        stringBuilder.append((String)object);
        String string = stringBuilder.toString();
        object = new HandshakeImpl1Client();
        object.setResourceDescriptor((String)object2);
        object.put("Host", string);
        if (this.headers != null) {
            for (Map.Entry entry : this.headers.entrySet()) {
                object.put((String)entry.getKey(), (String)entry.getValue());
            }
        }
        this.engine.startHandshake((ClientHandshakeBuilder)object);
    }

    @Override
    public void close() {
        if (this.writeThread != null) {
            this.engine.close(1000);
        }
    }

    @Override
    public void close(int n) {
        this.engine.close();
    }

    @Override
    public void close(int n, String string) {
        this.engine.close(n, string);
    }

    public void closeBlocking() throws InterruptedException {
        this.close();
        this.closeLatch.await();
    }

    @Override
    public void closeConnection(int n, String string) {
        this.engine.closeConnection(n, string);
    }

    public void connect() {
        if (this.writeThread != null) {
            throw new IllegalStateException("WebSocketClient objects are not reuseable");
        }
        this.writeThread = new Thread(this);
        this.writeThread.start();
    }

    public boolean connectBlocking() throws InterruptedException {
        this.connect();
        this.connectLatch.await();
        return this.engine.isOpen();
    }

    public WebSocket getConnection() {
        return this.engine;
    }

    @Override
    public Draft getDraft() {
        return this.draft;
    }

    @Override
    public InetSocketAddress getLocalSocketAddress() {
        return this.engine.getLocalSocketAddress();
    }

    @Override
    public InetSocketAddress getLocalSocketAddress(WebSocket webSocket) {
        if (this.socket != null) {
            return (InetSocketAddress)this.socket.getLocalSocketAddress();
        }
        return null;
    }

    @Override
    public WebSocket.READYSTATE getReadyState() {
        return this.engine.getReadyState();
    }

    @Override
    public InetSocketAddress getRemoteSocketAddress() {
        return this.engine.getRemoteSocketAddress();
    }

    @Override
    public InetSocketAddress getRemoteSocketAddress(WebSocket webSocket) {
        if (this.socket != null) {
            return (InetSocketAddress)this.socket.getRemoteSocketAddress();
        }
        return null;
    }

    @Override
    public String getResourceDescriptor() {
        return this.uri.getPath();
    }

    public URI getURI() {
        return this.uri;
    }

    @Override
    public boolean hasBufferedData() {
        return this.engine.hasBufferedData();
    }

    @Override
    public boolean isClosed() {
        return this.engine.isClosed();
    }

    @Override
    public boolean isClosing() {
        return this.engine.isClosing();
    }

    @Override
    public boolean isConnecting() {
        return this.engine.isConnecting();
    }

    @Override
    public boolean isFlushAndClose() {
        return this.engine.isFlushAndClose();
    }

    @Override
    public boolean isOpen() {
        return this.engine.isOpen();
    }

    public abstract void onClose(int var1, String var2, boolean var3);

    public void onCloseInitiated(int n, String string) {
    }

    public void onClosing(int n, String string, boolean bl) {
    }

    public abstract void onError(Exception var1);

    public void onFragment(Framedata framedata) {
    }

    public abstract void onMessage(String var1);

    public void onMessage(ByteBuffer byteBuffer) {
    }

    public abstract void onOpen(ServerHandshake var1);

    @Override
    public final void onWebsocketClose(WebSocket webSocket, int n, String string, boolean bl) {
        this.connectLatch.countDown();
        this.closeLatch.countDown();
        if (this.writeThread != null) {
            this.writeThread.interrupt();
        }
        try {
            if (this.socket != null) {
                this.socket.close();
            }
        }
        catch (IOException iOException) {
            this.onWebsocketError(this, iOException);
        }
        this.onClose(n, string, bl);
    }

    @Override
    public void onWebsocketCloseInitiated(WebSocket webSocket, int n, String string) {
        this.onCloseInitiated(n, string);
    }

    @Override
    public void onWebsocketClosing(WebSocket webSocket, int n, String string, boolean bl) {
        this.onClosing(n, string, bl);
    }

    @Override
    public final void onWebsocketError(WebSocket webSocket, Exception exception) {
        this.onError(exception);
    }

    @Override
    public final void onWebsocketMessage(WebSocket webSocket, String string) {
        this.onMessage(string);
    }

    @Override
    public final void onWebsocketMessage(WebSocket webSocket, ByteBuffer byteBuffer) {
        this.onMessage(byteBuffer);
    }

    @Override
    public void onWebsocketMessageFragment(WebSocket webSocket, Framedata framedata) {
        this.onFragment(framedata);
    }

    @Override
    public final void onWebsocketOpen(WebSocket webSocket, Handshakedata handshakedata) {
        this.connectLatch.countDown();
        this.onOpen((ServerHandshake)handshakedata);
    }

    @Override
    public final void onWriteDemand(WebSocket webSocket) {
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
        if (this.socket == null) {
            this.socket = new Socket(this.proxy);
        } else if (this.socket.isClosed()) {
            throw new IOException();
        }
        if (!this.socket.isBound()) {
            this.socket.connect(new InetSocketAddress(this.uri.getHost(), this.getPort()), this.connectTimeout);
        }
        this.istream = this.socket.getInputStream();
        this.ostream = this.socket.getOutputStream();
        this.sendHandshake();
        this.writeThread = new Thread(new WebsocketWriteThread());
        this.writeThread.start();
        byte[] arrby = new byte[WebSocketImpl.RCVBUF];
        try {
            int n;
            while (!this.isClosed() && (n = this.istream.read(arrby)) != -1) {
                this.engine.decode(ByteBuffer.wrap(arrby, 0, n));
            }
            this.engine.eot();
            return;
        }
        catch (RuntimeException runtimeException) {
            this.onError(runtimeException);
            this.engine.closeConnection(1006, runtimeException.getMessage());
            return;
        }
        catch (Exception exception) {
            this.onWebsocketError(this.engine, exception);
            this.engine.closeConnection(-1, exception.getMessage());
            return;
        }
        catch (IOException iOException) {}
        this.engine.eot();
    }

    @Override
    public void send(String string) throws NotYetConnectedException {
        this.engine.send(string);
    }

    @Override
    public void send(ByteBuffer byteBuffer) throws IllegalArgumentException, NotYetConnectedException {
        this.engine.send(byteBuffer);
    }

    @Override
    public void send(byte[] arrby) throws NotYetConnectedException {
        this.engine.send(arrby);
    }

    @Override
    public void sendFragmentedFrame(Framedata.Opcode opcode, ByteBuffer byteBuffer, boolean bl) {
        this.engine.sendFragmentedFrame(opcode, byteBuffer, bl);
    }

    @Override
    public void sendFrame(Framedata framedata) {
        this.engine.sendFrame(framedata);
    }

    public void setProxy(Proxy proxy) {
        if (proxy == null) {
            throw new IllegalArgumentException();
        }
        this.proxy = proxy;
    }

    public void setSocket(Socket socket) {
        if (this.socket != null) {
            throw new IllegalStateException("socket has already been set");
        }
        this.socket = socket;
    }

    private class WebsocketWriteThread
    implements Runnable {
        private WebsocketWriteThread() {
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

}
