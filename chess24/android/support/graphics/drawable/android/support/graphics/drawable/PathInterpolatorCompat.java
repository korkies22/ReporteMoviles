/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Path
 *  android.graphics.PathMeasure
 *  android.util.AttributeSet
 *  android.view.InflateException
 *  android.view.animation.Interpolator
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.RestrictTo;
import android.support.graphics.drawable.AndroidResources;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.animation.Interpolator;
import org.xmlpull.v1.XmlPullParser;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class PathInterpolatorCompat
implements Interpolator {
    public static final double EPSILON = 1.0E-5;
    public static final int MAX_NUM_POINTS = 3000;
    private static final float PRECISION = 0.002f;
    private float[] mX;
    private float[] mY;

    public PathInterpolatorCompat(Context context, AttributeSet attributeSet, XmlPullParser xmlPullParser) {
        this(context.getResources(), context.getTheme(), attributeSet, xmlPullParser);
    }

    public PathInterpolatorCompat(Resources resources, Resources.Theme theme, AttributeSet attributeSet, XmlPullParser xmlPullParser) {
        resources = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_PATH_INTERPOLATOR);
        this.parseInterpolatorFromTypeArray((TypedArray)resources, xmlPullParser);
        resources.recycle();
    }

    private void initCubic(float f, float f2, float f3, float f4) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(f, f2, f3, f4, 1.0f, 1.0f);
        this.initPath(path);
    }

    private void initPath(Path object) {
        int n;
        int n2 = 0;
        float f = (object = new PathMeasure((Path)object, false)).getLength();
        int n3 = Math.min(3000, (int)(f / 0.002f) + 1);
        if (n3 <= 0) {
            object = new StringBuilder();
            object.append("The Path has a invalid length ");
            object.append(f);
            throw new IllegalArgumentException(object.toString());
        }
        this.mX = new float[n3];
        this.mY = new float[n3];
        float[] arrf = new float[2];
        for (n = 0; n < n3; ++n) {
            object.getPosTan((float)n * f / (float)(n3 - 1), arrf, null);
            this.mX[n] = arrf[0];
            this.mY[n] = arrf[1];
        }
        if ((double)Math.abs(this.mX[0]) <= 1.0E-5 && (double)Math.abs(this.mY[0]) <= 1.0E-5 && (double)Math.abs((arrf = this.mX)[n = n3 - 1] - 1.0f) <= 1.0E-5 && (double)Math.abs(this.mY[n] - 1.0f) <= 1.0E-5) {
            f = 0.0f;
            n = 0;
            while (n2 < n3) {
                float f2 = this.mX[n];
                if (f2 < f) {
                    object = new StringBuilder();
                    object.append("The Path cannot loop back on itself, x :");
                    object.append(f2);
                    throw new IllegalArgumentException(object.toString());
                }
                this.mX[n2] = f2;
                ++n2;
                f = f2;
                ++n;
            }
            if (object.nextContour()) {
                throw new IllegalArgumentException("The Path should be continuous, can't have 2+ contours");
            }
            return;
        }
        object = new StringBuilder();
        object.append("The Path must start at (0,0) and end at (1,1) start: ");
        object.append(this.mX[0]);
        object.append(",");
        object.append(this.mY[0]);
        object.append(" end:");
        arrf = this.mX;
        n = n3 - 1;
        object.append(arrf[n]);
        object.append(",");
        object.append(this.mY[n]);
        throw new IllegalArgumentException(object.toString());
    }

    private void initQuad(float f, float f2) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.quadTo(f, f2, 1.0f, 1.0f);
        this.initPath(path);
    }

    private void parseInterpolatorFromTypeArray(TypedArray object, XmlPullParser object2) {
        if (TypedArrayUtils.hasAttribute((XmlPullParser)object2, "pathData")) {
            object = TypedArrayUtils.getNamedString(object, (XmlPullParser)object2, "pathData", 4);
            object2 = PathParser.createPathFromPathData((String)object);
            if (object2 == null) {
                object2 = new StringBuilder();
                object2.append("The path is null, which is created from ");
                object2.append((String)object);
                throw new InflateException(object2.toString());
            }
            this.initPath((Path)object2);
            return;
        }
        if (!TypedArrayUtils.hasAttribute((XmlPullParser)object2, "controlX1")) {
            throw new InflateException("pathInterpolator requires the controlX1 attribute");
        }
        if (!TypedArrayUtils.hasAttribute((XmlPullParser)object2, "controlY1")) {
            throw new InflateException("pathInterpolator requires the controlY1 attribute");
        }
        float f = TypedArrayUtils.getNamedFloat(object, (XmlPullParser)object2, "controlX1", 0, 0.0f);
        float f2 = TypedArrayUtils.getNamedFloat(object, (XmlPullParser)object2, "controlY1", 1, 0.0f);
        boolean bl = TypedArrayUtils.hasAttribute((XmlPullParser)object2, "controlX2");
        if (bl != TypedArrayUtils.hasAttribute((XmlPullParser)object2, "controlY2")) {
            throw new InflateException("pathInterpolator requires both controlX2 and controlY2 for cubic Beziers.");
        }
        if (!bl) {
            this.initQuad(f, f2);
            return;
        }
        this.initCubic(f, f2, TypedArrayUtils.getNamedFloat(object, (XmlPullParser)object2, "controlX2", 2, 0.0f), TypedArrayUtils.getNamedFloat(object, (XmlPullParser)object2, "controlY2", 3, 0.0f));
    }

    public float getInterpolation(float f) {
        if (f <= 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        int n = 0;
        int n2 = this.mX.length - 1;
        while (n2 - n > 1) {
            int n3 = (n + n2) / 2;
            if (f < this.mX[n3]) {
                n2 = n3;
                continue;
            }
            n = n3;
        }
        float f2 = this.mX[n2] - this.mX[n];
        if (f2 == 0.0f) {
            return this.mY[n];
        }
        f = (f - this.mX[n]) / f2;
        f2 = this.mY[n];
        return f2 + f * (this.mY[n2] - f2);
    }
}
