/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
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
    final /* synthetic */ View val$animatingView;
    final /* synthetic */ ViewGroup val$container;
    final /* synthetic */ Fragment val$fragment;

    FragmentManagerImpl(ViewGroup viewGroup, View view, Fragment fragment) {
        this.val$container = viewGroup;
        this.val$animatingView = view;
        this.val$fragment = fragment;
    }

    public void onAnimationEnd(Animator animator) {
        this.val$container.endViewTransition(this.val$animatingView);
        animator.removeListener((Animator.AnimatorListener)this);
        if (this.val$fragment.mView != null) {
            this.val$fragment.mView.setVisibility(8);
        }
    }
}
