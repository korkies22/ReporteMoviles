/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ValueAnimator
 */
package android.support.v7.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.v7.widget.FastScroller;

private class FastScroller.AnimatorListener
extends AnimatorListenerAdapter {
    private boolean mCanceled = false;

    private FastScroller.AnimatorListener() {
    }

    public void onAnimationCancel(Animator animator) {
        this.mCanceled = true;
    }

    public void onAnimationEnd(Animator animator) {
        if (this.mCanceled) {
            this.mCanceled = false;
            return;
        }
        if (((Float)FastScroller.this.mShowHideAnimator.getAnimatedValue()).floatValue() == 0.0f) {
            FastScroller.this.mAnimationState = 0;
            FastScroller.this.setState(0);
            return;
        }
        FastScroller.this.mAnimationState = 2;
        FastScroller.this.requestRedraw();
    }
}
