/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import org.java_websocket.AbstractWrappedByteChannel;

public abstract class AbstractClientProxyChannel
extends AbstractWrappedByteChannel {
    protected final ByteBuffer proxyHandshake;

    public AbstractClientProxyChannel(ByteChannel byteChannel) {
        super(byteChannel);
        try {
            this.proxyHandshake = ByteBuffer.wrap(this.buildHandShake().getBytes("ASCII"));
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }

    public abstract String buildHandShake();

    @Override
    public int write(ByteBuffer byteBuffer) throws IOException {
        if (!this.proxyHandshake.hasRemaining()) {
            return super.write(byteBuffer);
        }
        return super.write(this.proxyHandshake);
    }
}
