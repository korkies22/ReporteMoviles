/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.BitmapShader
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 */
package de.cisha.android.view;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import de.cisha.chess.util.BitmapHelper;
import de.cisha.chess.util.Logger;

public class RoundedImageDrawable
extends Drawable {
    private static final boolean USE_VIGNETTE = false;
    private Bitmap _bitmapScaledWithCorners;
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private final float mCornerRadius;
    private final int mMargin;
    private final Paint mPaint;
    private final RectF mRect = new RectF();

    public RoundedImageDrawable(Bitmap bitmap, float f, int n) {
        this.mBitmap = bitmap;
        this.mCornerRadius = f;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mMargin = n;
    }

    public void draw(Canvas object) {
        Object object2 = new RectF(this.mRect.left - 2.0f, this.mRect.top - 2.0f, this.mRect.right + 2.0f, this.mRect.bottom + 2.0f);
        Paint paint = new Paint();
        paint.setColor(Color.rgb((int)155, (int)155, (int)155));
        RectF rectF = new RectF((RectF)object2);
        rectF.offset(1.0f, 1.0f);
        object.drawRoundRect(rectF, this.mCornerRadius, this.mCornerRadius, paint);
        paint.setColor(-1);
        object.drawRoundRect((RectF)object2, this.mCornerRadius, this.mCornerRadius, paint);
        if (this._bitmapScaledWithCorners != null && !this._bitmapScaledWithCorners.isRecycled()) {
            object2 = new Rect(0, 0, this._bitmapScaledWithCorners.getWidth(), this._bitmapScaledWithCorners.getHeight());
            object.drawBitmap(this._bitmapScaledWithCorners, (Rect)object2, this.mRect, this.mPaint);
            return;
        }
        object2 = Logger.getInstance();
        object = this._bitmapScaledWithCorners == null ? "internal Bitmap was null" : "Bitmap was recycled";
        object2.error("RoundedImageDrawable", (String)object);
    }

    public int getOpacity() {
        return -3;
    }

    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mRect.set((float)this.mMargin, (float)this.mMargin, (float)(rect.width() - this.mMargin), (float)(rect.height() - this.mMargin));
        int n = (int)this.mRect.width();
        int n2 = (int)this.mRect.height();
        if (this.mBitmap.getWidth() < this.mBitmap.getHeight()) {
            float f = (float)this.mBitmap.getHeight() / (float)this.mBitmap.getWidth();
            n2 = (int)Math.floor((float)n2 * f + 1.0f);
        } else {
            float f = (float)this.mBitmap.getWidth() / (float)this.mBitmap.getHeight();
            n = (int)Math.floor((float)n * f + 1.0f);
        }
        this.mRect.set((float)this.mMargin, (float)this.mMargin, (float)(this.mMargin + n), (float)(this.mMargin + n2));
        this._bitmapScaledWithCorners = BitmapHelper.getRoundedCornerBitmap(this.mBitmap, (int)this.mCornerRadius, n, n2);
    }

    public void setAlpha(int n) {
        this.mPaint.setAlpha(n);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }
}
