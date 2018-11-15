/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package android.support.v4.widget;

import android.view.animation.Animation;

class SwipeRefreshLayout
implements Animation.AnimationListener {
    SwipeRefreshLayout() {
    }

    public void onAnimationEnd(Animation animation) {
        if (!SwipeRefreshLayout.this.mScale) {
            SwipeRefreshLayout.this.startScaleDownAnimation(null);
        }
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
}
