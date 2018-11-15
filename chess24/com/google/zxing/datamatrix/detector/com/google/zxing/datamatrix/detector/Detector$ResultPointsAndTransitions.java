/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.detector;

import com.google.zxing.ResultPoint;
import com.google.zxing.datamatrix.detector.Detector;

private static final class Detector.ResultPointsAndTransitions {
    private final ResultPoint from;
    private final ResultPoint to;
    private final int transitions;

    private Detector.ResultPointsAndTransitions(ResultPoint resultPoint, ResultPoint resultPoint2, int n) {
        this.from = resultPoint;
        this.to = resultPoint2;
        this.transitions = n;
    }

    ResultPoint getFrom() {
        return this.from;
    }

    ResultPoint getTo() {
        return this.to;
    }

    int getTransitions() {
        return this.transitions;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.from);
        stringBuilder.append("/");
        stringBuilder.append(this.to);
        stringBuilder.append('/');
        stringBuilder.append(this.transitions);
        return stringBuilder.toString();
    }
}
