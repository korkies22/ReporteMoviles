/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.ColorFilter
 *  android.graphics.LinearGradient
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.Drawable
 */
package de.cisha.android.board.modalfragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

public class GoPremiumTitleBackgroundDrawable
extends Drawable {
    private float _cornerRadius;
    private Paint _paint;
    private RectF _rectBottom;
    private RectF _rectTop;

    public GoPremiumTitleBackgroundDrawable(float f) {
        this._cornerRadius = f;
        this._paint = new Paint(1);
        this._paint.setStyle(Paint.Style.FILL);
    }

    public void draw(Canvas canvas) {
        canvas.drawRoundRect(this._rectTop, this._cornerRadius, this._cornerRadius, this._paint);
        canvas.drawRect(this._rectBottom, this._paint);
    }

    public int getOpacity() {
        return -3;
    }

    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this._rectTop = new RectF(0.0f, 0.0f, (float)rect.width(), (float)rect.height());
        this._rectBottom = new RectF(0.0f, rect.exactCenterY(), (float)rect.width(), (float)rect.height());
        rect = new LinearGradient((float)rect.centerX(), 0.0f, (float)rect.centerX(), (float)rect.height(), Color.rgb((int)62, (int)108, (int)176), Color.rgb((int)0, (int)74, (int)158), Shader.TileMode.CLAMP);
        this._paint.setShader((Shader)rect);
    }

    public void setAlpha(int n) {
        this._paint.setAlpha(n);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this._paint.setColorFilter(colorFilter);
    }
}
