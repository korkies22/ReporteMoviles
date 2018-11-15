/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.pdf417.PDF417ResultMetadata;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;

final class DecodedBitStreamParser {
    private static final int AL = 28;
    private static final int AS = 27;
    private static final int BEGIN_MACRO_PDF417_CONTROL_BLOCK = 928;
    private static final int BEGIN_MACRO_PDF417_OPTIONAL_FIELD = 923;
    private static final int BYTE_COMPACTION_MODE_LATCH = 901;
    private static final int BYTE_COMPACTION_MODE_LATCH_6 = 924;
    private static final Charset DEFAULT_ENCODING;
    private static final int ECI_CHARSET = 927;
    private static final int ECI_GENERAL_PURPOSE = 926;
    private static final int ECI_USER_DEFINED = 925;
    private static final BigInteger[] EXP900;
    private static final int LL = 27;
    private static final int MACRO_PDF417_TERMINATOR = 922;
    private static final int MAX_NUMERIC_CODEWORDS = 15;
    private static final char[] MIXED_CHARS;
    private static final int ML = 28;
    private static final int MODE_SHIFT_TO_BYTE_COMPACTION_MODE = 913;
    private static final int NUMBER_OF_SEQUENCE_CODEWORDS = 2;
    private static final int NUMERIC_COMPACTION_MODE_LATCH = 902;
    private static final int PAL = 29;
    private static final int PL = 25;
    private static final int PS = 29;
    private static final char[] PUNCT_CHARS;
    private static final int TEXT_COMPACTION_MODE_LATCH = 900;

    static {
        PUNCT_CHARS = ";<>@[\\]_`~!\r\t,:\n-.$/\"|*()?{}'".toCharArray();
        MIXED_CHARS = "0123456789&\r\t,:#-.$/+%*=^".toCharArray();
        DEFAULT_ENCODING = Charset.forName("ISO-8859-1");
        Object object = new BigInteger[16];
        EXP900 = object;
        object[0] = BigInteger.ONE;
        DecodedBitStreamParser.EXP900[1] = object = BigInteger.valueOf(900L);
        for (int i = 2; i < EXP900.length; ++i) {
            DecodedBitStreamParser.EXP900[i] = EXP900[i - 1].multiply((BigInteger)object);
        }
    }

    private DecodedBitStreamParser() {
    }

    private static int byteCompaction(int n, int[] arrn, Charset charset, int n2, StringBuilder stringBuilder) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n3 = 922;
        int n4 = 923;
        if (n == 901) {
            int[] arrn2 = new int[6];
            int n5 = arrn[n2];
            n = 0;
            int n6 = n2 + 1;
            long l = 0L;
            int n7 = n;
            n2 = n;
            n = n6;
            while (n < arrn[0] && n7 == 0) {
                n6 = n2 + 1;
                arrn2[n2] = n5;
                l = l * 900L + (long)n5;
                n2 = n + 1;
                if ((n = arrn[n]) != 900 && n != 901 && n != 902 && n != 924 && n != 928 && n != n4 && n != n3) {
                    if (n6 % 5 == 0 && n6 > 0) {
                        for (n5 = 0; n5 < 6; ++n5) {
                            byteArrayOutputStream.write((byte)(l >> (5 - n5) * 8));
                            n3 = 922;
                            n4 = 923;
                        }
                        n5 = n;
                        n = n2;
                        n2 = 0;
                        l = 0L;
                        continue;
                    }
                    n3 = 922;
                    n4 = 923;
                    n5 = n;
                    n = n2;
                    n2 = n6;
                    continue;
                }
                n7 = 1;
                n4 = 923;
                n5 = n;
                n = n2 - 1;
                n3 = 922;
                n2 = n6;
            }
            if (n == arrn[0] && n5 < 900) {
                n3 = n2 + 1;
                arrn2[n2] = n5;
                n2 = n3;
            }
            n4 = 0;
            do {
                n3 = n;
                if (n4 < n2) {
                    byteArrayOutputStream.write((byte)arrn2[n4]);
                    ++n4;
                    continue;
                }
                break;
            } while (true);
        } else if (n == 924) {
            n = n2;
            n2 = n4 = 0;
            long l = 0L;
            do {
                n3 = n;
                if (n < arrn[0]) {
                    n3 = n;
                    if (n4 == 0) {
                        n3 = n + 1;
                        if ((n = arrn[n]) < 900) {
                            ++n2;
                            long l2 = n;
                            n = n3;
                            l = l * 900L + l2;
                            n3 = n4;
                        } else if (n != 900 && n != 901 && n != 902 && n != 924 && n != 928 && n != 923 && n != 922) {
                            n = n3;
                            n3 = n4;
                        } else {
                            n = n3 - 1;
                            n3 = 1;
                        }
                        if (n2 % 5 == 0 && n2 > 0) {
                            for (n2 = 0; n2 < 6; ++n2) {
                                byteArrayOutputStream.write((byte)(l >> (5 - n2) * 8));
                            }
                            n2 = 0;
                            l = 0L;
                        }
                        n4 = n3;
                        continue;
                    }
                }
                break;
            } while (true);
        } else {
            n3 = n2;
        }
        stringBuilder.append(new String(byteArrayOutputStream.toByteArray(), charset));
        return n3;
    }

    /*
     * Exception decompiling
     */
    static DecoderResult decode(int[] var0, String var1_1) throws FormatException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:487)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:374)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }

    private static String decodeBase900toBase10(int[] object, int n) throws FormatException {
        BigInteger bigInteger = BigInteger.ZERO;
        for (int i = 0; i < n; ++i) {
            bigInteger = bigInteger.add(EXP900[n - i - 1].multiply(BigInteger.valueOf((long)object[i])));
        }
        object = bigInteger.toString();
        if (object.charAt(0) != '1') {
            throw FormatException.getFormatInstance();
        }
        return object.substring(1);
    }

    private static int decodeMacroBlock(int[] arrn, int n, PDF417ResultMetadata pDF417ResultMetadata) throws FormatException {
        if (n + 2 > arrn[0]) {
            throw FormatException.getFormatInstance();
        }
        Object object = new int[2];
        int n2 = 0;
        while (n2 < 2) {
            object[n2] = arrn[n];
            ++n2;
            ++n;
        }
        pDF417ResultMetadata.setSegmentIndex(Integer.parseInt(DecodedBitStreamParser.decodeBase900toBase10((int[])object, 2)));
        object = new StringBuilder();
        n2 = DecodedBitStreamParser.textCompaction(arrn, n, (StringBuilder)object);
        pDF417ResultMetadata.setFileId(object.toString());
        if (arrn[n2] == 923) {
            int n3;
            n = n2 + 1;
            object = new int[arrn[0] - n];
            n2 = n3 = 0;
            while (n < arrn[0] && n3 == 0) {
                int n4 = n + 1;
                if ((n = arrn[n]) < 900) {
                    object[n2] = n;
                    n = n4;
                    ++n2;
                    continue;
                }
                if (n != 922) {
                    throw FormatException.getFormatInstance();
                }
                pDF417ResultMetadata.setLastSegment(true);
                n = n4 + 1;
                n3 = 1;
            }
            pDF417ResultMetadata.setOptionalData(Arrays.copyOf((int[])object, n2));
            return n;
        }
        n = n2;
        if (arrn[n2] == 922) {
            pDF417ResultMetadata.setLastSegment(true);
            n = n2 + 1;
        }
        return n;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void decodeTextCompaction(int[] var0, int[] var1_1, int var2_2, StringBuilder var3_3) {
        var7_4 = Mode.ALPHA;
        var8_5 = Mode.ALPHA;
        var5_6 = 0;
        while (var5_6 < var2_2) {
            block16 : {
                block14 : {
                    block15 : {
                        block24 : {
                            block23 : {
                                block18 : {
                                    block22 : {
                                        block21 : {
                                            block20 : {
                                                block17 : {
                                                    block19 : {
                                                        var6_8 = var0[var5_6];
                                                        switch (.$SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[var7_4.ordinal()]) {
                                                            default: {
                                                                var9_9 = var8_5;
                                                                break block14;
                                                            }
                                                            case 6: {
                                                                if (var6_8 >= 29) ** GOTO lbl14
                                                                var4_7 = DecodedBitStreamParser.PUNCT_CHARS[var6_8];
                                                                ** GOTO lbl26
lbl14: // 1 sources:
                                                                if (var6_8 != 29) ** GOTO lbl17
                                                                var7_4 = Mode.ALPHA;
                                                                break block15;
lbl17: // 1 sources:
                                                                if (var6_8 != 913) ** GOTO lbl20
                                                                var3_3.append((char)var1_1[var5_6]);
                                                                ** GOTO lbl34
lbl20: // 1 sources:
                                                                if (var6_8 != 900) ** GOTO lbl34
                                                                var7_4 = Mode.ALPHA;
                                                                break block15;
                                                            }
                                                            case 5: {
                                                                if (var6_8 >= 26) ** GOTO lbl28
                                                                var4_7 = (char)(var6_8 + 65);
lbl26: // 2 sources:
                                                                var7_4 = var8_5;
                                                                break block16;
lbl28: // 1 sources:
                                                                if (var6_8 != 26) ** GOTO lbl31
                                                                var7_4 = var8_5;
                                                                break block17;
lbl31: // 1 sources:
                                                                if (var6_8 != 900) ** GOTO lbl34
                                                                var7_4 = Mode.ALPHA;
                                                                break block15;
lbl34: // 3 sources:
                                                                var7_4 = var8_5;
                                                                var9_9 = var8_5;
                                                                break block14;
                                                            }
                                                            case 4: {
                                                                if (var6_8 >= 29) ** GOTO lbl41
                                                                var4_7 = DecodedBitStreamParser.PUNCT_CHARS[var6_8];
                                                                break block16;
lbl41: // 1 sources:
                                                                if (var6_8 != 29) ** GOTO lbl44
                                                                var7_4 = Mode.ALPHA;
                                                                break block15;
lbl44: // 1 sources:
                                                                if (var6_8 != 913) ** GOTO lbl48
                                                                var3_3.append((char)var1_1[var5_6]);
                                                                var9_9 = var8_5;
                                                                break block14;
lbl48: // 1 sources:
                                                                var9_9 = var8_5;
                                                                if (var6_8 != 900) break block14;
                                                                var7_4 = Mode.ALPHA;
                                                                break block15;
                                                            }
                                                            case 3: {
                                                                if (var6_8 >= 25) ** GOTO lbl56
                                                                var4_7 = DecodedBitStreamParser.MIXED_CHARS[var6_8];
                                                                break block16;
lbl56: // 1 sources:
                                                                if (var6_8 != 25) ** GOTO lbl59
                                                                var7_4 = Mode.PUNCT;
                                                                break block15;
lbl59: // 1 sources:
                                                                if (var6_8 == 26) break block17;
                                                                if (var6_8 != 27) ** GOTO lbl63
                                                                var7_4 = Mode.LOWER;
                                                                break block15;
lbl63: // 1 sources:
                                                                if (var6_8 != 28) ** GOTO lbl66
                                                                var7_4 = Mode.ALPHA;
                                                                break block15;
lbl66: // 1 sources:
                                                                if (var6_8 != 29) ** GOTO lbl69
                                                                var8_5 = Mode.PUNCT_SHIFT;
                                                                break block18;
lbl69: // 1 sources:
                                                                if (var6_8 != 913) ** GOTO lbl73
                                                                var3_3.append((char)var1_1[var5_6]);
                                                                var9_9 = var8_5;
                                                                break block14;
lbl73: // 1 sources:
                                                                var9_9 = var8_5;
                                                                if (var6_8 != 900) break block14;
                                                                var7_4 = Mode.ALPHA;
                                                                break block15;
                                                            }
                                                            case 2: {
                                                                if (var6_8 >= 26) ** GOTO lbl81
                                                                var4_7 = (char)(var6_8 + 97);
                                                                break block16;
lbl81: // 1 sources:
                                                                if (var6_8 == 26) break block17;
                                                                if (var6_8 != 27) ** GOTO lbl85
                                                                var8_5 = Mode.ALPHA_SHIFT;
                                                                break block18;
lbl85: // 1 sources:
                                                                if (var6_8 != 28) ** GOTO lbl88
                                                                var7_4 = Mode.MIXED;
                                                                break block15;
lbl88: // 1 sources:
                                                                if (var6_8 != 29) ** GOTO lbl91
                                                                var8_5 = Mode.PUNCT_SHIFT;
                                                                break block18;
lbl91: // 1 sources:
                                                                if (var6_8 != 913) ** GOTO lbl95
                                                                var3_3.append((char)var1_1[var5_6]);
                                                                var9_9 = var8_5;
                                                                break block14;
lbl95: // 1 sources:
                                                                var9_9 = var8_5;
                                                                if (var6_8 != 900) break block14;
                                                                var7_4 = Mode.ALPHA;
                                                                break block15;
                                                            }
                                                            case 1: 
                                                        }
                                                        if (var6_8 >= 26) break block19;
                                                        var4_7 = (char)(var6_8 + 65);
                                                        break block16;
                                                    }
                                                    if (var6_8 != 26) break block20;
                                                }
                                                var4_7 = ' ';
                                                break block16;
                                            }
                                            if (var6_8 != 27) break block21;
                                            var7_4 = Mode.LOWER;
                                            break block15;
                                        }
                                        if (var6_8 != 28) break block22;
                                        var7_4 = Mode.MIXED;
                                        break block15;
                                    }
                                    if (var6_8 != 29) break block23;
                                    var8_5 = Mode.PUNCT_SHIFT;
                                }
                                var9_9 = var7_4;
                                var7_4 = var8_5;
                                var8_5 = var9_9;
                                break block15;
                            }
                            if (var6_8 != 913) break block24;
                            var3_3.append((char)var1_1[var5_6]);
                            var9_9 = var8_5;
                            break block14;
                        }
                        var9_9 = var8_5;
                        if (var6_8 != 900) break block14;
                        var7_4 = Mode.ALPHA;
                    }
                    var9_9 = var8_5;
                }
                var4_7 = '\u0000';
                var8_5 = var9_9;
            }
            if (var4_7 != '\u0000') {
                var3_3.append(var4_7);
            }
            ++var5_6;
        }
    }

    private static int numericCompaction(int[] arrn, int n, StringBuilder stringBuilder) throws FormatException {
        int n2;
        int[] arrn2 = new int[15];
        int n3 = n2 = 0;
        int n4 = n;
        n = n3;
        while (n4 < arrn[0] && n2 == 0) {
            int n5;
            block7 : {
                int n6;
                block8 : {
                    block6 : {
                        n6 = n4 + 1;
                        n5 = arrn[n4];
                        if (n6 == arrn[0]) {
                            n2 = 1;
                        }
                        if (n5 >= 900) break block6;
                        arrn2[n] = n5;
                        n3 = n + 1;
                        n4 = n6;
                        break block7;
                    }
                    if (n5 == 900 || n5 == 901 || n5 == 924 || n5 == 928 || n5 == 923) break block8;
                    n3 = n;
                    n4 = n6;
                    if (n5 != 922) break block7;
                }
                n4 = n6 - 1;
                n2 = 1;
                n3 = n;
            }
            if (n3 % 15 != 0 && n5 != 902) {
                n = n3;
                if (n2 == 0) continue;
            }
            n = n3;
            if (n3 <= 0) continue;
            stringBuilder.append(DecodedBitStreamParser.decodeBase900toBase10(arrn2, n3));
            n = 0;
        }
        return n4;
    }

    /*
     * Exception decompiling
     */
    private static int textCompaction(int[] var0, int var1_1, StringBuilder var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:487)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:374)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:186)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:131)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:378)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:884)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:786)
        // org.benf.cfr.reader.Main.doClass(Main.java:54)
        // org.benf.cfr.reader.Main.main(Main.java:247)
        throw new IllegalStateException("Decompilation failed");
    }

    private static enum Mode {
        ALPHA,
        LOWER,
        MIXED,
        PUNCT,
        ALPHA_SHIFT,
        PUNCT_SHIFT;
        

        private Mode() {
        }
    }

}
