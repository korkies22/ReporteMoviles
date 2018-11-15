/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.aztec.AztecWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.oned.Code93Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.EAN8Writer;
import com.google.zxing.oned.ITFWriter;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.oned.UPCEWriter;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.Map;

public final class MultiFormatWriter
implements Writer {
    @Override
    public BitMatrix encode(String string, BarcodeFormat barcodeFormat, int n, int n2) throws WriterException {
        return this.encode(string, barcodeFormat, n, n2, null);
    }

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat barcodeFormat, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        Writer writer;
        switch (.$SwitchMap$com$google$zxing$BarcodeFormat[barcodeFormat.ordinal()]) {
            default: {
                charSequence = new StringBuilder("No encoder available for format ");
                charSequence.append((Object)barcodeFormat);
                throw new IllegalArgumentException(charSequence.toString());
            }
            case 13: {
                writer = new AztecWriter();
                break;
            }
            case 12: {
                writer = new DataMatrixWriter();
                break;
            }
            case 11: {
                writer = new CodaBarWriter();
                break;
            }
            case 10: {
                writer = new PDF417Writer();
                break;
            }
            case 9: {
                writer = new ITFWriter();
                break;
            }
            case 8: {
                writer = new Code128Writer();
                break;
            }
            case 7: {
                writer = new Code93Writer();
                break;
            }
            case 6: {
                writer = new Code39Writer();
                break;
            }
            case 5: {
                writer = new QRCodeWriter();
                break;
            }
            case 4: {
                writer = new UPCAWriter();
                break;
            }
            case 3: {
                writer = new EAN13Writer();
                break;
            }
            case 2: {
                writer = new UPCEWriter();
                break;
            }
            case 1: {
                writer = new EAN8Writer();
            }
        }
        return writer.encode((String)charSequence, barcodeFormat, n, n2, map);
    }

}
