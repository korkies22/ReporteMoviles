/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapShader
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$FillType
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package de.cisha.android.board.broadcast.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TournamentGameInfoFooterView
extends LinearLayout {
    private static Bitmap _bgBitmap;
    private TextView _lastMove;
    private Paint _paint;
    private Path _path;
    private Path _pathR;
    private TextView _time1;
    private TextView _time2;

    public TournamentGameInfoFooterView(Context context) {
        super(context);
        this.init();
    }

    public TournamentGameInfoFooterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setOrientation(0);
        TournamentGameInfoFooterView.inflate((Context)this.getContext(), (int)2131427385, (ViewGroup)this);
        this.setWillNotDraw(false);
        if (_bgBitmap == null) {
            _bgBitmap = BitmapFactory.decodeResource((Resources)this.getContext().getResources(), (int)2131230990);
        }
        this._time1 = (TextView)this.findViewById(2131296380);
        this._time2 = (TextView)this.findViewById(2131296381);
        this._lastMove = (TextView)this.findViewById(2131296372);
    }

    private void initPaintObjects(int n, int n2) {
        this._path = new Path();
        this._path.setFillType(Path.FillType.EVEN_ODD);
        this._path.moveTo(0.0f, 0.0f);
        int n3 = this.getChildAt(0).getWidth();
        n3 = this.getPaddingLeft() + n3 - 5;
        this._path.lineTo((float)n3, 0.0f);
        Path path = this._path;
        float f = n3 + 10;
        float f2 = n2;
        path.lineTo(f, f2);
        this._path.lineTo((float)false, f2);
        this._path.close();
        n2 = n - this.getPaddingRight() - this.getChildAt(this.getChildCount() - 1).getWidth() + 5;
        this._path.moveTo((float)n2, 0.0f);
        this._path.lineTo((float)(n2 - 10), f2);
        path = this._path;
        f = n;
        path.lineTo(f, f2);
        this._path.lineTo(f, 0.0f);
        this._path.close();
        this._paint = new Paint(1);
        this._paint.setStyle(Paint.Style.FILL);
        path = new BitmapShader(_bgBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        this._paint.setShader((Shader)path);
        this.invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this._path, this._paint);
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (bl) {
            this.initPaintObjects(n3 - n, n4 - n2);
        }
    }

    public void setTextLastMove(CharSequence charSequence) {
        this._lastMove.setText(charSequence);
    }

    public void setTextTime1(CharSequence charSequence) {
        this._time1.setText(charSequence);
    }

    public void setTextTime2(CharSequence charSequence) {
        this._time2.setText(charSequence);
    }
}
