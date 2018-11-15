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
import android.support.v7.widget.ScrollingTabContainerView;
import android.view.ViewPropertyAnimator;

protected class ScrollingTabContainerView.VisibilityAnimListener
extends AnimatorListenerAdapter {
    private boolean mCanceled = false;
    private int mFinalVisibility;

    protected ScrollingTabContainerView.VisibilityAnimListener() {
    }

    public void onAnimationCancel(Animator animator) {
        this.mCanceled = true;
    }

    public void onAnimationEnd(Animator animator) {
        if (this.mCanceled) {
            return;
        }
        ScrollingTabContainerView.this.mVisibilityAnim = null;
        ScrollingTabContainerView.this.setVisibility(this.mFinalVisibility);
    }

    public void onAnimationStart(Animator animator) {
        ScrollingTabContainerView.this.setVisibility(0);
        this.mCanceled = false;
    }

    public ScrollingTabContainerView.VisibilityAnimListener withFinalVisibility(ViewPropertyAnimator viewPropertyAnimator, int n) {
        this.mFinalVisibility = n;
        ScrollingTabContainerView.this.mVisibilityAnim = viewPropertyAnimator;
        return this;
    }
}
