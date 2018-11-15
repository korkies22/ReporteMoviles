/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.Dimension;
import com.google.zxing.datamatrix.encoder.ASCIIEncoder;
import com.google.zxing.datamatrix.encoder.Base256Encoder;
import com.google.zxing.datamatrix.encoder.C40Encoder;
import com.google.zxing.datamatrix.encoder.EdifactEncoder;
import com.google.zxing.datamatrix.encoder.Encoder;
import com.google.zxing.datamatrix.encoder.EncoderContext;
import com.google.zxing.datamatrix.encoder.SymbolInfo;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.datamatrix.encoder.TextEncoder;
import com.google.zxing.datamatrix.encoder.X12Encoder;
import java.util.Arrays;

public final class HighLevelEncoder {
    static final int ASCII_ENCODATION = 0;
    static final int BASE256_ENCODATION = 5;
    static final int C40_ENCODATION = 1;
    static final char C40_UNLATCH = '\u00fe';
    static final int EDIFACT_ENCODATION = 4;
    static final char LATCH_TO_ANSIX12 = '\u00ee';
    static final char LATCH_TO_BASE256 = '\u00e7';
    static final char LATCH_TO_C40 = '\u00e6';
    static final char LATCH_TO_EDIFACT = '\u00f0';
    static final char LATCH_TO_TEXT = '\u00ef';
    private static final char MACRO_05 = '\u00ec';
    private static final String MACRO_05_HEADER = "[)>\u001e05\u001d";
    private static final char MACRO_06 = '\u00ed';
    private static final String MACRO_06_HEADER = "[)>\u001e06\u001d";
    private static final String MACRO_TRAILER = "\u001e\u0004";
    private static final char PAD = '\u0081';
    static final int TEXT_ENCODATION = 2;
    static final char UPPER_SHIFT = '\u00eb';
    static final int X12_ENCODATION = 3;
    static final char X12_UNLATCH = '\u00fe';

    private HighLevelEncoder() {
    }

    public static int determineConsecutiveDigitCount(CharSequence charSequence, int n) {
        int n2 = charSequence.length();
        int n3 = 0;
        int n4 = 0;
        if (n < n2) {
            char c = charSequence.charAt(n);
            int n5 = n;
            n = n4;
            do {
                n3 = n;
                if (!HighLevelEncoder.isDigit(c)) break;
                n3 = n;
                if (n5 >= n2) break;
                n3 = n + 1;
                n4 = n5 + 1;
                n = n3;
                n5 = n4;
                if (n4 >= n2) continue;
                c = charSequence.charAt(n4);
                n = n3;
                n5 = n4;
            } while (true);
        }
        return n3;
    }

    public static String encodeHighLevel(String string) {
        return HighLevelEncoder.encodeHighLevel(string, SymbolShapeHint.FORCE_NONE, null, null);
    }

    public static String encodeHighLevel(String charSequence, SymbolShapeHint symbolShapeHint, Dimension dimension, Dimension dimension2) {
        int n;
        ASCIIEncoder aSCIIEncoder = new ASCIIEncoder();
        int n2 = 0;
        C40Encoder c40Encoder = new C40Encoder();
        TextEncoder textEncoder = new TextEncoder();
        X12Encoder x12Encoder = new X12Encoder();
        EdifactEncoder edifactEncoder = new EdifactEncoder();
        Base256Encoder base256Encoder = new Base256Encoder();
        EncoderContext encoderContext = new EncoderContext((String)charSequence);
        encoderContext.setSymbolShape(symbolShapeHint);
        encoderContext.setSizeConstraints(dimension, dimension2);
        if (charSequence.startsWith(MACRO_05_HEADER) && charSequence.endsWith(MACRO_TRAILER)) {
            encoderContext.writeCodeword('\u00ec');
            encoderContext.setSkipAtEnd(2);
            encoderContext.pos += 7;
            n = n2;
        } else {
            n = n2;
            if (charSequence.startsWith(MACRO_06_HEADER)) {
                n = n2;
                if (charSequence.endsWith(MACRO_TRAILER)) {
                    encoderContext.writeCodeword('\u00ed');
                    encoderContext.setSkipAtEnd(2);
                    encoderContext.pos += 7;
                    n = n2;
                }
            }
        }
        while (encoderContext.hasMoreCharacters()) {
            new Encoder[]{aSCIIEncoder, c40Encoder, textEncoder, x12Encoder, edifactEncoder, base256Encoder}[n].encode(encoderContext);
            if (encoderContext.getNewEncoding() < 0) continue;
            n = encoderContext.getNewEncoding();
            encoderContext.resetEncoderSignal();
        }
        n2 = encoderContext.getCodewordCount();
        encoderContext.updateSymbolInfo();
        int n3 = encoderContext.getSymbolInfo().getDataCapacity();
        if (n2 < n3 && n != 0 && n != 5) {
            encoderContext.writeCodeword('\u00fe');
        }
        if ((charSequence = encoderContext.getCodewords()).length() < n3) {
            charSequence.append('\u0081');
        }
        while (charSequence.length() < n3) {
            charSequence.append(HighLevelEncoder.randomize253State('\u0081', charSequence.length() + 1));
        }
        return encoderContext.getCodewords().toString();
    }

    private static int findMinimums(float[] arrf, int[] arrn, int n, byte[] arrby) {
        Arrays.fill(arrby, (byte)0);
        int n2 = n;
        for (n = 0; n < 6; ++n) {
            arrn[n] = (int)Math.ceil(arrf[n]);
            int n3 = arrn[n];
            int n4 = n2;
            if (n2 > n3) {
                Arrays.fill(arrby, (byte)0);
                n4 = n3;
            }
            if (n4 == n3) {
                arrby[n] = (byte)(arrby[n] + 1);
            }
            n2 = n4;
        }
        return n2;
    }

    private static int getMinimumCount(byte[] arrby) {
        int n = 0;
        for (int i = 0; i < 6; ++i) {
            n += arrby[i];
        }
        return n;
    }

    static void illegalCharacter(char c) {
        String string = Integer.toHexString(c);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0000".substring(0, 4 - string.length()));
        stringBuilder.append(string);
        string = stringBuilder.toString();
        stringBuilder = new StringBuilder("Illegal character: ");
        stringBuilder.append(c);
        stringBuilder.append(" (0x");
        stringBuilder.append(string);
        stringBuilder.append(')');
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static boolean isDigit(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        return false;
    }

    static boolean isExtendedASCII(char c) {
        if (c >= '?' && c <= '\u00ff') {
            return true;
        }
        return false;
    }

    private static boolean isNativeC40(char c) {
        if (!(c == ' ' || c >= '0' && c <= '9' || c >= 'A' && c <= 'Z')) {
            return false;
        }
        return true;
    }

    private static boolean isNativeEDIFACT(char c) {
        if (c >= ' ' && c <= '^') {
            return true;
        }
        return false;
    }

    private static boolean isNativeText(char c) {
        if (!(c == ' ' || c >= '0' && c <= '9' || c >= 'a' && c <= 'z')) {
            return false;
        }
        return true;
    }

    private static boolean isNativeX12(char c) {
        if (!(HighLevelEncoder.isX12TermSep(c) || c == ' ' || c >= '0' && c <= '9' || c >= 'A' && c <= 'Z')) {
            return false;
        }
        return true;
    }

    private static boolean isSpecialB256(char c) {
        return false;
    }

    private static boolean isX12TermSep(char c) {
        if (c != '\r' && c != '*' && c != '>') {
            return false;
        }
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    static int lookAheadTest(CharSequence var0, int var1_1, int var2_2) {
        if (var1_1 >= var0.length()) {
            return var2_2;
        }
        if (var2_2 == 0) {
            v0 = var5_3 = new float[6];
            v0[0] = 0.0f;
            v0[1] = 1.0f;
            v0[2] = 1.0f;
            v0[3] = 1.0f;
            v0[4] = 1.0f;
            v0[5] = 1.25f;
        } else {
            v1 = var5_3 = new float[6];
            v1[0] = 1.0f;
            v1[1] = 2.0f;
            v1[2] = 2.0f;
            v1[3] = 2.0f;
            v1[4] = 2.0f;
            v1[5] = 2.25f;
            var5_3[var2_2] = 0.0f;
        }
        var2_2 = 0;
        do lbl-1000: // 7 sources:
        {
            if ((var4_5 = var1_1 + var2_2) == var0.length()) {
                var0 = new byte[6];
                var6_6 = new int[6];
                var1_1 = HighLevelEncoder.findMinimums(var5_3, var6_6, Integer.MAX_VALUE, var0);
                var2_2 = HighLevelEncoder.getMinimumCount(var0);
                if (var6_6[0] == var1_1) {
                    return 0;
                }
                if (var2_2 == 1 && var0[5] > 0) {
                    return 5;
                }
                if (var2_2 == 1 && var0[4] > 0) {
                    return 4;
                }
                if (var2_2 == 1 && var0[2] > 0) {
                    return 2;
                }
                if (var2_2 != 1) return 1;
                if (var0[3] <= 0) return 1;
                return 3;
            }
            var3_4 = var0.charAt(var4_5);
            var4_5 = var2_2 + 1;
            if (HighLevelEncoder.isDigit(var3_4)) {
                var5_3[0] = var5_3[0] + 0.5f;
            } else if (HighLevelEncoder.isExtendedASCII(var3_4)) {
                var5_3[0] = (float)Math.ceil(var5_3[0]);
                var5_3[0] = var5_3[0] + 2.0f;
            } else {
                var5_3[0] = (float)Math.ceil(var5_3[0]);
                var5_3[0] = var5_3[0] + 1.0f;
            }
            var5_3[1] = HighLevelEncoder.isNativeC40(var3_4) != false ? var5_3[1] + 0.6666667f : (HighLevelEncoder.isExtendedASCII(var3_4) != false ? var5_3[1] + 2.6666667f : var5_3[1] + 1.3333334f);
            var5_3[2] = HighLevelEncoder.isNativeText(var3_4) != false ? var5_3[2] + 0.6666667f : (HighLevelEncoder.isExtendedASCII(var3_4) != false ? var5_3[2] + 2.6666667f : var5_3[2] + 1.3333334f);
            var5_3[3] = HighLevelEncoder.isNativeX12(var3_4) != false ? var5_3[3] + 0.6666667f : (HighLevelEncoder.isExtendedASCII(var3_4) != false ? var5_3[3] + 4.3333335f : var5_3[3] + 3.3333333f);
            var5_3[4] = HighLevelEncoder.isNativeEDIFACT(var3_4) != false ? var5_3[4] + 0.75f : (HighLevelEncoder.isExtendedASCII(var3_4) != false ? var5_3[4] + 4.25f : var5_3[4] + 3.25f);
            var5_3[5] = HighLevelEncoder.isSpecialB256(var3_4) != false ? var5_3[5] + 4.0f : var5_3[5] + 1.0f;
            var2_2 = var4_5;
            if (var4_5 < 4) ** GOTO lbl-1000
            var6_6 = new int[6];
            var7_7 = new byte[6];
            HighLevelEncoder.findMinimums(var5_3, var6_6, Integer.MAX_VALUE, var7_7);
            var2_2 = HighLevelEncoder.getMinimumCount(var7_7);
            if (var6_6[0] < var6_6[5] && var6_6[0] < var6_6[1] && var6_6[0] < var6_6[2] && var6_6[0] < var6_6[3] && var6_6[0] < var6_6[4]) {
                return 0;
            }
            if (var6_6[5] < var6_6[0]) return 5;
            if (var7_7[1] + var7_7[2] + var7_7[3] + var7_7[4] == 0) {
                return 5;
            }
            if (var2_2 == 1 && var7_7[4] > 0) {
                return 4;
            }
            if (var2_2 == 1 && var7_7[2] > 0) {
                return 2;
            }
            if (var2_2 == 1 && var7_7[3] > 0) {
                return 3;
            }
            var2_2 = var4_5;
            if (var6_6[1] + 1 >= var6_6[0]) ** GOTO lbl-1000
            var2_2 = var4_5;
            if (var6_6[1] + 1 >= var6_6[5]) ** GOTO lbl-1000
            var2_2 = var4_5;
            if (var6_6[1] + 1 >= var6_6[4]) ** GOTO lbl-1000
            var2_2 = var4_5;
            if (var6_6[1] + 1 >= var6_6[2]) ** GOTO lbl-1000
            if (var6_6[1] < var6_6[3]) {
                return 1;
            }
            var2_2 = var4_5;
        } while (var6_6[1] != var6_6[3]);
        var1_1 = var1_1 + var4_5 + 1;
        while (var1_1 < var0.length()) {
            var3_4 = var0.charAt(var1_1);
            if (HighLevelEncoder.isX12TermSep(var3_4)) {
                return 3;
            }
            if (HighLevelEncoder.isNativeX12(var3_4) == false) return 1;
            ++var1_1;
        }
        return 1;
    }

    private static char randomize253State(char c, int n) {
        if ((c = (char)(c + (n * 149 % 253 + 1))) > '\u00fe') {
            c = (char)(c - 254);
        }
        return c;
    }
}
