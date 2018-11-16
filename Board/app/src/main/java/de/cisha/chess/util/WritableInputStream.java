// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.util;

import java.io.IOException;
import java.io.InputStream;

public class WritableInputStream extends InputStream
{
    String _buffer;
    Object _lock;
    
    public WritableInputStream() {
        this._buffer = "";
        this._lock = new Object();
    }
    
    public static int unsignedByteToInt(final byte b) {
        return b & 0xFF;
    }
    
    private void waitForInput() {
        while (true) {
            if (!this._buffer.isEmpty()) {
                return;
            }
            try {
                Thread.sleep(10L);
                continue;
            }
            catch (InterruptedException ex) {}
        }
    }
    
    public void addStringToBuffer(final String s) {
        synchronized (this._lock) {
            final StringBuilder sb = new StringBuilder();
            sb.append(this._buffer);
            sb.append(s);
            this._buffer = sb.toString();
        }
    }
    
    @Override
    public int available() throws IOException {
        return this._buffer.length();
    }
    
    @Override
    public int read() throws IOException {
        this.waitForInput();
        if (!this._buffer.isEmpty()) {
            synchronized (this._lock) {
                final byte b = this._buffer.substring(0, 1).getBytes()[0];
                this._buffer = this._buffer.substring(1);
                // monitorexit(this._lock)
                return unsignedByteToInt(b);
            }
        }
        return -1;
    }
    
    @Override
    public int read(final byte[] array, final int n, int min) throws IOException {
        this.waitForInput();
        synchronized (this._lock) {
            min = Math.min(this._buffer.length(), min);
            final byte[] bytes = this._buffer.getBytes();
            if (min > 0) {
                System.arraycopy(bytes, 0, array, n, min);
                this._buffer = this._buffer.substring(min);
            }
            return min;
        }
    }
}
