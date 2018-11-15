/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.NonNull;
import android.support.transition.AnimatorUtilsApi14;
import android.support.transition.Transition;
import android.support.transition.ViewGroupUtils;
import android.support.transition.ViewUtils;
import android.support.transition.Visibility;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

private static class Visibility.DisappearListener
extends AnimatorListenerAdapter
implements Transition.TransitionListener,
AnimatorUtilsApi14.AnimatorPauseListenerCompat {
    boolean mCanceled = false;
    private final int mFinalVisibility;
    private boolean mLayoutSuppressed;
    private final ViewGroup mParent;
    private final boolean mSuppressLayout;
    private final View mView;

    Visibility.DisappearListener(View view, int n, boolean bl) {
        this.mView = view;
        this.mFinalVisibility = n;
        this.mParent = (ViewGroup)view.getParent();
        this.mSuppressLayout = bl;
        this.suppressLayout(true);
    }

    private void hideViewWhenNotCanceled() {
        if (!this.mCanceled) {
            ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
            if (this.mParent != null) {
                this.mParent.invalidate();
            }
        }
        this.suppressLayout(false);
    }

    private void suppressLayout(boolean bl) {
        if (this.mSuppressLayout && this.mLayoutSuppressed != bl && this.mParent != null) {
            this.mLayoutSuppressed = bl;
            ViewGroupUtils.suppressLayout(this.mParent, bl);
        }
    }

    public void onAnimationCancel(Animator animator) {
        this.mCanceled = true;
    }

    public void onAnimationEnd(Animator animator) {
        this.hideViewWhenNotCanceled();
    }

    @Override
    public void onAnimationPause(Animator animator) {
        if (!this.mCanceled) {
            ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
        }
    }

    public void onAnimationRepeat(Animator animator) {
    }

    @Override
    public void onAnimationResume(Animator animator) {
        if (!this.mCanceled) {
            ViewUtils.setTransitionVisibility(this.mView, 0);
        }
    }

    public void onAnimationStart(Animator animator) {
    }

    @Override
    public void onTransitionCancel(@NonNull Transition transition) {
    }

    @Override
    public void onTransitionEnd(@NonNull Transition transition) {
        this.hideViewWhenNotCanceled();
        transition.removeListener(this);
    }

    @Override
    public void onTransitionPause(@NonNull Transition transition) {
        this.suppressLayout(false);
    }

    @Override
    public void onTransitionResume(@NonNull Transition transition) {
        this.suppressLayout(true);
    }

    @Override
    public void onTransitionStart(@NonNull Transition transition) {
    }
}
