/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 *  android.view.animation.Interpolator
 *  android.view.animation.LinearInterpolator
 *  android.view.animation.Transformation
 */
package de.cisha.android.board.tactics.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class TranslateViewGroup
extends ViewGroup {
    private WidthAnimation _animation;
    private int _xOffset = 0;

    public TranslateViewGroup(Context context) {
        super(context);
    }

    public TranslateViewGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @SuppressLint(value={"WrongCall"})
    private void forceOnLayout() {
        this.onLayout(false, this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
    }

    public void adjustAnimationTimeBy(int n) {
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
        if (this._animation != null) {
            return true;
        }
        return false;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        for (n = 0; n < this.getChildCount(); ++n) {
            n3 = this.getChildAt(n).getMeasuredWidth();
            this.getChildAt(n).layout(this._xOffset + 0, 0, n3 + this._xOffset, n4 - n2);
        }
    }

    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        this.measureChildren(n, n2);
        int n5 = n3 = 0;
        for (n4 = 0; n4 < this.getChildCount(); ++n4) {
            n3 = Math.max(n3, this.getChildAt(n4).getMeasuredWidth());
            n5 = Math.max(n5, this.getChildAt(n4).getMeasuredHeight());
        }
        n4 = this.getPaddingLeft();
        int n6 = this.getPaddingRight();
        int n7 = this.getPaddingTop();
        int n8 = this.getPaddingBottom();
        this.setMeasuredDimension(TranslateViewGroup.resolveSize((int)(n3 + (n4 + n6)), (int)n), TranslateViewGroup.resolveSize((int)(n5 + (n7 + n8)), (int)n2));
    }

    public void translateViewTo(int n) {
        this._xOffset = n;
    }

    public void translateViewTo(int n, int n2) {
        this.clearAnimation();
        if (n2 > 0) {
            this._animation = new WidthAnimation(n);
            this._animation.setInterpolator((Interpolator)new LinearInterpolator());
            this._animation.setDuration((long)n2);
            this._animation.setFillAfter(true);
            this.startAnimation((Animation)this._animation);
            return;
        }
        this.translateViewTo(n);
    }

    private class WidthAnimation
    extends Animation {
        private boolean _canceled = false;
        private int _from;
        private float _interpolatedAdjustOffset = 0.0f;
        private int _offset;

        public WidthAnimation(int n) {
            this._from = TranslateViewGroup.this._xOffset;
            this._offset = this._from - n;
        }

        protected void applyTransformation(float f, Transformation transformation) {
            super.applyTransformation(f, transformation);
            if (!this._canceled) {
                float f2;
                f = f2 = f + this._interpolatedAdjustOffset;
                if (f2 >= 1.0f) {
                    TranslateViewGroup.this.clearAnimation();
                    f = 1.0f;
                }
                TranslateViewGroup.this._xOffset = (int)((float)this._from - f * (float)this._offset);
                TranslateViewGroup.this.forceOnLayout();
            }
        }

        public void cancel() {
            this._canceled = true;
            super.cancel();
        }

        public void reduceAnimationTime(int n) {
            float f = (float)n / (float)this.getDuration();
            this._interpolatedAdjustOffset += f;
        }
    }

}
