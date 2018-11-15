/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.aztec.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;

public final class Detector {
    private static final int[] EXPECTED_CORNER_BITS = new int[]{3808, 476, 2107, 1799};
    private boolean compact;
    private final BitMatrix image;
    private int nbCenterLayers;
    private int nbDataBlocks;
    private int nbLayers;
    private int shift;

    public Detector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    private static float distance(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.distance(resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY());
    }

    private static float distance(Point point, Point point2) {
        return MathUtils.distance(point.getX(), point.getY(), point2.getX(), point2.getY());
    }

    private static ResultPoint[] expandSquare(ResultPoint[] arrresultPoint, float f, float f2) {
        f = f2 / (f * 2.0f);
        float f3 = arrresultPoint[0].getX();
        float f4 = arrresultPoint[2].getX();
        float f5 = arrresultPoint[0].getY();
        float f6 = arrresultPoint[2].getY();
        f2 = (arrresultPoint[0].getX() + arrresultPoint[2].getX()) / 2.0f;
        float f7 = (arrresultPoint[0].getY() + arrresultPoint[2].getY()) / 2.0f;
        f3 = (f3 - f4) * f;
        f5 = (f5 - f6) * f;
        ResultPoint resultPoint = new ResultPoint(f2 + f3, f7 + f5);
        ResultPoint resultPoint2 = new ResultPoint(f2 - f3, f7 - f5);
        f3 = arrresultPoint[1].getX();
        f4 = arrresultPoint[3].getX();
        f5 = arrresultPoint[1].getY();
        f6 = arrresultPoint[3].getY();
        f2 = (arrresultPoint[1].getX() + arrresultPoint[3].getX()) / 2.0f;
        f7 = (arrresultPoint[1].getY() + arrresultPoint[3].getY()) / 2.0f;
        f3 = (f3 - f4) * f;
        return new ResultPoint[]{resultPoint, new ResultPoint(f2 + f3, f7 + (f *= f5 - f6)), resultPoint2, new ResultPoint(f2 - f3, f7 - f)};
    }

    private void extractParameters(ResultPoint[] arrresultPoint) throws NotFoundException {
        if (this.isValid(arrresultPoint[0]) && this.isValid(arrresultPoint[1]) && this.isValid(arrresultPoint[2]) && this.isValid(arrresultPoint[3])) {
            int n;
            int n2 = this.nbCenterLayers * 2;
            int[] arrn = new int[]{this.sampleLine(arrresultPoint[0], arrresultPoint[1], n2), this.sampleLine(arrresultPoint[1], arrresultPoint[2], n2), this.sampleLine(arrresultPoint[2], arrresultPoint[3], n2), this.sampleLine(arrresultPoint[3], arrresultPoint[0], n2)};
            this.shift = Detector.getRotation(arrn, n2);
            long l = 0L;
            for (n = 0; n < 4; ++n) {
                n2 = arrn[(this.shift + n) % 4];
                if (this.compact) {
                    l = (l << 7) + (long)(n2 >> 1 & 127);
                    continue;
                }
                l = (l << 10) + (long)((n2 >> 2 & 992) + (n2 >> 1 & 31));
            }
            n = Detector.getCorrectedParameterData(l, this.compact);
            if (this.compact) {
                this.nbLayers = (n >> 6) + 1;
                this.nbDataBlocks = (n & 63) + 1;
                return;
            }
            this.nbLayers = (n >> 11) + 1;
            this.nbDataBlocks = (n & 2047) + 1;
            return;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private ResultPoint[] getBullsEyeCorners(Point object) throws NotFoundException {
        Object object2;
        Object object3;
        this.nbCenterLayers = 1;
        Object object4 = object2 = (object3 = object);
        boolean bl = true;
        while (this.nbCenterLayers < 9) {
            double d;
            Point point = this.getFirstDifferent((Point)object, bl, 1, -1);
            Point point2 = this.getFirstDifferent((Point)object3, bl, 1, 1);
            Point point3 = this.getFirstDifferent((Point)object2, bl, -1, 1);
            Point point4 = this.getFirstDifferent((Point)object4, bl, -1, -1);
            if (this.nbCenterLayers > 2 && ((d = (double)(Detector.distance(point4, point) * (float)this.nbCenterLayers / (Detector.distance((Point)object4, (Point)object) * (float)(this.nbCenterLayers + 2)))) < 0.75 || d > 1.25 || !this.isWhiteOrBlackRectangle(point, point2, point3, point4))) break;
            bl ^= true;
            ++this.nbCenterLayers;
            object4 = point4;
            object = point;
            object3 = point2;
            object2 = point3;
        }
        if (this.nbCenterLayers != 5 && this.nbCenterLayers != 7) {
            throw NotFoundException.getNotFoundInstance();
        }
        bl = this.nbCenterLayers == 5;
        this.compact = bl;
        object = new ResultPoint((float)object.getX() + 0.5f, (float)object.getY() - 0.5f);
        object3 = new ResultPoint((float)object3.getX() + 0.5f, (float)object3.getY() + 0.5f);
        object2 = new ResultPoint((float)object2.getX() - 0.5f, (float)object2.getY() + 0.5f);
        object4 = new ResultPoint((float)object4.getX() - 0.5f, (float)object4.getY() - 0.5f);
        float f = this.nbCenterLayers * 2 - 3;
        float f2 = 2 * this.nbCenterLayers;
        return Detector.expandSquare(new ResultPoint[]{object, object3, object2, object4}, f, f2);
    }

    private int getColor(Point point, Point point2) {
        int n;
        float f = Detector.distance(point, point2);
        float f2 = (float)(point2.getX() - point.getX()) / f;
        float f3 = (float)(point2.getY() - point.getY()) / f;
        float f4 = point.getX();
        float f5 = point.getY();
        boolean bl = this.image.get(point.getX(), point.getY());
        int n2 = (int)Math.ceil(f);
        boolean bl2 = false;
        for (int i = n = 0; i < n2; ++i) {
            int n3 = n;
            if (this.image.get(MathUtils.round(f4 += f2), MathUtils.round(f5 += f3)) != bl) {
                n3 = n + 1;
            }
            n = n3;
        }
        f5 = (float)n / f;
        if (f5 > 0.1f && f5 < 0.9f) {
            return 0;
        }
        if (f5 <= 0.1f) {
            bl2 = true;
        }
        if (bl2 == bl) {
            return 1;
        }
        return -1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int getCorrectedParameterData(long l, boolean bl) throws NotFoundException {
        int n;
        int n2;
        int n3;
        if (bl) {
            n2 = 7;
            n3 = 2;
        } else {
            n2 = 10;
            n3 = 4;
        }
        int[] arrn = new int[n2];
        for (n = n2 - 1; n >= 0; --n) {
            arrn[n] = (int)l & 15;
            l >>= 4;
        }
        try {
            new ReedSolomonDecoder(GenericGF.AZTEC_PARAM).decode(arrn, n2 - n3);
            n = 0;
        }
        catch (ReedSolomonException reedSolomonException) {
            throw NotFoundException.getNotFoundInstance();
        }
        for (n2 = 0; n2 < n3; ++n2) {
            n = (n << 4) + arrn[n2];
        }
        return n;
    }

    private int getDimension() {
        if (this.compact) {
            return 4 * this.nbLayers + 11;
        }
        if (this.nbLayers <= 4) {
            return 4 * this.nbLayers + 15;
        }
        return this.nbLayers * 4 + 2 * ((this.nbLayers - 4) / 8 + 1) + 15;
    }

    private Point getFirstDifferent(Point point, boolean bl, int n, int n2) {
        int n3 = point.getX() + n;
        int n4 = point.getY() + n2;
        while (this.isValid(n3, n4) && this.image.get(n3, n4) == bl) {
            n3 += n;
            n4 += n2;
        }
        int n5 = n3 - n;
        n3 = n4 - n2;
        n4 = n5;
        while (this.isValid(n4, n3) && this.image.get(n4, n3) == bl) {
            n4 += n;
        }
        n = n3;
        while (this.isValid(n4 -= n, n) && this.image.get(n4, n) == bl) {
            n += n2;
        }
        return new Point(n4, n - n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Point getMatrixCenter() {
        Object object;
        int n;
        Object object2;
        int n2;
        Object object3;
        int n3;
        Object object4;
        int n4;
        block4 : {
            try {
                object3 = new WhiteRectangleDetector(this.image).detect();
            }
            catch (NotFoundException notFoundException) {}
            object4 = object3[0];
            object = object3[1];
            object2 = object3[2];
            object3 = object3[3];
            break block4;
            n = this.image.getWidth() / 2;
            n4 = this.image.getHeight() / 2;
            n3 = n + 7;
            n2 = n4 - 7;
            object3 = this.getFirstDifferent(new Point(n3, n2), false, 1, -1).toResultPoint();
            object = this.getFirstDifferent(new Point(n3, n4 += 7), false, 1, 1).toResultPoint();
            object2 = this.getFirstDifferent(new Point(n -= 7, n4), false, -1, 1).toResultPoint();
            ResultPoint resultPoint = this.getFirstDifferent(new Point(n, n2), false, -1, -1).toResultPoint();
            object4 = object3;
            object3 = resultPoint;
        }
        n = MathUtils.round((object4.getX() + object3.getX() + object.getX() + object2.getX()) / 4.0f);
        n4 = MathUtils.round((object4.getY() + object3.getY() + object.getY() + object2.getY()) / 4.0f);
        try {
            object3 = new WhiteRectangleDetector(this.image, 15, n, n4).detect();
        }
        catch (NotFoundException notFoundException) {}
        object = object3[0];
        object2 = object3[1];
        object4 = object3[2];
        object3 = object3[3];
        return new Point(MathUtils.round((object.getX() + object3.getX() + object2.getX() + object4.getX()) / 4.0f), MathUtils.round((object.getY() + object3.getY() + object2.getY() + object4.getY()) / 4.0f));
        n3 = n + 7;
        n2 = n4 - 7;
        object = this.getFirstDifferent(new Point(n3, n2), false, 1, -1).toResultPoint();
        object2 = this.getFirstDifferent(new Point(n3, n4 += 7), false, 1, 1).toResultPoint();
        object4 = this.getFirstDifferent(new Point(n -= 7, n4), false, -1, 1).toResultPoint();
        object3 = this.getFirstDifferent(new Point(n, n2), false, -1, -1).toResultPoint();
        return new Point(MathUtils.round((object.getX() + object3.getX() + object2.getX() + object4.getX()) / 4.0f), MathUtils.round((object.getY() + object3.getY() + object2.getY() + object4.getY()) / 4.0f));
    }

    private ResultPoint[] getMatrixCornerPoints(ResultPoint[] arrresultPoint) {
        return Detector.expandSquare(arrresultPoint, 2 * this.nbCenterLayers, this.getDimension());
    }

    private static int getRotation(int[] arrn, int n) throws NotFoundException {
        int n2;
        int n3 = 0;
        int n4 = arrn.length;
        int n5 = n2 = 0;
        while (n2 < n4) {
            int n6 = arrn[n2];
            n5 = (n5 << 3) + ((n6 >> n - 2 << 1) + (n6 & 1));
            ++n2;
        }
        for (n = n3; n < 4; ++n) {
            if (Integer.bitCount(EXPECTED_CORNER_BITS[n] ^ ((n5 & 1) << 11) + (n5 >> 1)) > 2) continue;
            return n;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private boolean isValid(int n, int n2) {
        if (n >= 0 && n < this.image.getWidth() && n2 > 0 && n2 < this.image.getHeight()) {
            return true;
        }
        return false;
    }

    private boolean isValid(ResultPoint resultPoint) {
        return this.isValid(MathUtils.round(resultPoint.getX()), MathUtils.round(resultPoint.getY()));
    }

    private boolean isWhiteOrBlackRectangle(Point point, Point point2, Point point3, Point point4) {
        point = new Point(point.getX() - 3, point.getY() + 3);
        point2 = new Point(point2.getX() - 3, point2.getY() - 3);
        point3 = new Point(point3.getX() + 3, point3.getY() - 3);
        int n = this.getColor(point4 = new Point(point4.getX() + 3, point4.getY() + 3), point);
        if (n == 0) {
            return false;
        }
        if (this.getColor(point, point2) != n) {
            return false;
        }
        if (this.getColor(point2, point3) != n) {
            return false;
        }
        if (this.getColor(point3, point4) == n) {
            return true;
        }
        return false;
    }

    private BitMatrix sampleGrid(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) throws NotFoundException {
        GridSampler gridSampler = GridSampler.getInstance();
        int n = this.getDimension();
        float f = (float)n / 2.0f;
        float f2 = f - (float)this.nbCenterLayers;
        return gridSampler.sampleGrid(bitMatrix, n, n, f2, f2, f, f2, f, f, f2, f += (float)this.nbCenterLayers, resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY(), resultPoint3.getX(), resultPoint3.getY(), resultPoint4.getX(), resultPoint4.getY());
    }

    private int sampleLine(ResultPoint object, ResultPoint resultPoint, int n) {
        float f = Detector.distance((ResultPoint)object, resultPoint);
        float f2 = f / (float)n;
        float f3 = object.getX();
        float f4 = object.getY();
        float f5 = (resultPoint.getX() - object.getX()) * f2 / f;
        f = f2 * (resultPoint.getY() - object.getY()) / f;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            object = this.image;
            f2 = i;
            int n3 = n2;
            if (object.get(MathUtils.round(f2 * f5 + f3), MathUtils.round(f2 * f + f4))) {
                n3 = n2 | 1 << n - i - 1;
            }
            n2 = n3;
        }
        return n2;
    }

    public AztecDetectorResult detect() throws NotFoundException {
        return this.detect(false);
    }

    public AztecDetectorResult detect(boolean bl) throws NotFoundException {
        ResultPoint[] arrresultPoint = this.getBullsEyeCorners(this.getMatrixCenter());
        if (bl) {
            ResultPoint resultPoint = arrresultPoint[0];
            arrresultPoint[0] = arrresultPoint[2];
            arrresultPoint[2] = resultPoint;
        }
        this.extractParameters(arrresultPoint);
        return new AztecDetectorResult(this.sampleGrid(this.image, arrresultPoint[this.shift % 4], arrresultPoint[(this.shift + 1) % 4], arrresultPoint[(this.shift + 2) % 4], arrresultPoint[(this.shift + 3) % 4]), this.getMatrixCornerPoints(arrresultPoint), this.compact, this.nbDataBlocks, this.nbLayers);
    }

    static final class Point {
        private final int x;
        private final int y;

        Point(int n, int n2) {
            this.x = n;
            this.y = n2;
        }

        int getX() {
            return this.x;
        }

        int getY() {
            return this.y;
        }

        ResultPoint toResultPoint() {
            return new ResultPoint(this.getX(), this.getY());
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("<");
            stringBuilder.append(this.x);
            stringBuilder.append(' ');
            stringBuilder.append(this.y);
            stringBuilder.append('>');
            return stringBuilder.toString();
        }
    }

}
