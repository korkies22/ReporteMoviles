/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.view.View
 */
package android.support.v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.View;

class ViewPropertyAnimatorCompat
extends AnimatorListenerAdapter {
    final /* synthetic */ ViewPropertyAnimatorListener val$listener;
    final /* synthetic */ View val$view;

    ViewPropertyAnimatorCompat(ViewPropertyAnimatorListener viewPropertyAnimatorListener, View view) {
        this.val$listener = viewPropertyAnimatorListener;
        this.val$view = view;
    }

    public void onAnimationCancel(Animator animator) {
        this.val$listener.onAnimationCancel(this.val$view);
    }

    public void onAnimationEnd(Animator animator) {
        this.val$listener.onAnimationEnd(this.val$view);
    }

    public void onAnimationStart(Animator animator) {
        this.val$listener.onAnimationStart(this.val$view);
    }
}
