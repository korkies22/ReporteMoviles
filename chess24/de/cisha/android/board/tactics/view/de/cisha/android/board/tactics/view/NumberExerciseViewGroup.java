/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.tactics.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class NumberExerciseViewGroup
extends LinearLayout {
    private Bitmap _bgBitmap;
    private Paint _bgPaint;
    private Rect _bgRect;

    public NumberExerciseViewGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setOrientation(0);
        this._bgBitmap = BitmapFactory.decodeResource((Resources)this.getResources(), (int)2131231814);
        this.setWillNotDraw(false);
        this._bgPaint = new Paint();
        this._bgPaint.setColor(-394759);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawRect(this._bgRect, this._bgPaint);
        int n = this.getPaddingLeft();
        for (int i = 0; i < this.getChildCount(); ++i) {
            canvas.drawBitmap(this._bgBitmap, (float)n, (float)((this.getHeight() - this._bgBitmap.getHeight()) / 2), null);
            n += this.getChildAt(i).getWidth();
        }
        super.onDraw(canvas);
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        this._bgRect = new Rect(this.getPaddingLeft() + this._bgBitmap.getWidth(), 0, this.getWidth(), this.getHeight());
        super.onSizeChanged(n, n2, n3, n4);
    }
}
