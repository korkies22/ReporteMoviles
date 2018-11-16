// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view;

import android.os.SystemClock;
import android.graphics.Canvas;
import java.io.IOException;
import android.util.AttributeSet;
import android.content.Context;
import java.io.InputStream;
import android.graphics.Movie;
import android.view.View;

public class GifView extends View
{
    private Movie _movie;
    private long _movieStart;
    private InputStream _stream;
    
    public GifView(final Context context) {
        super(context);
        this.init();
    }
    
    public GifView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    public GifView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.init();
    }
    
    private void init() {
    Label_0016_Outer:
        while (true) {
            while (true) {
                try {
                    InputStream open = this.getContext().getAssets().open("webloader.gif");
                    while (true) {
                        this._stream = open;
                        this._movie = Movie.decodeStream(this._stream);
                        return;
                        open = null;
                        continue Label_0016_Outer;
                    }
                }
                catch (IOException ex) {}
                continue;
            }
        }
    }
    
    protected void onDraw(final Canvas canvas) {
        canvas.drawColor(0);
        super.onDraw(canvas);
        final long uptimeMillis = SystemClock.uptimeMillis();
        if (this._movieStart == 0L) {
            this._movieStart = uptimeMillis;
        }
        this._movie.setTime((int)((uptimeMillis - this._movieStart) % this._movie.duration()));
        this._movie.draw(canvas, 0.0f, 0.0f);
        this.invalidate();
    }
    
    protected void onMeasure(final int n, final int n2) {
        super.onMeasure(n, n2);
        this.setMeasuredDimension(122, 122);
    }
}
