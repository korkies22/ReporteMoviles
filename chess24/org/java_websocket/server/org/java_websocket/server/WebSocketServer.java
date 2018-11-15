/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.java_websocket.SocketChannelIOHelper;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketAdapter;
import org.java_websocket.WebSocketFactory;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.WrappedByteChannel;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.server.DefaultWebSocketServerFactory;

public abstract class WebSocketServer
extends WebSocketAdapter
implements Runnable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static int DECODERS = Runtime.getRuntime().availableProcessors();
    private final InetSocketAddress address;
    private BlockingQueue<ByteBuffer> buffers;
    private final Collection<WebSocket> connections;
    private List<WebSocketWorker> decoders;
    private List<Draft> drafts;
    private List<WebSocketImpl> iqueue;
    private volatile AtomicBoolean isclosed = new AtomicBoolean(false);
    private int queueinvokes = 0;
    private AtomicInteger queuesize = new AtomicInteger(0);
    private Selector selector;
    private Thread selectorthread;
    private ServerSocketChannel server;
    private WebSocketServerFactory wsf = new DefaultWebSocketServerFactory();

    public WebSocketServer() throws UnknownHostException {
        this(new InetSocketAddress(80), DECODERS, null);
    }

    public WebSocketServer(InetSocketAddress inetSocketAddress) {
        this(inetSocketAddress, DECODERS, null);
    }

    public WebSocketServer(InetSocketAddress inetSocketAddress, int n) {
        this(inetSocketAddress, n, null);
    }

    public WebSocketServer(InetSocketAddress inetSocketAddress, int n, List<Draft> list) {
        this(inetSocketAddress, n, list, new HashSet<WebSocket>());
    }

    public WebSocketServer(InetSocketAddress object, int n, List<Draft> list, Collection<WebSocket> collection) {
        if (object != null && n >= 1 && collection != null) {
            this.drafts = list == null ? Collections.emptyList() : list;
            this.address = object;
            this.connections = collection;
            this.iqueue = new LinkedList<WebSocketImpl>();
            this.decoders = new ArrayList<WebSocketWorker>(n);
            this.buffers = new LinkedBlockingQueue<ByteBuffer>();
            for (int i = 0; i < n; ++i) {
                object = new WebSocketWorker();
                this.decoders.add((WebSocketWorker)object);
                object.start();
            }
            return;
        }
        throw new IllegalArgumentException("address and connectionscontainer must not be null and you need at least 1 decoder");
    }

    public WebSocketServer(InetSocketAddress inetSocketAddress, List<Draft> list) {
        this(inetSocketAddress, DECODERS, list);
    }

    private Socket getSocket(WebSocket webSocket) {
        return ((SocketChannel)((WebSocketImpl)webSocket).key.channel()).socket();
    }

    private void handleFatal(WebSocket webSocket, Exception exception) {
        this.onError(webSocket, exception);
        try {
            this.stop();
            return;
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            this.onError(null, interruptedException);
            return;
        }
        catch (IOException iOException) {
            this.onError(null, iOException);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void handleIOException(SelectionKey object, WebSocket object2, IOException iOException) {
        if (object2 != null) {
            object2.closeConnection(1006, iOException.getMessage());
            return;
        }
        if (object != null && (object = object.channel()) != null && object.isOpen()) {
            try {
                object.close();
            }
            catch (IOException iOException2) {}
            if (WebSocketImpl.DEBUG) {
                object = System.out;
                object2 = new StringBuilder();
                object2.append("Connection closed because of");
                object2.append(iOException);
                object.println(object2.toString());
            }
        }
    }

    private void pushBuffer(ByteBuffer byteBuffer) throws InterruptedException {
        if (this.buffers.size() > this.queuesize.intValue()) {
            return;
        }
        this.buffers.put(byteBuffer);
    }

    private void queue(WebSocketImpl webSocketImpl) throws InterruptedException {
        if (webSocketImpl.workerThread == null) {
            webSocketImpl.workerThread = this.decoders.get(this.queueinvokes % this.decoders.size());
            ++this.queueinvokes;
        }
        webSocketImpl.workerThread.put(webSocketImpl);
    }

    private ByteBuffer takeBuffer() throws InterruptedException {
        return this.buffers.take();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected boolean addConnection(WebSocket webSocket) {
        if (!this.isclosed.get()) {
            Collection<WebSocket> collection = this.connections;
            synchronized (collection) {
                return this.connections.add(webSocket);
            }
        }
        webSocket.close(1001);
        return true;
    }

    protected void allocateBuffers(WebSocket webSocket) throws InterruptedException {
        if (this.queuesize.get() >= 2 * this.decoders.size() + 1) {
            return;
        }
        this.queuesize.incrementAndGet();
        this.buffers.put(this.createBuffer());
    }

    public Collection<WebSocket> connections() {
        return this.connections;
    }

    public ByteBuffer createBuffer() {
        return ByteBuffer.allocate(WebSocketImpl.RCVBUF);
    }

    public InetSocketAddress getAddress() {
        return this.address;
    }

    public List<Draft> getDraft() {
        return Collections.unmodifiableList(this.drafts);
    }

    protected String getFlashSecurityPolicy() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"");
        stringBuilder.append(this.getPort());
        stringBuilder.append("\" /></cross-domain-policy>");
        return stringBuilder.toString();
    }

    @Override
    public InetSocketAddress getLocalSocketAddress(WebSocket webSocket) {
        return (InetSocketAddress)this.getSocket(webSocket).getLocalSocketAddress();
    }

    public int getPort() {
        int n;
        int n2 = n = this.getAddress().getPort();
        if (n == 0) {
            n2 = n;
            if (this.server != null) {
                n2 = this.server.socket().getLocalPort();
            }
        }
        return n2;
    }

    @Override
    public InetSocketAddress getRemoteSocketAddress(WebSocket webSocket) {
        return (InetSocketAddress)this.getSocket(webSocket).getRemoteSocketAddress();
    }

    public final WebSocketFactory getWebSocketFactory() {
        return this.wsf;
    }

    public abstract void onClose(WebSocket var1, int var2, String var3, boolean var4);

    public void onCloseInitiated(WebSocket webSocket, int n, String string) {
    }

    public void onClosing(WebSocket webSocket, int n, String string, boolean bl) {
    }

    protected boolean onConnect(SelectionKey selectionKey) {
        return true;
    }

    public abstract void onError(WebSocket var1, Exception var2);

    public void onFragment(WebSocket webSocket, Framedata framedata) {
    }

    public abstract void onMessage(WebSocket var1, String var2);

    public void onMessage(WebSocket webSocket, ByteBuffer byteBuffer) {
    }

    public abstract void onOpen(WebSocket var1, ClientHandshake var2);

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final void onWebsocketClose(WebSocket webSocket, int n, String string, boolean bl) {
        block6 : {
            this.selector.wakeup();
            if (!this.removeConnection(webSocket)) break block6;
            this.onClose(webSocket, n, string, bl);
        }
        this.releaseBuffers(webSocket);
        return;
        {
            catch (InterruptedException interruptedException) {}
        }
        catch (Throwable throwable) {
            try {
                this.releaseBuffers(webSocket);
                throw throwable;
            }
            catch (InterruptedException interruptedException) {}
            Thread.currentThread().interrupt();
            return;
            Thread.currentThread().interrupt();
            throw throwable;
        }
    }

    @Override
    public void onWebsocketCloseInitiated(WebSocket webSocket, int n, String string) {
        this.onCloseInitiated(webSocket, n, string);
    }

    @Override
    public void onWebsocketClosing(WebSocket webSocket, int n, String string, boolean bl) {
        this.onClosing(webSocket, n, string, bl);
    }

    @Override
    public final void onWebsocketError(WebSocket webSocket, Exception exception) {
        this.onError(webSocket, exception);
    }

    @Override
    public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket webSocket, Draft draft, ClientHandshake clientHandshake) throws InvalidDataException {
        return super.onWebsocketHandshakeReceivedAsServer(webSocket, draft, clientHandshake);
    }

    @Override
    public final void onWebsocketMessage(WebSocket webSocket, String string) {
        this.onMessage(webSocket, string);
    }

    @Override
    public final void onWebsocketMessage(WebSocket webSocket, ByteBuffer byteBuffer) {
        this.onMessage(webSocket, byteBuffer);
    }

    @Deprecated
    @Override
    public void onWebsocketMessageFragment(WebSocket webSocket, Framedata framedata) {
        this.onFragment(webSocket, framedata);
    }

    @Override
    public final void onWebsocketOpen(WebSocket webSocket, Handshakedata handshakedata) {
        if (this.addConnection(webSocket)) {
            this.onOpen(webSocket, (ClientHandshake)handshakedata);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void onWriteDemand(WebSocket webSocket) {
        block2 : {
            webSocket = (WebSocketImpl)webSocket;
            try {
                webSocket.key.interestOps(5);
                break block2;
            }
            catch (CancelledKeyException cancelledKeyException) {}
            webSocket.outQueue.clear();
        }
        this.selector.wakeup();
    }

    protected void releaseBuffers(WebSocket webSocket) throws InterruptedException {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected boolean removeConnection(WebSocket webSocket) {
        Collection<WebSocket> collection = this.connections;
        // MONITORENTER : collection
        boolean bl = this.connections.remove(webSocket);
        // MONITOREXIT : collection
        if (!this.isclosed.get()) return bl;
        if (this.connections.size() != 0) return bl;
        this.selectorthread.interrupt();
        return bl;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        block74 : {
            // MONITORENTER : this
            if (this.selectorthread != null) {
                var2_1 = new StringBuilder();
                var2_1.append(this.getClass().getName());
                var2_1.append(" can only be started once.");
                throw new IllegalStateException(var2_1.toString());
            }
            this.selectorthread = Thread.currentThread();
            if (this.isclosed.get()) {
                // MONITOREXIT : this
                return;
            }
            // MONITOREXIT : this
            var2_2 = this.selectorthread;
            var3_17 = new StringBuilder();
            var3_17.append("WebsocketSelector");
            var3_17.append(this.selectorthread.getId());
            var2_2.setName(var3_17.toString());
            try {
                this.server = ServerSocketChannel.open();
                this.server.configureBlocking(false);
                var2_2 = this.server.socket();
                var2_2.setReceiveBufferSize(WebSocketImpl.RCVBUF);
                var2_2.bind(this.address);
                this.selector = Selector.open();
                this.server.register(this.selector, this.server.validOps());
            }
            catch (IOException var2_15) {
                this.handleFatal(null, var2_15);
                return;
            }
            block42 : do {
                block72 : {
                    block71 : {
                        if (!(var1_19 = this.selectorthread.isInterrupted())) {
                            try {
                                this.selector.select();
                                var6_25 = this.selector.selectedKeys().iterator();
                                var2_2 = var3_17 = null;
                                break block71;
                            }
                            catch (IOException var2_8) {
                                var4_20 = var3_17 = null;
                                break block72;
                            }
                        }
                        if (this.decoders != null) {
                            var2_2 = this.decoders.iterator();
                            while (var2_2.hasNext()) {
                                var2_2.next().interrupt();
                            }
                        }
                        if (this.server == null) return;
                        this.server.close();
                        return;
                        catch (CancelledKeyException var2_16) {
                            continue;
                        }
                        catch (ClosedByInterruptException var2_9) {}
                        if (this.decoders != null) {
                            var2_10 = this.decoders.iterator();
                            while (var2_10.hasNext()) {
                                var2_10.next().interrupt();
                            }
                        }
                        if (this.server == null) return;
                        try {
                            this.server.close();
                            return;
                        }
                        catch (IOException var2_11) {
                            this.onError(null, var2_11);
                        }
                        return;
                        catch (InterruptedException var2_5) {}
                        if (this.decoders != null) {
                            var2_6 = this.decoders.iterator();
                            while (var2_6.hasNext()) {
                                var2_6.next().interrupt();
                            }
                        }
                        if (this.server == null) return;
                        try {
                            this.server.close();
                            return;
                        }
                        catch (IOException var2_7) {
                            this.onError(null, var2_7);
                        }
                        return;
                    }
                    do {
                        block73 : {
                            var5_22 = var2_2;
                            var4_20 = var2_2;
                            if (!var6_25.hasNext()) break;
                            var4_20 = var2_2;
                            var5_22 = var6_25.next();
                            var4_20 = var2_2;
                            if (!var5_22.isValid()) break block73;
                            var4_20 = var2_2;
                            if (var5_22.isAcceptable()) {
                                var4_20 = var2_2;
                                if (!this.onConnect((SelectionKey)var5_22)) {
                                    var4_20 = var2_2;
                                    var5_22.cancel();
                                } else {
                                    var4_20 = var2_2;
                                    var3_17 = this.server.accept();
                                    var4_20 = var2_2;
                                    var3_17.configureBlocking(false);
                                    var4_20 = var2_2;
                                    var7_27 = this.wsf.createWebSocket((WebSocketAdapter)this, this.drafts, var3_17.socket());
                                    var4_20 = var2_2;
                                    var7_27.key = var3_17.register(this.selector, 1, var7_27);
                                    var4_20 = var2_2;
                                    var7_27.channel = this.wsf.wrapChannel((SocketChannel)var3_17, var7_27.key);
                                    var4_20 = var2_2;
                                    var6_25.remove();
                                    var4_20 = var2_2;
                                    this.allocateBuffers(var7_27);
                                }
                                break block73;
                            }
                            var3_17 = var2_2;
                            var4_20 = var2_2;
                            if (!var5_22.isReadable()) ** GOTO lbl142
                            var4_20 = var2_2;
                            var2_2 = var3_17 = (WebSocketImpl)var5_22.attachment();
                            var4_20 = this.takeBuffer();
                            {
                                catch (IOException var4_21) {
                                    var3_17 = var2_2;
                                    var2_2 = var4_21;
                                    var4_20 = var5_22;
                                    break block72;
                                }
                            }
                            try {
                                if (SocketChannelIOHelper.read((ByteBuffer)var4_20, (WebSocketImpl)var3_17, var3_17.channel)) {
                                    if (var4_20.hasRemaining()) {
                                        var3_17.inQueue.put((ByteBuffer)var4_20);
                                        this.queue((WebSocketImpl)var3_17);
                                        var6_25.remove();
                                        if (var3_17.channel instanceof WrappedByteChannel && ((WrappedByteChannel)var3_17.channel).isNeedRead()) {
                                            this.iqueue.add((WebSocketImpl)var3_17);
                                        }
                                    } else {
                                        this.pushBuffer((ByteBuffer)var4_20);
                                    }
                                } else {
                                    this.pushBuffer((ByteBuffer)var4_20);
                                }
                                ** GOTO lbl142
                            }
                            catch (IOException var6_26) {
                                var2_2 = var3_17;
                                this.pushBuffer((ByteBuffer)var4_20);
                                var2_2 = var3_17;
                                throw var6_26;
lbl142: // 4 sources:
                                var4_20 = var3_17;
                                var2_2 = var3_17;
                                if (!var5_22.isWritable()) break block73;
                                var4_20 = var3_17;
                                var2_2 = var3_17 = (WebSocketImpl)var5_22.attachment();
                            }
                            catch (IOException var2_3) {
                                var3_17 = var4_20;
                                var4_20 = var5_22;
                                break block72;
                            }
                            {
                                if (SocketChannelIOHelper.batch((WebSocketImpl)var3_17, var3_17.channel)) {
                                    var2_2 = var3_17;
                                    if (var5_22.isValid()) {
                                        var2_2 = var3_17;
                                        var5_22.interestOps(1);
                                    }
                                }
                                var2_2 = var3_17;
                            }
                        }
                        var3_17 = var5_22;
                    } while (true);
                    do {
                        var4_20 = var5_22;
                        if (this.iqueue.isEmpty()) continue block42;
                        var4_20 = var5_22;
                        var2_2 = this.iqueue.remove(0);
                        var5_22 = (WrappedByteChannel)var2_2.channel;
                        var4_20 = this.takeBuffer();
                        {
                            catch (IOException var5_24) {
                                var4_20 = var3_17;
                                var3_17 = var2_2;
                                var2_2 = var5_24;
                                break block72;
                            }
                        }
                        try {
                            if (SocketChannelIOHelper.readMore((ByteBuffer)var4_20, (WebSocketImpl)var2_2, (WrappedByteChannel)var5_22)) {
                                this.iqueue.add((WebSocketImpl)var2_2);
                            }
                            if (var4_20.hasRemaining()) {
                                var2_2.inQueue.put((ByteBuffer)var4_20);
                                this.queue((WebSocketImpl)var2_2);
                            } else {
                                this.pushBuffer((ByteBuffer)var4_20);
                            }
                            var5_22 = var2_2;
                        }
                        catch (IOException var5_23) {
                            this.pushBuffer((ByteBuffer)var4_20);
                            throw var5_23;
                        }
                    } while (true);
                    catch (IOException var2_4) {
                        var5_22 = var3_17;
                        var3_17 = var4_20;
                        var4_20 = var5_22;
                    }
                }
                if (var4_20 != null) {
                    var4_20.cancel();
                }
                this.handleIOException((SelectionKey)var4_20, (WebSocket)var3_17, (IOException)var2_2);
            } while (true);
            {
                block75 : {
                    catch (Throwable var2_12) {
                        break block74;
                    }
                    catch (RuntimeException var2_13) {}
                    {
                        this.handleFatal(null, var2_13);
                        if (this.decoders == null) break block75;
                        var2_2 = this.decoders.iterator();
                    }
                    while (var2_2.hasNext()) {
                        var2_2.next().interrupt();
                    }
                }
                if (this.server == null) return;
                try {
                    this.server.close();
                    return;
                }
                catch (IOException var2_14) {
                    this.onError(null, var2_14);
                }
            }
            return;
        }
        if (this.decoders != null) {
            var3_17 = this.decoders.iterator();
            while (var3_17.hasNext()) {
                ((WebSocketWorker)var3_17.next()).interrupt();
            }
        }
        if (this.server == null) throw var2_12;
        try {
            this.server.close();
            throw var2_12;
        }
        catch (IOException var3_18) {
            this.onError(null, var3_18);
        }
        throw var2_12;
    }

    public final void setWebSocketFactory(WebSocketServerFactory webSocketServerFactory) {
        this.wsf = webSocketServerFactory;
    }

    public void start() {
        if (this.selectorthread != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getClass().getName());
            stringBuilder.append(" can only be started once.");
            throw new IllegalStateException(stringBuilder.toString());
        }
        new Thread(this).start();
    }

    public void stop() throws IOException, InterruptedException {
        this.stop(0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void stop(int n) throws InterruptedException {
        ArrayList<WebSocket> arrayList;
        if (!this.isclosed.compareAndSet(false, true)) {
            return;
        }
        Object object = this.connections;
        synchronized (object) {
            arrayList = new ArrayList<WebSocket>(this.connections);
        }
        object = arrayList.iterator();
        while (object.hasNext()) {
            ((WebSocket)object.next()).close(1001);
        }
        synchronized (this) {
            if (this.selectorthread != null) {
                Thread.currentThread();
                object = this.selectorthread;
                if (this.selectorthread != Thread.currentThread()) {
                    if (arrayList.size() > 0) {
                        this.selectorthread.join(n);
                    }
                    this.selectorthread.interrupt();
                    this.selectorthread.join();
                }
            }
            return;
        }
    }

    public static interface WebSocketServerFactory
    extends WebSocketFactory {
        @Override
        public WebSocketImpl createWebSocket(WebSocketAdapter var1, List<Draft> var2, Socket var3);

        @Override
        public WebSocketImpl createWebSocket(WebSocketAdapter var1, Draft var2, Socket var3);

        public ByteChannel wrapChannel(SocketChannel var1, SelectionKey var2) throws IOException;
    }

    public class WebSocketWorker
    extends Thread {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private BlockingQueue<WebSocketImpl> iqueue = new LinkedBlockingQueue<WebSocketImpl>();

        public WebSocketWorker() {
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

}
