/*
 * Decompiled with CFR 0_134.
 */
package uk.co.jasonfry.android.tools.util;

public class AnimationUtil {
    public static int quadraticOutEase(float f, float f2, float f3, float f4) {
        return (int)((- f3) * f * ((f /= f4) - 2.0f) + f2);
    }
}
