/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import java.util.Map;

public final class QRCodeWriter
implements Writer {
    private static final int QUIET_ZONE_SIZE = 4;

    private static BitMatrix renderResult(QRCode object, int n, int n2, int n3) {
        if ((object = object.getMatrix()) == null) {
            throw new IllegalStateException();
        }
        int n4 = object.getWidth();
        int n5 = object.getHeight();
        int n6 = n4 + (n3 <<= 1);
        int n7 = n3 + n5;
        n3 = Math.max(n, n6);
        n2 = Math.max(n2, n7);
        int n8 = Math.min(n3 / n6, n2 / n7);
        n7 = (n3 - n4 * n8) / 2;
        n = (n2 - n5 * n8) / 2;
        BitMatrix bitMatrix = new BitMatrix(n3, n2);
        n2 = 0;
        while (n2 < n5) {
            n6 = 0;
            n3 = n7;
            while (n6 < n4) {
                if (object.get(n6, n2) == 1) {
                    bitMatrix.setRegion(n3, n, n8, n8);
                }
                ++n6;
                n3 += n8;
            }
            ++n2;
            n += n8;
        }
        return bitMatrix;
    }

    @Override
    public BitMatrix encode(String string, BarcodeFormat barcodeFormat, int n, int n2) throws WriterException {
        return this.encode(string, barcodeFormat, n, n2, null);
    }

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat enum_, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        if (charSequence.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (enum_ != BarcodeFormat.QR_CODE) {
            charSequence = new StringBuilder("Can only encode QR_CODE, but got ");
            charSequence.append(enum_);
            throw new IllegalArgumentException(charSequence.toString());
        }
        if (n >= 0 && n2 >= 0) {
            int n3;
            enum_ = ErrorCorrectionLevel.L;
            int n4 = n3 = 4;
            Enum enum_2 = enum_;
            if (map != null) {
                if (map.containsKey((Object)EncodeHintType.ERROR_CORRECTION)) {
                    enum_ = ErrorCorrectionLevel.valueOf(map.get((Object)EncodeHintType.ERROR_CORRECTION).toString());
                }
                n4 = n3;
                enum_2 = enum_;
                if (map.containsKey((Object)EncodeHintType.MARGIN)) {
                    n4 = Integer.parseInt(map.get((Object)EncodeHintType.MARGIN).toString());
                    enum_2 = enum_;
                }
            }
            return QRCodeWriter.renderResult(Encoder.encode((String)charSequence, (ErrorCorrectionLevel)enum_2, map), n, n2, n4);
        }
        charSequence = new StringBuilder("Requested dimensions are too small: ");
        charSequence.append(n);
        charSequence.append('x');
        charSequence.append(n2);
        throw new IllegalArgumentException(charSequence.toString());
    }
}
