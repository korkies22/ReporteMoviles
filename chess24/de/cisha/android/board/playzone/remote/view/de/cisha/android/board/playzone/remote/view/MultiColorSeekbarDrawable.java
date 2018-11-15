/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.ColorFilter
 *  android.graphics.LinearGradient
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.Drawable
 */
package de.cisha.android.board.playzone.remote.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

public class MultiColorSeekbarDrawable
extends Drawable {
    private static final int[] colors = new int[]{Color.rgb((int)255, (int)8, (int)17), Color.rgb((int)248, (int)237, (int)0), Color.rgb((int)3, (int)125, (int)53)};
    private Paint _paint = new Paint();

    public MultiColorSeekbarDrawable() {
        this._paint.setDither(true);
    }

    public void draw(Canvas canvas) {
        Rect rect = this.copyBounds();
        rect.inset(0, rect.height() / 4);
        canvas.drawRect(rect, this._paint);
    }

    public int getOpacity() {
        return -3;
    }

    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        rect = new LinearGradient(0.0f, rect.exactCenterY(), (float)rect.width(), rect.exactCenterY(), colors, null, Shader.TileMode.CLAMP);
        this._paint.setShader((Shader)rect);
    }

    public void setAlpha(int n) {
        this._paint.setAlpha(n);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this._paint.setColorFilter(colorFilter);
    }
}
