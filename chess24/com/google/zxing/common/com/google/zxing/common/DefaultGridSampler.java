/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.common;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.PerspectiveTransform;

public final class DefaultGridSampler
extends GridSampler {
    @Override
    public BitMatrix sampleGrid(BitMatrix bitMatrix, int n, int n2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16) throws NotFoundException {
        return this.sampleGrid(bitMatrix, n, n2, PerspectiveTransform.quadrilateralToQuadrilateral(f, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16));
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public BitMatrix sampleGrid(BitMatrix bitMatrix, int n, int n2, PerspectiveTransform perspectiveTransform) throws NotFoundException {
        if (n <= 0) throw NotFoundException.getNotFoundInstance();
        if (n2 <= 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        BitMatrix bitMatrix2 = new BitMatrix(n, n2);
        float[] arrf = new float[2 * n];
        n = 0;
        while (n < n2) {
            int n3;
            int n4 = arrf.length;
            float f = n;
            for (n3 = 0; n3 < n4; n3 += 2) {
                arrf[n3] = (float)(n3 / 2) + 0.5f;
                arrf[n3 + 1] = f + 0.5f;
            }
            perspectiveTransform.transformPoints(arrf);
            DefaultGridSampler.checkAndNudgePoints(bitMatrix, arrf);
            for (n3 = 0; n3 < n4; n3 += 2) {
                if (!bitMatrix.get((int)arrf[n3], (int)arrf[n3 + 1])) continue;
                bitMatrix2.set(n3 / 2, n);
                continue;
            }
            ++n;
        }
        return bitMatrix2;
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw NotFoundException.getNotFoundInstance();
        }
    }
}
