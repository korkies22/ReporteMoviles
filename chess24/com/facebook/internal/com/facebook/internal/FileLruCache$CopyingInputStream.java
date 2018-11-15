/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.FileLruCache;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

private static final class FileLruCache.CopyingInputStream
extends InputStream {
    final InputStream input;
    final OutputStream output;

    FileLruCache.CopyingInputStream(InputStream inputStream, OutputStream outputStream) {
        this.input = inputStream;
        this.output = outputStream;
    }

    @Override
    public int available() throws IOException {
        return this.input.available();
    }

    @Override
    public void close() throws IOException {
        try {
            this.input.close();
            return;
        }
        finally {
            this.output.close();
        }
    }

    @Override
    public void mark(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read() throws IOException {
        int n = this.input.read();
        if (n >= 0) {
            this.output.write(n);
        }
        return n;
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        int n = this.input.read(arrby);
        if (n > 0) {
            this.output.write(arrby, 0, n);
        }
        return n;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if ((n2 = this.input.read(arrby, n, n2)) > 0) {
            this.output.write(arrby, n, n2);
        }
        return n2;
    }

    @Override
    public void reset() {
        synchronized (this) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public long skip(long l) throws IOException {
        int n;
        long l2;
        byte[] arrby = new byte[1024];
        for (l2 = 0L; l2 < l; l2 += (long)n) {
            n = this.read(arrby, 0, (int)Math.min(l - l2, (long)arrby.length));
            if (n >= 0) continue;
            return l2;
        }
        return l2;
    }
}
