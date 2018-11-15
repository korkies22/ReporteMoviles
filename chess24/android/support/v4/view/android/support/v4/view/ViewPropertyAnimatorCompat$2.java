/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.view.View
 */
package android.support.v4.view;

import android.animation.ValueAnimator;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.view.View;

class ViewPropertyAnimatorCompat
implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ ViewPropertyAnimatorUpdateListener val$listener;
    final /* synthetic */ View val$view;

    ViewPropertyAnimatorCompat(ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener, View view) {
        this.val$listener = viewPropertyAnimatorUpdateListener;
        this.val$view = view;
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.val$listener.onAnimationUpdate(this.val$view);
    }
}
