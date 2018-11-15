/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned.rss;

import com.google.zxing.ResultPoint;

public final class FinderPattern {
    private final ResultPoint[] resultPoints;
    private final int[] startEnd;
    private final int value;

    public FinderPattern(int n, int[] arrn, int n2, int n3, int n4) {
        this.value = n;
        this.startEnd = arrn;
        float f = n2;
        float f2 = n4;
        this.resultPoints = new ResultPoint[]{new ResultPoint(f, f2), new ResultPoint(n3, f2)};
    }

    public boolean equals(Object object) {
        if (!(object instanceof FinderPattern)) {
            return false;
        }
        object = (FinderPattern)object;
        if (this.value == object.value) {
            return true;
        }
        return false;
    }

    public ResultPoint[] getResultPoints() {
        return this.resultPoints;
    }

    public int[] getStartEnd() {
        return this.startEnd;
    }

    public int getValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value;
    }
}
