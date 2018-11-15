/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.common.reedsolomon;

import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.GenericGFPoly;
import com.google.zxing.common.reedsolomon.ReedSolomonException;

public final class ReedSolomonDecoder {
    private final GenericGF field;

    public ReedSolomonDecoder(GenericGF genericGF) {
        this.field = genericGF;
    }

    private int[] findErrorLocations(GenericGFPoly genericGFPoly) throws ReedSolomonException {
        int n = genericGFPoly.getDegree();
        int n2 = 0;
        if (n == 1) {
            return new int[]{genericGFPoly.getCoefficient(1)};
        }
        int[] arrn = new int[n];
        for (int i = 1; i < this.field.getSize() && n2 < n; ++i) {
            int n3 = n2;
            if (genericGFPoly.evaluateAt(i) == 0) {
                arrn[n2] = this.field.inverse(i);
                n3 = n2 + 1;
            }
            n2 = n3;
        }
        if (n2 != n) {
            throw new ReedSolomonException("Error locator degree does not match number of roots");
        }
        return arrn;
    }

    private int[] findErrorMagnitudes(GenericGFPoly genericGFPoly, int[] arrn) {
        int n = arrn.length;
        int[] arrn2 = new int[n];
        for (int i = 0; i < n; ++i) {
            int n2 = this.field.inverse(arrn[i]);
            int n3 = 1;
            for (int j = 0; j < n; ++j) {
                int n4 = n3;
                if (i != j) {
                    n4 = this.field.multiply(arrn[j], n2);
                    n4 = (n4 & 1) == 0 ? (n4 |= 1) : (n4 &= -2);
                    n4 = this.field.multiply(n3, n4);
                }
                n3 = n4;
            }
            arrn2[i] = this.field.multiply(genericGFPoly.evaluateAt(n2), this.field.inverse(n3));
            if (this.field.getGeneratorBase() == 0) continue;
            arrn2[i] = this.field.multiply(arrn2[i], n2);
        }
        return arrn2;
    }

    private GenericGFPoly[] runEuclideanAlgorithm(GenericGFPoly genericGFPoly, GenericGFPoly genericGFPoly2, int n) throws ReedSolomonException {
        GenericGFPoly genericGFPoly3 = genericGFPoly;
        GenericGFPoly genericGFPoly4 = genericGFPoly2;
        if (genericGFPoly.getDegree() < genericGFPoly2.getDegree()) {
            genericGFPoly4 = genericGFPoly;
            genericGFPoly3 = genericGFPoly2;
        }
        GenericGFPoly genericGFPoly5 = this.field.getZero();
        GenericGFPoly genericGFPoly6 = this.field.getOne();
        genericGFPoly2 = genericGFPoly3;
        genericGFPoly = genericGFPoly4;
        genericGFPoly4 = genericGFPoly6;
        genericGFPoly3 = genericGFPoly5;
        while (genericGFPoly.getDegree() >= n / 2) {
            if (genericGFPoly.isZero()) {
                throw new ReedSolomonException("r_{i-1} was zero");
            }
            genericGFPoly5 = this.field.getZero();
            int n2 = genericGFPoly.getCoefficient(genericGFPoly.getDegree());
            n2 = this.field.inverse(n2);
            while (genericGFPoly2.getDegree() >= genericGFPoly.getDegree() && !genericGFPoly2.isZero()) {
                int n3 = genericGFPoly2.getDegree() - genericGFPoly.getDegree();
                int n4 = this.field.multiply(genericGFPoly2.getCoefficient(genericGFPoly2.getDegree()), n2);
                genericGFPoly5 = genericGFPoly5.addOrSubtract(this.field.buildMonomial(n3, n4));
                genericGFPoly2 = genericGFPoly2.addOrSubtract(genericGFPoly.multiplyByMonomial(n3, n4));
            }
            genericGFPoly3 = genericGFPoly5.multiply(genericGFPoly4).addOrSubtract(genericGFPoly3);
            if (genericGFPoly2.getDegree() >= genericGFPoly.getDegree()) {
                throw new IllegalStateException("Division algorithm failed to reduce polynomial?");
            }
            genericGFPoly5 = genericGFPoly;
            genericGFPoly = genericGFPoly2;
            genericGFPoly2 = genericGFPoly3;
            genericGFPoly3 = genericGFPoly4;
            genericGFPoly4 = genericGFPoly2;
            genericGFPoly2 = genericGFPoly5;
        }
        n = genericGFPoly4.getCoefficient(0);
        if (n == 0) {
            throw new ReedSolomonException("sigmaTilde(0) was zero");
        }
        n = this.field.inverse(n);
        return new GenericGFPoly[]{genericGFPoly4.multiply(n), genericGFPoly.multiply(n)};
    }

    public void decode(int[] arrn, int n) throws ReedSolomonException {
        int n2;
        int[] arrn2 = new GenericGFPoly(this.field, arrn);
        Object object = new int[n];
        int n3 = 0;
        boolean bl = true;
        for (n2 = 0; n2 < n; ++n2) {
            int n4;
            object[n - 1 - n2] = n4 = arrn2.evaluateAt(this.field.exp(this.field.getGeneratorBase() + n2));
            if (n4 == 0) continue;
            bl = false;
        }
        if (bl) {
            return;
        }
        arrn2 = new GenericGFPoly(this.field, (int[])object);
        object = this.runEuclideanAlgorithm(this.field.buildMonomial(n, 1), (GenericGFPoly)arrn2, n);
        arrn2 = (int[])object[0];
        object = object[1];
        arrn2 = this.findErrorLocations((GenericGFPoly)arrn2);
        object = this.findErrorMagnitudes((GenericGFPoly)object, arrn2);
        for (n = n3; n < arrn2.length; ++n) {
            n2 = arrn.length - 1 - this.field.log(arrn2[n]);
            if (n2 < 0) {
                throw new ReedSolomonException("Bad error location");
            }
            arrn[n2] = GenericGF.addOrSubtract(arrn[n2], object[n]);
        }
    }
}
