/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package android.support.design.widget;

import android.animation.ValueAnimator;

class CollapsingToolbarLayout
implements ValueAnimator.AnimatorUpdateListener {
    CollapsingToolbarLayout() {
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        CollapsingToolbarLayout.this.setScrimAlpha((Integer)valueAnimator.getAnimatedValue());
    }
}
