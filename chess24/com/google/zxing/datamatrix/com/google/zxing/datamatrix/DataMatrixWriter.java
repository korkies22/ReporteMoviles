/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Dimension;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.encoder.DefaultPlacement;
import com.google.zxing.datamatrix.encoder.ErrorCorrection;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;
import com.google.zxing.datamatrix.encoder.SymbolInfo;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import java.util.Map;

public final class DataMatrixWriter
implements Writer {
    private static BitMatrix convertByteMatrixToBitMatrix(ByteMatrix byteMatrix) {
        int n = byteMatrix.getWidth();
        int n2 = byteMatrix.getHeight();
        BitMatrix bitMatrix = new BitMatrix(n, n2);
        bitMatrix.clear();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                if (byteMatrix.get(i, j) != 1) continue;
                bitMatrix.set(i, j);
            }
        }
        return bitMatrix;
    }

    private static BitMatrix encodeLowLevel(DefaultPlacement defaultPlacement, SymbolInfo symbolInfo) {
        int n;
        int n2 = symbolInfo.getSymbolDataWidth();
        int n3 = symbolInfo.getSymbolDataHeight();
        ByteMatrix byteMatrix = new ByteMatrix(symbolInfo.getSymbolWidth(), symbolInfo.getSymbolHeight());
        int n4 = n = 0;
        while (n < n3) {
            int n5;
            boolean bl;
            int n6 = n4;
            if (n % symbolInfo.matrixHeight == 0) {
                n5 = n6 = 0;
                while (n6 < symbolInfo.getSymbolWidth()) {
                    bl = n6 % 2 == 0;
                    byteMatrix.set(n5, n4, bl);
                    ++n5;
                    ++n6;
                }
                n6 = n4 + 1;
            }
            n4 = n5 = 0;
            while (n5 < n2) {
                int n7 = n4;
                if (n5 % symbolInfo.matrixWidth == 0) {
                    byteMatrix.set(n4, n6, true);
                    n7 = n4 + 1;
                }
                byteMatrix.set(n7, n6, defaultPlacement.getBit(n5, n));
                n4 = ++n7;
                if (n5 % symbolInfo.matrixWidth == symbolInfo.matrixWidth - 1) {
                    bl = n % 2 == 0;
                    byteMatrix.set(n7, n6, bl);
                    n4 = n7 + 1;
                }
                ++n5;
            }
            n4 = n5 = n6 + 1;
            if (n % symbolInfo.matrixHeight == symbolInfo.matrixHeight - 1) {
                n6 = n4 = 0;
                while (n4 < symbolInfo.getSymbolWidth()) {
                    byteMatrix.set(n6, n5, true);
                    ++n6;
                    ++n4;
                }
                n4 = n5 + 1;
            }
            ++n;
        }
        return DataMatrixWriter.convertByteMatrixToBitMatrix(byteMatrix);
    }

    @Override
    public BitMatrix encode(String string, BarcodeFormat barcodeFormat, int n, int n2) {
        return this.encode(string, barcodeFormat, n, n2, null);
    }

    @Override
    public BitMatrix encode(String object, BarcodeFormat object2, int n, int n2, Map<EncodeHintType, ?> object3) {
        if (object.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (object2 != BarcodeFormat.DATA_MATRIX) {
            object = new StringBuilder("Can only encode DATA_MATRIX, but got ");
            object.append(object2);
            throw new IllegalArgumentException(object.toString());
        }
        if (n >= 0 && n2 >= 0) {
            Object object4;
            object2 = SymbolShapeHint.FORCE_NONE;
            Dimension dimension = null;
            if (object3 != null) {
                Object object5 = (SymbolShapeHint)((Object)object3.get((Object)EncodeHintType.DATA_MATRIX_SHAPE));
                if (object5 != null) {
                    object2 = object5;
                }
                if ((object5 = (Dimension)object3.get((Object)EncodeHintType.MIN_SIZE)) == null) {
                    object5 = null;
                }
                Dimension dimension2 = (Dimension)object3.get((Object)EncodeHintType.MAX_SIZE);
                object3 = object2;
                object4 = object5;
                if (dimension2 != null) {
                    dimension = dimension2;
                    object3 = object2;
                    object4 = object5;
                }
            } else {
                object4 = null;
                object3 = object2;
            }
            object = HighLevelEncoder.encodeHighLevel((String)object, (SymbolShapeHint)((Object)object3), object4, dimension);
            object2 = SymbolInfo.lookup(object.length(), (SymbolShapeHint)((Object)object3), object4, dimension, true);
            object = new DefaultPlacement(ErrorCorrection.encodeECC200((String)object, (SymbolInfo)object2), object2.getSymbolDataWidth(), object2.getSymbolDataHeight());
            object.place();
            return DataMatrixWriter.encodeLowLevel((DefaultPlacement)object, (SymbolInfo)object2);
        }
        object = new StringBuilder("Requested dimensions are too small: ");
        object.append(n);
        object.append('x');
        object.append(n2);
        throw new IllegalArgumentException(object.toString());
    }
}
