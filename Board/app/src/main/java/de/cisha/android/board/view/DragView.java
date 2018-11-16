// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import android.graphics.Paint;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View.OnTouchListener;
import android.graphics.Bitmap;
import android.view.View;

public class DragView extends View
{
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
    
    public DragView(final Context context) {
        super(context);
        this.init();
    }
    
    public DragView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setWillNotDraw(false);
        this._onTouchListener = (View.OnTouchListener)new View.OnTouchListener() {
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    DragView.this.startDrag(view);
                }
                return true;
            }
        };
    }
    
    private void setDragPosition(final int n, final int n2) {
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
        }
    }
    
    public void addDraggable(final View view) {
        view.setOnTouchListener(this._onTouchListener);
    }
    
    public void destroy() {
        synchronized (this) {
            if (this._bitmap != null) {
                this._bitmap.recycle();
                this._bitmap = null;
            }
        }
    }
    
    public void handleTouchEvent(final MotionEvent motionEvent) {
        final int action = motionEvent.getAction();
        final int startX = (int)motionEvent.getX();
        final int startY = (int)motionEvent.getY();
        boolean drag = false;
        if (action == 0) {
            this._drag = false;
            this.setDragPosition(this._startX = startX, this._startY = startY);
            return;
        }
        if (action != 2) {
            this.stopDrag();
            return;
        }
        if (!this._drag) {
            if (Math.abs(this._startX - startX) + Math.abs(this._startY - startY) > 10) {
                drag = true;
            }
            this._drag = drag;
        }
        this.setDragPosition(startX, startY);
    }
    
    protected void onDraw(final Canvas canvas) {
        if (this._bitmap != null && !this._bitmap.isRecycled()) {
            canvas.drawBitmap(this._bitmap, (float)this._x, (float)this._y, (Paint)null);
        }
    }
    
    public void startDrag(final View view) {
        synchronized (this) {
            final boolean drawingCacheEnabled = view.isDrawingCacheEnabled();
            view.setDrawingCacheEnabled(true);
            final Bitmap drawingCache = view.getDrawingCache();
            if (drawingCache != null) {
                this._bitmap = Bitmap.createScaledBitmap(drawingCache, (int)(view.getWidth() * 2.0f), (int)(view.getHeight() * 2.0f), false);
            }
            if (this._bitmap != null) {
                this._xOffset = this._bitmap.getWidth() / 2;
                this._yOffset = this._bitmap.getHeight() - this._bitmap.getHeight() / 4;
            }
            view.setDrawingCacheEnabled(drawingCacheEnabled);
        }
    }
}
