/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.pdf417;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.encoder.BarcodeMatrix;
import com.google.zxing.pdf417.encoder.Compaction;
import com.google.zxing.pdf417.encoder.Dimensions;
import com.google.zxing.pdf417.encoder.PDF417;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Map;

public final class PDF417Writer
implements Writer {
    static final int DEFAULT_ERROR_CORRECTION_LEVEL = 2;
    static final int WHITE_SPACE = 30;

    private static BitMatrix bitMatrixFromEncoder(PDF417 arrby, String arrby2, int n, int n2, int n3, int n4) throws WriterException {
        arrby.generateBarcodeLogic((String)arrby2, n);
        arrby2 = arrby.getBarcodeMatrix().getScaledMatrix(1, 4);
        n = n3 > n2 ? 1 : 0;
        int n5 = arrby2[0].length < arrby2.length ? 1 : 0;
        if ((n ^ n5) != 0) {
            arrby2 = PDF417Writer.rotateArray(arrby2);
            n = 1;
        } else {
            n = 0;
        }
        if ((n2 /= arrby2[0].length) >= (n3 /= arrby2.length)) {
            n2 = n3;
        }
        if (n2 > 1) {
            arrby = arrby2 = arrby.getBarcodeMatrix().getScaledMatrix(n2, n2 << 2);
            if (n != 0) {
                arrby = PDF417Writer.rotateArray(arrby2);
            }
            return PDF417Writer.bitMatrixFrombitArray(arrby, n4);
        }
        return PDF417Writer.bitMatrixFrombitArray(arrby2, n4);
    }

    private static BitMatrix bitMatrixFrombitArray(byte[][] arrby, int n) {
        int n2 = arrby[0].length;
        int n3 = 2 * n;
        BitMatrix bitMatrix = new BitMatrix(n2 + n3, arrby.length + n3);
        bitMatrix.clear();
        n2 = bitMatrix.getHeight() - n - 1;
        n3 = 0;
        while (n3 < arrby.length) {
            for (int i = 0; i < arrby[0].length; ++i) {
                if (arrby[n3][i] != 1) continue;
                bitMatrix.set(i + n, n2);
            }
            ++n3;
            --n2;
        }
        return bitMatrix;
    }

    private static byte[][] rotateArray(byte[][] arrby) {
        byte[][] arrby2 = (byte[][])Array.newInstance(Byte.TYPE, arrby[0].length, arrby.length);
        for (int i = 0; i < arrby.length; ++i) {
            int n = arrby.length;
            for (int j = 0; j < arrby[0].length; ++j) {
                arrby2[j][n - i - 1] = arrby[i][j];
            }
        }
        return arrby2;
    }

    @Override
    public BitMatrix encode(String string, BarcodeFormat barcodeFormat, int n, int n2) throws WriterException {
        return this.encode(string, barcodeFormat, n, n2, null);
    }

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat object, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        int n3;
        if (object != BarcodeFormat.PDF_417) {
            charSequence = new StringBuilder("Can only encode PDF_417, but got ");
            charSequence.append(object);
            throw new IllegalArgumentException(charSequence.toString());
        }
        object = new PDF417();
        int n4 = 30;
        int n5 = n3 = 2;
        int n6 = n4;
        if (map != null) {
            if (map.containsKey((Object)EncodeHintType.PDF417_COMPACT)) {
                object.setCompact(Boolean.valueOf(map.get((Object)EncodeHintType.PDF417_COMPACT).toString()));
            }
            if (map.containsKey((Object)EncodeHintType.PDF417_COMPACTION)) {
                object.setCompaction(Compaction.valueOf(map.get((Object)EncodeHintType.PDF417_COMPACTION).toString()));
            }
            if (map.containsKey((Object)EncodeHintType.PDF417_DIMENSIONS)) {
                Dimensions dimensions = (Dimensions)map.get((Object)EncodeHintType.PDF417_DIMENSIONS);
                object.setDimensions(dimensions.getMaxCols(), dimensions.getMinCols(), dimensions.getMaxRows(), dimensions.getMinRows());
            }
            if (map.containsKey((Object)EncodeHintType.MARGIN)) {
                n4 = Integer.parseInt(map.get((Object)EncodeHintType.MARGIN).toString());
            }
            if (map.containsKey((Object)EncodeHintType.ERROR_CORRECTION)) {
                n3 = Integer.parseInt(map.get((Object)EncodeHintType.ERROR_CORRECTION).toString());
            }
            n5 = n3;
            n6 = n4;
            if (map.containsKey((Object)EncodeHintType.CHARACTER_SET)) {
                object.setEncoding(Charset.forName(map.get((Object)EncodeHintType.CHARACTER_SET).toString()));
                n6 = n4;
                n5 = n3;
            }
        }
        return PDF417Writer.bitMatrixFromEncoder((PDF417)object, (String)charSequence, n5, n, n2, n6);
    }
}
