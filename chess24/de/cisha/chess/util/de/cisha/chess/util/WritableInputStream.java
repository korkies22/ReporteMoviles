/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.util;

import java.io.IOException;
import java.io.InputStream;

public class WritableInputStream
extends InputStream {
    String _buffer = "";
    Object _lock = new Object();

    public static int unsignedByteToInt(byte by) {
        return by & 255;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void waitForInput() {
        while (this._buffer.isEmpty()) {
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException interruptedException) {
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addStringToBuffer(String string) {
        Object object = this._lock;
        synchronized (object) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this._buffer);
            stringBuilder.append(string);
            this._buffer = stringBuilder.toString();
            return;
        }
    }

    @Override
    public int available() throws IOException {
        return this._buffer.length();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read() throws IOException {
        this.waitForInput();
        if (!this._buffer.isEmpty()) {
            Object object = this._lock;
            synchronized (object) {
                byte by = this._buffer.substring(0, 1).getBytes()[0];
                this._buffer = this._buffer.substring(1);
                return WritableInputStream.unsignedByteToInt(by);
            }
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        this.waitForInput();
        Object object = this._lock;
        synchronized (object) {
            n2 = Math.min(this._buffer.length(), n2);
            byte[] arrby2 = this._buffer.getBytes();
            if (n2 > 0) {
                System.arraycopy(arrby2, 0, arrby, n, n2);
                this._buffer = this._buffer.substring(n2);
            }
            return n2;
        }
    }
}
