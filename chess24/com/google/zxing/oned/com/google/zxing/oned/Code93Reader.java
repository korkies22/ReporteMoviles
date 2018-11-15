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

public final class Code93Reader
extends OneDReader {
    private static final char[] ALPHABET;
    static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*";
    private static final int ASTERISK_ENCODING;
    static final int[] CHARACTER_ENCODINGS;
    private final int[] counters = new int[6];
    private final StringBuilder decodeRowResult = new StringBuilder(20);

    static {
        int[] arrn;
        ALPHABET = ALPHABET_STRING.toCharArray();
        int[] arrn2 = arrn = new int[48];
        arrn2[0] = 276;
        arrn2[1] = 328;
        arrn2[2] = 324;
        arrn2[3] = 322;
        arrn2[4] = 296;
        arrn2[5] = 292;
        arrn2[6] = 290;
        arrn2[7] = 336;
        arrn2[8] = 274;
        arrn2[9] = 266;
        arrn2[10] = 424;
        arrn2[11] = 420;
        arrn2[12] = 418;
        arrn2[13] = 404;
        arrn2[14] = 402;
        arrn2[15] = 394;
        arrn2[16] = 360;
        arrn2[17] = 356;
        arrn2[18] = 354;
        arrn2[19] = 308;
        arrn2[20] = 282;
        arrn2[21] = 344;
        arrn2[22] = 332;
        arrn2[23] = 326;
        arrn2[24] = 300;
        arrn2[25] = 278;
        arrn2[26] = 436;
        arrn2[27] = 434;
        arrn2[28] = 428;
        arrn2[29] = 422;
        arrn2[30] = 406;
        arrn2[31] = 410;
        arrn2[32] = 364;
        arrn2[33] = 358;
        arrn2[34] = 310;
        arrn2[35] = 314;
        arrn2[36] = 302;
        arrn2[37] = 468;
        arrn2[38] = 466;
        arrn2[39] = 458;
        arrn2[40] = 366;
        arrn2[41] = 374;
        arrn2[42] = 430;
        arrn2[43] = 294;
        arrn2[44] = 474;
        arrn2[45] = 470;
        arrn2[46] = 306;
        arrn2[47] = 350;
        CHARACTER_ENCODINGS = arrn;
        ASTERISK_ENCODING = arrn[47];
    }

    private static void checkChecksums(CharSequence charSequence) throws ChecksumException {
        int n = charSequence.length();
        Code93Reader.checkOneChecksum(charSequence, n - 2, 20);
        Code93Reader.checkOneChecksum(charSequence, n - 1, 15);
    }

    private static void checkOneChecksum(CharSequence charSequence, int n, int n2) throws ChecksumException {
        int n3 = 0;
        int n4 = 1;
        for (int i = n - 1; i >= 0; --i) {
            int n5;
            n3 += ALPHABET_STRING.indexOf(charSequence.charAt(i)) * n4;
            n4 = n5 = n4 + 1;
            if (n5 <= n2) continue;
            n4 = 1;
        }
        if (charSequence.charAt(n) != ALPHABET[n3 % 47]) {
            throw ChecksumException.getChecksumInstance();
        }
    }

    private static String decodeExtended(CharSequence charSequence) throws FormatException {
        int n = charSequence.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; ++i) {
            int n2 = charSequence.charAt(i);
            if (n2 >= 97 && n2 <= 100) {
                if (i >= n - 1) {
                    throw FormatException.getFormatInstance();
                }
                char c = charSequence.charAt(++i);
                switch (n2) {
                    default: {
                        n2 = 0;
                        break;
                    }
                    case 100: {
                        if (c >= 'A' && c <= 'Z') {
                            n2 = (char)(c + 32);
                            break;
                        }
                        throw FormatException.getFormatInstance();
                    }
                    case 99: {
                        if (c >= 'A' && c <= 'O') {
                            n2 = (char)(c - 32);
                            break;
                        }
                        if (c == 'Z') {
                            n2 = 58;
                            break;
                        }
                        throw FormatException.getFormatInstance();
                    }
                    case 98: {
                        if (c >= 'A' && c <= 'E') {
                            n2 = (char)(c - 38);
                            break;
                        }
                        if (c >= 'F' && c <= 'J') {
                            n2 = (char)(c - 11);
                            break;
                        }
                        if (c >= 'K' && c <= 'O') {
                            n2 = (char)(c + 16);
                            break;
                        }
                        if (c >= 'P' && c <= 'S') {
                            n2 = (char)(c + 43);
                            break;
                        }
                        if (c >= 'T' && c <= 'Z') {
                            n2 = 127;
                            break;
                        }
                        throw FormatException.getFormatInstance();
                    }
                    case 97: {
                        if (c >= 'A' && c <= 'Z') {
                            n2 = (char)(c - 64);
                            break;
                        }
                        throw FormatException.getFormatInstance();
                    }
                }
                stringBuilder.append((char)n2);
                continue;
            }
            stringBuilder.append((char)n2);
        }
        return stringBuilder.toString();
    }

    private int[] findAsteriskPattern(BitArray bitArray) throws NotFoundException {
        int n;
        int n2;
        int n3 = bitArray.getSize();
        Arrays.fill(this.counters, 0);
        int[] arrn = this.counters;
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
                    if (Code93Reader.toPattern(arrn) == ASTERISK_ENCODING) {
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
            return ALPHABET[i];
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int toPattern(int[] arrn) {
        int n;
        int n2 = arrn.length;
        int n3 = n = 0;
        while (n < n2) {
            n3 += arrn[n];
            ++n;
        }
        int n4 = arrn.length;
        n = n2 = 0;
        while (n2 < n4) {
            int n5 = Math.round((float)arrn[n2] * 9.0f / (float)n3);
            if (n5 > 0 && n5 <= 4) {
                if ((n2 & 1) == 0) {
                    for (int i = 0; i < n5; ++i) {
                        n = n << 1 | 1;
                    }
                } else {
                    n <<= n5;
                }
                ++n2;
                continue;
            }
            return -1;
        }
        return n;
    }

    @Override
    public Result decodeRow(int n, BitArray object, Map<DecodeHintType, ?> object2) throws NotFoundException, ChecksumException, FormatException {
        object2 = this.findAsteriskPattern((BitArray)object);
        int n2 = object.getNextSet(object2[1]);
        int n3 = object.getSize();
        Object object3 = this.counters;
        Arrays.fill(object3, 0);
        Object object4 = this.decodeRowResult;
        object4.setLength(0);
        do {
            Code93Reader.recordPattern((BitArray)object, n2, object3);
            int n4 = Code93Reader.toPattern(object3);
            if (n4 < 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            char c = Code93Reader.patternToChar(n4);
            object4.append(c);
            int n5 = ((int[])object3).length;
            int n6 = n2;
            for (n4 = 0; n4 < n5; ++n4) {
                n6 += object3[n4];
            }
            n5 = object.getNextSet(n6);
            if (c == '*') {
                object4.deleteCharAt(object4.length() - 1);
                int n7 = ((int[])object3).length;
                n6 = n4 = 0;
                while (n4 < n7) {
                    n6 += object3[n4];
                    ++n4;
                }
                if (n5 != n3 && object.get(n5)) {
                    if (object4.length() < 2) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    Code93Reader.checkChecksums(object4);
                    object4.setLength(object4.length() - 2);
                    object = Code93Reader.decodeExtended(object4);
                    float f = (float)(object2[1] + object2[0]) / 2.0f;
                    float f2 = n2;
                    float f3 = (float)n6 / 2.0f;
                    float f4 = n;
                    object2 = new ResultPoint(f, f4);
                    object3 = new ResultPoint(f2 + f3, f4);
                    object4 = BarcodeFormat.CODE_93;
                    return new Result((String)object, null, new ResultPoint[]{object2, object3}, (BarcodeFormat)((Object)object4));
                }
                throw NotFoundException.getNotFoundInstance();
            }
            n2 = n5;
        } while (true);
    }
}
