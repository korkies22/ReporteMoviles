/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Path
 *  android.util.Log
 */
package android.support.v4.graphics;

import android.graphics.Path;
import android.support.annotation.RestrictTo;
import android.support.v4.graphics.PathParser;
import android.util.Log;

public static class PathParser.PathDataNode {
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public float[] mParams;
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public char mType;

    PathParser.PathDataNode(char c, float[] arrf) {
        this.mType = c;
        this.mParams = arrf;
    }

    PathParser.PathDataNode(PathParser.PathDataNode pathDataNode) {
        this.mType = pathDataNode.mType;
        this.mParams = PathParser.copyOfRange(pathDataNode.mParams, 0, pathDataNode.mParams.length);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void addCommand(Path var0, float[] var1_1, char var2_2, char var3_3, float[] var4_4) {
        var11_5 = var1_1[0];
        var12_6 = var1_1[1];
        var13_7 = var1_1[2];
        var14_8 = var1_1[3];
        var10_9 = var1_1[4];
        var9_10 = var1_1[5];
        var5_11 = var11_5;
        var6_12 = var12_6;
        var7_13 = var13_7;
        var8_14 = var14_8;
        switch (var3_3) {
            default: {
                var8_14 = var14_8;
                var7_13 = var13_7;
                var6_12 = var12_6;
                var5_11 = var11_5;
                ** GOTO lbl23
            }
            case 'Z': 
            case 'z': {
                var0.close();
                var0.moveTo(var10_9, var9_10);
                var7_13 = var5_11 = var10_9;
                var8_14 = var6_12 = var9_10;
            }
lbl23: // 3 sources:
            case 'L': 
            case 'M': 
            case 'T': 
            case 'l': 
            case 'm': 
            case 't': {
                var15_15 = 2;
                break;
            }
            case 'Q': 
            case 'S': 
            case 'q': 
            case 's': {
                var15_15 = 4;
                var5_11 = var11_5;
                var6_12 = var12_6;
                var7_13 = var13_7;
                var8_14 = var14_8;
                break;
            }
            case 'H': 
            case 'V': 
            case 'h': 
            case 'v': {
                var15_15 = 1;
                var5_11 = var11_5;
                var6_12 = var12_6;
                var7_13 = var13_7;
                var8_14 = var14_8;
                break;
            }
            case 'C': 
            case 'c': {
                var15_15 = 6;
                ** break;
            }
            case 'A': 
            case 'a': {
                var15_15 = 7;
lbl45: // 2 sources:
                var8_14 = var14_8;
                var7_13 = var13_7;
                var6_12 = var12_6;
                var5_11 = var11_5;
            }
        }
        var11_5 = var6_12;
        var17_16 = 0;
        var16_17 = var2_2;
        var2_2 = var17_16;
        var6_12 = var5_11;
        var5_11 = var11_5;
        do {
            block42 : {
                block43 : {
                    if (var2_2 >= var4_4.length) {
                        var1_1[0] = var6_12;
                        var1_1[1] = var5_11;
                        var1_1[2] = var7_13;
                        var1_1[3] = var8_14;
                        var1_1[4] = var10_9;
                        var1_1[5] = var9_10;
                        return;
                    }
                    var12_6 = 0.0f;
                    var11_5 = 0.0f;
                    switch (var3_3) {
                        case 'v': {
                            var16_17 = var2_2 + 0;
                            var0.rLineTo(0.0f, var4_4[var16_17]);
                            var5_11 += var4_4[var16_17];
                            ** break;
                        }
                        case 't': {
                            if (var16_17 != 113 && var16_17 != 116 && var16_17 != 81 && var16_17 != 84) {
                                var8_14 = 0.0f;
                                var7_13 = var11_5;
                            } else {
                                var7_13 = var6_12 - var7_13;
                                var8_14 = var5_11 - var8_14;
                            }
                            var16_17 = var2_2 + 0;
                            var11_5 = var4_4[var16_17];
                            var17_16 = var2_2 + 1;
                            var0.rQuadTo(var7_13, var8_14, var11_5, var4_4[var17_16]);
                            var11_5 = var6_12 + var4_4[var16_17];
                            var12_6 = var5_11 + var4_4[var17_16];
                            var8_14 += var5_11;
                            var7_13 += var6_12;
                            var5_11 = var12_6;
                            var6_12 = var11_5;
                            ** break;
                        }
                        case 's': {
                            if (var16_17 != 99 && var16_17 != 115 && var16_17 != 67 && var16_17 != 83) {
                                var8_14 = 0.0f;
                                var7_13 = var12_6;
                            } else {
                                var8_14 = var5_11 - var8_14;
                                var7_13 = var6_12 - var7_13;
                            }
                            var16_17 = var2_2 + 0;
                            var11_5 = var4_4[var16_17];
                            var17_16 = var2_2 + 1;
                            var12_6 = var4_4[var17_16];
                            var18_18 = var2_2 + 2;
                            var13_7 = var4_4[var18_18];
                            var19_19 = var2_2 + 3;
                            var0.rCubicTo(var7_13, var8_14, var11_5, var12_6, var13_7, var4_4[var19_19]);
                            var7_13 = var4_4[var16_17] + var6_12;
                            var8_14 = var4_4[var17_16] + var5_11;
                            var6_12 += var4_4[var18_18];
                            var5_11 += var4_4[var19_19];
                            ** break;
                        }
                        case 'q': {
                            var16_17 = var2_2 + 0;
                            var7_13 = var4_4[var16_17];
                            var17_16 = var2_2 + 1;
                            var8_14 = var4_4[var17_16];
                            var18_18 = var2_2 + 2;
                            var11_5 = var4_4[var18_18];
                            var19_19 = var2_2 + 3;
                            var0.rQuadTo(var7_13, var8_14, var11_5, var4_4[var19_19]);
                            var7_13 = var4_4[var16_17] + var6_12;
                            var8_14 = var4_4[var17_16] + var5_11;
                            var6_12 += var4_4[var18_18];
                            var5_11 += var4_4[var19_19];
                            ** break;
                        }
                        case 'm': {
                            var16_17 = var2_2 + 0;
                            var6_12 += var4_4[var16_17];
                            var17_16 = var2_2 + 1;
                            var5_11 += var4_4[var17_16];
                            if (var2_2 > 0) {
                                var0.rLineTo(var4_4[var16_17], var4_4[var17_16]);
                                ** break;
                            }
                            var0.rMoveTo(var4_4[var16_17], var4_4[var17_16]);
                            var9_10 = var5_11;
                            var10_9 = var6_12;
                            ** break;
                        }
                        case 'l': {
                            var16_17 = var2_2 + 0;
                            var11_5 = var4_4[var16_17];
                            var17_16 = var2_2 + 1;
                            var0.rLineTo(var11_5, var4_4[var17_16]);
                            var6_12 += var4_4[var16_17];
                            var5_11 += var4_4[var17_16];
                            ** break;
                        }
                        case 'h': {
                            var16_17 = var2_2 + 0;
                            var0.rLineTo(var4_4[var16_17], 0.0f);
                            var6_12 += var4_4[var16_17];
                            ** break;
                        }
                        case 'c': {
                            var7_13 = var4_4[var2_2 + 0];
                            var8_14 = var4_4[var2_2 + 1];
                            var16_17 = var2_2 + 2;
                            var11_5 = var4_4[var16_17];
                            var17_16 = var2_2 + 3;
                            var12_6 = var4_4[var17_16];
                            var18_18 = var2_2 + 4;
                            var13_7 = var4_4[var18_18];
                            var19_19 = var2_2 + 5;
                            var0.rCubicTo(var7_13, var8_14, var11_5, var12_6, var13_7, var4_4[var19_19]);
                            var7_13 = var4_4[var16_17] + var6_12;
                            var8_14 = var4_4[var17_16] + var5_11;
                            var6_12 += var4_4[var18_18];
                            var5_11 += var4_4[var19_19];
                        }
lbl164: // 10 sources:
                        default: {
                            break block42;
                        }
                        case 'a': {
                            var16_17 = var2_2 + 5;
                            var7_13 = var4_4[var16_17];
                            var17_16 = var2_2 + 6;
                            var8_14 = var4_4[var17_16];
                            var11_5 = var4_4[var2_2 + 0];
                            var12_6 = var4_4[var2_2 + 1];
                            var13_7 = var4_4[var2_2 + 2];
                            var20_20 = var4_4[var2_2 + 3] != 0.0f;
                            var21_21 = var4_4[var2_2 + 4] != 0.0f;
                            PathParser.PathDataNode.drawArc(var0, var6_12, var5_11, var7_13 + var6_12, var8_14 + var5_11, var11_5, var12_6, var13_7, var20_20, var21_21);
                            var6_12 += var4_4[var16_17];
                            var5_11 += var4_4[var17_16];
                            break block43;
                        }
                        case 'V': {
                            var16_17 = var2_2 + 0;
                            var0.lineTo(var6_12, var4_4[var16_17]);
                            var5_11 = var4_4[var16_17];
                            break block42;
                        }
                        case 'T': {
                            var11_5 = var5_11;
                            var12_6 = var6_12;
                            var17_16 = var2_2;
                            if (var16_17 == 113 || var16_17 == 116 || var16_17 == 81) ** GOTO lbl193
                            var6_12 = var12_6;
                            var5_11 = var11_5;
                            if (var16_17 != 84) ** GOTO lbl195
lbl193: // 2 sources:
                            var5_11 = 2.0f * var11_5 - var8_14;
                            var6_12 = 2.0f * var12_6 - var7_13;
lbl195: // 2 sources:
                            var16_17 = var17_16 + 0;
                            var7_13 = var4_4[var16_17];
                            var0.quadTo(var6_12, var5_11, var7_13, var4_4[++var17_16]);
                            var11_5 = var4_4[var16_17];
                            var12_6 = var4_4[var17_16];
                            var7_13 = var6_12;
                            var8_14 = var5_11;
                            var5_11 = var12_6;
                            var6_12 = var11_5;
                            break block42;
                        }
                        case 'S': {
                            var17_16 = var2_2;
                            if (var16_17 != 99 && var16_17 != 115 && var16_17 != 67 && var16_17 != 83) {
                                var7_13 = var5_11;
                                var5_11 = var6_12;
                                var6_12 = var7_13;
                            } else {
                                var7_13 = 2.0f * var6_12 - var7_13;
                                var6_12 = 2.0f * var5_11 - var8_14;
                                var5_11 = var7_13;
                            }
                            var16_17 = var17_16 + 0;
                            var7_13 = var4_4[var16_17];
                            var18_18 = var17_16 + 1;
                            var8_14 = var4_4[var18_18];
                            var19_19 = var17_16 + 2;
                            var11_5 = var4_4[var19_19];
                            var0.cubicTo(var5_11, var6_12, var7_13, var8_14, var11_5, var4_4[var17_16 += 3]);
                            var8_14 = var4_4[var16_17];
                            var7_13 = var4_4[var18_18];
                            var6_12 = var4_4[var19_19];
                            var5_11 = var4_4[var17_16];
                            ** GOTO lbl240
                        }
                        case 'Q': {
                            var16_17 = var2_2;
                            var17_16 = var16_17 + 0;
                            var5_11 = var4_4[var17_16];
                            var18_18 = var16_17 + 1;
                            var6_12 = var4_4[var18_18];
                            var19_19 = var16_17 + 2;
                            var7_13 = var4_4[var19_19];
                            var0.quadTo(var5_11, var6_12, var7_13, var4_4[var16_17 += 3]);
                            var8_14 = var4_4[var17_16];
                            var7_13 = var4_4[var18_18];
                            var6_12 = var4_4[var19_19];
                            var5_11 = var4_4[var16_17];
lbl240: // 2 sources:
                            var11_5 = var7_13;
                            var7_13 = var8_14;
                            var8_14 = var11_5;
                            break block42;
                        }
                        case 'M': {
                            var16_17 = var2_2;
                            var17_16 = var16_17 + 0;
                            var6_12 = var4_4[var17_16];
                            var18_18 = var16_17 + 1;
                            var5_11 = var4_4[var18_18];
                            if (var16_17 > 0) {
                                var0.lineTo(var4_4[var17_16], var4_4[var18_18]);
                            } else {
                                var0.moveTo(var4_4[var17_16], var4_4[var18_18]);
                                var9_10 = var5_11;
                                var10_9 = var6_12;
                            }
                            break block42;
                        }
                        case 'L': {
                            var16_17 = var2_2;
                            var17_16 = var16_17 + 0;
                            var5_11 = var4_4[var17_16];
                            var0.lineTo(var5_11, var4_4[++var16_17]);
                            var6_12 = var4_4[var17_16];
                            var5_11 = var4_4[var16_17];
                            break block42;
                        }
                        case 'H': {
                            var16_17 = var2_2 + 0;
                            var0.lineTo(var4_4[var16_17], var5_11);
                            var6_12 = var4_4[var16_17];
                            break block42;
                        }
                        case 'C': {
                            var16_17 = var2_2;
                            var5_11 = var4_4[var16_17 + 0];
                            var6_12 = var4_4[var16_17 + 1];
                            var17_16 = var16_17 + 2;
                            var7_13 = var4_4[var17_16];
                            var18_18 = var16_17 + 3;
                            var8_14 = var4_4[var18_18];
                            var19_19 = var16_17 + 4;
                            var11_5 = var4_4[var19_19];
                            var0.cubicTo(var5_11, var6_12, var7_13, var8_14, var11_5, var4_4[var16_17 += 5]);
                            var6_12 = var4_4[var19_19];
                            var5_11 = var4_4[var16_17];
                            var7_13 = var4_4[var17_16];
                            var8_14 = var4_4[var18_18];
                            break block42;
                        }
                        case 'A': 
                    }
                    var16_17 = var2_2;
                    var17_16 = var16_17 + 5;
                    var7_13 = var4_4[var17_16];
                    var18_18 = var16_17 + 6;
                    var8_14 = var4_4[var18_18];
                    var11_5 = var4_4[var16_17 + 0];
                    var12_6 = var4_4[var16_17 + 1];
                    var13_7 = var4_4[var16_17 + 2];
                    var20_20 = var4_4[var16_17 + 3] != 0.0f;
                    var21_21 = var4_4[var16_17 + 4] != 0.0f;
                    PathParser.PathDataNode.drawArc(var0, var6_12, var5_11, var7_13, var8_14, var11_5, var12_6, var13_7, var20_20, var21_21);
                    var6_12 = var4_4[var17_16];
                    var5_11 = var4_4[var18_18];
                }
                var8_14 = var5_11;
                var7_13 = var6_12;
            }
            var2_2 += var15_15;
            var16_17 = var3_3;
        } while (true);
    }

    private static void arcToBezier(Path path, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
        int n = (int)Math.ceil(Math.abs(d9 * 4.0 / 3.141592653589793));
        double d10 = Math.cos(d7);
        double d11 = Math.sin(d7);
        double d12 = Math.cos(d8);
        double d13 = Math.sin(d8);
        d7 = - d3;
        double d14 = d7 * d10;
        double d15 = d4 * d11;
        double d16 = d4 * d10;
        double d17 = d9 / (double)n;
        int n2 = 0;
        double d18 = d13 * (d7 *= d11) + d12 * d16;
        d12 = d14 * d13 - d15 * d12;
        d13 = d6;
        d9 = d5;
        double d19 = d8;
        d4 = d11;
        d5 = d10;
        d8 = d7;
        d7 = d17;
        d6 = d16;
        do {
            d16 = d3;
            if (n2 >= n) break;
            d10 = d19 + d7;
            d17 = Math.sin(d10);
            double d20 = Math.cos(d10);
            d11 = d + d16 * d5 * d20 - d15 * d17;
            double d21 = d2 + d16 * d4 * d20 + d6 * d17;
            d16 = d14 * d17 - d15 * d20;
            d17 = d17 * d8 + d20 * d6;
            d19 = d10 - d19;
            d20 = Math.tan(d19 / 2.0);
            d19 = Math.sin(d19) * (Math.sqrt(4.0 + 3.0 * d20 * d20) - 1.0) / 3.0;
            path.rLineTo(0.0f, 0.0f);
            path.cubicTo((float)(d9 + d12 * d19), (float)(d13 + d18 * d19), (float)(d11 - d19 * d16), (float)(d21 - d19 * d17), (float)d11, (float)d21);
            ++n2;
            d13 = d21;
            d9 = d11;
            d18 = d17;
            d12 = d16;
            d19 = d10;
        } while (true);
    }

    private static void drawArc(Path path, float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean bl, boolean bl2) {
        double d = Math.toRadians(f7);
        double d2 = Math.cos(d);
        double d3 = Math.sin(d);
        double d4 = f;
        double d5 = f2;
        double d6 = f5;
        double d7 = (d4 * d2 + d5 * d3) / d6;
        double d8 = - f;
        double d9 = f6;
        double d10 = (d8 * d3 + d5 * d2) / d9;
        double d11 = f3;
        d8 = f4;
        double d12 = (d11 * d2 + d8 * d3) / d6;
        double d13 = ((double)(- f3) * d3 + d8 * d2) / d9;
        double d14 = d7 - d12;
        double d15 = d10 - d13;
        d11 = (d7 + d12) / 2.0;
        d8 = (d10 + d13) / 2.0;
        double d16 = d14 * d14 + d15 * d15;
        if (d16 == 0.0) {
            Log.w((String)PathParser.LOGTAG, (String)" Points are coincident");
            return;
        }
        double d17 = 1.0 / d16 - 0.25;
        if (d17 < 0.0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Points are too far apart ");
            stringBuilder.append(d16);
            Log.w((String)PathParser.LOGTAG, (String)stringBuilder.toString());
            float f8 = (float)(Math.sqrt(d16) / 1.99999);
            PathParser.PathDataNode.drawArc(path, f, f2, f3, f4, f5 * f8, f6 * f8, f7, bl, bl2);
            return;
        }
        d16 = Math.sqrt(d17);
        d14 *= d16;
        d15 = d16 * d15;
        if (bl == bl2) {
            d11 -= d15;
            d8 += d14;
        } else {
            d11 += d15;
            d8 -= d14;
        }
        d15 = Math.atan2(d10 - d8, d7 - d11);
        d10 = Math.atan2(d13 - d8, d12 - d11) - d15;
        bl = d10 >= 0.0;
        d7 = d10;
        if (bl2 != bl) {
            d7 = d10 > 0.0 ? d10 - 6.283185307179586 : d10 + 6.283185307179586;
        }
        PathParser.PathDataNode.arcToBezier(path, d11 * d2 - d8 * d3, (d11 *= d6) * d3 + (d8 *= d9) * d2, d6, d9, d4, d5, d, d15, d7);
    }

    public static void nodesToPath(PathParser.PathDataNode[] arrpathDataNode, Path path) {
        float[] arrf = new float[6];
        char c = 'm';
        for (int i = 0; i < arrpathDataNode.length; ++i) {
            PathParser.PathDataNode.addCommand(path, arrf, c, arrpathDataNode[i].mType, arrpathDataNode[i].mParams);
            c = arrpathDataNode[i].mType;
        }
    }

    public void interpolatePathDataNode(PathParser.PathDataNode pathDataNode, PathParser.PathDataNode pathDataNode2, float f) {
        for (int i = 0; i < pathDataNode.mParams.length; ++i) {
            this.mParams[i] = pathDataNode.mParams[i] * (1.0f - f) + pathDataNode2.mParams[i] * f;
        }
    }
}
