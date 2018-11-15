/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.FileLruCache;
import java.io.IOException;
import java.io.OutputStream;

private static class FileLruCache.CloseCallbackOutputStream
extends OutputStream {
    final FileLruCache.StreamCloseCallback callback;
    final OutputStream innerStream;

    FileLruCache.CloseCallbackOutputStream(OutputStream outputStream, FileLruCache.StreamCloseCallback streamCloseCallback) {
        this.innerStream = outputStream;
        this.callback = streamCloseCallback;
    }

    @Override
    public void close() throws IOException {
        try {
            this.innerStream.close();
            return;
        }
        finally {
            this.callback.onClose();
        }
    }

    @Override
    public void flush() throws IOException {
        this.innerStream.flush();
    }

    @Override
    public void write(int n) throws IOException {
        this.innerStream.write(n);
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.innerStream.write(arrby);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.innerStream.write(arrby, n, n2);
    }
}
