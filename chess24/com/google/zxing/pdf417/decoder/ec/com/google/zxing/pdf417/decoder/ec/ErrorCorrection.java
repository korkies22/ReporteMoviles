/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.pdf417.decoder.ec;

import com.google.zxing.ChecksumException;
import com.google.zxing.pdf417.decoder.ec.ModulusGF;
import com.google.zxing.pdf417.decoder.ec.ModulusPoly;

public final class ErrorCorrection {
    private final ModulusGF field = ModulusGF.PDF417_GF;

    private int[] findErrorLocations(ModulusPoly modulusPoly) throws ChecksumException {
        int n = modulusPoly.getDegree();
        int[] arrn = new int[n];
        int n2 = 0;
        for (int i = 1; i < this.field.getSize() && n2 < n; ++i) {
            int n3 = n2;
            if (modulusPoly.evaluateAt(i) == 0) {
                arrn[n2] = this.field.inverse(i);
                n3 = n2 + 1;
            }
            n2 = n3;
        }
        if (n2 != n) {
            throw ChecksumException.getChecksumInstance();
        }
        return arrn;
    }

    private int[] findErrorMagnitudes(ModulusPoly modulusPoly, ModulusPoly modulusPoly2, int[] arrn) {
        int n;
        int n2 = modulusPoly2.getDegree();
        int[] arrn2 = new int[n2];
        for (n = 1; n <= n2; ++n) {
            arrn2[n2 - n] = this.field.multiply(n, modulusPoly2.getCoefficient(n));
        }
        modulusPoly2 = new ModulusPoly(this.field, arrn2);
        n2 = arrn.length;
        arrn2 = new int[n2];
        for (n = 0; n < n2; ++n) {
            int n3 = this.field.inverse(arrn[n]);
            int n4 = this.field.subtract(0, modulusPoly.evaluateAt(n3));
            n3 = this.field.inverse(modulusPoly2.evaluateAt(n3));
            arrn2[n] = this.field.multiply(n4, n3);
        }
        return arrn2;
    }

    private ModulusPoly[] runEuclideanAlgorithm(ModulusPoly modulusPoly, ModulusPoly modulusPoly2, int n) throws ChecksumException {
        ModulusPoly modulusPoly3 = modulusPoly;
        ModulusPoly modulusPoly4 = modulusPoly2;
        if (modulusPoly.getDegree() < modulusPoly2.getDegree()) {
            modulusPoly4 = modulusPoly;
            modulusPoly3 = modulusPoly2;
        }
        ModulusPoly modulusPoly5 = this.field.getZero();
        ModulusPoly modulusPoly6 = this.field.getOne();
        modulusPoly2 = modulusPoly3;
        modulusPoly = modulusPoly4;
        modulusPoly4 = modulusPoly6;
        modulusPoly3 = modulusPoly5;
        while (modulusPoly.getDegree() >= n / 2) {
            if (modulusPoly.isZero()) {
                throw ChecksumException.getChecksumInstance();
            }
            modulusPoly5 = this.field.getZero();
            int n2 = modulusPoly.getCoefficient(modulusPoly.getDegree());
            n2 = this.field.inverse(n2);
            while (modulusPoly2.getDegree() >= modulusPoly.getDegree() && !modulusPoly2.isZero()) {
                int n3 = modulusPoly2.getDegree() - modulusPoly.getDegree();
                int n4 = this.field.multiply(modulusPoly2.getCoefficient(modulusPoly2.getDegree()), n2);
                modulusPoly5 = modulusPoly5.add(this.field.buildMonomial(n3, n4));
                modulusPoly2 = modulusPoly2.subtract(modulusPoly.multiplyByMonomial(n3, n4));
            }
            modulusPoly3 = modulusPoly5.multiply(modulusPoly4).subtract(modulusPoly3).negative();
            modulusPoly5 = modulusPoly;
            modulusPoly = modulusPoly2;
            modulusPoly2 = modulusPoly3;
            modulusPoly3 = modulusPoly4;
            modulusPoly4 = modulusPoly2;
            modulusPoly2 = modulusPoly5;
        }
        n = modulusPoly4.getCoefficient(0);
        if (n == 0) {
            throw ChecksumException.getChecksumInstance();
        }
        n = this.field.inverse(n);
        return new ModulusPoly[]{modulusPoly4.multiply(n), modulusPoly.multiply(n)};
    }

    public int decode(int[] arrn, int n, int[] object) throws ChecksumException {
        int n2;
        Object object2 = new ModulusPoly[](this.field, arrn);
        Object object3 = new int[n];
        int n3 = 0;
        int n4 = 0;
        for (n2 = n; n2 > 0; --n2) {
            int n5;
            object3[n - n2] = n5 = object2.evaluateAt(this.field.exp(n2));
            if (n5 == 0) continue;
            n4 = 1;
        }
        if (n4 == 0) {
            return 0;
        }
        object2 = this.field.getOne();
        if (object != null) {
            for (int n5 : object) {
                n5 = this.field.exp(arrn.length - 1 - n5);
                object2 = object2.multiply(new ModulusPoly(this.field, new int[]{this.field.subtract(0, n5), 1}));
            }
        }
        object = new ModulusPoly(this.field, (int[])object3);
        object2 = this.runEuclideanAlgorithm(this.field.buildMonomial(n, 1), (ModulusPoly)object, n);
        object = object2[0];
        object3 = object2[1];
        object2 = this.findErrorLocations((ModulusPoly)object);
        object = this.findErrorMagnitudes((ModulusPoly)object3, (ModulusPoly)object, (int[])object2);
        for (n = n3; n < ((ModulusPoly[])object2).length; ++n) {
            n2 = arrn.length - 1 - this.field.log((int)object2[n]);
            if (n2 < 0) {
                throw ChecksumException.getChecksumInstance();
            }
            arrn[n2] = this.field.subtract(arrn[n2], object[n]);
        }
        return ((ModulusPoly[])object2).length;
    }
}
