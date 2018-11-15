/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package android.support.design.widget;

import android.animation.ValueAnimator;

class TabLayout
implements ValueAnimator.AnimatorUpdateListener {
    TabLayout() {
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        TabLayout.this.scrollTo(((Integer)valueAnimator.getAnimatedValue()).intValue(), 0);
    }
}
