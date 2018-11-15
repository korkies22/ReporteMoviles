/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.common;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DefaultGridSampler;
import com.google.zxing.common.PerspectiveTransform;

public abstract class GridSampler {
    private static GridSampler gridSampler = new DefaultGridSampler();

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected static void checkAndNudgePoints(BitMatrix var0, float[] var1_1) throws NotFoundException {
        var4_2 = var0.getWidth();
        var5_3 = var0.getHeight();
        var2_5 = 1;
        for (var3_4 = 0; var3_4 < var1_1.length && var2_5 != 0; var3_4 += 2) {
            block13 : {
                var2_5 = (int)var1_1[var3_4];
                var6_6 = var3_4 + 1;
                var7_7 = (int)var1_1[var6_6];
                if (var2_5 < -1) throw NotFoundException.getNotFoundInstance();
                if (var2_5 > var4_2) throw NotFoundException.getNotFoundInstance();
                if (var7_7 < -1) throw NotFoundException.getNotFoundInstance();
                if (var7_7 > var5_3) {
                    throw NotFoundException.getNotFoundInstance();
                }
                if (var2_5 != -1) break block13;
                var1_1[var3_4] = 0.0f;
                ** GOTO lbl19
            }
            if (var2_5 == var4_2) {
                var1_1[var3_4] = var4_2 - 1;
lbl19: // 2 sources:
                var2_5 = 1;
            } else {
                var2_5 = 0;
            }
            if (var7_7 == -1) {
                var1_1[var6_6] = 0.0f;
            } else {
                if (var7_7 != var5_3) continue;
                var1_1[var6_6] = var5_3 - 1;
            }
            var2_5 = 1;
        }
        var3_4 = var1_1.length - 2;
        var2_5 = 1;
        while (var3_4 >= 0) {
            block17 : {
                block16 : {
                    block15 : {
                        block14 : {
                            if (var2_5 == 0) return;
                            var2_5 = (int)var1_1[var3_4];
                            var6_6 = var3_4 + 1;
                            var7_7 = (int)var1_1[var6_6];
                            if (var2_5 < -1) throw NotFoundException.getNotFoundInstance();
                            if (var2_5 > var4_2) throw NotFoundException.getNotFoundInstance();
                            if (var7_7 < -1) throw NotFoundException.getNotFoundInstance();
                            if (var7_7 > var5_3) {
                                throw NotFoundException.getNotFoundInstance();
                            }
                            if (var2_5 != -1) break block14;
                            var1_1[var3_4] = 0.0f;
                            ** GOTO lbl47
                        }
                        if (var2_5 == var4_2) {
                            var1_1[var3_4] = var4_2 - 1;
lbl47: // 2 sources:
                            var2_5 = 1;
                        } else {
                            var2_5 = 0;
                        }
                        if (var7_7 != -1) break block15;
                        var1_1[var6_6] = 0.0f;
                        break block16;
                    }
                    if (var7_7 != var5_3) break block17;
                    var1_1[var6_6] = var5_3 - 1;
                }
                var2_5 = 1;
            }
            var3_4 -= 2;
        }
    }

    public static GridSampler getInstance() {
        return gridSampler;
    }

    public static void setGridSampler(GridSampler gridSampler) {
        GridSampler.gridSampler = gridSampler;
    }

    public abstract BitMatrix sampleGrid(BitMatrix var1, int var2, int var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, float var14, float var15, float var16, float var17, float var18, float var19) throws NotFoundException;

    public abstract BitMatrix sampleGrid(BitMatrix var1, int var2, int var3, PerspectiveTransform var4) throws NotFoundException;
}
