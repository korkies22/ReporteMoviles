/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ValueAnimator
 */
package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;

class StateListAnimator
extends AnimatorListenerAdapter {
    StateListAnimator() {
    }

    public void onAnimationEnd(Animator animator) {
        if (StateListAnimator.this.mRunningAnimator == animator) {
            StateListAnimator.this.mRunningAnimator = null;
        }
    }
}
