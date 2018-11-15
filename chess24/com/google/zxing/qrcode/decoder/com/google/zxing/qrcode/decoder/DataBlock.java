/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;

final class DataBlock {
    private final byte[] codewords;
    private final int numDataCodewords;

    private DataBlock(int n, byte[] arrby) {
        this.numDataCodewords = n;
        this.codewords = arrby;
    }

    static DataBlock[] getDataBlocks(byte[] arrby, Version object, ErrorCorrectionLevel arreCB) {
        int n;
        int n2;
        if (arrby.length != object.getTotalCodewords()) {
            throw new IllegalArgumentException();
        }
        object = object.getECBlocksForLevel((ErrorCorrectionLevel)arreCB);
        arreCB = object.getECBlocks();
        int n3 = arreCB.length;
        int n4 = n2 = 0;
        while (n2 < n3) {
            n4 += arreCB[n2].getCount();
            ++n2;
        }
        DataBlock[] arrdataBlock = new DataBlock[n4];
        int n5 = arreCB.length;
        n4 = n3 = 0;
        while (n3 < n5) {
            Version.ECB eCB = arreCB[n3];
            n2 = n4;
            n4 = 0;
            while (n4 < eCB.getCount()) {
                n = eCB.getDataCodewords();
                arrdataBlock[n2] = new DataBlock(n, new byte[object.getECCodewordsPerBlock() + n]);
                ++n4;
                ++n2;
            }
            ++n3;
            n4 = n2;
        }
        n3 = arrdataBlock[0].codewords.length;
        for (n2 = arrdataBlock.length - 1; n2 >= 0 && arrdataBlock[n2].codewords.length != n3; --n2) {
        }
        int n6 = n2 + 1;
        n = n3 - object.getECCodewordsPerBlock();
        n2 = n3 = 0;
        while (n3 < n) {
            n5 = 0;
            while (n5 < n4) {
                arrdataBlock[n5].codewords[n3] = arrby[n2];
                ++n5;
                ++n2;
            }
            ++n3;
        }
        n5 = n6;
        n3 = n2;
        while (n5 < n4) {
            arrdataBlock[n5].codewords[n] = arrby[n3];
            ++n5;
            ++n3;
        }
        int n7 = arrdataBlock[0].codewords.length;
        for (n2 = n; n2 < n7; ++n2) {
            n5 = 0;
            while (n5 < n4) {
                n = n5 < n6 ? n2 : n2 + 1;
                arrdataBlock[n5].codewords[n] = arrby[n3];
                ++n5;
                ++n3;
            }
        }
        return arrdataBlock;
    }

    byte[] getCodewords() {
        return this.codewords;
    }

    int getNumDataCodewords() {
        return this.numDataCodewords;
    }
}
