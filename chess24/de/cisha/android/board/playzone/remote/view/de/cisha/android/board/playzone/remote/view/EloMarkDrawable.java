/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Path
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 */
package de.cisha.android.board.playzone.remote.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class EloMarkDrawable
extends Drawable {
    private static final float HALF_SPACE = 5.0f;
    private Paint _paint = new Paint();
    private Path _pathArrowBottom;
    private Path _pathArrowTop;

    public EloMarkDrawable() {
        this._paint.setAntiAlias(true);
        this._paint.setColor(-16777216);
        this._pathArrowTop = new Path();
        this._pathArrowBottom = new Path();
    }

    public void draw(Canvas canvas) {
        canvas.drawPath(this._pathArrowBottom, this._paint);
        canvas.drawPath(this._pathArrowTop, this._paint);
    }

    public int getOpacity() {
        return -3;
    }

    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this._pathArrowTop.moveTo(0.0f, 0.0f);
        float f = Math.max(rect.exactCenterY() - 5.0f, 0.0f);
        this._pathArrowTop.lineTo(rect.exactCenterX(), f);
        this._pathArrowTop.lineTo((float)rect.width(), 0.0f);
        this._pathArrowTop.close();
        this._pathArrowBottom.moveTo(0.0f, (float)rect.height());
        this._pathArrowBottom.lineTo(rect.exactCenterX(), (float)rect.height() - f);
        this._pathArrowBottom.lineTo((float)rect.width(), (float)rect.height());
        this._pathArrowBottom.close();
    }

    public void setAlpha(int n) {
        this._paint.setAlpha(n);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this._paint.setColorFilter(colorFilter);
    }
}
