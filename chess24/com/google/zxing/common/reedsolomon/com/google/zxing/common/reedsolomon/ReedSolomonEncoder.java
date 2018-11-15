/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.common.reedsolomon;

import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.GenericGFPoly;
import java.util.ArrayList;
import java.util.List;

public final class ReedSolomonEncoder {
    private final List<GenericGFPoly> cachedGenerators;
    private final GenericGF field;

    public ReedSolomonEncoder(GenericGF genericGF) {
        this.field = genericGF;
        this.cachedGenerators = new ArrayList<GenericGFPoly>();
        this.cachedGenerators.add(new GenericGFPoly(genericGF, new int[]{1}));
    }

    private GenericGFPoly buildGenerator(int n) {
        if (n >= this.cachedGenerators.size()) {
            GenericGFPoly genericGFPoly = this.cachedGenerators.get(this.cachedGenerators.size() - 1);
            for (int i = this.cachedGenerators.size(); i <= n; ++i) {
                genericGFPoly = genericGFPoly.multiply(new GenericGFPoly(this.field, new int[]{1, this.field.exp(i - 1 + this.field.getGeneratorBase())}));
                this.cachedGenerators.add(genericGFPoly);
            }
        }
        return this.cachedGenerators.get(n);
    }

    public void encode(int[] arrn, int n) {
        if (n == 0) {
            throw new IllegalArgumentException("No error correction bytes");
        }
        int n2 = arrn.length - n;
        if (n2 <= 0) {
            throw new IllegalArgumentException("No data bytes provided");
        }
        int[] arrn2 = this.buildGenerator(n);
        int[] arrn3 = new int[n2];
        System.arraycopy(arrn, 0, arrn3, 0, n2);
        arrn2 = new GenericGFPoly(this.field, arrn3).multiplyByMonomial(n, 1).divide((GenericGFPoly)arrn2)[1].getCoefficients();
        int n3 = n - arrn2.length;
        for (n = 0; n < n3; ++n) {
            arrn[n2 + n] = 0;
        }
        System.arraycopy(arrn2, 0, arrn, n2 + n3, arrn2.length);
    }
}
