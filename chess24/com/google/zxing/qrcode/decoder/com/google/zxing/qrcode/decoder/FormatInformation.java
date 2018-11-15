/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

final class FormatInformation {
    private static final int[][] FORMAT_INFO_DECODE_LOOKUP;
    private static final int FORMAT_INFO_MASK_QR = 21522;
    private final byte dataMask;
    private final ErrorCorrectionLevel errorCorrectionLevel;

    static {
        int[] arrn = new int[]{21522, 0};
        int[] arrn2 = new int[]{20773, 1};
        int[] arrn3 = new int[]{24188, 2};
        int[] arrn4 = new int[]{23371, 3};
        int[] arrn5 = new int[]{19104, 7};
        int[] arrn6 = new int[]{30660, 8};
        int[] arrn7 = new int[]{29427, 9};
        int[] arrn8 = new int[]{32170, 10};
        int[] arrn9 = new int[]{26159, 12};
        int[] arrn10 = new int[]{25368, 13};
        int[] arrn11 = new int[]{26998, 15};
        int[] arrn12 = new int[]{5769, 16};
        int[] arrn13 = new int[]{5054, 17};
        int[] arrn14 = new int[]{6608, 19};
        int[] arrn15 = new int[]{1890, 20};
        int[] arrn16 = new int[]{597, 21};
        int[] arrn17 = new int[]{3340, 22};
        int[] arrn18 = new int[]{13663, 24};
        int[] arrn19 = new int[]{12392, 25};
        int[] arrn20 = new int[]{16177, 26};
        int[] arrn21 = new int[]{9396, 28};
        int[] arrn22 = new int[]{8579, 29};
        int[] arrn23 = new int[]{11994, 30};
        int[] arrn24 = new int[]{11245, 31};
        FORMAT_INFO_DECODE_LOOKUP = new int[][]{arrn, arrn2, arrn3, arrn4, {17913, 4}, {16590, 5}, {20375, 6}, arrn5, arrn6, arrn7, arrn8, {30877, 11}, arrn9, arrn10, {27713, 14}, arrn11, arrn12, arrn13, {7399, 18}, arrn14, arrn15, arrn16, arrn17, {2107, 23}, arrn18, arrn19, arrn20, {14854, 27}, arrn21, arrn22, arrn23, arrn24};
    }

    private FormatInformation(int n) {
        this.errorCorrectionLevel = ErrorCorrectionLevel.forBits(n >> 3 & 3);
        this.dataMask = (byte)(n & 7);
    }

    static FormatInformation decodeFormatInformation(int n, int n2) {
        FormatInformation formatInformation = FormatInformation.doDecodeFormatInformation(n, n2);
        if (formatInformation != null) {
            return formatInformation;
        }
        return FormatInformation.doDecodeFormatInformation(n ^ 21522, n2 ^ 21522);
    }

    private static FormatInformation doDecodeFormatInformation(int n, int n2) {
        int[][] arrn = FORMAT_INFO_DECODE_LOOKUP;
        int n3 = arrn.length;
        int n4 = 0;
        int n5 = Integer.MAX_VALUE;
        for (int i = n4; i < n3; ++i) {
            int[] arrn2 = arrn[i];
            int n6 = arrn2[0];
            if (n6 != n && n6 != n2) {
                int n7 = FormatInformation.numBitsDiffering(n, n6);
                int n8 = n5;
                if (n7 < n5) {
                    n4 = arrn2[1];
                    n8 = n7;
                }
                n5 = n8;
                n7 = n4;
                if (n != n2) {
                    n6 = FormatInformation.numBitsDiffering(n2, n6);
                    n5 = n8;
                    n7 = n4;
                    if (n6 < n8) {
                        n7 = arrn2[1];
                        n5 = n6;
                    }
                }
                n4 = n7;
                continue;
            }
            return new FormatInformation(arrn2[1]);
        }
        if (n5 <= 3) {
            return new FormatInformation(n4);
        }
        return null;
    }

    static int numBitsDiffering(int n, int n2) {
        return Integer.bitCount(n ^ n2);
    }

    public boolean equals(Object object) {
        if (!(object instanceof FormatInformation)) {
            return false;
        }
        object = (FormatInformation)object;
        if (this.errorCorrectionLevel == object.errorCorrectionLevel && this.dataMask == object.dataMask) {
            return true;
        }
        return false;
    }

    byte getDataMask() {
        return this.dataMask;
    }

    ErrorCorrectionLevel getErrorCorrectionLevel() {
        return this.errorCorrectionLevel;
    }

    public int hashCode() {
        return this.errorCorrectionLevel.ordinal() << 3 | this.dataMask;
    }
}
