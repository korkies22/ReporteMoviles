/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.StateListDrawable
 */
package android.support.v7.widget;

import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.FastScroller;

private class FastScroller.AnimatorUpdater
implements ValueAnimator.AnimatorUpdateListener {
    private FastScroller.AnimatorUpdater() {
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        int n = (int)(255.0f * ((Float)valueAnimator.getAnimatedValue()).floatValue());
        FastScroller.this.mVerticalThumbDrawable.setAlpha(n);
        FastScroller.this.mVerticalTrackDrawable.setAlpha(n);
        FastScroller.this.requestRedraw();
    }
}
