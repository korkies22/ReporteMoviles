/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.maxicode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.maxicode.decoder.Decoder;
import java.util.Map;

public final class MaxiCodeReader
implements Reader {
    private static final int MATRIX_HEIGHT = 33;
    private static final int MATRIX_WIDTH = 30;
    private static final ResultPoint[] NO_POINTS = new ResultPoint[0];
    private final Decoder decoder = new Decoder();

    private static BitMatrix extractPureBits(BitMatrix bitMatrix) throws NotFoundException {
        Object object = bitMatrix.getEnclosingRectangle();
        if (object == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        int n = object[0];
        int n2 = object[1];
        int n3 = object[2];
        int n4 = object[3];
        object = new BitMatrix(30, 33);
        for (int i = 0; i < 33; ++i) {
            int n5 = (i * n4 + n4 / 2) / 33;
            for (int j = 0; j < 30; ++j) {
                if (!bitMatrix.get((j * n3 + n3 / 2 + (i & 1) * n3 / 2) / 30 + n, n5 + n2)) continue;
                object.set(j, i);
            }
        }
        return object;
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException {
        return this.decode(binaryBitmap, null);
    }

    @Override
    public Result decode(BinaryBitmap object, Map<DecodeHintType, ?> object2) throws NotFoundException, ChecksumException, FormatException {
        if (object2 != null && object2.containsKey((Object)DecodeHintType.PURE_BARCODE)) {
            object = MaxiCodeReader.extractPureBits(object.getBlackMatrix());
            object2 = this.decoder.decode((BitMatrix)object, (Map<DecodeHintType, ?>)object2);
            object = new Result(object2.getText(), object2.getRawBytes(), NO_POINTS, BarcodeFormat.MAXICODE);
            if ((object2 = object2.getECLevel()) != null) {
                object.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, object2);
            }
            return object;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    public void reset() {
    }
}
