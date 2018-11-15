/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.common;

public final class BitSource {
    private int bitOffset;
    private int byteOffset;
    private final byte[] bytes;

    public BitSource(byte[] arrby) {
        this.bytes = arrby;
    }

    public int available() {
        return 8 * (this.bytes.length - this.byteOffset) - this.bitOffset;
    }

    public int getBitOffset() {
        return this.bitOffset;
    }

    public int getByteOffset() {
        return this.byteOffset;
    }

    public int readBits(int n) {
        if (n > 0 && n <= 32 && n <= this.available()) {
            int n2;
            int n3;
            if (this.bitOffset > 0) {
                n3 = 8 - this.bitOffset;
                n2 = n < n3 ? n : n3;
                n3 -= n2;
                n3 = (255 >> 8 - n2 << n3 & this.bytes[this.byteOffset]) >> n3;
                int n4 = n - n2;
                this.bitOffset += n2;
                n = n3;
                n2 = n4;
                if (this.bitOffset == 8) {
                    this.bitOffset = 0;
                    ++this.byteOffset;
                    n = n3;
                    n2 = n4;
                }
            } else {
                n3 = 0;
                n2 = n;
                n = n3;
            }
            n3 = n;
            if (n2 > 0) {
                while (n2 >= 8) {
                    n = n << 8 | this.bytes[this.byteOffset] & 255;
                    ++this.byteOffset;
                    n2 -= 8;
                }
                n3 = n;
                if (n2 > 0) {
                    n3 = 8 - n2;
                    n3 = n << n2 | (255 >> n3 << n3 & this.bytes[this.byteOffset]) >> n3;
                    this.bitOffset += n2;
                }
            }
            return n3;
        }
        throw new IllegalArgumentException(String.valueOf(n));
    }
}
