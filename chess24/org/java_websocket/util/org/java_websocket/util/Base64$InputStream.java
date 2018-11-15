/*
 * Decompiled with CFR 0_134.
 */
package org.java_websocket.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.java_websocket.util.Base64;

public static class Base64.InputStream
extends FilterInputStream {
    private boolean breakLines;
    private byte[] buffer;
    private int bufferLength;
    private byte[] decodabet;
    private boolean encode;
    private int lineLength;
    private int numSigBytes;
    private int options;
    private int position;

    public Base64.InputStream(InputStream inputStream) {
        this(inputStream, 0);
    }

    public Base64.InputStream(InputStream inputStream, int n) {
        super(inputStream);
        this.options = n;
        boolean bl = true;
        boolean bl2 = (n & 8) > 0;
        this.breakLines = bl2;
        bl2 = (n & 1) > 0 ? bl : false;
        this.encode = bl2;
        int n2 = this.encode ? 4 : 3;
        this.bufferLength = n2;
        this.buffer = new byte[this.bufferLength];
        this.position = -1;
        this.lineLength = 0;
        this.decodabet = Base64.getDecodabet(n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int read() throws IOException {
        int n;
        byte[] arrby;
        if (this.position < 0) {
            if (this.encode) {
                int n2;
                arrby = new byte[3];
                int n3 = n = 0;
                while (n < 3 && (n2 = this.in.read()) >= 0) {
                    arrby[n] = (byte)n2;
                    ++n3;
                    ++n;
                }
                if (n3 <= 0) return -1;
                Base64.encode3to4(arrby, 0, n3, this.buffer, 0, this.options);
                this.position = 0;
                this.numSigBytes = 4;
            } else {
                arrby = new byte[4];
                for (n = 0; n < 4; ++n) {
                    int n4;
                    while ((n4 = this.in.read()) >= 0 && this.decodabet[n4 & 127] <= -5) {
                    }
                    if (n4 < 0) break;
                    arrby[n] = (byte)n4;
                }
                if (n == 4) {
                    this.numSigBytes = Base64.decode4to3(arrby, 0, this.buffer, 0, this.options);
                    this.position = 0;
                } else {
                    if (n != 0) throw new IOException("Improperly padded Base64 input.");
                    return -1;
                }
            }
        }
        if (this.position < 0) throw new IOException("Error in Base64 code reading stream.");
        if (this.position >= this.numSigBytes) {
            return -1;
        }
        if (this.encode && this.breakLines && this.lineLength >= 76) {
            this.lineLength = 0;
            return 10;
        }
        ++this.lineLength;
        arrby = this.buffer;
        n = this.position;
        this.position = n + 1;
        n = arrby[n];
        if (this.position < this.bufferLength) return n & 255;
        this.position = -1;
        return n & 255;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        int n3;
        for (n3 = 0; n3 < n2; ++n3) {
            int n4 = this.read();
            if (n4 >= 0) {
                arrby[n + n3] = (byte)n4;
                continue;
            }
            if (n3 != 0) break;
            return -1;
        }
        return n3;
    }
}
