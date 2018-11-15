/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.multi.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.multi.MultipleBarcodeReader;
import com.google.zxing.multi.qrcode.detector.MultiDetector;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.Decoder;
import com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class QRCodeMultiReader
extends QRCodeReader
implements MultipleBarcodeReader {
    private static final Result[] EMPTY_RESULT_ARRAY = new Result[0];
    private static final ResultPoint[] NO_POINTS = new ResultPoint[0];

    private static List<Result> processStructuredAppend(List<Result> object) {
        Object object2;
        int n;
        byte[] arrby;
        int n2;
        Object object3;
        int n3;
        int n4;
        block8 : {
            object2 = object.iterator();
            while (object2.hasNext()) {
                if (!object2.next().getResultMetadata().containsKey((Object)ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)) continue;
                n4 = 1;
                break block8;
            }
            n4 = 0;
        }
        if (n4 == 0) {
            return object;
        }
        object2 = new ArrayList();
        ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
        object = object.iterator();
        while (object.hasNext()) {
            arrby = (Result)object.next();
            object2.add(arrby);
            if (!arrby.getResultMetadata().containsKey((Object)ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)) continue;
            arrayList.add(arrby);
        }
        Collections.sort(arrayList, new SAComparator());
        object = new StringBuilder();
        arrby = arrayList.iterator();
        n4 = n3 = 0;
        block2 : while (arrby.hasNext()) {
            object3 = (Result)arrby.next();
            object.append(object3.getText());
            n3 = n2 = n3 + object3.getRawBytes().length;
            if (!object3.getResultMetadata().containsKey((Object)ResultMetadataType.BYTE_SEGMENTS)) continue;
            object3 = ((Iterable)object3.getResultMetadata().get((Object)ResultMetadataType.BYTE_SEGMENTS)).iterator();
            n = n4;
            do {
                n3 = n2;
                n4 = n;
                if (!object3.hasNext()) continue block2;
                n += ((byte[])object3.next()).length;
            } while (true);
        }
        object3 = new byte[n3];
        arrby = new byte[n4];
        arrayList = arrayList.iterator();
        n3 = n = 0;
        block4 : while (arrayList.hasNext()) {
            int n5;
            Object object4 = (Result)arrayList.next();
            System.arraycopy(object4.getRawBytes(), 0, object3, n, object4.getRawBytes().length);
            n = n5 = n + object4.getRawBytes().length;
            if (!object4.getResultMetadata().containsKey((Object)ResultMetadataType.BYTE_SEGMENTS)) continue;
            object4 = ((Iterable)object4.getResultMetadata().get((Object)ResultMetadataType.BYTE_SEGMENTS)).iterator();
            n2 = n3;
            do {
                n = n5;
                n3 = n2;
                if (!object4.hasNext()) continue block4;
                byte[] arrby2 = (byte[])object4.next();
                System.arraycopy(arrby2, 0, arrby, n2, arrby2.length);
                n2 += arrby2.length;
            } while (true);
        }
        object = new Result(object.toString(), (byte[])object3, NO_POINTS, BarcodeFormat.QR_CODE);
        if (n4 > 0) {
            arrayList = new ArrayList<byte[]>();
            arrayList.add(arrby);
            object.putMetadata(ResultMetadataType.BYTE_SEGMENTS, arrayList);
        }
        object2.add(object);
        return object2;
    }

    @Override
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return this.decodeMultiple(binaryBitmap, null);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Result[] decodeMultiple(BinaryBitmap object, Map<DecodeHintType, ?> map) throws NotFoundException {
        ArrayList<Result> arrayList = new ArrayList<Result>();
        object = new MultiDetector(object.getBlackMatrix()).detectMulti(map);
        int n = 0;
        int n2 = ((Object)object).length;
        do {
            if (n < n2) {
                Object object2 = object[n];
                DecoderResult decoderResult = this.getDecoder().decode(object2.getBits(), map);
                ResultPoint[] arrresultPoint = object2.getPoints();
                if (decoderResult.getOther() instanceof QRCodeDecoderMetaData) {
                    ((QRCodeDecoderMetaData)decoderResult.getOther()).applyMirroredCorrection(arrresultPoint);
                }
                Result result = new Result(decoderResult.getText(), decoderResult.getRawBytes(), arrresultPoint, BarcodeFormat.QR_CODE);
                List<byte[]> list = decoderResult.getByteSegments();
                if (list != null) {
                    result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, list);
                }
                if ((list = decoderResult.getECLevel()) != null) {
                    result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, list);
                }
                if (decoderResult.hasStructuredAppend()) {
                    result.putMetadata(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE, decoderResult.getStructuredAppendSequenceNumber());
                    result.putMetadata(ResultMetadataType.STRUCTURED_APPEND_PARITY, decoderResult.getStructuredAppendParity());
                }
                arrayList.add(result);
            } else {
                if (arrayList.isEmpty()) {
                    return EMPTY_RESULT_ARRAY;
                }
                object = QRCodeMultiReader.processStructuredAppend(arrayList);
                return object.toArray(new Result[object.size()]);
                catch (ReaderException readerException) {}
            }
            ++n;
        } while (true);
    }

    private static final class SAComparator
    implements Serializable,
    Comparator<Result> {
        private SAComparator() {
        }

        @Override
        public int compare(Result result, Result result2) {
            int n;
            int n2 = (Integer)result.getResultMetadata().get((Object)ResultMetadataType.STRUCTURED_APPEND_SEQUENCE);
            if (n2 < (n = ((Integer)result2.getResultMetadata().get((Object)ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)).intValue())) {
                return -1;
            }
            if (n2 > n) {
                return 1;
            }
            return 0;
        }
    }

}
