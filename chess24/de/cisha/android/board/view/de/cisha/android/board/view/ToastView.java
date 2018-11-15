/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.CornerPathEffect
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.PathEffect
 *  android.util.AttributeSet
 *  android.widget.LinearLayout
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ToastView
extends LinearLayout {
    private Paint _bgPaint;

    public ToastView(Context context) {
        super(context);
        this.initBackground();
    }

    public ToastView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initBackground();
    }

    private void initBackground() {
        this.setOrientation(0);
        this.setPadding(10, 10, 10, 10);
        this.setWillNotDraw(false);
        this._bgPaint = new Paint();
        this._bgPaint.setStyle(Paint.Style.FILL);
        this._bgPaint.setColor(Color.argb((int)150, (int)0, (int)0, (int)0));
        this._bgPaint.setPathEffect((PathEffect)new CornerPathEffect(10.0f));
    }

    public void hide() {
        this.setVisibility(8);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0.0f, (float)this.getHeight(), (float)this.getWidth(), 0.0f, this._bgPaint);
    }

    public void show() {
        this.setVisibility(0);
    }

    public void showFor(int n) {
        this.setVisibility(0);
        this.postDelayed(new Runnable(){

            @Override
            public void run() {
                ToastView.this.hide();
            }
        }, (long)n);
    }

}
