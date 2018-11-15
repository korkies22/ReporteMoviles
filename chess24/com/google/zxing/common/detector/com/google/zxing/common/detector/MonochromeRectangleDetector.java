/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

@Deprecated
public final class MonochromeRectangleDetector {
    private static final int MAX_MODULES = 32;
    private final BitMatrix image;

    public MonochromeRectangleDetector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    private int[] blackWhiteRange(int n, int n2, int n3, int n4, boolean bl) {
        int n5;
        int n6;
        int n7 = n5 = (n3 + n4) / 2;
        while (n7 >= n3) {
            int n8;
            if (bl ? this.image.get(n7, n) : this.image.get(n, n7)) {
                --n7;
                continue;
            }
            n6 = n7;
            while ((n8 = n6 - 1) >= n3) {
                if (bl) {
                    n6 = n8;
                    if (!this.image.get(n8, n)) continue;
                    break;
                }
                n6 = n8;
                if (!this.image.get(n, n8)) continue;
            }
            if (n8 < n3 || n7 - n8 > n2) break;
            n7 = n8;
        }
        n6 = n7 + 1;
        n3 = n5;
        while (n3 < n4) {
            if (bl ? this.image.get(n3, n) : this.image.get(n, n3)) {
                ++n3;
                continue;
            }
            n7 = n3;
            while ((n5 = n7 + 1) < n4) {
                if (bl) {
                    n7 = n5;
                    if (!this.image.get(n5, n)) continue;
                    break;
                }
                n7 = n5;
                if (!this.image.get(n, n5)) continue;
            }
            if (n5 >= n4 || n5 - n3 > n2) break;
            n3 = n5;
        }
        if ((n = n3 - 1) > n6) {
            return new int[]{n6, n};
        }
        return null;
    }

    private ResultPoint findCornerFromCenter(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) throws NotFoundException {
        int n10 = n;
        int[] arrn = null;
        for (int i = n5; i < n8 && i >= n7 && n10 < n4 && n10 >= n3; i += n6, n10 += n2) {
            int[] arrn2 = n2 == 0 ? this.blackWhiteRange(i, n9, n3, n4, true) : this.blackWhiteRange(n10, n9, n7, n8, false);
            if (arrn2 == null) {
                if (arrn == null) {
                    throw NotFoundException.getNotFoundInstance();
                }
                n4 = 1;
                n3 = 1;
                if (n2 == 0) {
                    n2 = i - n6;
                    if (arrn[0] < n) {
                        if (arrn[1] > n) {
                            n = n3;
                            if (n6 > 0) {
                                n = 0;
                            }
                            return new ResultPoint(arrn[n], n2);
                        }
                        return new ResultPoint(arrn[0], n2);
                    }
                    return new ResultPoint(arrn[1], n2);
                }
                n = n10 - n2;
                if (arrn[0] < n5) {
                    if (arrn[1] > n5) {
                        float f = n;
                        n = n4;
                        if (n2 < 0) {
                            n = 0;
                        }
                        return new ResultPoint(f, arrn[n]);
                    }
                    return new ResultPoint(n, arrn[0]);
                }
                return new ResultPoint(n, arrn[1]);
            }
            arrn = arrn2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public ResultPoint[] detect() throws NotFoundException {
        int n = this.image.getHeight();
        int n2 = this.image.getWidth();
        int n3 = n / 2;
        int n4 = n2 / 2;
        int n5 = Math.max(1, n / 256);
        int n6 = Math.max(1, n2 / 256);
        int n7 = - n5;
        int n8 = n4 / 2;
        int n9 = (int)this.findCornerFromCenter(n4, 0, 0, n2, n3, n7, 0, n, n8).getY() - 1;
        int n10 = - n6;
        int n11 = n3 / 2;
        ResultPoint resultPoint = this.findCornerFromCenter(n4, n10, 0, n2, n3, 0, n9, n, n11);
        n10 = (int)resultPoint.getX() - 1;
        ResultPoint resultPoint2 = this.findCornerFromCenter(n4, n6, n10, n2, n3, 0, n9, n, n11);
        n2 = (int)resultPoint2.getX() + 1;
        ResultPoint resultPoint3 = this.findCornerFromCenter(n4, 0, n10, n2, n3, n5, n9, n, n8);
        return new ResultPoint[]{this.findCornerFromCenter(n4, 0, n10, n2, n3, n7, n9, (int)resultPoint3.getY() + 1, n4 / 4), resultPoint, resultPoint2, resultPoint3};
    }
}
