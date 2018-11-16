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

public class PremiumFlagDrawable extends Drawable
{
    private static final int ARROW_SIZE = 5;
    private float _dipFactor;
    private Paint _paint;
    private Path _path;
    
    public PremiumFlagDrawable(final Context context, final int n) {
        this._dipFactor = context.getResources().getDisplayMetrics().density;
        (this._paint = new Paint()).setAntiAlias(true);
        this._paint.setColor(context.getResources().getColor(n));
    }
    
    public void draw(final Canvas canvas) {
        canvas.drawPath(this._path, this._paint);
    }
    
    public int getOpacity() {
        return -3;
    }
    
    public boolean getPadding(final Rect rect) {
        final int n = (int)(this._dipFactor * 5.0f);
        final int n2 = (int)(5.0f * this._dipFactor);
        final int left = n2 * 2;
        rect.left = left;
        rect.right = n + left;
        rect.top = n2;
        rect.bottom = n2;
        return true;
    }
    
    protected void onBoundsChange(final Rect rect) {
        super.onBoundsChange(rect);
        (this._path = new Path()).moveTo(0.0f, 0.0f);
        this._path.lineTo((float)rect.width(), 0.0f);
        this._path.lineTo(rect.width() - 5.0f * this._dipFactor, (float)(rect.height() / 2));
        this._path.lineTo((float)rect.width(), (float)rect.height());
        this._path.lineTo(0.0f, (float)rect.height());
        this._path.close();
    }
    
    public void setAlpha(final int alpha) {
        this._paint.setAlpha(alpha);
    }
    
    public void setColorFilter(final ColorFilter colorFilter) {
        this._paint.setColorFilter(colorFilter);
    }
}
