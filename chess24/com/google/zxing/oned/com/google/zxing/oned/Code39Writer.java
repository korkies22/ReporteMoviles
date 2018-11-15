/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code39Reader;
import com.google.zxing.oned.OneDimensionalCodeWriter;
import java.util.Map;

public final class Code39Writer
extends OneDimensionalCodeWriter {
    private static void toIntArray(int n, int[] arrn) {
        for (int i = 0; i < 9; ++i) {
            int n2 = 1;
            if ((1 << 8 - i & n) != 0) {
                n2 = 2;
            }
            arrn[i] = n2;
        }
    }

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat barcodeFormat, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat != BarcodeFormat.CODE_39) {
            charSequence = new StringBuilder("Can only encode CODE_39, but got ");
            charSequence.append((Object)barcodeFormat);
            throw new IllegalArgumentException(charSequence.toString());
        }
        return super.encode((String)charSequence, barcodeFormat, n, n2, map);
    }

    @Override
    public boolean[] encode(String charSequence) {
        int n;
        int n2;
        int n3 = charSequence.length();
        if (n3 > 80) {
            charSequence = new StringBuilder("Requested contents should be less than 80 digits long, but got ");
            charSequence.append(n3);
            throw new IllegalArgumentException(charSequence.toString());
        }
        Object object = new int[9];
        int n4 = n3 + 25;
        for (n = 0; n < n3; ++n) {
            n2 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(charSequence.charAt(n));
            if (n2 < 0) {
                object = new StringBuilder("Bad contents: ");
                object.append((String)charSequence);
                throw new IllegalArgumentException(object.toString());
            }
            Code39Writer.toIntArray(Code39Reader.CHARACTER_ENCODINGS[n2], (int[])object);
            for (n2 = 0; n2 < 9; ++n2) {
                n4 += object[n2];
            }
        }
        boolean[] arrbl = new boolean[n4];
        Code39Writer.toIntArray(Code39Reader.ASTERISK_ENCODING, (int[])object);
        n4 = Code39Writer.appendPattern(arrbl, 0, (int[])object, true);
        int[] arrn = new int[]{1};
        n = n4 + Code39Writer.appendPattern(arrbl, n4, arrn, false);
        for (n4 = 0; n4 < n3; ++n4) {
            n2 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(charSequence.charAt(n4));
            Code39Writer.toIntArray(Code39Reader.CHARACTER_ENCODINGS[n2], (int[])object);
            n += Code39Writer.appendPattern(arrbl, n, (int[])object, true);
            n += Code39Writer.appendPattern(arrbl, n, arrn, false);
        }
        Code39Writer.toIntArray(Code39Reader.ASTERISK_ENCODING, (int[])object);
        Code39Writer.appendPattern(arrbl, n, (int[])object, true);
        return arrbl;
    }
}
