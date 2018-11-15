/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Path
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.DisplayMetrics
 */
package de.cisha.android.board.account.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

public class PremiumFlagDrawable
extends Drawable {
    private static final int ARROW_SIZE = 5;
    private float _dipFactor;
    private Paint _paint;
    private Path _path;

    public PremiumFlagDrawable(Context context, int n) {
        this._dipFactor = context.getResources().getDisplayMetrics().density;
        this._paint = new Paint();
        this._paint.setAntiAlias(true);
        this._paint.setColor(context.getResources().getColor(n));
    }

    public void draw(Canvas canvas) {
        canvas.drawPath(this._path, this._paint);
    }

    public int getOpacity() {
        return -3;
    }

    public boolean getPadding(Rect rect) {
        int n;
        int n2 = (int)(this._dipFactor * 5.0f);
        int n3 = (int)(5.0f * this._dipFactor);
        rect.left = n = n3 * 2;
        rect.right = n2 + n;
        rect.top = n3;
        rect.bottom = n3;
        return true;
    }

    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this._path = new Path();
        this._path.moveTo(0.0f, 0.0f);
        this._path.lineTo((float)rect.width(), 0.0f);
        this._path.lineTo((float)rect.width() - 5.0f * this._dipFactor, (float)(rect.height() / 2));
        this._path.lineTo((float)rect.width(), (float)rect.height());
        this._path.lineTo(0.0f, (float)rect.height());
        this._path.close();
    }

    public void setAlpha(int n) {
        this._paint.setAlpha(n);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this._paint.setColorFilter(colorFilter);
    }
}
