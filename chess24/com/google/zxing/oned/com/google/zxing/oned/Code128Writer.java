/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Reader;
import com.google.zxing.oned.OneDimensionalCodeWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public final class Code128Writer
extends OneDimensionalCodeWriter {
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_B = 100;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final char ESCAPE_FNC_1 = '\u00f1';
    private static final char ESCAPE_FNC_2 = '\u00f2';
    private static final char ESCAPE_FNC_3 = '\u00f3';
    private static final char ESCAPE_FNC_4 = '\u00f4';

    private static int chooseCode(CharSequence charSequence, int n, int n2) {
        CType cType = Code128Writer.findCType(charSequence, n);
        if (cType != CType.UNCODABLE) {
            if (cType == CType.ONE_DIGIT) {
                return 100;
            }
            if (n2 == 99) {
                return n2;
            }
            if (n2 == 100) {
                if (cType == CType.FNC_1) {
                    return n2;
                }
                CType cType2 = Code128Writer.findCType(charSequence, n + 2);
                if (cType2 != CType.UNCODABLE) {
                    if (cType2 == CType.ONE_DIGIT) {
                        return n2;
                    }
                    if (cType2 == CType.FNC_1) {
                        if (Code128Writer.findCType(charSequence, n + 3) == CType.TWO_DIGITS) {
                            return 99;
                        }
                        return 100;
                    }
                    n += 4;
                    while ((cType2 = Code128Writer.findCType(charSequence, n)) == CType.TWO_DIGITS) {
                        n += 2;
                    }
                    if (cType2 == CType.ONE_DIGIT) {
                        return 100;
                    }
                    return 99;
                }
                return n2;
            }
            CType cType3 = cType;
            if (cType == CType.FNC_1) {
                cType3 = Code128Writer.findCType(charSequence, n + 1);
            }
            if (cType3 == CType.TWO_DIGITS) {
                return 99;
            }
            return 100;
        }
        return 100;
    }

    private static CType findCType(CharSequence charSequence, int n) {
        int n2 = charSequence.length();
        if (n >= n2) {
            return CType.UNCODABLE;
        }
        char c = charSequence.charAt(n);
        if (c == '\u00f1') {
            return CType.FNC_1;
        }
        if (c >= '0' && c <= '9') {
            if (++n >= n2) {
                return CType.ONE_DIGIT;
            }
            if ((n = (int)charSequence.charAt(n)) >= 48 && n <= 57) {
                return CType.TWO_DIGITS;
            }
            return CType.ONE_DIGIT;
        }
        return CType.UNCODABLE;
    }

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat barcodeFormat, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat != BarcodeFormat.CODE_128) {
            charSequence = new StringBuilder("Can only encode CODE_128, but got ");
            charSequence.append((Object)barcodeFormat);
            throw new IllegalArgumentException(charSequence.toString());
        }
        return super.encode((String)charSequence, barcodeFormat, n, n2, map);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean[] encode(String var1_1) {
        var11_2 = var1_1.length();
        if (var11_2 > 0 && var11_2 <= 80) {
            var10_3 = 0;
        } else {
            var1_1 = new StringBuilder("Contents length should be between 1 and 80 characters, but got ");
            var1_1.append(var11_2);
            throw new IllegalArgumentException(var1_1.toString());
        }
        for (var3_4 = 0; var3_4 < var11_2; ++var3_4) {
            var2_5 = var1_1.charAt(var3_4);
            if (var2_5 >= ' ' && var2_5 <= '~') continue;
            switch (var2_5) {
                default: {
                    var1_1 = new StringBuilder("Bad character in input: ");
                    var1_1.append(var2_5);
                    throw new IllegalArgumentException(var1_1.toString());
                }
                case '\u00f1': 
                case '\u00f2': 
                case '\u00f3': 
                case '\u00f4': 
            }
        }
        var12_6 = new ArrayList<int[]>();
        var5_8 = var3_4 = (var4_7 = 0);
        var6_9 = 1;
        var7_10 = var3_4;
        while (var4_7 < var11_2) {
            block18 : {
                block17 : {
                    var8_11 = Code128Writer.chooseCode((CharSequence)var1_1, var4_7, var7_10);
                    var3_4 = 100;
                    if (var8_11 != var7_10) break block17;
                    var8_11 = var4_7;
                    switch (var1_1.charAt(var4_7)) {
                        default: {
                            if (var7_10 == 100) {
                                var3_4 = var1_1.charAt(var4_7) - 32;
                                var8_11 = var4_7;
                                break;
                            }
                            ** GOTO lbl46
                        }
                        case '\u00f3': {
                            var3_4 = 96;
                            var8_11 = var4_7;
                            break;
                        }
                        case '\u00f2': {
                            var3_4 = 97;
                            var8_11 = var4_7;
                            break;
                        }
                        case '\u00f1': {
                            var3_4 = 102;
                            var8_11 = var4_7;
                            break;
                        }
lbl46: // 1 sources:
                        var3_4 = Integer.parseInt(var1_1.substring(var4_7, var4_7 + 2));
                        var8_11 = var4_7 + 1;
                        case '\u00f4': 
                    }
                    ++var8_11;
                    var9_12 = var7_10;
                    break block18;
                }
                var3_4 = var7_10 == 0 ? (var8_11 == 100 ? 104 : 105) : var8_11;
                var9_12 = var8_11;
                var8_11 = var4_7;
            }
            var12_6.add(Code128Reader.CODE_PATTERNS[var3_4]);
            var3_4 = var5_8 + var3_4 * var6_9;
            var4_7 = var8_11;
            var7_10 = var9_12;
            var5_8 = var3_4;
            if (var8_11 == 0) continue;
            ++var6_9;
            var4_7 = var8_11;
            var7_10 = var9_12;
            var5_8 = var3_4;
        }
        var12_6.add(Code128Reader.CODE_PATTERNS[var5_8 % 103]);
        var12_6.add(Code128Reader.CODE_PATTERNS[106]);
        var1_1 = var12_6.iterator();
        var3_4 = 0;
        while (var1_1.hasNext()) {
            var13_13 = (int[])var1_1.next();
            var5_8 = var13_13.length;
            for (var4_7 = 0; var4_7 < var5_8; var3_4 += var13_13[var4_7], ++var4_7) {
            }
        }
        var1_1 = new boolean[var3_4];
        var12_6 = var12_6.iterator();
        var3_4 = var10_3;
        while (var12_6.hasNext() != false) {
            var3_4 += Code128Writer.appendPattern((boolean[])var1_1, var3_4, (int[])var12_6.next(), true);
        }
        return var1_1;
    }

    private static enum CType {
        UNCODABLE,
        ONE_DIGIT,
        TWO_DIGITS,
        FNC_1;
        

        private CType() {
        }
    }

}
