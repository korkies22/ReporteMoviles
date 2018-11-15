/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.view.ViewPropertyAnimator
 */
package android.support.v7.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.ActionBarContainer;
import android.view.ViewPropertyAnimator;

class ActionBarOverlayLayout
implements Runnable {
    ActionBarOverlayLayout() {
    }

    @Override
    public void run() {
        ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
        ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = ActionBarOverlayLayout.this.mActionBarTop.animate().translationY((float)(- ActionBarOverlayLayout.this.mActionBarTop.getHeight())).setListener((Animator.AnimatorListener)ActionBarOverlayLayout.this.mTopAnimatorListener);
    }
}
