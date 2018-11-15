/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 *  android.view.animation.Animation
 *  android.view.animation.Transformation
 *  android.widget.TextView
 */
package de.cisha.android.animation;

import android.graphics.Color;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

public class RGBTransformation
extends Animation {
    private int _b1;
    private int _b2;
    private int _g1;
    private int _g2;
    private int _r1;
    private int _r2;
    private TextView _view;

    public RGBTransformation(TextView textView, int n, int n2, int n3, int n4, int n5, int n6) {
        this._view = textView;
        this._r1 = n;
        this._r2 = n2;
        this._g1 = n3;
        this._g2 = n4;
        this._b1 = n5;
        this._b2 = n6;
    }

    private int getInterpolatedValue(int n, int n2, float f) {
        return n + (int)(f * (float)(n2 - n));
    }

    protected void applyTransformation(float f, Transformation transformation) {
        super.applyTransformation(f, transformation);
        final int n = Color.rgb((int)this.getInterpolatedValue(this._r1, this._r2, f), (int)this.getInterpolatedValue(this._g1, this._g2, f), (int)this.getInterpolatedValue(this._b1, this._b2, f));
        this._view.post(new Runnable(){

            @Override
            public void run() {
                RGBTransformation.this._view.setTextColor(n);
            }
        });
    }

}
