/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import org.java_websocket.WrappedByteChannel;

public class SSLSocketChannel2
implements ByteChannel,
WrappedByteChannel {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected static ByteBuffer emptybuffer = ByteBuffer.allocate(0);
    protected int bufferallocations = 0;
    protected ExecutorService exec;
    protected ByteBuffer inCrypt;
    protected ByteBuffer inData;
    protected ByteBuffer outCrypt;
    protected SSLEngineResult readEngineResult;
    protected SelectionKey selectionKey;
    protected SocketChannel socketChannel;
    protected SSLEngine sslEngine;
    protected List<Future<?>> tasks;
    protected SSLEngineResult writeEngineResult;

    public SSLSocketChannel2(SocketChannel object, SSLEngine sSLEngine, ExecutorService executorService, SelectionKey selectionKey) throws IOException {
        if (object != null && sSLEngine != null && executorService != null) {
            this.socketChannel = object;
            this.sslEngine = sSLEngine;
            this.exec = executorService;
            this.writeEngineResult = object = new SSLEngineResult(SSLEngineResult.Status.BUFFER_UNDERFLOW, sSLEngine.getHandshakeStatus(), 0, 0);
            this.readEngineResult = object;
            this.tasks = new ArrayList(3);
            if (selectionKey != null) {
                selectionKey.interestOps(selectionKey.interestOps() | 4);
                this.selectionKey = selectionKey;
            }
            this.createBuffers(sSLEngine.getSession());
            this.socketChannel.write(this.wrap(emptybuffer));
            this.processHandshake();
            return;
        }
        throw new IllegalArgumentException("parameter must not be null");
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void consumeFutureUninterruptible(Future<?> future) {
        boolean bl = false;
        do {
            try {
                future.get();
                if (!bl) return;
                Thread.currentThread().interrupt();
                return;
            }
            catch (ExecutionException executionException) {
                throw new RuntimeException(executionException);
            }
            catch (InterruptedException interruptedException) {}
            bl = true;
        } while (true);
    }

    private boolean isHandShakeComplete() {
        SSLEngineResult.HandshakeStatus handshakeStatus = this.sslEngine.getHandshakeStatus();
        if (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED && handshakeStatus != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
            return false;
        }
        return true;
    }

    private void processHandshake() throws IOException {
        synchronized (this) {
            Object object;
            Object object2;
            block14 : {
                object = this.sslEngine.getHandshakeStatus();
                object2 = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
                if (object != object2) break block14;
                return;
            }
            if (!this.tasks.isEmpty()) {
                object = this.tasks.iterator();
                while (object.hasNext()) {
                    object2 = (Future)object.next();
                    if (object2.isDone()) {
                        object.remove();
                        continue;
                    }
                    if (this.isBlocking()) {
                        this.consumeFutureUninterruptible((Future<?>)object2);
                    }
                    return;
                }
            }
            if (this.sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
                if (!this.isBlocking() || this.readEngineResult.getStatus() == SSLEngineResult.Status.BUFFER_UNDERFLOW) {
                    this.inCrypt.compact();
                    if (this.socketChannel.read(this.inCrypt) == -1) {
                        throw new IOException("connection closed unexpectedly by peer");
                    }
                    this.inCrypt.flip();
                }
                this.inData.compact();
                this.unwrap();
                if (this.readEngineResult.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
                    this.createBuffers(this.sslEngine.getSession());
                    return;
                }
            }
            this.consumeDelegatedTasks();
            if (this.tasks.isEmpty() || this.sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
                this.socketChannel.write(this.wrap(emptybuffer));
                if (this.writeEngineResult.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
                    this.createBuffers(this.sslEngine.getSession());
                    return;
                }
            }
            this.bufferallocations = 1;
            return;
        }
    }

    private int readRemaining(ByteBuffer byteBuffer) throws SSLException {
        if (this.inData.hasRemaining()) {
            return this.transfereTo(this.inData, byteBuffer);
        }
        if (!this.inData.hasRemaining()) {
            this.inData.clear();
        }
        if (this.inCrypt.hasRemaining()) {
            this.unwrap();
            int n = this.transfereTo(this.inData, byteBuffer);
            if (n > 0) {
                return n;
            }
        }
        return 0;
    }

    private int transfereTo(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        int n;
        int n2 = byteBuffer.remaining();
        if (n2 > (n = byteBuffer2.remaining())) {
            n = Math.min(n2, n);
            for (n2 = 0; n2 < n; ++n2) {
                byteBuffer2.put(byteBuffer.get());
            }
            return n;
        }
        byteBuffer2.put(byteBuffer);
        return n2;
    }

    private ByteBuffer unwrap() throws SSLException {
        synchronized (this) {
            int n;
            do {
                n = this.inData.remaining();
                this.readEngineResult = this.sslEngine.unwrap(this.inCrypt, this.inData);
            } while (this.readEngineResult.getStatus() == SSLEngineResult.Status.OK && (n != this.inData.remaining() || this.sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_UNWRAP));
            this.inData.flip();
            ByteBuffer byteBuffer = this.inData;
            return byteBuffer;
        }
    }

    private ByteBuffer wrap(ByteBuffer byteBuffer) throws SSLException {
        synchronized (this) {
            this.outCrypt.compact();
            this.writeEngineResult = this.sslEngine.wrap(byteBuffer, this.outCrypt);
            this.outCrypt.flip();
            byteBuffer = this.outCrypt;
            return byteBuffer;
        }
    }

    @Override
    public void close() throws IOException {
        this.sslEngine.closeOutbound();
        this.sslEngine.getSession().invalidate();
        if (this.socketChannel.isOpen()) {
            this.socketChannel.write(this.wrap(emptybuffer));
        }
        this.socketChannel.close();
        this.exec.shutdownNow();
    }

    public SelectableChannel configureBlocking(boolean bl) throws IOException {
        return this.socketChannel.configureBlocking(bl);
    }

    public boolean connect(SocketAddress socketAddress) throws IOException {
        return this.socketChannel.connect(socketAddress);
    }

    protected void consumeDelegatedTasks() {
        Runnable runnable;
        while ((runnable = this.sslEngine.getDelegatedTask()) != null) {
            this.tasks.add(this.exec.submit(runnable));
        }
    }

    protected void createBuffers(SSLSession sSLSession) {
        int n = sSLSession.getApplicationBufferSize();
        int n2 = sSLSession.getPacketBufferSize();
        if (this.inData == null) {
            this.inData = ByteBuffer.allocate(n);
            this.outCrypt = ByteBuffer.allocate(n2);
            this.inCrypt = ByteBuffer.allocate(n2);
        } else {
            if (this.inData.capacity() != n) {
                this.inData = ByteBuffer.allocate(n);
            }
            if (this.outCrypt.capacity() != n2) {
                this.outCrypt = ByteBuffer.allocate(n2);
            }
            if (this.inCrypt.capacity() != n2) {
                this.inCrypt = ByteBuffer.allocate(n2);
            }
        }
        this.inData.rewind();
        this.inData.flip();
        this.inCrypt.rewind();
        this.inCrypt.flip();
        this.outCrypt.rewind();
        this.outCrypt.flip();
        ++this.bufferallocations;
    }

    public boolean finishConnect() throws IOException {
        return this.socketChannel.finishConnect();
    }

    @Override
    public boolean isBlocking() {
        return this.socketChannel.isBlocking();
    }

    public boolean isConnected() {
        return this.socketChannel.isConnected();
    }

    public boolean isInboundDone() {
        return this.sslEngine.isInboundDone();
    }

    @Override
    public boolean isNeedRead() {
        if (!(this.inData.hasRemaining() || this.inCrypt.hasRemaining() && this.readEngineResult.getStatus() != SSLEngineResult.Status.BUFFER_UNDERFLOW && this.readEngineResult.getStatus() != SSLEngineResult.Status.CLOSED)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isNeedWrite() {
        if (!this.outCrypt.hasRemaining() && this.isHandShakeComplete()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isOpen() {
        return this.socketChannel.isOpen();
    }

    @Override
    public int read(ByteBuffer byteBuffer) throws IOException {
        int n;
        if (!byteBuffer.hasRemaining()) {
            return 0;
        }
        if (!this.isHandShakeComplete()) {
            if (this.isBlocking()) {
                while (!this.isHandShakeComplete()) {
                    this.processHandshake();
                }
            } else {
                this.processHandshake();
                if (!this.isHandShakeComplete()) {
                    return 0;
                }
            }
        }
        if (this.bufferallocations <= 1) {
            this.createBuffers(this.sslEngine.getSession());
        }
        if ((n = this.readRemaining(byteBuffer)) != 0) {
            return n;
        }
        this.inData.clear();
        if (!this.inCrypt.hasRemaining()) {
            this.inCrypt.clear();
        } else {
            this.inCrypt.compact();
        }
        if ((this.isBlocking() || this.readEngineResult.getStatus() == SSLEngineResult.Status.BUFFER_UNDERFLOW) && this.socketChannel.read(this.inCrypt) == -1) {
            return -1;
        }
        this.inCrypt.flip();
        this.unwrap();
        n = this.transfereTo(this.inData, byteBuffer);
        if (n == 0 && this.isBlocking()) {
            return this.read(byteBuffer);
        }
        return n;
    }

    @Override
    public int readMore(ByteBuffer byteBuffer) throws SSLException {
        return this.readRemaining(byteBuffer);
    }

    public Socket socket() {
        return this.socketChannel.socket();
    }

    @Override
    public int write(ByteBuffer byteBuffer) throws IOException {
        if (!this.isHandShakeComplete()) {
            this.processHandshake();
            return 0;
        }
        if (this.bufferallocations <= 1) {
            this.createBuffers(this.sslEngine.getSession());
        }
        return this.socketChannel.write(this.wrap(byteBuffer));
    }

    @Override
    public void writeMore() throws IOException {
        this.write(this.outCrypt);
    }
}
