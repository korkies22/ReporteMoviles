/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.SymbolInfo;

public final class ErrorCorrection {
    private static final int[] ALOG;
    private static final int[][] FACTORS;
    private static final int[] FACTOR_SETS;
    private static final int[] LOG;
    private static final int MODULO_VALUE = 301;

    static {
        FACTOR_SETS = new int[]{5, 7, 10, 11, 12, 14, 18, 20, 24, 28, 36, 42, 48, 56, 62, 68};
        int[] arrn = new int[]{228, 48, 15, 111, 62};
        int[] arrn2 = new int[]{28, 24, 185, 166, 223, 248, 116, 255, 110, 61};
        int[] arrn3 = new int[]{41, 153, 158, 91, 61, 42, 142, 213, 97, 178, 100, 242};
        int[] arrn4 = new int[]{83, 195, 100, 39, 188, 75, 66, 61, 241, 213, 109, 129, 94, 254, 225, 48, 90, 188};
        int[] arrn5 = new int[]{15, 195, 244, 9, 233, 71, 168, 2, 188, 160, 153, 145, 253, 79, 108, 82, 27, 174, 186, 172};
        int[] arrn6 = new int[]{52, 190, 88, 205, 109, 39, 176, 21, 155, 197, 251, 223, 155, 21, 5, 172, 254, 124, 12, 181, 184, 96, 50, 193};
        int[] arrn7 = new int[]{175, 9, 223, 238, 12, 17, 220, 208, 100, 29, 175, 170, 230, 192, 215, 235, 150, 159, 36, 223, 38, 200, 132, 54, 228, 146, 218, 234, 117, 203, 29, 232, 144, 238, 22, 150, 201, 117, 62, 207, 164, 13, 137, 245, 127, 67, 247, 28, 155, 43, 203, 107, 233, 53, 143, 46};
        FACTORS = new int[][]{arrn, {23, 68, 144, 134, 240, 92, 254}, arrn2, {175, 138, 205, 12, 194, 168, 39, 245, 60, 97, 120}, arrn3, {156, 97, 192, 252, 95, 9, 157, 119, 138, 45, 18, 186, 83, 185}, arrn4, arrn5, arrn6, {211, 231, 43, 97, 71, 96, 103, 174, 37, 151, 170, 53, 75, 34, 249, 121, 17, 138, 110, 213, 141, 136, 120, 151, 233, 168, 93, 255}, {245, 127, 242, 218, 130, 250, 162, 181, 102, 120, 84, 179, 220, 251, 80, 182, 229, 18, 2, 4, 68, 33, 101, 137, 95, 119, 115, 44, 175, 184, 59, 25, 225, 98, 81, 112}, {77, 193, 137, 31, 19, 38, 22, 153, 247, 105, 122, 2, 245, 133, 242, 8, 175, 95, 100, 9, 167, 105, 214, 111, 57, 121, 21, 1, 253, 57, 54, 101, 248, 202, 69, 50, 150, 177, 226, 5, 9, 5}, {245, 132, 172, 223, 96, 32, 117, 22, 238, 133, 238, 231, 205, 188, 237, 87, 191, 106, 16, 147, 118, 23, 37, 90, 170, 205, 131, 88, 120, 100, 66, 138, 186, 240, 82, 44, 176, 87, 187, 147, 160, 175, 69, 213, 92, 253, 225, 19}, arrn7, {242, 93, 169, 50, 144, 210, 39, 118, 202, 188, 201, 189, 143, 108, 196, 37, 185, 112, 134, 230, 245, 63, 197, 190, 250, 106, 185, 221, 175, 64, 114, 71, 161, 44, 147, 6, 27, 218, 51, 63, 87, 10, 40, 130, 188, 17, 163, 31, 176, 170, 4, 107, 232, 7, 94, 166, 224, 124, 86, 47, 11, 204}, {220, 228, 173, 89, 251, 149, 159, 56, 89, 33, 147, 244, 154, 36, 73, 127, 213, 136, 248, 180, 234, 197, 158, 177, 68, 122, 93, 213, 15, 160, 227, 236, 66, 139, 153, 185, 202, 167, 179, 25, 220, 232, 96, 210, 231, 136, 223, 239, 181, 241, 59, 52, 172, 25, 49, 232, 211, 189, 64, 54, 108, 153, 132, 63, 96, 103, 82, 186}};
        LOG = new int[256];
        ALOG = new int[255];
        int n = 1;
        for (int i = 0; i < 255; ++i) {
            int n2;
            ErrorCorrection.ALOG[i] = n;
            ErrorCorrection.LOG[n] = i;
            n = n2 = n << 1;
            if (n2 < 256) continue;
            n = n2 ^ 301;
        }
    }

    private ErrorCorrection() {
    }

    private static String createECCBlock(CharSequence charSequence, int n) {
        return ErrorCorrection.createECCBlock(charSequence, 0, charSequence.length(), n);
    }

    private static String createECCBlock(CharSequence arrc, int n, int n2, int n3) {
        int n4;
        block7 : {
            int n5 = 0;
            for (n4 = 0; n4 < FACTOR_SETS.length; ++n4) {
                if (FACTOR_SETS[n4] != n3) {
                    continue;
                }
                break block7;
            }
            n4 = -1;
        }
        if (n4 < 0) {
            arrc = new StringBuilder("Illegal number of error correction codewords specified: ");
            arrc.append(n3);
            throw new IllegalArgumentException(arrc.toString());
        }
        int[] arrn = FACTORS[n4];
        char[] arrc2 = new char[n3];
        for (n4 = 0; n4 < n3; ++n4) {
            arrc2[n4] = '\u0000';
        }
        for (n4 = n; n4 < n + n2; ++n4) {
            int n6;
            int n7 = arrc2[n6] ^ arrc.charAt(n4);
            for (n6 = n3 - 1; n6 > 0; --n6) {
                arrc2[n6] = n7 != 0 && arrn[n6] != 0 ? (char)(arrc2[n6 - 1] ^ ALOG[(LOG[n7] + LOG[arrn[n6]]) % 255]) : arrc2[n6 - 1];
            }
            arrc2[0] = n7 != 0 && arrn[0] != 0 ? (char)ALOG[(LOG[n7] + LOG[arrn[0]]) % 255] : (char)'\u0000';
        }
        arrc = new char[n3];
        for (n = n5; n < n3; ++n) {
            arrc[n] = arrc2[n3 - n - 1];
        }
        return String.valueOf(arrc);
    }

    public static String encodeECC200(String string, SymbolInfo symbolInfo) {
        if (string.length() != symbolInfo.getDataCapacity()) {
            throw new IllegalArgumentException("The number of codewords does not match the selected symbol");
        }
        StringBuilder stringBuilder = new StringBuilder(symbolInfo.getDataCapacity() + symbolInfo.getErrorCodewords());
        stringBuilder.append(string);
        int n = symbolInfo.getInterleavedBlockCount();
        if (n == 1) {
            stringBuilder.append(ErrorCorrection.createECCBlock(string, symbolInfo.getErrorCodewords()));
        } else {
            int n2;
            stringBuilder.setLength(stringBuilder.capacity());
            int[] arrn = new int[n];
            int[] arrn2 = new int[n];
            Object object = new int[n];
            int n3 = 0;
            while (n3 < n) {
                n2 = n3 + 1;
                arrn[n3] = symbolInfo.getDataLengthForInterleavedBlock(n2);
                arrn2[n3] = symbolInfo.getErrorLengthForInterleavedBlock(n2);
                object[n3] = 0;
                if (n3 > 0) {
                    object[n3] = object[n3 - 1] + arrn[n3];
                }
                n3 = n2;
            }
            for (n3 = 0; n3 < n; ++n3) {
                object = new StringBuilder(arrn[n3]);
                for (n2 = n3; n2 < symbolInfo.getDataCapacity(); n2 += n) {
                    object.append(string.charAt(n2));
                }
                object = ErrorCorrection.createECCBlock(object.toString(), arrn2[n3]);
                int n4 = n3;
                n2 = 0;
                while (n4 < arrn2[n3] * n) {
                    stringBuilder.setCharAt(symbolInfo.getDataCapacity() + n4, object.charAt(n2));
                    n4 += n;
                    ++n2;
                }
            }
        }
        return stringBuilder.toString();
    }
}
