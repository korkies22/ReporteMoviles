/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.ColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 */
package de.cisha.android.board.broadcast.standings.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class BroadcastTournamentMatchInfoBackground
extends Drawable {
    private static final int COLOR_ARROW_FILL;
    private static final int COLOR_ARROW_STROKE;
    private static final int COLOR_STROKE;
    private boolean _arrowEnabled;
    private float _arrowHeight;
    private int _arrowMargin;
    private float _arrowWidth;
    private boolean _hasThinLine;
    private boolean _isArrowLeft;
    private int _padding;
    private Paint _paintFill;
    private Paint _paintStroke;
    private Path _path;
    private int _strokeWidth;

    static {
        COLOR_STROKE = Color.rgb((int)166, (int)166, (int)166);
        COLOR_ARROW_STROKE = Color.rgb((int)0, (int)74, (int)158);
        COLOR_ARROW_FILL = Color.argb((int)51, (int)0, (int)74, (int)158);
    }

    public BroadcastTournamentMatchInfoBackground(int n, boolean bl, boolean bl2, boolean bl3) {
        this._padding = n;
        this._hasThinLine = bl3;
        this._paintStroke = new Paint();
        this._paintStroke.setAntiAlias(true);
        this._paintStroke.setStyle(Paint.Style.STROKE);
        this._strokeWidth = 2;
        this._arrowMargin = this._strokeWidth / 2;
        this._paintStroke.setStrokeWidth((float)this._strokeWidth);
        this._path = new Path();
        this._arrowHeight = 10.0f;
        this._arrowWidth = 15.0f;
        this._paintFill = new Paint();
        this._paintFill.setAntiAlias(true);
        this._paintFill.setStyle(Paint.Style.FILL);
        this.setArrowEnabled(bl);
        this.setArrowLeft(bl2);
    }

    public void draw(Canvas canvas) {
        canvas.drawPath(this._path, this._paintFill);
        canvas.drawPath(this._path, this._paintStroke);
    }

    public int getOpacity() {
        return -2;
    }

    public boolean getPadding(Rect rect) {
        int n = this._padding;
        boolean bl = this._isArrowLeft;
        int n2 = 0;
        int n3 = bl ? (int)this._arrowWidth : 0;
        int n4 = this._padding;
        if (!this._isArrowLeft) {
            n2 = (int)this._arrowWidth;
        }
        int n5 = this._padding;
        rect.set(n + n3, this._padding, n4 + n2, n5);
        return true;
    }

    public boolean isArrowLeft() {
        return this._isArrowLeft;
    }

    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        float f = this.getBounds().width() - this._arrowMargin;
        float f2 = this.getBounds().height() - this._arrowMargin;
        float f3 = this.getBounds().height() / 2;
        float f4 = this._arrowHeight;
        float f5 = this._arrowHeight;
        float f6 = (float)this._arrowMargin + f - this._arrowWidth;
        this._path.reset();
        if (this._hasThinLine) {
            this._path.moveTo(0.0f, 0.0f);
            this._path.lineTo(0.0f, (float)this.getBounds().height());
        }
        this._path.moveTo((float)this._arrowMargin, (float)this._arrowMargin);
        if (this._arrowEnabled) {
            this._path.lineTo(f6, (float)this._arrowMargin);
        } else {
            this._path.moveTo(f6, 0.0f);
        }
        this._path.lineTo(f6, f3 - f4);
        if (this._arrowEnabled) {
            this._path.lineTo(f, f3);
        }
        this._path.lineTo(f6, f5 + f3);
        this._path.lineTo(f6, f2);
        if (this._arrowEnabled) {
            this._path.lineTo((float)this._arrowMargin, f2);
        } else {
            this._path.lineTo(f6, (float)this.getBounds().height());
        }
        this._path.close();
        if (this._isArrowLeft) {
            rect = new Matrix();
            rect.setRotate(180.0f, (float)(this.getBounds().width() / 2), (float)(this.getBounds().height() / 2));
            this._path.transform((Matrix)rect);
        }
    }

    public void setAlpha(int n) {
    }

    public void setArrowEnabled(boolean bl) {
        this._arrowEnabled = bl;
        int n = bl ? COLOR_ARROW_FILL : 0;
        this._paintFill.setColor(n);
        n = bl ? COLOR_ARROW_STROKE : COLOR_STROKE;
        this._paintStroke.setColor(n);
    }

    public void setArrowLeft(boolean bl) {
        this._isArrowLeft = bl;
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }
}
