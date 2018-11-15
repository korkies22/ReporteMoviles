/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v4.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

class FragmentManagerImpl
extends AnimatorListenerAdapter {
    final /* synthetic */ ViewGroup val$container;
    final /* synthetic */ Fragment val$fragment;
    final /* synthetic */ View val$viewToAnimate;

    FragmentManagerImpl(ViewGroup viewGroup, View view, Fragment fragment) {
        this.val$container = viewGroup;
        this.val$viewToAnimate = view;
        this.val$fragment = fragment;
    }

    public void onAnimationEnd(Animator animator) {
        this.val$container.endViewTransition(this.val$viewToAnimate);
        animator = this.val$fragment.getAnimator();
        this.val$fragment.setAnimator(null);
        if (animator != null && this.val$container.indexOfChild(this.val$viewToAnimate) < 0) {
            FragmentManagerImpl.this.moveToState(this.val$fragment, this.val$fragment.getStateAfterAnimating(), 0, 0, false);
        }
    }
}
