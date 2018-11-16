// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.view;

import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.BitmapShader;
import android.graphics.Shader.TileMode;
import android.graphics.Paint.Style;
import android.graphics.Path.FillType;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Path;
import android.graphics.Paint;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.widget.LinearLayout;

public class TournamentGameInfoFooterView extends LinearLayout
{
    private static Bitmap _bgBitmap;
    private TextView _lastMove;
    private Paint _paint;
    private Path _path;
    private Path _pathR;
    private TextView _time1;
    private TextView _time2;
    
    public TournamentGameInfoFooterView(final Context context) {
        super(context);
        this.init();
    }
    
    public TournamentGameInfoFooterView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setOrientation(0);
        inflate(this.getContext(), 2131427385, (ViewGroup)this);
        this.setWillNotDraw(false);
        if (TournamentGameInfoFooterView._bgBitmap == null) {
            TournamentGameInfoFooterView._bgBitmap = BitmapFactory.decodeResource(this.getContext().getResources(), 2131230990);
        }
        this._time1 = (TextView)this.findViewById(2131296380);
        this._time2 = (TextView)this.findViewById(2131296381);
        this._lastMove = (TextView)this.findViewById(2131296372);
    }
    
    private void initPaintObjects(final int n, int n2) {
        (this._path = new Path()).setFillType(Path.FillType.EVEN_ODD);
        this._path.moveTo(0.0f, 0.0f);
        final int n3 = this.getPaddingLeft() + this.getChildAt(0).getWidth() - 5;
        this._path.lineTo((float)n3, 0.0f);
        final Path path = this._path;
        final float n4 = n3 + 10;
        final float n5 = n2;
        path.lineTo(n4, n5);
        this._path.lineTo((float)0, n5);
        this._path.close();
        n2 = n - this.getPaddingRight() - this.getChildAt(this.getChildCount() - 1).getWidth() + 5;
        this._path.moveTo((float)n2, 0.0f);
        this._path.lineTo((float)(n2 - 10), n5);
        final Path path2 = this._path;
        final float n6 = n;
        path2.lineTo(n6, n5);
        this._path.lineTo(n6, 0.0f);
        this._path.close();
        (this._paint = new Paint(1)).setStyle(Paint.Style.FILL);
        this._paint.setShader((Shader)new BitmapShader(TournamentGameInfoFooterView._bgBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP));
        this.invalidate();
    }
    
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this._path, this._paint);
    }
    
    protected void onLayout(final boolean b, final int n, final int n2, final int n3, final int n4) {
        super.onLayout(b, n, n2, n3, n4);
        if (b) {
            this.initPaintObjects(n3 - n, n4 - n2);
        }
    }
    
    public void setTextLastMove(final CharSequence text) {
        this._lastMove.setText(text);
    }
    
    public void setTextTime1(final CharSequence text) {
        this._time1.setText(text);
    }
    
    public void setTextTime2(final CharSequence text) {
        this._time2.setText(text);
    }
}
