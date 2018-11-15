/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

class Transition
extends AnimatorListenerAdapter {
    Transition() {
    }

    public void onAnimationEnd(Animator animator) {
        Transition.this.end();
        animator.removeListener((Animator.AnimatorListener)this);
    }
}
