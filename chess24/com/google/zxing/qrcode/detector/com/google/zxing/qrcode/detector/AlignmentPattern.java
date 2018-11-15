/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.detector;

import com.google.zxing.ResultPoint;

public final class AlignmentPattern
extends ResultPoint {
    private final float estimatedModuleSize;

    AlignmentPattern(float f, float f2, float f3) {
        super(f, f2);
        this.estimatedModuleSize = f3;
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

    AlignmentPattern combineEstimate(float f, float f2, float f3) {
        return new AlignmentPattern((this.getX() + f2) / 2.0f, (this.getY() + f) / 2.0f, (this.estimatedModuleSize + f3) / 2.0f);
    }
}
