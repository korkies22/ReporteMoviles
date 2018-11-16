// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.standings.view;

import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.graphics.Paint.Style;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class BroadcastTournamentMatchInfoBackground extends Drawable
{
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
        COLOR_STROKE = Color.rgb(166, 166, 166);
        COLOR_ARROW_STROKE = Color.rgb(0, 74, 158);
        COLOR_ARROW_FILL = Color.argb(51, 0, 74, 158);
    }
    
    public BroadcastTournamentMatchInfoBackground(final int padding, final boolean arrowEnabled, final boolean arrowLeft, final boolean hasThinLine) {
        this._padding = padding;
        this._hasThinLine = hasThinLine;
        (this._paintStroke = new Paint()).setAntiAlias(true);
        this._paintStroke.setStyle(Paint.Style.STROKE);
        this._strokeWidth = 2;
        this._arrowMargin = this._strokeWidth / 2;
        this._paintStroke.setStrokeWidth((float)this._strokeWidth);
        this._path = new Path();
        this._arrowHeight = 10.0f;
        this._arrowWidth = 15.0f;
        (this._paintFill = new Paint()).setAntiAlias(true);
        this._paintFill.setStyle(Paint.Style.FILL);
        this.setArrowEnabled(arrowEnabled);
        this.setArrowLeft(arrowLeft);
    }
    
    public void draw(final Canvas canvas) {
        canvas.drawPath(this._path, this._paintFill);
        canvas.drawPath(this._path, this._paintStroke);
    }
    
    public int getOpacity() {
        return -2;
    }
    
    public boolean getPadding(final Rect rect) {
        final int padding = this._padding;
        final boolean isArrowLeft = this._isArrowLeft;
        int n = 0;
        int n2;
        if (isArrowLeft) {
            n2 = (int)this._arrowWidth;
        }
        else {
            n2 = 0;
        }
        final int padding2 = this._padding;
        if (!this._isArrowLeft) {
            n = (int)this._arrowWidth;
        }
        rect.set(padding + n2, this._padding, padding2 + n, this._padding);
        return true;
    }
    
    public boolean isArrowLeft() {
        return this._isArrowLeft;
    }
    
    protected void onBoundsChange(final Rect rect) {
        super.onBoundsChange(rect);
        final float n = this.getBounds().width() - this._arrowMargin;
        final float n2 = this.getBounds().height() - this._arrowMargin;
        final float n3 = this.getBounds().height() / 2;
        final float arrowHeight = this._arrowHeight;
        final float arrowHeight2 = this._arrowHeight;
        final float n4 = this._arrowMargin + n - this._arrowWidth;
        this._path.reset();
        if (this._hasThinLine) {
            this._path.moveTo(0.0f, 0.0f);
            this._path.lineTo(0.0f, (float)this.getBounds().height());
        }
        this._path.moveTo((float)this._arrowMargin, (float)this._arrowMargin);
        if (this._arrowEnabled) {
            this._path.lineTo(n4, (float)this._arrowMargin);
        }
        else {
            this._path.moveTo(n4, 0.0f);
        }
        this._path.lineTo(n4, n3 - arrowHeight);
        if (this._arrowEnabled) {
            this._path.lineTo(n, n3);
        }
        this._path.lineTo(n4, arrowHeight2 + n3);
        this._path.lineTo(n4, n2);
        if (this._arrowEnabled) {
            this._path.lineTo((float)this._arrowMargin, n2);
        }
        else {
            this._path.lineTo(n4, (float)this.getBounds().height());
        }
        this._path.close();
        if (this._isArrowLeft) {
            final Matrix matrix = new Matrix();
            matrix.setRotate(180.0f, (float)(this.getBounds().width() / 2), (float)(this.getBounds().height() / 2));
            this._path.transform(matrix);
        }
    }
    
    public void setAlpha(final int n) {
    }
    
    public void setArrowEnabled(final boolean arrowEnabled) {
        this._arrowEnabled = arrowEnabled;
        int color_ARROW_FILL;
        if (arrowEnabled) {
            color_ARROW_FILL = BroadcastTournamentMatchInfoBackground.COLOR_ARROW_FILL;
        }
        else {
            color_ARROW_FILL = 0;
        }
        this._paintFill.setColor(color_ARROW_FILL);
        int color;
        if (arrowEnabled) {
            color = BroadcastTournamentMatchInfoBackground.COLOR_ARROW_STROKE;
        }
        else {
            color = BroadcastTournamentMatchInfoBackground.COLOR_STROKE;
        }
        this._paintStroke.setColor(color);
    }
    
    public void setArrowLeft(final boolean isArrowLeft) {
        this._isArrowLeft = isArrowLeft;
    }
    
    public void setColorFilter(final ColorFilter colorFilter) {
    }
}
