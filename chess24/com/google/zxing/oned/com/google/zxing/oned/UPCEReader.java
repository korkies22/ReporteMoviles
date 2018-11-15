/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.UPCEANReader;

public final class UPCEReader
extends UPCEANReader {
    static final int[] CHECK_DIGIT_ENCODINGS = new int[]{56, 52, 50, 49, 44, 38, 35, 42, 41, 37};
    private static final int[] MIDDLE_END_PATTERN = new int[]{1, 1, 1, 1, 1, 1};
    private static final int[][] NUMSYS_AND_CHECK_DIGIT_PATTERNS = new int[][]{{56, 52, 50, 49, 44, 38, 35, 42, 41, 37}, {7, 11, 13, 14, 19, 25, 28, 21, 22, 26}};
    private final int[] decodeMiddleCounters = new int[4];

    public static String convertUPCEtoUPCA(String string) {
        char[] arrc = new char[6];
        string.getChars(1, 7, arrc, 0);
        StringBuilder stringBuilder = new StringBuilder(12);
        stringBuilder.append(string.charAt(0));
        char c = arrc[5];
        switch (c) {
            default: {
                stringBuilder.append(arrc, 0, 5);
                stringBuilder.append("0000");
                stringBuilder.append(c);
                break;
            }
            case '4': {
                stringBuilder.append(arrc, 0, 4);
                stringBuilder.append("00000");
                stringBuilder.append(arrc[4]);
                break;
            }
            case '3': {
                stringBuilder.append(arrc, 0, 3);
                stringBuilder.append("00000");
                stringBuilder.append(arrc, 3, 2);
                break;
            }
            case '0': 
            case '1': 
            case '2': {
                stringBuilder.append(arrc, 0, 2);
                stringBuilder.append(c);
                stringBuilder.append("0000");
                stringBuilder.append(arrc, 2, 3);
            }
        }
        stringBuilder.append(string.charAt(7));
        return stringBuilder.toString();
    }

    private static void determineNumSysAndCheckDigit(StringBuilder stringBuilder, int n) throws NotFoundException {
        for (int i = 0; i <= 1; ++i) {
            for (int j = 0; j < 10; ++j) {
                if (n != NUMSYS_AND_CHECK_DIGIT_PATTERNS[i][j]) continue;
                stringBuilder.insert(0, (char)(i + 48));
                stringBuilder.append((char)(j + 48));
                return;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    protected boolean checkChecksum(String string) throws FormatException {
        return super.checkChecksum(UPCEReader.convertUPCEtoUPCA(string));
    }

    @Override
    protected int[] decodeEnd(BitArray bitArray, int n) throws NotFoundException {
        return UPCEReader.findGuardPattern(bitArray, n, true, MIDDLE_END_PATTERN);
    }

    @Override
    protected int decodeMiddle(BitArray bitArray, int[] arrn, StringBuilder stringBuilder) throws NotFoundException {
        int n;
        int[] arrn2 = this.decodeMiddleCounters;
        arrn2[0] = 0;
        arrn2[1] = 0;
        arrn2[2] = 0;
        arrn2[3] = 0;
        int n2 = bitArray.getSize();
        int n3 = arrn[1];
        int n4 = n = 0;
        while (n < 6 && n3 < n2) {
            int n5;
            int n6 = UPCEReader.decodeDigit(bitArray, arrn2, n3, L_AND_G_PATTERNS);
            stringBuilder.append((char)(48 + n6 % 10));
            int n7 = arrn2.length;
            for (n5 = 0; n5 < n7; ++n5) {
                n3 += arrn2[n5];
            }
            n5 = n4;
            if (n6 >= 10) {
                n5 = 1 << 5 - n | n4;
            }
            ++n;
            n4 = n5;
        }
        UPCEReader.determineNumSysAndCheckDigit(stringBuilder, n4);
        return n3;
    }

    @Override
    BarcodeFormat getBarcodeFormat() {
        return BarcodeFormat.UPC_E;
    }
}
