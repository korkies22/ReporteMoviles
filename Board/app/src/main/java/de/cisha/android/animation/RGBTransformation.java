// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.animation;

import android.graphics.Color;
import android.view.animation.Transformation;
import android.widget.TextView;
import android.view.animation.Animation;

public class RGBTransformation extends Animation
{
    private int _b1;
    private int _b2;
    private int _g1;
    private int _g2;
    private int _r1;
    private int _r2;
    private TextView _view;
    
    public RGBTransformation(final TextView view, final int r1, final int r2, final int g1, final int g2, final int b1, final int b2) {
        this._view = view;
        this._r1 = r1;
        this._r2 = r2;
        this._g1 = g1;
        this._g2 = g2;
        this._b1 = b1;
        this._b2 = b2;
    }
    
    private int getInterpolatedValue(final int n, final int n2, final float n3) {
        return n + (int)(n3 * (n2 - n));
    }
    
    protected void applyTransformation(final float n, final Transformation transformation) {
        super.applyTransformation(n, transformation);
        this._view.post((Runnable)new Runnable() {
            final /* synthetic */ int val.rgb = Color.rgb(RGBTransformation.this.getInterpolatedValue(RGBTransformation.this._r1, RGBTransformation.this._r2, n), RGBTransformation.this.getInterpolatedValue(RGBTransformation.this._g1, RGBTransformation.this._g2, n), RGBTransformation.this.getInterpolatedValue(RGBTransformation.this._b1, RGBTransformation.this._b2, n));
            
            @Override
            public void run() {
                RGBTransformation.this._view.setTextColor(this.val.rgb);
            }
        });
    }
}
