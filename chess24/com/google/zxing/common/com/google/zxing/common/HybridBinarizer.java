/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GlobalHistogramBinarizer;
import java.lang.reflect.Array;

public final class HybridBinarizer
extends GlobalHistogramBinarizer {
    private static final int BLOCK_SIZE = 8;
    private static final int BLOCK_SIZE_MASK = 7;
    private static final int BLOCK_SIZE_POWER = 3;
    private static final int MINIMUM_DIMENSION = 40;
    private static final int MIN_DYNAMIC_RANGE = 24;
    private BitMatrix matrix;

    public HybridBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    private static int[][] calculateBlackPoints(byte[] arrby, int n, int n2, int n3, int n4) {
        int[][] arrn = (int[][])Array.newInstance(Integer.TYPE, n2, n);
        for (int i = 0; i < n2; ++i) {
            int n5 = i << 3;
            int n6 = n4 - 8;
            if (n5 > n6) {
                n5 = n6;
            }
            for (int j = 0; j < n; ++j) {
                int n7 = j << 3;
                int n8 = n3 - 8;
                n6 = n7;
                if (n7 > n8) {
                    n6 = n8;
                }
                int n9 = 255;
                n7 = n5 * n3 + n6;
                n6 = 0;
                int n10 = 0;
                n8 = 0;
                while (n6 < 8) {
                    int n11;
                    int n12;
                    int n13;
                    int n14 = n8;
                    n8 = n10;
                    n10 = n14;
                    for (n13 = 0; n13 < 8; ++n13) {
                        n11 = arrby[n7 + n13] & 255;
                        n12 = n8 + n11;
                        n14 = n9;
                        if (n11 < n9) {
                            n14 = n11;
                        }
                        n8 = n10;
                        if (n11 > n10) {
                            n8 = n11;
                        }
                        n10 = n8;
                        n8 = n12;
                        n9 = n14;
                    }
                    n11 = n6;
                    n13 = n8;
                    n14 = n7;
                    if (n10 - n9 > 24) {
                        n13 = n7;
                        n12 = n6;
                        block4 : do {
                            n6 = n12 + 1;
                            n7 = n13 + n3;
                            n11 = n6;
                            n13 = n8;
                            n14 = n7;
                            if (n6 >= 8) break;
                            n14 = 0;
                            n11 = n8;
                            do {
                                n12 = n6;
                                n8 = n11;
                                n13 = n7;
                                if (n14 >= 8) continue block4;
                                n11 += arrby[n7 + n14] & 255;
                                ++n14;
                            } while (true);
                            break;
                        } while (true);
                    }
                    n6 = n11 + 1;
                    n7 = n14 + n3;
                    n8 = n10;
                    n10 = n13;
                }
                n6 = n10 >> 6;
                if (n8 - n9 <= 24) {
                    n6 = n7 = n9 / 2;
                    if (i > 0) {
                        n6 = n7;
                        if (j > 0) {
                            n6 = i - 1;
                            n8 = arrn[n6][j];
                            int[] arrn2 = arrn[i];
                            n10 = j - 1;
                            n8 = (n8 + 2 * arrn2[n10] + arrn[n6][n10]) / 4;
                            n6 = n7;
                            if (n9 < n8) {
                                n6 = n8;
                            }
                        }
                    }
                }
                arrn[i][j] = n6;
            }
        }
        return arrn;
    }

    private static void calculateThresholdForBlock(byte[] arrby, int n, int n2, int n3, int n4, int[][] arrn, BitMatrix bitMatrix) {
        for (int i = 0; i < n2; ++i) {
            int n5 = i << 3;
            int n6 = n4 - 8;
            int n7 = n5;
            if (n5 > n6) {
                n7 = n6;
            }
            for (n5 = 0; n5 < n; ++n5) {
                n6 = n5 << 3;
                int n8 = n3 - 8;
                if (n6 > n8) {
                    n6 = n8;
                }
                int n9 = HybridBinarizer.cap(n5, 2, n - 3);
                int n10 = HybridBinarizer.cap(i, 2, n2 - 3);
                int n11 = 0;
                for (n8 = -2; n8 <= 2; ++n8) {
                    int[] arrn2 = arrn[n10 + n8];
                    n11 += arrn2[n9 - 2] + arrn2[n9 - 1] + arrn2[n9] + arrn2[n9 + 1] + arrn2[n9 + 2];
                }
                HybridBinarizer.thresholdBlock(arrby, n6, n7, n11 / 25, n3, bitMatrix);
            }
        }
    }

    private static int cap(int n, int n2, int n3) {
        if (n < n2) {
            return n2;
        }
        if (n > n3) {
            return n3;
        }
        return n;
    }

    private static void thresholdBlock(byte[] arrby, int n, int n2, int n3, int n4, BitMatrix bitMatrix) {
        int n5 = n2 * n4 + n;
        int n6 = 0;
        while (n6 < 8) {
            for (int i = 0; i < 8; ++i) {
                if ((arrby[n5 + i] & 255) > n3) continue;
                bitMatrix.set(n + i, n2 + n6);
            }
            ++n6;
            n5 += n4;
        }
    }

    @Override
    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new HybridBinarizer(luminanceSource);
    }

    @Override
    public BitMatrix getBlackMatrix() throws NotFoundException {
        if (this.matrix != null) {
            return this.matrix;
        }
        byte[] arrby = this.getLuminanceSource();
        int n = arrby.getWidth();
        int n2 = arrby.getHeight();
        if (n >= 40 && n2 >= 40) {
            int n3;
            int n4;
            arrby = arrby.getMatrix();
            int n5 = n4 = n >> 3;
            if ((n & 7) != 0) {
                n5 = n4 + 1;
            }
            n4 = n3 = n2 >> 3;
            if ((n2 & 7) != 0) {
                n4 = n3 + 1;
            }
            int[][] arrn = HybridBinarizer.calculateBlackPoints(arrby, n5, n4, n, n2);
            BitMatrix bitMatrix = new BitMatrix(n, n2);
            HybridBinarizer.calculateThresholdForBlock(arrby, n5, n4, n, n2, arrn, bitMatrix);
            this.matrix = bitMatrix;
        } else {
            this.matrix = super.getBlackMatrix();
        }
        return this.matrix;
    }
}
