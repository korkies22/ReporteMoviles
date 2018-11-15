/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.util.AttributeSet
 *  android.widget.ImageView
 */
package uk.co.jasonfry.android.tools.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RotatableImageView
extends ImageView {
    private int mRotation = 0;
    private float mXPivot = 0.0f;
    private float mYPivot = 0.0f;

    public RotatableImageView(Context context) {
        super(context);
    }

    public RotatableImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public int getImageRotation() {
        return this.mRotation;
    }

    public float getXPivot() {
        return this.mXPivot;
    }

    public float getYPivot() {
        return this.mYPivot;
    }

    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.rotate((float)this.mRotation, (float)this.getWidth() * this.mXPivot, (float)this.getHeight() * this.mYPivot);
        super.onDraw(canvas);
        canvas.restore();
    }

    public void setRotation(int n) {
        this.setRotation(n, 0.5f, 0.5f);
    }

    public void setRotation(int n, float f, float f2) {
        this.mRotation = n;
        this.mXPivot = f;
        this.mYPivot = f2;
    }
}
