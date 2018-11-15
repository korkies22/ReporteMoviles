/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package android.support.design.widget;

import android.animation.ValueAnimator;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.TabLayout;

class TabLayout.SlidingTabStrip
implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ int val$startLeft;
    final /* synthetic */ int val$startRight;
    final /* synthetic */ int val$targetLeft;
    final /* synthetic */ int val$targetRight;

    TabLayout.SlidingTabStrip(int n, int n2, int n3, int n4) {
        this.val$startLeft = n;
        this.val$targetLeft = n2;
        this.val$startRight = n3;
        this.val$targetRight = n4;
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float f = valueAnimator.getAnimatedFraction();
        SlidingTabStrip.this.setIndicatorPosition(AnimationUtils.lerp(this.val$startLeft, this.val$targetLeft, f), AnimationUtils.lerp(this.val$startRight, this.val$targetRight, f));
    }
}
