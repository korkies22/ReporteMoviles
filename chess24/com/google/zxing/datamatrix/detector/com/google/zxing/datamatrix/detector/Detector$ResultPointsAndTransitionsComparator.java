/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.detector;

import com.google.zxing.datamatrix.detector.Detector;
import java.io.Serializable;
import java.util.Comparator;

private static final class Detector.ResultPointsAndTransitionsComparator
implements Serializable,
Comparator<Detector.ResultPointsAndTransitions> {
    private Detector.ResultPointsAndTransitionsComparator() {
    }

    @Override
    public int compare(Detector.ResultPointsAndTransitions resultPointsAndTransitions, Detector.ResultPointsAndTransitions resultPointsAndTransitions2) {
        return resultPointsAndTransitions.getTransitions() - resultPointsAndTransitions2.getTransitions();
    }
}
