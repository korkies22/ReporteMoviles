/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.view.ViewPropertyAnimator
 */
package android.support.v7.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.ViewPropertyAnimator;

class ActionBarOverlayLayout
extends AnimatorListenerAdapter {
    ActionBarOverlayLayout() {
    }

    public void onAnimationCancel(Animator animator) {
        ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
        ActionBarOverlayLayout.this.mAnimatingForFling = false;
    }

    public void onAnimationEnd(Animator animator) {
        ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
        ActionBarOverlayLayout.this.mAnimatingForFling = false;
    }
}
