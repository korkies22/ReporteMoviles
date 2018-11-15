/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.multi.qrcode;

import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

private static final class QRCodeMultiReader.SAComparator
implements Serializable,
Comparator<Result> {
    private QRCodeMultiReader.SAComparator() {
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
