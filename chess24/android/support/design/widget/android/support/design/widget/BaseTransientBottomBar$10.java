/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 */
package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.design.widget.BaseTransientBottomBar;

class BaseTransientBottomBar
extends AnimatorListenerAdapter {
    final /* synthetic */ int val$event;

    BaseTransientBottomBar(int n) {
        this.val$event = n;
    }

    public void onAnimationEnd(Animator animator) {
        BaseTransientBottomBar.this.onViewHidden(this.val$event);
    }

    public void onAnimationStart(Animator animator) {
        BaseTransientBottomBar.this.mContentViewCallback.animateContentOut(0, 180);
    }
}
