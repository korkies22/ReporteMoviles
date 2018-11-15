/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.ReaderException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import com.google.zxing.qrcode.decoder.BitMatrixParser;
import com.google.zxing.qrcode.decoder.DataBlock;
import com.google.zxing.qrcode.decoder.DecodedBitStreamParser;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.FormatInformation;
import com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData;
import com.google.zxing.qrcode.decoder.Version;
import java.util.Map;

public final class Decoder {
    private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void correctErrors(byte[] arrby, int n) throws ChecksumException {
        int n2;
        int n3 = 0;
        int n4 = arrby.length;
        int[] arrn = new int[n4];
        for (n2 = 0; n2 < n4; ++n2) {
            arrn[n2] = arrby[n2] & 255;
        }
        try {
            this.rsDecoder.decode(arrn, arrby.length - n);
        }
        catch (ReedSolomonException reedSolomonException) {
            throw ChecksumException.getChecksumInstance();
        }
        for (n2 = n3; n2 < n; ++n2) {
            arrby[n2] = (byte)arrn[n2];
        }
        return;
    }

    private DecoderResult decode(BitMatrixParser arrdataBlock, Map<DecodeHintType, ?> map) throws FormatException, ChecksumException {
        int n;
        Version version = arrdataBlock.readVersion();
        ErrorCorrectionLevel errorCorrectionLevel = arrdataBlock.readFormatInformation().getErrorCorrectionLevel();
        arrdataBlock = DataBlock.getDataBlocks(arrdataBlock.readCodewords(), version, errorCorrectionLevel);
        int n2 = arrdataBlock.length;
        int n3 = n = 0;
        while (n < n2) {
            n3 += arrdataBlock[n].getNumDataCodewords();
            ++n;
        }
        byte[] arrby = new byte[n3];
        int n4 = arrdataBlock.length;
        n = n3 = 0;
        while (n3 < n4) {
            DataBlock dataBlock = arrdataBlock[n3];
            byte[] arrby2 = dataBlock.getCodewords();
            int n5 = dataBlock.getNumDataCodewords();
            this.correctErrors(arrby2, n5);
            n2 = 0;
            while (n2 < n5) {
                arrby[n] = arrby2[n2];
                ++n2;
                ++n;
            }
            ++n3;
        }
        return DecodedBitStreamParser.decode(arrby, version, errorCorrectionLevel, map);
    }

    public DecoderResult decode(BitMatrix bitMatrix) throws ChecksumException, FormatException {
        return this.decode(bitMatrix, null);
    }

    public DecoderResult decode(BitMatrix object, Map<DecodeHintType, ?> object2) throws FormatException, ChecksumException {
        BitMatrixParser bitMatrixParser = new BitMatrixParser((BitMatrix)object);
        Object var3_6 = null;
        try {
            object = this.decode(bitMatrixParser, (Map<DecodeHintType, ?>)object2);
            return object;
        }
        catch (ChecksumException checksumException) {
        }
        catch (FormatException formatException) {
            object = null;
        }
        try {
            bitMatrixParser.remask();
            bitMatrixParser.setMirror(true);
            bitMatrixParser.readVersion();
            bitMatrixParser.readFormatInformation();
            bitMatrixParser.mirror();
            object2 = this.decode(bitMatrixParser, (Map<DecodeHintType, ?>)object2);
            object2.setOther(new QRCodeDecoderMetaData(true));
            return object2;
        }
        catch (ChecksumException | FormatException readerException) {
            if (var3_6 != null) {
                throw var3_6;
            }
            if (object != null) {
                throw object;
            }
            throw readerException;
        }
    }

    public DecoderResult decode(boolean[][] arrbl) throws ChecksumException, FormatException {
        return this.decode(arrbl, null);
    }

    public DecoderResult decode(boolean[][] arrbl, Map<DecodeHintType, ?> map) throws ChecksumException, FormatException {
        int n = arrbl.length;
        BitMatrix bitMatrix = new BitMatrix(n);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (!arrbl[i][j]) continue;
                bitMatrix.set(j, i);
            }
        }
        return this.decode(bitMatrix, map);
    }
}
