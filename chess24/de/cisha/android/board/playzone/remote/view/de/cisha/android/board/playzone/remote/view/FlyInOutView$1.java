/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package de.cisha.android.board.playzone.remote.view;

import android.view.animation.Animation;

class FlyInOutView
implements Animation.AnimationListener {
    FlyInOutView() {
    }

    public void onAnimationEnd(Animation animation) {
        FlyInOutView.this.setVisibility(8);
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
}
