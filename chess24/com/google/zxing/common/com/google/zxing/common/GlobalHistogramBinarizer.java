/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;

public class GlobalHistogramBinarizer
extends Binarizer {
    private static final byte[] EMPTY = new byte[0];
    private static final int LUMINANCE_BITS = 5;
    private static final int LUMINANCE_BUCKETS = 32;
    private static final int LUMINANCE_SHIFT = 3;
    private final int[] buckets = new int[32];
    private byte[] luminances = EMPTY;

    public GlobalHistogramBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    private static int estimateBlackPoint(int[] arrn) throws NotFoundException {
        int n;
        int n2;
        int n3;
        int n4 = 0;
        int n5 = arrn.length;
        int n6 = n2 = (n3 = (n = 0));
        int n7 = n2;
        int n8 = n3;
        for (n3 = n; n3 < n5; ++n3) {
            n2 = n8;
            if (arrn[n3] > n8) {
                n2 = arrn[n3];
                n6 = n3;
            }
            n = n7;
            if (arrn[n3] > n7) {
                n = arrn[n3];
            }
            n8 = n2;
            n7 = n;
        }
        n3 = n8 = 0;
        for (n2 = n4; n2 < n5; ++n2) {
            n = n2 - n6;
            n4 = arrn[n2] * n * n;
            n = n8;
            if (n4 > n8) {
                n3 = n2;
                n = n4;
            }
            n8 = n;
        }
        n8 = n3;
        n2 = n6;
        if (n6 > n3) {
            n2 = n3;
            n8 = n6;
        }
        if (n8 - n2 <= n5 / 16) {
            throw NotFoundException.getNotFoundInstance();
        }
        n3 = -1;
        n = n6;
        for (n6 = n8 - 1; n6 > n2; --n6) {
            n4 = n6 - n2;
            n5 = n4 * n4 * (n8 - n6) * (n7 - arrn[n6]);
            n4 = n3;
            if (n5 > n3) {
                n = n6;
                n4 = n5;
            }
            n3 = n4;
        }
        return n << 3;
    }

    private void initArrays(int n) {
        if (this.luminances.length < n) {
            this.luminances = new byte[n];
        }
        for (n = 0; n < 32; ++n) {
            this.buckets[n] = 0;
        }
    }

    @Override
    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new GlobalHistogramBinarizer(luminanceSource);
    }

    @Override
    public BitMatrix getBlackMatrix() throws NotFoundException {
        int n;
        int n2;
        int n3;
        byte[] arrby = this.getLuminanceSource();
        int n4 = arrby.getWidth();
        int n5 = arrby.getHeight();
        BitMatrix bitMatrix = new BitMatrix(n4, n5);
        this.initArrays(n4);
        int[] arrn = this.buckets;
        for (n3 = 1; n3 < 5; ++n3) {
            byte[] arrby2 = arrby.getRow(n5 * n3 / 5, this.luminances);
            n2 = (n4 << 2) / 5;
            for (n = n4 / 5; n < n2; ++n) {
                int n6 = (arrby2[n] & 255) >> 3;
                arrn[n6] = arrn[n6] + 1;
            }
        }
        n2 = GlobalHistogramBinarizer.estimateBlackPoint(arrn);
        arrby = arrby.getMatrix();
        for (n3 = 0; n3 < n5; ++n3) {
            for (n = 0; n < n4; ++n) {
                if ((arrby[n3 * n4 + n] & 255) >= n2) continue;
                bitMatrix.set(n, n3);
            }
        }
        return bitMatrix;
    }

    @Override
    public BitArray getBlackRow(int n, BitArray bitArray) throws NotFoundException {
        int n2;
        byte[] arrby = this.getLuminanceSource();
        int n3 = arrby.getWidth();
        if (bitArray != null && bitArray.getSize() >= n3) {
            bitArray.clear();
        } else {
            bitArray = new BitArray(n3);
        }
        this.initArrays(n3);
        arrby = arrby.getRow(n, this.luminances);
        int[] arrn = this.buckets;
        int n4 = 0;
        for (n = 0; n < n3; ++n) {
            n2 = (arrby[n] & 255) >> 3;
            arrn[n2] = arrn[n2] + 1;
        }
        int n5 = GlobalHistogramBinarizer.estimateBlackPoint(arrn);
        if (n3 < 3) {
            for (n = n4; n < n3; ++n) {
                if ((arrby[n] & 255) >= n5) continue;
                bitArray.set(n);
            }
        } else {
            n4 = arrby[0];
            n = arrby[1] & 255;
            n2 = n4 & 255;
            n4 = 1;
            while (n4 < n3 - 1) {
                int n6 = n4 + 1;
                int n7 = arrby[n6] & 255;
                if (((n << 2) - n2 - n7) / 2 < n5) {
                    bitArray.set(n4);
                }
                n2 = n;
                n4 = n6;
                n = n7;
            }
        }
        return bitArray;
    }
}
