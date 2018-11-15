/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.ColorFilter
 *  android.graphics.CornerPathEffect
 *  android.graphics.LinearGradient
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.PathEffect
 *  android.graphics.Rect
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.Drawable
 */
package de.cisha.android.board.modalfragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

public class GoPremiumLogoBackgroundDrawable
extends Drawable {
    private float _dpi;
    private Paint _paint;
    private Paint _paintCorner;
    private Paint _paintPath;
    private Path _path;
    private Path _pathShaddow;
    private Path _pathUpperLeftCorner;

    public GoPremiumLogoBackgroundDrawable(float f, float f2) {
        this._dpi = f2;
        this._paintPath = new Paint(1);
        this._paintPath.setStyle(Paint.Style.FILL);
        this._paintPath.setColor(Color.rgb((int)220, (int)220, (int)220));
        this._paint = new Paint(1);
        this._paint.setColor(Color.argb((int)100, (int)0, (int)0, (int)0));
        this._paint.setStyle(Paint.Style.FILL);
        this._paintCorner = new Paint(1);
        this._paintCorner.setStyle(Paint.Style.FILL);
        this._paintCorner.setColor(Color.rgb((int)220, (int)220, (int)220));
        this._paintCorner.setPathEffect((PathEffect)new CornerPathEffect(f));
    }

    public void draw(Canvas canvas) {
        canvas.drawPath(this._pathShaddow, this._paint);
        canvas.drawPath(this._pathUpperLeftCorner, this._paintCorner);
        canvas.drawPath(this._path, this._paintPath);
    }

    public int getOpacity() {
        return -3;
    }

    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this._path = new Path();
        this._path.moveTo(10.0f, 0.0f);
        this._path.lineTo((float)rect.width(), 0.0f);
        this._path.lineTo((float)rect.width() - this._dpi * 10.0f, (float)(rect.height() - 1));
        this._path.lineTo(0.0f, (float)(rect.height() - 1));
        this._path.lineTo(0.0f, 10.0f);
        this._path.lineTo(10.0f, 10.0f);
        this._path.close();
        LinearGradient linearGradient = new LinearGradient((float)rect.centerX(), 0.0f, (float)rect.centerX(), (float)rect.height(), Color.rgb((int)255, (int)255, (int)255), Color.rgb((int)171, (int)171, (int)171), Shader.TileMode.CLAMP);
        this._paintPath.setShader((Shader)linearGradient);
        this._paintCorner.setShader((Shader)linearGradient);
        this._pathShaddow = new Path();
        this._pathShaddow.moveTo(10.0f, 0.0f);
        this._pathShaddow.lineTo((float)rect.width(), 0.0f);
        this._pathShaddow.lineTo((float)rect.width() - 7.0f * this._dpi, (float)(rect.height() - 1));
        this._pathShaddow.lineTo(0.0f, (float)(rect.height() - 1));
        this._pathShaddow.lineTo(0.0f, 10.0f);
        this._pathShaddow.lineTo(10.0f, 10.0f);
        this._pathShaddow.close();
        this._pathShaddow.offset(0.0f, 1.0f);
        this._pathUpperLeftCorner = new Path();
        this._pathUpperLeftCorner.addRect(0.0f, 0.0f, rect.exactCenterX(), rect.exactCenterY(), Path.Direction.CCW);
    }

    public void setAlpha(int n) {
        this._paintPath.setAlpha(n);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this._paintPath.setColorFilter(colorFilter);
    }
}
