/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.graphics.Rect
 *  android.view.View
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Rect;
import android.support.transition.ViewUtils;
import android.support.v4.view.ViewCompat;
import android.view.View;

class ChangeBounds
extends AnimatorListenerAdapter {
    private boolean mIsCanceled;
    final /* synthetic */ int val$endBottom;
    final /* synthetic */ int val$endLeft;
    final /* synthetic */ int val$endRight;
    final /* synthetic */ int val$endTop;
    final /* synthetic */ Rect val$finalClip;
    final /* synthetic */ View val$view;

    ChangeBounds(View view, Rect rect, int n, int n2, int n3, int n4) {
        this.val$view = view;
        this.val$finalClip = rect;
        this.val$endLeft = n;
        this.val$endTop = n2;
        this.val$endRight = n3;
        this.val$endBottom = n4;
    }

    public void onAnimationCancel(Animator animator) {
        this.mIsCanceled = true;
    }

    public void onAnimationEnd(Animator animator) {
        if (!this.mIsCanceled) {
            ViewCompat.setClipBounds(this.val$view, this.val$finalClip);
            ViewUtils.setLeftTopRightBottom(this.val$view, this.val$endLeft, this.val$endTop, this.val$endRight, this.val$endBottom);
        }
    }
}
