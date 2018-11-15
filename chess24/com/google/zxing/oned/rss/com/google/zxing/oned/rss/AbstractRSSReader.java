/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned.rss;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.oned.OneDReader;

public abstract class AbstractRSSReader
extends OneDReader {
    private static final float MAX_AVG_VARIANCE = 0.2f;
    private static final float MAX_FINDER_PATTERN_RATIO = 0.89285713f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.45f;
    private static final float MIN_FINDER_PATTERN_RATIO = 0.7916667f;
    private final int[] dataCharacterCounters = new int[8];
    private final int[] decodeFinderCounters = new int[4];
    private final int[] evenCounts = new int[this.dataCharacterCounters.length / 2];
    private final float[] evenRoundingErrors = new float[4];
    private final int[] oddCounts = new int[this.dataCharacterCounters.length / 2];
    private final float[] oddRoundingErrors = new float[4];

    protected AbstractRSSReader() {
    }

    @Deprecated
    protected static int count(int[] arrn) {
        return MathUtils.sum(arrn);
    }

    protected static void decrement(int[] arrn, float[] arrf) {
        float f = arrf[0];
        int n = 0;
        for (int i = 1; i < arrn.length; ++i) {
            float f2 = f;
            if (arrf[i] < f) {
                f2 = arrf[i];
                n = i;
            }
            f = f2;
        }
        arrn[n] = arrn[n] - 1;
    }

    protected static void increment(int[] arrn, float[] arrf) {
        float f = arrf[0];
        int n = 0;
        for (int i = 1; i < arrn.length; ++i) {
            float f2 = f;
            if (arrf[i] > f) {
                f2 = arrf[i];
                n = i;
            }
            f = f2;
        }
        arrn[n] = arrn[n] + 1;
    }

    protected static boolean isFinderPattern(int[] arrn) {
        int n = arrn[0] + arrn[1];
        int n2 = arrn[2];
        int n3 = arrn[3];
        float f = (float)n / (float)(n2 + n + n3);
        if (f >= 0.7916667f && f <= 0.89285713f) {
            int n4 = Integer.MIN_VALUE;
            int n5 = arrn.length;
            n = Integer.MAX_VALUE;
            for (n2 = 0; n2 < n5; ++n2) {
                int n6 = arrn[n2];
                n3 = n4;
                if (n6 > n4) {
                    n3 = n6;
                }
                int n7 = n;
                if (n6 < n) {
                    n7 = n6;
                }
                n4 = n3;
                n = n7;
            }
            if (n4 < n * 10) {
                return true;
            }
            return false;
        }
        return false;
    }

    protected static int parseFinderValue(int[] arrn, int[][] arrn2) throws NotFoundException {
        for (int i = 0; i < arrn2.length; ++i) {
            if (AbstractRSSReader.patternMatchVariance(arrn, arrn2[i], 0.45f) >= 0.2f) continue;
            return i;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    protected final int[] getDataCharacterCounters() {
        return this.dataCharacterCounters;
    }

    protected final int[] getDecodeFinderCounters() {
        return this.decodeFinderCounters;
    }

    protected final int[] getEvenCounts() {
        return this.evenCounts;
    }

    protected final float[] getEvenRoundingErrors() {
        return this.evenRoundingErrors;
    }

    protected final int[] getOddCounts() {
        return this.oddCounts;
    }

    protected final float[] getOddRoundingErrors() {
        return this.oddRoundingErrors;
    }
}
