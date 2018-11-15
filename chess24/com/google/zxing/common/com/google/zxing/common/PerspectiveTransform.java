/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.common;

public final class PerspectiveTransform {
    private final float a11;
    private final float a12;
    private final float a13;
    private final float a21;
    private final float a22;
    private final float a23;
    private final float a31;
    private final float a32;
    private final float a33;

    private PerspectiveTransform(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        this.a11 = f;
        this.a12 = f4;
        this.a13 = f7;
        this.a21 = f2;
        this.a22 = f5;
        this.a23 = f8;
        this.a31 = f3;
        this.a32 = f6;
        this.a33 = f9;
    }

    public static PerspectiveTransform quadrilateralToQuadrilateral(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16) {
        PerspectiveTransform perspectiveTransform = PerspectiveTransform.quadrilateralToSquare(f, f2, f3, f4, f5, f6, f7, f8);
        return PerspectiveTransform.squareToQuadrilateral(f9, f10, f11, f12, f13, f14, f15, f16).times(perspectiveTransform);
    }

    public static PerspectiveTransform quadrilateralToSquare(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        return PerspectiveTransform.squareToQuadrilateral(f, f2, f3, f4, f5, f6, f7, f8).buildAdjoint();
    }

    public static PerspectiveTransform squareToQuadrilateral(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        float f9 = f - f3 + f5 - f7;
        float f10 = f2 - f4 + f6 - f8;
        if (f9 == 0.0f && f10 == 0.0f) {
            return new PerspectiveTransform(f3 - f, f5 - f3, f, f4 - f2, f6 - f4, f2, 0.0f, 0.0f, 1.0f);
        }
        float f11 = f3 - f5;
        float f12 = f7 - f5;
        f5 = f4 - f6;
        float f13 = f8 - f6;
        f6 = f11 * f13 - f12 * f5;
        f12 = (f13 * f9 - f12 * f10) / f6;
        f5 = (f11 * f10 - f9 * f5) / f6;
        return new PerspectiveTransform(f3 - f + f12 * f3, f5 * f7 + (f7 - f), f, f4 - f2 + f12 * f4, f8 - f2 + f5 * f8, f2, f12, f5, 1.0f);
    }

    PerspectiveTransform buildAdjoint() {
        return new PerspectiveTransform(this.a22 * this.a33 - this.a23 * this.a32, this.a23 * this.a31 - this.a21 * this.a33, this.a21 * this.a32 - this.a22 * this.a31, this.a13 * this.a32 - this.a12 * this.a33, this.a11 * this.a33 - this.a13 * this.a31, this.a12 * this.a31 - this.a11 * this.a32, this.a12 * this.a23 - this.a13 * this.a22, this.a13 * this.a21 - this.a11 * this.a23, this.a11 * this.a22 - this.a12 * this.a21);
    }

    PerspectiveTransform times(PerspectiveTransform perspectiveTransform) {
        float f = this.a11;
        float f2 = perspectiveTransform.a11;
        float f3 = this.a21;
        float f4 = perspectiveTransform.a12;
        float f5 = this.a31;
        float f6 = perspectiveTransform.a13;
        float f7 = this.a11;
        float f8 = perspectiveTransform.a21;
        float f9 = this.a21;
        float f10 = perspectiveTransform.a22;
        float f11 = this.a31;
        float f12 = perspectiveTransform.a23;
        float f13 = this.a11;
        float f14 = perspectiveTransform.a31;
        float f15 = this.a21;
        float f16 = perspectiveTransform.a32;
        float f17 = this.a31;
        float f18 = perspectiveTransform.a33;
        float f19 = this.a12;
        float f20 = perspectiveTransform.a11;
        float f21 = this.a22;
        float f22 = perspectiveTransform.a12;
        float f23 = this.a32;
        float f24 = perspectiveTransform.a13;
        float f25 = this.a12;
        float f26 = perspectiveTransform.a21;
        float f27 = this.a22;
        float f28 = perspectiveTransform.a22;
        float f29 = this.a32;
        float f30 = perspectiveTransform.a23;
        float f31 = this.a12;
        float f32 = perspectiveTransform.a31;
        float f33 = this.a22;
        float f34 = perspectiveTransform.a32;
        float f35 = this.a32;
        float f36 = perspectiveTransform.a33;
        float f37 = this.a13;
        float f38 = perspectiveTransform.a11;
        float f39 = this.a23;
        float f40 = perspectiveTransform.a12;
        float f41 = this.a33;
        float f42 = perspectiveTransform.a13;
        float f43 = this.a13;
        float f44 = perspectiveTransform.a21;
        float f45 = this.a23;
        float f46 = perspectiveTransform.a22;
        float f47 = this.a33;
        float f48 = perspectiveTransform.a23;
        float f49 = this.a13;
        float f50 = perspectiveTransform.a31;
        float f51 = this.a23;
        float f52 = perspectiveTransform.a32;
        return new PerspectiveTransform(f5 * f6 + (f * f2 + f3 * f4), f11 * f12 + (f7 * f8 + f9 * f10), f17 * f18 + (f13 * f14 + f15 * f16), f23 * f24 + (f19 * f20 + f21 * f22), f29 * f30 + (f25 * f26 + f27 * f28), f35 * f36 + (f31 * f32 + f33 * f34), f41 * f42 + (f37 * f38 + f39 * f40), f47 * f48 + (f43 * f44 + f45 * f46), this.a33 * perspectiveTransform.a33 + (f49 * f50 + f51 * f52));
    }

    public void transformPoints(float[] arrf) {
        int n = arrf.length;
        float f = this.a11;
        float f2 = this.a12;
        float f3 = this.a13;
        float f4 = this.a21;
        float f5 = this.a22;
        float f6 = this.a23;
        float f7 = this.a31;
        float f8 = this.a32;
        float f9 = this.a33;
        for (int i = 0; i < n; i += 2) {
            float f10 = arrf[i];
            int n2 = i + 1;
            float f11 = arrf[n2];
            float f12 = f3 * f10 + f6 * f11 + f9;
            arrf[i] = (f * f10 + f4 * f11 + f7) / f12;
            arrf[n2] = (f10 * f2 + f11 * f5 + f8) / f12;
        }
    }

    public void transformPoints(float[] arrf, float[] arrf2) {
        int n = arrf.length;
        for (int i = 0; i < n; ++i) {
            float f = arrf[i];
            float f2 = arrf2[i];
            float f3 = this.a13 * f + this.a23 * f2 + this.a33;
            arrf[i] = (this.a11 * f + this.a21 * f2 + this.a31) / f3;
            arrf2[i] = (this.a12 * f + this.a22 * f2 + this.a32) / f3;
        }
    }
}
