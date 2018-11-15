/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode;

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
import com.google.zxing.common.DetectorResult;
import com.google.zxing.qrcode.decoder.Decoder;
import com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData;
import com.google.zxing.qrcode.detector.Detector;
import java.util.List;
import java.util.Map;

public class QRCodeReader
implements Reader {
    private static final ResultPoint[] NO_POINTS = new ResultPoint[0];
    private final Decoder decoder = new Decoder();

    private static BitMatrix extractPureBits(BitMatrix bitMatrix) throws NotFoundException {
        Object object = bitMatrix.getTopLeftOnBit();
        int[] arrn = bitMatrix.getBottomRightOnBit();
        if (object != null && arrn != null) {
            float f = QRCodeReader.moduleSize((int[])object, bitMatrix);
            int n = object[1];
            int n2 = arrn[1];
            int n3 = object[0];
            int n4 = arrn[0];
            if (n3 < n4 && n < n2) {
                int n5 = n2 - n;
                int n6 = n4;
                if (n5 != n4 - n3) {
                    n6 = n4 = n3 + n5;
                    if (n4 >= bitMatrix.getWidth()) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                }
                int n7 = Math.round((float)(n6 - n3 + 1) / f);
                n5 = Math.round((float)(n5 + 1) / f);
                if (n7 > 0 && n5 > 0) {
                    if (n5 != n7) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    int n8 = (int)(f / 2.0f);
                    n += n8;
                    n4 = n3 + n8;
                    n3 = (int)((float)(n7 - 1) * f) + n4 - n6;
                    n6 = n4;
                    if (n3 > 0) {
                        if (n3 > n8) {
                            throw NotFoundException.getNotFoundInstance();
                        }
                        n6 = n4 - n3;
                    }
                    n2 = (int)((float)(n5 - 1) * f) + n - n2;
                    n4 = n;
                    if (n2 > 0) {
                        if (n2 > n8) {
                            throw NotFoundException.getNotFoundInstance();
                        }
                        n4 = n - n2;
                    }
                    object = new BitMatrix(n7, n5);
                    for (n = 0; n < n5; ++n) {
                        n8 = (int)((float)n * f);
                        for (n2 = 0; n2 < n7; ++n2) {
                            if (!bitMatrix.get((int)((float)n2 * f) + n6, n8 + n4)) continue;
                            object.set(n2, n);
                        }
                    }
                    return object;
                }
                throw NotFoundException.getNotFoundInstance();
            }
            throw NotFoundException.getNotFoundInstance();
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static float moduleSize(int[] arrn, BitMatrix bitMatrix) throws NotFoundException {
        int n;
        int n2 = bitMatrix.getHeight();
        int n3 = bitMatrix.getWidth();
        int n4 = arrn[0];
        boolean bl = true;
        int n5 = 0;
        for (n = arrn[1]; n4 < n3 && n < n2; ++n4, ++n) {
            boolean bl2 = bl;
            int n6 = n5;
            if (bl != bitMatrix.get(n4, n)) {
                n6 = n5 + 1;
                if (n6 == 5) break;
                bl2 = bl ^ true;
            }
            bl = bl2;
            n5 = n6;
        }
        if (n4 != n3 && n != n2) {
            return (float)(n4 - arrn[0]) / 7.0f;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException {
        return this.decode(binaryBitmap, null);
    }

    @Override
    public final Result decode(BinaryBitmap object, Map<DecodeHintType, ?> object2) throws NotFoundException, ChecksumException, FormatException {
        List<byte[]> list;
        if (object2 != null && object2.containsKey((Object)DecodeHintType.PURE_BARCODE)) {
            object = QRCodeReader.extractPureBits(object.getBlackMatrix());
            object = this.decoder.decode((BitMatrix)object, (Map<DecodeHintType, ?>)object2);
            object2 = NO_POINTS;
        } else {
            list = new Detector(object.getBlackMatrix()).detect((Map<DecodeHintType, ?>)object2);
            object = this.decoder.decode(list.getBits(), (Map<DecodeHintType, ?>)object2);
            object2 = list.getPoints();
        }
        if (object.getOther() instanceof QRCodeDecoderMetaData) {
            ((QRCodeDecoderMetaData)object.getOther()).applyMirroredCorrection((ResultPoint[])object2);
        }
        object2 = new Result(object.getText(), object.getRawBytes(), (ResultPoint[])object2, BarcodeFormat.QR_CODE);
        list = object.getByteSegments();
        if (list != null) {
            object2.putMetadata(ResultMetadataType.BYTE_SEGMENTS, list);
        }
        if ((list = object.getECLevel()) != null) {
            object2.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, list);
        }
        if (object.hasStructuredAppend()) {
            object2.putMetadata(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE, object.getStructuredAppendSequenceNumber());
            object2.putMetadata(ResultMetadataType.STRUCTURED_APPEND_PARITY, object.getStructuredAppendParity());
        }
        return object2;
    }

    protected final Decoder getDecoder() {
        return this.decoder;
    }

    @Override
    public void reset() {
    }
}
