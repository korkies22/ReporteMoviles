/*
 * Decompiled with CFR 0_134.
 */
package com.jcraft.jsch;

import com.jcraft.jsch.JSchException;

public class Buffer {
    byte[] buffer;
    int index;
    int s;
    final byte[] tmp = new byte[4];

    public Buffer() {
        this(20480);
    }

    public Buffer(int n) {
        this.buffer = new byte[n];
        this.index = 0;
        this.s = 0;
    }

    public Buffer(byte[] arrby) {
        this.buffer = arrby;
        this.index = 0;
        this.s = 0;
    }

    static Buffer fromBytes(byte[][] arrby) {
        int n;
        int n2 = 0;
        int n3 = arrby.length * 4;
        for (n = 0; n < arrby.length; ++n) {
            n3 += arrby[n].length;
        }
        Buffer buffer = new Buffer(n3);
        for (n = n2; n < arrby.length; ++n) {
            buffer.putString(arrby[n]);
        }
        return buffer;
    }

    void checkFreeSize(int n) {
        int n2 = this.index + n + 128;
        if (this.buffer.length < n2) {
            int n3;
            n = n3 = this.buffer.length * 2;
            if (n3 < n2) {
                n = n2;
            }
            byte[] arrby = new byte[n];
            System.arraycopy(this.buffer, 0, arrby, 0, this.index);
            this.buffer = arrby;
        }
    }

    public int getByte() {
        byte[] arrby = this.buffer;
        int n = this.s;
        this.s = n + 1;
        return arrby[n] & 255;
    }

    public int getByte(int n) {
        int n2 = this.s;
        this.s += n;
        return n2;
    }

    public void getByte(byte[] arrby) {
        this.getByte(arrby, 0, arrby.length);
    }

    void getByte(byte[] arrby, int n, int n2) {
        System.arraycopy(this.buffer, this.s, arrby, n, n2);
        this.s += n2;
    }

    byte[][] getBytes(int n, String string) throws JSchException {
        byte[][] arrarrby = new byte[n][];
        for (int i = 0; i < n; ++i) {
            int n2 = this.getInt();
            if (this.getLength() < n2) {
                throw new JSchException(string);
            }
            arrarrby[i] = new byte[n2];
            this.getByte(arrarrby[i]);
        }
        return arrarrby;
    }

    byte getCommand() {
        return this.buffer[5];
    }

    public int getInt() {
        return this.getShort() << 16 & -65536 | this.getShort() & 65535;
    }

    public int getLength() {
        return this.index - this.s;
    }

    public long getLong() {
        return ((long)this.getInt() & 0xFFFFFFFFL) << 32 | (long)this.getInt() & 0xFFFFFFFFL;
    }

    public byte[] getMPInt() {
        int n;
        block3 : {
            block2 : {
                int n2 = this.getInt();
                if (n2 < 0) break block2;
                n = n2;
                if (n2 <= 8192) break block3;
            }
            n = 8192;
        }
        byte[] arrby = new byte[n];
        this.getByte(arrby, 0, n);
        return arrby;
    }

    public byte[] getMPIntBits() {
        int n = (this.getInt() + 7) / 8;
        byte[] arrby = new byte[n];
        this.getByte(arrby, 0, n);
        byte[] arrby2 = arrby;
        if ((arrby[0] & 128) != 0) {
            arrby2 = new byte[arrby.length + 1];
            arrby2[0] = 0;
            System.arraycopy(arrby, 0, arrby2, 1, arrby.length);
        }
        return arrby2;
    }

    public int getOffSet() {
        return this.s;
    }

    int getShort() {
        return this.getByte() << 8 & 65280 | this.getByte() & 255;
    }

    public byte[] getString() {
        int n;
        block3 : {
            block2 : {
                int n2 = this.getInt();
                if (n2 < 0) break block2;
                n = n2;
                if (n2 <= 262144) break block3;
            }
            n = 262144;
        }
        byte[] arrby = new byte[n];
        this.getByte(arrby, 0, n);
        return arrby;
    }

    byte[] getString(int[] arrn, int[] arrn2) {
        int n = this.getInt();
        arrn[0] = this.getByte(n);
        arrn2[0] = n;
        return this.buffer;
    }

    public long getUInt() {
        return ((long)this.getByte() << 8 & 65280L | (long)(this.getByte() & 255)) << 16 & -65536L | ((long)this.getByte() << 8 & 65280L | (long)(this.getByte() & 255)) & 65535L;
    }

    public void putByte(byte by) {
        byte[] arrby = this.buffer;
        int n = this.index;
        this.index = n + 1;
        arrby[n] = by;
    }

    public void putByte(byte[] arrby) {
        this.putByte(arrby, 0, arrby.length);
    }

    public void putByte(byte[] arrby, int n, int n2) {
        System.arraycopy(arrby, n, this.buffer, this.index, n2);
        this.index += n2;
    }

    public void putInt(int n) {
        this.tmp[0] = (byte)(n >>> 24);
        this.tmp[1] = (byte)(n >>> 16);
        this.tmp[2] = (byte)(n >>> 8);
        this.tmp[3] = (byte)n;
        System.arraycopy(this.tmp, 0, this.buffer, this.index, 4);
        this.index += 4;
    }

    public void putLong(long l) {
        this.tmp[0] = (byte)(l >>> 56);
        this.tmp[1] = (byte)(l >>> 48);
        this.tmp[2] = (byte)(l >>> 40);
        this.tmp[3] = (byte)(l >>> 32);
        System.arraycopy(this.tmp, 0, this.buffer, this.index, 4);
        this.tmp[0] = (byte)(l >>> 24);
        this.tmp[1] = (byte)(l >>> 16);
        this.tmp[2] = (byte)(l >>> 8);
        this.tmp[3] = (byte)l;
        System.arraycopy(this.tmp, 0, this.buffer, this.index + 4, 4);
        this.index += 8;
    }

    public void putMPInt(byte[] arrby) {
        int n = arrby.length;
        if ((arrby[0] & 128) != 0) {
            this.putInt(n + 1);
            this.putByte((byte)0);
        } else {
            this.putInt(n);
        }
        this.putByte(arrby);
    }

    void putPad(int n) {
        while (n > 0) {
            byte[] arrby = this.buffer;
            int n2 = this.index;
            this.index = n2 + 1;
            arrby[n2] = 0;
            --n;
        }
    }

    public void putString(byte[] arrby) {
        this.putString(arrby, 0, arrby.length);
    }

    public void putString(byte[] arrby, int n, int n2) {
        this.putInt(n2);
        this.putByte(arrby, n, n2);
    }

    public void reset() {
        this.index = 0;
        this.s = 0;
    }

    void rewind() {
        this.s = 0;
    }

    public void setOffSet(int n) {
        this.s = n;
    }

    public void shift() {
        if (this.s == 0) {
            return;
        }
        System.arraycopy(this.buffer, this.s, this.buffer, 0, this.index - this.s);
        this.index -= this.s;
        this.s = 0;
    }

    void skip(int n) {
        this.index += n;
    }
}
