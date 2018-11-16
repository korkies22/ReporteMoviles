// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.view;

import android.graphics.ColorFilter;
import de.cisha.chess.util.BitmapHelper;
import de.cisha.chess.util.Logger;
import android.graphics.Rect;
import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Paint;
import android.graphics.BitmapShader;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class RoundedImageDrawable extends Drawable
{
    private static final boolean USE_VIGNETTE = false;
    private Bitmap _bitmapScaledWithCorners;
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private final float mCornerRadius;
    private final int mMargin;
    private final Paint mPaint;
    private final RectF mRect;
    
    public RoundedImageDrawable(final Bitmap mBitmap, final float mCornerRadius, final int mMargin) {
        this.mRect = new RectF();
        this.mBitmap = mBitmap;
        this.mCornerRadius = mCornerRadius;
        (this.mPaint = new Paint()).setAntiAlias(true);
        this.mMargin = mMargin;
    }
    
    public void draw(final Canvas canvas) {
        final RectF rectF = new RectF(this.mRect.left - 2.0f, this.mRect.top - 2.0f, this.mRect.right + 2.0f, this.mRect.bottom + 2.0f);
        final Paint paint = new Paint();
        paint.setColor(Color.rgb(155, 155, 155));
        final RectF rectF2 = new RectF(rectF);
        rectF2.offset(1.0f, 1.0f);
        canvas.drawRoundRect(rectF2, this.mCornerRadius, this.mCornerRadius, paint);
        paint.setColor(-1);
        canvas.drawRoundRect(rectF, this.mCornerRadius, this.mCornerRadius, paint);
        if (this._bitmapScaledWithCorners != null && !this._bitmapScaledWithCorners.isRecycled()) {
            canvas.drawBitmap(this._bitmapScaledWithCorners, new Rect(0, 0, this._bitmapScaledWithCorners.getWidth(), this._bitmapScaledWithCorners.getHeight()), this.mRect, this.mPaint);
            return;
        }
        final Logger instance = Logger.getInstance();
        String s;
        if (this._bitmapScaledWithCorners == null) {
            s = "internal Bitmap was null";
        }
        else {
            s = "Bitmap was recycled";
        }
        instance.error("RoundedImageDrawable", s);
    }
    
    public int getOpacity() {
        return -3;
    }
    
    protected void onBoundsChange(final Rect rect) {
        super.onBoundsChange(rect);
        this.mRect.set((float)this.mMargin, (float)this.mMargin, (float)(rect.width() - this.mMargin), (float)(rect.height() - this.mMargin));
        int n = (int)this.mRect.width();
        int n2 = (int)this.mRect.height();
        if (this.mBitmap.getWidth() < this.mBitmap.getHeight()) {
            n2 = (int)Math.floor(n2 * (this.mBitmap.getHeight() / this.mBitmap.getWidth()) + 1.0f);
        }
        else {
            n = (int)Math.floor(n * (this.mBitmap.getWidth() / this.mBitmap.getHeight()) + 1.0f);
        }
        this.mRect.set((float)this.mMargin, (float)this.mMargin, (float)(this.mMargin + n), (float)(this.mMargin + n2));
        this._bitmapScaledWithCorners = BitmapHelper.getRoundedCornerBitmap(this.mBitmap, (int)this.mCornerRadius, n, n2);
    }
    
    public void setAlpha(final int alpha) {
        this.mPaint.setAlpha(alpha);
    }
    
    public void setColorFilter(final ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }
}
