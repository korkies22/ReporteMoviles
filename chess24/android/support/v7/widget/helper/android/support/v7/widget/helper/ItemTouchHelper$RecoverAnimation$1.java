/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package android.support.v7.widget.helper;

import android.animation.ValueAnimator;
import android.support.v7.widget.helper.ItemTouchHelper;

class ItemTouchHelper.RecoverAnimation
implements ValueAnimator.AnimatorUpdateListener {
    ItemTouchHelper.RecoverAnimation() {
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        RecoverAnimation.this.setFraction(valueAnimator.getAnimatedFraction());
    }
}
