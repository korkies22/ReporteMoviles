/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.OneDReader;
import java.util.Arrays;
import java.util.Map;

public final class Code39Reader
extends OneDReader {
    static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%";
    static final int ASTERISK_ENCODING;
    static final int[] CHARACTER_ENCODINGS;
    private static final String CHECK_DIGIT_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%";
    private final int[] counters;
    private final StringBuilder decodeRowResult;
    private final boolean extendedMode;
    private final boolean usingCheckDigit;

    static {
        int[] arrn;
        int[] arrn2 = arrn = new int[44];
        arrn2[0] = 52;
        arrn2[1] = 289;
        arrn2[2] = 97;
        arrn2[3] = 352;
        arrn2[4] = 49;
        arrn2[5] = 304;
        arrn2[6] = 112;
        arrn2[7] = 37;
        arrn2[8] = 292;
        arrn2[9] = 100;
        arrn2[10] = 265;
        arrn2[11] = 73;
        arrn2[12] = 328;
        arrn2[13] = 25;
        arrn2[14] = 280;
        arrn2[15] = 88;
        arrn2[16] = 13;
        arrn2[17] = 268;
        arrn2[18] = 76;
        arrn2[19] = 28;
        arrn2[20] = 259;
        arrn2[21] = 67;
        arrn2[22] = 322;
        arrn2[23] = 19;
        arrn2[24] = 274;
        arrn2[25] = 82;
        arrn2[26] = 7;
        arrn2[27] = 262;
        arrn2[28] = 70;
        arrn2[29] = 22;
        arrn2[30] = 385;
        arrn2[31] = 193;
        arrn2[32] = 448;
        arrn2[33] = 145;
        arrn2[34] = 400;
        arrn2[35] = 208;
        arrn2[36] = 133;
        arrn2[37] = 388;
        arrn2[38] = 196;
        arrn2[39] = 148;
        arrn2[40] = 168;
        arrn2[41] = 162;
        arrn2[42] = 138;
        arrn2[43] = 42;
        CHARACTER_ENCODINGS = arrn;
        ASTERISK_ENCODING = arrn[39];
    }

    public Code39Reader() {
        this(false);
    }

    public Code39Reader(boolean bl) {
        this(bl, false);
    }

    public Code39Reader(boolean bl, boolean bl2) {
        this.usingCheckDigit = bl;
        this.extendedMode = bl2;
        this.decodeRowResult = new StringBuilder(20);
        this.counters = new int[9];
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String decodeExtended(CharSequence charSequence) throws FormatException {
        int n = charSequence.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; ++i) {
            int n2;
            block12 : {
                char c;
                block10 : {
                    block11 : {
                        n2 = charSequence.charAt(i);
                        if (n2 != 43 && n2 != 36 && n2 != 37 && n2 != 47) {
                            stringBuilder.append((char)n2);
                            continue;
                        }
                        c = charSequence.charAt(++i);
                        if (n2 == 43) break block10;
                        if (n2 == 47) break block11;
                        switch (n2) {
                            default: {
                                n2 = 0;
                                break block12;
                            }
                            case 37: {
                                if (c >= 'A' && c <= 'E') {
                                    n2 = (char)(c - 38);
                                } else {
                                    if (c < 'F' || c > 'W') throw FormatException.getFormatInstance();
                                    n2 = (char)(c - 11);
                                }
                                break block12;
                            }
                            case 36: {
                                if (c < 'A' || c > 'Z') throw FormatException.getFormatInstance();
                                n2 = (char)(c - 64);
                                break block12;
                            }
                        }
                    }
                    if (c >= 'A' && c <= 'O') {
                        n2 = (char)(c - 32);
                    } else {
                        if (c != 'Z') throw FormatException.getFormatInstance();
                        n2 = 58;
                    }
                    break block12;
                }
                if (c < 'A' || c > 'Z') throw FormatException.getFormatInstance();
                n2 = (char)(c + 32);
            }
            stringBuilder.append((char)n2);
        }
        return stringBuilder.toString();
    }

    private static int[] findAsteriskPattern(BitArray bitArray, int[] arrn) throws NotFoundException {
        int n;
        int n2;
        int n3 = bitArray.getSize();
        int n4 = arrn.length;
        int n5 = n = 0;
        int n6 = n2;
        for (n2 = bitArray.getNextSet((int)0); n2 < n3; ++n2) {
            int n7;
            if (bitArray.get(n2) ^ n) {
                arrn[n5] = arrn[n5] + 1;
                n7 = n6;
            } else {
                int n8 = n4 - 1;
                if (n5 == n8) {
                    if (Code39Reader.toNarrowWidePattern(arrn) == ASTERISK_ENCODING && bitArray.isRange(Math.max(0, n6 - (n2 - n6) / 2), n6, false)) {
                        return new int[]{n6, n2};
                    }
                    n7 = n6 + (arrn[0] + arrn[1]);
                    n6 = n4 - 2;
                    System.arraycopy(arrn, 2, arrn, 0, n6);
                    arrn[n6] = 0;
                    arrn[n8] = 0;
                    n6 = n5 - 1;
                    n5 = n7;
                } else {
                    n7 = n5 + 1;
                    n5 = n6;
                    n6 = n7;
                }
                arrn[n6] = 1;
                n ^= 1;
                n7 = n5;
                n5 = n6;
            }
            n6 = n7;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static char patternToChar(int n) throws NotFoundException {
        for (int i = 0; i < CHARACTER_ENCODINGS.length; ++i) {
            if (CHARACTER_ENCODINGS[i] != n) continue;
            return ALPHABET_STRING.charAt(i);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int toNarrowWidePattern(int[] arrn) {
        int n = 0;
        int n2 = arrn.length;
        int n3 = 0;
        do {
            int n4;
            int n5;
            int n6;
            int n7;
            int n8 = arrn.length;
            int n9 = Integer.MAX_VALUE;
            for (n7 = 0; n7 < n8; ++n7) {
                n6 = arrn[n7];
                n4 = n9;
                if (n6 < n9) {
                    n4 = n9;
                    if (n6 > n3) {
                        n4 = n6;
                    }
                }
                n9 = n4;
            }
            n4 = n7 = (n3 = (n6 = 0));
            while (n6 < n2) {
                int n10 = arrn[n6];
                int n11 = n3;
                n5 = n7;
                n8 = n4;
                if (n10 > n9) {
                    n5 = n7 | 1 << n2 - 1 - n6;
                    n11 = n3 + 1;
                    n8 = n4 + n10;
                }
                ++n6;
                n3 = n11;
                n7 = n5;
                n4 = n8;
            }
            if (n3 == 3) {
                n6 = n3;
                for (n3 = n; n3 < n2 && n6 > 0; ++n3) {
                    n5 = arrn[n3];
                    n8 = n6;
                    if (n5 > n9) {
                        n8 = n6 - 1;
                        if (n5 << 1 >= n4) {
                            return -1;
                        }
                    }
                    n6 = n8;
                }
                return n7;
            }
            if (n3 <= 3) {
                return -1;
            }
            n3 = n9;
        } while (true);
    }

    @Override
    public Result decodeRow(int n, BitArray object, Map<DecodeHintType, ?> object2) throws NotFoundException, ChecksumException, FormatException {
        Object object3 = this.counters;
        Arrays.fill(object3, 0);
        Object object4 = this.decodeRowResult;
        object4.setLength(0);
        object2 = Code39Reader.findAsteriskPattern((BitArray)object, object3);
        int n2 = object.getNextSet(object2[1]);
        int n3 = object.getSize();
        do {
            Code39Reader.recordPattern((BitArray)object, n2, object3);
            int n4 = Code39Reader.toNarrowWidePattern(object3);
            if (n4 < 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            char c = Code39Reader.patternToChar(n4);
            object4.append(c);
            int n5 = ((int[])object3).length;
            int n6 = n2;
            for (n4 = 0; n4 < n5; ++n4) {
                n6 += object3[n4];
            }
            n5 = object.getNextSet(n6);
            if (c == '*') {
                object4.setLength(object4.length() - 1);
                int n7 = ((int[])object3).length;
                n4 = n6 = 0;
                while (n6 < n7) {
                    n4 += object3[n6];
                    ++n6;
                }
                if (n5 != n3 && n5 - n2 - n4 << 1 < n4) {
                    throw NotFoundException.getNotFoundInstance();
                }
                if (this.usingCheckDigit) {
                    n3 = object4.length() - 1;
                    n5 = n6 = 0;
                    while (n6 < n3) {
                        n5 += CHECK_DIGIT_STRING.indexOf(this.decodeRowResult.charAt(n6));
                        ++n6;
                    }
                    if (object4.charAt(n3) != CHECK_DIGIT_STRING.charAt(n5 % 43)) {
                        throw ChecksumException.getChecksumInstance();
                    }
                    object4.setLength(n3);
                }
                if (object4.length() == 0) {
                    throw NotFoundException.getNotFoundInstance();
                }
                object = this.extendedMode ? Code39Reader.decodeExtended(object4) : object4.toString();
                float f = (float)(object2[1] + object2[0]) / 2.0f;
                float f2 = n2;
                float f3 = (float)n4 / 2.0f;
                float f4 = n;
                object2 = new ResultPoint(f, f4);
                object3 = new ResultPoint(f2 + f3, f4);
                object4 = BarcodeFormat.CODE_39;
                return new Result((String)object, null, new ResultPoint[]{object2, object3}, (BarcodeFormat)((Object)object4));
            }
            n2 = n5;
        } while (true);
    }
}
