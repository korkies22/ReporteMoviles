/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.ResultPoint;

public final class QRCodeDecoderMetaData {
    private final boolean mirrored;

    QRCodeDecoderMetaData(boolean bl) {
        this.mirrored = bl;
    }

    public void applyMirroredCorrection(ResultPoint[] arrresultPoint) {
        if (this.mirrored && arrresultPoint != null) {
            if (arrresultPoint.length < 3) {
                return;
            }
            ResultPoint resultPoint = arrresultPoint[0];
            arrresultPoint[0] = arrresultPoint[2];
            arrresultPoint[2] = resultPoint;
            return;
        }
    }

    public boolean isMirrored() {
        return this.mirrored;
    }
}
