/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Channel;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

class Channel.MyPipedInputStream
extends PipedInputStream {
    private int BUFFER_SIZE;
    private int max_buffer_size;

    Channel.MyPipedInputStream() throws IOException {
        this.max_buffer_size = this.BUFFER_SIZE = 1024;
    }

    Channel.MyPipedInputStream(int n) throws IOException {
        this.max_buffer_size = this.BUFFER_SIZE = 1024;
        this.buffer = new byte[n];
        this.BUFFER_SIZE = n;
        this.max_buffer_size = n;
    }

    Channel.MyPipedInputStream(int n, int n2) throws IOException {
        this(n);
        this.max_buffer_size = n2;
    }

    Channel.MyPipedInputStream(PipedOutputStream pipedOutputStream) throws IOException {
        super(pipedOutputStream);
        this.max_buffer_size = this.BUFFER_SIZE = 1024;
    }

    Channel.MyPipedInputStream(PipedOutputStream pipedOutputStream, int n) throws IOException {
        super(pipedOutputStream);
        this.max_buffer_size = this.BUFFER_SIZE = 1024;
        this.buffer = new byte[n];
        this.BUFFER_SIZE = n;
    }

    private int freeSpace() {
        if (this.out < this.in) {
            return this.buffer.length - this.in;
        }
        if (this.in < this.out) {
            if (this.in == -1) {
                return this.buffer.length;
            }
            return this.out - this.in;
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void checkSpace(int n) throws IOException {
        synchronized (this) {
            int n2 = this.freeSpace();
            if (n2 >= n) {
                if (this.buffer.length == n2 && n2 > this.BUFFER_SIZE) {
                    n = n2 /= 2;
                    if (n2 < this.BUFFER_SIZE) {
                        n = this.BUFFER_SIZE;
                    }
                    this.buffer = new byte[n];
                }
            } else {
                int n3 = this.buffer.length - n2;
                n2 = this.buffer.length;
                while (n2 - n3 < n) {
                    n2 *= 2;
                }
                int n4 = n2;
                if (n2 > this.max_buffer_size) {
                    n4 = this.max_buffer_size;
                }
                if (n4 - n3 < n) {
                    return;
                }
                byte[] arrby = new byte[n4];
                if (this.out < this.in) {
                    System.arraycopy(this.buffer, 0, arrby, 0, this.buffer.length);
                } else if (this.in < this.out) {
                    if (this.in != -1) {
                        System.arraycopy(this.buffer, 0, arrby, 0, this.in);
                        System.arraycopy(this.buffer, this.out, arrby, arrby.length - (this.buffer.length - this.out), this.buffer.length - this.out);
                        this.out = arrby.length - (this.buffer.length - this.out);
                    }
                } else if (this.in == this.out) {
                    System.arraycopy(this.buffer, 0, arrby, 0, this.buffer.length);
                    this.in = this.buffer.length;
                }
                this.buffer = arrby;
            }
            return;
        }
    }

    public void updateReadSide() throws IOException {
        synchronized (this) {
            int n;
            block5 : {
                n = this.available();
                if (n == 0) break block5;
                return;
            }
            this.in = 0;
            this.out = 0;
            byte[] arrby = this.buffer;
            n = this.in;
            this.in = n + 1;
            arrby[n] = 0;
            this.read();
            return;
        }
    }
}
