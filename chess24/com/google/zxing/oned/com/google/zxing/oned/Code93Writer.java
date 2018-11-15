/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code93Reader;
import com.google.zxing.oned.OneDimensionalCodeWriter;
import java.util.Map;

public class Code93Writer
extends OneDimensionalCodeWriter {
    protected static int appendPattern(boolean[] arrbl, int n, int[] arrn, boolean bl) {
        int n2 = arrn.length;
        int n3 = 0;
        while (n3 < n2) {
            bl = arrn[n3] != 0;
            arrbl[n] = bl;
            ++n3;
            ++n;
        }
        return 9;
    }

    private static int computeChecksumIndex(String string, int n) {
        int n2 = 0;
        int n3 = 1;
        for (int i = string.length() - 1; i >= 0; --i) {
            int n4;
            n2 += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(string.charAt(i)) * n3;
            n3 = n4 = n3 + 1;
            if (n4 <= n) continue;
            n3 = 1;
        }
        return n2 % 47;
    }

    private static void toIntArray(int n, int[] arrn) {
        for (int i = 0; i < 9; ++i) {
            int n2 = 1;
            if ((1 << 8 - i & n) == 0) {
                n2 = 0;
            }
            arrn[i] = n2;
        }
    }

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat barcodeFormat, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat != BarcodeFormat.CODE_93) {
            charSequence = new StringBuilder("Can only encode CODE_93, but got ");
            charSequence.append((Object)barcodeFormat);
            throw new IllegalArgumentException(charSequence.toString());
        }
        return super.encode((String)charSequence, barcodeFormat, n, n2, map);
    }

    @Override
    public boolean[] encode(String charSequence) {
        int n;
        int n2 = charSequence.length();
        if (n2 > 80) {
            charSequence = new StringBuilder("Requested contents should be less than 80 digits long, but got ");
            charSequence.append(n2);
            throw new IllegalArgumentException(charSequence.toString());
        }
        int[] arrn = new int[9];
        boolean[] arrbl = new boolean[(charSequence.length() + 2 + 2) * 9 + 1];
        Code93Writer.toIntArray(Code93Reader.CHARACTER_ENCODINGS[47], arrn);
        int n3 = Code93Writer.appendPattern(arrbl, 0, arrn, true);
        for (n = 0; n < n2; ++n) {
            int n4 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(charSequence.charAt(n));
            Code93Writer.toIntArray(Code93Reader.CHARACTER_ENCODINGS[n4], arrn);
            n3 += Code93Writer.appendPattern(arrbl, n3, arrn, true);
        }
        n = Code93Writer.computeChecksumIndex((String)charSequence, 20);
        Code93Writer.toIntArray(Code93Reader.CHARACTER_ENCODINGS[n], arrn);
        n3 += Code93Writer.appendPattern(arrbl, n3, arrn, true);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".charAt(n));
        n = Code93Writer.computeChecksumIndex(stringBuilder.toString(), 15);
        Code93Writer.toIntArray(Code93Reader.CHARACTER_ENCODINGS[n], arrn);
        n3 += Code93Writer.appendPattern(arrbl, n3, arrn, true);
        Code93Writer.toIntArray(Code93Reader.CHARACTER_ENCODINGS[47], arrn);
        arrbl[n3 + Code93Writer.appendPattern((boolean[])arrbl, (int)n3, (int[])arrn, (boolean)true)] = true;
        return arrbl;
    }
}
