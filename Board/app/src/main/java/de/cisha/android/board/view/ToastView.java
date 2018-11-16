// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import android.graphics.Canvas;
import android.graphics.PathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Paint;
import android.widget.LinearLayout;

public class ToastView extends LinearLayout
{
    private Paint _bgPaint;
    
    public ToastView(final Context context) {
        super(context);
        this.initBackground();
    }
    
    public ToastView(final Context context, final AttributeSet set) {
        super(context, set);
        this.initBackground();
    }
    
    private void initBackground() {
        this.setOrientation(0);
        this.setPadding(10, 10, 10, 10);
        this.setWillNotDraw(false);
        (this._bgPaint = new Paint()).setStyle(Paint.Style.FILL);
        this._bgPaint.setColor(Color.argb(150, 0, 0, 0));
        this._bgPaint.setPathEffect((PathEffect)new CornerPathEffect(10.0f));
    }
    
    public void hide() {
        this.setVisibility(8);
    }
    
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0.0f, (float)this.getHeight(), (float)this.getWidth(), 0.0f, this._bgPaint);
    }
    
    public void show() {
        this.setVisibility(0);
    }
    
    public void showFor(final int n) {
        this.setVisibility(0);
        this.postDelayed((Runnable)new Runnable() {
            @Override
            public void run() {
                ToastView.this.hide();
            }
        }, (long)n);
    }
}
