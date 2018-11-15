/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapShader
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.util.AttributeSet
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.broadcast.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class TournamentDetailsInfoHeaderView
extends LinearLayout {
    private int _bgHeight;
    private Rect _bgRect;
    private Bitmap _bitmapBg;
    private Paint _paint;

    public TournamentDetailsInfoHeaderView(Context context) {
        super(context);
        this.init();
    }

    public TournamentDetailsInfoHeaderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setWillNotDraw(false);
        this._bitmapBg = BitmapFactory.decodeResource((Resources)this.getResources(), (int)2131231862);
        this._bgHeight = this._bitmapBg.getHeight();
        this._paint = new Paint();
        this._paint.setShader((Shader)new BitmapShader(this._bitmapBg, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR));
        this.setMinimumHeight(this._bgHeight);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawRect(this._bgRect, this._paint);
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this._bgRect = new Rect(0, 0, n, n2);
    }
}
