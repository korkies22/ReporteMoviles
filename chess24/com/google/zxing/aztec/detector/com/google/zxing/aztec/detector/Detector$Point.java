/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.aztec.detector;

import com.google.zxing.ResultPoint;
import com.google.zxing.aztec.detector.Detector;

static final class Detector.Point {
    private final int x;
    private final int y;

    Detector.Point(int n, int n2) {
        this.x = n;
        this.y = n2;
    }

    int getX() {
        return this.x;
    }

    int getY() {
        return this.y;
    }

    ResultPoint toResultPoint() {
        return new ResultPoint(this.getX(), this.getY());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("<");
        stringBuilder.append(this.x);
        stringBuilder.append(' ');
        stringBuilder.append(this.y);
        stringBuilder.append('>');
        return stringBuilder.toString();
    }
}
