// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.account.view;

import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.content.Context;
import android.graphics.Path;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class TwoColorDiagonalDividerDrawable extends Drawable
{
    private Paint _paintLeft;
    private Paint _paintRight;
    private Path _pathLeft;
    private Path _pathRight;
    
    public TwoColorDiagonalDividerDrawable(final Context context, int color, int color2) {
        color = context.getResources().getColor(color);
        color2 = context.getResources().getColor(color2);
        (this._paintLeft = new Paint()).setAntiAlias(true);
        this._paintLeft.setColor(color);
        (this._paintRight = new Paint()).setAntiAlias(true);
        this._paintRight.setColor(color2);
    }
    
    public void draw(final Canvas canvas) {
        canvas.drawPath(this._pathLeft, this._paintLeft);
        canvas.drawPath(this._pathRight, this._paintRight);
    }
    
    public int getOpacity() {
        return -3;
    }
    
    protected void onBoundsChange(final Rect rect) {
        super.onBoundsChange(rect);
        (this._pathLeft = new Path()).moveTo(0.0f, 0.0f);
        this._pathLeft.lineTo((float)rect.width(), 0.0f);
        this._pathLeft.lineTo(0.0f, (float)rect.height());
        this._pathLeft.close();
        (this._pathRight = new Path()).moveTo((float)rect.width(), 0.0f);
        this._pathRight.lineTo((float)rect.width(), (float)rect.height());
        this._pathRight.lineTo(0.0f, (float)rect.height());
        this._pathRight.close();
    }
    
    public void setAlpha(final int n) {
        this._paintLeft.setAlpha(n);
        this._paintRight.setAlpha(n);
    }
    
    public void setColorFilter(final ColorFilter colorFilter) {
        this._paintLeft.setColorFilter(colorFilter);
        this._paintRight.setColorFilter(colorFilter);
    }
}
