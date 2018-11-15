/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SocketChannel;
import javax.net.ssl.SSLException;
import org.java_websocket.WrappedByteChannel;

public class AbstractWrappedByteChannel
implements WrappedByteChannel {
    private final ByteChannel channel;

    public AbstractWrappedByteChannel(ByteChannel byteChannel) {
        this.channel = byteChannel;
    }

    public AbstractWrappedByteChannel(WrappedByteChannel wrappedByteChannel) {
        this.channel = wrappedByteChannel;
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
    }

    @Override
    public boolean isBlocking() {
        if (this.channel instanceof SocketChannel) {
            return ((SocketChannel)this.channel).isBlocking();
        }
        if (this.channel instanceof WrappedByteChannel) {
            return ((WrappedByteChannel)this.channel).isBlocking();
        }
        return false;
    }

    @Override
    public boolean isNeedRead() {
        if (this.channel instanceof WrappedByteChannel) {
            return ((WrappedByteChannel)this.channel).isNeedRead();
        }
        return false;
    }

    @Override
    public boolean isNeedWrite() {
        if (this.channel instanceof WrappedByteChannel) {
            return ((WrappedByteChannel)this.channel).isNeedWrite();
        }
        return false;
    }

    @Override
    public boolean isOpen() {
        return this.channel.isOpen();
    }

    @Override
    public int read(ByteBuffer byteBuffer) throws IOException {
        return this.channel.read(byteBuffer);
    }

    @Override
    public int readMore(ByteBuffer byteBuffer) throws SSLException {
        if (this.channel instanceof WrappedByteChannel) {
            return ((WrappedByteChannel)this.channel).readMore(byteBuffer);
        }
        return 0;
    }

    @Override
    public int write(ByteBuffer byteBuffer) throws IOException {
        return this.channel.write(byteBuffer);
    }

    @Override
    public void writeMore() throws IOException {
        if (this.channel instanceof WrappedByteChannel) {
            ((WrappedByteChannel)this.channel).writeMore();
        }
    }
}
