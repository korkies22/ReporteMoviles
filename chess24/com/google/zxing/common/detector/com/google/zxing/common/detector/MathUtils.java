/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.common.detector;

public final class MathUtils {
    private MathUtils() {
    }

    public static float distance(float f, float f2, float f3, float f4) {
        return (float)Math.sqrt(f * (f -= f3) + f2 * (f2 -= f4));
    }

    public static float distance(int n, int n2, int n3, int n4) {
        return (float)Math.sqrt(n * (n -= n3) + n2 * (n2 -= n4));
    }

    public static int round(float f) {
        float f2 = f < 0.0f ? -0.5f : 0.5f;
        return (int)(f + f2);
    }

    public static int sum(int[] arrn) {
        int n = arrn.length;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            n2 += arrn[i];
        }
        return n2;
    }
}
