/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.decoder;

import com.google.zxing.datamatrix.decoder.Version;

final class DataBlock {
    private final byte[] codewords;
    private final int numDataCodewords;

    private DataBlock(int n, byte[] arrby) {
        this.numDataCodewords = n;
        this.codewords = arrby;
    }

    static DataBlock[] getDataBlocks(byte[] arrby, Version version) {
        int n;
        int n2;
        Version.ECBlocks eCBlocks = version.getECBlocks();
        Version.ECB[] arreCB = eCBlocks.getECBlocks();
        int n3 = arreCB.length;
        int n4 = n = 0;
        while (n < n3) {
            n4 += arreCB[n].getCount();
            ++n;
        }
        DataBlock[] arrdataBlock = new DataBlock[n4];
        int n5 = arreCB.length;
        n = n4 = 0;
        while (n4 < n5) {
            Version.ECB eCB = arreCB[n4];
            n3 = 0;
            while (n3 < eCB.getCount()) {
                n2 = eCB.getDataCodewords();
                arrdataBlock[n] = new DataBlock(n2, new byte[eCBlocks.getECCodewords() + n2]);
                ++n3;
                ++n;
            }
            ++n4;
        }
        int n6 = arrdataBlock[0].codewords.length - eCBlocks.getECCodewords();
        int n7 = n6 - 1;
        n4 = n3 = 0;
        while (n3 < n7) {
            n5 = 0;
            while (n5 < n) {
                arrdataBlock[n5].codewords[n3] = arrby[n4];
                ++n5;
                ++n4;
            }
            ++n3;
        }
        n5 = version.getVersionNumber() == 24 ? 1 : 0;
        n3 = n5 != 0 ? 8 : n;
        n2 = 0;
        while (n2 < n3) {
            arrdataBlock[n2].codewords[n7] = arrby[n4];
            ++n2;
            ++n4;
        }
        int n8 = arrdataBlock[0].codewords.length;
        n2 = n4;
        for (n4 = n6; n4 < n8; ++n4) {
            n3 = 0;
            while (n3 < n) {
                n6 = n5 != 0 ? (n3 + 8) % n : n3;
                n7 = n5 != 0 && n6 > 7 ? n4 - 1 : n4;
                arrdataBlock[n6].codewords[n7] = arrby[n2];
                ++n3;
                ++n2;
            }
        }
        if (n2 != arrby.length) {
            throw new IllegalArgumentException();
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
