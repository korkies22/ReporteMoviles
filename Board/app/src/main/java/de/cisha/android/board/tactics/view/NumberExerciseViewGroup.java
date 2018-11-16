// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics.view;

import android.graphics.Canvas;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Paint;
import android.graphics.Bitmap;
import android.widget.LinearLayout;

public class NumberExerciseViewGroup extends LinearLayout
{
    private Bitmap _bgBitmap;
    private Paint _bgPaint;
    private Rect _bgRect;
    
    public NumberExerciseViewGroup(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setOrientation(0);
        this._bgBitmap = BitmapFactory.decodeResource(this.getResources(), 2131231814);
        this.setWillNotDraw(false);
        (this._bgPaint = new Paint()).setColor(-394759);
    }
    
    protected void onDraw(final Canvas canvas) {
        canvas.drawRect(this._bgRect, this._bgPaint);
        int paddingLeft = this.getPaddingLeft();
        for (int i = 0; i < this.getChildCount(); ++i) {
            canvas.drawBitmap(this._bgBitmap, (float)paddingLeft, (float)((this.getHeight() - this._bgBitmap.getHeight()) / 2), (Paint)null);
            paddingLeft += this.getChildAt(i).getWidth();
        }
        super.onDraw(canvas);
    }
    
    protected void onSizeChanged(final int n, final int n2, final int n3, final int n4) {
        this._bgRect = new Rect(this.getPaddingLeft() + this._bgBitmap.getWidth(), 0, this.getWidth(), this.getHeight());
        super.onSizeChanged(n, n2, n3, n4);
    }
}
