/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.java_websocket.util.Base64;

public static class Base64.OutputStream
extends FilterOutputStream {
    private byte[] b4;
    private boolean breakLines;
    private byte[] buffer;
    private int bufferLength;
    private byte[] decodabet;
    private boolean encode;
    private int lineLength;
    private int options;
    private int position;
    private boolean suspendEncoding;

    public Base64.OutputStream(OutputStream outputStream) {
        this(outputStream, 1);
    }

    public Base64.OutputStream(OutputStream outputStream, int n) {
        super(outputStream);
        boolean bl = true;
        boolean bl2 = (n & 8) != 0;
        this.breakLines = bl2;
        bl2 = (n & 1) != 0 ? bl : false;
        this.encode = bl2;
        int n2 = this.encode ? 3 : 4;
        this.bufferLength = n2;
        this.buffer = new byte[this.bufferLength];
        this.position = 0;
        this.lineLength = 0;
        this.suspendEncoding = false;
        this.b4 = new byte[4];
        this.options = n;
        this.decodabet = Base64.getDecodabet(n);
    }

    @Override
    public void close() throws IOException {
        this.flushBase64();
        super.close();
        this.buffer = null;
        this.out = null;
    }

    public void flushBase64() throws IOException {
        if (this.position > 0) {
            if (this.encode) {
                this.out.write(Base64.encode3to4(this.b4, this.buffer, this.position, this.options));
                this.position = 0;
                return;
            }
            throw new IOException("Base64 input not properly padded.");
        }
    }

    public void resumeEncoding() {
        this.suspendEncoding = false;
    }

    public void suspendEncoding() throws IOException {
        this.flushBase64();
        this.suspendEncoding = true;
    }

    @Override
    public void write(int n) throws IOException {
        if (this.suspendEncoding) {
            this.out.write(n);
            return;
        }
        if (this.encode) {
            byte[] arrby = this.buffer;
            int n2 = this.position;
            this.position = n2 + 1;
            arrby[n2] = (byte)n;
            if (this.position >= this.bufferLength) {
                this.out.write(Base64.encode3to4(this.b4, this.buffer, this.bufferLength, this.options));
                this.lineLength += 4;
                if (this.breakLines && this.lineLength >= 76) {
                    this.out.write(10);
                    this.lineLength = 0;
                }
                this.position = 0;
                return;
            }
        } else {
            byte[] arrby = this.decodabet;
            int n3 = n & 127;
            if (arrby[n3] > -5) {
                arrby = this.buffer;
                n3 = this.position;
                this.position = n3 + 1;
                arrby[n3] = (byte)n;
                if (this.position >= this.bufferLength) {
                    n = Base64.decode4to3(this.buffer, 0, this.b4, 0, this.options);
                    this.out.write(this.b4, 0, n);
                    this.position = 0;
                    return;
                }
            } else if (this.decodabet[n3] != -5) {
                throw new IOException("Invalid character in Base64 data.");
            }
        }
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        if (this.suspendEncoding) {
            this.out.write(arrby, n, n2);
            return;
        }
        for (int i = 0; i < n2; ++i) {
            this.write(arrby[n + i]);
        }
    }
}
