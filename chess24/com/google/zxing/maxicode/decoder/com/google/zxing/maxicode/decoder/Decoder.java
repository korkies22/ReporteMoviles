/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.maxicode.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import com.google.zxing.maxicode.decoder.BitMatrixParser;
import com.google.zxing.maxicode.decoder.DecodedBitStreamParser;
import java.util.Map;

public final class Decoder {
    private static final int ALL = 0;
    private static final int EVEN = 1;
    private static final int ODD = 2;
    private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.MAXICODE_FIELD_64);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void correctErrors(byte[] arrby, int n, int n2, int n3, int n4) throws ChecksumException {
        int n5 = n2 + n3;
        int n6 = n4 == 0 ? 1 : 2;
        int[] arrn = new int[n5 / n6];
        int n7 = 0;
        for (int i = 0; i < n5; ++i) {
            if (n4 != 0 && i % 2 != n4 - 1) continue;
            arrn[i / n6] = arrby[i + n] & 255;
        }
        try {
            this.rsDecoder.decode(arrn, n3 / n6);
        }
        catch (ReedSolomonException reedSolomonException) {
            throw ChecksumException.getChecksumInstance();
        }
        for (n3 = n7; n3 < n2; ++n3) {
            if (n4 != 0 && n3 % 2 != n4 - 1) continue;
            arrby[n3 + n] = (byte)arrn[n3 / n6];
        }
        return;
    }

    public DecoderResult decode(BitMatrix bitMatrix) throws ChecksumException, FormatException {
        return this.decode(bitMatrix, null);
    }

    public DecoderResult decode(BitMatrix arrby, Map<DecodeHintType, ?> arrby2) throws FormatException, ChecksumException {
        arrby2 = new BitMatrixParser((BitMatrix)arrby).readCodewords();
        this.correctErrors(arrby2, 0, 10, 10, 0);
        int n = arrby2[0] & 15;
        switch (n) {
            default: {
                throw FormatException.getFormatInstance();
            }
            case 5: {
                this.correctErrors(arrby2, 20, 68, 56, 1);
                this.correctErrors(arrby2, 20, 68, 56, 2);
                arrby = new byte[78];
                break;
            }
            case 2: 
            case 3: 
            case 4: {
                this.correctErrors(arrby2, 20, 84, 40, 1);
                this.correctErrors(arrby2, 20, 84, 40, 2);
                arrby = new byte[94];
            }
        }
        System.arraycopy(arrby2, 0, arrby, 0, 10);
        System.arraycopy(arrby2, 20, arrby, 10, arrby.length - 10);
        return DecodedBitStreamParser.decode(arrby, n);
    }
}
