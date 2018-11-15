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

public class TwoColorDiagonalDividerDrawable
extends Drawable {
    private Paint _paintLeft;
    private Paint _paintRight;
    private Path _pathLeft;
    private Path _pathRight;

    public TwoColorDiagonalDividerDrawable(Context context, int n, int n2) {
        n = context.getResources().getColor(n);
        n2 = context.getResources().getColor(n2);
        this._paintLeft = new Paint();
        this._paintLeft.setAntiAlias(true);
        this._paintLeft.setColor(n);
        this._paintRight = new Paint();
        this._paintRight.setAntiAlias(true);
        this._paintRight.setColor(n2);
    }

    public void draw(Canvas canvas) {
        canvas.drawPath(this._pathLeft, this._paintLeft);
        canvas.drawPath(this._pathRight, this._paintRight);
    }

    public int getOpacity() {
        return -3;
    }

    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this._pathLeft = new Path();
        this._pathLeft.moveTo(0.0f, 0.0f);
        this._pathLeft.lineTo((float)rect.width(), 0.0f);
        this._pathLeft.lineTo(0.0f, (float)rect.height());
        this._pathLeft.close();
        this._pathRight = new Path();
        this._pathRight.moveTo((float)rect.width(), 0.0f);
        this._pathRight.lineTo((float)rect.width(), (float)rect.height());
        this._pathRight.lineTo(0.0f, (float)rect.height());
        this._pathRight.close();
    }

    public void setAlpha(int n) {
        this._paintLeft.setAlpha(n);
        this._paintRight.setAlpha(n);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this._paintLeft.setColorFilter(colorFilter);
        this._paintRight.setColorFilter(colorFilter);
    }
}
