/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.detector.MathUtils;

public final class WhiteRectangleDetector {
    private static final int CORR = 1;
    private static final int INIT_SIZE = 10;
    private final int downInit;
    private final int height;
    private final BitMatrix image;
    private final int leftInit;
    private final int rightInit;
    private final int upInit;
    private final int width;

    public WhiteRectangleDetector(BitMatrix bitMatrix) throws NotFoundException {
        this(bitMatrix, 10, bitMatrix.getWidth() / 2, bitMatrix.getHeight() / 2);
    }

    public WhiteRectangleDetector(BitMatrix bitMatrix, int n, int n2, int n3) throws NotFoundException {
        this.image = bitMatrix;
        this.height = bitMatrix.getHeight();
        this.width = bitMatrix.getWidth();
        this.leftInit = n2 - (n /= 2);
        this.rightInit = n2 + n;
        this.upInit = n3 - n;
        this.downInit = n3 + n;
        if (this.upInit >= 0 && this.leftInit >= 0 && this.downInit < this.height && this.rightInit < this.width) {
            return;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private ResultPoint[] centerEdges(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) {
        float f = resultPoint.getX();
        float f2 = resultPoint.getY();
        float f3 = resultPoint2.getX();
        float f4 = resultPoint2.getY();
        float f5 = resultPoint3.getX();
        float f6 = resultPoint3.getY();
        float f7 = resultPoint4.getX();
        float f8 = resultPoint4.getY();
        if (f < (float)this.width / 2.0f) {
            return new ResultPoint[]{new ResultPoint(f7 - 1.0f, f8 + 1.0f), new ResultPoint(f3 + 1.0f, f4 + 1.0f), new ResultPoint(f5 - 1.0f, f6 - 1.0f), new ResultPoint(f + 1.0f, f2 - 1.0f)};
        }
        return new ResultPoint[]{new ResultPoint(f7 + 1.0f, f8 + 1.0f), new ResultPoint(f3 + 1.0f, f4 - 1.0f), new ResultPoint(f5 - 1.0f, f6 + 1.0f), new ResultPoint(f - 1.0f, f2 - 1.0f)};
    }

    private boolean containsBlackPoint(int n, int n2, int n3, boolean bl) {
        if (bl) {
            while (n <= n2) {
                if (this.image.get(n, n3)) {
                    return true;
                }
                ++n;
            }
        } else {
            for (int i = n; i <= n2; ++i) {
                if (!this.image.get(n3, i)) continue;
                return true;
            }
        }
        return false;
    }

    private ResultPoint getBlackPointOnSegment(float f, float f2, float f3, float f4) {
        int n = MathUtils.round(MathUtils.distance(f, f2, f3, f4));
        float f5 = n;
        f3 = (f3 - f) / f5;
        f4 = (f4 - f2) / f5;
        for (int i = 0; i < n; ++i) {
            int n2;
            f5 = i;
            int n3 = MathUtils.round(f5 * f3 + f);
            if (!this.image.get(n3, n2 = MathUtils.round(f5 * f4 + f2))) continue;
            return new ResultPoint(n3, n2);
        }
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public ResultPoint[] detect() throws NotFoundException {
        var4_1 = this.leftInit;
        var5_2 = this.rightInit;
        var9_3 = this.upInit;
        var11_4 = this.downInit;
        var17_5 = false;
        var16_6 = 1;
        var7_11 = var3_10 = (var2_9 = (var1_8 = (var12_7 = 0)));
        var13_12 = 1;
        var6_13 = var3_10;
        var8_14 = var2_9;
        var10_15 = var1_8;
        var2_9 = var4_1;
        var4_1 = var11_4;
        var1_8 = var9_3;
        var3_10 = var5_2;
        do {
            var5_2 = var3_10;
            var11_4 = var1_8;
            var9_3 = var4_1;
            var15_17 = var17_5;
            var14_16 = var2_9;
            if (var13_12 == 0) ** GOTO lbl47
            var5_2 = 0;
            var18_18 = true;
            var9_3 = var12_7;
            while ((var18_18 || var9_3 == 0) && var3_10 < this.width) {
                var19_19 = this.containsBlackPoint(var1_8, var4_1, var3_10, false);
                if (var19_19) {
                    ++var3_10;
                    var5_2 = var9_3 = 1;
                    var18_18 = var19_19;
                    continue;
                }
                var18_18 = var19_19;
                if (var9_3 != 0) continue;
                ++var3_10;
                var18_18 = var19_19;
            }
            if (var3_10 < this.width) {
                var18_18 = true;
            } else lbl-1000: // 4 sources:
            {
                do {
                    var15_17 = true;
                    var5_2 = var3_10;
                    var11_4 = var1_8;
                    var9_3 = var4_1;
                    var14_16 = var2_9;
lbl47: // 2 sources:
                    if (var15_17 != false) throw NotFoundException.getNotFoundInstance();
                    if (var6_13 == 0) throw NotFoundException.getNotFoundInstance();
                    var2_9 = var5_2 - var14_16;
                    var23_20 = null;
                    var20_21 = null;
                    for (var1_8 = 1; var20_21 == null && var1_8 < var2_9; ++var1_8) {
                        var20_21 = this.getBlackPointOnSegment(var14_16, var9_3 - var1_8, var14_16 + var1_8, var9_3);
                    }
                    if (var20_21 == null) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    var21_22 = null;
                    for (var1_8 = 1; var21_22 == null && var1_8 < var2_9; ++var1_8) {
                        var21_22 = this.getBlackPointOnSegment(var14_16, var11_4 + var1_8, var14_16 + var1_8, var11_4);
                    }
                    if (var21_22 == null) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    var22_23 = null;
                    for (var1_8 = 1; var22_23 == null && var1_8 < var2_9; ++var1_8) {
                        var22_23 = this.getBlackPointOnSegment(var5_2, var11_4 + var1_8, var5_2 - var1_8, var11_4);
                    }
                    if (var22_23 == null) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    for (var1_8 = var16_6; var23_20 == null && var1_8 < var2_9; ++var1_8) {
                        var23_20 = this.getBlackPointOnSegment(var5_2, var9_3 - var1_8, var5_2 - var1_8, var9_3);
                    }
                    if (var23_20 != null) return this.centerEdges(var23_20, var20_21, var22_23, var21_22);
                    throw NotFoundException.getNotFoundInstance();
                    break;
                } while (true);
            }
            while ((var18_18 || var10_15 == 0) && var4_1 < this.height) {
                var19_19 = this.containsBlackPoint(var2_9, var3_10, var4_1, true);
                if (var19_19) {
                    ++var4_1;
                    var5_2 = var10_15 = 1;
                    var18_18 = var19_19;
                    continue;
                }
                var18_18 = var19_19;
                if (var10_15 != 0) continue;
                ++var4_1;
                var18_18 = var19_19;
            }
            if (var4_1 >= this.height) ** GOTO lbl-1000
            var18_18 = true;
            while ((var18_18 || var8_14 == 0) && var2_9 >= 0) {
                var19_19 = this.containsBlackPoint(var1_8, var4_1, var2_9, false);
                if (var19_19) {
                    --var2_9;
                    var5_2 = var8_14 = 1;
                    var18_18 = var19_19;
                    continue;
                }
                var18_18 = var19_19;
                if (var8_14 != 0) continue;
                --var2_9;
                var18_18 = var19_19;
            }
            if (var2_9 < 0) ** GOTO lbl-1000
            var18_18 = true;
            while ((var18_18 || var7_11 == 0) && var1_8 >= 0) {
                var19_19 = this.containsBlackPoint(var2_9, var3_10, var1_8, true);
                if (var19_19) {
                    --var1_8;
                    var5_2 = var7_11 = 1;
                    var18_18 = var19_19;
                    continue;
                }
                var18_18 = var19_19;
                if (var7_11 != 0) continue;
                --var1_8;
                var18_18 = var19_19;
            }
            if (var1_8 < 0) ** continue;
            if (var5_2 != 0) {
                var6_13 = 1;
            }
            var13_12 = var5_2;
            var12_7 = var9_3;
        } while (true);
    }
}
