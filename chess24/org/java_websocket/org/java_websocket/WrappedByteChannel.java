/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import javax.net.ssl.SSLException;

public interface WrappedByteChannel
extends ByteChannel {
    public boolean isBlocking();

    public boolean isNeedRead();

    public boolean isNeedWrite();

    public int readMore(ByteBuffer var1) throws SSLException;

    public void writeMore() throws IOException;
}
