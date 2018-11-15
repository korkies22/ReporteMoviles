/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.multi;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.multi.MultipleBarcodeReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class GenericMultipleBarcodeReader
implements MultipleBarcodeReader {
    private static final int MAX_DEPTH = 4;
    private static final int MIN_DIMENSION_TO_RECUR = 100;
    private final Reader delegate;

    public GenericMultipleBarcodeReader(Reader reader) {
        this.delegate = reader;
    }

    private void doDecodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, List<Result> list, int n, int n2, int n3) {
        ResultPoint[] arrresultPoint;
        int n4;
        Object object;
        block17 : {
            if (n3 > 4) {
                return;
            }
            try {
                arrresultPoint = this.delegate.decode(binaryBitmap, map);
                object = list.iterator();
            }
            catch (ReaderException readerException) {
                return;
            }
            while (object.hasNext()) {
                if (!object.next().getText().equals(arrresultPoint.getText())) continue;
                n4 = 1;
                break block17;
            }
            n4 = 0;
        }
        if (n4 == 0) {
            list.add(GenericMultipleBarcodeReader.translateResultPoints((Result)arrresultPoint, n, n2));
        }
        if ((arrresultPoint = arrresultPoint.getResultPoints()) != null) {
            float f;
            int n5;
            if (arrresultPoint.length == 0) {
                return;
            }
            int n6 = binaryBitmap.getWidth();
            int n7 = binaryBitmap.getHeight();
            float f2 = n6;
            float f3 = n7;
            n4 = arrresultPoint.length;
            float f4 = f = 0.0f;
            for (n5 = 0; n5 < n4; ++n5) {
                object = arrresultPoint[n5];
                float f5 = f2;
                float f6 = f3;
                float f7 = f;
                float f8 = f4;
                if (object != null) {
                    f5 = object.getX();
                    float f9 = object.getY();
                    float f10 = f2;
                    if (f5 < f2) {
                        f10 = f5;
                    }
                    f2 = f3;
                    if (f9 < f3) {
                        f2 = f9;
                    }
                    f3 = f;
                    if (f5 > f) {
                        f3 = f5;
                    }
                    f5 = f10;
                    f6 = f2;
                    f7 = f3;
                    f8 = f4;
                    if (f9 > f4) {
                        f8 = f9;
                        f7 = f3;
                        f6 = f2;
                        f5 = f10;
                    }
                }
                f2 = f5;
                f3 = f6;
                f = f7;
                f4 = f8;
            }
            if (f2 > 100.0f) {
                this.doDecodeMultiple(binaryBitmap.crop(0, 0, (int)f2, n7), map, list, n, n2, n3 + 1);
            }
            n4 = n7;
            if (f3 > 100.0f) {
                this.doDecodeMultiple(binaryBitmap.crop(0, 0, n6, (int)f3), map, list, n, n2, n3 + 1);
            }
            if (f < (float)(n6 - 100)) {
                n5 = (int)f;
                this.doDecodeMultiple(binaryBitmap.crop(n5, 0, n6 - n5, n4), map, list, n + n5, n2, n3 + 1);
            }
            if (f4 < (float)(n4 - 100)) {
                n5 = (int)f4;
                this.doDecodeMultiple(binaryBitmap.crop(0, n5, n6, n4 - n5), map, list, n, n2 + n5, n3 + 1);
            }
            return;
        }
        return;
    }

    private static Result translateResultPoints(Result result, int n, int n2) {
        Object object = result.getResultPoints();
        if (object == null) {
            return result;
        }
        ResultPoint[] arrresultPoint = new ResultPoint[((ResultPoint[])object).length];
        for (int i = 0; i < ((ResultPoint[])object).length; ++i) {
            ResultPoint resultPoint = object[i];
            if (resultPoint == null) continue;
            arrresultPoint[i] = new ResultPoint(resultPoint.getX() + (float)n, resultPoint.getY() + (float)n2);
        }
        object = new Result(result.getText(), result.getRawBytes(), result.getNumBits(), arrresultPoint, result.getBarcodeFormat(), result.getTimestamp());
        object.putAllMetadata(result.getResultMetadata());
        return object;
    }

    @Override
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return this.decodeMultiple(binaryBitmap, null);
    }

    @Override
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        ArrayList<Result> arrayList = new ArrayList<Result>();
        this.doDecodeMultiple(binaryBitmap, map, arrayList, 0, 0, 0);
        if (arrayList.isEmpty()) {
            throw NotFoundException.getNotFoundInstance();
        }
        return arrayList.toArray(new Result[arrayList.size()]);
    }
}
