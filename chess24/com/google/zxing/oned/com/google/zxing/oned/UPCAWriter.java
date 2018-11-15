/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import java.util.Map;

public final class UPCAWriter
implements Writer {
    private final EAN13Writer subWriter = new EAN13Writer();

    private static String preencode(String charSequence) {
        CharSequence charSequence2;
        int n = charSequence.length();
        if (n == 11) {
            int n2 = 0;
            for (n = 0; n < 11; ++n) {
                char c = charSequence.charAt(n);
                int n3 = n % 2 == 0 ? 3 : 1;
                n2 += (c - 48) * n3;
            }
            charSequence2 = new StringBuilder();
            charSequence2.append((String)charSequence);
            charSequence2.append((1000 - n2) % 10);
            charSequence2 = charSequence2.toString();
        } else {
            charSequence2 = charSequence;
            if (n != 12) {
                charSequence2 = new StringBuilder("Requested contents should be 11 or 12 digits long, but got ");
                charSequence2.append(charSequence.length());
                throw new IllegalArgumentException(charSequence2.toString());
            }
        }
        charSequence = new StringBuilder("0");
        charSequence.append((String)charSequence2);
        return charSequence.toString();
    }

    @Override
    public BitMatrix encode(String string, BarcodeFormat barcodeFormat, int n, int n2) throws WriterException {
        return this.encode(string, barcodeFormat, n, n2, null);
    }

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat barcodeFormat, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat != BarcodeFormat.UPC_A) {
            charSequence = new StringBuilder("Can only encode UPC-A, but got ");
            charSequence.append((Object)barcodeFormat);
            throw new IllegalArgumentException(charSequence.toString());
        }
        return this.subWriter.encode(UPCAWriter.preencode((String)charSequence), BarcodeFormat.EAN_13, n, n2, map);
    }
}
