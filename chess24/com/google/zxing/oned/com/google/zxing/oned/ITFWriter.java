/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.ITFReader;
import com.google.zxing.oned.OneDimensionalCodeWriter;
import java.util.Map;

public final class ITFWriter
extends OneDimensionalCodeWriter {
    private static final int[] END_PATTERN;
    private static final int[] START_PATTERN;

    static {
        START_PATTERN = new int[]{1, 1, 1, 1};
        END_PATTERN = new int[]{3, 1, 1};
    }

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat barcodeFormat, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat != BarcodeFormat.ITF) {
            charSequence = new StringBuilder("Can only encode ITF, but got ");
            charSequence.append((Object)barcodeFormat);
            throw new IllegalArgumentException(charSequence.toString());
        }
        return super.encode((String)charSequence, barcodeFormat, n, n2, map);
    }

    @Override
    public boolean[] encode(String charSequence) {
        int n = charSequence.length();
        if (n % 2 != 0) {
            throw new IllegalArgumentException("The length of the input should be even");
        }
        if (n > 80) {
            charSequence = new StringBuilder("Requested contents should be less than 80 digits long, but got ");
            charSequence.append(n);
            throw new IllegalArgumentException(charSequence.toString());
        }
        boolean[] arrbl = new boolean[9 + n * 9];
        int n2 = ITFWriter.appendPattern(arrbl, 0, START_PATTERN, true);
        for (int i = 0; i < n; i += 2) {
            int n3 = Character.digit(charSequence.charAt(i), 10);
            int n4 = Character.digit(charSequence.charAt(i + 1), 10);
            int[] arrn = new int[18];
            for (int j = 0; j < 5; ++j) {
                int n5 = 2 * j;
                arrn[n5] = ITFReader.PATTERNS[n3][j];
                arrn[n5 + 1] = ITFReader.PATTERNS[n4][j];
            }
            n2 += ITFWriter.appendPattern(arrbl, n2, arrn, true);
        }
        ITFWriter.appendPattern(arrbl, n2, END_PATTERN, true);
        return arrbl;
    }
}
