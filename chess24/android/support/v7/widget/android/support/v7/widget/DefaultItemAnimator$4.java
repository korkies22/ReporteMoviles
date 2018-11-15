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
    final /* synthetic */ RecyclerView.ViewHolder val$holder;
    final /* synthetic */ View val$view;

    DefaultItemAnimator(RecyclerView.ViewHolder viewHolder, ViewPropertyAnimator viewPropertyAnimator, View view) {
        this.val$holder = viewHolder;
        this.val$animation = viewPropertyAnimator;
        this.val$view = view;
    }

    public void onAnimationEnd(Animator animator) {
        this.val$animation.setListener(null);
        this.val$view.setAlpha(1.0f);
        DefaultItemAnimator.this.dispatchRemoveFinished(this.val$holder);
        DefaultItemAnimator.this.mRemoveAnimations.remove(this.val$holder);
        DefaultItemAnimator.this.dispatchFinishedWhenDone();
    }

    public void onAnimationStart(Animator animator) {
        DefaultItemAnimator.this.dispatchRemoveStarting(this.val$holder);
    }
}
