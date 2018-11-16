// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.modalfragments;

import android.graphics.ColorFilter;
import android.graphics.Shader;
import android.graphics.LinearGradient;
import android.graphics.Shader.TileMode;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class GoPremiumTitleBackgroundDrawable extends Drawable
{
    private float _cornerRadius;
    private Paint _paint;
    private RectF _rectBottom;
    private RectF _rectTop;
    
    public GoPremiumTitleBackgroundDrawable(final float cornerRadius) {
        this._cornerRadius = cornerRadius;
        (this._paint = new Paint(1)).setStyle(Paint.Style.FILL);
    }
    
    public void draw(final Canvas canvas) {
        canvas.drawRoundRect(this._rectTop, this._cornerRadius, this._cornerRadius, this._paint);
        canvas.drawRect(this._rectBottom, this._paint);
    }
    
    public int getOpacity() {
        return -3;
    }
    
    protected void onBoundsChange(final Rect rect) {
        super.onBoundsChange(rect);
        this._rectTop = new RectF(0.0f, 0.0f, (float)rect.width(), (float)rect.height());
        this._rectBottom = new RectF(0.0f, rect.exactCenterY(), (float)rect.width(), (float)rect.height());
        this._paint.setShader((Shader)new LinearGradient((float)rect.centerX(), 0.0f, (float)rect.centerX(), (float)rect.height(), Color.rgb(62, 108, 176), Color.rgb(0, 74, 158), Shader.TileMode.CLAMP));
    }
    
    public void setAlpha(final int alpha) {
        this._paint.setAlpha(alpha);
    }
    
    public void setColorFilter(final ColorFilter colorFilter) {
        this._paint.setColorFilter(colorFilter);
    }
}
