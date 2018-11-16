// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.modalfragments;

import android.graphics.ColorFilter;
import android.graphics.Path.Direction;
import android.graphics.Shader;
import android.graphics.LinearGradient;
import android.graphics.Shader.TileMode;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.graphics.PathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class GoPremiumLogoBackgroundDrawable extends Drawable
{
    private float _dpi;
    private Paint _paint;
    private Paint _paintCorner;
    private Paint _paintPath;
    private Path _path;
    private Path _pathShaddow;
    private Path _pathUpperLeftCorner;
    
    public GoPremiumLogoBackgroundDrawable(final float n, final float dpi) {
        this._dpi = dpi;
        (this._paintPath = new Paint(1)).setStyle(Paint.Style.FILL);
        this._paintPath.setColor(Color.rgb(220, 220, 220));
        (this._paint = new Paint(1)).setColor(Color.argb(100, 0, 0, 0));
        this._paint.setStyle(Paint.Style.FILL);
        (this._paintCorner = new Paint(1)).setStyle(Paint.Style.FILL);
        this._paintCorner.setColor(Color.rgb(220, 220, 220));
        this._paintCorner.setPathEffect((PathEffect)new CornerPathEffect(n));
    }
    
    public void draw(final Canvas canvas) {
        canvas.drawPath(this._pathShaddow, this._paint);
        canvas.drawPath(this._pathUpperLeftCorner, this._paintCorner);
        canvas.drawPath(this._path, this._paintPath);
    }
    
    public int getOpacity() {
        return -3;
    }
    
    protected void onBoundsChange(final Rect rect) {
        super.onBoundsChange(rect);
        (this._path = new Path()).moveTo(10.0f, 0.0f);
        this._path.lineTo((float)rect.width(), 0.0f);
        this._path.lineTo(rect.width() - this._dpi * 10.0f, (float)(rect.height() - 1));
        this._path.lineTo(0.0f, (float)(rect.height() - 1));
        this._path.lineTo(0.0f, 10.0f);
        this._path.lineTo(10.0f, 10.0f);
        this._path.close();
        final LinearGradient linearGradient = new LinearGradient((float)rect.centerX(), 0.0f, (float)rect.centerX(), (float)rect.height(), Color.rgb(255, 255, 255), Color.rgb(171, 171, 171), Shader.TileMode.CLAMP);
        this._paintPath.setShader((Shader)linearGradient);
        this._paintCorner.setShader((Shader)linearGradient);
        (this._pathShaddow = new Path()).moveTo(10.0f, 0.0f);
        this._pathShaddow.lineTo((float)rect.width(), 0.0f);
        this._pathShaddow.lineTo(rect.width() - 7.0f * this._dpi, (float)(rect.height() - 1));
        this._pathShaddow.lineTo(0.0f, (float)(rect.height() - 1));
        this._pathShaddow.lineTo(0.0f, 10.0f);
        this._pathShaddow.lineTo(10.0f, 10.0f);
        this._pathShaddow.close();
        this._pathShaddow.offset(0.0f, 1.0f);
        (this._pathUpperLeftCorner = new Path()).addRect(0.0f, 0.0f, rect.exactCenterX(), rect.exactCenterY(), Path.Direction.CCW);
    }
    
    public void setAlpha(final int alpha) {
        this._paintPath.setAlpha(alpha);
    }
    
    public void setColorFilter(final ColorFilter colorFilter) {
        this._paintPath.setColorFilter(colorFilter);
    }
}
