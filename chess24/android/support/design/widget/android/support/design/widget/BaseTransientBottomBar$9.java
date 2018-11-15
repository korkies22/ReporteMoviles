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
    BaseTransientBottomBar() {
    }

    public void onAnimationEnd(Animation animation) {
        BaseTransientBottomBar.this.onViewShown();
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
}
