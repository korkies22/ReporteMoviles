/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package android.support.v4.widget;

import android.animation.ValueAnimator;
import android.support.v4.widget.CircularProgressDrawable;

class CircularProgressDrawable
implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ CircularProgressDrawable.Ring val$ring;

    CircularProgressDrawable(CircularProgressDrawable.Ring ring) {
        this.val$ring = ring;
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float f = ((Float)valueAnimator.getAnimatedValue()).floatValue();
        CircularProgressDrawable.this.updateRingColor(f, this.val$ring);
        CircularProgressDrawable.this.applyTransformation(f, this.val$ring, false);
        CircularProgressDrawable.this.invalidateSelf();
    }
}
