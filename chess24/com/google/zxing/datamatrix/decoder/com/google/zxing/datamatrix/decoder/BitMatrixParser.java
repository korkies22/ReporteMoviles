/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.decoder.Version;

final class BitMatrixParser {
    private final BitMatrix mappingBitMatrix;
    private final BitMatrix readMappingMatrix;
    private final Version version;

    BitMatrixParser(BitMatrix bitMatrix) throws FormatException {
        int n = bitMatrix.getHeight();
        if (n >= 8 && n <= 144 && (n & 1) == 0) {
            this.version = BitMatrixParser.readVersion(bitMatrix);
            this.mappingBitMatrix = this.extractDataRegion(bitMatrix);
            this.readMappingMatrix = new BitMatrix(this.mappingBitMatrix.getWidth(), this.mappingBitMatrix.getHeight());
            return;
        }
        throw FormatException.getFormatInstance();
    }

    private BitMatrix extractDataRegion(BitMatrix bitMatrix) {
        int n = this.version.getSymbolSizeRows();
        int n2 = this.version.getSymbolSizeColumns();
        if (bitMatrix.getHeight() != n) {
            throw new IllegalArgumentException("Dimension of bitMarix must match the version size");
        }
        int n3 = this.version.getDataRegionSizeRows();
        int n4 = this.version.getDataRegionSizeColumns();
        int n5 = n / n3;
        int n6 = n2 / n4;
        BitMatrix bitMatrix2 = new BitMatrix(n6 * n4, n5 * n3);
        for (n2 = 0; n2 < n5; ++n2) {
            for (n = 0; n < n6; ++n) {
                for (int i = 0; i < n3; ++i) {
                    for (int j = 0; j < n4; ++j) {
                        if (!bitMatrix.get((n4 + 2) * n + 1 + j, (n3 + 2) * n2 + 1 + i)) continue;
                        bitMatrix2.set(n * n4 + j, n2 * n3 + i);
                    }
                }
            }
        }
        return bitMatrix2;
    }

    private int readCorner1(int n, int n2) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:629)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:716)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:820)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    private int readCorner2(int n, int n2) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:629)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:716)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:820)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    private int readCorner3(int n, int n2) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:629)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:716)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:820)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    private int readCorner4(int n, int n2) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:629)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:716)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:820)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    private boolean readModule(int n, int n2, int n3, int n4) {
        int n5 = n;
        int n6 = n2;
        if (n < 0) {
            n5 = n + n3;
            n6 = n2 + (4 - (n3 + 4 & 7));
        }
        n2 = n5;
        n = n6;
        if (n6 < 0) {
            n = n6 + n4;
            n2 = n5 + (4 - (n4 + 4 & 7));
        }
        this.readMappingMatrix.set(n, n2);
        return this.mappingBitMatrix.get(n, n2);
    }

    private int readUtah(int n, int n2, int n3, int n4) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:629)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:716)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:820)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    private static Version readVersion(BitMatrix bitMatrix) throws FormatException {
        return Version.getVersionForDimensions(bitMatrix.getHeight(), bitMatrix.getWidth());
    }

    Version getVersion() {
        return this.version;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    byte[] readCodewords() throws FormatException {
        var17_1 = new byte[this.version.getTotalCodewords()];
        var15_2 = this.mappingBitMatrix.getHeight();
        var16_3 = this.mappingBitMatrix.getWidth();
        var7_9 = var3_8 = (var2_7 = (var4_6 = (var1_5 = (var5_4 = 0))));
        var6_10 = 4;
        var8_11 = var3_8;
        var9_12 = var2_7;
        var10_13 = var1_5;
        do lbl-1000: // 3 sources:
        {
            block12 : {
                block14 : {
                    block13 : {
                        block11 : {
                            if (var6_10 != var15_2 || var5_4 != 0 || var10_13 != 0) break block11;
                            var17_1[var4_6] = (byte)this.readCorner1(var15_2, var16_3);
                            var1_5 = var6_10 - 2;
                            var2_7 = var5_4 + 2;
                            var3_8 = var4_6 + 1;
                            var11_14 = 1;
                            var12_15 = var9_12;
                            var13_16 = var8_11;
                            var14_17 = var7_9;
                            break block12;
                        }
                        var1_5 = var15_2 - 2;
                        if (var6_10 != var1_5 || var5_4 != 0 || (var16_3 & 3) == 0 || var9_12 != 0) break block13;
                        var17_1[var4_6] = (byte)this.readCorner2(var15_2, var16_3);
                        var1_5 = var6_10 - 2;
                        var2_7 = var5_4 + 2;
                        var3_8 = var4_6 + 1;
                        var12_15 = 1;
                        var11_14 = var10_13;
                        var13_16 = var8_11;
                        var14_17 = var7_9;
                        break block12;
                    }
                    if (var6_10 != var15_2 + 4 || var5_4 != 2 || (var16_3 & 7) != 0 || var8_11 != 0) break block14;
                    var17_1[var4_6] = (byte)this.readCorner3(var15_2, var16_3);
                    var1_5 = var6_10 - 2;
                    var2_7 = var5_4 + 2;
                    var3_8 = var4_6 + 1;
                    var13_16 = 1;
                    var11_14 = var10_13;
                    var12_15 = var9_12;
                    var14_17 = var7_9;
                    break block12;
                }
                var2_7 = var6_10;
                var3_8 = var5_4;
                var11_14 = var4_6;
                if (var6_10 != var1_5) ** GOTO lbl-1000
                var2_7 = var6_10;
                var3_8 = var5_4;
                var11_14 = var4_6;
                if (var5_4 != 0) ** GOTO lbl-1000
                var2_7 = var6_10;
                var3_8 = var5_4;
                var11_14 = var4_6;
                if ((var16_3 & 7) != 4) ** GOTO lbl-1000
                var2_7 = var6_10;
                var3_8 = var5_4;
                var11_14 = var4_6;
                if (var7_9 == 0) {
                    var17_1[var4_6] = (byte)this.readCorner4(var15_2, var16_3);
                    var1_5 = var6_10 - 2;
                    var2_7 = var5_4 + 2;
                    var3_8 = var4_6 + 1;
                    var14_17 = 1;
                    var11_14 = var10_13;
                    var12_15 = var9_12;
                    var13_16 = var8_11;
                } else lbl-1000: // 4 sources:
                {
                    do {
                        var1_5 = var11_14;
                        if (var2_7 < var15_2) {
                            var1_5 = var11_14;
                            if (var3_8 >= 0) {
                                var1_5 = var11_14;
                                if (!this.readMappingMatrix.get(var3_8, var2_7)) {
                                    var17_1[var11_14] = (byte)this.readUtah(var2_7, var3_8, var15_2, var16_3);
                                    var1_5 = var11_14 + 1;
                                }
                            }
                        }
                        var5_4 = var2_7 - 2;
                        var4_6 = var3_8 + 2;
                        if (var5_4 < 0) break;
                        var2_7 = var5_4;
                        var3_8 = var4_6;
                        var11_14 = var1_5;
                    } while (var4_6 < var16_3);
                    var3_8 = var5_4 + 1;
                    var2_7 = var1_5;
                    var1_5 = var4_6 += 3;
                    var4_6 = var3_8;
                    do {
                        var3_8 = var2_7;
                        if (var4_6 >= 0) {
                            var3_8 = var2_7;
                            if (var1_5 < var16_3) {
                                var3_8 = var2_7;
                                if (!this.readMappingMatrix.get(var1_5, var4_6)) {
                                    var17_1[var2_7] = (byte)this.readUtah(var4_6, var1_5, var15_2, var16_3);
                                    var3_8 = var2_7 + 1;
                                }
                            }
                        }
                        var6_10 = var4_6 + 2;
                        var5_4 = var1_5 - 2;
                        if (var6_10 >= var15_2) break;
                        var4_6 = var6_10;
                        var1_5 = var5_4;
                        var2_7 = var3_8;
                    } while (var5_4 >= 0);
                    var1_5 = var6_10 + 3;
                    var2_7 = var5_4 + 1;
                    var14_17 = var7_9;
                    var13_16 = var8_11;
                    var12_15 = var9_12;
                    var11_14 = var10_13;
                }
            }
            var6_10 = var1_5;
            var5_4 = var2_7;
            var10_13 = var11_14;
            var4_6 = var3_8;
            var9_12 = var12_15;
            var8_11 = var13_16;
            var7_9 = var14_17;
            if (var1_5 < var15_2) ** GOTO lbl-1000
            var6_10 = var1_5;
            var5_4 = var2_7;
            var10_13 = var11_14;
            var4_6 = var3_8;
            var9_12 = var12_15;
            var8_11 = var13_16;
            var7_9 = var14_17;
        } while (var2_7 < var16_3);
        if (var3_8 == this.version.getTotalCodewords()) return var17_1;
        throw FormatException.getFormatInstance();
    }
}
