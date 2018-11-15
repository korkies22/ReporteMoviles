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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewPropertyAnimator;
import java.util.ArrayList;

class DefaultItemAnimator
extends AnimatorListenerAdapter {
    final /* synthetic */ DefaultItemAnimator.ChangeInfo val$changeInfo;
    final /* synthetic */ View val$newView;
    final /* synthetic */ ViewPropertyAnimator val$newViewAnimation;

    DefaultItemAnimator(DefaultItemAnimator.ChangeInfo changeInfo, ViewPropertyAnimator viewPropertyAnimator, View view) {
        this.val$changeInfo = changeInfo;
        this.val$newViewAnimation = viewPropertyAnimator;
        this.val$newView = view;
    }

    public void onAnimationEnd(Animator animator) {
        this.val$newViewAnimation.setListener(null);
        this.val$newView.setAlpha(1.0f);
        this.val$newView.setTranslationX(0.0f);
        this.val$newView.setTranslationY(0.0f);
        DefaultItemAnimator.this.dispatchChangeFinished(this.val$changeInfo.newHolder, false);
        DefaultItemAnimator.this.mChangeAnimations.remove(this.val$changeInfo.newHolder);
        DefaultItemAnimator.this.dispatchFinishedWhenDone();
    }

    public void onAnimationStart(Animator animator) {
        DefaultItemAnimator.this.dispatchChangeStarting(this.val$changeInfo.newHolder, false);
    }
}
