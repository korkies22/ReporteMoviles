/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$FillType
 *  android.graphics.Rect
 *  android.graphics.RectF
 */
package android.support.v4.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;

private static class CircularProgressDrawable.Ring {
    int mAlpha = 255;
    Path mArrow;
    int mArrowHeight;
    final Paint mArrowPaint = new Paint();
    float mArrowScale = 1.0f;
    int mArrowWidth;
    final Paint mCirclePaint = new Paint();
    int mColorIndex;
    int[] mColors;
    int mCurrentColor;
    float mEndTrim = 0.0f;
    final Paint mPaint = new Paint();
    float mRingCenterRadius;
    float mRotation = 0.0f;
    boolean mShowArrow;
    float mStartTrim = 0.0f;
    float mStartingEndTrim;
    float mStartingRotation;
    float mStartingStartTrim;
    float mStrokeWidth = 5.0f;
    final RectF mTempBounds = new RectF();

    CircularProgressDrawable.Ring() {
        this.mPaint.setStrokeCap(Paint.Cap.SQUARE);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mArrowPaint.setStyle(Paint.Style.FILL);
        this.mArrowPaint.setAntiAlias(true);
        this.mCirclePaint.setColor(0);
    }

    void draw(Canvas canvas, Rect rect) {
        RectF rectF = this.mTempBounds;
        float f = this.mRingCenterRadius + this.mStrokeWidth / 2.0f;
        if (this.mRingCenterRadius <= 0.0f) {
            f = (float)Math.min(rect.width(), rect.height()) / 2.0f - Math.max((float)this.mArrowWidth * this.mArrowScale / 2.0f, this.mStrokeWidth / 2.0f);
        }
        rectF.set((float)rect.centerX() - f, (float)rect.centerY() - f, (float)rect.centerX() + f, (float)rect.centerY() + f);
        f = (this.mStartTrim + this.mRotation) * 360.0f;
        float f2 = (this.mEndTrim + this.mRotation) * 360.0f - f;
        this.mPaint.setColor(this.mCurrentColor);
        this.mPaint.setAlpha(this.mAlpha);
        float f3 = this.mStrokeWidth / 2.0f;
        rectF.inset(f3, f3);
        canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() / 2.0f, this.mCirclePaint);
        f3 = - f3;
        rectF.inset(f3, f3);
        canvas.drawArc(rectF, f, f2, false, this.mPaint);
        this.drawTriangle(canvas, f, f2, rectF);
    }

    void drawTriangle(Canvas canvas, float f, float f2, RectF rectF) {
        if (this.mShowArrow) {
            if (this.mArrow == null) {
                this.mArrow = new Path();
                this.mArrow.setFillType(Path.FillType.EVEN_ODD);
            } else {
                this.mArrow.reset();
            }
            float f3 = Math.min(rectF.width(), rectF.height()) / 2.0f;
            float f4 = (float)this.mArrowWidth * this.mArrowScale / 2.0f;
            this.mArrow.moveTo(0.0f, 0.0f);
            this.mArrow.lineTo((float)this.mArrowWidth * this.mArrowScale, 0.0f);
            this.mArrow.lineTo((float)this.mArrowWidth * this.mArrowScale / 2.0f, (float)this.mArrowHeight * this.mArrowScale);
            this.mArrow.offset(f3 + rectF.centerX() - f4, rectF.centerY() + this.mStrokeWidth / 2.0f);
            this.mArrow.close();
            this.mArrowPaint.setColor(this.mCurrentColor);
            this.mArrowPaint.setAlpha(this.mAlpha);
            canvas.save();
            canvas.rotate(f + f2, rectF.centerX(), rectF.centerY());
            canvas.drawPath(this.mArrow, this.mArrowPaint);
            canvas.restore();
        }
    }

    int getAlpha() {
        return this.mAlpha;
    }

    float getArrowHeight() {
        return this.mArrowHeight;
    }

    float getArrowScale() {
        return this.mArrowScale;
    }

    float getArrowWidth() {
        return this.mArrowWidth;
    }

    int getBackgroundColor() {
        return this.mCirclePaint.getColor();
    }

    float getCenterRadius() {
        return this.mRingCenterRadius;
    }

    int[] getColors() {
        return this.mColors;
    }

    float getEndTrim() {
        return this.mEndTrim;
    }

    int getNextColor() {
        return this.mColors[this.getNextColorIndex()];
    }

    int getNextColorIndex() {
        return (this.mColorIndex + 1) % this.mColors.length;
    }

    float getRotation() {
        return this.mRotation;
    }

    boolean getShowArrow() {
        return this.mShowArrow;
    }

    float getStartTrim() {
        return this.mStartTrim;
    }

    int getStartingColor() {
        return this.mColors[this.mColorIndex];
    }

    float getStartingEndTrim() {
        return this.mStartingEndTrim;
    }

    float getStartingRotation() {
        return this.mStartingRotation;
    }

    float getStartingStartTrim() {
        return this.mStartingStartTrim;
    }

    Paint.Cap getStrokeCap() {
        return this.mPaint.getStrokeCap();
    }

    float getStrokeWidth() {
        return this.mStrokeWidth;
    }

    void goToNextColor() {
        this.setColorIndex(this.getNextColorIndex());
    }

    void resetOriginals() {
        this.mStartingStartTrim = 0.0f;
        this.mStartingEndTrim = 0.0f;
        this.mStartingRotation = 0.0f;
        this.setStartTrim(0.0f);
        this.setEndTrim(0.0f);
        this.setRotation(0.0f);
    }

    void setAlpha(int n) {
        this.mAlpha = n;
    }

    void setArrowDimensions(float f, float f2) {
        this.mArrowWidth = (int)f;
        this.mArrowHeight = (int)f2;
    }

    void setArrowScale(float f) {
        if (f != this.mArrowScale) {
            this.mArrowScale = f;
        }
    }

    void setBackgroundColor(int n) {
        this.mCirclePaint.setColor(n);
    }

    void setCenterRadius(float f) {
        this.mRingCenterRadius = f;
    }

    void setColor(int n) {
        this.mCurrentColor = n;
    }

    void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    void setColorIndex(int n) {
        this.mColorIndex = n;
        this.mCurrentColor = this.mColors[this.mColorIndex];
    }

    void setColors(@NonNull int[] arrn) {
        this.mColors = arrn;
        this.setColorIndex(0);
    }

    void setEndTrim(float f) {
        this.mEndTrim = f;
    }

    void setRotation(float f) {
        this.mRotation = f;
    }

    void setShowArrow(boolean bl) {
        if (this.mShowArrow != bl) {
            this.mShowArrow = bl;
        }
    }

    void setStartTrim(float f) {
        this.mStartTrim = f;
    }

    void setStrokeCap(Paint.Cap cap) {
        this.mPaint.setStrokeCap(cap);
    }

    void setStrokeWidth(float f) {
        this.mStrokeWidth = f;
        this.mPaint.setStrokeWidth(f);
    }

    void storeOriginals() {
        this.mStartingStartTrim = this.mStartTrim;
        this.mStartingEndTrim = this.mEndTrim;
        this.mStartingRotation = this.mRotation;
    }
}
