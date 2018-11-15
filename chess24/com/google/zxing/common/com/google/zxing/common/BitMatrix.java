/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.common;

import com.google.zxing.common.BitArray;
import java.util.Arrays;

public final class BitMatrix
implements Cloneable {
    private final int[] bits;
    private final int height;
    private final int rowSize;
    private final int width;

    public BitMatrix(int n) {
        this(n, n);
    }

    public BitMatrix(int n, int n2) {
        if (n > 0 && n2 > 0) {
            this.width = n;
            this.height = n2;
            this.rowSize = (n + 31) / 32;
            this.bits = new int[this.rowSize * n2];
            return;
        }
        throw new IllegalArgumentException("Both dimensions must be greater than 0");
    }

    private BitMatrix(int n, int n2, int n3, int[] arrn) {
        this.width = n;
        this.height = n2;
        this.rowSize = n3;
        this.bits = arrn;
    }

    private String buildToString(String string, String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder(this.height * (this.width + 1));
        for (int i = 0; i < this.height; ++i) {
            for (int j = 0; j < this.width; ++j) {
                String string4 = this.get(j, i) ? string : string2;
                stringBuilder.append(string4);
            }
            stringBuilder.append(string3);
        }
        return stringBuilder.toString();
    }

    public static BitMatrix parse(String object, String charSequence, String string) {
        int n;
        int n2;
        int n3;
        if (object == null) {
            throw new IllegalArgumentException();
        }
        boolean[] arrbl = new boolean[object.length()];
        int n4 = 0;
        int n5 = -1;
        int n6 = n = (n3 = (n2 = 0));
        int n7 = n;
        n = n2;
        while (n < object.length()) {
            if (object.charAt(n) != '\n' && object.charAt(n) != '\r') {
                if (object.substring(n, charSequence.length() + n).equals(charSequence)) {
                    n += charSequence.length();
                    arrbl[n3] = true;
                    ++n3;
                    continue;
                }
                if (object.substring(n, string.length() + n).equals(string)) {
                    n += string.length();
                    arrbl[n3] = false;
                    ++n3;
                    continue;
                }
                charSequence = new StringBuilder("illegal character encountered: ");
                charSequence.append(object.substring(n));
                throw new IllegalArgumentException(charSequence.toString());
            }
            int n8 = n7;
            n2 = n5;
            int n9 = n6;
            if (n3 > n7) {
                if (n5 == -1) {
                    n2 = n3 - n7;
                } else {
                    n2 = n5;
                    if (n3 - n7 != n5) {
                        throw new IllegalArgumentException("row lengths do not match");
                    }
                }
                n9 = n6 + 1;
                n8 = n3;
            }
            ++n;
            n7 = n8;
            n5 = n2;
            n6 = n9;
        }
        n = n5;
        n2 = n6;
        if (n3 > n7) {
            if (n5 == -1) {
                n = n3 - n7;
            } else {
                n = n5;
                if (n3 - n7 != n5) {
                    throw new IllegalArgumentException("row lengths do not match");
                }
            }
            n2 = n6 + 1;
        }
        object = new BitMatrix(n, n2);
        for (n5 = n4; n5 < n3; ++n5) {
            if (!arrbl[n5]) continue;
            object.set(n5 % n, n5 / n);
        }
        return object;
    }

    public void clear() {
        int n = this.bits.length;
        for (int i = 0; i < n; ++i) {
            this.bits[i] = 0;
        }
    }

    public BitMatrix clone() {
        return new BitMatrix(this.width, this.height, this.rowSize, (int[])this.bits.clone());
    }

    public boolean equals(Object object) {
        if (!(object instanceof BitMatrix)) {
            return false;
        }
        object = (BitMatrix)object;
        if (this.width == object.width && this.height == object.height && this.rowSize == object.rowSize && Arrays.equals(this.bits, object.bits)) {
            return true;
        }
        return false;
    }

    public void flip(int n, int n2) {
        n2 = n2 * this.rowSize + n / 32;
        int[] arrn = this.bits;
        arrn[n2] = 1 << (n & 31) ^ arrn[n2];
    }

    public boolean get(int n, int n2) {
        int n3 = this.rowSize;
        int n4 = n / 32;
        if ((this.bits[n2 * n3 + n4] >>> (n & 31) & 1) != 0) {
            return true;
        }
        return false;
    }

    public int[] getBottomRightOnBit() {
        int n;
        for (n = this.bits.length - 1; n >= 0 && this.bits[n] == 0; --n) {
        }
        if (n < 0) {
            return null;
        }
        int n2 = n / this.rowSize;
        int n3 = this.rowSize;
        int n4 = this.bits[n];
        int n5 = 31;
        while (n4 >>> n5 == 0) {
            --n5;
        }
        return new int[]{(n % n3 << 5) + n5, n2};
    }

    public int[] getEnclosingRectangle() {
        int n = this.width;
        int n2 = this.height;
        int n3 = -1;
        int n4 = -1;
        for (int i = 0; i < this.height; ++i) {
            int n5 = n4;
            n4 = n3;
            n3 = n5;
            for (int j = 0; j < this.rowSize; ++j) {
                int n6 = this.bits[this.rowSize * i + j];
                int n7 = n;
                int n8 = n4;
                int n9 = n2;
                int n10 = n3;
                if (n6 != 0) {
                    n5 = n2;
                    if (i < n2) {
                        n5 = i;
                    }
                    n2 = n3;
                    if (i > n3) {
                        n2 = i;
                    }
                    int n11 = j << 5;
                    int n12 = 31;
                    n3 = n;
                    if (n11 < n) {
                        n3 = 0;
                        while (n6 << 31 - n3 == 0) {
                            ++n3;
                        }
                        n9 = n3 + n11;
                        n3 = n;
                        if (n9 < n) {
                            n3 = n9;
                        }
                    }
                    n7 = n3;
                    n8 = n4;
                    n9 = n5;
                    n10 = n2;
                    if (n11 + 31 > n4) {
                        n = n12;
                        while (n6 >>> n == 0) {
                            --n;
                        }
                        n = n11 + n;
                        n7 = n3;
                        n8 = n4;
                        n9 = n5;
                        n10 = n2;
                        if (n > n4) {
                            n8 = n;
                            n10 = n2;
                            n9 = n5;
                            n7 = n3;
                        }
                    }
                }
                n = n7;
                n4 = n8;
                n2 = n9;
                n3 = n10;
            }
            n5 = n3;
            n3 = n4;
            n4 = n5;
        }
        if (n3 >= n && n4 >= n2) {
            return new int[]{n, n2, n3 - n + 1, n4 - n2 + 1};
        }
        return null;
    }

    public int getHeight() {
        return this.height;
    }

    public BitArray getRow(int n, BitArray bitArray) {
        if (bitArray != null && bitArray.getSize() >= this.width) {
            bitArray.clear();
        } else {
            bitArray = new BitArray(this.width);
        }
        int n2 = this.rowSize;
        for (int i = 0; i < this.rowSize; ++i) {
            bitArray.setBulk(i << 5, this.bits[n * n2 + i]);
        }
        return bitArray;
    }

    public int getRowSize() {
        return this.rowSize;
    }

    public int[] getTopLeftOnBit() {
        int n;
        for (n = 0; n < this.bits.length && this.bits[n] == 0; ++n) {
        }
        if (n == this.bits.length) {
            return null;
        }
        int n2 = n / this.rowSize;
        int n3 = this.rowSize;
        int n4 = this.bits[n];
        int n5 = 0;
        while (n4 << 31 - n5 == 0) {
            ++n5;
        }
        return new int[]{(n % n3 << 5) + n5, n2};
    }

    public int getWidth() {
        return this.width;
    }

    public int hashCode() {
        return (((this.width * 31 + this.width) * 31 + this.height) * 31 + this.rowSize) * 31 + Arrays.hashCode(this.bits);
    }

    public void rotate180() {
        int n = this.getWidth();
        int n2 = this.getHeight();
        BitArray bitArray = new BitArray(n);
        BitArray bitArray2 = new BitArray(n);
        for (n = 0; n < (n2 + 1) / 2; ++n) {
            bitArray = this.getRow(n, bitArray);
            int n3 = n2 - 1 - n;
            bitArray2 = this.getRow(n3, bitArray2);
            bitArray.reverse();
            bitArray2.reverse();
            this.setRow(n, bitArray2);
            this.setRow(n3, bitArray);
        }
    }

    public void set(int n, int n2) {
        n2 = n2 * this.rowSize + n / 32;
        int[] arrn = this.bits;
        arrn[n2] = 1 << (n & 31) | arrn[n2];
    }

    public void setRegion(int n, int n2, int n3, int n4) {
        if (n2 >= 0 && n >= 0) {
            if (n4 > 0 && n3 > 0) {
                int n5 = n3 + n;
                if ((n4 += n2) <= this.height && n5 <= this.width) {
                    while (n2 < n4) {
                        int n6 = this.rowSize;
                        for (n3 = n; n3 < n5; ++n3) {
                            int[] arrn = this.bits;
                            int n7 = n3 / 32 + n6 * n2;
                            arrn[n7] = arrn[n7] | 1 << (n3 & 31);
                        }
                        ++n2;
                    }
                    return;
                }
                throw new IllegalArgumentException("The region must fit inside the matrix");
            }
            throw new IllegalArgumentException("Height and width must be at least 1");
        }
        throw new IllegalArgumentException("Left and top must be nonnegative");
    }

    public void setRow(int n, BitArray bitArray) {
        System.arraycopy(bitArray.getBitArray(), 0, this.bits, n * this.rowSize, this.rowSize);
    }

    public String toString() {
        return this.toString("X ", "  ");
    }

    public String toString(String string, String string2) {
        return this.buildToString(string, string2, "\n");
    }

    @Deprecated
    public String toString(String string, String string2, String string3) {
        return this.buildToString(string, string2, string3);
    }

    public void unset(int n, int n2) {
        n2 = n2 * this.rowSize + n / 32;
        int[] arrn = this.bits;
        arrn[n2] = ~ (1 << (n & 31)) & arrn[n2];
    }

    public void xor(BitMatrix bitMatrix) {
        if (this.width == bitMatrix.getWidth() && this.height == bitMatrix.getHeight() && this.rowSize == bitMatrix.getRowSize()) {
            BitArray bitArray = new BitArray(this.width / 32 + 1);
            for (int i = 0; i < this.height; ++i) {
                int n = this.rowSize;
                int[] arrn = bitMatrix.getRow(i, bitArray).getBitArray();
                for (int j = 0; j < this.rowSize; ++j) {
                    int[] arrn2 = this.bits;
                    int n2 = n * i + j;
                    arrn2[n2] = arrn2[n2] ^ arrn[j];
                }
            }
            return;
        }
        throw new IllegalArgumentException("input matrix dimensions do not match");
    }
}
