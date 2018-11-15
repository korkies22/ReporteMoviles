/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.pdf417.decoder.ec;

import com.google.zxing.pdf417.decoder.ec.ModulusGF;

final class ModulusPoly {
    private final int[] coefficients;
    private final ModulusGF field;

    ModulusPoly(ModulusGF modulusGF, int[] arrn) {
        if (arrn.length == 0) {
            throw new IllegalArgumentException();
        }
        this.field = modulusGF;
        int n = arrn.length;
        if (n > 1 && arrn[0] == 0) {
            int n2;
            for (n2 = 1; n2 < n && arrn[n2] == 0; ++n2) {
            }
            if (n2 == n) {
                this.coefficients = new int[]{0};
                return;
            }
            this.coefficients = new int[n - n2];
            System.arraycopy(arrn, n2, this.coefficients, 0, this.coefficients.length);
            return;
        }
        this.coefficients = arrn;
    }

    ModulusPoly add(ModulusPoly arrn) {
        if (!this.field.equals(arrn.field)) {
            throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
        }
        if (this.isZero()) {
            return arrn;
        }
        if (arrn.isZero()) {
            return this;
        }
        int[] arrn2 = this.coefficients;
        int[] arrn3 = arrn.coefficients;
        int[] arrn4 = arrn2;
        arrn = arrn3;
        if (arrn2.length > arrn3.length) {
            arrn4 = arrn3;
            arrn = arrn2;
        }
        arrn2 = new int[arrn.length];
        int n = arrn.length - arrn4.length;
        System.arraycopy(arrn, 0, arrn2, 0, n);
        for (int i = n; i < arrn.length; ++i) {
            arrn2[i] = this.field.add(arrn4[i - n], arrn[i]);
        }
        return new ModulusPoly(this.field, arrn2);
    }

    int evaluateAt(int n) {
        int n2;
        int n3 = 0;
        if (n == 0) {
            return this.getCoefficient(0);
        }
        if (n == 1) {
            int[] arrn = this.coefficients;
            int n4 = arrn.length;
            n2 = 0;
            for (n = n3; n < n4; ++n) {
                n3 = arrn[n];
                n2 = this.field.add(n2, n3);
            }
            return n2;
        }
        n3 = this.coefficients[0];
        int n5 = this.coefficients.length;
        for (n2 = 1; n2 < n5; ++n2) {
            n3 = this.field.add(this.field.multiply(n, n3), this.coefficients[n2]);
        }
        return n3;
    }

    int getCoefficient(int n) {
        return this.coefficients[this.coefficients.length - 1 - n];
    }

    int[] getCoefficients() {
        return this.coefficients;
    }

    int getDegree() {
        return this.coefficients.length - 1;
    }

    boolean isZero() {
        if (this.coefficients[0] == 0) {
            return true;
        }
        return false;
    }

    ModulusPoly multiply(int n) {
        if (n == 0) {
            return this.field.getZero();
        }
        if (n == 1) {
            return this;
        }
        int n2 = this.coefficients.length;
        int[] arrn = new int[n2];
        for (int i = 0; i < n2; ++i) {
            arrn[i] = this.field.multiply(this.coefficients[i], n);
        }
        return new ModulusPoly(this.field, arrn);
    }

    ModulusPoly multiply(ModulusPoly arrn) {
        if (!this.field.equals(arrn.field)) {
            throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
        }
        if (!this.isZero() && !arrn.isZero()) {
            int[] arrn2 = this.coefficients;
            int n = arrn2.length;
            arrn = arrn.coefficients;
            int n2 = arrn.length;
            int[] arrn3 = new int[n + n2 - 1];
            for (int i = 0; i < n; ++i) {
                int n3 = arrn2[i];
                for (int j = 0; j < n2; ++j) {
                    int n4 = i + j;
                    arrn3[n4] = this.field.add(arrn3[n4], this.field.multiply(n3, arrn[j]));
                }
            }
            return new ModulusPoly(this.field, arrn3);
        }
        return this.field.getZero();
    }

    ModulusPoly multiplyByMonomial(int n, int n2) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        if (n2 == 0) {
            return this.field.getZero();
        }
        int n3 = this.coefficients.length;
        int[] arrn = new int[n + n3];
        for (n = 0; n < n3; ++n) {
            arrn[n] = this.field.multiply(this.coefficients[n], n2);
        }
        return new ModulusPoly(this.field, arrn);
    }

    ModulusPoly negative() {
        int n = this.coefficients.length;
        int[] arrn = new int[n];
        for (int i = 0; i < n; ++i) {
            arrn[i] = this.field.subtract(0, this.coefficients[i]);
        }
        return new ModulusPoly(this.field, arrn);
    }

    ModulusPoly subtract(ModulusPoly modulusPoly) {
        if (!this.field.equals(modulusPoly.field)) {
            throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
        }
        if (modulusPoly.isZero()) {
            return this;
        }
        return this.add(modulusPoly.negative());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(8 * this.getDegree());
        for (int i = this.getDegree(); i >= 0; --i) {
            int n;
            int n2 = this.getCoefficient(i);
            if (n2 == 0) continue;
            if (n2 < 0) {
                stringBuilder.append(" - ");
                n = - n2;
            } else {
                n = n2;
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(" + ");
                    n = n2;
                }
            }
            if (i == 0 || n != 1) {
                stringBuilder.append(n);
            }
            if (i == 0) continue;
            if (i == 1) {
                stringBuilder.append('x');
                continue;
            }
            stringBuilder.append("x^");
            stringBuilder.append(i);
        }
        return stringBuilder.toString();
    }
}
