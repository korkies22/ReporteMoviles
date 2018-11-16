// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote.view;

import android.graphics.ColorFilter;
import android.graphics.Shader;
import android.graphics.LinearGradient;
import android.graphics.Shader.TileMode;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class MultiColorSeekbarDrawable extends Drawable
{
    private static final int[] colors;
    private Paint _paint;
    
    static {
        colors = new int[] { Color.rgb(255, 8, 17), Color.rgb(248, 237, 0), Color.rgb(3, 125, 53) };
    }
    
    public MultiColorSeekbarDrawable() {
        (this._paint = new Paint()).setDither(true);
    }
    
    public void draw(final Canvas canvas) {
        final Rect copyBounds = this.copyBounds();
        copyBounds.inset(0, copyBounds.height() / 4);
        canvas.drawRect(copyBounds, this._paint);
    }
    
    public int getOpacity() {
        return -3;
    }
    
    protected void onBoundsChange(final Rect rect) {
        super.onBoundsChange(rect);
        this._paint.setShader((Shader)new LinearGradient(0.0f, rect.exactCenterY(), (float)rect.width(), rect.exactCenterY(), MultiColorSeekbarDrawable.colors, (float[])null, Shader.TileMode.CLAMP));
    }
    
    public void setAlpha(final int alpha) {
        this._paint.setAlpha(alpha);
    }
    
    public void setColorFilter(final ColorFilter colorFilter) {
        this._paint.setColorFilter(colorFilter);
    }
}
