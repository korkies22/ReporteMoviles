/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.pdf417.encoder;

import com.google.zxing.WriterException;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.pdf417.encoder.Compaction;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;

final class PDF417HighLevelEncoder {
    private static final int BYTE_COMPACTION = 1;
    private static final Charset DEFAULT_ENCODING;
    private static final int ECI_CHARSET = 927;
    private static final int ECI_GENERAL_PURPOSE = 926;
    private static final int ECI_USER_DEFINED = 925;
    private static final int LATCH_TO_BYTE = 924;
    private static final int LATCH_TO_BYTE_PADDED = 901;
    private static final int LATCH_TO_NUMERIC = 902;
    private static final int LATCH_TO_TEXT = 900;
    private static final byte[] MIXED;
    private static final int NUMERIC_COMPACTION = 2;
    private static final byte[] PUNCTUATION;
    private static final int SHIFT_TO_BYTE = 913;
    private static final int SUBMODE_ALPHA = 0;
    private static final int SUBMODE_LOWER = 1;
    private static final int SUBMODE_MIXED = 2;
    private static final int SUBMODE_PUNCTUATION = 3;
    private static final int TEXT_COMPACTION = 0;
    private static final byte[] TEXT_MIXED_RAW;
    private static final byte[] TEXT_PUNCTUATION_RAW;

    static {
        int n;
        TEXT_MIXED_RAW = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 38, 13, 9, 44, 58, 35, 45, 46, 36, 47, 43, 37, 42, 61, 94, 0, 32, 0, 0, 0};
        TEXT_PUNCTUATION_RAW = new byte[]{59, 60, 62, 64, 91, 92, 93, 95, 96, 126, 33, 13, 9, 44, 58, 10, 45, 46, 36, 47, 34, 124, 42, 40, 41, 63, 123, 125, 39, 0};
        MIXED = new byte[128];
        PUNCTUATION = new byte[128];
        DEFAULT_ENCODING = Charset.forName("ISO-8859-1");
        Arrays.fill(MIXED, (byte)-1);
        int n2 = 0;
        for (n = 0; n < TEXT_MIXED_RAW.length; ++n) {
            byte by = TEXT_MIXED_RAW[n];
            if (by <= 0) continue;
            PDF417HighLevelEncoder.MIXED[by] = (byte)n;
        }
        Arrays.fill(PUNCTUATION, (byte)-1);
        for (n = n2; n < TEXT_PUNCTUATION_RAW.length; ++n) {
            n2 = TEXT_PUNCTUATION_RAW[n];
            if (n2 <= 0) continue;
            PDF417HighLevelEncoder.PUNCTUATION[n2] = (byte)n;
        }
    }

    private PDF417HighLevelEncoder() {
    }

    private static int determineConsecutiveBinaryCount(String charSequence, int n, Charset object) throws WriterException {
        int n2;
        object = object.newEncoder();
        int n3 = charSequence.length();
        for (n2 = n; n2 < n3; ++n2) {
            int n4;
            char c = charSequence.charAt(n2);
            int n5 = 0;
            do {
                n4 = n5;
                if (n5 >= 13) break;
                n4 = n5++;
                if (!PDF417HighLevelEncoder.isDigit(c)) break;
                int n6 = n2 + n5;
                n4 = n5;
                if (n6 >= n3) break;
                c = charSequence.charAt(n6);
            } while (true);
            if (n4 >= 13) {
                return n2 - n;
            }
            c = charSequence.charAt(n2);
            if (object.canEncode(c)) continue;
            charSequence = new StringBuilder("Non-encodable character detected: ");
            charSequence.append(c);
            charSequence.append(" (Unicode: ");
            charSequence.append((int)c);
            charSequence.append(')');
            throw new WriterException(charSequence.toString());
        }
        return n2 - n;
    }

    private static int determineConsecutiveDigitCount(CharSequence charSequence, int n) {
        int n2 = charSequence.length();
        int n3 = 0;
        int n4 = 0;
        if (n < n2) {
            char c = charSequence.charAt(n);
            int n5 = n;
            n = n4;
            do {
                n3 = n;
                if (!PDF417HighLevelEncoder.isDigit(c)) break;
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

    private static int determineConsecutiveTextCount(CharSequence charSequence, int n) {
        int n2;
        int n3 = charSequence.length();
        int n4 = n;
        do {
            n2 = n4;
            if (n4 >= n3) break;
            char c = charSequence.charAt(n4);
            n2 = 0;
            int n5 = n4;
            while (n2 < 13 && PDF417HighLevelEncoder.isDigit(c) && n5 < n3) {
                int n6;
                n4 = n2 + 1;
                n5 = n6 = n5 + 1;
                n2 = n4;
                if (n6 >= n3) continue;
                c = charSequence.charAt(n6);
                n5 = n6;
                n2 = n4;
            }
            if (n2 >= 13) {
                return n5 - n - n2;
            }
            n4 = n5;
            if (n2 > 0) continue;
            n2 = n5;
            if (!PDF417HighLevelEncoder.isText(charSequence.charAt(n5))) break;
            n4 = n5 + 1;
        } while (true);
        return n2 - n;
    }

    private static void encodeBinary(byte[] arrby, int n, int n2, int n3, StringBuilder stringBuilder) {
        int n4;
        if (n2 == 1 && n3 == 0) {
            stringBuilder.append('\u0391');
        } else if (n2 % 6 == 0) {
            stringBuilder.append('\u039c');
        } else {
            stringBuilder.append('\u0385');
        }
        if (n2 >= 6) {
            char[] arrc = new char[5];
            n3 = n;
            do {
                n4 = n3;
                if (n + n2 - n3 >= 6) {
                    long l;
                    int n5 = 0;
                    long l2 = 0L;
                    n4 = 0;
                    do {
                        l = l2;
                        if (n4 >= 6) break;
                        l = arrby[n3 + n4] & 255;
                        ++n4;
                        l2 = (l2 << 8) + l;
                    } while (true);
                    for (int i = n5; i < 5; ++i) {
                        arrc[i] = (char)(l % 900L);
                        l /= 900L;
                    }
                    for (n4 = 4; n4 >= 0; --n4) {
                        stringBuilder.append(arrc[n4]);
                    }
                    n3 += 6;
                    continue;
                }
                break;
            } while (true);
        } else {
            n4 = n;
        }
        while (n4 < n + n2) {
            stringBuilder.append((char)(arrby[n4] & 255));
            ++n4;
        }
    }

    static String encodeHighLevel(String arrby, Compaction arrby2, Charset charset) throws WriterException {
        Charset charset2;
        StringBuilder stringBuilder = new StringBuilder(arrby.length());
        if (charset == null) {
            charset2 = DEFAULT_ENCODING;
        } else {
            charset2 = charset;
            if (!DEFAULT_ENCODING.equals(charset)) {
                CharacterSetECI characterSetECI = CharacterSetECI.getCharacterSetECIByName(charset.name());
                charset2 = charset;
                if (characterSetECI != null) {
                    PDF417HighLevelEncoder.encodingECI(characterSetECI.getValue(), stringBuilder);
                    charset2 = charset;
                }
            }
        }
        int n = arrby.length();
        if (arrby2 == Compaction.TEXT) {
            PDF417HighLevelEncoder.encodeText((CharSequence)arrby, 0, n, stringBuilder, 0);
        } else if (arrby2 == Compaction.BYTE) {
            arrby = arrby.getBytes(charset2);
            PDF417HighLevelEncoder.encodeBinary(arrby, 0, arrby.length, 1, stringBuilder);
        } else if (arrby2 == Compaction.NUMERIC) {
            stringBuilder.append('\u0386');
            PDF417HighLevelEncoder.encodeNumeric((String)arrby, 0, n, stringBuilder);
        } else {
            int n2;
            int n3;
            int n4 = n3 = (n2 = 0);
            while (n2 < n) {
                int n5 = PDF417HighLevelEncoder.determineConsecutiveDigitCount((CharSequence)arrby, n2);
                if (n5 >= 13) {
                    stringBuilder.append('\u0386');
                    n3 = 2;
                    PDF417HighLevelEncoder.encodeNumeric((String)arrby, n2, n5, stringBuilder);
                    n2 += n5;
                    n4 = 0;
                    continue;
                }
                int n6 = PDF417HighLevelEncoder.determineConsecutiveTextCount((CharSequence)arrby, n2);
                if (n6 < 5 && n5 != n) {
                    n5 = n6 = PDF417HighLevelEncoder.determineConsecutiveBinaryCount((String)arrby, n2, charset2);
                    if (n6 == 0) {
                        n5 = 1;
                    }
                    if ((arrby2 = arrby.substring(n2, n5 += n2).getBytes(charset2)).length == 1 && n3 == 0) {
                        PDF417HighLevelEncoder.encodeBinary(arrby2, 0, 1, 0, stringBuilder);
                    } else {
                        PDF417HighLevelEncoder.encodeBinary(arrby2, 0, arrby2.length, n3, stringBuilder);
                        n4 = 0;
                        n3 = 1;
                    }
                    n2 = n5;
                    continue;
                }
                n5 = n3;
                if (n3 != 0) {
                    stringBuilder.append('\u0384');
                    n4 = n5 = 0;
                }
                n4 = PDF417HighLevelEncoder.encodeText((CharSequence)arrby, n2, n6, stringBuilder, n4);
                n2 += n6;
                n3 = n5;
            }
        }
        return stringBuilder.toString();
    }

    private static void encodeNumeric(String string, int n, int n2, StringBuilder stringBuilder) {
        int n3;
        StringBuilder stringBuilder2 = new StringBuilder(n2 / 3 + 1);
        BigInteger bigInteger = BigInteger.valueOf(900L);
        BigInteger bigInteger2 = BigInteger.valueOf(0L);
        for (int i = 0; i < n2; i += n3) {
            BigInteger bigInteger3;
            stringBuilder2.setLength(0);
            n3 = Math.min(44, n2 - i);
            Serializable serializable = new StringBuilder("1");
            int n4 = n + i;
            serializable.append(string.substring(n4, n4 + n3));
            serializable = new BigInteger(serializable.toString());
            do {
                stringBuilder2.append((char)serializable.mod(bigInteger).intValue());
                bigInteger3 = serializable.divide(bigInteger);
                serializable = bigInteger3;
            } while (!bigInteger3.equals(bigInteger2));
            for (n4 = stringBuilder2.length() - 1; n4 >= 0; --n4) {
                stringBuilder.append(stringBuilder2.charAt(n4));
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static int encodeText(CharSequence var0, int var1_1, int var2_2, StringBuilder var3_3, int var4_4) {
        var8_5 = new StringBuilder(var2_2);
        var6_6 = 0;
        block5 : do {
            block26 : {
                block24 : {
                    block25 : {
                        block27 : {
                            var7_8 = var1_1 + var6_6;
                            var5_7 = var0.charAt(var7_8);
                            switch (var4_4) {
                                default: {
                                    if (!PDF417HighLevelEncoder.isPunctuation(var5_7)) break block24;
                                    var8_5.append((char)PDF417HighLevelEncoder.PUNCTUATION[var5_7]);
                                    break block25;
                                }
                                case 2: {
                                    if (!PDF417HighLevelEncoder.isMixed(var5_7)) ** GOTO lbl15
                                    var8_5.append((char)PDF417HighLevelEncoder.MIXED[var5_7]);
                                    break block25;
lbl15: // 1 sources:
                                    if (!PDF417HighLevelEncoder.isAlphaUpper(var5_7)) ** GOTO lbl18
                                    var8_5.append('\u001c');
                                    break block26;
lbl18: // 1 sources:
                                    if (PDF417HighLevelEncoder.isAlphaLower(var5_7)) {
                                        var8_5.append('\u001b');
                                        ** break;
                                    }
                                    if (++var7_8 < var2_2 && PDF417HighLevelEncoder.isPunctuation(var0.charAt(var7_8))) {
                                        var4_4 = 3;
                                        var8_5.append('\u0019');
                                        continue block5;
                                    }
                                    var8_5.append('\u001d');
                                    var8_5.append((char)PDF417HighLevelEncoder.PUNCTUATION[var5_7]);
                                    break block25;
                                }
                                case 1: {
                                    if (PDF417HighLevelEncoder.isAlphaLower(var5_7)) {
                                        if (var5_7 == ' ') {
                                            var8_5.append('\u001a');
                                        } else {
                                            var8_5.append((char)(var5_7 - 97));
                                        }
                                    } else if (PDF417HighLevelEncoder.isAlphaUpper(var5_7)) {
                                        var8_5.append('\u001b');
                                        var8_5.append((char)(var5_7 - 65));
                                    } else {
                                        if (PDF417HighLevelEncoder.isMixed(var5_7)) {
                                            var8_5.append('\u001c');
                                            break;
                                        }
                                        var8_5.append('\u001d');
                                        var8_5.append((char)PDF417HighLevelEncoder.PUNCTUATION[var5_7]);
                                    }
                                    break block25;
                                }
                                case 0: {
                                    if (!PDF417HighLevelEncoder.isAlphaUpper(var5_7)) ** GOTO lbl52
                                    if (var5_7 == ' ') {
                                        var8_5.append('\u001a');
                                    } else {
                                        var8_5.append((char)(var5_7 - 65));
                                    }
                                    break block25;
lbl52: // 1 sources:
                                    if (!PDF417HighLevelEncoder.isAlphaLower(var5_7)) ** GOTO lbl56
                                    var8_5.append('\u001b');
lbl54: // 2 sources:
                                    var4_4 = 1;
                                    continue block5;
lbl56: // 1 sources:
                                    if (!PDF417HighLevelEncoder.isMixed(var5_7)) break block27;
                                    var8_5.append('\u001c');
                                }
                            }
                            var4_4 = 2;
                            continue;
                        }
                        var8_5.append('\u001d');
                        var8_5.append((char)PDF417HighLevelEncoder.PUNCTUATION[var5_7]);
                    }
                    var6_6 = var7_8 = var6_6 + 1;
                    if (var7_8 < var2_2) continue;
                    break;
                }
                var8_5.append('\u001d');
            }
            var4_4 = 0;
        } while (true);
        var7_8 = var8_5.length();
        var2_2 = var1_1 = 0;
        do {
            if (var1_1 >= var7_8) {
                if (var7_8 % 2 == 0) return var4_4;
                var3_3.append((char)(var2_2 * 30 + 29));
                return var4_4;
            }
            var6_6 = var1_1 % 2 != 0 ? 1 : 0;
            if (var6_6 != 0) {
                var5_7 = (char)(var2_2 * 30 + var8_5.charAt(var1_1));
                var3_3.append(var5_7);
                var2_2 = var5_7;
            } else {
                var2_2 = var8_5.charAt(var1_1);
            }
            ++var1_1;
        } while (true);
    }

    private static void encodingECI(int n, StringBuilder stringBuilder) throws WriterException {
        if (n >= 0 && n < 900) {
            stringBuilder.append('\u039f');
            stringBuilder.append((char)n);
            return;
        }
        if (n < 810900) {
            stringBuilder.append('\u039e');
            stringBuilder.append((char)(n / 900 - 1));
            stringBuilder.append((char)(n % 900));
            return;
        }
        if (n < 811800) {
            stringBuilder.append('\u039d');
            stringBuilder.append((char)(810900 - n));
            return;
        }
        stringBuilder = new StringBuilder("ECI number not in valid range from 0..811799, but was ");
        stringBuilder.append(n);
        throw new WriterException(stringBuilder.toString());
    }

    private static boolean isAlphaLower(char c) {
        if (c != ' ' && (c < 'a' || c > 'z')) {
            return false;
        }
        return true;
    }

    private static boolean isAlphaUpper(char c) {
        if (c != ' ' && (c < 'A' || c > 'Z')) {
            return false;
        }
        return true;
    }

    private static boolean isDigit(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        return false;
    }

    private static boolean isMixed(char c) {
        if (MIXED[c] != -1) {
            return true;
        }
        return false;
    }

    private static boolean isPunctuation(char c) {
        if (PUNCTUATION[c] != -1) {
            return true;
        }
        return false;
    }

    private static boolean isText(char c) {
        if (c != '\t' && c != '\n' && c != '\r' && (c < ' ' || c > '~')) {
            return false;
        }
        return true;
    }
}
