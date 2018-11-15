/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public abstract class OneDimensionalCodeWriter
implements Writer {
    protected static int appendPattern(boolean[] arrbl, int n, int[] arrn, boolean bl) {
        int n2;
        int n3 = arrn.length;
        int n4 = n2 = 0;
        while (n2 < n3) {
            int n5 = arrn[n2];
            int n6 = 0;
            while (n6 < n5) {
                arrbl[n] = bl;
                ++n6;
                ++n;
            }
            n4 += n5;
            bl = !bl;
            ++n2;
        }
        return n4;
    }

    private static BitMatrix renderResult(boolean[] arrbl, int n, int n2, int n3) {
        int n4 = arrbl.length;
        int n5 = n3 + n4;
        int n6 = Math.max(n, n5);
        n3 = Math.max(1, n2);
        n5 = n6 / n5;
        n = (n6 - n4 * n5) / 2;
        BitMatrix bitMatrix = new BitMatrix(n6, n3);
        n2 = 0;
        while (n2 < n4) {
            if (arrbl[n2]) {
                bitMatrix.setRegion(n, 0, n5, n3);
            }
            ++n2;
            n += n5;
        }
        return bitMatrix;
    }

    @Override
    public final BitMatrix encode(String string, BarcodeFormat barcodeFormat, int n, int n2) throws WriterException {
        return this.encode(string, barcodeFormat, n, n2, null);
    }

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat barcodeFormat, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        if (charSequence.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (n >= 0 && n2 >= 0) {
            int n3;
            int n4 = n3 = this.getDefaultMargin();
            if (map != null) {
                n4 = n3;
                if (map.containsKey((Object)EncodeHintType.MARGIN)) {
                    n4 = Integer.parseInt(map.get((Object)EncodeHintType.MARGIN).toString());
                }
            }
            return OneDimensionalCodeWriter.renderResult(this.encode((String)charSequence), n, n2, n4);
        }
        charSequence = new StringBuilder("Negative size is not allowed. Input: ");
        charSequence.append(n);
        charSequence.append('x');
        charSequence.append(n2);
        throw new IllegalArgumentException(charSequence.toString());
    }

    public abstract boolean[] encode(String var1);

    public int getDefaultMargin() {
        return 10;
    }
}
