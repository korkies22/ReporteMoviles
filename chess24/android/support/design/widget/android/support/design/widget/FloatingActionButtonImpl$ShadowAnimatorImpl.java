/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.design.widget.FloatingActionButtonImpl;
import android.support.design.widget.ShadowDrawableWrapper;

private abstract class FloatingActionButtonImpl.ShadowAnimatorImpl
extends AnimatorListenerAdapter
implements ValueAnimator.AnimatorUpdateListener {
    private float mShadowSizeEnd;
    private float mShadowSizeStart;
    private boolean mValidValues;

    private FloatingActionButtonImpl.ShadowAnimatorImpl() {
    }

    protected abstract float getTargetShadowSize();

    public void onAnimationEnd(Animator animator) {
        FloatingActionButtonImpl.this.mShadowDrawable.setShadowSize(this.mShadowSizeEnd);
        this.mValidValues = false;
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        if (!this.mValidValues) {
            this.mShadowSizeStart = FloatingActionButtonImpl.this.mShadowDrawable.getShadowSize();
            this.mShadowSizeEnd = this.getTargetShadowSize();
            this.mValidValues = true;
        }
        FloatingActionButtonImpl.this.mShadowDrawable.setShadowSize(this.mShadowSizeStart + (this.mShadowSizeEnd - this.mShadowSizeStart) * valueAnimator.getAnimatedFraction());
    }
}
