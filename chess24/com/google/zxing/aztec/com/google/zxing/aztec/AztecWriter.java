/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.aztec;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.aztec.encoder.AztecCode;
import com.google.zxing.aztec.encoder.Encoder;
import com.google.zxing.common.BitMatrix;
import java.nio.charset.Charset;
import java.util.Map;

public final class AztecWriter
implements Writer {
    private static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");

    private static BitMatrix encode(String charSequence, BarcodeFormat barcodeFormat, int n, int n2, Charset charset, int n3, int n4) {
        if (barcodeFormat != BarcodeFormat.AZTEC) {
            charSequence = new StringBuilder("Can only encode AZTEC, but got ");
            charSequence.append((Object)barcodeFormat);
            throw new IllegalArgumentException(charSequence.toString());
        }
        return AztecWriter.renderResult(Encoder.encode(charSequence.getBytes(charset), n3, n4), n, n2);
    }

    private static BitMatrix renderResult(AztecCode object, int n, int n2) {
        if ((object = object.getMatrix()) == null) {
            throw new IllegalStateException();
        }
        int n3 = object.getWidth();
        int n4 = object.getHeight();
        int n5 = Math.max(n, n3);
        n2 = Math.max(n2, n4);
        int n6 = Math.min(n5 / n3, n2 / n4);
        int n7 = (n5 - n3 * n6) / 2;
        n = (n2 - n4 * n6) / 2;
        BitMatrix bitMatrix = new BitMatrix(n5, n2);
        n2 = 0;
        while (n2 < n4) {
            int n8 = 0;
            n5 = n7;
            while (n8 < n3) {
                if (object.get(n8, n2)) {
                    bitMatrix.setRegion(n5, n, n6, n6);
                }
                ++n8;
                n5 += n6;
            }
            ++n2;
            n += n6;
        }
        return bitMatrix;
    }

    @Override
    public BitMatrix encode(String string, BarcodeFormat barcodeFormat, int n, int n2) {
        return this.encode(string, barcodeFormat, n, n2, null);
    }

    @Override
    public BitMatrix encode(String string, BarcodeFormat barcodeFormat, int n, int n2, Map<EncodeHintType, ?> map) {
        Charset charset = DEFAULT_CHARSET;
        int n3 = 33;
        int n4 = 0;
        Charset charset2 = charset;
        int n5 = n3;
        int n6 = n4;
        if (map != null) {
            if (map.containsKey((Object)EncodeHintType.CHARACTER_SET)) {
                charset = Charset.forName(map.get((Object)EncodeHintType.CHARACTER_SET).toString());
            }
            if (map.containsKey((Object)EncodeHintType.ERROR_CORRECTION)) {
                n3 = Integer.parseInt(map.get((Object)EncodeHintType.ERROR_CORRECTION).toString());
            }
            charset2 = charset;
            n5 = n3;
            n6 = n4;
            if (map.containsKey((Object)EncodeHintType.AZTEC_LAYERS)) {
                n6 = Integer.parseInt(map.get((Object)EncodeHintType.AZTEC_LAYERS).toString());
                n5 = n3;
                charset2 = charset;
            }
        }
        return AztecWriter.encode(string, barcodeFormat, n, n2, charset2, n5, n6);
    }
}
