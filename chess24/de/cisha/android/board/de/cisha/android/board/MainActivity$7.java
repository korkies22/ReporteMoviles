/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package de.cisha.android.board;

import android.view.View;
import android.view.animation.Animation;

class MainActivity
implements Animation.AnimationListener {
    MainActivity() {
    }

    public void onAnimationEnd(Animation animation) {
        MainActivity.this._loadingView.clearAnimation();
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
}
