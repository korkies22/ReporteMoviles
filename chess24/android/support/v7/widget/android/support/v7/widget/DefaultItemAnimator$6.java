/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.view.View
 *  android.view.ViewPropertyAnimator
 */
package android.support.v7.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewPropertyAnimator;
import java.util.ArrayList;

class DefaultItemAnimator
extends AnimatorListenerAdapter {
    final /* synthetic */ ViewPropertyAnimator val$animation;
    final /* synthetic */ int val$deltaX;
    final /* synthetic */ int val$deltaY;
    final /* synthetic */ RecyclerView.ViewHolder val$holder;
    final /* synthetic */ View val$view;

    DefaultItemAnimator(RecyclerView.ViewHolder viewHolder, int n, View view, int n2, ViewPropertyAnimator viewPropertyAnimator) {
        this.val$holder = viewHolder;
        this.val$deltaX = n;
        this.val$view = view;
        this.val$deltaY = n2;
        this.val$animation = viewPropertyAnimator;
    }

    public void onAnimationCancel(Animator animator) {
        if (this.val$deltaX != 0) {
            this.val$view.setTranslationX(0.0f);
        }
        if (this.val$deltaY != 0) {
            this.val$view.setTranslationY(0.0f);
        }
    }

    public void onAnimationEnd(Animator animator) {
        this.val$animation.setListener(null);
        DefaultItemAnimator.this.dispatchMoveFinished(this.val$holder);
        DefaultItemAnimator.this.mMoveAnimations.remove(this.val$holder);
        DefaultItemAnimator.this.dispatchFinishedWhenDone();
    }

    public void onAnimationStart(Animator animator) {
        DefaultItemAnimator.this.dispatchMoveStarting(this.val$holder);
    }
}
