/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package android.support.design.widget;

import android.view.animation.Animation;

class BaseTransientBottomBar
implements Animation.AnimationListener {
    final /* synthetic */ int val$event;

    BaseTransientBottomBar(int n) {
        this.val$event = n;
    }

    public void onAnimationEnd(Animation animation) {
        BaseTransientBottomBar.this.onViewHidden(this.val$event);
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
}
