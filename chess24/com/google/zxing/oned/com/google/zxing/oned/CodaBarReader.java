/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.OneDReader;
import java.util.Arrays;
import java.util.Map;

public final class CodaBarReader
extends OneDReader {
    static final char[] ALPHABET = "0123456789-$:/.+ABCD".toCharArray();
    private static final String ALPHABET_STRING = "0123456789-$:/.+ABCD";
    static final int[] CHARACTER_ENCODINGS = new int[]{3, 6, 9, 96, 18, 66, 33, 36, 48, 72, 12, 24, 69, 81, 84, 21, 26, 41, 11, 14};
    private static final float MAX_ACCEPTABLE = 2.0f;
    private static final int MIN_CHARACTER_LENGTH = 3;
    private static final float PADDING = 1.5f;
    private static final char[] STARTEND_ENCODING = new char[]{'A', 'B', 'C', 'D'};
    private int counterLength = 0;
    private int[] counters = new int[80];
    private final StringBuilder decodeRowResult = new StringBuilder(20);

    static boolean arrayContains(char[] arrc, char c) {
        if (arrc != null) {
            int n = arrc.length;
            for (int i = 0; i < n; ++i) {
                if (arrc[i] != c) continue;
                return true;
            }
        }
        return false;
    }

    private void counterAppend(int n) {
        this.counters[this.counterLength] = n;
        ++this.counterLength;
        if (this.counterLength >= this.counters.length) {
            int[] arrn = new int[this.counterLength << 1];
            System.arraycopy(this.counters, 0, arrn, 0, this.counterLength);
            this.counters = arrn;
        }
    }

    private int findStartPattern() throws NotFoundException {
        for (int i = 1; i < this.counterLength; i += 2) {
            int n = this.toNarrowWidePattern(i);
            if (n == -1 || !CodaBarReader.arrayContains(STARTEND_ENCODING, ALPHABET[n])) continue;
            int n2 = 0;
            for (n = i; n < i + 7; ++n) {
                n2 += this.counters[n];
            }
            if (i != 1 && this.counters[i - 1] < n2 / 2) continue;
            return i;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void setCounters(BitArray bitArray) throws NotFoundException {
        int n;
        this.counterLength = 0;
        int n2 = bitArray.getNextUnset(0);
        if (n2 >= (n = bitArray.getSize())) {
            throw NotFoundException.getNotFoundInstance();
        }
        int n3 = 0;
        boolean bl = true;
        while (n2 < n) {
            if (bitArray.get(n2) ^ bl) {
                ++n3;
            } else {
                this.counterAppend(n3);
                bl ^= true;
                n3 = 1;
            }
            ++n2;
        }
        this.counterAppend(n3);
    }

    private int toNarrowWidePattern(int n) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6 = n + 7;
        if (n6 >= this.counterLength) {
            return -1;
        }
        int[] arrn = this.counters;
        int n7 = Integer.MAX_VALUE;
        int n8 = 0;
        int n9 = Integer.MAX_VALUE;
        int n10 = 0;
        for (n4 = n; n4 < n6; n4 += 2) {
            n2 = arrn[n4];
            n5 = n9;
            if (n2 < n9) {
                n5 = n2;
            }
            n3 = n10;
            if (n2 > n10) {
                n3 = n2;
            }
            n9 = n5;
            n10 = n3;
        }
        int n11 = (n9 + n10) / 2;
        n4 = 0;
        n9 = n7;
        for (n10 = n + 1; n10 < n6; n10 += 2) {
            n2 = arrn[n10];
            n5 = n9;
            if (n2 < n9) {
                n5 = n2;
            }
            n3 = n4;
            if (n2 > n4) {
                n3 = n2;
            }
            n9 = n5;
            n4 = n3;
        }
        n3 = (n9 + n4) / 2;
        n5 = 128;
        n10 = n4 = 0;
        do {
            if (n4 >= 7) break;
            n2 = (n4 & 1) == 0 ? n11 : n3;
            n5 >>= 1;
            n9 = n10;
            if (arrn[n + n4] > n2) {
                n9 = n10 | n5;
            }
            ++n4;
            n10 = n9;
        } while (true);
        for (n9 = n8; n9 < CHARACTER_ENCODINGS.length; ++n9) {
            if (CHARACTER_ENCODINGS[n9] != n10) continue;
            return n9;
        }
        return -1;
    }

    private void validatePattern(int n) throws NotFoundException {
        int[] arrn;
        int n2;
        int[] arrn2;
        int n3;
        int[] arrn3 = arrn = new int[4];
        arrn3[0] = 0;
        arrn3[1] = 0;
        arrn3[2] = 0;
        arrn3[3] = 0;
        int[] arrn4 = arrn2 = new int[4];
        arrn4[0] = 0;
        arrn4[1] = 0;
        arrn4[2] = 0;
        arrn4[3] = 0;
        int n4 = this.decodeRowResult.length() - 1;
        int n5 = 0;
        int n6 = n;
        int n7 = 0;
        do {
            n2 = CHARACTER_ENCODINGS[this.decodeRowResult.charAt(n7)];
            for (n3 = 6; n3 >= 0; --n3) {
                int n8 = (n3 & 1) + ((n2 & 1) << 1);
                arrn[n8] = arrn[n8] + this.counters[n6 + n3];
                arrn2[n8] = arrn2[n8] + 1;
                n2 >>= 1;
            }
            if (n7 >= n4) break;
            n6 += 8;
            ++n7;
        } while (true);
        float[] arrf = new float[4];
        float[] arrf2 = new float[4];
        n3 = 0;
        do {
            n7 = n5;
            n6 = n;
            if (n3 >= 2) break;
            arrf2[n3] = 0.0f;
            n7 = n3 + 2;
            arrf2[n7] = ((float)arrn[n3] / (float)arrn2[n3] + (float)arrn[n7] / (float)arrn2[n7]) / 2.0f;
            arrf[n3] = arrf2[n7];
            arrf[n7] = ((float)arrn[n7] * 2.0f + 1.5f) / (float)arrn2[n7];
            ++n3;
        } while (true);
        do {
            n3 = CHARACTER_ENCODINGS[this.decodeRowResult.charAt(n7)];
            for (n = 6; n >= 0; --n) {
                float f = this.counters[n6 + n];
                n2 = (n & 1) + ((n3 & 1) << 1);
                if (f >= arrf2[n2] && f <= arrf[n2]) {
                    n3 >>= 1;
                    continue;
                }
                throw NotFoundException.getNotFoundInstance();
            }
            if (n7 >= n4) break;
            n6 += 8;
            ++n7;
        } while (true);
    }

    @Override
    public Result decodeRow(int n, BitArray object, Map<DecodeHintType, ?> object2) throws NotFoundException {
        int n2;
        int n3;
        Arrays.fill(this.counters, 0);
        this.setCounters((BitArray)object);
        int n4 = this.findStartPattern();
        this.decodeRowResult.setLength(0);
        int n5 = n4;
        do {
            if ((n2 = this.toNarrowWidePattern(n5)) == -1) {
                throw NotFoundException.getNotFoundInstance();
            }
            this.decodeRowResult.append((char)n2);
            n3 = n5 + 8;
            if (this.decodeRowResult.length() > 1 && CodaBarReader.arrayContains(STARTEND_ENCODING, ALPHABET[n2])) break;
            n5 = n3;
        } while (n3 < this.counterLength);
        object = this.counters;
        int n6 = n3 - 1;
        int n7 = object[n6];
        n2 = 0;
        for (n5 = -8; n5 < -1; ++n5) {
            n2 += this.counters[n3 + n5];
        }
        if (n3 < this.counterLength && n7 < n2 / 2) {
            throw NotFoundException.getNotFoundInstance();
        }
        this.validatePattern(n4);
        for (n5 = 0; n5 < this.decodeRowResult.length(); ++n5) {
            this.decodeRowResult.setCharAt(n5, ALPHABET[this.decodeRowResult.charAt(n5)]);
        }
        char c = this.decodeRowResult.charAt(0);
        if (!CodaBarReader.arrayContains(STARTEND_ENCODING, c)) {
            throw NotFoundException.getNotFoundInstance();
        }
        c = this.decodeRowResult.charAt(this.decodeRowResult.length() - 1);
        if (!CodaBarReader.arrayContains(STARTEND_ENCODING, c)) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (this.decodeRowResult.length() <= 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (object2 == null || !object2.containsKey((Object)DecodeHintType.RETURN_CODABAR_START_END)) {
            this.decodeRowResult.deleteCharAt(this.decodeRowResult.length() - 1);
            this.decodeRowResult.deleteCharAt(0);
        }
        n5 = n2 = 0;
        while (n2 < n4) {
            n5 += this.counters[n2];
            ++n2;
        }
        float f = n5;
        while (n4 < n6) {
            n5 += this.counters[n4];
            ++n4;
        }
        float f2 = n5;
        object = this.decodeRowResult.toString();
        float f3 = n;
        object2 = new ResultPoint(f, f3);
        ResultPoint resultPoint = new ResultPoint(f2, f3);
        BarcodeFormat barcodeFormat = BarcodeFormat.CODABAR;
        return new Result((String)object, null, new ResultPoint[]{object2, resultPoint}, barcodeFormat);
    }
}
