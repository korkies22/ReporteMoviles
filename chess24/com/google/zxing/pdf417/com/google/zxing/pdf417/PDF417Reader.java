/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.pdf417;

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
import com.google.zxing.multi.MultipleBarcodeReader;
import com.google.zxing.pdf417.PDF417ResultMetadata;
import com.google.zxing.pdf417.decoder.PDF417ScanningDecoder;
import com.google.zxing.pdf417.detector.Detector;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class PDF417Reader
implements Reader,
MultipleBarcodeReader {
    private static Result[] decode(BinaryBitmap object, Map<DecodeHintType, ?> object2, boolean bl) throws NotFoundException, FormatException, ChecksumException {
        ArrayList<Result> arrayList = new ArrayList<Result>();
        object = Detector.detect((BinaryBitmap)object, object2, bl);
        for (ResultPoint[] arrresultPoint : object.getPoints()) {
            Object object4 = PDF417ScanningDecoder.decode(object.getBits(), arrresultPoint[4], arrresultPoint[5], arrresultPoint[6], arrresultPoint[7], PDF417Reader.getMinCodewordWidth(arrresultPoint), PDF417Reader.getMaxCodewordWidth(arrresultPoint));
            Result object3 = new Result(object4.getText(), object4.getRawBytes(), arrresultPoint, BarcodeFormat.PDF_417);
            object3.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, object4.getECLevel());
            object4 = (PDF417ResultMetadata)object4.getOther();
            if (object4 != null) {
                object3.putMetadata(ResultMetadataType.PDF417_EXTRA_METADATA, object4);
            }
            arrayList.add(object3);
        }
        return arrayList.toArray(new Result[arrayList.size()]);
    }

    private static int getMaxCodewordWidth(ResultPoint[] arrresultPoint) {
        return Math.max(Math.max(PDF417Reader.getMaxWidth(arrresultPoint[0], arrresultPoint[4]), PDF417Reader.getMaxWidth(arrresultPoint[6], arrresultPoint[2]) * 17 / 18), Math.max(PDF417Reader.getMaxWidth(arrresultPoint[1], arrresultPoint[5]), PDF417Reader.getMaxWidth(arrresultPoint[7], arrresultPoint[3]) * 17 / 18));
    }

    private static int getMaxWidth(ResultPoint resultPoint, ResultPoint resultPoint2) {
        if (resultPoint != null && resultPoint2 != null) {
            return (int)Math.abs(resultPoint.getX() - resultPoint2.getX());
        }
        return 0;
    }

    private static int getMinCodewordWidth(ResultPoint[] arrresultPoint) {
        return Math.min(Math.min(PDF417Reader.getMinWidth(arrresultPoint[0], arrresultPoint[4]), PDF417Reader.getMinWidth(arrresultPoint[6], arrresultPoint[2]) * 17 / 18), Math.min(PDF417Reader.getMinWidth(arrresultPoint[1], arrresultPoint[5]), PDF417Reader.getMinWidth(arrresultPoint[7], arrresultPoint[3]) * 17 / 18));
    }

    private static int getMinWidth(ResultPoint resultPoint, ResultPoint resultPoint2) {
        if (resultPoint != null && resultPoint2 != null) {
            return (int)Math.abs(resultPoint.getX() - resultPoint2.getX());
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, FormatException, ChecksumException {
        return this.decode(binaryBitmap, null);
    }

    @Override
    public Result decode(BinaryBitmap arrresult, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException, ChecksumException {
        if ((arrresult = PDF417Reader.decode((BinaryBitmap)arrresult, map, false)) != null && arrresult.length != 0 && arrresult[0] != null) {
            return arrresult[0];
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return this.decodeMultiple(binaryBitmap, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Result[] decodeMultiple(BinaryBitmap arrresult, Map<DecodeHintType, ?> map) throws NotFoundException {
        try {
            return PDF417Reader.decode((BinaryBitmap)arrresult, map, true);
        }
        catch (ChecksumException | FormatException readerException) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    @Override
    public void reset() {
    }
}
