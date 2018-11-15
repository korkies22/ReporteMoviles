/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.detector;

import com.google.zxing.ResultPoint;

public final class FinderPattern
extends ResultPoint {
    private final int count;
    private final float estimatedModuleSize;

    FinderPattern(float f, float f2, float f3) {
        this(f, f2, f3, 1);
    }

    private FinderPattern(float f, float f2, float f3, int n) {
        super(f, f2);
        this.estimatedModuleSize = f3;
        this.count = n;
    }

    boolean aboutEquals(float f, float f2, float f3) {
        if (Math.abs(f2 - this.getY()) <= f && Math.abs(f3 - this.getX()) <= f) {
            if ((f = Math.abs(f - this.estimatedModuleSize)) > 1.0f && f > this.estimatedModuleSize) {
                return false;
            }
            return true;
        }
        return false;
    }

    FinderPattern combineEstimate(float f, float f2, float f3) {
        int n = this.count + 1;
        float f4 = this.count;
        float f5 = this.getX();
        float f6 = n;
        return new FinderPattern((f4 * f5 + f2) / f6, ((float)this.count * this.getY() + f) / f6, ((float)this.count * this.estimatedModuleSize + f3) / f6, n);
    }

    int getCount() {
        return this.count;
    }

    public float getEstimatedModuleSize() {
        return this.estimatedModuleSize;
    }
}
