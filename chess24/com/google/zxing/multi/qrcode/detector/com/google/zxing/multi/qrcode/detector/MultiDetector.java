/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.multi.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.multi.qrcode.detector.MultiFinderPatternFinder;
import com.google.zxing.qrcode.detector.Detector;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.util.ArrayList;
import java.util.Map;

public final class MultiDetector
extends Detector {
    private static final DetectorResult[] EMPTY_DETECTOR_RESULTS = new DetectorResult[0];

    public MultiDetector(BitMatrix bitMatrix) {
        super(bitMatrix);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public DetectorResult[] detectMulti(Map<DecodeHintType, ?> arrfinderPatternInfo) throws NotFoundException {
        BitMatrix bitMatrix = this.getImage();
        Object object = arrfinderPatternInfo == null ? null : (ResultPointCallback)arrfinderPatternInfo.get((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK);
        if ((arrfinderPatternInfo = new MultiFinderPatternFinder(bitMatrix, (ResultPointCallback)object).findMulti((Map<DecodeHintType, ?>)arrfinderPatternInfo)).length == 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        object = new ArrayList();
        int n = arrfinderPatternInfo.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                if (!object.isEmpty()) return object.toArray(new DetectorResult[object.size()]);
                return EMPTY_DETECTOR_RESULTS;
            }
            FinderPatternInfo finderPatternInfo = arrfinderPatternInfo[n2];
            try {
                object.add(this.processFinderPatternInfo(finderPatternInfo));
            }
            catch (ReaderException readerException) {}
            ++n2;
        } while (true);
    }
}
