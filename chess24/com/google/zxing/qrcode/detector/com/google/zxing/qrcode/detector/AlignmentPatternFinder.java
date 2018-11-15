/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.detector.AlignmentPattern;
import java.util.ArrayList;
import java.util.List;

final class AlignmentPatternFinder {
    private final int[] crossCheckStateCount;
    private final int height;
    private final BitMatrix image;
    private final float moduleSize;
    private final List<AlignmentPattern> possibleCenters;
    private final ResultPointCallback resultPointCallback;
    private final int startX;
    private final int startY;
    private final int width;

    AlignmentPatternFinder(BitMatrix bitMatrix, int n, int n2, int n3, int n4, float f, ResultPointCallback resultPointCallback) {
        this.image = bitMatrix;
        this.possibleCenters = new ArrayList<AlignmentPattern>(5);
        this.startX = n;
        this.startY = n2;
        this.width = n3;
        this.height = n4;
        this.moduleSize = f;
        this.crossCheckStateCount = new int[3];
        this.resultPointCallback = resultPointCallback;
    }

    private static float centerFromEnd(int[] arrn, int n) {
        return (float)(n - arrn[2]) - (float)arrn[1] / 2.0f;
    }

    private float crossCheckVertical(int n, int n2, int n3, int n4) {
        int n5;
        BitMatrix bitMatrix = this.image;
        int n6 = bitMatrix.getHeight();
        int[] arrn = this.crossCheckStateCount;
        arrn[0] = 0;
        arrn[1] = 0;
        arrn[2] = 0;
        for (n5 = n; n5 >= 0 && bitMatrix.get(n2, n5) && arrn[1] <= n3; --n5) {
            arrn[1] = arrn[1] + 1;
        }
        if (n5 >= 0) {
            if (arrn[1] > n3) {
                return Float.NaN;
            }
            while (n5 >= 0 && !bitMatrix.get(n2, n5) && arrn[0] <= n3) {
                arrn[0] = arrn[0] + 1;
                --n5;
            }
            if (arrn[0] > n3) {
                return Float.NaN;
            }
            ++n;
            while (n < n6 && bitMatrix.get(n2, n) && arrn[1] <= n3) {
                arrn[1] = arrn[1] + 1;
                ++n;
            }
            if (n != n6) {
                if (arrn[1] > n3) {
                    return Float.NaN;
                }
                while (n < n6 && !bitMatrix.get(n2, n) && arrn[2] <= n3) {
                    arrn[2] = arrn[2] + 1;
                    ++n;
                }
                if (arrn[2] > n3) {
                    return Float.NaN;
                }
                if (5 * Math.abs(arrn[0] + arrn[1] + arrn[2] - n4) >= 2 * n4) {
                    return Float.NaN;
                }
                if (this.foundPatternCross(arrn)) {
                    return AlignmentPatternFinder.centerFromEnd(arrn, n);
                }
                return Float.NaN;
            }
            return Float.NaN;
        }
        return Float.NaN;
    }

    private boolean foundPatternCross(int[] arrn) {
        float f = this.moduleSize;
        float f2 = f / 2.0f;
        for (int i = 0; i < 3; ++i) {
            if (Math.abs(f - (float)arrn[i]) < f2) continue;
            return false;
        }
        return true;
    }

    private AlignmentPattern handlePossibleCenter(int[] object, int n, int n2) {
        int n3 = object[0];
        int n4 = object[1];
        int n5 = object[2];
        float f = AlignmentPatternFinder.centerFromEnd((int[])object, n2);
        float f2 = this.crossCheckVertical(n, (int)f, object[1] * 2, n3 + n4 + n5);
        if (!Float.isNaN(f2)) {
            float f3 = (float)(object[0] + object[1] + object[2]) / 3.0f;
            for (AlignmentPattern alignmentPattern : this.possibleCenters) {
                if (!alignmentPattern.aboutEquals(f3, f2, f)) continue;
                return alignmentPattern.combineEstimate(f2, f, f3);
            }
            object = new AlignmentPattern(f, f2, f3);
            this.possibleCenters.add((AlignmentPattern)object);
            if (this.resultPointCallback != null) {
                this.resultPointCallback.foundPossibleResultPoint((ResultPoint)object);
            }
        }
        return null;
    }

    AlignmentPattern find() throws NotFoundException {
        int n = this.startX;
        int n2 = this.height;
        int n3 = this.width + n;
        int n4 = this.startY;
        int n5 = n2 / 2;
        int[] arrn = new int[3];
        for (int i = 0; i < n2; ++i) {
            AlignmentPattern alignmentPattern;
            int n6;
            int n7 = (i & 1) == 0 ? (i + 1) / 2 : - (i + 1) / 2;
            int n8 = n7 + (n4 + n5);
            arrn[0] = 0;
            arrn[1] = 0;
            arrn[2] = 0;
            for (n6 = n; n6 < n3 && !this.image.get(n6, n8); ++n6) {
            }
            n7 = 0;
            for (int j = n6; j < n3; ++j) {
                if (this.image.get(j, n8)) {
                    if (n7 == 1) {
                        arrn[1] = arrn[1] + 1;
                        continue;
                    }
                    if (n7 == 2) {
                        if (this.foundPatternCross(arrn) && (alignmentPattern = this.handlePossibleCenter(arrn, n8, j)) != null) {
                            return alignmentPattern;
                        }
                        arrn[0] = arrn[2];
                        arrn[1] = 1;
                        arrn[2] = 0;
                        n7 = 1;
                        continue;
                    }
                    arrn[++n7] = arrn[n7] + 1;
                    continue;
                }
                n6 = n7;
                if (n7 == 1) {
                    n6 = n7 + 1;
                }
                arrn[n6] = arrn[n6] + 1;
                n7 = n6;
            }
            if (!this.foundPatternCross(arrn) || (alignmentPattern = this.handlePossibleCenter(arrn, n8, n3)) == null) continue;
            return alignmentPattern;
        }
        if (!this.possibleCenters.isEmpty()) {
            return this.possibleCenters.get(0);
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
