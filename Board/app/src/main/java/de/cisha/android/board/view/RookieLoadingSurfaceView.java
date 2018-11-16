// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import android.view.View;
import java.util.concurrent.TimeUnit;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.Canvas;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.content.Context;
import java.util.concurrent.Executors;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.graphics.Bitmap;
import java.util.LinkedList;
import java.util.concurrent.ScheduledExecutorService;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class RookieLoadingSurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
    private static ScheduledExecutorService _executor;
    private LinkedList<Bitmap> _bitmapList;
    private int _currentBitmapNumber;
    private boolean _flagCanvasCleared;
    private boolean _flagSurfaceCreated;
    private SurfaceHolder _holder;
    private float _innerBitmapOffsetTop;
    private Paint _paint;
    private Bitmap _rookieBg;
    private int _rookieBgLeft;
    private int _rookieBgTop;
    
    static {
        RookieLoadingSurfaceView._executor = Executors.newSingleThreadScheduledExecutor();
    }
    
    public RookieLoadingSurfaceView(final Context context) {
        super(context);
        this._currentBitmapNumber = 0;
        this.init();
    }
    
    public RookieLoadingSurfaceView(final Context context, final AttributeSet set) {
        super(context, set);
        this._currentBitmapNumber = 0;
        this.init();
    }
    
    private void addBitmapToList(final int n) {
        this._bitmapList.add(BitmapFactory.decodeResource(this.getResources(), n));
    }
    
    private void drawingOperations(final Canvas canvas, final Bitmap bitmap) {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.drawBitmap(this._rookieBg, (float)this._rookieBgLeft, (float)this._rookieBgTop, (Paint)null);
        canvas.drawBitmap(bitmap, (float)(this.getWidth() / 2 - bitmap.getWidth() / 2), this._innerBitmapOffsetTop + this.getHeight() / 2 - bitmap.getHeight() / 2, (Paint)null);
    }
    
    private void init() {
        (this._holder = this.getHolder()).addCallback((SurfaceHolder.Callback)this);
        this._holder.setFormat(-3);
        (this._paint = new Paint()).setStyle(Paint.Style.STROKE);
        this._paint.setStrokeWidth(5.0f);
        this.setZOrderOnTop(true);
        this._paint.setColor(Color.rgb(50, 255, 50));
        this._innerBitmapOffsetTop = 12.0f * this.getContext().getResources().getDisplayMetrics().density;
        this._flagCanvasCleared = true;
        RookieLoadingSurfaceView._executor.execute(new Runnable() {
            @Override
            public void run() {
                RookieLoadingSurfaceView.this._bitmapList = (LinkedList<Bitmap>)new LinkedList();
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
                RookieLoadingSurfaceView.this._rookieBg = BitmapFactory.decodeResource(RookieLoadingSurfaceView.this.getResources(), 2131231433);
            }
        });
    }
    
    private void startDrawingOperations() {
        if (this._flagSurfaceCreated) {
            if (this.isShown()) {
                final Canvas lockCanvas = this._holder.lockCanvas();
                if (lockCanvas != null) {
                    if (++this._currentBitmapNumber >= this._bitmapList.size()) {
                        this._currentBitmapNumber = 0;
                    }
                    this.drawingOperations(lockCanvas, this._bitmapList.get(this._currentBitmapNumber));
                    this._holder.unlockCanvasAndPost(lockCanvas);
                    this._flagCanvasCleared = false;
                }
                RookieLoadingSurfaceView._executor.schedule(new Runnable() {
                    @Override
                    public void run() {
                        RookieLoadingSurfaceView.this.startDrawingOperations();
                    }
                }, 50L, TimeUnit.MILLISECONDS);
                return;
            }
            this._flagCanvasCleared = true;
            final Canvas lockCanvas2 = this._holder.lockCanvas();
            if (lockCanvas2 != null) {
                lockCanvas2.drawColor(0, PorterDuff.Mode.CLEAR);
                this._holder.unlockCanvasAndPost(lockCanvas2);
            }
        }
    }
    
    protected void onVisibilityChanged(final View view, final int n) {
        super.onVisibilityChanged(view, n);
        RookieLoadingSurfaceView._executor.execute(new Runnable() {
            @Override
            public void run() {
                if (RookieLoadingSurfaceView.this._flagCanvasCleared) {
                    RookieLoadingSurfaceView.this.startDrawingOperations();
                }
            }
        });
    }
    
    public void surfaceChanged(final SurfaceHolder surfaceHolder, final int n, final int n2, final int n3) {
        RookieLoadingSurfaceView._executor.execute(new Runnable() {
            @Override
            public void run() {
                RookieLoadingSurfaceView.this._rookieBgLeft = n2 / 2 - RookieLoadingSurfaceView.this._rookieBg.getWidth() / 2;
                RookieLoadingSurfaceView.this._rookieBgTop = n3 / 2 - RookieLoadingSurfaceView.this._rookieBg.getHeight() / 2;
            }
        });
    }
    
    public void surfaceCreated(final SurfaceHolder surfaceHolder) {
        RookieLoadingSurfaceView._executor.execute(new Runnable() {
            @Override
            public void run() {
                RookieLoadingSurfaceView.this._flagSurfaceCreated = true;
                RookieLoadingSurfaceView.this.startDrawingOperations();
            }
        });
    }
    
    public void surfaceDestroyed(final SurfaceHolder surfaceHolder) {
    }
}
