/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.UPCEANReader;
import com.google.zxing.oned.UPCEANWriter;
import com.google.zxing.oned.UPCEReader;
import java.util.Map;

public final class UPCEWriter
extends UPCEANWriter {
    private static final int CODE_WIDTH = 51;

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat barcodeFormat, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat != BarcodeFormat.UPC_E) {
            charSequence = new StringBuilder("Can only encode UPC_E, but got ");
            charSequence.append((Object)barcodeFormat);
            throw new IllegalArgumentException(charSequence.toString());
        }
        return super.encode((String)charSequence, barcodeFormat, n, n2, map);
    }

    @Override
    public boolean[] encode(String string) {
        if (string.length() != 8) {
            StringBuilder stringBuilder = new StringBuilder("Requested contents should be 8 digits long, but got ");
            stringBuilder.append(string.length());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        int n = Integer.parseInt(string.substring(7, 8));
        int n2 = UPCEReader.CHECK_DIGIT_ENCODINGS[n];
        boolean[] arrbl = new boolean[51];
        n = UPCEWriter.appendPattern(arrbl, 0, UPCEANReader.START_END_PATTERN, true) + 0;
        int n3 = 1;
        while (n3 <= 6) {
            int n4;
            int n5 = n3 + 1;
            int n6 = n4 = Integer.parseInt(string.substring(n3, n5));
            if ((n2 >> 6 - n3 & 1) == 1) {
                n6 = n4 + 10;
            }
            n += UPCEWriter.appendPattern(arrbl, n, UPCEANReader.L_AND_G_PATTERNS[n6], false);
            n3 = n5;
        }
        UPCEWriter.appendPattern(arrbl, n, UPCEANReader.END_PATTERN, false);
        return arrbl;
    }
}
