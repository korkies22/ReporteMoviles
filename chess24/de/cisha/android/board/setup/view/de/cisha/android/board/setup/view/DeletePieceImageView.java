/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.util.AttributeSet
 *  android.widget.ImageView
 */
package de.cisha.android.board.setup.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DeletePieceImageView
extends ImageView {
    private Bitmap _bgBitmap;

    public DeletePieceImageView(Context context) {
        super(context);
        this.init();
    }

    public DeletePieceImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setWillNotDraw(false);
        this._bgBitmap = BitmapFactory.decodeResource((Resources)this.getResources(), (int)2131231704);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(this._bgBitmap, (float)(this.getWidth() - this._bgBitmap.getWidth()), (float)((this.getHeight() - this._bgBitmap.getHeight()) / 2), null);
        super.onDraw(canvas);
    }
}
