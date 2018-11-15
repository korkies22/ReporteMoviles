/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.Packet;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.OutputStream;

class Channel
extends OutputStream {
    byte[] b = new byte[1];
    private Buffer buffer = null;
    private boolean closed = false;
    private int dataLen = 0;
    private Packet packet = null;
    final /* synthetic */ com.jcraft.jsch.Channel val$channel;

    Channel(com.jcraft.jsch.Channel channel2) {
        this.val$channel = channel2;
    }

    private void init() throws IOException {
        synchronized (this) {
            this.buffer = new Buffer(Channel.this.rmpsize);
            this.packet = new Packet(this.buffer);
            if (this.buffer.buffer.length - 14 - 128 <= 0) {
                this.buffer = null;
                this.packet = null;
                throw new IOException("failed to initialize the channel.");
            }
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void close() throws IOException {
        if (this.packet == null) {
            this.init();
        }
        if (this.closed) {
            return;
        }
        if (this.dataLen > 0) {
            this.flush();
        }
        this.val$channel.eof();
        this.closed = true;
        return;
        catch (IOException iOException) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void flush() throws IOException {
        block7 : {
            int n;
            if (this.closed) {
                throw new IOException("Already closed");
            }
            if (this.dataLen == 0) {
                return;
            }
            this.packet.reset();
            this.buffer.putByte((byte)94);
            this.buffer.putInt(Channel.this.recipient);
            this.buffer.putInt(this.dataLen);
            this.buffer.skip(this.dataLen);
            try {
                n = this.dataLen;
                this.dataLen = 0;
                com.jcraft.jsch.Channel channel = this.val$channel;
                // MONITORENTER : channel
                if (this.val$channel.close) break block7;
            }
            catch (Exception exception) {
                this.close();
                throw new IOException(exception.toString());
            }
            Channel.this.getSession().write(this.packet, this.val$channel, n);
        }
        // MONITOREXIT : channel
    }

    @Override
    public void write(int n) throws IOException {
        this.b[0] = (byte)n;
        this.write(this.b, 0, 1);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        if (this.packet == null) {
            this.init();
        }
        if (this.closed) {
            throw new IOException("Already closed");
        }
        byte[] arrby2 = this.buffer.buffer;
        int n3 = arrby2.length;
        while (n2 > 0) {
            int n4 = n2 > n3 - (this.dataLen + 14) - 128 ? n3 - (this.dataLen + 14) - 128 : n2;
            if (n4 <= 0) {
                this.flush();
                continue;
            }
            System.arraycopy(arrby, n, arrby2, 14 + this.dataLen, n4);
            this.dataLen += n4;
            n += n4;
            n2 -= n4;
        }
    }
}
