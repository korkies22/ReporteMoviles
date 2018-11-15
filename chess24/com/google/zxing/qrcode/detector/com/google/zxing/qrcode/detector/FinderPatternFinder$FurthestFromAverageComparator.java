/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.detector;

import com.google.zxing.qrcode.detector.FinderPattern;
import com.google.zxing.qrcode.detector.FinderPatternFinder;
import java.io.Serializable;
import java.util.Comparator;

private static final class FinderPatternFinder.FurthestFromAverageComparator
implements Serializable,
Comparator<FinderPattern> {
    private final float average;

    private FinderPatternFinder.FurthestFromAverageComparator(float f) {
        this.average = f;
    }

    @Override
    public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
        float f;
        float f2 = Math.abs(finderPattern2.getEstimatedModuleSize() - this.average);
        if (f2 < (f = Math.abs(finderPattern.getEstimatedModuleSize() - this.average))) {
            return -1;
        }
        if (f2 == f) {
            return 0;
        }
        return 1;
    }
}
