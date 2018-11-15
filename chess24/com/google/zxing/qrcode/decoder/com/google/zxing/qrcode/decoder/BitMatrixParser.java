/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.DataMask;
import com.google.zxing.qrcode.decoder.FormatInformation;
import com.google.zxing.qrcode.decoder.Version;

final class BitMatrixParser {
    private final BitMatrix bitMatrix;
    private boolean mirror;
    private FormatInformation parsedFormatInfo;
    private Version parsedVersion;

    BitMatrixParser(BitMatrix bitMatrix) throws FormatException {
        int n = bitMatrix.getHeight();
        if (n >= 21 && (n & 3) == 1) {
            this.bitMatrix = bitMatrix;
            return;
        }
        throw FormatException.getFormatInstance();
    }

    private int copyBit(int n, int n2, int n3) {
        boolean bl = this.mirror ? this.bitMatrix.get(n2, n) : this.bitMatrix.get(n, n2);
        if (bl) {
            return n3 << 1 | 1;
        }
        return n3 << 1;
    }

    void mirror() {
        int n = 0;
        while (n < this.bitMatrix.getWidth()) {
            int n2;
            for (int i = n2 = n + 1; i < this.bitMatrix.getHeight(); ++i) {
                if (this.bitMatrix.get(n, i) == this.bitMatrix.get(i, n)) continue;
                this.bitMatrix.flip(i, n);
                this.bitMatrix.flip(n, i);
            }
            n = n2;
        }
    }

    byte[] readCodewords() throws FormatException {
        Object object = this.readFormatInformation();
        Version version = this.readVersion();
        object = DataMask.values()[object.getDataMask()];
        int n = this.bitMatrix.getHeight();
        object.unmaskBitMatrix(this.bitMatrix, n);
        object = version.buildFunctionPattern();
        byte[] arrby = new byte[version.getTotalCodewords()];
        int n2 = n - 1;
        boolean bl = true;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = n2;
        while (n6 > 0) {
            int n7 = n6;
            if (n6 == 6) {
                n7 = n6 - 1;
            }
            int n8 = 0;
            n6 = n5;
            for (n5 = n8; n5 < n; ++n5) {
                int n9 = bl ? n2 - n5 : n5;
                int n10 = n6;
                for (int i = 0; i < 2; ++i) {
                    int n11 = n7 - i;
                    int n12 = n3;
                    n6 = n10;
                    n8 = n4;
                    if (!object.get(n11, n9)) {
                        n6 = n10 + 1;
                        n4 <<= 1;
                        if (this.bitMatrix.get(n11, n9)) {
                            n4 |= 1;
                        }
                        if (n6 == 8) {
                            arrby[n3] = (byte)n4;
                            n12 = n3 + 1;
                            n6 = 0;
                            n8 = 0;
                        } else {
                            n8 = n4;
                            n12 = n3;
                        }
                    }
                    n3 = n12;
                    n10 = n6;
                    n4 = n8;
                }
                n6 = n10;
            }
            bl ^= true;
            n8 = n7 - 2;
            n5 = n6;
            n6 = n8;
        }
        if (n3 != version.getTotalCodewords()) {
            throw FormatException.getFormatInstance();
        }
        return arrby;
    }

    FormatInformation readFormatInformation() throws FormatException {
        int n;
        int n2;
        if (this.parsedFormatInfo != null) {
            return this.parsedFormatInfo;
        }
        int n3 = 0;
        int n4 = n2 = 0;
        while (n2 < 6) {
            n4 = this.copyBit(n2, 8, n4);
            ++n2;
        }
        n4 = this.copyBit(8, 7, this.copyBit(8, 8, this.copyBit(7, 8, n4)));
        for (n2 = 5; n2 >= 0; --n2) {
            n4 = this.copyBit(8, n2, n4);
        }
        int n5 = this.bitMatrix.getHeight();
        n2 = n3;
        for (n = n5 - 1; n >= n5 - 7; --n) {
            n2 = this.copyBit(8, n, n2);
        }
        for (n = n5 - 8; n < n5; ++n) {
            n2 = this.copyBit(n, 8, n2);
        }
        this.parsedFormatInfo = FormatInformation.decodeFormatInformation(n4, n2);
        if (this.parsedFormatInfo != null) {
            return this.parsedFormatInfo;
        }
        throw FormatException.getFormatInstance();
    }

    Version readVersion() throws FormatException {
        int n;
        if (this.parsedVersion != null) {
            return this.parsedVersion;
        }
        int n2 = this.bitMatrix.getHeight();
        int n3 = (n2 - 17) / 4;
        if (n3 <= 6) {
            return Version.getVersionForNumber(n3);
        }
        int n4 = n2 - 11;
        int n5 = 5;
        int n6 = 0;
        int n7 = 0;
        for (n3 = 5; n3 >= 0; --n3) {
            for (n = n2 - 9; n >= n4; --n) {
                n7 = this.copyBit(n, n3, n7);
            }
        }
        Version version = Version.decodeVersionInformation(n7);
        n3 = n5;
        n7 = n6;
        if (version != null) {
            n3 = n5;
            n7 = n6;
            if (version.getDimensionForVersion() == n2) {
                this.parsedVersion = version;
                return version;
            }
        }
        while (n3 >= 0) {
            for (n = n2 - 9; n >= n4; --n) {
                n7 = this.copyBit(n3, n, n7);
            }
            --n3;
        }
        version = Version.decodeVersionInformation(n7);
        if (version != null && version.getDimensionForVersion() == n2) {
            this.parsedVersion = version;
            return version;
        }
        throw FormatException.getFormatInstance();
    }

    void remask() {
        if (this.parsedFormatInfo == null) {
            return;
        }
        DataMask dataMask = DataMask.values()[this.parsedFormatInfo.getDataMask()];
        int n = this.bitMatrix.getHeight();
        dataMask.unmaskBitMatrix(this.bitMatrix, n);
    }

    void setMirror(boolean bl) {
        this.parsedVersion = null;
        this.parsedFormatInfo = null;
        this.mirror = bl;
    }
}
