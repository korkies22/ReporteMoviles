/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.aztec;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.aztec.decoder.Decoder;
import com.google.zxing.aztec.detector.Detector;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import java.util.List;
import java.util.Map;

public final class AztecReader
implements Reader {
    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, FormatException {
        return this.decode(binaryBitmap, null);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Result decode(BinaryBitmap object, Map<DecodeHintType, ?> list) throws NotFoundException, FormatException {
        Object object2;
        Object object3;
        Detector detector;
        AztecDetectorResult aztecDetectorResult;
        int n;
        block13 : {
            block15 : {
                block14 : {
                    detector = new Detector(object.getBlackMatrix());
                    n = 0;
                    object3 = null;
                    object2 = detector.detect(false);
                    object = object2.getPoints();
                    try {
                        object2 = new Decoder().decode((AztecDetectorResult)object2);
                        aztecDetectorResult = null;
                        object3 = object2;
                        object2 = aztecDetectorResult;
                        break block13;
                    }
                    catch (FormatException formatException) {
                        break block14;
                    }
                    catch (NotFoundException notFoundException) {
                        break block15;
                    }
                    catch (FormatException formatException) {
                        object = null;
                    }
                }
                aztecDetectorResult = object2;
                object2 = null;
                break block13;
                catch (NotFoundException notFoundException) {
                    object = null;
                }
            }
            aztecDetectorResult = null;
        }
        Object object4 = object3;
        if (object3 == null) {
            try {
                object3 = detector.detect(true);
                object = object3.getPoints();
                object4 = new Decoder().decode((AztecDetectorResult)object3);
            }
            catch (FormatException | NotFoundException readerException) {
                if (object2 != null) {
                    throw object2;
                }
                if (aztecDetectorResult == null) throw readerException;
                throw aztecDetectorResult;
            }
        }
        if (list != null && (list = (ResultPointCallback)list.get((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK)) != null) {
            int n2 = ((Object)object).length;
            while (n < n2) {
                list.foundPossibleResultPoint((ResultPoint)object[n]);
                ++n;
            }
        }
        object = new Result(object4.getText(), object4.getRawBytes(), object4.getNumBits(), (ResultPoint[])object, BarcodeFormat.AZTEC, System.currentTimeMillis());
        list = object4.getByteSegments();
        if (list != null) {
            object.putMetadata(ResultMetadataType.BYTE_SEGMENTS, list);
        }
        if ((list = object4.getECLevel()) == null) return object;
        object.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, list);
        return object;
    }

    @Override
    public void reset() {
    }
}
