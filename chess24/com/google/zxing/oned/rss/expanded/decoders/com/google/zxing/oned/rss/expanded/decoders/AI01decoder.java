/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;
import com.google.zxing.oned.rss.expanded.decoders.GeneralAppIdDecoder;

abstract class AI01decoder
extends AbstractExpandedDecoder {
    static final int GTIN_SIZE = 40;

    AI01decoder(BitArray bitArray) {
        super(bitArray);
    }

    private static void appendCheckDigit(StringBuilder stringBuilder, int n) {
        int n2;
        int n3 = 0;
        int n4 = n2 = 0;
        while (n2 < 13) {
            int n5;
            int n6 = n5 = stringBuilder.charAt(n2 + n) - 48;
            if ((n2 & 1) == 0) {
                n6 = n5 * 3;
            }
            n4 += n6;
            ++n2;
        }
        n = 10 - n4 % 10;
        if (n == 10) {
            n = n3;
        }
        stringBuilder.append(n);
    }

    final void encodeCompressedGtin(StringBuilder stringBuilder, int n) {
        stringBuilder.append("(01)");
        int n2 = stringBuilder.length();
        stringBuilder.append('9');
        this.encodeCompressedGtinWithoutAI(stringBuilder, n, n2);
    }

    final void encodeCompressedGtinWithoutAI(StringBuilder stringBuilder, int n, int n2) {
        for (int i = 0; i < 4; ++i) {
            int n3 = this.getGeneralDecoder().extractNumericValueFromBitArray(i * 10 + n, 10);
            if (n3 / 100 == 0) {
                stringBuilder.append('0');
            }
            if (n3 / 10 == 0) {
                stringBuilder.append('0');
            }
            stringBuilder.append(n3);
        }
        AI01decoder.appendCheckDigit(stringBuilder, n2);
    }
}
