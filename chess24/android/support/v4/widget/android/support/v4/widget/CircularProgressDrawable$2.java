/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 */
package android.support.v4.widget;

import android.animation.Animator;
import android.support.v4.widget.CircularProgressDrawable;

class CircularProgressDrawable
implements Animator.AnimatorListener {
    final /* synthetic */ CircularProgressDrawable.Ring val$ring;

    CircularProgressDrawable(CircularProgressDrawable.Ring ring) {
        this.val$ring = ring;
    }

    public void onAnimationCancel(Animator animator) {
    }

    public void onAnimationEnd(Animator animator) {
    }

    public void onAnimationRepeat(Animator animator) {
        CircularProgressDrawable.this.applyTransformation(1.0f, this.val$ring, true);
        this.val$ring.storeOriginals();
        this.val$ring.goToNextColor();
        if (CircularProgressDrawable.this.mFinishing) {
            CircularProgressDrawable.this.mFinishing = false;
            animator.cancel();
            animator.setDuration(1332L);
            animator.start();
            this.val$ring.setShowArrow(false);
            return;
        }
        CircularProgressDrawable.this.mRotationCount = CircularProgressDrawable.this.mRotationCount + 1.0f;
    }

    public void onAnimationStart(Animator animator) {
        CircularProgressDrawable.this.mRotationCount = 0.0f;
    }
}
