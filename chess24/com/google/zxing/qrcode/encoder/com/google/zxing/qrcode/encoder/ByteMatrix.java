/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.encoder;

import java.lang.reflect.Array;

public final class ByteMatrix {
    private final byte[][] bytes;
    private final int height;
    private final int width;

    public ByteMatrix(int n, int n2) {
        this.bytes = (byte[][])Array.newInstance(Byte.TYPE, n2, n);
        this.width = n;
        this.height = n2;
    }

    public void clear(byte by) {
        for (int i = 0; i < this.height; ++i) {
            for (int j = 0; j < this.width; ++j) {
                this.bytes[i][j] = by;
            }
        }
    }

    public byte get(int n, int n2) {
        return this.bytes[n2][n];
    }

    public byte[][] getArray() {
        return this.bytes;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public void set(int n, int n2, byte by) {
        this.bytes[n2][n] = by;
    }

    public void set(int n, int n2, int n3) {
        this.bytes[n2][n] = (byte)n3;
    }

    public void set(int n, int n2, boolean bl) {
        this.bytes[n2][n] = (byte)(bl ? 1 : 0);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.width * 2 * this.height + 2);
        for (int i = 0; i < this.height; ++i) {
            block5 : for (int j = 0; j < this.width; ++j) {
                switch (this.bytes[i][j]) {
                    default: {
                        stringBuilder.append("  ");
                        continue block5;
                    }
                    case 1: {
                        stringBuilder.append(" 1");
                        continue block5;
                    }
                    case 0: {
                        stringBuilder.append(" 0");
                    }
                }
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
