/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.PerspectiveTransform;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.detector.AlignmentPattern;
import com.google.zxing.qrcode.detector.AlignmentPatternFinder;
import com.google.zxing.qrcode.detector.FinderPattern;
import com.google.zxing.qrcode.detector.FinderPatternFinder;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.util.Map;

public class Detector {
    private final BitMatrix image;
    private ResultPointCallback resultPointCallback;

    public Detector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    private float calculateModuleSizeOneWay(ResultPoint resultPoint, ResultPoint resultPoint2) {
        float f = this.sizeOfBlackWhiteBlackRunBothWays((int)resultPoint.getX(), (int)resultPoint.getY(), (int)resultPoint2.getX(), (int)resultPoint2.getY());
        float f2 = this.sizeOfBlackWhiteBlackRunBothWays((int)resultPoint2.getX(), (int)resultPoint2.getY(), (int)resultPoint.getX(), (int)resultPoint.getY());
        if (Float.isNaN(f)) {
            return f2 / 7.0f;
        }
        if (Float.isNaN(f2)) {
            return f / 7.0f;
        }
        return (f + f2) / 14.0f;
    }

    private static int computeDimension(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, float f) throws NotFoundException {
        int n = (MathUtils.round(ResultPoint.distance(resultPoint, resultPoint2) / f) + MathUtils.round(ResultPoint.distance(resultPoint, resultPoint3) / f)) / 2 + 7;
        int n2 = n & 3;
        if (n2 != 0) {
            switch (n2) {
                default: {
                    return n;
                }
                case 3: {
                    throw NotFoundException.getNotFoundInstance();
                }
                case 2: 
            }
            return n - 1;
        }
        return n + 1;
    }

    private static PerspectiveTransform createTransform(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int n) {
        float f;
        float f2;
        float f3;
        float f4 = (float)n - 3.5f;
        if (resultPoint4 != null) {
            f2 = resultPoint4.getX();
            f = resultPoint4.getY();
            f3 = f4 - 3.0f;
        } else {
            f2 = resultPoint2.getX();
            float f5 = resultPoint.getX();
            float f6 = resultPoint3.getX();
            f3 = resultPoint2.getY();
            f = resultPoint.getY();
            float f7 = resultPoint3.getY();
            f2 = f2 - f5 + f6;
            f = f3 - f + f7;
            f3 = f4;
        }
        return PerspectiveTransform.quadrilateralToQuadrilateral(3.5f, 3.5f, f4, 3.5f, f3, f3, 3.5f, f4, resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY(), f2, f, resultPoint3.getX(), resultPoint3.getY());
    }

    private static BitMatrix sampleGrid(BitMatrix bitMatrix, PerspectiveTransform perspectiveTransform, int n) throws NotFoundException {
        return GridSampler.getInstance().sampleGrid(bitMatrix, n, n, perspectiveTransform);
    }

    private float sizeOfBlackWhiteBlackRun(int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        int n7;
        block9 : {
            int n8;
            n7 = Math.abs(n4 - n2) > Math.abs(n3 - n) ? 1 : 0;
            if (n7 != 0) {
                n8 = n;
                n6 = n3;
                n = n4;
                n3 = n2;
                n4 = n8;
            } else {
                n8 = n;
                n = n3;
                n6 = n4;
                n4 = n2;
                n3 = n8;
            }
            int n9 = Math.abs(n - n3);
            int n10 = Math.abs(n6 - n4);
            int n11 = (- n9) / 2;
            int n12 = -1;
            int n13 = n3 < n ? 1 : -1;
            if (n4 < n6) {
                n12 = 1;
            }
            n5 = n + n13;
            n2 = n4;
            n8 = 0;
            int n14 = n7;
            for (n = n3; n != n5; n += n13) {
                int n15 = n14 != 0 ? n2 : n;
                int n16 = n14 != 0 ? n : n2;
                boolean bl = n8 == 1;
                n7 = n8;
                if (bl == this.image.get(n15, n16)) {
                    if (n8 == 2) {
                        return MathUtils.distance(n, n2, n3, n4);
                    }
                    n7 = n8 + 1;
                }
                n15 = n11 + n10;
                n8 = n2;
                n11 = n15;
                if (n15 > 0) {
                    if (n2 == n6) {
                        n = 2;
                        break block9;
                    }
                    n8 = n2 + n12;
                    n11 = n15 - n9;
                }
                n2 = n8;
                n8 = n7;
            }
            n = 2;
            n7 = n8;
        }
        if (n7 == n) {
            return MathUtils.distance(n5, n6, n3, n4);
        }
        return Float.NaN;
    }

    private float sizeOfBlackWhiteBlackRunBothWays(int n, int n2, int n3, int n4) {
        float f;
        float f2 = this.sizeOfBlackWhiteBlackRun(n, n2, n3, n4);
        n3 = n - (n3 - n);
        int n5 = 0;
        if (n3 < 0) {
            f = (float)n / (float)(n - n3);
            n3 = 0;
        } else if (n3 >= this.image.getWidth()) {
            f = (float)(this.image.getWidth() - 1 - n) / (float)(n3 - n);
            n3 = this.image.getWidth() - 1;
        } else {
            f = 1.0f;
        }
        float f3 = n2;
        n4 = (int)(f3 - (float)(n4 - n2) * f);
        if (n4 < 0) {
            f = f3 / (float)(n2 - n4);
            n4 = n5;
        } else if (n4 >= this.image.getHeight()) {
            f = (float)(this.image.getHeight() - 1 - n2) / (float)(n4 - n2);
            n4 = this.image.getHeight() - 1;
        } else {
            f = 1.0f;
        }
        return f2 + this.sizeOfBlackWhiteBlackRun(n, n2, (int)((float)n + (float)(n3 - n) * f), n4) - 1.0f;
    }

    protected final float calculateModuleSize(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3) {
        return (this.calculateModuleSizeOneWay(resultPoint, resultPoint2) + this.calculateModuleSizeOneWay(resultPoint, resultPoint3)) / 2.0f;
    }

    public DetectorResult detect() throws NotFoundException, FormatException {
        return this.detect(null);
    }

    public final DetectorResult detect(Map<DecodeHintType, ?> map) throws NotFoundException, FormatException {
        ResultPointCallback resultPointCallback = map == null ? null : (ResultPointCallback)map.get((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK);
        this.resultPointCallback = resultPointCallback;
        return this.processFinderPatternInfo(new FinderPatternFinder(this.image, this.resultPointCallback).find(map));
    }

    protected final AlignmentPattern findAlignmentInRegion(float f, int n, int n2, float f2) throws NotFoundException {
        float f3;
        int n3 = (int)(f2 * f);
        int n4 = Math.max(0, n - n3);
        n = Math.min(this.image.getWidth() - 1, n + n3) - n4;
        f2 = n;
        if (f2 < (f3 = 3.0f * f)) {
            throw NotFoundException.getNotFoundInstance();
        }
        int n5 = Math.max(0, n2 - n3);
        n2 = Math.min(this.image.getHeight() - 1, n2 + n3) - n5;
        if ((float)n2 < f3) {
            throw NotFoundException.getNotFoundInstance();
        }
        return new AlignmentPatternFinder(this.image, n4, n5, n, n2, f, this.resultPointCallback).find();
    }

    protected final BitMatrix getImage() {
        return this.image;
    }

    protected final ResultPointCallback getResultPointCallback() {
        return this.resultPointCallback;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected final DetectorResult processFinderPatternInfo(FinderPatternInfo var1_1) throws NotFoundException, FormatException {
        var15_11 = var1_1.getTopLeft();
        var2_14 = this.calculateModuleSize(var15_11, var16_12 = var1_1.getTopRight(), var17_13 = var1_1.getBottomLeft());
        if (var2_14 < 1.0f) {
            throw NotFoundException.getNotFoundInstance();
        }
        var11_15 = Detector.computeDimension(var15_11, var16_12, var17_13, var2_14);
        var18_16 = Version.getProvisionalVersionForDimension(var11_15);
        var10_17 = var18_16.getDimensionForVersion();
        var14_18 = null;
        var1_2 = var14_18;
        if (var18_16.getAlignmentPatternCenters().length <= 0) ** GOTO lbl27
        var3_19 = var16_12.getX();
        var4_20 = var15_11.getX();
        var5_21 = var17_13.getX();
        var6_22 = var16_12.getY();
        var7_23 = var15_11.getY();
        var8_24 = var17_13.getY();
        var9_25 = 1.0f - 3.0f / (float)(var10_17 - 7);
        var12_26 = (int)(var15_11.getX() + (var3_19 - var4_20 + var5_21 - var15_11.getX()) * var9_25);
        var13_27 = (int)(var15_11.getY() + var9_25 * (var6_22 - var7_23 + var8_24 - var15_11.getY()));
        var10_17 = 4;
        do {
            var1_4 = var14_18;
            if (var10_17 <= 16) {
                var3_19 = var10_17;
                var1_5 = this.findAlignmentInRegion(var2_14, var12_26, var13_27, var3_19);
            }
lbl27: // 4 sources:
            var14_18 = Detector.createTransform(var15_11, var16_12, var17_13, (ResultPoint)var1_7, var11_15);
            var14_18 = Detector.sampleGrid(this.image, (PerspectiveTransform)var14_18, var11_15);
            if (var1_7 == null) {
                var1_8 = new ResultPoint[]{var17_13, var15_11, var16_12};
                return new DetectorResult((BitMatrix)var14_18, (ResultPoint[])var1_10);
            }
            var1_9 = new ResultPoint[]{var17_13, var15_11, var16_12, var1_7};
            return new DetectorResult((BitMatrix)var14_18, (ResultPoint[])var1_10);
            catch (NotFoundException var1_6) {}
            var10_17 <<= 1;
        } while (true);
    }
}
