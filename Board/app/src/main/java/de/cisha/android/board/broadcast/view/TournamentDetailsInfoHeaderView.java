// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.view;

import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.BitmapShader;
import android.graphics.Shader.TileMode;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.widget.LinearLayout;

public class TournamentDetailsInfoHeaderView extends LinearLayout
{
    private int _bgHeight;
    private Rect _bgRect;
    private Bitmap _bitmapBg;
    private Paint _paint;
    
    public TournamentDetailsInfoHeaderView(final Context context) {
        super(context);
        this.init();
    }
    
    public TournamentDetailsInfoHeaderView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setWillNotDraw(false);
        this._bitmapBg = BitmapFactory.decodeResource(this.getResources(), 2131231862);
        this._bgHeight = this._bitmapBg.getHeight();
        (this._paint = new Paint()).setShader((Shader)new BitmapShader(this._bitmapBg, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR));
        this.setMinimumHeight(this._bgHeight);
    }
    
    protected void onDraw(final Canvas canvas) {
        canvas.drawRect(this._bgRect, this._paint);
    }
    
    protected void onSizeChanged(final int n, final int n2, final int n3, final int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this._bgRect = new Rect(0, 0, n, n2);
    }
}
