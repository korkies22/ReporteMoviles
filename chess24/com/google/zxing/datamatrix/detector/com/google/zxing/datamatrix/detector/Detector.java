/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.datamatrix.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class Detector {
    private final BitMatrix image;
    private final WhiteRectangleDetector rectangleDetector;

    public Detector(BitMatrix bitMatrix) throws NotFoundException {
        this.image = bitMatrix;
        this.rectangleDetector = new WhiteRectangleDetector(bitMatrix);
    }

    private ResultPoint correctTopRight(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int n) {
        float f = Detector.distance(resultPoint, resultPoint2);
        float f2 = n;
        n = Detector.distance(resultPoint3, resultPoint4);
        float f3 = resultPoint4.getX();
        float f4 = resultPoint3.getX();
        float f5 = n;
        f3 = (f3 - f4) / f5;
        f5 = (resultPoint4.getY() - resultPoint3.getY()) / f5;
        ResultPoint resultPoint5 = new ResultPoint(resultPoint4.getX() + f3 * f, resultPoint4.getY() + (f /= f2) * f5);
        f2 = (float)Detector.distance(resultPoint, resultPoint3) / f2;
        n = Detector.distance(resultPoint2, resultPoint4);
        f5 = resultPoint4.getX();
        f3 = resultPoint2.getX();
        f = n;
        f5 = (f5 - f3) / f;
        f = (resultPoint4.getY() - resultPoint2.getY()) / f;
        resultPoint = new ResultPoint(resultPoint4.getX() + f5 * f2, resultPoint4.getY() + f2 * f);
        if (!this.isValid(resultPoint5)) {
            if (this.isValid(resultPoint)) {
                return resultPoint;
            }
            return null;
        }
        if (!this.isValid(resultPoint)) {
            return resultPoint5;
        }
        if (Math.abs(this.transitionsBetween(resultPoint3, resultPoint5).getTransitions() - this.transitionsBetween(resultPoint2, resultPoint5).getTransitions()) <= Math.abs(this.transitionsBetween(resultPoint3, resultPoint).getTransitions() - this.transitionsBetween(resultPoint2, resultPoint).getTransitions())) {
            return resultPoint5;
        }
        return resultPoint;
    }

    private ResultPoint correctTopRightRectangular(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int n, int n2) {
        float f = (float)Detector.distance(resultPoint, resultPoint2) / (float)n;
        int n3 = Detector.distance(resultPoint3, resultPoint4);
        float f2 = resultPoint4.getX();
        float f3 = resultPoint3.getX();
        float f4 = n3;
        f2 = (f2 - f3) / f4;
        f4 = (resultPoint4.getY() - resultPoint3.getY()) / f4;
        ResultPoint resultPoint5 = new ResultPoint(resultPoint4.getX() + f2 * f, resultPoint4.getY() + f * f4);
        f = (float)Detector.distance(resultPoint, resultPoint3) / (float)n2;
        n3 = Detector.distance(resultPoint2, resultPoint4);
        f2 = resultPoint4.getX();
        f3 = resultPoint2.getX();
        f4 = n3;
        f2 = (f2 - f3) / f4;
        f4 = (resultPoint4.getY() - resultPoint2.getY()) / f4;
        resultPoint = new ResultPoint(resultPoint4.getX() + f2 * f, resultPoint4.getY() + f * f4);
        if (!this.isValid(resultPoint5)) {
            if (this.isValid(resultPoint)) {
                return resultPoint;
            }
            return null;
        }
        if (!this.isValid(resultPoint)) {
            return resultPoint5;
        }
        if (Math.abs(n - this.transitionsBetween(resultPoint3, resultPoint5).getTransitions()) + Math.abs(n2 - this.transitionsBetween(resultPoint2, resultPoint5).getTransitions()) <= Math.abs(n - this.transitionsBetween(resultPoint3, resultPoint).getTransitions()) + Math.abs(n2 - this.transitionsBetween(resultPoint2, resultPoint).getTransitions())) {
            return resultPoint5;
        }
        return resultPoint;
    }

    private static int distance(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.round(ResultPoint.distance(resultPoint, resultPoint2));
    }

    private static void increment(Map<ResultPoint, Integer> map, ResultPoint resultPoint) {
        Integer n = map.get(resultPoint);
        int n2 = 1;
        if (n != null) {
            n2 = 1 + n;
        }
        map.put(resultPoint, n2);
    }

    private boolean isValid(ResultPoint resultPoint) {
        if (resultPoint.getX() >= 0.0f && resultPoint.getX() < (float)this.image.getWidth() && resultPoint.getY() > 0.0f && resultPoint.getY() < (float)this.image.getHeight()) {
            return true;
        }
        return false;
    }

    private static BitMatrix sampleGrid(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int n, int n2) throws NotFoundException {
        GridSampler gridSampler = GridSampler.getInstance();
        float f = (float)n - 0.5f;
        float f2 = (float)n2 - 0.5f;
        return gridSampler.sampleGrid(bitMatrix, n, n2, 0.5f, 0.5f, f, 0.5f, f, f2, 0.5f, f2, resultPoint.getX(), resultPoint.getY(), resultPoint4.getX(), resultPoint4.getY(), resultPoint3.getX(), resultPoint3.getY(), resultPoint2.getX(), resultPoint2.getY());
    }

    private ResultPointsAndTransitions transitionsBetween(ResultPoint resultPoint, ResultPoint resultPoint2) {
        int n = (int)resultPoint.getX();
        int n2 = (int)resultPoint.getY();
        int n3 = (int)resultPoint2.getX();
        int n4 = (int)resultPoint2.getY();
        int n5 = Math.abs(n4 - n2);
        int n6 = Math.abs(n3 - n);
        int n7 = 0;
        boolean bl = n5 > n6;
        n5 = n;
        n6 = n2;
        int n8 = n3;
        int n9 = n4;
        if (bl) {
            n6 = n;
            n5 = n2;
            n9 = n3;
            n8 = n4;
        }
        int n10 = Math.abs(n8 - n5);
        int n11 = Math.abs(n9 - n6);
        n2 = (- n10) / 2;
        int n12 = -1;
        n = n6 < n9 ? 1 : -1;
        if (n5 < n8) {
            n12 = 1;
        }
        BitMatrix bitMatrix = this.image;
        n4 = bl ? n6 : n5;
        n3 = bl ? n5 : n6;
        boolean bl2 = bitMatrix.get(n4, n3);
        n4 = n7;
        do {
            n7 = n4;
            if (n5 == n8) break;
            bitMatrix = this.image;
            n3 = bl ? n6 : n5;
            n7 = bl ? n5 : n6;
            boolean bl3 = bitMatrix.get(n3, n7);
            n3 = n4;
            boolean bl4 = bl2;
            if (bl3 != bl2) {
                n3 = n4 + 1;
                bl4 = bl3;
            }
            int n13 = n2 + n11;
            n4 = n6;
            n2 = n13;
            if (n13 > 0) {
                n7 = n3;
                if (n6 == n9) break;
                n4 = n6 + n;
                n2 = n13 - n10;
            }
            n5 += n12;
            n6 = n4;
            n4 = n3;
            bl2 = bl4;
        } while (true);
        return new ResultPointsAndTransitions(resultPoint, resultPoint2, n7);
    }

    public DetectorResult detect() throws NotFoundException {
        Object object;
        Object object2;
        Object object3 = this.rectangleDetector.detect();
        ResultPoint resultPoint = object3[0];
        ResultPoint resultPoint2 = object3[1];
        ResultPoint resultPoint3 = object3[2];
        ResultPoint resultPoint4 = object3[3];
        Object object4 = new ArrayList<ResultPointsAndTransitions>(4);
        object4.add(this.transitionsBetween(resultPoint, resultPoint2));
        object4.add(this.transitionsBetween(resultPoint, resultPoint3));
        object4.add(this.transitionsBetween(resultPoint2, resultPoint4));
        object4.add(this.transitionsBetween(resultPoint3, resultPoint4));
        Object object5 = null;
        Collections.sort(object4, new ResultPointsAndTransitionsComparator());
        object3 = (ResultPointsAndTransitions)object4.get(0);
        object4 = (ResultPointsAndTransitions)object4.get(1);
        HashMap<ResultPoint, Integer> hashMap = new HashMap<ResultPoint, Integer>();
        Detector.increment(hashMap, object3.getFrom());
        Detector.increment(hashMap, object3.getTo());
        Detector.increment(hashMap, object4.getFrom());
        Detector.increment(hashMap, object4.getTo());
        Object object6 = hashMap.entrySet().iterator();
        object4 = object = null;
        while (object6.hasNext()) {
            object2 = object6.next();
            object3 = (ResultPoint)object2.getKey();
            if ((Integer)object2.getValue() == 2) {
                object = object3;
                continue;
            }
            if (object5 == null) {
                object5 = object3;
                continue;
            }
            object4 = object3;
        }
        if (object5 != null && object != null && object4 != null) {
            object3 = new ResultPoint[]{object5, object, object4};
            ResultPoint.orderBestPatterns((ResultPoint[])object3);
            object6 = object3[0];
            object2 = object3[1];
            object5 = object3[2];
            object3 = !hashMap.containsKey(resultPoint) ? resultPoint : (!hashMap.containsKey(resultPoint2) ? resultPoint2 : (!hashMap.containsKey(resultPoint3) ? resultPoint3 : resultPoint4));
            int n = this.transitionsBetween((ResultPoint)object5, (ResultPoint)object3).getTransitions();
            int n2 = this.transitionsBetween((ResultPoint)object6, (ResultPoint)object3).getTransitions();
            int n3 = n;
            if ((n & 1) == 1) {
                n3 = n + 1;
            }
            n = n3 + 2;
            n3 = n2;
            if ((n2 & 1) == 1) {
                n3 = n2 + 1;
            }
            if (4 * n < (n3 += 2) * 7 && 4 * n3 < n * 7) {
                object4 = this.correctTopRight((ResultPoint)object2, (ResultPoint)object6, (ResultPoint)object5, (ResultPoint)object3, Math.min(n3, n));
                if (object4 != null) {
                    object3 = object4;
                }
                n3 = n2 = Math.max(this.transitionsBetween((ResultPoint)object5, (ResultPoint)object3).getTransitions(), this.transitionsBetween((ResultPoint)object6, (ResultPoint)object3).getTransitions()) + 1;
                if ((n2 & 1) == 1) {
                    n3 = n2 + 1;
                }
                object4 = Detector.sampleGrid(this.image, (ResultPoint)object5, (ResultPoint)object2, (ResultPoint)object6, (ResultPoint)object3, n3, n3);
            } else {
                object4 = object5;
                object = this.correctTopRightRectangular((ResultPoint)object2, (ResultPoint)object6, (ResultPoint)object5, (ResultPoint)object3, n, n3);
                if (object != null) {
                    object3 = object;
                }
                n2 = this.transitionsBetween((ResultPoint)object4, (ResultPoint)object3).getTransitions();
                n = this.transitionsBetween((ResultPoint)object6, (ResultPoint)object3).getTransitions();
                n3 = n2;
                if ((n2 & 1) == 1) {
                    n3 = n2 + 1;
                }
                n2 = n;
                if ((n & 1) == 1) {
                    n2 = n + 1;
                }
                object4 = Detector.sampleGrid(this.image, (ResultPoint)object4, (ResultPoint)object2, (ResultPoint)object6, (ResultPoint)object3, n3, n2);
            }
            return new DetectorResult((BitMatrix)object4, new ResultPoint[]{object5, object2, object6, object3});
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static final class ResultPointsAndTransitions {
        private final ResultPoint from;
        private final ResultPoint to;
        private final int transitions;

        private ResultPointsAndTransitions(ResultPoint resultPoint, ResultPoint resultPoint2, int n) {
            this.from = resultPoint;
            this.to = resultPoint2;
            this.transitions = n;
        }

        ResultPoint getFrom() {
            return this.from;
        }

        ResultPoint getTo() {
            return this.to;
        }

        int getTransitions() {
            return this.transitions;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.from);
            stringBuilder.append("/");
            stringBuilder.append(this.to);
            stringBuilder.append('/');
            stringBuilder.append(this.transitions);
            return stringBuilder.toString();
        }
    }

    private static final class ResultPointsAndTransitionsComparator
    implements Serializable,
    Comparator<ResultPointsAndTransitions> {
        private ResultPointsAndTransitionsComparator() {
        }

        @Override
        public int compare(ResultPointsAndTransitions resultPointsAndTransitions, ResultPointsAndTransitions resultPointsAndTransitions2) {
            return resultPointsAndTransitions.getTransitions() - resultPointsAndTransitions2.getTransitions();
        }
    }

}
