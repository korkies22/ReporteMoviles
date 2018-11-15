/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.pdf417.PDF417Common;
import java.lang.reflect.Array;

final class PDF417CodewordDecoder {
    private static final float[][] RATIOS_TABLE = (float[][])Array.newInstance(Float.TYPE, PDF417Common.SYMBOL_TABLE.length, 8);

    static {
        for (int i = 0; i < PDF417Common.SYMBOL_TABLE.length; ++i) {
            int n = PDF417Common.SYMBOL_TABLE[i];
            int n2 = n & 1;
            for (int j = 0; j < 8; ++j) {
                int n3;
                float f = 0.0f;
                while ((n3 = n & 1) == n2) {
                    f += 1.0f;
                    n >>= 1;
                }
                PDF417CodewordDecoder.RATIOS_TABLE[i][8 - j - 1] = f / 17.0f;
                n2 = n3;
            }
        }
    }

    private PDF417CodewordDecoder() {
    }

    private static int getBitValue(int[] arrn) {
        long l = 0L;
        for (int i = 0; i < arrn.length; ++i) {
            for (int j = 0; j < arrn[i]; ++j) {
                int n = 1;
                if (i % 2 != 0) {
                    n = 0;
                }
                long l2 = n;
                l = l << 1 | l2;
            }
        }
        return (int)l;
    }

    private static int getClosestDecodedValue(int[] arrn) {
        int n;
        int n2 = MathUtils.sum(arrn);
        float[] arrf = new float[8];
        for (n = 0; n < 8; ++n) {
            arrf[n] = (float)arrn[n] / (float)n2;
        }
        n2 = -1;
        float f = Float.MAX_VALUE;
        for (n = 0; n < RATIOS_TABLE.length; ++n) {
            float f2;
            arrn = RATIOS_TABLE[n];
            float f3 = 0.0f;
            int n3 = 0;
            do {
                f2 = f3;
                if (n3 >= 8) break;
                f2 = arrn[n3] - arrf[n3];
                f2 = f3 += f2 * f2;
                if (f3 >= f) break;
                ++n3;
            } while (true);
            f3 = f;
            if (f2 < f) {
                n2 = PDF417Common.SYMBOL_TABLE[n];
                f3 = f2;
            }
            f = f3;
        }
        return n2;
    }

    private static int getDecodedCodewordValue(int[] arrn) {
        int n = PDF417CodewordDecoder.getBitValue(arrn);
        if (PDF417Common.getCodeword(n) == -1) {
            return -1;
        }
        return n;
    }

    static int getDecodedValue(int[] arrn) {
        int n = PDF417CodewordDecoder.getDecodedCodewordValue(PDF417CodewordDecoder.sampleBitCounts(arrn));
        if (n != -1) {
            return n;
        }
        return PDF417CodewordDecoder.getClosestDecodedValue(arrn);
    }

    private static int[] sampleBitCounts(int[] arrn) {
        int n;
        float f = MathUtils.sum(arrn);
        int[] arrn2 = new int[8];
        int n2 = n = 0;
        for (int i = 0; i < 17; ++i) {
            float f2 = f / 34.0f;
            float f3 = (float)i * f / 17.0f;
            int n3 = n;
            int n4 = n2;
            if ((float)(arrn[n2] + n) <= f2 + f3) {
                n3 = n + arrn[n2];
                n4 = n2 + 1;
            }
            arrn2[n4] = arrn2[n4] + 1;
            n = n3;
            n2 = n4;
        }
        return arrn2;
    }
}
