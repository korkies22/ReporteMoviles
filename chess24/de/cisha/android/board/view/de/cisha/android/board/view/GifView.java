/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.graphics.Canvas
 *  android.graphics.Movie
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.view.View
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import java.io.IOException;
import java.io.InputStream;

public class GifView
extends View {
    private Movie _movie;
    private long _movieStart;
    private InputStream _stream;

    public GifView(Context context) {
        super(context);
        this.init();
    }

    public GifView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    public GifView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void init() {
        InputStream inputStream;
        block2 : {
            try {
                inputStream = this.getContext().getAssets().open("webloader.gif");
                break block2;
            }
            catch (IOException iOException) {}
            inputStream = null;
        }
        this._stream = inputStream;
        this._movie = Movie.decodeStream((InputStream)this._stream);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0);
        super.onDraw(canvas);
        long l = SystemClock.uptimeMillis();
        if (this._movieStart == 0L) {
            this._movieStart = l;
        }
        int n = (int)((l - this._movieStart) % (long)this._movie.duration());
        this._movie.setTime(n);
        this._movie.draw(canvas, 0.0f, 0.0f);
        this.invalidate();
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        this.setMeasuredDimension(122, 122);
    }
}
