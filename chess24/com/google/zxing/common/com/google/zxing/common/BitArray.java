/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.common;

import java.util.Arrays;

public final class BitArray
implements Cloneable {
    private int[] bits;
    private int size;

    public BitArray() {
        this.size = 0;
        this.bits = new int[1];
    }

    public BitArray(int n) {
        this.size = n;
        this.bits = BitArray.makeArray(n);
    }

    BitArray(int[] arrn, int n) {
        this.bits = arrn;
        this.size = n;
    }

    private void ensureCapacity(int n) {
        if (n > this.bits.length << 5) {
            int[] arrn = BitArray.makeArray(n);
            System.arraycopy(this.bits, 0, arrn, 0, this.bits.length);
            this.bits = arrn;
        }
    }

    private static int[] makeArray(int n) {
        return new int[(n + 31) / 32];
    }

    public void appendBit(boolean bl) {
        this.ensureCapacity(this.size + 1);
        if (bl) {
            int[] arrn = this.bits;
            int n = this.size / 32;
            arrn[n] = arrn[n] | 1 << (this.size & 31);
        }
        ++this.size;
    }

    public void appendBitArray(BitArray bitArray) {
        int n = bitArray.size;
        this.ensureCapacity(this.size + n);
        for (int i = 0; i < n; ++i) {
            this.appendBit(bitArray.get(i));
        }
    }

    public void appendBits(int n, int n2) {
        if (n2 >= 0 && n2 <= 32) {
            this.ensureCapacity(this.size + n2);
            while (n2 > 0) {
                boolean bl = true;
                if ((n >> n2 - 1 & 1) != 1) {
                    bl = false;
                }
                this.appendBit(bl);
                --n2;
            }
            return;
        }
        throw new IllegalArgumentException("Num bits must be between 0 and 32");
    }

    public void clear() {
        int n = this.bits.length;
        for (int i = 0; i < n; ++i) {
            this.bits[i] = 0;
        }
    }

    public BitArray clone() {
        return new BitArray((int[])this.bits.clone(), this.size);
    }

    public boolean equals(Object object) {
        if (!(object instanceof BitArray)) {
            return false;
        }
        object = (BitArray)object;
        if (this.size == object.size && Arrays.equals(this.bits, object.bits)) {
            return true;
        }
        return false;
    }

    public void flip(int n) {
        int[] arrn = this.bits;
        int n2 = n / 32;
        arrn[n2] = 1 << (n & 31) ^ arrn[n2];
    }

    public boolean get(int n) {
        if ((1 << (n & 31) & this.bits[n / 32]) != 0) {
            return true;
        }
        return false;
    }

    public int[] getBitArray() {
        return this.bits;
    }

    public int getNextSet(int n) {
        if (n >= this.size) {
            return this.size;
        }
        int n2 = n / 32;
        int n3 = ~ ((1 << (n & 31)) - 1) & this.bits[n2];
        n = n2;
        while (n3 == 0) {
            if (++n == this.bits.length) {
                return this.size;
            }
            n3 = this.bits[n];
        }
        if ((n = (n << 5) + Integer.numberOfTrailingZeros(n3)) > this.size) {
            return this.size;
        }
        return n;
    }

    public int getNextUnset(int n) {
        if (n >= this.size) {
            return this.size;
        }
        int n2 = n / 32;
        int n3 = ~ ((1 << (n & 31)) - 1) & ~ this.bits[n2];
        n = n2;
        while (n3 == 0) {
            if (++n == this.bits.length) {
                return this.size;
            }
            n3 = ~ this.bits[n];
        }
        if ((n = (n << 5) + Integer.numberOfTrailingZeros(n3)) > this.size) {
            return this.size;
        }
        return n;
    }

    public int getSize() {
        return this.size;
    }

    public int getSizeInBytes() {
        return (this.size + 7) / 8;
    }

    public int hashCode() {
        return 31 * this.size + Arrays.hashCode(this.bits);
    }

    public boolean isRange(int n, int n2, boolean bl) {
        if (n2 >= n && n >= 0 && n2 <= this.size) {
            if (n2 == n) {
                return true;
            }
            int n3 = n2 - 1;
            int n4 = n / 32;
            int n5 = n3 / 32;
            for (int i = n4; i <= n5; ++i) {
                int n6 = 31;
                n2 = i > n4 ? 0 : n & 31;
                if (i >= n5) {
                    n6 = 31 & n3;
                }
                n6 = (2 << n6) - (1 << n2);
                int n7 = this.bits[i];
                n2 = bl ? n6 : 0;
                if ((n7 & n6) == n2) continue;
                return false;
            }
            return true;
        }
        throw new IllegalArgumentException();
    }

    public void reverse() {
        int n;
        int[] arrn = new int[this.bits.length];
        int n2 = (this.size - 1) / 32;
        int n3 = n2 + 1;
        for (n = 0; n < n3; ++n) {
            long l = this.bits[n];
            l = l >> 1 & 0x55555555L | (l & 0x55555555L) << 1;
            l = l >> 2 & 0x33333333L | (l & 0x33333333L) << 2;
            l = l >> 4 & 0xF0F0F0FL | (l & 0xF0F0F0FL) << 4;
            l = l >> 8 & 0xFF00FFL | (l & 0xFF00FFL) << 8;
            arrn[n2 - n] = (int)(l >> 16 & 65535L | (l & 65535L) << 16);
        }
        n = this.size;
        n2 = n3 << 5;
        if (n != n2) {
            int n4 = n2 - this.size;
            n2 = arrn[0] >>> n4;
            for (n = 1; n < n3; ++n) {
                int n5 = arrn[n];
                arrn[n - 1] = n2 | n5 << 32 - n4;
                n2 = n5 >>> n4;
            }
            arrn[n3 - 1] = n2;
        }
        this.bits = arrn;
    }

    public void set(int n) {
        int[] arrn = this.bits;
        int n2 = n / 32;
        arrn[n2] = 1 << (n & 31) | arrn[n2];
    }

    public void setBulk(int n, int n2) {
        this.bits[n / 32] = n2;
    }

    public void setRange(int n, int n2) {
        if (n2 >= n && n >= 0 && n2 <= this.size) {
            if (n2 == n) {
                return;
            }
            int n3 = n2 - 1;
            int n4 = n / 32;
            int n5 = n3 / 32;
            for (n2 = n4; n2 <= n5; ++n2) {
                int n6 = 31;
                int n7 = n2 > n4 ? 0 : n & 31;
                if (n2 >= n5) {
                    n6 = 31 & n3;
                }
                int[] arrn = this.bits;
                arrn[n2] = (2 << n6) - (1 << n7) | arrn[n2];
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    public void toBytes(int n, byte[] arrby, int n2, int n3) {
        for (int i = 0; i < n3; ++i) {
            int n4;
            for (int j = n4 = 0; j < 8; ++j) {
                int n5 = n4;
                if (this.get(n)) {
                    n5 = n4 | 1 << 7 - j;
                }
                ++n;
                n4 = n5;
            }
            arrby[n2 + i] = (byte)n4;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.size);
        for (int i = 0; i < this.size; ++i) {
            if ((i & 7) == 0) {
                stringBuilder.append(' ');
            }
            char c = this.get(i) ? (char)'X' : '.';
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public void xor(BitArray bitArray) {
        if (this.size != bitArray.size) {
            throw new IllegalArgumentException("Sizes don't match");
        }
        for (int i = 0; i < this.bits.length; ++i) {
            int[] arrn = this.bits;
            arrn[i] = arrn[i] ^ bitArray.bits[i];
        }
    }
}
