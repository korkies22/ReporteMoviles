/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package android.support.design.widget;

import android.animation.ValueAnimator;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;

class AppBarLayout.Behavior
implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ AppBarLayout val$child;
    final /* synthetic */ CoordinatorLayout val$coordinatorLayout;

    AppBarLayout.Behavior(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
        this.val$coordinatorLayout = coordinatorLayout;
        this.val$child = appBarLayout;
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        Behavior.this.setHeaderTopBottomOffset(this.val$coordinatorLayout, this.val$child, (Integer)valueAnimator.getAnimatedValue());
    }
}
