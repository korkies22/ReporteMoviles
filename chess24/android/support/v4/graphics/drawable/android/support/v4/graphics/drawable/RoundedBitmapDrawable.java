/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapShader
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.Drawable
 *  android.util.DisplayMetrics
 */
package android.support.v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;

@RequiresApi(value=9)
public abstract class RoundedBitmapDrawable
extends Drawable {
    private static final int DEFAULT_PAINT_FLAGS = 3;
    private boolean mApplyGravity = true;
    final Bitmap mBitmap;
    private int mBitmapHeight;
    private final BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private float mCornerRadius;
    final Rect mDstRect = new Rect();
    private final RectF mDstRectF = new RectF();
    private int mGravity = 119;
    private boolean mIsCircular;
    private final Paint mPaint = new Paint(3);
    private final Matrix mShaderMatrix = new Matrix();
    private int mTargetDensity = 160;

    RoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
        if (resources != null) {
            this.mTargetDensity = resources.getDisplayMetrics().densityDpi;
        }
        this.mBitmap = bitmap;
        if (this.mBitmap != null) {
            this.computeBitmapSize();
            this.mBitmapShader = new BitmapShader(this.mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            return;
        }
        this.mBitmapHeight = -1;
        this.mBitmapWidth = -1;
        this.mBitmapShader = null;
    }

    private void computeBitmapSize() {
        this.mBitmapWidth = this.mBitmap.getScaledWidth(this.mTargetDensity);
        this.mBitmapHeight = this.mBitmap.getScaledHeight(this.mTargetDensity);
    }

    private static boolean isGreaterThanZero(float f) {
        if (f > 0.05f) {
            return true;
        }
        return false;
    }

    private void updateCircularCornerRadius() {
        this.mCornerRadius = Math.min(this.mBitmapHeight, this.mBitmapWidth) / 2;
    }

    public void draw(@NonNull Canvas canvas) {
        Bitmap bitmap = this.mBitmap;
        if (bitmap == null) {
            return;
        }
        this.updateDstRect();
        if (this.mPaint.getShader() == null) {
            canvas.drawBitmap(bitmap, null, this.mDstRect, this.mPaint);
            return;
        }
        canvas.drawRoundRect(this.mDstRectF, this.mCornerRadius, this.mCornerRadius, this.mPaint);
    }

    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    @Nullable
    public final Bitmap getBitmap() {
        return this.mBitmap;
    }

    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    public int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    public int getOpacity() {
        int n = this.mGravity;
        int n2 = -3;
        if (n == 119) {
            if (this.mIsCircular) {
                return -3;
            }
            Bitmap bitmap = this.mBitmap;
            n = n2;
            if (bitmap != null) {
                n = n2;
                if (!bitmap.hasAlpha()) {
                    n = n2;
                    if (this.mPaint.getAlpha() >= 255) {
                        if (RoundedBitmapDrawable.isGreaterThanZero(this.mCornerRadius)) {
                            return -3;
                        }
                        n = -1;
                    }
                }
            }
            return n;
        }
        return -3;
    }

    @NonNull
    public final Paint getPaint() {
        return this.mPaint;
    }

    void gravityCompatApply(int n, int n2, int n3, Rect rect, Rect rect2) {
        throw new UnsupportedOperationException();
    }

    public boolean hasAntiAlias() {
        return this.mPaint.isAntiAlias();
    }

    public boolean hasMipMap() {
        throw new UnsupportedOperationException();
    }

    public boolean isCircular() {
        return this.mIsCircular;
    }

    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        if (this.mIsCircular) {
            this.updateCircularCornerRadius();
        }
        this.mApplyGravity = true;
    }

    public void setAlpha(int n) {
        if (n != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(n);
            this.invalidateSelf();
        }
    }

    public void setAntiAlias(boolean bl) {
        this.mPaint.setAntiAlias(bl);
        this.invalidateSelf();
    }

    public void setCircular(boolean bl) {
        this.mIsCircular = bl;
        this.mApplyGravity = true;
        if (bl) {
            this.updateCircularCornerRadius();
            this.mPaint.setShader((Shader)this.mBitmapShader);
            this.invalidateSelf();
            return;
        }
        this.setCornerRadius(0.0f);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        this.invalidateSelf();
    }

    public void setCornerRadius(float f) {
        if (this.mCornerRadius == f) {
            return;
        }
        this.mIsCircular = false;
        if (RoundedBitmapDrawable.isGreaterThanZero(f)) {
            this.mPaint.setShader((Shader)this.mBitmapShader);
        } else {
            this.mPaint.setShader(null);
        }
        this.mCornerRadius = f;
        this.invalidateSelf();
    }

    public void setDither(boolean bl) {
        this.mPaint.setDither(bl);
        this.invalidateSelf();
    }

    public void setFilterBitmap(boolean bl) {
        this.mPaint.setFilterBitmap(bl);
        this.invalidateSelf();
    }

    public void setGravity(int n) {
        if (this.mGravity != n) {
            this.mGravity = n;
            this.mApplyGravity = true;
            this.invalidateSelf();
        }
    }

    public void setMipMap(boolean bl) {
        throw new UnsupportedOperationException();
    }

    public void setTargetDensity(int n) {
        if (this.mTargetDensity != n) {
            int n2 = n;
            if (n == 0) {
                n2 = 160;
            }
            this.mTargetDensity = n2;
            if (this.mBitmap != null) {
                this.computeBitmapSize();
            }
            this.invalidateSelf();
        }
    }

    public void setTargetDensity(@NonNull Canvas canvas) {
        this.setTargetDensity(canvas.getDensity());
    }

    public void setTargetDensity(@NonNull DisplayMetrics displayMetrics) {
        this.setTargetDensity(displayMetrics.densityDpi);
    }

    void updateDstRect() {
        if (this.mApplyGravity) {
            if (this.mIsCircular) {
                int n = Math.min(this.mBitmapWidth, this.mBitmapHeight);
                this.gravityCompatApply(this.mGravity, n, n, this.getBounds(), this.mDstRect);
                n = Math.min(this.mDstRect.width(), this.mDstRect.height());
                int n2 = Math.max(0, (this.mDstRect.width() - n) / 2);
                int n3 = Math.max(0, (this.mDstRect.height() - n) / 2);
                this.mDstRect.inset(n2, n3);
                this.mCornerRadius = 0.5f * (float)n;
            } else {
                this.gravityCompatApply(this.mGravity, this.mBitmapWidth, this.mBitmapHeight, this.getBounds(), this.mDstRect);
            }
            this.mDstRectF.set(this.mDstRect);
            if (this.mBitmapShader != null) {
                this.mShaderMatrix.setTranslate(this.mDstRectF.left, this.mDstRectF.top);
                this.mShaderMatrix.preScale(this.mDstRectF.width() / (float)this.mBitmap.getWidth(), this.mDstRectF.height() / (float)this.mBitmap.getHeight());
                this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
                this.mPaint.setShader((Shader)this.mBitmapShader);
            }
            this.mApplyGravity = false;
        }
    }
}
