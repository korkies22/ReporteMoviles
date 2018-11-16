// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote.view;

import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class EloMarkDrawable extends Drawable
{
    private static final float HALF_SPACE = 5.0f;
    private Paint _paint;
    private Path _pathArrowBottom;
    private Path _pathArrowTop;
    
    public EloMarkDrawable() {
        (this._paint = new Paint()).setAntiAlias(true);
        this._paint.setColor(-16777216);
        this._pathArrowTop = new Path();
        this._pathArrowBottom = new Path();
    }
    
    public void draw(final Canvas canvas) {
        canvas.drawPath(this._pathArrowBottom, this._paint);
        canvas.drawPath(this._pathArrowTop, this._paint);
    }
    
    public int getOpacity() {
        return -3;
    }
    
    protected void onBoundsChange(final Rect rect) {
        super.onBoundsChange(rect);
        this._pathArrowTop.moveTo(0.0f, 0.0f);
        final float max = Math.max(rect.exactCenterY() - 5.0f, 0.0f);
        this._pathArrowTop.lineTo(rect.exactCenterX(), max);
        this._pathArrowTop.lineTo((float)rect.width(), 0.0f);
        this._pathArrowTop.close();
        this._pathArrowBottom.moveTo(0.0f, (float)rect.height());
        this._pathArrowBottom.lineTo(rect.exactCenterX(), rect.height() - max);
        this._pathArrowBottom.lineTo((float)rect.width(), (float)rect.height());
        this._pathArrowBottom.close();
    }
    
    public void setAlpha(final int alpha) {
        this._paint.setAlpha(alpha);
    }
    
    public void setColorFilter(final ColorFilter colorFilter) {
        this._paint.setColorFilter(colorFilter);
    }
}
