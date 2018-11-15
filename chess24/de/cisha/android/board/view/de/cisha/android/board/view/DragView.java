/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DragView
extends View {
    private static final float SCALE = 2.0f;
    private Bitmap _bitmap;
    private boolean _drag;
    private View.OnTouchListener _onTouchListener;
    private int _startX;
    private int _startY;
    private int _x;
    private int _xOffset;
    private int _y;
    private int _yOffset;

    public DragView(Context context) {
        super(context);
        this.init();
    }

    public DragView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setWillNotDraw(false);
        this._onTouchListener = new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    DragView.this.startDrag(view);
                }
                return true;
            }
        };
    }

    private void setDragPosition(int n, int n2) {
        this._x = n - this._xOffset;
        this._y = n2 - this._yOffset;
        if (this._drag) {
            this.invalidate();
        }
    }

    private void stopDrag() {
        synchronized (this) {
            if (this._bitmap != null) {
                this._bitmap.recycle();
                this._bitmap = null;
            }
            this.invalidate();
            return;
        }
    }

    public void addDraggable(View view) {
        view.setOnTouchListener(this._onTouchListener);
    }

    public void destroy() {
        synchronized (this) {
            if (this._bitmap != null) {
                this._bitmap.recycle();
                this._bitmap = null;
            }
            return;
        }
    }

    public void handleTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getAction();
        int n2 = (int)motionEvent.getX();
        int n3 = (int)motionEvent.getY();
        boolean bl = false;
        if (n != 0) {
            if (n != 2) {
                this.stopDrag();
                return;
            }
            if (!this._drag) {
                if (Math.abs(this._startX - n2) + Math.abs(this._startY - n3) > 10) {
                    bl = true;
                }
                this._drag = bl;
            }
            this.setDragPosition(n2, n3);
            return;
        }
        this._drag = false;
        this._startX = n2;
        this._startY = n3;
        this.setDragPosition(n2, n3);
    }

    protected void onDraw(Canvas canvas) {
        if (this._bitmap != null && !this._bitmap.isRecycled()) {
            canvas.drawBitmap(this._bitmap, (float)this._x, (float)this._y, null);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void startDrag(View view) {
        synchronized (this) {
            boolean bl = view.isDrawingCacheEnabled();
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = view.getDrawingCache();
            if (bitmap != null) {
                this._bitmap = Bitmap.createScaledBitmap((Bitmap)bitmap, (int)((int)((float)view.getWidth() * 2.0f)), (int)((int)((float)view.getHeight() * 2.0f)), (boolean)false);
            }
            if (this._bitmap != null) {
                this._xOffset = this._bitmap.getWidth() / 2;
                this._yOffset = this._bitmap.getHeight() - this._bitmap.getHeight() / 4;
            }
            view.setDrawingCacheEnabled(bl);
            return;
        }
    }

}
