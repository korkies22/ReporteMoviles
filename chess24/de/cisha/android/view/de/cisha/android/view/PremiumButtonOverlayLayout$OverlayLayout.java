/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.widget.FrameLayout
 */
package de.cisha.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import de.cisha.android.view.PremiumButtonOverlayLayout;

public static class PremiumButtonOverlayLayout.OverlayLayout
extends FrameLayout {
    private boolean _overlayEnabled;
    private Paint _paint;

    public PremiumButtonOverlayLayout.OverlayLayout(Context context) {
        super(context);
        this.init();
    }

    public PremiumButtonOverlayLayout.OverlayLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this._overlayEnabled = false;
        this._paint = new Paint();
        this._paint.setColor(OVERLAY_COLOR);
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this._overlayEnabled) {
            canvas.drawPaint(this._paint);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this._overlayEnabled;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    public void setOverlayEnabled(boolean bl) {
        this._overlayEnabled = bl;
        this.invalidate();
    }
}
