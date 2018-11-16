// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.setup.view;

import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class DeletePieceImageView extends ImageView
{
    private Bitmap _bgBitmap;
    
    public DeletePieceImageView(final Context context) {
        super(context);
        this.init();
    }
    
    public DeletePieceImageView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    private void init() {
        this.setWillNotDraw(false);
        this._bgBitmap = BitmapFactory.decodeResource(this.getResources(), 2131231704);
    }
    
    protected void onDraw(final Canvas canvas) {
        canvas.drawBitmap(this._bgBitmap, (float)(this.getWidth() - this._bgBitmap.getWidth()), (float)((this.getHeight() - this._bgBitmap.getHeight()) / 2), (Paint)null);
        super.onDraw(canvas);
    }
}
