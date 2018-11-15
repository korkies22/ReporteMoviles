/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.Buffer;
import com.jcraft.jsch.Random;

public class Packet {
    private static Random random;
    byte[] ba4 = new byte[4];
    Buffer buffer;

    public Packet(Buffer buffer) {
        this.buffer = buffer;
    }

    static void setRandom(Random random) {
        Packet.random = random;
    }

    Buffer getBuffer() {
        return this.buffer;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void padding(int n) {
        int n2;
        int n3 = this.buffer.index;
        int n4 = n2 = - n3 & n - 1;
        if (n2 < n) {
            n4 = n2 + n;
        }
        n = n3 + n4 - 4;
        this.ba4[0] = (byte)(n >>> 24);
        this.ba4[1] = (byte)(n >>> 16);
        this.ba4[2] = (byte)(n >>> 8);
        this.ba4[3] = (byte)n;
        System.arraycopy(this.ba4, 0, this.buffer.buffer, 0, 4);
        this.buffer.buffer[4] = (byte)n4;
        Random random = Packet.random;
        synchronized (random) {
            Packet.random.fill(this.buffer.buffer, this.buffer.index, n4);
        }
        this.buffer.skip(n4);
    }

    public void reset() {
        this.buffer.index = 5;
    }

    int shift(int n, int n2, int n3) {
        int n4;
        int n5 = n + 5 + 9;
        int n6 = n4 = - n5 & n2 - 1;
        if (n4 < n2) {
            n6 = n4 + n2;
        }
        if (this.buffer.buffer.length < this.buffer.index + (n2 = n6 + n5 + n3 + 32) - 5 - 9 - n) {
            byte[] arrby = new byte[this.buffer.index + n2 - 5 - 9 - n];
            System.arraycopy(this.buffer.buffer, 0, arrby, 0, this.buffer.buffer.length);
            this.buffer.buffer = arrby;
        }
        System.arraycopy(this.buffer.buffer, n5, this.buffer.buffer, n2, this.buffer.index - 5 - 9 - n);
        this.buffer.index = 10;
        this.buffer.putInt(n);
        this.buffer.index = n5;
        return n2;
    }

    void unshift(byte by, int n, int n2, int n3) {
        System.arraycopy(this.buffer.buffer, n2, this.buffer.buffer, 14, n3);
        this.buffer.buffer[5] = by;
        this.buffer.index = 6;
        this.buffer.putInt(n);
        this.buffer.putInt(n3);
        this.buffer.index = n3 + 5 + 9;
    }
}
