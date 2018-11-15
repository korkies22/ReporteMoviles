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
import android.util.Log;
import java.util.ArrayList;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class PathParser {
    private static final String LOGTAG = "PathParser";

    private static void addNode(ArrayList<PathDataNode> arrayList, char c, float[] arrf) {
        arrayList.add(new PathDataNode(c, arrf));
    }

    public static boolean canMorph(PathDataNode[] arrpathDataNode, PathDataNode[] arrpathDataNode2) {
        if (arrpathDataNode != null) {
            if (arrpathDataNode2 == null) {
                return false;
            }
            if (arrpathDataNode.length != arrpathDataNode2.length) {
                return false;
            }
            for (int i = 0; i < arrpathDataNode.length; ++i) {
                if (arrpathDataNode[i].mType == arrpathDataNode2[i].mType) {
                    if (arrpathDataNode[i].mParams.length == arrpathDataNode2[i].mParams.length) continue;
                    return false;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    static float[] copyOfRange(float[] arrf, int n, int n2) {
        if (n > n2) {
            throw new IllegalArgumentException();
        }
        int n3 = arrf.length;
        if (n >= 0 && n <= n3) {
            n3 = Math.min(n2 -= n, n3 - n);
            float[] arrf2 = new float[n2];
            System.arraycopy(arrf, n, arrf2, 0, n3);
            return arrf2;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static PathDataNode[] createNodesFromPathData(String string) {
        if (string == null) {
            return null;
        }
        ArrayList<PathDataNode> arrayList = new ArrayList<PathDataNode>();
        int n = 1;
        int n2 = 0;
        while (n < string.length()) {
            String string2 = string.substring(n2, n = PathParser.nextStart(string, n)).trim();
            if (string2.length() > 0) {
                float[] arrf = PathParser.getFloats(string2);
                PathParser.addNode(arrayList, string2.charAt(0), arrf);
            }
            n2 = n++;
        }
        if (n - n2 == 1 && n2 < string.length()) {
            PathParser.addNode(arrayList, string.charAt(n2), new float[0]);
        }
        return arrayList.toArray(new PathDataNode[arrayList.size()]);
    }

    public static Path createPathFromPathData(String string) {
        Path path = new Path();
        Object object = PathParser.createNodesFromPathData(string);
        if (object != null) {
            try {
                PathDataNode.nodesToPath((PathDataNode[])object, path);
                return path;
            }
            catch (RuntimeException runtimeException) {
                object = new StringBuilder();
                object.append("Error in parsing ");
                object.append(string);
                throw new RuntimeException(object.toString(), runtimeException);
            }
        }
        return null;
    }

    public static PathDataNode[] deepCopyNodes(PathDataNode[] arrpathDataNode) {
        if (arrpathDataNode == null) {
            return null;
        }
        PathDataNode[] arrpathDataNode2 = new PathDataNode[arrpathDataNode.length];
        for (int i = 0; i < arrpathDataNode.length; ++i) {
            arrpathDataNode2[i] = new PathDataNode(arrpathDataNode[i]);
        }
        return arrpathDataNode2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void extract(String var0, int var1_1, ExtractFloatResult var2_2) {
        var2_2.mEndWithNegOrDot = false;
        var5_6 = var3_5 = (var7_4 = '\u0000');
        var6_7 = var3_5;
        var3_5 = var7_4;
        for (var4_3 = var1_1; var4_3 < var0.length(); ++var4_3) {
            block8 : {
                block7 : {
                    var7_4 = var0.charAt(var4_3);
                    if (var7_4 == ' ') break block7;
                    if (var7_4 == 'E' || var7_4 == 'e') ** GOTO lbl25
                    switch (var7_4) {
                        default: {
                            ** GOTO lbl23
                        }
                        case '.': {
                            if (var6_7 != '\u0000') ** GOTO lbl17
                            var3_5 = '\u0000';
                            var6_7 = '\u0001';
                            break block8;
lbl17: // 1 sources:
                            var2_2.mEndWithNegOrDot = true;
                            break;
                        }
                        case '-': {
                            if (var4_3 != var1_1 && var3_5 == '\u0000') {
                                var2_2.mEndWithNegOrDot = true;
                                break;
                            }
lbl23: // 3 sources:
                            var3_5 = '\u0000';
                            break block8;
                        }
lbl25: // 1 sources:
                        var3_5 = '\u0001';
                        break block8;
                        case ',': 
                    }
                }
                var3_5 = '\u0000';
                var5_6 = '\u0001';
            }
            if (var5_6 != '\u0000') break;
        }
        var2_2.mEndPosition = var4_3;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static float[] getFloats(String var0) {
        block6 : {
            if (var0.charAt(0) == 'z') return new float[0];
            if (var0.charAt(0) == 'Z') {
                return new float[0];
            }
            try {
                var6_1 = new float[var0.length()];
                var7_3 = new ExtractFloatResult();
                var5_4 = var0.length();
                var1_5 = 1;
                var3_6 = 0;
lbl10: // 3 sources:
                if (var1_5 >= var5_4) return PathParser.copyOfRange(var6_1, 0, var3_6);
                PathParser.extract(var0, var1_5, (ExtractFloatResult)var7_3);
                var4_8 = var7_3.mEndPosition;
                var2_7 = var3_6;
                if (var1_5 < var4_8) {
                    var6_1[var3_6] = Float.parseFloat(var0.substring(var1_5, var4_8));
                    var2_7 = var3_6 + 1;
                }
                if (!var7_3.mEndWithNegOrDot) break block6;
                var1_5 = var4_8;
                var3_6 = var2_7;
                ** GOTO lbl10
            }
            catch (NumberFormatException var6_2) {
                var7_3 = new StringBuilder();
                var7_3.append("error in parsing \"");
                var7_3.append(var0);
                var7_3.append("\"");
                throw new RuntimeException(var7_3.toString(), var6_2);
            }
        }
        var1_5 = var4_8 + 1;
        var3_6 = var2_7;
        ** GOTO lbl10
    }

    private static int nextStart(String string, int n) {
        while (n < string.length()) {
            char c = string.charAt(n);
            if (((c - 65) * (c - 90) <= 0 || (c - 97) * (c - 122) <= 0) && c != 'e' && c != 'E') {
                return n;
            }
            ++n;
        }
        return n;
    }

    public static void updateNodes(PathDataNode[] arrpathDataNode, PathDataNode[] arrpathDataNode2) {
        for (int i = 0; i < arrpathDataNode2.length; ++i) {
            arrpathDataNode[i].mType = arrpathDataNode2[i].mType;
            for (int j = 0; j < arrpathDataNode2[i].mParams.length; ++j) {
                arrpathDataNode[i].mParams[j] = arrpathDataNode2[i].mParams[j];
            }
        }
    }

    private static class ExtractFloatResult {
        int mEndPosition;
        boolean mEndWithNegOrDot;

        ExtractFloatResult() {
        }
    }

    public static class PathDataNode {
        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        public float[] mParams;
        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        public char mType;

        PathDataNode(char c, float[] arrf) {
            this.mType = c;
            this.mParams = arrf;
        }

        PathDataNode(PathDataNode pathDataNode) {
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
                                PathDataNode.drawArc(var0, var6_12, var5_11, var7_13 + var6_12, var8_14 + var5_11, var11_5, var12_6, var13_7, var20_20, var21_21);
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
                        PathDataNode.drawArc(var0, var6_12, var5_11, var7_13, var8_14, var11_5, var12_6, var13_7, var20_20, var21_21);
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
                PathDataNode.drawArc(path, f, f2, f3, f4, f5 * f8, f6 * f8, f7, bl, bl2);
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
            PathDataNode.arcToBezier(path, d11 * d2 - d8 * d3, (d11 *= d6) * d3 + (d8 *= d9) * d2, d6, d9, d4, d5, d, d15, d7);
        }

        public static void nodesToPath(PathDataNode[] arrpathDataNode, Path path) {
            float[] arrf = new float[6];
            char c = 'm';
            for (int i = 0; i < arrpathDataNode.length; ++i) {
                PathDataNode.addCommand(path, arrf, c, arrpathDataNode[i].mType, arrpathDataNode[i].mParams);
                c = arrpathDataNode[i].mType;
            }
        }

        public void interpolatePathDataNode(PathDataNode pathDataNode, PathDataNode pathDataNode2, float f) {
            for (int i = 0; i < pathDataNode.mParams.length; ++i) {
                this.mParams[i] = pathDataNode.mParams[i] * (1.0f - f) + pathDataNode2.mParams[i] * f;
            }
        }
    }

}
