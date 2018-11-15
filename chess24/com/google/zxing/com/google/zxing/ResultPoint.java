/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing;

import com.google.zxing.common.detector.MathUtils;

public class ResultPoint {
    private final float x;
    private final float y;

    public ResultPoint(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    private static float crossProductZ(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3) {
        float f = resultPoint2.x;
        float f2 = resultPoint2.y;
        return (resultPoint3.x - f) * (resultPoint.y - f2) - (resultPoint3.y - f2) * (resultPoint.x - f);
    }

    public static float distance(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.distance(resultPoint.x, resultPoint.y, resultPoint2.x, resultPoint2.y);
    }

    public static void orderBestPatterns(ResultPoint[] arrresultPoint) {
        ResultPoint resultPoint;
        ResultPoint resultPoint2;
        ResultPoint resultPoint3;
        float f = ResultPoint.distance(arrresultPoint[0], arrresultPoint[1]);
        float f2 = ResultPoint.distance(arrresultPoint[1], arrresultPoint[2]);
        float f3 = ResultPoint.distance(arrresultPoint[0], arrresultPoint[2]);
        if (f2 >= f && f2 >= f3) {
            resultPoint = arrresultPoint[0];
            resultPoint2 = arrresultPoint[1];
            resultPoint3 = arrresultPoint[2];
        } else if (f3 >= f2 && f3 >= f) {
            resultPoint = arrresultPoint[1];
            resultPoint2 = arrresultPoint[0];
            resultPoint3 = arrresultPoint[2];
        } else {
            resultPoint = arrresultPoint[2];
            resultPoint2 = arrresultPoint[0];
            resultPoint3 = arrresultPoint[1];
        }
        ResultPoint resultPoint4 = resultPoint2;
        ResultPoint resultPoint5 = resultPoint3;
        if (ResultPoint.crossProductZ(resultPoint2, resultPoint, resultPoint3) < 0.0f) {
            resultPoint5 = resultPoint2;
            resultPoint4 = resultPoint3;
        }
        arrresultPoint[0] = resultPoint4;
        arrresultPoint[1] = resultPoint;
        arrresultPoint[2] = resultPoint5;
    }

    public final boolean equals(Object object) {
        if (object instanceof ResultPoint) {
            object = (ResultPoint)object;
            if (this.x == object.x && this.y == object.y) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    public final int hashCode() {
        return 31 * Float.floatToIntBits(this.x) + Float.floatToIntBits(this.y);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder("(");
        stringBuilder.append(this.x);
        stringBuilder.append(',');
        stringBuilder.append(this.y);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}
