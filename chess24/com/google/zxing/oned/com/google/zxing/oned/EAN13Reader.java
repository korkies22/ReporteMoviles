/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.UPCEANReader;

public final class EAN13Reader
extends UPCEANReader {
    static final int[] FIRST_DIGIT_ENCODINGS = new int[]{0, 11, 13, 14, 19, 25, 28, 21, 22, 26};
    private final int[] decodeMiddleCounters = new int[4];

    private static void determineFirstDigit(StringBuilder stringBuilder, int n) throws NotFoundException {
        for (int i = 0; i < 10; ++i) {
            if (n != FIRST_DIGIT_ENCODINGS[i]) continue;
            stringBuilder.insert(0, (char)(i + 48));
            return;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    protected int decodeMiddle(BitArray bitArray, int[] arrn, StringBuilder stringBuilder) throws NotFoundException {
        int n;
        int n2;
        int[] arrn2 = this.decodeMiddleCounters;
        arrn2[0] = 0;
        arrn2[1] = 0;
        arrn2[2] = 0;
        arrn2[3] = 0;
        int n3 = bitArray.getSize();
        int n4 = arrn[1];
        int n5 = n2 = 0;
        while (n2 < 6 && n4 < n3) {
            int n6 = EAN13Reader.decodeDigit(bitArray, arrn2, n4, L_AND_G_PATTERNS);
            stringBuilder.append((char)(48 + n6 % 10));
            int n7 = arrn2.length;
            for (n = 0; n < n7; ++n) {
                n4 += arrn2[n];
            }
            n = n5;
            if (n6 >= 10) {
                n = 1 << 5 - n2 | n5;
            }
            ++n2;
            n5 = n;
        }
        EAN13Reader.determineFirstDigit(stringBuilder, n5);
        n4 = EAN13Reader.findGuardPattern(bitArray, n4, true, MIDDLE_PATTERN)[1];
        for (n2 = 0; n2 < 6 && n4 < n3; ++n2) {
            stringBuilder.append((char)(EAN13Reader.decodeDigit(bitArray, arrn2, n4, L_PATTERNS) + 48));
            n = arrn2.length;
            for (n5 = 0; n5 < n; ++n5) {
                n4 += arrn2[n5];
            }
        }
        return n4;
    }

    @Override
    BarcodeFormat getBarcodeFormat() {
        return BarcodeFormat.EAN_13;
    }
}
