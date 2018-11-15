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
    final /* synthetic */ ViewPropertyAnimator val$oldViewAnim;
    final /* synthetic */ View val$view;

    DefaultItemAnimator(DefaultItemAnimator.ChangeInfo changeInfo, ViewPropertyAnimator viewPropertyAnimator, View view) {
        this.val$changeInfo = changeInfo;
        this.val$oldViewAnim = viewPropertyAnimator;
        this.val$view = view;
    }

    public void onAnimationEnd(Animator animator) {
        this.val$oldViewAnim.setListener(null);
        this.val$view.setAlpha(1.0f);
        this.val$view.setTranslationX(0.0f);
        this.val$view.setTranslationY(0.0f);
        DefaultItemAnimator.this.dispatchChangeFinished(this.val$changeInfo.oldHolder, true);
        DefaultItemAnimator.this.mChangeAnimations.remove(this.val$changeInfo.oldHolder);
        DefaultItemAnimator.this.dispatchFinishedWhenDone();
    }

    public void onAnimationStart(Animator animator) {
        DefaultItemAnimator.this.dispatchChangeStarting(this.val$changeInfo.oldHolder, true);
    }
}
