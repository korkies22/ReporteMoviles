/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.multi.qrcode.detector;

import com.google.zxing.multi.qrcode.detector.MultiFinderPatternFinder;
import com.google.zxing.qrcode.detector.FinderPattern;
import java.io.Serializable;
import java.util.Comparator;

private static final class MultiFinderPatternFinder.ModuleSizeComparator
implements Serializable,
Comparator<FinderPattern> {
    private MultiFinderPatternFinder.ModuleSizeComparator() {
    }

    @Override
    public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
        double d = finderPattern2.getEstimatedModuleSize() - finderPattern.getEstimatedModuleSize();
        if (d < 0.0) {
            return -1;
        }
        if (d > 0.0) {
            return 1;
        }
        return 0;
    }
}
