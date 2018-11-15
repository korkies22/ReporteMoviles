/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.OneDReader;
import java.util.Map;

public final class ITFReader
extends OneDReader {
    private static final int[] DEFAULT_ALLOWED_LENGTHS = new int[]{6, 8, 10, 12, 14};
    private static final int[] END_PATTERN_REVERSED;
    private static final float MAX_AVG_VARIANCE = 0.38f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.78f;
    private static final int N = 1;
    static final int[][] PATTERNS;
    private static final int[] START_PATTERN;
    private static final int W = 3;
    private int narrowLineWidth = -1;

    static {
        START_PATTERN = new int[]{1, 1, 1, 1};
        END_PATTERN_REVERSED = new int[]{1, 1, 3};
        int[] arrn = new int[]{1, 1, 1, 3, 3};
        int[] arrn2 = new int[]{3, 1, 1, 3, 1};
        int[] arrn3 = new int[]{1, 3, 1, 3, 1};
        PATTERNS = new int[][]{{1, 1, 3, 3, 1}, {3, 1, 1, 1, 3}, {1, 3, 1, 1, 3}, {3, 3, 1, 1, 1}, {1, 1, 3, 1, 3}, {3, 1, 3, 1, 1}, {1, 3, 3, 1, 1}, arrn, arrn2, arrn3};
    }

    private static int decodeDigit(int[] arrn) throws NotFoundException {
        int[][] arrn2 = PATTERNS;
        float f = 0.38f;
        int n = -1;
        int n2 = arrn2.length;
        for (int i = 0; i < n2; ++i) {
            float f2 = ITFReader.patternMatchVariance(arrn, PATTERNS[i], 0.78f);
            float f3 = f;
            if (f2 < f) {
                n = i;
                f3 = f2;
            }
            f = f3;
        }
        if (n >= 0) {
            return n;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private int[] decodeEnd(BitArray bitArray) throws NotFoundException {
        bitArray.reverse();
        int[] arrn = ITFReader.findGuardPattern(bitArray, ITFReader.skipWhiteSpace(bitArray), END_PATTERN_REVERSED);
        this.validateQuietZone(bitArray, arrn[0]);
        int n = arrn[0];
        try {
            arrn[0] = bitArray.getSize() - arrn[1];
            arrn[1] = bitArray.getSize() - n;
            return arrn;
        }
        finally {
            bitArray.reverse();
        }
    }

    private static void decodeMiddle(BitArray bitArray, int n, int n2, StringBuilder stringBuilder) throws NotFoundException {
        int[] arrn = new int[10];
        int[] arrn2 = new int[5];
        int[] arrn3 = new int[5];
        block0 : while (n < n2) {
            int n3;
            int n4;
            ITFReader.recordPattern(bitArray, n, arrn);
            int n5 = 0;
            for (n4 = 0; n4 < 5; ++n4) {
                n3 = 2 * n4;
                arrn2[n4] = arrn[n3];
                arrn3[n4] = arrn[n3 + 1];
            }
            stringBuilder.append((char)(ITFReader.decodeDigit(arrn2) + 48));
            stringBuilder.append((char)(ITFReader.decodeDigit(arrn3) + 48));
            n3 = n;
            n4 = n5;
            do {
                n = n3;
                if (n4 >= 10) continue block0;
                n3 += arrn[n4];
                ++n4;
            } while (true);
        }
    }

    private int[] decodeStart(BitArray bitArray) throws NotFoundException {
        int[] arrn = ITFReader.findGuardPattern(bitArray, ITFReader.skipWhiteSpace(bitArray), START_PATTERN);
        this.narrowLineWidth = (arrn[1] - arrn[0]) / 4;
        this.validateQuietZone(bitArray, arrn[0]);
        return arrn;
    }

    private static int[] findGuardPattern(BitArray bitArray, int n, int[] arrn) throws NotFoundException {
        int n2;
        int n3 = arrn.length;
        int[] arrn2 = new int[n3];
        int n4 = bitArray.getSize();
        int n5 = n;
        int n6 = n2 = 0;
        int n7 = n;
        n = n5;
        while (n7 < n4) {
            if (bitArray.get(n7) ^ n2) {
                arrn2[n6] = arrn2[n6] + 1;
                n5 = n;
            } else {
                int n8 = n3 - 1;
                if (n6 == n8) {
                    if (ITFReader.patternMatchVariance(arrn2, arrn, 0.78f) < 0.38f) {
                        return new int[]{n, n7};
                    }
                    n5 = n + (arrn2[0] + arrn2[1]);
                    n = n3 - 2;
                    System.arraycopy(arrn2, 2, arrn2, 0, n);
                    arrn2[n] = 0;
                    arrn2[n8] = 0;
                    n = n6 - 1;
                    n6 = n5;
                } else {
                    n5 = n6 + 1;
                    n6 = n;
                    n = n5;
                }
                arrn2[n] = 1;
                n2 ^= 1;
                n5 = n6;
                n6 = n;
            }
            ++n7;
            n = n5;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int skipWhiteSpace(BitArray bitArray) throws NotFoundException {
        int n = bitArray.getSize();
        int n2 = bitArray.getNextSet(0);
        if (n2 == n) {
            throw NotFoundException.getNotFoundInstance();
        }
        return n2;
    }

    private void validateQuietZone(BitArray bitArray, int n) throws NotFoundException {
        int n2 = this.narrowLineWidth * 10;
        if (n2 >= n) {
            n2 = n;
        }
        --n;
        while (n2 > 0 && n >= 0 && !bitArray.get(n)) {
            --n2;
            --n;
        }
        if (n2 != 0) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    @Override
    public Result decodeRow(int n, BitArray object, Map<DecodeHintType, ?> object2) throws FormatException, NotFoundException {
        int[] arrn;
        int n2;
        int n3;
        Object object3;
        CharSequence charSequence;
        int n4;
        int n5;
        block7 : {
            arrn = this.decodeStart((BitArray)object);
            object3 = this.decodeEnd((BitArray)object);
            charSequence = new StringBuilder(20);
            ITFReader.decodeMiddle((BitArray)object, arrn[1], object3[0], (StringBuilder)charSequence);
            charSequence = ((StringBuilder)charSequence).toString();
            object = object2 != null ? (int[])object2.get((Object)DecodeHintType.ALLOWED_LENGTHS) : null;
            object2 = object;
            if (object == null) {
                object2 = DEFAULT_ALLOWED_LENGTHS;
            }
            n4 = ((String)charSequence).length();
            int n6 = ((int[])object2).length;
            n2 = n3 = 0;
            while (n3 < n6) {
                int n7 = object2[n3];
                if (n4 == n7) {
                    n3 = 1;
                    break block7;
                }
                n5 = n2;
                if (n7 > n2) {
                    n5 = n7;
                }
                ++n3;
                n2 = n5;
            }
            n3 = 0;
        }
        n5 = n3;
        if (n3 == 0) {
            n5 = n3;
            if (n4 > n2) {
                n5 = 1;
            }
        }
        if (n5 == 0) {
            throw FormatException.getFormatInstance();
        }
        float f = arrn[1];
        float f2 = n;
        object = new ResultPoint(f, f2);
        object2 = new ResultPoint(object3[0], f2);
        object3 = BarcodeFormat.ITF;
        return new Result((String)charSequence, null, new ResultPoint[]{object, object2}, (BarcodeFormat)((Object)object3));
    }
}
