/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import com.google.zxing.datamatrix.decoder.BitMatrixParser;
import com.google.zxing.datamatrix.decoder.DataBlock;
import com.google.zxing.datamatrix.decoder.DecodedBitStreamParser;
import com.google.zxing.datamatrix.decoder.Version;

public final class Decoder {
    private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.DATA_MATRIX_FIELD_256);

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

    public DecoderResult decode(BitMatrix arrdataBlock) throws FormatException, ChecksumException {
        int n;
        arrdataBlock = new BitMatrixParser((BitMatrix)arrdataBlock);
        byte[] arrby = arrdataBlock.getVersion();
        arrdataBlock = DataBlock.getDataBlocks(arrdataBlock.readCodewords(), (Version)arrby);
        int n2 = arrdataBlock.length;
        int n3 = n = 0;
        while (n < n2) {
            n3 += arrdataBlock[n].getNumDataCodewords();
            ++n;
        }
        arrby = new byte[n3];
        for (DataBlock dataBlock : arrdataBlock) {
            byte[] arrby2 = dataBlock.getCodewords();
            int n4 = dataBlock.getNumDataCodewords();
            this.correctErrors(arrby2, n4);
            for (n3 = 0; n3 < n4; ++n3) {
                arrby[n3 * n2 + n] = arrby2[n3];
            }
        }
        return DecodedBitStreamParser.decode(arrby);
    }

    public DecoderResult decode(boolean[][] arrbl) throws FormatException, ChecksumException {
        int n = arrbl.length;
        BitMatrix bitMatrix = new BitMatrix(n);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (!arrbl[i][j]) continue;
                bitMatrix.set(j, i);
            }
        }
        return this.decode(bitMatrix);
    }
}
