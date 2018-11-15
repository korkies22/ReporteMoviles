/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Reader;
import com.google.zxing.oned.UPCEANReader;
import com.google.zxing.oned.UPCEANWriter;
import java.util.Map;

public final class EAN13Writer
extends UPCEANWriter {
    private static final int CODE_WIDTH = 95;

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat barcodeFormat, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat != BarcodeFormat.EAN_13) {
            charSequence = new StringBuilder("Can only encode EAN_13, but got ");
            charSequence.append((Object)barcodeFormat);
            throw new IllegalArgumentException(charSequence.toString());
        }
        return super.encode((String)charSequence, barcodeFormat, n, n2, map);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean[] encode(String string) {
        int n;
        block7 : {
            if (string.length() != 13) {
                StringBuilder stringBuilder = new StringBuilder("Requested contents should be 13 digits long, but got ");
                stringBuilder.append(string.length());
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            try {
                if (UPCEANReader.checkStandardUPCEANChecksum(string)) break block7;
                throw new IllegalArgumentException("Contents do not pass checksum");
            }
            catch (FormatException formatException) {
                throw new IllegalArgumentException("Illegal contents");
            }
        }
        int n2 = Integer.parseInt(string.substring(0, 1));
        int n3 = EAN13Reader.FIRST_DIGIT_ENCODINGS[n2];
        boolean[] arrbl = new boolean[95];
        n2 = EAN13Writer.appendPattern(arrbl, 0, UPCEANReader.START_END_PATTERN, true) + 0;
        int n4 = 1;
        while (n4 <= 6) {
            int n5;
            int n6 = n4 + 1;
            n = n5 = Integer.parseInt(string.substring(n4, n6));
            if ((n3 >> 6 - n4 & 1) == 1) {
                n = n5 + 10;
            }
            n2 += EAN13Writer.appendPattern(arrbl, n2, UPCEANReader.L_AND_G_PATTERNS[n], false);
            n4 = n6;
        }
        n2 += EAN13Writer.appendPattern(arrbl, n2, UPCEANReader.MIDDLE_PATTERN, false);
        n4 = 7;
        while (n4 <= 12) {
            n = n4 + 1;
            n4 = Integer.parseInt(string.substring(n4, n));
            n2 += EAN13Writer.appendPattern(arrbl, n2, UPCEANReader.L_PATTERNS[n4], true);
            n4 = n;
        }
        EAN13Writer.appendPattern(arrbl, n2, UPCEANReader.START_END_PATTERN, true);
        return arrbl;
    }
}
