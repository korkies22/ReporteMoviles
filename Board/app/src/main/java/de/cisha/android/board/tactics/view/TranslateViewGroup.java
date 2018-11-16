// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics.view;

import android.view.animation.Transformation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.annotation.SuppressLint;
import android.util.AttributeSet;
import android.content.Context;
import android.view.ViewGroup;

public class TranslateViewGroup extends ViewGroup
{
    private WidthAnimation _animation;
    private int _xOffset;
    
    public TranslateViewGroup(final Context context) {
        super(context);
        this._xOffset = 0;
    }
    
    public TranslateViewGroup(final Context context, final AttributeSet set) {
        super(context, set);
        this._xOffset = 0;
    }
    
    @SuppressLint({ "WrongCall" })
    private void forceOnLayout() {
        this.onLayout(false, this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
    }
    
    public void adjustAnimationTimeBy(final int n) {
        if (this._animation != null) {
            this._animation.reduceAnimationTime(n);
        }
    }
    
    public void clearAnimation() {
        if (this._animation != null) {
            this._animation.cancel();
        }
        super.clearAnimation();
        this._animation = null;
    }
    
    public boolean isAnimating() {
        return this._animation != null;
    }
    
    protected void onLayout(final boolean b, int i, final int n, int measuredWidth, final int n2) {
        for (i = 0; i < this.getChildCount(); ++i) {
            measuredWidth = this.getChildAt(i).getMeasuredWidth();
            this.getChildAt(i).layout(this._xOffset + 0, 0, measuredWidth + this._xOffset, n2 - n);
        }
    }
    
    protected void onMeasure(final int n, final int n2) {
        this.measureChildren(n, n2);
        int i = 0;
        int max2;
        int max = max2 = 0;
        while (i < this.getChildCount()) {
            max = Math.max(max, this.getChildAt(i).getMeasuredWidth());
            max2 = Math.max(max2, this.getChildAt(i).getMeasuredHeight());
            ++i;
        }
        this.setMeasuredDimension(resolveSize(max + (this.getPaddingLeft() + this.getPaddingRight()), n), resolveSize(max2 + (this.getPaddingTop() + this.getPaddingBottom()), n2));
    }
    
    public void translateViewTo(final int xOffset) {
        this._xOffset = xOffset;
    }
    
    public void translateViewTo(final int n, final int n2) {
        this.clearAnimation();
        if (n2 > 0) {
            (this._animation = new WidthAnimation(n)).setInterpolator((Interpolator)new LinearInterpolator());
            this._animation.setDuration((long)n2);
            this._animation.setFillAfter(true);
            this.startAnimation((Animation)this._animation);
            return;
        }
        this.translateViewTo(n);
    }
    
    private class WidthAnimation extends Animation
    {
        private boolean _canceled;
        private int _from;
        private float _interpolatedAdjustOffset;
        private int _offset;
        
        public WidthAnimation(final int n) {
            this._interpolatedAdjustOffset = 0.0f;
            this._canceled = false;
            this._from = TranslateViewGroup.this._xOffset;
            this._offset = this._from - n;
        }
        
        protected void applyTransformation(float n, final Transformation transformation) {
            super.applyTransformation(n, transformation);
            if (!this._canceled) {
                if ((n += this._interpolatedAdjustOffset) >= 1.0f) {
                    TranslateViewGroup.this.clearAnimation();
                    n = 1.0f;
                }
                TranslateViewGroup.this._xOffset = (int)(this._from - n * this._offset);
                TranslateViewGroup.this.forceOnLayout();
            }
        }
        
        public void cancel() {
            this._canceled = true;
            super.cancel();
        }
        
        public void reduceAnimationTime(final int n) {
            this._interpolatedAdjustOffset += n / this.getDuration();
        }
    }
}
