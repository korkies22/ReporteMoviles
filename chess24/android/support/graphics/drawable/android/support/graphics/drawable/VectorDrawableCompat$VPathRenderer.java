/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Join
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$FillType
 *  android.graphics.PathMeasure
 */
package android.support.graphics.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.util.ArrayMap;
import java.util.ArrayList;

private static class VectorDrawableCompat.VPathRenderer {
    private static final Matrix IDENTITY_MATRIX = new Matrix();
    float mBaseHeight = 0.0f;
    float mBaseWidth = 0.0f;
    private int mChangingConfigurations;
    private Paint mFillPaint;
    private final Matrix mFinalPathMatrix = new Matrix();
    private final Path mPath;
    private PathMeasure mPathMeasure;
    private final Path mRenderPath;
    int mRootAlpha = 255;
    final VectorDrawableCompat.VGroup mRootGroup;
    String mRootName = null;
    private Paint mStrokePaint;
    final ArrayMap<String, Object> mVGTargetsMap = new ArrayMap();
    float mViewportHeight = 0.0f;
    float mViewportWidth = 0.0f;

    public VectorDrawableCompat.VPathRenderer() {
        this.mRootGroup = new VectorDrawableCompat.VGroup();
        this.mPath = new Path();
        this.mRenderPath = new Path();
    }

    public VectorDrawableCompat.VPathRenderer(VectorDrawableCompat.VPathRenderer vPathRenderer) {
        this.mRootGroup = new VectorDrawableCompat.VGroup(vPathRenderer.mRootGroup, this.mVGTargetsMap);
        this.mPath = new Path(vPathRenderer.mPath);
        this.mRenderPath = new Path(vPathRenderer.mRenderPath);
        this.mBaseWidth = vPathRenderer.mBaseWidth;
        this.mBaseHeight = vPathRenderer.mBaseHeight;
        this.mViewportWidth = vPathRenderer.mViewportWidth;
        this.mViewportHeight = vPathRenderer.mViewportHeight;
        this.mChangingConfigurations = vPathRenderer.mChangingConfigurations;
        this.mRootAlpha = vPathRenderer.mRootAlpha;
        this.mRootName = vPathRenderer.mRootName;
        if (vPathRenderer.mRootName != null) {
            this.mVGTargetsMap.put(vPathRenderer.mRootName, this);
        }
    }

    static /* synthetic */ Paint access$000(VectorDrawableCompat.VPathRenderer vPathRenderer) {
        return vPathRenderer.mFillPaint;
    }

    static /* synthetic */ Paint access$002(VectorDrawableCompat.VPathRenderer vPathRenderer, Paint paint) {
        vPathRenderer.mFillPaint = paint;
        return paint;
    }

    static /* synthetic */ Paint access$100(VectorDrawableCompat.VPathRenderer vPathRenderer) {
        return vPathRenderer.mStrokePaint;
    }

    static /* synthetic */ Paint access$102(VectorDrawableCompat.VPathRenderer vPathRenderer, Paint paint) {
        vPathRenderer.mStrokePaint = paint;
        return paint;
    }

    private static float cross(float f, float f2, float f3, float f4) {
        return f * f4 - f2 * f3;
    }

    private void drawGroupTree(VectorDrawableCompat.VGroup vGroup, Matrix object, Canvas canvas, int n, int n2, ColorFilter colorFilter) {
        vGroup.mStackedMatrix.set(object);
        vGroup.mStackedMatrix.preConcat(vGroup.mLocalMatrix);
        canvas.save();
        for (int i = 0; i < vGroup.mChildren.size(); ++i) {
            object = vGroup.mChildren.get(i);
            if (object instanceof VectorDrawableCompat.VGroup) {
                this.drawGroupTree((VectorDrawableCompat.VGroup)object, vGroup.mStackedMatrix, canvas, n, n2, colorFilter);
                continue;
            }
            if (!(object instanceof VectorDrawableCompat.VPath)) continue;
            this.drawPath(vGroup, (VectorDrawableCompat.VPath)object, canvas, n, n2, colorFilter);
        }
        canvas.restore();
    }

    private void drawPath(VectorDrawableCompat.VGroup vGroup, VectorDrawableCompat.VPath vPath, Canvas canvas, int n, int n2, ColorFilter colorFilter) {
        float f = (float)n / this.mViewportWidth;
        float f2 = (float)n2 / this.mViewportHeight;
        float f3 = Math.min(f, f2);
        vGroup = vGroup.mStackedMatrix;
        this.mFinalPathMatrix.set((Matrix)vGroup);
        this.mFinalPathMatrix.postScale(f, f2);
        f = this.getMatrixScale((Matrix)vGroup);
        if (f == 0.0f) {
            return;
        }
        vPath.toPath(this.mPath);
        vGroup = this.mPath;
        this.mRenderPath.reset();
        if (vPath.isClipPath()) {
            this.mRenderPath.addPath((Path)vGroup, this.mFinalPathMatrix);
            canvas.clipPath(this.mRenderPath);
            return;
        }
        vPath = (VectorDrawableCompat.VFullPath)vPath;
        if (vPath.mTrimPathStart != 0.0f || vPath.mTrimPathEnd != 1.0f) {
            float f4 = vPath.mTrimPathStart;
            float f5 = vPath.mTrimPathOffset;
            float f6 = vPath.mTrimPathEnd;
            float f7 = vPath.mTrimPathOffset;
            if (this.mPathMeasure == null) {
                this.mPathMeasure = new PathMeasure();
            }
            this.mPathMeasure.setPath(this.mPath, false);
            f2 = this.mPathMeasure.getLength();
            f4 = (f4 + f5) % 1.0f * f2;
            f6 = (f6 + f7) % 1.0f * f2;
            vGroup.reset();
            if (f4 > f6) {
                this.mPathMeasure.getSegment(f4, f2, (Path)vGroup, true);
                this.mPathMeasure.getSegment(0.0f, f6, (Path)vGroup, true);
            } else {
                this.mPathMeasure.getSegment(f4, f6, (Path)vGroup, true);
            }
            vGroup.rLineTo(0.0f, 0.0f);
        }
        this.mRenderPath.addPath((Path)vGroup, this.mFinalPathMatrix);
        if (vPath.mFillColor != 0) {
            if (this.mFillPaint == null) {
                this.mFillPaint = new Paint();
                this.mFillPaint.setStyle(Paint.Style.FILL);
                this.mFillPaint.setAntiAlias(true);
            }
            Paint paint = this.mFillPaint;
            paint.setColor(VectorDrawableCompat.applyAlpha(vPath.mFillColor, vPath.mFillAlpha));
            paint.setColorFilter(colorFilter);
            Path path = this.mRenderPath;
            vGroup = vPath.mFillRule == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD;
            path.setFillType((Path.FillType)vGroup);
            canvas.drawPath(this.mRenderPath, paint);
        }
        if (vPath.mStrokeColor != 0) {
            if (this.mStrokePaint == null) {
                this.mStrokePaint = new Paint();
                this.mStrokePaint.setStyle(Paint.Style.STROKE);
                this.mStrokePaint.setAntiAlias(true);
            }
            vGroup = this.mStrokePaint;
            if (vPath.mStrokeLineJoin != null) {
                vGroup.setStrokeJoin(vPath.mStrokeLineJoin);
            }
            if (vPath.mStrokeLineCap != null) {
                vGroup.setStrokeCap(vPath.mStrokeLineCap);
            }
            vGroup.setStrokeMiter(vPath.mStrokeMiterlimit);
            vGroup.setColor(VectorDrawableCompat.applyAlpha(vPath.mStrokeColor, vPath.mStrokeAlpha));
            vGroup.setColorFilter(colorFilter);
            vGroup.setStrokeWidth(vPath.mStrokeWidth * (f3 * f));
            canvas.drawPath(this.mRenderPath, (Paint)vGroup);
        }
    }

    private float getMatrixScale(Matrix matrix) {
        float[] arrf;
        float[] arrf2 = arrf = new float[4];
        arrf2[0] = 0.0f;
        arrf2[1] = 1.0f;
        arrf2[2] = 1.0f;
        arrf2[3] = 0.0f;
        matrix.mapVectors(arrf);
        float f = (float)Math.hypot(arrf[0], arrf[1]);
        float f2 = (float)Math.hypot(arrf[2], arrf[3]);
        float f3 = VectorDrawableCompat.VPathRenderer.cross(arrf[0], arrf[1], arrf[2], arrf[3]);
        f2 = Math.max(f, f2);
        f = 0.0f;
        if (f2 > 0.0f) {
            f = Math.abs(f3) / f2;
        }
        return f;
    }

    public void draw(Canvas canvas, int n, int n2, ColorFilter colorFilter) {
        this.drawGroupTree(this.mRootGroup, IDENTITY_MATRIX, canvas, n, n2, colorFilter);
    }

    public float getAlpha() {
        return (float)this.getRootAlpha() / 255.0f;
    }

    public int getRootAlpha() {
        return this.mRootAlpha;
    }

    public void setAlpha(float f) {
        this.setRootAlpha((int)(f * 255.0f));
    }

    public void setRootAlpha(int n) {
        this.mRootAlpha = n;
    }
}
