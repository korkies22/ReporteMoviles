/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.SurfaceHolder
 *  android.view.SurfaceHolder$Callback
 *  android.view.SurfaceView
 *  android.view.View
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class RookieLoadingSurfaceView
extends SurfaceView
implements SurfaceHolder.Callback {
    private static ScheduledExecutorService _executor = Executors.newSingleThreadScheduledExecutor();
    private LinkedList<Bitmap> _bitmapList;
    private int _currentBitmapNumber = 0;
    private boolean _flagCanvasCleared;
    private boolean _flagSurfaceCreated;
    private SurfaceHolder _holder;
    private float _innerBitmapOffsetTop;
    private Paint _paint;
    private Bitmap _rookieBg;
    private int _rookieBgLeft;
    private int _rookieBgTop;

    public RookieLoadingSurfaceView(Context context) {
        super(context);
        this.init();
    }

    public RookieLoadingSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void addBitmapToList(int n) {
        this._bitmapList.add(BitmapFactory.decodeResource((Resources)this.getResources(), (int)n));
    }

    private void drawingOperations(Canvas canvas, Bitmap bitmap) {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.drawBitmap(this._rookieBg, (float)this._rookieBgLeft, (float)this._rookieBgTop, null);
        canvas.drawBitmap(bitmap, (float)(this.getWidth() / 2 - bitmap.getWidth() / 2), this._innerBitmapOffsetTop + (float)(this.getHeight() / 2) - (float)(bitmap.getHeight() / 2), null);
    }

    private void init() {
        this._holder = this.getHolder();
        this._holder.addCallback((SurfaceHolder.Callback)this);
        this._holder.setFormat(-3);
        this._paint = new Paint();
        this._paint.setStyle(Paint.Style.STROKE);
        this._paint.setStrokeWidth(5.0f);
        this.setZOrderOnTop(true);
        this._paint.setColor(Color.rgb((int)50, (int)255, (int)50));
        this._innerBitmapOffsetTop = 12.0f * this.getContext().getResources().getDisplayMetrics().density;
        this._flagCanvasCleared = true;
        _executor.execute(new Runnable(){

            @Override
            public void run() {
                RookieLoadingSurfaceView.this._bitmapList = new LinkedList();
                RookieLoadingSurfaceView.this.addBitmapToList(2131231407);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231418);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231424);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231425);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231426);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231427);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231428);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231429);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231430);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231408);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231409);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231410);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231411);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231412);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231413);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231414);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231415);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231416);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231417);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231419);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231420);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231421);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231422);
                RookieLoadingSurfaceView.this.addBitmapToList(2131231423);
                RookieLoadingSurfaceView.this._rookieBg = BitmapFactory.decodeResource((Resources)RookieLoadingSurfaceView.this.getResources(), (int)2131231433);
            }
        });
    }

    private void startDrawingOperations() {
        if (this._flagSurfaceCreated) {
            if (this.isShown()) {
                Canvas canvas = this._holder.lockCanvas();
                if (canvas != null) {
                    int n;
                    this._currentBitmapNumber = n = this._currentBitmapNumber + 1;
                    if (n >= this._bitmapList.size()) {
                        this._currentBitmapNumber = 0;
                    }
                    this.drawingOperations(canvas, this._bitmapList.get(this._currentBitmapNumber));
                    this._holder.unlockCanvasAndPost(canvas);
                    this._flagCanvasCleared = false;
                }
                _executor.schedule(new Runnable(){

                    @Override
                    public void run() {
                        RookieLoadingSurfaceView.this.startDrawingOperations();
                    }
                }, 50L, TimeUnit.MILLISECONDS);
                return;
            }
            this._flagCanvasCleared = true;
            Canvas canvas = this._holder.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                this._holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    protected void onVisibilityChanged(View view, int n) {
        super.onVisibilityChanged(view, n);
        _executor.execute(new Runnable(){

            @Override
            public void run() {
                if (RookieLoadingSurfaceView.this._flagCanvasCleared) {
                    RookieLoadingSurfaceView.this.startDrawingOperations();
                }
            }
        });
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int n, final int n2, final int n3) {
        _executor.execute(new Runnable(){

            @Override
            public void run() {
                RookieLoadingSurfaceView.this._rookieBgLeft = n2 / 2 - RookieLoadingSurfaceView.this._rookieBg.getWidth() / 2;
                RookieLoadingSurfaceView.this._rookieBgTop = n3 / 2 - RookieLoadingSurfaceView.this._rookieBg.getHeight() / 2;
            }
        });
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        _executor.execute(new Runnable(){

            @Override
            public void run() {
                RookieLoadingSurfaceView.this._flagSurfaceCreated = true;
                RookieLoadingSurfaceView.this.startDrawingOperations();
            }
        });
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

}
